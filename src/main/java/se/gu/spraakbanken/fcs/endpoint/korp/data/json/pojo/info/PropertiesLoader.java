/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.gu.spraakbanken.fcs.endpoint.korp.data.json.pojo.info;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
final class PropertiesLoader {

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * @return the lib
     */
    public Map getLib() {
        return lib;
    }

    /**
     * @param lib the lib to set
     */
    public void setLib(Map lib) {
        this.lib = lib;
    }
    private Properties properties;
    private Map lib = new HashMap();
    private static final String FILENAME = "/tmp/config-test.properties";
    private static final PropertiesLoader propertiesLoader = new PropertiesLoader(FILENAME);

    private PropertiesLoader(String fileName) {
        System.err.println("STICA SON QUI");
        try {
            properties = load(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesLoader getInstance() {
        return propertiesLoader;
    }

    private Properties load(String propName) throws IOException {

        Properties prop = (Properties) lib.get(propName);
        if (prop == null) {
            System.out.println("***STICA NULL***");
            InputStream is = new FileInputStream(propName);
            Properties props = new Properties();
            props.load(is);
            lib.put(propName, prop);
            setLib(lib);
        } else {

            System.out.println("***STICA NOT NULL***");
        }

        return prop;
    }

//    private Properties load( String propName) throws IOException
//    {
// 
//        Properties prop = (Properties) getLib().get( propName );
//        synchronized ( getLib() )
//        {
//            prop = (Properties) getLib().get( propName );
//            if (prop == null)
//            {
//                System.out.println("***STICA***");
//                InputStream is = new FileInputStream(propName);
//                Properties props = new Properties();
//                props.load(is);
//                getLib().put(propName, prop);
//            }
//        }
// 
//        return prop;
//    }
}
