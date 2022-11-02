package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {

    private Long loanId;

    private Double amount;

    private Integer payment;

    private String numberAccount;


    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(Long loanId, Double amount, Integer payment, String numberAccount) {
        this.loanId = loanId;
        this.amount = amount;
        this.payment = payment;
        this.numberAccount = numberAccount;
    }


    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayment() {
        return payment;
    }

    public String getNumberAccount() {
        return numberAccount;
    }
}
