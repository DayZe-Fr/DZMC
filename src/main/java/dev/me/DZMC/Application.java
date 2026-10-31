package dev.me.DZMC;


import dev.me.DZMC.UI.LauncherWindow;
import dev.me.DZMC.UI.SceneController;

public class Application {
    public static void main(String[] args) {
//        LauncherWindow.setArgs(args);
//        LauncherWindow.startWindow();

        javafx.application.Application.launch(
                SceneController.class, args
        );
    }
}
