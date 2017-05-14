package com.mercator.docker.dashboard.giu.container;

import com.mercator.docker.dashboard.domain.DockerContainer;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;

/**
 * Created by Joro on 07.05.2017.
 */
public class ContainerInfoVBox extends VBox {
    public void populateWithDockerContainerData(DockerContainer dockerContainer) {
        addDockerContainerLabel("", "");
        addDockerContainerLabel("ID:", dockerContainer.getContainerId());
        addDockerContainerLabel("Image:", dockerContainer.getImage());
//        addDockerContainerLabel("Command:", dockerContainer.getCommand());
        addDockerContainerLabel("Created:", dockerContainer.getCreated());
        addDockerContainerLabel("Status:", dockerContainer.getStatus());
        addDockerContainerLabel("Ports:", dockerContainer.getPorts());
    }

    private void addDockerContainerLabel(String name, String value) {
        Text propertyName = new Text();
        propertyName.setStyle("-fx-font-weight: bold; -fx-fill: lightgray");
        propertyName.setFont(Font.font("Droid Sans Mono", FontWeight.EXTRA_BOLD, 10));
        propertyName.setText(name + "  ");

        Text propertyValue = new Text();
        propertyValue.setStyle("-fx-font-weight: normal; -fx-fill: lightgray");
        propertyValue.setFont(Font.font("Droid Sans Mono", FontWeight.BOLD, 10));
        propertyValue.setText(value);

        TextFlow textFlow = new TextFlow();
        textFlow.setLineSpacing(0.0);
        textFlow.getChildren().addAll(propertyName, propertyValue);

        getChildren().add(textFlow);
    }
}
