/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.gu.spraakbanken.fcs.endpoint.korp.utils;

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
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class Utilities {
    private static Logger LOG = LoggerFactory.getLogger(Utilities.class);
    public static Map getTagSetFromCorpusList(Properties prop) {
        String temp = prop.getProperty("CORPORA2POS");
        List<String> mappedList = Collections.unmodifiableList(Arrays.asList(temp.split(",")));
        Map<String, String> map = new HashMap<String, String>();
        String tagset;
        String pos = "", pcorpus = "";
        //System.out.println("se.gu.spraakbanken.fcs.endpoint.korp.KorpSRUSearchResultSet.getTagSetFromCorpus() " + mappedList);
        for (String mapped : mappedList) {
            pcorpus = mapped.split("=")[0];
            pos = mapped.split("=")[1];

            //System.out.println("se.gu.spraakbanken.fcs.endpoint.korp.data.json.pojo.info.CorporaInfo.getTagSetFromCorpus() Inserting key " + pcorpus + " with value " + pos);
            map.put(pcorpus, pos);

        }
        //tagset = map.get(corpus);
        
        //LOG.info("se.gu.spraakbanken.fcs.endpoint.korp.utils.Utilities.getTagSetFromCorpus() Getting tagset " + tagset + " for value '{}'", corpus);
        
        return map;
    }
    
    public static String getTagSetFromCorpus(Properties prop, String corpus) {
        String temp = prop.getProperty("CORPORA2POS");
        List<String> mappedList = Collections.unmodifiableList(Arrays.asList(temp.split(",")));
        Map<String, String> map = new HashMap<String, String>();
        String tagset;
        String pos = "", pcorpus = "";
        //System.out.println("se.gu.spraakbanken.fcs.endpoint.korp.KorpSRUSearchResultSet.getTagSetFromCorpus() " + mappedList);
        for (String mapped : mappedList) {
            pcorpus = mapped.split("=")[0];
            pos = mapped.split("=")[1];

            //System.out.println("se.gu.spraakbanken.fcs.endpoint.korp.data.json.pojo.info.CorporaInfo.getTagSetFromCorpus() Inserting key " + pcorpus + " with value " + pos);
            map.put(pcorpus, pos);

        }
        tagset = map.get(corpus);
        LOG.info("se.gu.spraakbanken.fcs.endpoint.korp.KorpSRUSearchResultSet.getTagSetFromCorpus() Getting tagset " + tagset + " for value '{}'", corpus);

        return tagset;
    }
    
    public static String getTagSetFromCorpus(Map<String,String> map, String corpus) {
        String  tagset = map.get(corpus);
        LOG.info("se.gu.spraakbanken.fcs.endpoint.korp.KorpSRUSearchResultSet.getTagSetFromCorpus() Getting tagset " + tagset + " for value '{}'", corpus);
        System.err.println("se.gu.spraakbanken.fcs.endpoint.korp.KorpSRUSearchResultSet.getTagSetFromCorpus() Getting tagset " + tagset + " for  corpus "+corpus);
        return tagset;
    }
    
}
