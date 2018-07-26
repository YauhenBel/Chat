package controller;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public CustomTextField nickName;
    public Button input;
    private static String FXMLSection = "../layouts/chat.fxml";
    private Stage primaryStage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupClearButtonField(nickName);
    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnClick(ActionEvent actionEvent) throws IOException, SQLException {
        System.out.println(nickName.getText());
        createGui(nickName.getText());
        close_window(actionEvent);
    }
    private void close_window(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    private void createGui(String name) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXMLSection));
        Parent root = fxmlLoader.load();
        ControllerChat controllerChat = fxmlLoader.getController();
        controllerChat.setNick(name);
        primaryStage = new Stage();
        controllerChat.setMainStage(primaryStage);
        primaryStage.setTitle("Чат");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(primaryStage.getHeight());
        primaryStage.setMaxHeight(primaryStage.getWidth());
    }


}
