package menu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import playerinfo.AccountData;
import playerinfo.Data;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AccountMenu extends Application implements Blurrable {

    private Image userImg, passwordImg;
    private ImageView userImgView, passwordImgView;
    private Text mainText;
    private TextField userField, passwordField;
    private Button goButton, exitButton;
    private Path tick, cross;
    private ToggleGroup toggleGroup;
    private ToggleButton signInButton, signUpButton;
    private Timeline checkValidity;
    private Group accountGroup;
    private Scene scene;
    private Stage accountMenuStage;


    @Override
    public void start(Stage primaryStage) throws Exception{

        accountMenuStage = primaryStage;

        userImg = new Image(new FileInputStream("images/username.png"),52,52,true,true);
        userImgView = new ImageView(userImg);
        userImgView.preserveRatioProperty();
        userImgView.setLayoutX(285);
        userImgView.setLayoutY(330);
        passwordImg = new Image(new FileInputStream("images/Password.png"),52,52,true,true);
        passwordImgView = new ImageView(passwordImg);
        passwordImgView.preserveRatioProperty();
        passwordImgView.setLayoutX(285);
        passwordImgView.setLayoutY(410);


        mainText = new Text("SIGN IN / UP");
        mainText.setFont(Font.font("Sans Serif",45));
        mainText.setX(270);
        mainText.setY(125);
        mainText.setFill(Color.WHITE);

        tick = new Path();
        tick.setStrokeWidth(5);
        tick.setStroke(Color.rgb(45,247,5));
        tick.setLayoutX(580);
        tick.setLayoutY(353);
        tick.getElements().addAll(new MoveTo(0, 3), new LineTo(5, 10),
                new LineTo(20, -4));

        cross = new Path();
        cross.setStrokeWidth(5);
        cross.setStroke(Color.RED);
        cross.setLayoutX(580);
        cross.setLayoutY(353);
        cross.getElements().addAll(new MoveTo(2, -4), new LineTo(14, 10),
                new MoveTo(14, -4), new LineTo(2, 10));

        userField = new TextField();
        userField.setFont(Font.font("Sans Serif",13));
        userField.setLayoutX(370);
        userField.setLayoutY(342);
        userField.setPrefWidth(200);
        userField.setPrefHeight(30);
        userField.setFocusTraversable(false);
        userField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setFont(Font.font("Sans Serif",13));
        passwordField.setLayoutX(370);
        passwordField.setLayoutY(422);
        passwordField.setPrefWidth(200);
        passwordField.setPrefHeight(30);
        passwordField.setFocusTraversable(false);
        passwordField.setPromptText("Password");

        goButton = new Button("GO!");
        goButton.setLayoutX(300);
        goButton.setLayoutY(530);
        goButton.setFocusTraversable(false);
        goButton.setFont(Font.font("Sans Serif",20));
        goButton.setPrefWidth(190);
        goButton.setPrefHeight(50);
        goButton.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
        goButton.setOnMouseEntered(e -> goButton.setStyle("-fx-background-color: green;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
        goButton.setOnMouseExited(e -> goButton.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;

        exitButton = new Button("EXIT");
        exitButton.setLayoutX(300);
        exitButton.setLayoutY(600);
        exitButton.setFocusTraversable(false);
        exitButton.setFont(Font.font("Sans Serif",20));
        exitButton.setPrefWidth(190);
        exitButton.setPrefHeight(50);
        exitButton.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
        exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-background-color: red;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
        exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;

        toggleGroup  = new ToggleGroup();
        signInButton = new ToggleButton("Sign In");
        signUpButton = new ToggleButton("Sign Up");
        signInButton.setToggleGroup(toggleGroup);
        signUpButton.setToggleGroup(toggleGroup);
        signInButton.setLayoutX(210);
        signInButton.setLayoutY(230);
        signUpButton.setLayoutX(420);
        signUpButton.setLayoutY(230);
        signInButton.setFocusTraversable(false);
        signUpButton.setFocusTraversable(false);
        signInButton.setFont(Font.font("Sans Serif",15));
        signUpButton.setFont(Font.font("Sans Serif",15));
        signInButton.setPrefWidth(170);
        signInButton.setPrefHeight(40);
        signUpButton.setPrefWidth(170);
        signUpButton.setPrefHeight(40);
        signInButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");
        checkValidity = new Timeline(new KeyFrame(Duration.millis(100), ActionEvent -> {
            if(!signInButton.isSelected() && !signUpButton.isSelected()){
                userField.setDisable(true);
                passwordField.setDisable(true);
                goButton.setVisible(false);
            }
            else{
                userField.setDisable(false);
                passwordField.setDisable(false);
                goButton.setVisible(true);
            }
            if(signInButton.isSelected()) {
                tick.setVisible(false);
                cross.setVisible(false);
            }
            if(signUpButton.isSelected()){
                String path = "ColorSwitchData/" + userField.getText();
                if(!userField.getText().matches("[A-Za-z0-9]+")){
                    tick.setVisible(false);
                    cross.setVisible(true);
                    return;
                }
                File newDirectory = new File(path);
                if((newDirectory.exists() && newDirectory.isDirectory()) || path.length() == 0){
                    tick.setVisible(false);
                    cross.setVisible(true);
                }
                else{
                    tick.setVisible(true);
                    cross.setVisible(false);
                }
            }
            else{
                tick.setVisible(false);
                cross.setVisible(false);
            }
        }));
        checkValidity.setCycleCount(Timeline.INDEFINITE);
        checkValidity.play();
        signInButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {

                if(signInButton.isSelected()){
                    signInButton.setStyle("-fx-background-color: #FF4500;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");

                    signUpButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");

                }
                else{
                    signInButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");
                }

            }
        });
        signUpButton.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");
        signUpButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(signUpButton.isSelected()) {
                    signUpButton.setStyle("-fx-background-color: #FF4500;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");;
                    signInButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");;
                }
                else{
                    signUpButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");
                }
            }
        });
        goButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
//                primaryStage.hide();

                try {
//                    MainMenu mainMenu = new MainMenu();
//                    mainMenu.startMainMenu(new Stage());
                    System.out.println("Password hash: " + getSHA256(passwordField.getText()));
                    if(signInButton.isSelected()){
                        checkSignIn();
                    }
                    else{
                        checkSignUp();
                    }
                }
                catch (Exception e){
                    System.out.println("Unexpected error");
                }

            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        accountGroup = new Group();
        accountGroup.getChildren().add(signInButton);
        accountGroup.getChildren().add(signUpButton);
        accountGroup.getChildren().add(userField);
        accountGroup.getChildren().add(passwordField);
        accountGroup.getChildren().add(goButton);
        accountGroup.getChildren().add(exitButton);
        accountGroup.getChildren().add(mainText);
        accountGroup.getChildren().add(userImgView);
        accountGroup.getChildren().add(passwordImgView);
        accountGroup.getChildren().add(tick);
        accountGroup.getChildren().add(cross);
        scene = new Scene(accountGroup,800,800);
        scene.setFill(Color.rgb(41,41,41));
        primaryStage.setTitle("Account Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void checkSignIn() throws Exception {
        String username = userField.getText();
        String password = passwordField.getText();
        System.out.println(username);
        if(!username.matches("[A-Za-z0-9]+")){
            // alert(No such account (username must be alphanumeric))
            enableBlur(true);
            Alert a = new Alert(Alert.AlertType.WARNING,"No such account (Username not alphanumeric)");
            a.setOnCloseRequest(e-> enableBlur(false));
            a.show();

            System.out.println("No such account (Username not alphanumeric)");
            return;
        }
        File userDirectory = new File("ColorSwitchData/" + username);
        AccountData loadData = null;
        if(userDirectory.exists() && userDirectory.isDirectory()){
            String finalPath = "ColorSwitchData/" + username + "/accountData.ser";
            try {
                System.out.println(finalPath);
                FileInputStream inputStream = new FileInputStream(finalPath);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                System.out.println("working");
                loadData = (AccountData) in.readObject();
                in.close();
                inputStream.close();
            } catch (IOException i) {
                // alert("Some problem occured while signing in")
                enableBlur(true);
                Alert a = new Alert(Alert.AlertType.WARNING,"Some problem occured while signing in");
                a.setOnCloseRequest(e-> enableBlur(false));
                a.show();

                System.out.println("IO");
//                i.printStackTrace();
                return;
            } catch (ClassNotFoundException c) {
                // alert("Some problem occured while signing in")
                enableBlur(true);
                Alert a = new Alert(Alert.AlertType.WARNING,"Some problem occured while signing in");
                a.setOnCloseRequest(e-> enableBlur(false));
                a.show();

                System.out.println("ClassNotFound");
//                c.printStackTrace();
                return;
            }
            String newHash = getSHA256(password);
            String loadedHash = loadData.getPasswordHash();
            if(!newHash.equals(loadedHash)){
                // alert(Invalid username or password)
                enableBlur(true);
                Alert a = new Alert(Alert.AlertType.WARNING,"Invalid username or password");
                a.setOnCloseRequest(e-> enableBlur(false));
                a.show();
                System.out.println("Invalid username or password");
                return;
            }
            /*
            Load game here as both username
            and password are valid
             */
            System.out.println("ENJOY!");
            startMainMenu(username);
        }
        else{
            // alert(Invalid username or password)
            enableBlur(true);
            Alert a = new Alert(Alert.AlertType.WARNING,"Invalid username or password");
            a.setOnCloseRequest(e-> enableBlur(false));
            a.show();
            System.out.println("Invalid username or password");
        }
        return;
    }
    public void checkSignUp() throws NoSuchAlgorithmException{
        String username = userField.getText();
        String password = passwordField.getText();
        if(!username.matches("[A-Za-z0-9]+")){
            // alert(username must be alphanumeric)
            enableBlur(true);
            Alert a = new Alert(Alert.AlertType.WARNING,"Username must be alphanumeric");
            a.setOnCloseRequest(e-> enableBlur(false));
            a.show();
            System.out.println("Username not alphanumeric");
            return;
        }
        if(password.length() == 0){
            // alert(Enter password)
            enableBlur(true);
            Alert a = new Alert(Alert.AlertType.WARNING,"No password entered");
            a.setOnCloseRequest(e-> enableBlur(false));
            a.show();
            System.out.println("No password entered");
            return;
        }
        File userDirectory = new File("ColorSwitchData/" + username);
        if(userDirectory.exists() && userDirectory.isDirectory()){
            // alert(username is already taken)
            enableBlur(true);
            Alert a = new Alert(Alert.AlertType.WARNING,"Username is already taken");
            a.setOnCloseRequest(e-> enableBlur(false));
            a.show();
            System.out.println("Username is already taken");
            return;
        }
        boolean directoryMade = userDirectory.mkdir();
        System.out.println("Directory made: " + directoryMade);
        boolean createdAccount = createAccount(username, getSHA256(password), "ColorSwitchData/" + username + "/accountData.ser");
        if(!createdAccount){
            // alert("Account could not be created")
            enableBlur(true);
            Alert a = new Alert(Alert.AlertType.WARNING,"Account could not be created");
            a.setOnCloseRequest(e-> enableBlur(false));
            a.show();
            System.out.println("Account could not be created");
            return;
        }
        // alert(Account created successfully)
        enableBlur(true);
        Alert a = new Alert(Alert.AlertType.WARNING,"Account created successfully");
        a.setOnCloseRequest(e-> enableBlur(false));
        a.show();

        return;
    }
    public String getSHA256(String password) throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return convertByteToString(messageDigest.digest(password.getBytes(StandardCharsets.UTF_8)));
    }
    public String convertByteToString(byte hash[]){
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while(hexString.length() < 32){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
    public boolean createAccount(String username, String passwordHash, String finalPath){
        AccountData saveData = new AccountData(username, passwordHash, 0, 0);
        try{
            FileOutputStream outputStream = new FileOutputStream(finalPath);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(saveData);
            out.close();
            outputStream.close();
            System.out.println(finalPath);
            System.out.println("Account data serialized");
        }
        catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }
    public void startMainMenu(String username) throws Exception {
        accountMenuStage.hide();
        MainMenu mainMenu = new MainMenu();
        mainMenu.startMainMenu(new Stage(), username);
    }
    @Override
    public void enableBlur(Boolean blurringEffect){
        ColorAdjust adj;
        GaussianBlur blur;
        if(blurringEffect) {
            adj = new ColorAdjust(0, -0.05, -0.2, 0);
            blur = new GaussianBlur(20);
            adj.setInput(blur);
            accountGroup.setEffect(adj);

        }
        else{
            blur = new GaussianBlur(0);
            adj = new ColorAdjust();
            adj.setInput(blur);
            accountGroup.setEffect(null);
        }

    }
}
