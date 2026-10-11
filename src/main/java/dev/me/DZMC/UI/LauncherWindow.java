package dev.me.DZMC.UI;

import dev.me.DZMC.Localisation.LangLoader;
import dev.me.DZMC.Minecraft.CommandBuilder;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LauncherWindow {
    private static boolean _initialized = false;

    private static LauncherWindow _windowInstance;
    private static FXBooter _booter;
    private static String[] _args;


    public static Stage mainstage;

    public static void setArgs(String[] args) {
        _args = args;
    }

    private LauncherWindow() {

    }

    public static void startWindow() {
        if (_initialized) return;
        javafx.application.Application.launch(
                FXBooter.class, _args
        );
        _initialized = true;
    }

    private static void initializeWindow() {

        mainstage.setHeight(LauncherConfig.WindowBaseHeight);
        mainstage.setWidth(LauncherConfig.WindowBaseWidth);

        Label lbl = new Label("hello");
        Button launch = new Button(LangLoader.Query("dev.me.DZMC.launch_game"));

        VBox content = new VBox();

        content.getChildren().add(lbl);
        content.getChildren().add(launch);

        Scene scene = new Scene(content);

        mainstage.setScene(scene);

        launch.setOnAction(actionEvent -> {
            CommandBuilder.buildCommand();
            CommandBuilder.Run(null);
        });

        mainstage.show();
    }


    public static class FXBooter extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            mainstage = stage;
            initializeWindow();
        }
    }







    public static class LauncherConfig {
        public static final int WindowBaseHeight = (int)(Screen.getPrimary().getBounds().getHeight() * 0.75);
        public static final int WindowBaseWidth = (int)(Screen.getPrimary().getBounds().getWidth() * 0.75);


    }
}
