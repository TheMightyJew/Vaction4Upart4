package Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public  class Massage {

    public static void errorMassage(String massage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(massage);
        Optional<ButtonType> result = alert.showAndWait();
    }

    public static boolean confirmMassage(String massage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(massage);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void infoMassage(String massage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(massage);
        Optional<ButtonType> result = alert.showAndWait();
    }

}
