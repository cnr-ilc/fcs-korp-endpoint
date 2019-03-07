/**
 * Forked from https://github.com/clarin-eric/fcs-korp-endpoint
 *
 * @license http://www.gnu.org/licenses/gpl-3.0.txt GNU General Public License
 * v3
 */
package se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it|gmail.com&gt;
 */
public class Utilities {
    private static Logger LOG = LoggerFactory.getLogger(Utilities.class);
    
    /**
     * 
     * @param prop the property file
     * @return the map between the corpus and the tagset, according to propriety CORPORA2POS
     */
    public static Map getTagSetFromCorpusList(Properties prop) {
        String temp = prop.getProperty("CORPORA2POS");
        List<String> mappedList = Collections.unmodifiableList(Arrays.asList(temp.split(",")));
        Map<String, String> map = new HashMap<String, String>();
        String tagset;
        String pos = "", pcorpus = "";
        
        for (String mapped : mappedList) {
            pcorpus = mapped.split("=")[0];
            pos = mapped.split("=")[1];
            map.put(pcorpus, pos);

        }
       
        return map;
    }
    
    /**
     * 
     * @param prop the property file 
     * @param corpus the corpus 
     * @return the tagset of corpus 
     */
    public static String getTagSetFromCorpus(Properties prop, String corpus) {
        String temp = prop.getProperty("CORPORA2POS");
        List<String> mappedList = Collections.unmodifiableList(Arrays.asList(temp.split(",")));
        Map<String, String> map = new HashMap<String, String>();
        String tagset;
        String pos = "", pcorpus = "";
        
        for (String mapped : mappedList) {
            pcorpus = mapped.split("=")[0];
            pos = mapped.split("=")[1];
            map.put(pcorpus, pos);

        }
        tagset = map.get(corpus);
        LOG.debug("se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.utils.getTagSetFromCorpus() Getting tagset " + tagset + " for value '{}'", corpus);

        return tagset;
    }
    
    /**
     * 
     * @param map the map between corpus and tagset
     * @param corpus the corpus
     * @return  the tagset of corpus
     */
    public static String getTagSetFromCorpusMap(Map<String,String> map, String corpus) {
        String  tagset = map.get(corpus);
        LOG.debug("se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.utils.getTagSetFromCorpusMap() Getting tagset " + tagset + " for value '{}'", corpus);
        
        return tagset;
    }
    
    
    
}
