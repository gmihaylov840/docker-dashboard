package com.mercator.docker.dashboard.property;

import java.util.Properties;

/**
 * Created by Joro on 26.04.2017.
 */
public class PropertiesReader {

    public String lookupProperty(String property) throws Exception {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/application.properties"));
        return (String) props.get(property);
    }
}
