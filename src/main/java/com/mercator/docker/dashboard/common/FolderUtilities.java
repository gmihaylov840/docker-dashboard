package com.mercator.docker.dashboard.common;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Joro on 01.05.2017.
 */
public class FolderUtilities {

    public static void checkFolderExist(String folderDir) throws FileNotFoundException {
        if (!Files.exists(Paths.get(folderDir))) {
            throw new FileNotFoundException("Cannot read from docker-compose directory: " + folderDir);
        }
    }
}
