package controller;

import Interfaces.ListMessages;
import Objects.Messages;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerChat implements Initializable {
    public Label labelName;
    public TableView tableMess;
    public TextArea textSend;
    public Button btnSend;
    public TableColumn<Messages, String> columnName;
    public TableColumn<Messages, String> columnMess;
    public TableColumn<Messages, String> columnTime;
    private Stage mainStage;
    private String nick;
    ListMessages listMessages = new ListMessages();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnMess.setCellFactory(tc -> {
            TableCell<Messages, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(columnMess.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
    columnName.setCellValueFactory(new PropertyValueFactory<Messages, String>("name"));
    columnMess.setCellValueFactory(new PropertyValueFactory<Messages, String>("mess"));
    columnTime.setCellValueFactory(new PropertyValueFactory<Messages, String>("time"));

    listMessages.fillData();
    tableMess.setItems(listMessages.getMessList());
        Thread t = new Thread(() -> {
            try {
                while (true){
                    DownloadNewMess();
                    Thread.sleep(3000);
                    System.out.println("Время прошло. Новое обновление.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        t.start();
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public void setNick(String name) throws SQLException {
        this.nick = name;
        labelName.setText(nick);
    }

    public void initListeners(){
        tableMess.getItems().addListener(new ListChangeListener<Messages>() {
            @Override
            public void onChanged(Change<? extends Messages> c) {
                tableMess.scrollTo(c.getList().size()-1);
            }
        });
    }


    public void ActionSend(ActionEvent actionEvent) {
        String mess = textSend.getText();
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("HH:mm");
        listMessages.send(nick, mess, formatForDateNow.format(dateNow));
        listMessages.uploadmess(listMessages.getLastId());
        textSend.clear();

    }

    public void DownloadNewMess(){
        listMessages.uploadmess(listMessages.getLastId());
    }
}
