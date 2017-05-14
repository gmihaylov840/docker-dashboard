package com.mercator.docker.dashboard.common;

public class DockerImageUtilities {
    public static String getProperImageName(String image) {
        if (image.contains(":")) {
            image = image.substring(0, image.indexOf(":"));
        }
        if (image.contains("/")) {
            image = image.substring(image.indexOf("/") + 1);
        }

        return image;
    }
}