package com.morka.bank.service.impl;

import com.morka.bank.dto.AddDepositAgreementDto;
import com.morka.bank.exception.DepositAgreementNumberExists;
import com.morka.bank.exception.InvalidDepositBalanceException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepositAgreementServiceImpl implements DepositAgreementService {

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
        repository.save(agreement);
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
        currentAccount.setName("Current: " + clientFullname);
        currentAccount.setCurrencyType(currencyType);
        currentAccount.setCredit(balance);
        return currentAccount;
    }

    private static Account getPercentAccount(CurrencyType currencyType, String clientFullName) {
        var currentAccount = new Account();
        currentAccount.setNumber(AccountCode.INDIVIDUAL, getRandomNumberSuffix());
        currentAccount.setActivity(AccountActivity.PASSIVE);
        currentAccount.setName("Percent: " + clientFullName);
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
