package com.mercator.docker.dashboard.giu.service;

import com.mercator.docker.dashboard.common.FolderUtilities;
import com.mercator.docker.dashboard.giu.container.DockerDashboardPane;
import com.mercator.docker.dashboard.property.PropertiesReader;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Created by Joro on 01.05.2017.
 */
public abstract class ContainerService extends Service<Void> {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private StackPane containerStackPane;
    private VBox buttonPane;
    private DockerDashboardPane dockerDashboardPane;
    private Button sourceBtn;
    private final String dockerComposeDir;

    public String getDockerComposeDir() {
        return dockerComposeDir;
    }

    public ContainerService(StackPane containerStackPane, VBox buttonPane, DockerDashboardPane dockerDashboardPane,
                            Button sourceBtn) {
        this.containerStackPane = containerStackPane;
        this.buttonPane = buttonPane;
        this.dockerDashboardPane = dockerDashboardPane;
        this.sourceBtn = sourceBtn;

        setOnSucceeded(onReadyEvent -> refreshDashboard());

        String workspaceDir = null;
        try {
            workspaceDir = new PropertiesReader().lookupProperty("workspace.dir");
        } catch (Exception e) {
            e.printStackTrace();
        }
        dockerComposeDir = workspaceDir + "/deployment/run";
        try {
            FolderUtilities.checkFolderExist(dockerComposeDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public abstract ServiceAction getServiceAction() throws Exception;

    protected synchronized void updateCursorBeforeOperation() {
        initProgressIndicator();
        containerStackPane.setCursor(Cursor.WAIT);
        sourceBtn.setCursor(Cursor.WAIT);
        sourceBtn.setOnMouseEntered(event -> sourceBtn.setCursor(Cursor.WAIT));
        sourceBtn.setOnMouseExited(event -> sourceBtn.setCursor(Cursor.WAIT));
        buttonPane.setDisable(true);
    }

    protected synchronized void updateCursorAfterOperation() {
//        containerStackPane.setCursor(Cursor.DEFAULT);
//        containerStackPane.setDisable(false);
//        buttonPane.setCursor(Cursor.DEFAULT);
//        buttonPane.setDisable(false);
    }

    private void initProgressIndicator() {
        VBox progressVBox = new VBox(new ProgressIndicator());
        progressVBox.setAlignment(Pos.CENTER);

        String backgroundColor = "-fx-background-color: rgba(205,205,205,0.7);";
        String borderRadius = "-fx-border-radius: 10 10 0 0;";
        String backgroundRadius = "-fx-background-radius: 10 10 0 0px;";
        progressVBox.setStyle(backgroundColor + borderRadius + backgroundRadius);

        Platform.runLater(() -> containerStackPane.getChildren().add(progressVBox));
    }

    private synchronized void refreshDashboard() {
        try {
            LOGGER.info("Reloading all containers");
            dockerDashboardPane.refreshDashboard();
            LOGGER.info("All containers reloaded successfully");
        } catch (Exception e) {
            LOGGER.severe(String.format("Couldn't refresh dashboard: %s", e.getMessage()));
        }
    }
}
