package dev.me.DZMC.UI.main;

import dev.me.DZMC.UI.SceneController;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainView extends HBox {
    private String _cssPath = "fx-css/main.css";

    // Components
    private GamesetContainer gamesetContainer = new GamesetContainer();


    public MainView() {
        this.getStylesheets().add(SceneController.getCSSSheet_URL(_cssPath).toExternalForm());
        this.getStyleClass().add("main-container-box");
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setPrefWidth(Double.MAX_VALUE);
    }

}
