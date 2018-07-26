package Interfaces;

import Objects.Messages;
import javafx.collections.ObservableList;

public interface ListMess {
    public void send (String name, String Mess, String time);

    public void fillData();

    ObservableList<Messages> getMessList();

    public void uploadmess(String id);

    public String getLastId();


}
