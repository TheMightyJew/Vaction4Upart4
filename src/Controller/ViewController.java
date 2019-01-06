package Controller;

import Model.Model;
import Model.Objects.*;
import Model.Requests.PurchaseARequest;
import Model.Requests.PurchaseRequestData;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ViewController implements Initializable, Observer {

    //tabs
    public TabPane tabPane;
    public TabPane vacationTabPane;
    public Tab signTab;
    public Tab homeTab;
    public Tab createTab;
    public Tab readTab;
    public Tab updateTab;
    public Tab vacationsTab;
    public Tab searchTab;
    public Tab publishTab;
    public Tab requestTab;
    public Tab personalTab;
    //Sign in tab
    public TextField usernameSign;
    public PasswordField passwordSign;
    public Button submit;
    //Home tab
    public Label usernameHome;
    public Label firstHome;
    public Label lastHome;
    public Label birthHome;
    public Label cityHome;
    public Label countryHome;
    public Button delete;
    public Button signOut;
    //Create tab
    public TextField usernameCreate;
    public TextField passwordCreate;
    public TextField confirmCreate;
    public TextField firstCreate;
    public TextField lastCreate;
    public DatePicker birthCreate;
    public TextField cityCreate;
    public TextField countryCreate;
    public Button create;
    //Read tab
    public TextField usernameRead;
    public Button show;
    public Label firstRead;
    public Label lastRead;
    public Label birthRead;
    public Label cityRead;
    public Label countryRead;
    //update tab
    public TextField usernameUpdate;
    public TextField passwordUpdate;
    public TextField confirmUpdate;
    public TextField firstUpdate;
    public TextField lastUpdate;
    public DatePicker birthUpdate;
    public TextField cityUpdate;
    public TextField countryUpdate;
    public Button update;

    //vacations tab
    //search tab
    public GridPane gridPane_searchFilters;
    public TextField textField_flightCompany;
    public ComboBox comboBox_ticketsType;
    public TextField textField_sourceCountry;
    public TextField textField_destinationCountry;
    public TextField textField_maxPricePerTicket;
    public TextField textField_ticketsQuantity;
    public TextField textField_baggage;
    public TextField textField_hospitality;
    public CheckBox checkBox_baggage;
    public CheckBox checkBox_hospitality;
    public ComboBox comboBox_flightType;
    public ComboBox comboBox_vacationType;
    public DatePicker datePicker_fromDate;
    public DatePicker datePicker_toDate;
    //publish tab
    public TextField sourcePublish;
    public TextField destinationPublish;
    public TextField ticketsNumPublish;
    public TextField pricePublish;
    public TextField baggageLimitPublish;
    public TextField hospitalityRankPublish;
    public DatePicker fromDatePublish;
    public DatePicker toDatePublish;
    public ChoiceBox<Vacation.Tickets_Type> ticketsClassPublish;
    public CheckBox partTicketsPublish;
    public ChoiceBox<Vacation.Vacation_Type> vacationTypePublish;
    public ChoiceBox<Vacation.Flight_Type> flightTypePublish;
    public CheckBox hospitalityPublish;
    public CheckBox baggagePublish;
    public Button flightListBut;

    //requests tab
    public TableView tableView_myRequests;
    public TableView tableView_receivedRequests;


    private final String directoryPath = "C:/DATABASE/";//////
    private final String databaseName = "database.db";
    private final String tableName = "Users_Table";

    private Model model;
    private String username = "";
    private boolean loggedIn = false;
    private ArrayList<Flight> flights;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabSignOut();
        String pattern = "dd-MM-yyyy";
        changeDateFormat(birthCreate, pattern);
        changeDateFormat(birthUpdate, pattern);
        username = "";
        //publishTab
        baggageLimitPublish.setText("0");
        baggageLimitPublish.setDisable(true);
        hospitalityRankPublish.setText("0");
        hospitalityRankPublish.setDisable(true);
        vacationTypePublish.getItems().addAll(Vacation.Vacation_Type.values());
        flightTypePublish.getItems().addAll(Vacation.Flight_Type.values());
        ticketsClassPublish.getItems().addAll(Vacation.Tickets_Type.values());
        setTabsClosable(false);
        //searchTab
        tabSearchInit();
        tabRequestsInit();
    }

    private void tabRequestsInit() {
        Callback<TableColumn<PurchaseARequest, String>, TableCell<PurchaseARequest, String>> cellFactory1
                = //
                new Callback<TableColumn<PurchaseARequest, String>, TableCell<PurchaseARequest, String>>() {
                    @Override
                    public TableCell<PurchaseARequest, String> call(final TableColumn<PurchaseARequest, String> param) {
                        final TableCell<PurchaseARequest, String> cell = new TableCell<PurchaseARequest, String>() {

                            final Button btn = new Button("Buy");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Stage stage = new Stage();
                                        stage.setAlwaysOnTop(false);
                                        stage.setResizable(false);
                                        stage.setTitle("Payment Window");

                                        Parent root = null;
                                        FXMLLoader fxmlLoader = null;
                                        try {
                                            fxmlLoader = new FXMLLoader(getClass().getResource("../View/PaymentWindow.fxml"));
                                            root = fxmlLoader.load();
                                            PaymentWindowController controller = fxmlLoader.getController();
                                            controller.setData(model, getTableView().getItems().get(getIndex()).getId());
                                        } catch (IOException e) {
                                            e.printStackTrace();
//                                            showAlert("Exception!");
                                        }
                                        Scene scene = new Scene(root);
                                        stage.setScene(scene);
//                                        AView view = fxmlLoader.getController();
//                                        view.setViewModel(viewModel);
//                                        viewModel.addObserver(view);
//                                        stage.initModality(Modality.APPLICATION_MODAL);
                                        stage.setOnCloseRequest(event1 -> refreshRequests());
                                        stage.show();

                                    });
                                    PurchaseARequest purchaseRequest = getTableView().getItems().get(getIndex());
                                    if (purchaseRequest.getStatus().equals(PurchaseARequest.Request_Status.accepted)) {
                                        setGraphic(btn);
                                        setText(null);
                                    } else {
                                        setGraphic(null);
                                        setText(purchaseRequest.getStatus().toString());
                                    }
                                }
                            }
                        };
                        return cell;
                    }
                };
        tableView_myRequests = getRequestsTableView(tableView_myRequests, cellFactory1);

        Callback<TableColumn<PurchaseARequest, String>, TableCell<PurchaseARequest, String>> cellFactory2
                = //
                new Callback<TableColumn<PurchaseARequest, String>, TableCell<PurchaseARequest, String>>() {
                    @Override
                    public TableCell<PurchaseARequest, String> call(final TableColumn<PurchaseARequest, String> param) {
                        final TableCell<PurchaseARequest, String> cell = new TableCell<PurchaseARequest, String>() {

                            final Button btn = new Button("Accept request");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
//                                        showAlert()
                                        PurchaseARequest purchaseRequest = getTableView().getItems().get(getIndex());
                                        model.acceptPurchaseRequest(purchaseRequest.getId());
                                        refreshRequests();
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        Callback<TableColumn<PurchaseARequest, String>, TableCell<PurchaseARequest, String>> cellFactory3
                = //
                new Callback<TableColumn<PurchaseARequest, String>, TableCell<PurchaseARequest, String>>() {
                    @Override
                    public TableCell<PurchaseARequest, String> call(final TableColumn<PurchaseARequest, String> param) {
                        final TableCell<PurchaseARequest, String> cell = new TableCell<PurchaseARequest, String>() {

                            final Button btn = new Button("reject request");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
//                                        showAlert()
                                        PurchaseARequest purchaseRequest = getTableView().getItems().get(getIndex());
                                        model.rejectPurchaseRequest(purchaseRequest.getId());
                                        refreshRequests();
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        tableView_receivedRequests = getRequestsTableView(tableView_receivedRequests, cellFactory2, cellFactory3);


        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == requestTab) {
                refreshRequests();
            }
        });
    }

    private void refreshRequests() {
        tableView_myRequests.getItems().clear();
        tableView_myRequests.getItems().addAll(model.getMyPurchaseRequests(username));
        tableView_receivedRequests.getItems().clear();
        tableView_receivedRequests.getItems().addAll(model.getReceivedPurchaseRequests(username));
    }

    private void tabSearchInit() {
        textField_baggage.setDisable(true);
        textField_hospitality.setDisable(true);
        checkBox_baggage.selectedProperty().addListener((observable, oldValue, newValue) -> textField_baggage.setDisable(!newValue));
        checkBox_hospitality.selectedProperty().addListener((observable, oldValue, newValue) -> textField_hospitality.setDisable(!newValue));
        comboBox_flightType.getItems().addAll(Vacation.Flight_Type.values());
        comboBox_vacationType.getItems().addAll(Vacation.Vacation_Type.values());
        comboBox_ticketsType.getItems().addAll(Vacation.Tickets_Type.values());
        textFieldNumbersOnlyRestrict(textField_baggage);
        textFieldNumbersOnlyRestrict(textField_hospitality);
    }

    private void textFieldNumbersOnlyRestrict(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void setTabsClosable(boolean b) {
        signTab.setClosable(b);
        homeTab.setClosable(b);
        createTab.setClosable(b);
        readTab.setClosable(b);
        updateTab.setClosable(b);
        vacationsTab.setClosable(b);
        searchTab.setClosable(b);
        publishTab.setClosable(b);
        requestTab.setClosable(b);
        personalTab.setClosable(b);
    }

    private void changeDateFormat(DatePicker dp, String pattern) {
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

    public void setModel(Model model) {
        this.model = model;
    }

    public void tabSignOut() {
        clearAll();
        username = "";
        tabPane.getTabs().remove(0, tabPane.getTabs().size());
        tabPane.getTabs().add(signTab);
        tabPane.getTabs().add(createTab);
        vacationTabPane.getTabs().removeAll(publishTab);
        tabPane.getTabs().add(vacationsTab);
        createTab.setText("Sign up");
        create.setText("Sign up!");
        create.setOnAction(this::signUp);
    }

    public void tabSignIn() {
        clearAll();
        tabPane.getTabs().remove(0, 3);
        vacationTabPane.getTabs().add(publishTab);
        tabPane.getTabs().addAll(personalTab, vacationsTab, requestTab);
    }

    public void signUp(ActionEvent event) {
        if (create(event)) {
            signIn(event, usernameCreate.getText(), passwordCreate.getText());
            clearCreate();
            event.consume();
        }
    }

    private void clearAll() {
        clearSignIn();
        clearHome();
        clearCreate();
        clearRead();
        clearUpdate();
    }

    private void clearSignIn() {
        usernameSign.clear();
        passwordSign.clear();
    }

    private void clearHome() {
        usernameHome.setText("");
        firstHome.setText("");
        lastHome.setText("");
        birthHome.setText("");
        cityHome.setText("");
        countryHome.setText("");
    }

    private void clearCreate() {
        usernameCreate.clear();
        passwordCreate.clear();
        confirmCreate.clear();
        lastCreate.clear();
        firstCreate.clear();
        cityCreate.clear();
        birthCreate.setValue(null);
        countryCreate.clear();
    }

    private void clearRead() {
        usernameRead.clear();
        firstRead.setText("");
        lastRead.setText("");
        birthRead.setText("");
        cityRead.setText("");
        countryRead.setText("");
    }

    private void clearUpdate() {
        usernameUpdate.clear();
        passwordUpdate.clear();
        firstUpdate.clear();
        lastUpdate.clear();
        birthUpdate.setValue(null);
        cityUpdate.clear();
        countryUpdate.clear();
    }

    public boolean create(ActionEvent event) {
        if (createEmpty() == true) {
            Massage.errorMassage("Please fill all the fields");
        } else if (passwordCreate.getText().equals(confirmCreate.getText()) == false) {
            Massage.errorMassage("Password must be match in both options");
        } else if (model.userExist(usernameCreate.getText()) == true) {
            Massage.errorMassage("Username already taken");
        } else if (dateCheck(birthCreate.getValue()) == false) {
            Massage.errorMassage("Age must be at least 18");
        } else if (Massage.confirmMassage("Are you sure you want to Create an account with these details?")) {
            //model.createUser(usernameCreate.getText(),passwordCreate.getText(),DatePicker2Str(birthCreate),firstCreate.getText(),lastCreate.getText(),cityCreate.getText());
            model.createUser(new User(usernameCreate.getText(), passwordCreate.getText(), birthCreate.getValue(), firstCreate.getText(), lastCreate.getText(), cityCreate.getText(), countryCreate.getText()));
            Massage.infoMassage("User creation was made successfully!");
            event.consume();
            return true;
        }
        event.consume();
        return false;
    }

    private boolean createEmpty() {
        if (usernameCreate.getText().isEmpty() || passwordCreate.getText().isEmpty() || firstCreate.getText().isEmpty() || lastCreate.getText().isEmpty() || birthCreate.getValue() == null || birthCreate.getValue().toString().isEmpty() || cityCreate.getText().isEmpty() || countryCreate.getText().isEmpty())
            return true;
        else
            return false;
    }

    private boolean updateEmpty() {
        if (usernameUpdate.getText().isEmpty() || passwordUpdate.getText().isEmpty() || firstUpdate.getText().isEmpty() || lastUpdate.getText().isEmpty() || birthUpdate.getValue() == null || birthUpdate.getValue().toString().isEmpty() || cityUpdate.getText().isEmpty() || countryUpdate.getText().isEmpty())
            return true;
        else
            return false;
    }

    public void submit(ActionEvent event) {
        signIn(event, usernameSign.getText(), passwordSign.getText());
    }

    private void signIn(ActionEvent event, String username, String password) {
        if (model.userExist(username) == false) {
            Massage.errorMassage("Username is incorrect");
            event.consume();
        } else if (model.UsersTable_checkPassword(username, password) == false) {
            Massage.errorMassage("Username or Password are incorrect");
            event.consume();
        } else {
            this.username = username;
            tabSignIn();
            updateHome(username);
            fillUpdate(username);
            loggedIn = true;
        }
    }

    private void fillUpdate(String username) {
        User user = model.getUser(username);
        usernameUpdate.setText(user.getUsername());
        passwordUpdate.setText(user.getPassword());
        confirmUpdate.setText(user.getPassword());
        firstUpdate.setText(user.getFirst_Name());
        lastUpdate.setText(user.getLast_Name());
        birthUpdate.setValue(user.getBirth_Date());
        cityUpdate.setText(user.getCity());
        countryUpdate.setText(user.getCountry());
    }


    public void signOut(ActionEvent event) {
        if (Massage.confirmMassage("Are you sure you want to sign-out?")) {
            tabSignOut();
            loggedIn = false;
        }
        event.consume();
    }

    public void delete(ActionEvent event) {
        if (Massage.confirmMassage("Are you sure you want to delete your account?")) {
            Massage.infoMassage("Your account was deleted successfully!");
            model.deleteUser(username);
            tabSignOut();
        }
        event.consume();
    }

    public void show(ActionEvent event) {
        if (model.userExist(usernameRead.getText()) == true) {
            User user = model.getUser(usernameRead.getText());
            firstRead.setText(user.getFirst_Name());
            lastRead.setText(user.getLast_Name());
            birthRead.setText(localDate2Str(user.getBirth_Date()));
            cityRead.setText(user.getCity());
            countryRead.setText(user.getCountry());
        } else {
            Massage.errorMassage("Username is incorrect");
            event.consume();
        }
    }

    private String localDate2Str(LocalDate birth_date) {
        String str;
        str = birth_date.getDayOfMonth() + "-" + birth_date.getMonthValue() + "-" + birth_date.getYear();
        return str;
    }

    public void update(ActionEvent event) {
        if (updateEmpty() == true) {
            Massage.infoMassage("Please fill all the fields");
            event.consume();
        } else if (model.userExist(usernameUpdate.getText()) == true && usernameUpdate.getText().equals(this.username) == false) {
            Massage.errorMassage("Username already taken");
            event.consume();
        } else if (passwordUpdate.getText().equals(confirmUpdate.getText()) == false) {
            Massage.errorMassage("Password must be match in both options");
            event.consume();
        } else if (dateCheck(birthUpdate.getValue()) == false) {
            Massage.errorMassage("Age must be at least 18");
            event.consume();
        } else if (Massage.confirmMassage("Are you sure you want to update the details?")) {
            model.updateUserInfo(username, new User(usernameUpdate.getText(), passwordUpdate.getText(), birthUpdate.getValue(), firstUpdate.getText(), lastUpdate.getText(), cityUpdate.getText(), countryUpdate.getText()));
            if (usernameRead.getText().equals(this.username) == true) {
                clearRead();
            }
            username = usernameUpdate.getText();
            updateHome(username);
            Massage.infoMassage("The update was made successfully!");
        }
        event.consume();
    }

    public void baggagePublishClick(Event event) {
        if (baggagePublish.isSelected() == false) {
            baggageLimitPublish.setText("0");
            baggageLimitPublish.setDisable(true);
        } else {
            baggageLimitPublish.setDisable(false);
            baggageLimitPublish.setText("");
        }
    }

    public void hospitalityPublishClick(Event event) {
        if (hospitalityPublish.isSelected() == false) {
            hospitalityRankPublish.setText("0");
            hospitalityRankPublish.setDisable(true);
        } else {
            hospitalityRankPublish.setDisable(false);
            hospitalityRankPublish.setText("");
        }
    }

    public void partTicketsPublishClick(Event event) {
        if (partTicketsPublish.isSelected()) {
            if ((isTicketsMore1()) == false) {
                Massage.errorMassage("You can allow this only if you sell more than 1 ticket");
                partTicketsPublish.setSelected(false);
            }
        }
    }

    private boolean isTicketsMore1() {
        try {
            int num = Integer.parseInt(ticketsNumPublish.getText());
            return (num > 1);
        } catch (Exception e) {
            return false;
        }
    }

    public void publishPublish(Event event) {
        if (isPublishPorblem() == false) {
            if (model.publishVacation(new Vacation(username, fromDatePublish.getValue(), toDatePublish.getValue(), Integer.parseInt(pricePublish.getText()), Integer.parseInt(ticketsNumPublish.getText()), partTicketsPublish.isSelected(), sourcePublish.getText(), destinationPublish.getText(), baggagePublish.isSelected(), Integer.parseInt(baggageLimitPublish.getText()), ticketsClassPublish.getValue(), flights, flightTypePublish.getValue(), vacationTypePublish.getValue(), hospitalityPublish.isSelected(), Integer.parseInt(hospitalityRankPublish.getText()))) == true) {
                Massage.infoMassage("Vacation sell published successfully");
                clearPublish();
            } else
                Massage.infoMassage("Vacation sell publish has failed");
        }
    }

    public void reserPublish(ActionEvent event) {
        if (Massage.confirmMassage("Are you sure you want to reset these details?")) {
            clearPublish();
        }
    }

    private void clearPublish() {
        flights = new ArrayList<>();
        flightListBut.setText("Flights list (0)");
        toDatePublish.setValue(null);
        fromDatePublish.setValue(null);
        sourcePublish.setText("");
        destinationPublish.setText("");
        ticketsNumPublish.setText("");
        hospitalityRankPublish.setText("0");
        hospitalityRankPublish.setDisable(true);
        baggageLimitPublish.setText("0");
        baggageLimitPublish.setDisable(true);
        baggagePublish.setSelected(false);
        hospitalityPublish.setSelected(false);
        partTicketsPublish.setSelected(false);
        pricePublish.setText("");
        ticketsClassPublish.setValue(null);
        vacationTypePublish.setValue(null);
        flightTypePublish.setValue(null);

    }

    private boolean isPublishPorblem() {
        if (toDatePublish.getValue() == null || fromDatePublish.getValue() == null || sourcePublish.getText().isEmpty() || destinationPublish.getText().isEmpty() || ticketsNumPublish.getText().isEmpty() || pricePublish.getText().isEmpty() || ticketsClassPublish.getValue() == null || vacationTypePublish.getValue() == null || flightTypePublish.getValue() == null) {
            Massage.errorMassage("Please fill all the fields as needed");
            return true;
        }
        if (baggagePublish.isSelected()) {
            if (baggageLimitPublish.getText().isEmpty()) {
                Massage.errorMassage("Please fill all the fields as needed");
                return true;
            } else if (isNumber(baggageLimitPublish.getText()) == false) {
                Massage.errorMassage("Baggage limit must be a positive integer");
                return true;
            }
        }
        if (hospitalityPublish.isSelected()) {
            if (hospitalityRankPublish.getText().isEmpty()) {
                Massage.errorMassage("Please fill all the fields as needed");
                return true;
            } else if (isNumber(hospitalityRankPublish.getText()) == false) {
                Massage.errorMassage("Hospitality rank must be a positive integer");
                return true;
            }
        }
        if (fromDatePublish.getValue().isAfter(toDatePublish.getValue())) {
            Massage.errorMassage("From time must be before To time");
            return true;
        }
        if(fromDatePublish.getValue().isBefore(LocalDate.now())){
            Massage.errorMassage("From time must be in the future");
            return true;
        }
        if (isNumber(pricePublish.getText()) == false) {
            Massage.errorMassage("Price must be a positive integer");
            return true;
        }
        if (isNumber(ticketsNumPublish.getText()) == false) {
            Massage.errorMassage("Number of tickets must be a positive integer");
            return true;
        }
        if (flights == null || flights.size() == 0) {
            Massage.errorMassage("Number of listed flights must be at least 1");
            return true;
        }
        return false;
    }

    private boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void flightListPublish(Event event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/FlightsList.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            FlightsListController viewController = fxmlLoader.getController();
            viewController.addObserver(this);
            viewController.setFlights(flights);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Flight list");
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateHome(String username) {
        User user = model.getUser(username);
        usernameHome.setText(username);
        firstHome.setText(user.getFirst_Name());
        lastHome.setText(user.getLast_Name());
        birthHome.setText(localDate2Str(user.getBirth_Date()));
        cityHome.setText(user.getCity());
        countryHome.setText(user.getCountry());
    }

    private boolean dateCheck(LocalDate date) {
        LocalDate today = LocalDate.now().plusDays(1);
        LocalDate before18 = today.minusYears(18);
        return (date.isBefore(before18));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof FlightsListController) {
            flights = ((ArrayList<Flight>) arg);
            flightListBut.setText("Flights list (" + flights.size() + ")");
        }
        if (o instanceof PaymentWindowController) {
            refreshRequests();
        }
    }

    public void cleanFilters(ActionEvent actionEvent) {
        textField_hospitality.setDisable(true);
        textField_baggage.setDisable(true);
        clearRec(gridPane_searchFilters);
    }

    private void clearRec(Pane pane){
        for(Node node:pane.getChildren())
            if(node instanceof Pane)
                clearRec((Pane)node);
            else if(node instanceof TextField){
                ((TextField) node).clear();;
            }
            else if(node instanceof CheckBox){
                ((CheckBox) node).setSelected(false);;
            }
            else if(node instanceof ComboBox){
                ((ComboBox) node).getItems().clear();
            }
            else if(node instanceof DatePicker){
                ((DatePicker) node).setValue(null);
            }
    }



    private TableView<Flight> getRequestsTableView(TableView tableView, Callback<TableColumn<PurchaseARequest, String>, TableCell<PurchaseARequest, String>>... cellFactorys) {
        tableView = (TableView<PurchaseARequest>) tableView;
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn<PurchaseARequest, String> requester_username = new TableColumn<>("requester username");
        TableColumn<PurchaseARequest, String> seller_username = new TableColumn<>("Seller username");
        TableColumn<PurchaseARequest, String> sourceCountry = new TableColumn<>("Source country");
        TableColumn<PurchaseARequest, String> destinationCountry = new TableColumn<>("Destination country");
        TableColumn<PurchaseARequest, String> fromDate = new TableColumn<>("From date");
        TableColumn<PurchaseARequest, String> toDate = new TableColumn<>("To date");
        TableColumn<PurchaseARequest, String> ticketsType = new TableColumn<>("Tickets type");
        TableColumn<PurchaseARequest, String> flight_Type = new TableColumn<>("Flight type");
        TableColumn<PurchaseARequest, Number> max_Price_Per_Ticket = new TableColumn<>("Max price per ticket");
        TableColumn<PurchaseARequest, Number> tickets_Quantity = new TableColumn<>("Tickets quantity");
        TableColumn<PurchaseARequest, String> canBuyLess = new TableColumn<>("Can buy less");
        TableColumn<PurchaseARequest, String> baggage_Included = new TableColumn<>("baggage included");
        TableColumn<PurchaseARequest, Number> baggageLimit = new TableColumn<>("Baggage limit");
        TableColumn<PurchaseARequest, String> hospitality_Included = new TableColumn<>("Hospitality included");
        TableColumn<PurchaseARequest, Number> hospitality_Rank = new TableColumn<>("Hospitality rank");
        TableColumn<PurchaseARequest, String> vacation_type = new TableColumn<>("Vacation type");


        requester_username.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getUsername()));
        seller_username.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getSeller_username()));
        sourceCountry.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getSourceCountry()));
        destinationCountry.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getDestinationCountry()));
        fromDate.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getFromDate().toString()));
        toDate.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getToDate().toString()));
        ticketsType.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getTicketsType().toString()));
        flight_Type.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getFlight_Type().toString()));
        max_Price_Per_Ticket.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getPrice_Per_Ticket()));
        tickets_Quantity.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getTickets_Quantity()));
        canBuyLess.setCellValueFactory(param -> new SimpleObjectProperty<>(String.valueOf(param.getValue().getVacationSell().getVacation().isCanBuyLess())));
        baggage_Included.setCellValueFactory(param -> new SimpleObjectProperty<>(String.valueOf(param.getValue().getVacationSell().getVacation().isBaggage_Included())));
        baggageLimit.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getBaggageLimit()));
        hospitality_Included.setCellValueFactory(param -> new SimpleObjectProperty<>(String.valueOf(param.getValue().getVacationSell().getVacation().isHospitality_Included())));
        hospitality_Rank.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacationSell().getVacation().getHospitality_Rank()));
        vacation_type.setCellValueFactory(param -> new SimpleObjectProperty<>(String.valueOf(param.getValue().getVacationSell().getVacation().getVacation_type())));



        tableView.getColumns().addAll(requester_username, seller_username, sourceCountry, destinationCountry,fromDate,toDate, ticketsType, flight_Type, max_Price_Per_Ticket, tickets_Quantity, canBuyLess, baggage_Included, baggageLimit, hospitality_Included, hospitality_Rank, vacation_type);

        for (Callback<TableColumn<PurchaseARequest, String>, TableCell<PurchaseARequest, String>> cellCallback : cellFactorys) {
            TableColumn<PurchaseARequest, String> column = new TableColumn<>();//Button
            column.setCellFactory(new Callback<TableColumn<PurchaseARequest, String>, TableCell<PurchaseARequest, String>>() {
                @Override
                public TableCell<PurchaseARequest, String> call(TableColumn<PurchaseARequest, String> param) {
                    return null;
                }
            });
            column.setCellFactory(cellCallback);
            tableView.getColumns().add(column);
        }

        return tableView;
    }


    private TableView<Flight> getFlightsTableView() {
        TableView<Flight> flightTableView = new TableView<>();
        flightTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn<Flight, String> flightCompany = new TableColumn<>("Flight company");
        TableColumn<Flight, String> sourceAirPort = new TableColumn<>("Source airport");
        TableColumn<Flight, String> destinationAirPort = new TableColumn<>("Destination airport");
        TableColumn<Flight, String> departDate = new TableColumn<>("Depart date");
        TableColumn<Flight, String> landDate = new TableColumn<>("Land date");
        TableColumn<Flight, String> departHour = new TableColumn<>("Depart hour");
        TableColumn<Flight, String> landHour = new TableColumn<>("Land hour");
        flightCompany.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFlightCompany()));
        sourceAirPort.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSourceAirPort()));
        destinationAirPort.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDestinationAirPort()));
        departDate.setCellValueFactory(param -> new SimpleStringProperty((param).getValue().getDepartDate().toString()));
        landDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLandDate().toString()));
        departHour.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDepartHour()));
        landHour.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLandHour()));
        flightTableView.getColumns().addAll(flightCompany, sourceAirPort, destinationAirPort, departDate, landDate, departHour, landHour);
        return flightTableView;
    }

    public void searchByFilters(ActionEvent actionEvent) {

        TableView<VacationSell> vacations = new TableView<>();
        vacations.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn<VacationSell, String> seller_username = new TableColumn<>("Seller username");
        TableColumn<VacationSell, String> sourceCountry = new TableColumn<>("Source country");
        TableColumn<VacationSell, String> destinationCountry = new TableColumn<>("Destination country");
        TableColumn<VacationSell, String> fromDate = new TableColumn<>("From date");
        TableColumn<VacationSell, String> toDate = new TableColumn<>("To date");
        TableColumn<VacationSell, String> ticketsType = new TableColumn<>("Tickets type");
        TableColumn<VacationSell, String> flight_Type = new TableColumn<>("Flight type");
        TableColumn<VacationSell, Number> max_Price_Per_Ticket = new TableColumn<>("Max price per ticket");
        TableColumn<VacationSell, Number> tickets_Quantity = new TableColumn<>("Tickets quantity");
        TableColumn<VacationSell, String> canBuyLess = new TableColumn<>("Can buy less");
        TableColumn<VacationSell, String> baggage_Included = new TableColumn<>("baggage included");
        TableColumn<VacationSell, Number> baggageLimit = new TableColumn<>("Baggage limit");
        TableColumn<VacationSell, String> hospitality_Included = new TableColumn<>("Hospitality included");
        TableColumn<VacationSell, Number> hospitality_Rank = new TableColumn<>("Hospitality Rank");
        TableColumn<VacationSell, String> vacation_type = new TableColumn<>("Vacation type");

        fromDate.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getFromDate().toString()));
        toDate.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getToDate().toString()));
        seller_username.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getSeller_username()));
        sourceCountry.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getSourceCountry()));
        destinationCountry.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getDestinationCountry()));
        ticketsType.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getTicketsType().toString()));
        flight_Type.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getFlight_Type().toString()));
        max_Price_Per_Ticket.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getPrice_Per_Ticket()));
        tickets_Quantity.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getTickets_Quantity()));
        canBuyLess.setCellValueFactory(param -> new SimpleObjectProperty<>(String.valueOf(param.getValue().getVacation().isCanBuyLess())));
        baggage_Included.setCellValueFactory(param -> new SimpleObjectProperty<>(String.valueOf(param.getValue().getVacation().isBaggage_Included())));
        baggageLimit.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getBaggageLimit()));
        hospitality_Included.setCellValueFactory(param -> new SimpleObjectProperty<>(String.valueOf(param.getValue().getVacation().isHospitality_Included())));
        hospitality_Rank.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getVacation().getHospitality_Rank()));
        vacation_type.setCellValueFactory(param -> new SimpleObjectProperty<>(String.valueOf(param.getValue().getVacation().getVacation_type())));

        TableColumn<VacationSell, String> seeMore_buttons = new TableColumn<>();//Button
        TableColumn<VacationSell, String> requset_buttons = new TableColumn<>();//Button

        Callback<TableColumn<VacationSell, String>, TableCell<VacationSell, String>> cellFactory
                = //
                new Callback<TableColumn<VacationSell, String>, TableCell<VacationSell, String>>() {
                    @Override
                    public TableCell<VacationSell, String> call(final TableColumn<VacationSell, String> param) {
                        final TableCell<VacationSell, String> cell = new TableCell<VacationSell, String>() {

                            final Button btn = new Button("see more");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    btn.setOnAction(event -> {
                                        VacationSell vacationSell = getTableView().getItems().get(getIndex());
                                        Stage stage = new Stage();
//                                      stage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
                                        stage.setAlwaysOnTop(false);
                                        stage.setResizable(false);
                                        stage.setTitle("vacation " + vacationSell.getId() + " flights");
                                        stage.initModality(Modality.APPLICATION_MODAL);
                                        ScrollPane scrollPane = new ScrollPane();
                                        TableView<Flight> flightsTableView = getFlightsTableView();
                                        flightsTableView.getItems().addAll(vacationSell.getVacation().getFlights());
                                        flightsTableView.setPrefWidth(2500);
                                        scrollPane.setContent(flightsTableView);
                                        flightsTableView.setPrefHeight(600);
                                        scrollPane.setPrefHeight(600);
                                        scrollPane.setPrefWidth(800);
                                        Scene scene = new Scene(scrollPane);
                                        stage.setScene(scene);
                                        stage.show();
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        seeMore_buttons.setCellFactory(cellFactory);

        Callback<TableColumn<VacationSell, String>, TableCell<VacationSell, String>> cellFactory2
                = //
                new Callback<TableColumn<VacationSell, String>, TableCell<VacationSell, String>>() {
                    @Override
                    public TableCell<VacationSell, String> call(final TableColumn<VacationSell, String> param) {
                        final TableCell<VacationSell, String> cell = new TableCell<VacationSell, String>() {

                            final Button btn = new Button("request");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    btn.setOnAction(event -> {
                                        if (loggedIn) {
                                            VacationSell vacationSell = getTableView().getItems().get(getIndex());
                                            if (vacationSell.getVacation().getSeller_username().equals(username))
                                                Massage.errorMassage("You can not request vacation from yourself");
                                            else {
                                                if (model.sendRequest(new PurchaseRequestData(username, vacationSell.getId())))
                                                    Massage.infoMassage("sent request");
                                                else
                                                    Massage.errorMassage("set failed");
                                            }
                                        } else
                                            Massage.errorMassage("You should login to request and buy vacation");
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        requset_buttons.setCellFactory(cellFactory2);

        vacations.getColumns().addAll(seller_username, sourceCountry, destinationCountry, ticketsType, fromDate, toDate, flight_Type, max_Price_Per_Ticket, tickets_Quantity, canBuyLess, baggage_Included, baggageLimit, hospitality_Included, hospitality_Rank, vacation_type, seeMore_buttons, requset_buttons);

        List<VacationSell> vacationSells = model.getVacations(username,
                textField_flightCompany.getText().equals("") ? null : textField_flightCompany.getText(),
                datePicker_fromDate.getValue(),
                datePicker_toDate.getValue(),
                checkBox_baggage.isSelected(),
                checkBox_baggage.isSelected() ? Integer.parseInt(textField_baggage.getText()) : null,
                textField_ticketsQuantity.getText().equals("") ? null : Integer.parseInt(textField_ticketsQuantity.getText()),
                comboBox_ticketsType.getSelectionModel().getSelectedItem() == null ? null : Vacation.Tickets_Type.valueOf(comboBox_ticketsType.getSelectionModel().getSelectedItem().toString()),
                textField_maxPricePerTicket.getText().equals("") ? null : Integer.parseInt(textField_maxPricePerTicket.getText()),
                textField_sourceCountry.getText().equals("") ? null : textField_sourceCountry.getText(),
                textField_destinationCountry.getText().equals("") ? null : textField_destinationCountry.getText(),
                comboBox_vacationType.getSelectionModel().getSelectedItem() == null ? null : Vacation.Vacation_Type.valueOf(comboBox_vacationType.getSelectionModel().getSelectedItem().toString()),
                comboBox_flightType.getSelectionModel().getSelectedItem() == null ? null : Vacation.Flight_Type.valueOf(comboBox_flightType.getSelectionModel().getSelectedItem().toString()),
                checkBox_hospitality.isSelected(),
                checkBox_hospitality.isSelected() ? Integer.parseInt(textField_hospitality.getText()) : null);


        //add comboBox_flightType.getSelectionModel().getSelectedItem() == null ? null : Vacation.Flight_Type.valueOf(comboBox_flightType.getSelectionModel().getSelectedItem().toString()),
        vacations.getItems().addAll(vacationSells);
        vacations.setPrefWidth(2500);
        vacations.setPrefHeight(600);
        Stage stage = new Stage();
        stage.setAlwaysOnTop(false);
        stage.setResizable(false);
        stage.setTitle("Vacations");
        stage.initModality(Modality.APPLICATION_MODAL);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vacations);
        scrollPane.setPrefWidth(800);
        scrollPane.setPrefHeight(600);
        Scene scene = new Scene(scrollPane);
        stage.setScene(scene);
        stage.show();
    }
}
