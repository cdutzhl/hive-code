package cn.scu.imc.hiver.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class PropertiesUtils {

    private static Properties properties =null;


    static {
        properties = new Properties();
        try {
            properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("./application.properties"));
        } catch (IOException e) {
            new RuntimeException(e.getMessage(),e);
        }
    }


    public static Object getValueByKey(String key){
        return properties.getProperty(key);
    }

}




