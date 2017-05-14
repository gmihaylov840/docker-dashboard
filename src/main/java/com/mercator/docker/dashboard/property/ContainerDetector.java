package com.mercator.docker.dashboard.property;

import com.mercator.docker.dashboard.common.DockerImageUtilities;
import com.mercator.docker.dashboard.common.FolderUtilities;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Joro on 26.04.2017.
 */
public class ContainerDetector {
    private final PropertiesReader propertiesReader = new PropertiesReader();
    private ArrayList<String> detectedImages = new ArrayList<>();

    public ContainerDetector() throws Exception {
        detectContainers();
    }

    public ArrayList<String> getDetectedImages() {
        return detectedImages;
    }

    private void detectContainers() throws Exception {
        String workspaceDir = propertiesReader.lookupProperty("workspace.dir");
        String dockerComposeDir = workspaceDir + "/deployment/run";
        FolderUtilities.checkFolderExist(dockerComposeDir);

        FileInputStream inStream = new FileInputStream(Paths.get(dockerComposeDir + "/docker-compose.yml").toString());

        Map propertiesMap = (Map) new Yaml().load(inStream);
        Set<Map.Entry> set = propertiesMap.entrySet();
        for (Map.Entry innerMap : set) {
            LinkedHashMap value = (LinkedHashMap) innerMap.getValue();
            String image = (String) value.get("image");
            detectedImages.add(DockerImageUtilities.getProperImageName(image));
        }
    }
}
