package com.mercator.docker.dashboard.giu.container;

import com.mercator.docker.dashboard.commandShell.CommandExecutor;
import com.mercator.docker.dashboard.giu.controls.StartContainerButton;
import com.mercator.docker.dashboard.giu.controls.StopContainerButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Created by Joro on 07.05.2017.
 */
public class ButtonPane extends VBox {

    private StartContainerButton startButton;
    private final HBox buttonsContainer;
    private StopContainerButton stopButton;

    public ButtonPane() {
        Separator separator = new Separator();
        separator.setStyle("-fx-border-style: solid;-fx-border-width: 5 0 5 0;-fx-border-color: transparent;");
        separator.setPrefWidth(Double.MAX_VALUE);
        getChildren().add(separator);

        buttonsContainer = new HBox();
        buttonsContainer.setSpacing(10);
        buttonsContainer.setAlignment(Pos.BASELINE_CENTER);
        getChildren().add(buttonsContainer);
    }

    private void addLogButton(String containerId, String imageName) {
        Button showLogButton = new Button("Show log");
        showLogButton.setOnAction(e -> {
            String result = new CommandExecutor().executeCommand("docker logs " + containerId);
            TextFlow textFlow = new TextFlow();
            textFlow.getChildren().add(new Text(result));

            Pane logPane = new Pane();
            logPane.getChildren().add(textFlow);

            Stage stage = new Stage();
            stage.setTitle(imageName + " log");

            VBox logVboxPane = new VBox();
            logVboxPane.getChildren().add(logPane);

            ScrollPane sp = new ScrollPane();
            sp.setContent(logVboxPane);
            sp.setPrefSize(1000, 500);
            Button logRefreshButton = new Button("Refresh log");

            VBox mainVbox  = new VBox();
            mainVbox.getChildren().add(logRefreshButton);
            mainVbox.getChildren().add(sp);

            Scene scene = new Scene(mainVbox);
            stage.setScene(scene);

            stage.show();
        });
        buttonsContainer.getChildren().add(showLogButton);
    }

    public void updateForStoppedContainer(String dockerImageName, StackPane containerInfoStackPane,
                                          DockerDashboardPane dashboardPane) {
        startButton = new StartContainerButton(dockerImageName, containerInfoStackPane, this, dashboardPane);
        DockerDashboardPane.buttonMap.put(startButton, "start");
        buttonsContainer.getChildren().add(startButton);
    }

    public void updateForStartedContainer(String containerId, String imageName, StackPane containerInfoStackPane,
                                          DockerDashboardPane dashboardPane) {
        stopButton = new StopContainerButton(containerId, containerInfoStackPane, this, dashboardPane);
        startButton.setDisable(true);
        DockerDashboardPane.buttonMap.put(stopButton, "stop");
        buttonsContainer.getChildren().add(stopButton);
        addLogButton(containerId, imageName);
    }
}
