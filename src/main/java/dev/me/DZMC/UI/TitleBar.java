package dev.me.DZMC.UI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TitleBar extends HBox {

    private Region _gap = new Region();

    private Label _titleLabel = new Label("DZMC BUILD v0.3.0-alpha");
    private Button _closeBTN = new Button("✕");
    private Button _minBTN = new Button("—");

    private double mouseOffsetX;
    private double mouseOffsetY;

    public TitleBar() {
        this.setMinHeight(SceneController.LauncherConfig.TitleBarHeight);
        this.getStyleClass().add("title-bar");

        /*========================= configuring the buttons =========================*/
        _closeBTN.getStyleClass().addAll("title-bar-button", "title-bar-close-button");
        _minBTN.getStyleClass().addAll("title-bar-button", "title-bar-min-button");

        int barButtonDimentions = (int)(SceneController.LauncherConfig.TitleBarHeight * 0.75);
        _closeBTN.setMinSize(barButtonDimentions, barButtonDimentions);
        _minBTN.setMinSize(barButtonDimentions, barButtonDimentions);

        Insets button_margins = new Insets(5, 5, 4.5, 5);

        HBox.setMargin(_closeBTN, button_margins);
        HBox.setMargin(_minBTN, button_margins);

        _closeBTN.setOnAction(e -> System.exit(0));
        _minBTN.setOnAction(e -> SceneController.getInstance().getStage().setIconified(true));
        /*========================= ======================== =========================*/


        /*========================= configuring the titlebar =========================*/
        _titleLabel.getStyleClass().add("title-window-label");

        Insets titleInsets = new Insets(7, 0, 2.5, 10);
        HBox.setMargin(_titleLabel, titleInsets);
        /*========================= ======================== =========================*/

        HBox.setHgrow(_gap, Priority.ALWAYS);


        /*=================== dont understand this bit properly yet ===================*/
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
        /*========================= ======================== =========================*/

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
