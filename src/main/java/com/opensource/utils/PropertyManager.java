package com.opensource.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class PropertyManager {

    static Map<String, Properties> props = new HashMap<>();

    static  {
        List<String> propFiles = new ArrayList<>();
        propFiles.add("env");
        propFiles.add("test-data/user");

        for (String pf : propFiles) {
            Properties prop = new Properties();
            try {
                prop.load(new FileInputStream("src/test/resources/" + pf + ".properties"));
                String[] fileName=pf.split("/");
                props.put(fileName[fileName.length-1], prop);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getStringValue(String file, String key) {
        Properties prop = props.get(file);
        if (prop != null) {
            return prop.getProperty(key);
        }
        System.out.println(file + " propery file not found");
        return null;
    }

    public static Integer getIntegerValue(String file, String key) {
        Properties prop = props.get(file);
        if (prop != null) {
            return Integer.parseInt(prop.getProperty(key));
        }
        System.out.println(file + " propery file not found");
        return null;
    }

    public static Double getDoubleValue(String file, String key) {
        Properties prop = props.get(file);
        if (prop != null) {
            return Double.parseDouble(prop.getProperty(key));
        }
        System.out.println(file + " propery file not found");
        return null;
    }

    public static Boolean getBooleanValue(String file, String key) {
        Properties prop = props.get(file);
        if (prop != null) {
            return Boolean.parseBoolean(prop.getProperty(key));
        }
        System.out.println(file + " propery file not found");
        return null;
    }
}
