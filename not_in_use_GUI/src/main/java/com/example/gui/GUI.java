package com.example.gui;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class GUI extends Application {
    // Create UI elements

    private Label questionLabel;
    private Button button1, button2, button3, button4;
    private Button nextButton, quitButton;

    private Stage primaryStage;
    private Scene loginScene;
    TableView<Player> playerTable = new TableView<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set up UI elements
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Game Login");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Button loginButton = new Button("Login");

        HBox loginBox = new HBox(10, usernameLabel, usernameField, loginButton);
        loginBox.setAlignment(Pos.CENTER);

        BorderPane loginPane = new BorderPane();
        loginPane.setCenter(loginBox);
        loginPane.setPadding(new Insets(20));
        loginScene = new Scene(loginPane, 400, 200);


        // Show the login scene


        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            if (isValid(username)) {
                // Launch the game
                Player player = new Player(username, 0);
                ObservableList<Player> players = FXCollections.observableArrayList(player);
                playerTable.setItems(players);
                launchGame();
            } else {
                // Display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Playername");
                alert.showAndWait();
            }
        });
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void launchGame() {
        primaryStage.setTitle("Multiplayer Quiz");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        questionLabel = new Label("Question goes here");
        questionLabel.setStyle("-fx-font-size: 24px;");
        // Create the buttons
        button1 = new Button("Button 1");
        button1.setPrefSize(200, 100);
        button1.setStyle("-fx-font-size: 24px;");

        button2 = new Button("Button 2");
        button2.setPrefSize(200, 100);
        button2.setStyle("-fx-font-size: 24px;");

        button3 = new Button("Button 3");
        button3.setPrefSize(200, 100);
        button3.setStyle("-fx-font-size: 24px;");

        button4 = new Button("Button 4");
        button4.setPrefSize(200, 100);
        button4.setStyle("-fx-font-size: 24px;");

        nextButton = new Button("Ready");
        nextButton.setPrefSize(100, 50);

        quitButton = new Button("Quit");
        quitButton.setPrefSize(100, 50);

        Label playersLabel = new Label("Players");

        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TableColumn<Player, Integer> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());
        playerTable.getColumns().addAll(nameCol, scoreCol);

        VBox playerBox = new VBox(20, playersLabel, playerTable);
        playerBox.setAlignment(Pos.CENTER);
        // Add the buttons to the VBox
        GridPane answerGrid = new GridPane();
        answerGrid.setHgap(10);
        answerGrid.setVgap(10);
        answerGrid.add(button1, 0, 0);
        answerGrid.add(button2, 1, 0);
        answerGrid.add(button3, 0, 1);
        answerGrid.add(button4, 1, 1);
        answerGrid.setAlignment(Pos.CENTER);

        VBox questionBox = new VBox(20, questionLabel, answerGrid);
        questionBox.setAlignment(Pos.CENTER);

        HBox controlBox = new HBox(20, nextButton, quitButton);
        controlBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setCenter(questionBox);
        root.setRight(playerBox);
        root.setBottom(controlBox);
        root.setPadding(new Insets(20));

        // Set the scene and display the stage
        Scene scene = new Scene(root, 800, 500);

        // Set up stage
        primaryStage.setTitle("Multiplayer Quiz");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isValid(String username) {
        if (username.length() < 3 || username.length() > 8) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        launch(args);
    }

    public class Player {
        private String name;
        private int score;

        public Player(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int i) {
            this.score=i;
        }
    }

    private void updatePlayerScore(String playerName, int point) {
        // Find the player with the given name and update their score
        for (Player player : playerTable.getItems()) {
            if (player.getName().equals(playerName)) {
                player.setScore(player.getScore() + point);
                break;
            }
        }
        // Refresh the player table to show the updated scores
        playerTable.refresh();
    }

}