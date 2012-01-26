/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.geek.smajava;

import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author geek
 */
public class Configuration {
    
    private static Configuration configuration = new Configuration();

    
    private Inverter inverter;
    
    private String dataFilename;
    
    private Environment environment;
    
    private Collection<ReturnType> returnTypes;

    static void init() throws IOException {
        configuration.returnTypes = ReturnType.load();
        configuration.setEnvironment(new Environment());
    }    
    
    public static Inverter getInverter() {
        return configuration.inverter;
    }
    
    public static void setInverter(Inverter inverter) {
        configuration.inverter = inverter;
    }

    public static String getDataFilename() {
        return configuration.dataFilename;
    }

    public static void setDataFilename(String dataFilename) {
        configuration.dataFilename = dataFilename;
    }
    
    public static Environment getEnvironment() {
        return configuration.environment;
    }
    
    public static void setEnvironment(Environment environment) {
        configuration.environment = environment;
    }
    
    public static Collection<ReturnType> getReturnTypes() {
        return configuration.returnTypes;
    }
}
