package dev.me.DZMC.UI.main;

import dev.me.DZMC.Minecraft.Versions;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class GamesetContainer extends VBox {

    private static versionButton _selectedVersionButton;


    public GamesetContainer() {

    }


    private void addVersions(VBox _container) {
        HashMap<String, Versions.Version> all_versions = Versions.enumerateVersions();

        all_versions.forEach((verName, _version) -> {
            _container.getChildren().add(createVersionBox(verName, _version));
        });
    }

    private Button createVersionBox(
            String name,
            Versions.Version ver
    ) {
        versionButton _newButton = new versionButton();
        _newButton.version = ver;
        _newButton.setText(name);

        _newButton.setOnAction(e -> {
            _newButton.setSelected();
            _selectedVersionButton = _newButton;
        });

        _newButton.setMaxWidth(Double.MAX_VALUE);

        return _newButton;
    }

    private static class versionButton extends Button {
        public Versions.Version version;
        public static final String selectedClass = "main-version-button-selected";
        public static final String unselectedClass = "main-version-button-unselected";

        public void setSelected() {
            if (_selectedVersionButton == this) return;
            if (_selectedVersionButton != null) {
                _selectedVersionButton.getStyleClass().setAll(
                        "button",
                        unselectedClass
                );
            }
            this.getStyleClass().setAll(
                    "button",
                    selectedClass
            );
        }

        public versionButton() {
            super();
            this.getStyleClass().add(versionButton.unselectedClass);
        }
    }
}
