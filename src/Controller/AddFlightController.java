package Controller;

import Model.Objects.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddFlightController extends Observable implements Initializable {

    public TextField flightCompany;
    public TextField fromAirport;
    public TextField toAirport;
    public DatePicker departDate;
    public TextField departHour;
    public DatePicker landingDate;
    public TextField landingHour;

    public void cancel(ActionEvent event){
        if(Massage.confirmMassage("Are you sure you don't want to add a flight?"))
            closeStage(event);
    }

    public void confirm(ActionEvent event){
        if(fieldsProblem()==false && Massage.confirmMassage("Are you sure you want to add this flight?")){
            setChanged();
            notifyObservers(new Flight(flightCompany.getText(),fromAirport.getText(),toAirport.getText(),departDate.getValue(),landingDate.getValue(),departHour.getText(),landingHour.getText()));
            closeStage(event);
        }
    }

    private boolean fieldsProblem() {
        if (flightCompany.getText().isEmpty() || fromAirport.getText().isEmpty() || departHour.getText().isEmpty() || landingHour.getText().isEmpty() || toAirport.getText().isEmpty() || departDate.getValue() == null || landingDate.getValue() == null){
            Massage.errorMassage("Please fill all the fields");
            return true;
        }
        else if(hourContextProblem(departHour.getText()) || hourContextProblem(landingHour.getText())){
            Massage.errorMassage("Hour pattern must be <Hours:Minutes> pattern (0<=Hours<=23 , 0<=Minutes<=59)");
            return true;
        }
        else if(departDate.getValue().isAfter(landingDate.getValue()) || (departDate.getValue().isEqual(landingDate.getValue()) && compareHours(departHour.getText(),landingHour.getText())!=1)){
            Massage.errorMassage("Depart time must be before landing time");
            return true;
        }
        else if(departDate.getValue().isBefore(LocalDate.now())){
            Massage.errorMassage("Depart time must be in the future");
            return true;
        }
        return false;
    }

    private void closeStage(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private static boolean hourContextProblem(String hour) {
        try{
            String [] time=hour.split(":");
            if(time.length!=2 || time[0].length()!=2 || time[1].length()!=2)
                return true;
            if(Integer.parseInt(time[0])>23 || Integer.parseInt(time[0])<0)
                return true;
            if(Integer.parseInt(time[1])>59 || Integer.parseInt(time[1])<0)
                return true;
            return false;
        }
        catch (Exception e){
            return true;
        }
    }

    private int compareHours(String departHour , String landingHour) {
        String[] departTime=departHour.split(":");
        String[] landTime=landingHour.split(":");
        Integer departHours=Integer.parseInt(departTime[0]);
        Integer landingHours=Integer.parseInt(landTime[0]);
        Integer departMinutes=Integer.parseInt(departTime[1]);
        Integer landingMinutes=Integer.parseInt(landTime[1]);
        if(departHours.compareTo(landingHours)==0)
            return departMinutes.compareTo(landingMinutes);
        else
            return departHours.compareTo(landingHours);
    }

    private void changeDateFormat(DatePicker dp, String pattern)
    {
        dp.setPromptText(pattern.toLowerCase());

        dp.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        dp.setPromptText(pattern);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String pattern = "dd-MM-yyyy";
        changeDateFormat(departDate,pattern);
        changeDateFormat(landingDate,pattern);
    }
}
