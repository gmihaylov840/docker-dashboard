package com.mercator.docker.dashboard.giu.container;

import com.mercator.docker.dashboard.domain.DockerContainer;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by Joro on 07.05.2017.
 */
public class ContainerInfoVBox extends VBox {
    public void populateWithDockerContainerData(DockerContainer dockerContainer) {
        addDockerContainerLabel("", "");
        addDockerContainerLabel("ID:", dockerContainer.getContainerId());
        addDockerContainerLabel("Image:", dockerContainer.getImage());
        addDockerContainerLabel("Command:", dockerContainer.getCommand());
        addDockerContainerLabel("Created:", dockerContainer.getCreated());
        addDockerContainerLabel("Status:", dockerContainer.getStatus());
        addDockerContainerLabel("Ports:", dockerContainer.getPorts());
    }

    private void addDockerContainerLabel(String name, String value) {
        Text propertyName = new Text();
        propertyName.setStyle("-fx-font-weight: bolder");
        propertyName.setFill(Paint.valueOf("#ececec"));
        propertyName.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 10));
        propertyName.setText(name + "  ");

        Text propertyValue = new Text();
        propertyValue.setFill(Paint.valueOf("#ececec"));
        propertyValue.setFont(Font.font("Verdana", FontWeight.NORMAL, 10));
        propertyValue.setText(value);

        TextFlow textFlow = new TextFlow();
        textFlow.setLineSpacing(0.0);
        textFlow.getChildren().addAll(propertyName, propertyValue);
        getChildren().add(textFlow);
    }
}
