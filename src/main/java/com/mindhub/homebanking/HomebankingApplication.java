package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
		System.out.println("Application started");
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args) -> {

			Client clientOne = new Client("Melba","Morel","melba@mindhub.com",passwordEncoder.encode("123"));
			clientRepository.save(clientOne);
			Client clientTwo = new Client("Admin","Admin","zaratelucas63@gmail.com",passwordEncoder.encode("123"));
			clientRepository.save(clientTwo);


			Account accountOne = new Account("VIN001",LocalDateTime.now(), 5000.0, clientOne);
			accountRepository.save(accountOne);
			Account accountTwo = new Account("VIN002",LocalDateTime.now().plusDays(1),7500.0, clientOne);
			accountRepository.save(accountTwo);
			Account accountThree = new Account("VIN003",LocalDateTime.now(),1000000.0, clientTwo);
			accountRepository.save(accountThree);


			Transaction transactionOne = new Transaction(TransactionType.DEBIT,"Crunchyroll SA", 162.0,LocalDateTime.now(),accountOne);
			transactionRepository.save(transactionOne);
			Transaction transactionTwo = new Transaction(TransactionType.CREDIT,"Guillermo Bergesio", 66999.99,LocalDateTime.now(), accountOne);
			transactionRepository.save(transactionTwo);
			Transaction transactionThree = new Transaction(TransactionType.DEBIT,"Steam", 659.67,LocalDateTime.now(), accountOne);
			transactionRepository.save(transactionThree);
			Transaction transactionFour = new Transaction(TransactionType.CREDIT,"Valentino Arena",1600.00,LocalDateTime.now(),accountTwo);
			transactionRepository.save(transactionFour);
			Transaction transactionFive = new Transaction(TransactionType.CREDIT,"Ivo Pascal",5400.00,LocalDateTime.now(),accountTwo);
			transactionRepository.save(transactionFive);
			Transaction transactionSix = new Transaction(TransactionType.DEBIT,"LYS trapos de piso",2800.00,LocalDateTime.now(),accountTwo);
			transactionRepository.save(transactionSix);


			Loan loanOne = new Loan("Personal",100000, List.of(6,12,24));
			loanRepository.save(loanOne);
			Loan loanTwo = new Loan("Car",300000,List.of(6,12,24,36));
			loanRepository.save(loanTwo);
			Loan loanThree = new Loan("Mortgage",500000,List.of(12,24,36,48,60));
			loanRepository.save(loanThree);


			ClientLoan clientLoanOne = new ClientLoan(400000.0,60,clientOne,loanThree);
			clientLoanRepository.save(clientLoanOne);
			ClientLoan clientLoanTwo = new ClientLoan(50000.0,12,clientOne,loanOne);
			clientLoanRepository.save(clientLoanTwo);
			ClientLoan clientLoanThree = new ClientLoan(100000.0, 24,clientTwo,loanThree);
			clientLoanRepository.save(clientLoanThree);
			ClientLoan clientLoanFour = new ClientLoan(200000.0,36,clientTwo,loanTwo);
			clientLoanRepository.save(clientLoanFour);


			Card cardOne = new Card(clientOne,CardType.DEBIT, CardColor.GOLD, 2383993203125432L, (short) 633,LocalDate.now().plusYears(5),LocalDate.now());
			cardRepository.save(cardOne);
			Card cardTwo = new Card(clientOne,CardType.CREDIT, CardColor.TITANIUM, 6789238745890129L, (short) 952,LocalDate.now().plusYears(5),LocalDate.now());
			cardRepository.save(cardTwo);
			Card cardThree = new Card(clientTwo,CardType.CREDIT,CardColor.SILVER,4378548901292367L, (short) 294,LocalDate.now().plusYears(5),LocalDate.now());
			cardRepository.save(cardThree);

		};
	}

}
