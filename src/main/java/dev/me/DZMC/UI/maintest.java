package dev.me.DZMC.UI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class maintest extends Application {

    private TextField nameField;
    private TextField emailField;
    private ComboBox<String> courseBox;
    private DatePicker datePicker;
    private CheckBox termsCheckBox;
    private ListView<String> studentList;
    private Label messageLabel;

    private final ObservableList<String> students =
            FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {

        // Heading
        Label title = new Label("Student Registration");
        title.getStyleClass().add("title");

        // Text fields
        nameField = new TextField();
        nameField.setPromptText("Enter your name");

        emailField = new TextField();
        emailField.setPromptText("Enter your email");

        // Dropdown
        courseBox = new ComboBox<>();
        courseBox.getItems().addAll(
                "Computer Science",
                "Information Technology",
                "Software Engineering"
        );
        courseBox.setPromptText("Select a course");
        courseBox.setMaxWidth(Double.MAX_VALUE);

        // Date picker
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setMaxWidth(Double.MAX_VALUE);

        // Checkbox
        termsCheckBox = new CheckBox("I agree to the terms");

        // Buttons
        Button registerButton = new Button("Register");
        Button clearButton = new Button("Clear");

        registerButton.getStyleClass().add("primary-button");
        clearButton.getStyleClass().add("secondary-button");

        // Message label
        messageLabel = new Label("Fill in the form to register.");
        messageLabel.getStyleClass().add("message");

        // List view
        studentList = new ListView<>(students);
        studentList.setPlaceholder(
                new Label("No students registered yet.")
        );
        studentList.setPrefHeight(150);

        // Form layout
        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(12);

        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);

        form.add(new Label("Email:"), 0, 1);
        form.add(emailField, 1, 1);

        form.add(new Label("Course:"), 0, 2);
        form.add(courseBox, 1, 2);

        form.add(new Label("Start date:"), 0, 3);
        form.add(datePicker, 1, 3);

        ColumnConstraints labelColumn = new ColumnConstraints();
        ColumnConstraints inputColumn = new ColumnConstraints();
        inputColumn.setHgrow(Priority.ALWAYS);
        inputColumn.setFillWidth(true);

        form.getColumnConstraints().addAll(
                labelColumn, inputColumn
        );

        // Checkbox and buttons
        HBox buttonBox = new HBox(10, registerButton, clearButton);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        VBox content = new VBox(
                15,
                title,
                new Separator(),
                form,
                termsCheckBox,
                buttonBox,
                messageLabel,
                new Label("Registered Students"),
                studentList
        );

        content.setPadding(new Insets(25));
        content.getStyleClass().add("root-container");

        // Register button event
        registerButton.setOnAction(event -> registerStudent());

        // Clear button event
        clearButton.setOnAction(event -> clearForm());

        // Scene and CSS
        Scene scene = new Scene(content, 550, 650);
        scene.getStylesheets().add(
                getClass().getResource("/something.css").toExternalForm()
        );

        // Window
        stage.setTitle("JavaFX Basics");
        stage.setScene(scene);
        stage.setMinWidth(450);
        stage.show();
    }

    private void registerStudent() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String course = courseBox.getValue();
        LocalDate date = datePicker.getValue();

        // Basic validation
        if (name.isEmpty() || email.isEmpty()
                || course == null || date == null) {
            messageLabel.setText(
                    "Please complete all fields."
            );
            return;
        }

        if (!termsCheckBox.isSelected()) {
            messageLabel.setText(
                    "Please agree to the terms first."
            );
            return;
        }

        if (!email.contains("@")) {
            messageLabel.setText(
                    "Please enter a valid email address."
            );
            return;
        }

        // Add student to the list
        String student = name
                + " | " + email
                + " | " + course
                + " | " + date;

        students.add(student);

        messageLabel.setText(
                "Successfully registered " + name + "!"
        );

        clearForm();
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        courseBox.getSelectionModel().clearSelection();
        datePicker.setValue(LocalDate.now());
        termsCheckBox.setSelected(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
