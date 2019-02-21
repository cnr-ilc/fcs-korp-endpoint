/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.gu.spraakbanken.fcs.endpoint.korp.utils;

import java.util.Properties;

/**
 * use this class to pass urls, ports POS set to the main program
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class ManageProperties {

    public static String createKorpUrl(Properties prop) {

        String korpUrl = prop.getProperty("korpUrl");
        String korpPort = prop.getProperty("korpPort");
        Boolean useKorpPort = true;
        String transport;
        transport = prop.getProperty("transport");

        useKorpPort = "True".equalsIgnoreCase(prop.getProperty("useKorpPort"));

        // create the korp url
        if (useKorpPort) {
            korpUrl = transport + "://" + korpUrl + ":" + korpPort + "/";
        } else {
            korpUrl = transport + "://" + korpUrl + "/";
        }
        return korpUrl;

    }
    
    public static String createServerUrl(Properties prop) {

        String serverUrl = prop.getProperty("serverUrl");
        String serverPort = prop.getProperty("serverPort");
        Boolean useServerPort = true;
        String transport;
        transport = prop.getProperty("transport");

        useServerPort = "True".equalsIgnoreCase(prop.getProperty("useServerPort"));

        // create the korp url
        if (useServerPort) {
            serverUrl = transport + "://" + serverUrl + ":" + serverPort + "/";
        } else {
            serverUrl = transport + "://" + serverUrl + "/";
        }
        return serverUrl;

    }

    

}
