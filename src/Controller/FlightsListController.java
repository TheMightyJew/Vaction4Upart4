package Controller;

import Model.Objects.Flight;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.net.URL;
import java.util.*;

public class FlightsListController extends Observable implements Observer, Initializable {

    private ArrayList<Flight> flights;
    public TableView<Flight> flightsTable;
    @Override
    public void update(Observable o, Object arg) {
        if(arg!=null){
            Flight newOne=(Flight)(arg);
            flights.add(newOne);
            flightsTable.getItems().addAll(newOne);
        }
    }

    public void addFlight(Event event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/AddFlight.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            AddFlightController viewController =fxmlLoader.getController();
            viewController.addObserver(this);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add flight");
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void reset(Event event){
        if(Massage.confirmMassage("Are you sure you want to reset all the flights above?")){
            flights=new ArrayList<>();
            flightsTable.getItems().clear();
        }
    }

    public void confirm(ActionEvent event){
        if(Massage.confirmMassage("Are you sure these are all the flights of the vacation?")) {
            setChanged();
            notifyObservers(flights);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        flights=new ArrayList<>();
        flightsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Flight, String> flightCompany = new TableColumn<>("Flight Company");
        TableColumn<Flight, String> sourceAirPort = new TableColumn<>("From AirPort");
        TableColumn<Flight, String> destinationAirPort = new TableColumn<>("To AirPort");
        TableColumn<Flight, String> departDate = new TableColumn<>("Depart Date");
        TableColumn<Flight, String> landDate = new TableColumn<>("Land Date");
        TableColumn<Flight, String> departHour = new TableColumn<>("Depart Hour");
        TableColumn<Flight, String> landHour = new TableColumn<>("Land Hour");
        flightCompany.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFlightCompany()));
        sourceAirPort.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSourceAirPort()));
        destinationAirPort.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDestinationAirPort()));
        departDate.setCellValueFactory(param -> new SimpleStringProperty((param).getValue().getDepartDate().toString()));
        landDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLandDate().toString()));
        departHour.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDepartHour()));
        landHour.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLandHour()));
        flightsTable.getColumns().addAll(flightCompany, sourceAirPort, destinationAirPort, departDate, landDate, departHour, landHour);
    }

    public void setFlights(ArrayList<Flight> flights) {
        if(flights==null)
            flights=new ArrayList<>();
        this.flights = flights;
        showFlights();
    }

    private void showFlights() {
        flightsTable.getItems().addAll(flights);
    }
}
