package com.mercator.docker.dashboard.giu.container;

import com.mercator.docker.dashboard.commandShell.CommandAnalyzer;
import com.mercator.docker.dashboard.common.DockerImageUtilities;
import com.mercator.docker.dashboard.domain.DockerContainer;
import com.mercator.docker.dashboard.giu.controls.StartAllContainersButton;
import com.mercator.docker.dashboard.giu.controls.StopAllContainersButton;
import com.mercator.docker.dashboard.property.ContainerDetector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joro on 23.04.2017.
 */
public class DockerDashboardPane extends Pane {

    public static final Map<Button, String> buttonMap = new HashMap<>();

    private final ContainerDetector containerDetector;

    private CommandAnalyzer commandAnalyzer;
    private TilePane dockerContainersTilePane;
    private List<String> containerIds = new ArrayList<>();

    public DockerDashboardPane() throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        containerDetector = new ContainerDetector();
        commandAnalyzer = new CommandAnalyzer("docker ps");

        setStyle("-fx-background-color: #2b2b2b");

//        Label label = new Label();
//        label.setText("Docker Containers");
//        label.setTextFill(Color.web("#ffffff"));
//        getChildren().add(label);

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

        buttonPaneGeneralWrapper.getChildren().add(buttonPaneGeneral);
        getChildren().add(buttonPaneGeneralWrapper);
        dockerContainersTilePane = populateDockerContainersPane();
        getChildren().add(dockerContainersTilePane);

        buttonPaneGeneral.getChildren().add(new StopAllContainersButton(buttonPaneGeneralWrapper, this));
        requestLayout();
    }

    public void refreshDashboard() throws Exception {
        commandAnalyzer = new CommandAnalyzer("docker ps");
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

    private void loadContainersInfo(TilePane tilePane) {
        ObservableList<Node> children = tilePane.getChildren();
        if ( children != null && children.size() > 0 ) {
            children.clear();
        }

        ArrayList<String> expectedContainers = containerDetector.getDetectedImages();
        for (String expectedContainerImage : expectedContainers) {
            StackPane containerInfoStackPane = new StackPane();
            ContainerVBox containerVBox = new ContainerVBox();
            containerVBox.populateMissingContainer(expectedContainerImage, containerInfoStackPane, this);
            containerInfoStackPane.getChildren().add(containerVBox);
            for (String line : commandAnalyzer.getResultLines()) {
                DockerContainer dockerContainer = commandAnalyzer.extractContainerInfoFromLine(line);
                if (expectedContainerImage.equals(DockerImageUtilities.getProperImageName(dockerContainer.getImage()))) {
                    containerVBox.populateExistingContainer(dockerContainer, containerInfoStackPane, containerIds, this);
                }
            }
            tilePane.getChildren().add(containerInfoStackPane);
        }
    }
}
