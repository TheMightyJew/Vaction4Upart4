package View;

import Controller.ViewController;
import Model.Model;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        //primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("vacationPic2.jpg")));
        Parent root = fxmlLoader.load(getClass().getResource("View.fxml").openStream());
        ViewController viewController = fxmlLoader.getController();
        Model model = new Model();
        viewController.setModel(model);
        primaryStage.setTitle("Vaction4U");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

//        TableView<String> test = new TableView<>();
//        test.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        test.setFixedCellSize(25);
//        test.setPrefHeight(25);
//        Stage stage = new Stage();
//        ScrollPane scrollPane = new ScrollPane(test);
//        Scene scene = new Scene(scrollPane);
//        stage.setScene(scene);
//        test.getItems().addListener((ListChangeListener<String>) c -> {
//            test.setPrefHeight((test.getFixedCellSize()+0.05) * (test.getItems().size()) + 25);
//        });
//
//
//        TableColumn<String, String> tableColumn = new TableColumn<>("names");
//        tableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()));
//        test.getColumns().add(tableColumn);
//        test.getItems().addAll("eran", "joe", "liad", "amit","nigaa","nigaa","nigaa");
//        stage.show();
//
////        test.heightProperty()
////        test.setPrefHeight(test.getFixedCellSize()*(test.getItems().size()+1));
////        test.setMaxHeight(test.getFixedCellSize()*(test.getItems().size()+1));
////        test.setMinHeight(test.getFixedCellSize()*(test.getItems().size()+1));
//
////        stage.maxHeightProperty().bindBidirectional(test.maxHeightProperty());
////        stage.minHeightProperty().bindBidirectional(test.minHeightProperty());
//
//        System.out.println(test.getItems().size());
//        System.out.println("total: " + test.getPrefHeight());
//        System.out.println("per one: " + test.getPrefHeight() / test.getItems().size());
    }


    public static void main(String[] args) {
        launch(args);
    }

}
