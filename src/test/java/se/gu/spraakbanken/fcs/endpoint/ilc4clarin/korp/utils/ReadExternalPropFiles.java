/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.utils;

import eu.clarin.sru.server.SRUConfigException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * use this class to pass urls, ports POS set to the main program
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class ReadExternalPropFiles {

    String name;
    static String[] result;
    static InputStream inputStream;
    static Properties prop;

    /**
     * This function allows to configure the server from an external file
     *
     * @param fileprop the external file
     * @return the property file
     * @throws IOException
     * @throws SRUConfigException
     */
    public static Properties getPropertyFile(String fileprop) throws IOException, SRUConfigException {
        prop = new Properties();
        try {
            //inputStream = ReadExternalPropFiles.class.getResourceAsStream(fileprop);
            inputStream = new FileInputStream(fileprop);// ReadExternalPropFiles.class.getResourceAsStream(fileprop);

            prop.load(inputStream);
        } catch (IOException ioe) {

            throw new SRUConfigException("Property File " + fileprop + " - " + ioe.getMessage());

        }

        return prop;
    }

}
