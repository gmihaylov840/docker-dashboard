package com.mercator.docker.dashboard.giu.container;

import com.mercator.docker.dashboard.commandShell.CommandExecutor;
import com.mercator.docker.dashboard.giu.controls.StartContainerButton;
import com.mercator.docker.dashboard.giu.controls.StopContainerButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Created by Joro on 07.05.2017.
 */
public class ButtonPane extends VBox {

    private StartContainerButton startButton;
    private final HBox buttonsContainer;

    public ButtonPane() {
        Separator separator = new Separator();
        separator.setStyle("-fx-border-style: solid; -fx-border-width: 5 0 5 0; -fx-border-color: transparent;");
        separator.setPrefWidth(Double.MAX_VALUE);
        getChildren().add(separator);

        buttonsContainer = new HBox();
        buttonsContainer.setSpacing(10);
        buttonsContainer.setAlignment(Pos.BASELINE_CENTER);
        getChildren().add(buttonsContainer);
    }

    private void addLogButton(String containerId, String imageName) {
        Button showLogButton = new Button("Log");
        showLogButton.setOnAction(e -> {
            String result = new CommandExecutor().executeCommand("docker logs " + containerId);
            TextFlow textFlow = new TextFlow();
            textFlow.getChildren().add(new Text(result));

            Pane logPane = new Pane();
            logPane.getChildren().add(textFlow);

            Stage stage = new Stage();
            stage.setTitle(imageName + " log");

            VBox logVBoxPane = new VBox();
            logVBoxPane.getChildren().add(logPane);

            ScrollPane scrollPane = new ScrollPane();
            BackgroundFill myBF = new BackgroundFill(Color.RED, null, null);
            scrollPane.setBackground(new Background(myBF));
            scrollPane.setStyle("-fx-background: #2b2b2b; -fx-text-fill: #cecece");
            scrollPane.setContent(logVBoxPane);
            scrollPane.setPrefSize(1000, 500);
            Button logRefreshButtonTop = new Button("Refresh log");

            Button goToTopBtn = new Button("Go to top");
            goToTopBtn.setOnAction(actionEvent -> scrollPane.setVvalue(0));
            HBox bottomButtonPane = new HBox(goToTopBtn);
            bottomButtonPane.setPadding(new Insets(5, 5, 5, 5));

            Button goToBottomBtn = new Button("Go to bottom");
            goToBottomBtn.setOnAction(actionEvent -> scrollPane.setVvalue(1));
            HBox topButtonPane = new HBox(logRefreshButtonTop, goToBottomBtn);
            topButtonPane.setSpacing(10);
            topButtonPane.setPadding(new Insets(5, 5, 5, 5));

            VBox mainVBox = new VBox();
            mainVBox.setStyle("-fx-background-color: #2b2b2b");
            mainVBox.getChildren().add(topButtonPane);
            mainVBox.getChildren().add(scrollPane);
            mainVBox.getChildren().add(bottomButtonPane);

            Scene scene = new Scene(mainVBox);
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
        StopContainerButton stopButton = new StopContainerButton(containerId, containerInfoStackPane, this, dashboardPane);
        startButton.setDisable(true);
        DockerDashboardPane.buttonMap.put(stopButton, "stop");
        buttonsContainer.getChildren().add(stopButton);
        addLogButton(containerId, imageName);
    }
}
