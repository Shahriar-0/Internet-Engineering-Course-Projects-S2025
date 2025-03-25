package domain.exceptions.customer;

import domain.exceptions.DomainException;

public class NotEnoughCredit extends DomainException {

    public NotEnoughCredit(long totalCost, long credit) {
        super(createMessage(totalCost, credit));
    }

    private static String createMessage(long totalCost, long credit) {
        return "Not enough credit! Required credit: " + totalCost + ", Current credit: " + credit;
    }
}
