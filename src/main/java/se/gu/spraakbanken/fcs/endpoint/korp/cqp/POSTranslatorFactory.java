/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.gu.spraakbanken.fcs.endpoint.korp.cqp;

import eu.clarin.sru.server.SRUConstants;
import eu.clarin.sru.server.SRUException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class POSTranslatorFactory {

    private static Map<String, POSTranslator> mapTranslator = new HashMap<String, POSTranslator>();

    static {
        mapTranslator.put("EAGLES", new EAGLESTranslator());
        mapTranslator.put("UD", new UDTranslator());
        mapTranslator.put("S-EAGLES", new SimEAGLESTranslator());
    }
    
    public static POSTranslator getPOSTranslator(String tagset) throws SRUException{
        POSTranslator posTranslator=mapTranslator.get(tagset);
        if (posTranslator == null){
            throw new SRUException(
				   SRUConstants.SRU_GENERAL_SYSTEM_ERROR,
				   "unknown Translator for tagset: " + tagset);
        }
        return posTranslator;
    }

}
