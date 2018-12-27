package Model.Objects;

public class PayaplPayment extends Payment {
    private String email;
    private String password;

    public PayaplPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean isPaypal() {
        return true;
    }

    @Override
    public boolean isVisa() {
        return false;
    }

    @Override
    public String toString() {
        return ("Paypal Payment: "+ email +"|"+password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
