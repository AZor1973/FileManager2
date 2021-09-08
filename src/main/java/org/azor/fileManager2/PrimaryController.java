package org.azor.fileManager2;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;

public class PrimaryController {
    public VBox leftPanel;
    public VBox rightPanel;

    public void btnExitAction() {
        Platform.exit();
    }

    public void copyBtnAction() {
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");
        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
            Alert alert = new Alert(AlertType.ERROR, "No file has been selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        PanelController srcPC = null, dstPC = null;
        if (leftPC.getSelectedFilename() != null) {
            srcPC = leftPC;
            dstPC = rightPC;
        }
        if (rightPC.getSelectedFilename() != null) {
            srcPC = rightPC;
            dstPC = leftPC;
        }
        Path srcPath = Paths.get(Objects.requireNonNull(srcPC).getCurrentPath(), srcPC.getSelectedFilename());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());
        try {
            Files.copy(srcPath, dstPath);
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, "Failed to copy the specified file", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void cutBtnAction() {
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");
        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
            Alert alert = new Alert(AlertType.ERROR, "No file has been selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        PanelController srcPC = null, dstPC = null;
        if (leftPC.getSelectedFilename() != null) {
            srcPC = leftPC;
            dstPC = rightPC;
        }
        if (rightPC.getSelectedFilename() != null) {
            srcPC = rightPC;
            dstPC = leftPC;
        }
        Path srcPath = Paths.get(Objects.requireNonNull(srcPC).getCurrentPath(), srcPC.getSelectedFilename());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());
        try {
            Files.move(srcPath, dstPath);
            srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, "Failed to cut the specified file", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void deleteBtnAction() {
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");
        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
            Alert alert = new Alert(AlertType.ERROR, "No file has been selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        PanelController srcPC = null, dstPC = null;
        if (leftPC.getSelectedFilename() != null) {
            srcPC = leftPC;
            dstPC = rightPC;
        }
        if (rightPC.getSelectedFilename() != null) {
            srcPC = rightPC;
            dstPC = leftPC;
        }
        Path srcPath = Paths.get(Objects.requireNonNull(srcPC).getCurrentPath(), srcPC.getSelectedFilename());
        try {
            if (Files.isDirectory(srcPath)) {
                Files.walk(srcPath)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } else {
                Files.delete(srcPath);
            }
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, "Failed to delete the specified file", ButtonType.OK);
            alert.showAndWait();
        }
        srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
        Objects.requireNonNull(dstPC).updateList(Paths.get(dstPC.getCurrentPath()));
    }
}
