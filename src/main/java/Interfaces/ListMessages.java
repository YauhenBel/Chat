package Interfaces;

import Objects.Messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListMessages implements ListMess {
    private ObservableList<Messages> messages =
            FXCollections.observableArrayList();
    Messages message;
    @Override
    public void send(String name, String Mess, String time) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/chatDB?useUnicode=true&characterEncoding=utf-8";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                Integer typeWork = 0;
                String sql_query = "INSERT INTO `messages` (`id_mess`, `name`, `mess`, `time`) VALUES(NULL,"
                        + "'" + name + "', "
                        + "'" + Mess + "', "
                        + "'" + time + "')";
                System.out.println(sql_query);
                int rs = stmt.executeUpdate(sql_query);
                System.out.println(rs);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fillData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/chatDB";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT `id_mess`, `name`, `mess`, TIME_FORMAT(`time`, \"%H:%i\") AS 'time' FROM `messages`";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid_mess = " + rs.getString("id_mess")
                            + ",\nname = " + rs.getString("name")
                            + ",\nmess = " + rs.getString("mess")
                            + ",\ntime = " + rs.getString("time")+ ";\n}";
                    System.out.println("info: " + str);
                    String xId_ = rs.getString("id_mess");
                    String xName =rs.getString("name");
                    String xMess =rs.getString("mess");
                    String xTime =rs.getString("time");
                    messages.add(new Messages(xId_, xName, xMess, xTime));
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Messages> getMessList() {
        return messages;
    }

    @Override
    public void uploadmess(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/chatDB";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT `id_mess`, `name`, `mess`, TIME_FORMAT(`time`, \"%H:%i\") AS 'time' FROM `messages` WHERE `id_mess`>'" + id + "'";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid_mess = " + rs.getString("id_mess")
                            + ",\nname = " + rs.getString("name")
                            + ",\nmess = " + rs.getString("mess")
                            + ",\ntime = " + rs.getString("time")+ ";\n}";
                    System.out.println("info: " + str);
                    SimpleDateFormat formatForDateNow = new SimpleDateFormat("HH:mm");
                    Date time = formatForDateNow.parse(rs.getString("time"));
                    System.out.println("Формат времени: "   + time);
                    String xId_ = rs.getString("id_mess");
                    String xName =rs.getString("name");
                    String xMess =rs.getString("mess");
                    String xTime =rs.getString("time");
                    messages.add(new Messages(xId_, xName, xMess, xTime));
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLastId() {
        Integer x = messages.size();
        message = messages.get(x-1);
        return message.getId();
    }
}
