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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane root = new BorderPane();
        TextField inputField = new TextField();
        Label outputLabel = new Label();
        Button hashButton = new Button("Hash");
        VBox midBox = new VBox(inputField,outputLabel,hashButton);
        midBox.setAlignment(Pos.CENTER);
        midBox.setSpacing(20);
        midBox.setPadding(new Insets(30));
        root.setCenter(midBox);

        hashButton.setOnAction(val -> {
            try {
                MessageDigest md = MessageDigest.getInstance("md5");
                byte[] digest = md.digest(inputField.getText().getBytes());
                outputLabel.setText(DatatypeConverter.printHexBinary(digest));

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        });


        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
