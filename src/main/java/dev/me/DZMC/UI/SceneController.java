package dev.me.DZMC.UI;

import dev.me.DZMC.Data;
import dev.me.DZMC.Debug;
import dev.me.DZMC.UI.main.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class SceneController extends Application {
    private static SceneController instance;
    public static SceneController getInstance() {return instance;}

    private Stage _mainStage;
    public Stage getStage() {return _mainStage;}
    private Scene _scene;
    private StackPane _rootStackPane;
    private VBox _container;

    private TitleBar _titleBar;

    // Views

    private MainView _mainView;

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        _mainStage = stage;
        _mainStage.setResizable(false);
        _mainStage.setTitle("WIP: DZMC");
        _mainStage.initStyle(StageStyle.TRANSPARENT);


        // Object Creation
        _rootStackPane = new StackPane();
        _rootStackPane.getStyleClass().add("transparent");

        Rectangle clip = new Rectangle();
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        clip.widthProperty().bind(_rootStackPane.widthProperty());
        clip.heightProperty().bind(_rootStackPane.heightProperty());

        Rectangle border = new Rectangle();
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.rgb(90, 90, 90, 0.65));
        border.setStrokeWidth(1.5);
        border.setArcWidth(15);
        border.setArcHeight(15);
        border.widthProperty().bind(_rootStackPane.widthProperty().subtract(1));
        border.heightProperty().bind(_rootStackPane.heightProperty().subtract(1));
        border.setMouseTransparent(true);

        _rootStackPane.setClip(clip);

        _scene = new Scene(this._rootStackPane,
                LauncherConfig.WindowBaseWidth,
                LauncherConfig.WindowBaseHeight);
        _scene.setFill(Color.TRANSPARENT);
        _scene.getStylesheets().add(getCSSSheet_URL("fx-css/general.css").toExternalForm());

        _container = new VBox();
        _container.getStyleClass().add("transparent");

        _titleBar = new TitleBar();

        // View Creation
        _mainView = new MainView();

        // Adjustments
        _mainStage.setMinHeight(LauncherConfig.WindowBaseHeight);
        _mainStage.setMinWidth(LauncherConfig.WindowBaseWidth);

        // Stacking
        _container.getChildren().add(_titleBar);
        _container.getChildren().add(_mainView);
        _rootStackPane.getChildren().add(_container);

        _rootStackPane.getChildren().add(border);

        _mainStage.setScene(_scene);
        _mainStage.show();
    }

    /// <summery>Gets the `java.net.URL` from a resource. The 'resources/' is included for you.</summery>
    public static URL getCSSSheet_URL(String Path) {
        try {
            return Data.getResourcePath(Path);
        } catch (Data.DataLoadingException e) {
            Debug.logToTerminal("No file generator/repairer YET");
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public static class LauncherConfig {
        public static final int WindowBaseHeight = (int)(Screen.getPrimary().getBounds().getHeight() * 0.78);
        public static final int WindowBaseWidth = (int)(Screen.getPrimary().getBounds().getWidth() * 0.75);
        // content
        public static final int ContentAreaHeight = (int)(Screen.getPrimary().getBounds().getHeight() * 0.75);
        public static final int TitleBarHeight = (int)(Screen.getPrimary().getBounds().getHeight() * 0.03);
    }
}
