package Model.Objects;

import java.time.LocalDate;

public class VisaPayment extends Payment {
    private int cardNumber;
    private int threeNumbers;
    private LocalDate date;
    private String ownerId;
    private String ownerFirstName;
    private String ownerLastName;

    public VisaPayment(int cardNumber, int threeNumbers, LocalDate date, String ownerId, String ownerFirstName, String ownerLastName) {
        this.cardNumber = cardNumber;
        this.threeNumbers = threeNumbers;
        this.date = date;
        this.ownerId = ownerId;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
    }

    @Override
    public boolean isPaypal() {
        return false;
    }

    @Override
    public boolean isVisa() {
        return true;
    }

    @Override
    public String toString() {
        return ("Visa Payment: "+cardNumber+"|"+threeNumbers+"|"+date.toString()+"|"+ownerId+"|"+ownerFirstName+" "+ownerLastName);
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getThreeNumbers() {
        return threeNumbers;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }
}
