package com.mercator.docker.dashboard.giu.container;

import com.mercator.docker.dashboard.commandShell.CommandAnalyzer;
import com.mercator.docker.dashboard.common.DockerImageUtilities;
import com.mercator.docker.dashboard.domain.DockerContainer;
import com.mercator.docker.dashboard.giu.controls.StartAllContainersButton;
import com.mercator.docker.dashboard.giu.controls.StopAllContainersButton;
import com.mercator.docker.dashboard.property.ContainerDetector;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Joro on 23.04.2017.
 */
public class DockerDashboardPane extends Pane {

    public static final Map<Button, String> buttonMap = new HashMap<>();

    private final ContainerDetector containerDetector = new ContainerDetector();

    private CommandAnalyzer commandAnalyzer = new CommandAnalyzer();
    private TilePane dockerContainersTilePane;
    private List<String> containerIds = new ArrayList<>();
    private BooleanProperty isInitializedProperty;

    public DockerDashboardPane() throws Exception {
        isInitializedProperty = new SimpleBooleanProperty(false);

        setStyle("-fx-background-color: #2b2b2b");

        VBox buttonPaneGeneralWrapper = new VBox();
        buttonPaneGeneralWrapper.setLayoutX(40);
        buttonPaneGeneralWrapper.setLayoutY(20);

        HBox buttonPaneGeneral = new HBox();
        buttonPaneGeneral.setSpacing(20);
        Button refreshBtn = new Button("Refresh Dashboard");
        refreshBtn.setOnAction(e -> {
            try {
                refreshDashboard();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        buttonPaneGeneral.getChildren().add(refreshBtn);
        buttonPaneGeneral.getChildren().add(new StartAllContainersButton(buttonPaneGeneralWrapper, this));
        buttonPaneGeneral.getChildren().add(new StopAllContainersButton(buttonPaneGeneralWrapper, this));

        buttonPaneGeneralWrapper.getChildren().add(buttonPaneGeneral);
        getChildren().add(buttonPaneGeneralWrapper);

        showProgressBar();
    }

    private void showProgressBar() {
        new Thread(() -> {
            String loadingLabel = "Loading containers...";
            Text progressLabel = new Text(loadingLabel);
            progressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            progressLabel.setFill(Color.valueOf("#ffffff"));

            ProgressBar progressBar = new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
            progressBar.setTooltip(new Tooltip(loadingLabel));
            progressBar.setPrefWidth(1100);

            VBox progressVBox = new VBox();
            progressVBox.boundsInLocalProperty();
            progressVBox.setAlignment(Pos.BASELINE_CENTER);
            progressVBox.setSpacing(10);
            progressVBox.setLayoutX(40);
            progressVBox.setLayoutY(140);
            progressVBox.getChildren().add(progressLabel);
            progressVBox.getChildren().add(progressBar);
            progressBar.visibleProperty().bind(isInitializedProperty.not());

            getChildren().add(progressVBox);
        }).start();
    }

    public void initialize() throws Exception {
        for (int i = 0; i < 100000; ++i) {
            System.out.println(i);
        }

        dockerContainersTilePane = populateDockerContainersPane();
        runInProperThread((x) -> getChildren().add(dockerContainersTilePane));

        isInitializedProperty.setValue(true);
    }

    private void runInProperThread(Consumer guiAction) {
        if (Platform.isFxApplicationThread()) {
            guiAction.accept("");
        } else {
            Platform.runLater(() -> guiAction.accept(""));
        }
    }

    public void refreshDashboard() throws Exception {
        commandAnalyzer.analyzeCommand("docker ps");
        buttonMap.clear();
        loadContainersInfo(dockerContainersTilePane);
    }

    private TilePane populateDockerContainersPane() throws Exception {
        containerIds.clear();

        TilePane tilePane = new TilePane();
        tilePane.setHgap(40.0);
        tilePane.setVgap(40.0);
        tilePane.setLayoutX(20.0);
        tilePane.setLayoutY(40.0);
        tilePane.setPadding(new Insets(20, 20, 20, 20));
        tilePane.setPrefTileWidth(300.0);
        tilePane.setPrefWidth(1200.0);

        loadContainersInfo(tilePane);

        return tilePane;
    }

    private void loadContainersInfo(TilePane tilePane) throws Exception {
        commandAnalyzer.analyzeCommand("docker ps");

//        ObservableList<Node> children = tilePane.getChildren();
//        if (children != null && children.size() > 0) {
//            children.clear();
//        }

        addMissingContainers(tilePane, containerDetector.getDetectedImages());
    }

    private void addMissingContainers(TilePane tilePane, ArrayList<String> expectedContainers) {
        for (String expectedContainerImage : expectedContainers) {
            StackPane containerInfoStackPane = new StackPane();
            ContainerVBox containerVBox = new ContainerVBox();
            containerVBox.populateMissingContainer(expectedContainerImage, containerInfoStackPane, this);
            containerInfoStackPane.getChildren().add(containerVBox);
            addRunningContainers(expectedContainerImage, containerInfoStackPane, containerVBox);
            tilePane.getChildren().add(containerInfoStackPane);
        }
    }

    private void addRunningContainers(String expectedContainerImage, StackPane containerInfoStackPane, ContainerVBox containerVBox) {
        for (String line : commandAnalyzer.getResultLines()) {
            DockerContainer dockerContainer = commandAnalyzer.extractContainerInfoFromLine(line);
            if (expectedContainerImage.equals(DockerImageUtilities.getProperImageName(dockerContainer.getImage()))) {
                containerVBox.populateExistingContainer(dockerContainer, containerInfoStackPane, containerIds, this);
            }
        }
    }
}
