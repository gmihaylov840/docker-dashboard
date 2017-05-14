package com.mercator.docker.dashboard.giu.container;

import com.mercator.docker.dashboard.domain.DockerContainer;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Created by Joro on 07.05.2017.
 */
public class ContainerVBox extends VBox {

    private ButtonPane buttonPane;
    private ContainerInfoVBox infoVBox;

    public void populateMissingContainer(String dockerImageName, StackPane containerInfoStackPane,
                                         DockerDashboardPane dashboardPane) {
        setPadding(new Insets(10,10,10,10));
        setSpacing(0.0);

        String backgroundColor = "-fx-background-color: linear-gradient(from 0px 0px to 0px 35px, #800015, #ab0a0f 35%, #720005 99%, dimgray);";
        String borderRadius = "-fx-border-radius: 10 10 0 0;";
        String borderColor = "-fx-border-color: #640005;";
        String borderWidth = "-fx-border-width: 2px;";
        String backgroundRadius = "-fx-background-radius: 10 10 0 0px;";
        setStyle(backgroundColor + borderRadius + backgroundRadius + borderColor + borderWidth);

        Text containerHeaderLabel = new Text(dockerImageName);
        containerHeaderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        containerHeaderLabel.setFill(Color.valueOf("#ffffff"));
        getChildren().add(containerHeaderLabel);

        infoVBox = new ContainerInfoVBox();
        getChildren().add(infoVBox);

        buttonPane = new ButtonPane();
        buttonPane.updateForStoppedContainer(dockerImageName, containerInfoStackPane, dashboardPane);
        getChildren().add(buttonPane);
    }

    public void populateExistingContainer(DockerContainer dockerContainer, StackPane containerInfoStackPane,
                                          List<String> containerIds, DockerDashboardPane dashboardPane) {
        String backgroundColor =
                "-fx-background-color: linear-gradient(from 0px 0px to 0px 35px, green, forestgreen 35%, darkgreen 99%, dimgray);";
        String borderRadius = "-fx-border-radius: 10 10 0 0;";
        String borderColor = "-fx-border-color: #006800;";
        String borderWidth = "-fx-border-width: 2px;";
        String backgroundRadius = "-fx-background-radius: 10 10 0 0px;";
        setStyle(backgroundColor + borderRadius + backgroundRadius + borderColor + borderWidth);

        infoVBox.populateWithDockerContainerData(dockerContainer);

        containerIds.add(dockerContainer.getContainerId());

        buttonPane.updateForStartedContainer(dockerContainer.getContainerId(), dockerContainer.getImage(),
                containerInfoStackPane, dashboardPane);
    }

}
