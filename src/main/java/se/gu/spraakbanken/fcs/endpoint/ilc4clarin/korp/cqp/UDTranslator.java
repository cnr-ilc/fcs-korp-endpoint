/**
 * Added to the Forked from https://github.com/clarin-eric/fcs-korp-endpoint
 * @license http://www.gnu.org/licenses/gpl-3.0.txt
 *  GNU General Public License v3
 */
package se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.cqp;

import eu.clarin.sru.server.SRUConstants;
import eu.clarin.sru.server.SRUException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class really does nothing
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class UDTranslator extends POSTranslator {
    private final Map<String, List<String>> TO_POS = createToPos();
    private final Map<String, List<String>> TO_UD = createToUD();

    @Override
    Map<String, List<String>> createToPos() {
        Map<String, List<String>> pos = new HashMap<String, List<String>>();
        pos.put("NOUN", Arrays.asList("NOUN"));
        pos.put("PROPN", Arrays.asList("PROPN"));
        pos.put("ADJ", Arrays.asList("ADJ"));
        pos.put("VERB", Arrays.asList("VERB"));
        pos.put("AUX", Arrays.asList("AUX"));
        pos.put("NUM", Arrays.asList("NUM")); 
        pos.put("PRON", Arrays.asList("PRON")); 
        pos.put("DET", Arrays.asList("DET"));
        pos.put("PART", Arrays.asList("PART"));
        pos.put("ADV", Arrays.asList("ADV")); 
        pos.put("ADP", Arrays.asList("ADP")); 
        pos.put("CCONJ", Arrays.asList("CCONJ"));
        pos.put("SCONJ", Arrays.asList("SCONJ"));
        pos.put("INTJ", Arrays.asList("INTJ"));
        pos.put("PUNCT", Arrays.asList("PUNCT"));
        pos.put("X", Arrays.asList("X"));
        return Collections.unmodifiableMap(pos);
    }

    @Override
    Map<String, List<String>> createToUD() {
        Map<String, List<String>> ud = new HashMap<String, List<String>>();
        ud.put("NOUN", Arrays.asList("NOUN"));
        ud.put("PROPN", Arrays.asList("PROPN"));
        ud.put("ADJ", Arrays.asList("ADJ"));
        ud.put("VERB", Arrays.asList("VERB"));
        ud.put("AUX", Arrays.asList("AUX"));
        ud.put("NUM", Arrays.asList("NUM")); 
        ud.put("PRON", Arrays.asList("PRON")); 
        ud.put("DET", Arrays.asList("DET"));
        ud.put("PART", Arrays.asList("PART"));
        ud.put("ADV", Arrays.asList("ADV")); 
        ud.put("ADP", Arrays.asList("ADP")); 
        ud.put("CCONJ", Arrays.asList("CCONJ"));
        ud.put("SCONJ", Arrays.asList("SCONJ"));
        ud.put("INTJ", Arrays.asList("INTJ"));
        ud.put("PUNCT", Arrays.asList("PUNCT"));
        ud.put("X", Arrays.asList("X"));
        return Collections.unmodifiableMap(ud);
    }

    @Override
    public List<String> fromPos(String pos)throws SRUException {
        
       
        List<String> res = null;
	
	res = TO_UD.get(pos.toUpperCase());
	if (res == null) {
	    throw new SRUException(
				   SRUConstants.SRU_CANNOT_PROCESS_QUERY_REASON_UNKNOWN,
				   "unknown PoS code from search engine: " + pos);
	}
	return res;
    }

    @Override
    public List<String> toPos(String ud) throws SRUException{
        List<String> res = null;
        res = TO_POS.get(ud.toUpperCase());
	
	if (res == null) {
	    throw new SRUException(
				   SRUConstants.SRU_QUERY_SYNTAX_ERROR,
				   "unknown UD V2.0 PoS code in query: " + ud);
	}
	return res;
    }

}
