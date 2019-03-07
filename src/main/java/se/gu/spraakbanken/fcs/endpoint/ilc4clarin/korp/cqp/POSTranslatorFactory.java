/**
 * Added to the Forked from https://github.com/clarin-eric/fcs-korp-endpoint
 * @license http://www.gnu.org/licenses/gpl-3.0.txt
 *  GNU General Public License v3
 */
package se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.cqp;

import eu.clarin.sru.server.SRUConstants;
import eu.clarin.sru.server.SRUException;
import java.util.HashMap;
import java.util.Map;

/**
 * This Factory allows the program to instantiate the correct {@link POSTranslator} according
 * to the pos tagset.
 * The relation between corpora and encoded pos tagset is on the property file: property: CORPORA2POS
 * It is very useful to model the behavior of the code according to the pos. 
 * The Search Engine is no more limited to one set
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it|gmail.com&gt;
 */
public class POSTranslatorFactory {

    private static Map<String, POSTranslator> mapTranslator = new HashMap<String, POSTranslator>();

    static {
        mapTranslator.put("EAGLES", new EAGLESTranslator());
        mapTranslator.put("UD", new UDTranslator());
        mapTranslator.put("S-EAGLES", new SimEAGLESTranslator());
    }
    
    /**
     * Get the correct translator
     * @param tagset the pos tagset
     * @return the correct translator for the input tagset
     * @throws SRUException if no valid translator is found
     */
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
