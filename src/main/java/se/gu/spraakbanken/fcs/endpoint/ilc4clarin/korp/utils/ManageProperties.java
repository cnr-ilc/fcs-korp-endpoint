/**
 * Forked from https://github.com/clarin-eric/fcs-korp-endpoint
 *
 * @license http://www.gnu.org/licenses/gpl-3.0.txt GNU General Public License
 * v3
 */
package se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.utils;

import java.util.Properties;

/**
 * use this class to pass urls, ports, POS set to the main program
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it|gmail.com&gt;
 */
public class ManageProperties {

    /**
     * 
     * @param prop property file
     * @return the url of Korp Seach Engine
     */
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
    
    /**
     * 
     * @param prop The property file
     * @return the url of the EndPoint
     */
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
