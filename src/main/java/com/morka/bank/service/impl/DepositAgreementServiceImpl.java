package com.morka.bank.service.impl;

import com.morka.bank.dto.AddDepositAgreementDto;
import com.morka.bank.dto.DepositAgreementDto;
import com.morka.bank.exception.DepositAgreementNumberExists;
import com.morka.bank.exception.InvalidDepositBalanceException;
import com.morka.bank.mapper.Mapper;
import com.morka.bank.model.Account;
import com.morka.bank.model.AccountActivity;
import com.morka.bank.model.AccountCode;
import com.morka.bank.model.CurrencyType;
import com.morka.bank.model.DepositAgreement;
import com.morka.bank.model.DepositCurrency;
import com.morka.bank.repository.AccountRepository;
import com.morka.bank.repository.DepositAgreementRepository;
import com.morka.bank.service.ClientService;
import com.morka.bank.service.DepositAgreementService;
import com.morka.bank.service.DepositCurrencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepositAgreementServiceImpl implements DepositAgreementService {

    private final Mapper mapper;

    private final DepositAgreementRepository repository;

    private final ClientService clientService;

    private final DepositCurrencyService depositCurrencyService;

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void createDepositAgreement(AddDepositAgreementDto dto) {
        validateNumber(dto.getNumber());
        var client = clientService.getClient(dto.getClientId());
        var depositCurrency = depositCurrencyService.findById(dto.getDepositCurrencyId());
        var balance = validateAndGetBalance(dto.getDepositBalance(), depositCurrency);
        var endDate = dto.getStartDate().plusDays(depositCurrency.getPeriodInDays());
        var currentAccount = accountRepository.save(
                getCurrentAccount(depositCurrency.getCurrencyType(), balance, client.getFullName()));
        var percentAccount = accountRepository.save(
                getPercentAccount(depositCurrency.getCurrencyType(), client.getFullName()));
        var agreement = new DepositAgreement();
        agreement.setClient(client);
        agreement.setDepositCurrency(depositCurrency);
        agreement.setNumber(dto.getNumber());
        agreement.setStartDate(dto.getStartDate());
        agreement.setDepositBalance(balance);
        agreement.setEndDate(endDate);
        agreement.setCurrentAccount(currentAccount);
        agreement.setPercentAccount(percentAccount);
        agreement.setUpdatedAt(dto.getStartDate());
        agreement.setLastPaymentDate(dto.getStartDate());
        repository.save(agreement);
    }

    @Override
    @Transactional
    public void finishAtDay(LocalDate date) {
        var cash = DataAccessUtils.singleResult(accountRepository.findByCode(AccountCode.BANK_CASH));
        var bank = DataAccessUtils.singleResult(accountRepository.findByCode(AccountCode.BANK_DEVELOPMENT_FUND));
        var agreements = repository.findAllByExpiredTsIsNull();
        for (var agreement : agreements) {
            finishAgreement(date, agreement, cash, bank);
            agreement.setUpdatedAt(date);
        }
    }

    @Override
    public Page<DepositAgreementDto> getAgreements(Pageable pageable) {
        return repository.findAllByExpiredTsIsNull(pageable)
                .map(depositAgreement -> mapper.map(depositAgreement, DepositAgreementDto.class));
    }

    @Override
    @Transactional
    public void close(Long id) {
        var depositAgreement = getById(id);
        var cash = DataAccessUtils.singleResult(accountRepository.findByCode(AccountCode.BANK_CASH));
        var bank = DataAccessUtils.singleResult(accountRepository.findByCode(AccountCode.BANK_DEVELOPMENT_FUND));
        var currentAccount = depositAgreement.getCurrentAccount();
        var percentAccount = depositAgreement.getPercentAccount();
        var initialBalance = depositAgreement.getDepositBalance();

        bank.setDebit(bank.getDebit() - initialBalance);
        currentAccount.setCredit(currentAccount.getCredit() + initialBalance);

        payBackToCash(cash, currentAccount);

        var expiredTs = Instant.now();
        depositAgreement.setExpiredTs(expiredTs);
        currentAccount.setExpiredTs(expiredTs);
        percentAccount.setExpiredTs(expiredTs);
    }

    private DepositAgreement getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No deposit agreement found."));
    }

    private void finishAgreement(LocalDate date, DepositAgreement agreement, Account cash, Account bank) {
        if (date.isBefore(agreement.getUpdatedAt()) || date.isEqual(agreement.getUpdatedAt())) {
            log.info("Date={} is <= agreement={} last update date={}.", date, agreement.getId(), agreement.getStartDate());
            return;
        }
        var currentAccount = agreement.getCurrentAccount();
        var firstUpdated = agreement.getStartDate().isEqual(agreement.getUpdatedAt());
        if (firstUpdated) {
            sendMoneyToBank(bank, currentAccount);
        }

        var percentAccount = agreement.getPercentAccount();
        var lastPaymentDate = agreement.getLastPaymentDate();
        var uncoveredPaymentDates = agreement.getStartDate().datesUntil(date.plusDays(1), Period.ofMonths(1))
                .filter(paymentDate -> paymentDate.isAfter(lastPaymentDate))
                .toList();
        log.info("Uncovered payment days: {}", uncoveredPaymentDates);
        var totalMonths = uncoveredPaymentDates.size();
        var totalDays = totalMonths == 0
                ? lastPaymentDate.until(date).getDays()
                : uncoveredPaymentDates.get(uncoveredPaymentDates.size() - 1).until(date).getDays();

        var depositCurrency = agreement.getDepositCurrency();
        var percent = depositCurrency.getPercent();
        var bet = depositCurrency.isHasCapitalization()
                ? calculateWithCapitalization(percent, depositCurrency.getPeriodInDays())
                : percent;

        log.info("Bet is = {}", bet);
        log.info("Should be payed for months passed = {} + days = {}", totalMonths, totalDays);
        if (date.isAfter(agreement.getEndDate()) || date.isEqual(agreement.getEndDate())) {
            log.info("Date={} covers agreement={} full period (end={}).", date, agreement.getId(), agreement.getEndDate());
            var months = totalDays / 30.0 + totalMonths;
            var coef = bet
                    .multiply(BigDecimal.valueOf(months))
                    .divide(BigDecimal.valueOf(1200));
            var percentProfit = coef.multiply(BigDecimal.valueOf(currentAccount.getCredit())).longValue();
            log.info("Total profit for {} months is = {}", months, percentProfit);
            sendPercent(bank, percentProfit, percentAccount);
            payBackToCash(cash, percentAccount);
            agreement.setLastPaymentDate(agreement.getEndDate());
            return;
        }

        if (agreement.getDepositCurrency().isRevocable() && totalMonths > 0) {
            log.info("Date={} covers months={} for agreement={} period (start={}, end={}).", date, totalMonths, agreement.getId(), agreement.getStartDate(), agreement.getEndDate());
            var coef = bet
                    .multiply(BigDecimal.valueOf(totalMonths))
                    .divide(BigDecimal.valueOf(1200));
            var percentProfit = coef.multiply(BigDecimal.valueOf(currentAccount.getCredit())).longValue();
            log.info("Revocable Total profit for {} month is = {}", totalMonths, percentProfit);
            sendPercent(bank, percentProfit, percentAccount);
            agreement.setLastPaymentDate(uncoveredPaymentDates.get(uncoveredPaymentDates.size() - 1));
            return;
        }
        log.info("Date={} not covers non-revocable agreement={} full period (start={}, end={}).", date, agreement.getId(), agreement.getEndDate(), agreement.getEndDate());
    }

    private void payBackToCash(Account cash, Account account) {
        if (account.getCredit() > account.getDebit()) {
            var total = account.getCredit() - account.getDebit();
            cash.setDebit(cash.getDebit() + total);
            account.setDebit(account.getCredit());
        }
    }

    private void sendPercent(Account bank, Long percentProfit, Account percentAccount) {
        percentAccount.setCredit(percentAccount.getCredit() + percentProfit);
        bank.setDebit(bank.getDebit() - percentProfit);
    }

    private void sendMoneyToBank(Account bank, Account currentAccount) {
        if (currentAccount.getCredit() > currentAccount.getDebit()) {
            var diff = currentAccount.getCredit() - currentAccount.getDebit();
            bank.setCredit(bank.getCredit() + diff);
            currentAccount.setDebit(currentAccount.getCredit());
        }
    }

    private BigDecimal calculateWithCapitalization(BigDecimal percent, Integer periodInDays) {
        log.info("Calculate capitalization percent...");
        var term = percent
                .divide(BigDecimal.valueOf(1200))
                .add(BigDecimal.ONE)
                .doubleValue();
        var years = periodInDays / 365.0;
        return BigDecimal.valueOf(Math.pow(term, 12.0 * years))
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(years));
    }

    private void validateNumber(String number) {
        if (repository.existsByNumber(number)) {
            throw new DepositAgreementNumberExists("Deposit agreement number with such number already exists.");
        }
    }

    private static Account getCurrentAccount(CurrencyType currencyType, Long balance, String clientFullname) {
        var currentAccount = new Account();
        currentAccount.setNumber(AccountCode.INDIVIDUAL, getRandomNumberSuffix());
        currentAccount.setActivity(AccountActivity.PASSIVE);
        currentAccount.setName(clientFullname + " - Текущий");
        currentAccount.setCurrencyType(currencyType);
        currentAccount.setCredit(balance);
        return currentAccount;
    }

    private static Account getPercentAccount(CurrencyType currencyType, String clientFullName) {
        var currentAccount = new Account();
        currentAccount.setNumber(AccountCode.INDIVIDUAL, getRandomNumberSuffix());
        currentAccount.setActivity(AccountActivity.PASSIVE);
        currentAccount.setName(clientFullName + " - Процентный");
        currentAccount.setCurrencyType(currencyType);
        return currentAccount;
    }

    private static String getRandomNumberSuffix() {
        var random = String.valueOf(ThreadLocalRandom.current().nextLong(9999999999L));
        var leadingZerosRequired = 10 - random.length();
        return "0".repeat(leadingZerosRequired) + random;
    }

    private static Long validateAndGetBalance(Long depositBalance, DepositCurrency depositCurrency) {
        if (depositCurrency.getMinDepositSize() > depositBalance) {
            throw new InvalidDepositBalanceException("Your deposit balance is less then needed.");
        }
        return depositBalance;
    }
}
