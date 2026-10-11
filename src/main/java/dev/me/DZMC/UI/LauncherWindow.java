package dev.me.DZMC.UI;

import dev.me.DZMC.Minecraft.CommandBuilder;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LauncherWindow {
    private static boolean initialized = false;

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
        if (initialized) return;
        javafx.application.Application.launch(
                FXBooter.class, _args
        );
        initialized = true;
    }

    private static void initializeWindow() {

        mainstage.setHeight(1000);
        mainstage.setWidth(1000);

        Label lbl = new Label("hello");
        Button launch = new Button("launch");

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
}
