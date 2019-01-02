package Controller;

import Model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.Observable;

public class PaymentWindowController extends Observable {

    public TextField firstNamePayment;
    public TextField lastNamePayment;
    public TextField cardNumberPayment;
    public TextField idPayment;
    public TextField cvvPayment;
    public DatePicker datePayment;
    public TextField emailPayment;
    public TextField passwordPayment;

    private Model model;
    private int requestID;

    public void setData(Model model, int requestID) {
        this.model = model;
        this.requestID = requestID;
    }

    public void payVisaPayment(ActionEvent event) {
        if (firstNamePayment.getText().isEmpty()) {
            Massage.errorMassage("Please fill first name");
        } else if (isNumber(cardNumberPayment.getText()) == false)
            Massage.errorMassage("Error in input");
        else if (isNumber(idPayment.getText()) == false)
            Massage.errorMassage("ID must be number");
        else if (isNumber(cvvPayment.getText()) == false)
            Massage.errorMassage("cvv must be number");
        else if (cvvPayment.getText().length() != 3)
            Massage.errorMassage("cvv must be 3 digits");
        else if(lastNamePayment.getText().isEmpty())
            Massage.errorMassage("please fill last name ");
        else if(cardNumberPayment.getText().isEmpty())
            Massage.errorMassage("please fill card number");
        else if(idPayment.getText().isEmpty())
            Massage.errorMassage("please fill ID");
        else if(cvvPayment.getText().isEmpty())
            Massage.errorMassage("please fill cvv");
        else if (datePayment.getValue() == null)
            Massage.errorMassage("please fill date");


        else {
//            if (model.payForVacation(requestID, new VisaPayment((int) (Integer.parseInt(cardNumberPayment.getText())), (int) (Integer.parseInt(cvvPayment.getText())), datePayment.getValue(), idPayment.getText(), firstNamePayment.getText(), lastNamePayment.getText()))) {
//                closeStage(event);
//                setChanged();
//                notifyObservers();
//                Massage.infoMassage("Payment was made successfully");
//            } else {
//                Massage.errorMassage("Payment failed");
//            }
        }
    }

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void cancelPayment(javafx.event.ActionEvent event) {
        closeStage(event);
    }

    public void payPaypalPayment(ActionEvent event) {
        if (emailPayment.getText().isEmpty() || passwordPayment.getText().isEmpty()) {
            Massage.errorMassage("Please fill all the fields");
        }
//        if (model.payForVacation(requestID, new PayaplPayment(emailPayment.getText(), passwordPayment.getText()))) {
//            closeStage(event);
//            setChanged();
//            notifyObservers();
//            Massage.infoMassage("Payment was made successfully");
//        } else {
//            Massage.errorMassage("Payment failed");
//        }
    }

    private void closeStage(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
