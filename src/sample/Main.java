package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public class Main extends Application {
    Connection conn = null;
    Stage window = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/session1?useSSL=False", "root", "omar");
        login0();
//        BorderPane root = new BorderPane();
//        TextField inputField = new TextField();
//        Label outputLabel = new Label();
//        Button hashButton = new Button("Hash");
//        VBox midBox = new VBox(inputField,outputLabel,hashButton);
//        midBox.setAlignment(Pos.CENTER);
//        midBox.setSpacing(20);
//        midBox.setPadding(new Insets(30));
//        root.setCenter(midBox);
//
//        hashButton.setOnAction(val -> {
//            try {
//                outputLabel.setText(md5Hasher(inputField.getText()));
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//        });
//
//
//        primaryStage.setScene(new Scene(root, 400, 300));
//        primaryStage.show();
    }

    public void login0() throws FileNotFoundException {
        window.setTitle("Login");
        BorderPane root = new BorderPane();
        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");
        TextField userField = new TextField();
        TextField passField = new TextField();
        VBox labels = new VBox(userLabel,passLabel);
        VBox fields = new VBox(userField,passField);
        HBox bothSides = new HBox(labels,fields);
        ImageView logo = new ImageView(new Image(new FileInputStream("src\\Images\\WSC2017_TP09_color@4x.png")));

        VBox mainBox = new VBox(logo,bothSides);

        root.setCenter(mainBox);
        window.setScene(new Scene(root, 800,600));
        window.show();
    }

    public ResultSet sqlExe(String query) throws SQLException {
        Statement stmnt = conn.createStatement();
        System.out.println("Executing Query...");
        return stmnt.executeQuery(query);
    }

    public void sqlIns(String query) throws SQLException {
        Statement stmnt = conn.createStatement();
        System.out.println("Executing...");
        stmnt.execute(query);
    }

    public void insertUserData() throws NoSuchAlgorithmException, SQLException {
        String data = "Administrator,j.doe@amonic.com,123,John,Doe,Abu dhabi,1/13/1983,1\n" +
                "User,k.omar@amonic.com,4258,Karim,Omar,Abu dhabi,3/19/1980,1\n" +
                "User,h.saeed@amonic.com,2020,Hannan,Saeed,Cairo,12/20/1989,1\n" +
                "User,a.hobart@amonic.com,6996,Andrew,Hobart,Riyadh,1/30/1990,1\n" +
                "User,k.anderson@amonic.com,4570,Katrin,Anderson,Doha,11/10/1992,1\n" +
                "User,h.wyrick@amonic.com,1199,Hava,Wyrick,Abu dhabi,8/8/1988,1\n" +
                "User,marie.horn@amonic.com,55555,Marie,Horn,Bahrain,4/6/1981,1\n" +
                "User,m.osteen@amonic.com,9800,Milagros,Osteen,Abu dhabi,2/3/1991,0\n";
        String[][] parsedData = new String[data.split("\n").length][];
        int x = 0;
        for (String s : data.split("\n")) {
            parsedData[x] = s.split(",");
            x++;
        }
        for (int i = 0; i < parsedData.length; i++) {
            int role = parsedData[i][0].equals("Administrator") ? 1 : 2;
            String email = parsedData[i][1];
            String pass = md5Hasher(parsedData[i][2]);
            String fname = parsedData[i][3];
            String lname = parsedData[i][4];
            String title = parsedData[i][5];
            String dob = parsedData[i][6].split("/")[2] + "/" + parsedData[i][6].split("/")[0] + "/" + parsedData[i][6].split("/")[1];
            System.out.println(dob);
            String active = parsedData[i][7];

            sqlIns("INSERT INTO users (ID, roleID, Email, password, firstname, lastname, officeID, birthdate, active) VALUES ("+i+", "+role+", '"+email+"', '"+pass+"', '"+fname+"', '"+lname+"', (SELECT id FROM offices WHERE title='"+title+"'), '"+dob+"', "+active+");");
        }
    }

    public String md5Hasher(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digest = md.digest(pass.getBytes());
        return DatatypeConverter.printHexBinary(digest);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
