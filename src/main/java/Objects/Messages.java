package Objects;

import javafx.beans.property.SimpleStringProperty;

public class Messages {
    SimpleStringProperty id = new SimpleStringProperty();
    SimpleStringProperty name = new SimpleStringProperty();
    SimpleStringProperty mess = new SimpleStringProperty();
    SimpleStringProperty time = new SimpleStringProperty();

    public Messages(){}

    public Messages(String _id, String _name, String _mess, String _time){
        this.id = new SimpleStringProperty(_id);
        this.name = new SimpleStringProperty(_name);
        this.mess = new SimpleStringProperty(_mess);
        this.time = new SimpleStringProperty(_time);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getId() {
        return id.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public void setMess(String mess) {
        this.mess.set(mess);
    }

    public String getMess() {
        return mess.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }
}
