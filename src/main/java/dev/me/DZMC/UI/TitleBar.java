package dev.me.DZMC.UI;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TitleBar extends HBox {

    private Region _gap = new Region();

    private Label _titleLabel = new Label("DZMC");
    private Button _closeBTN = new Button("✕");
    private Button _minBTN = new Button("—");

    private double mouseOffsetX;
    private double mouseOffsetY;

    public TitleBar() {
        this.setMinHeight(SceneController.LauncherConfig.TitleBarHeight);
        this.getStyleClass().add("title-bar");

        _titleLabel.getStyleClass().add("title-window-label");

        HBox.setHgrow(_gap, Priority.ALWAYS);

        _closeBTN.setOnAction(e -> System.exit(0));
        _minBTN.setOnAction(e -> SceneController.getInstance().getStage().setIconified(true));


        setOnMousePressed(event -> {
            mouseOffsetX = event.getScreenX()
                    - SceneController.getInstance().getStage().getX();

            mouseOffsetY = event.getScreenY()
                    - SceneController.getInstance().getStage().getY();
        });

        setOnMouseDragged(event -> {
            SceneController.getInstance().getStage().setX(
                    event.getScreenX() - mouseOffsetX);

            SceneController.getInstance().getStage().setY(
                    event.getScreenY() - mouseOffsetY);
        });

        this.getChildren().addAll(_titleLabel,
                _gap,
                _minBTN,
                _closeBTN
        );
    }

    public void setText(String newText) {
        _titleLabel.setText(newText);
    }

}
