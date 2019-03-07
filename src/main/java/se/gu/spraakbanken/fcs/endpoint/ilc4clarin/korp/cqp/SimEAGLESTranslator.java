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
 * This class is a simplified map between UD and EAGLES tagsets 
 * <p>There is no direct mapping for each EAGLES code to UD such as AQ0FS00 to ADJ.</p>
 * <p>Instead a more shallow mapping between the tagsets is provided: tf EAGLES pos starts with A then UD * is ADJ 
 * and something similar for the other pos.</p>
 * <p>For example when UD is VERB V.* is returned. This is useful for the search engine which executes a like search instead of an
 * exact one.</p>
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it|gmail.com&gt;
 */
public class SimEAGLESTranslator extends POSTranslator {

    private final Map<String, List<String>> TO_POS = createToPos();
    private final Map<String, List<String>> TO_UD = createToUD();

    @Override
    Map<String, List<String>> createToPos() {
        Map<String, List<String>> pos = new HashMap<String, List<String>>();

        //PUNCT
        pos.put("PUNCT", Arrays.asList("Faa", "Fat", "Fc", "Fca", "Fct", "Fd", "Fe", "Fg", "Fh", "Fia", "Fit", "Fla", "Flt", "Fp", "Fpa", "Fpt", "Fra", "Frc", "Fs", "Ft", "Fx", "Fz"));

        //ADJ
        pos.put("ADJ", Arrays.asList("A.*"));
        //pos.put("ADJ", Arrays.asList("AQ0FS00","AO0CN0","AO0CNP"));
        //NOUN
        pos.put("NOUN", Arrays.asList("NC.*"));

        //PROPN
        pos.put("PROPN", Arrays.asList("NP.*"));

        //AUX
        pos.put("AUX", Arrays.asList("VA.*", "VS.*"));

        //VERB
        pos.put("VERB", Arrays.asList("VM.*"));

        //CC & CS
        pos.put("CCONJ", Arrays.asList("CC"));
        pos.put("SCONJ", Arrays.asList("CS"));

        //INTJ
        pos.put("INTJ", Arrays.asList("I"));

        // ADV        
        pos.put("ADV", Arrays.asList("RG", "RN"));

        // ADP
        pos.put("ADP", Arrays.asList("SP.*"));

        //DET
        pos.put("DET", Arrays.asList("D.*"));

        //NUM
        pos.put("NUM", Arrays.asList("Zd", "Zm", "Z", "Zp", "Zu"));

        //PRON
        pos.put("PRON", Arrays.asList("P.*"));

        // PART TO DO
        //X
        //X
        pos.put("X", Arrays.asList("W"));

        return Collections.unmodifiableMap(pos);
    }

    @Override
    Map<String, List<String>> createToUD() {
        Map<String, List<String>> ud = new HashMap<String, List<String>>();
        // fixme! - check lemma/msd for toUd17
        //PUNCT
        ud.put("Faa", Arrays.asList("PUNCT"));
        ud.put("Fat", Arrays.asList("PUNCT"));
        ud.put("Fc", Arrays.asList("PUNCT"));
        ud.put("Fca", Arrays.asList("PUNCT"));
        ud.put("Fct", Arrays.asList("PUNCT"));
        ud.put("Fd", Arrays.asList("PUNCT"));
        ud.put("Fe", Arrays.asList("PUNCT"));
        ud.put("Fg", Arrays.asList("PUNCT"));
        ud.put("Fh", Arrays.asList("PUNCT"));
        ud.put("Fia", Arrays.asList("PUNCT"));
        ud.put("Fit", Arrays.asList("PUNCT"));
        ud.put("Fla", Arrays.asList("PUNCT"));
        ud.put("Flt", Arrays.asList("PUNCT"));
        ud.put("Fp", Arrays.asList("PUNCT"));
        ud.put("Fpa", Arrays.asList("PUNCT"));
        ud.put("Fpt", Arrays.asList("PUNCT"));
        ud.put("Fra", Arrays.asList("PUNCT"));
        ud.put("Frc", Arrays.asList("PUNCT"));
        ud.put("Fs", Arrays.asList("PUNCT"));
        ud.put("Ft", Arrays.asList("PUNCT"));
        ud.put("Fx", Arrays.asList("PUNCT"));
        ud.put("Fz", Arrays.asList("PUNCT"));

        // ADJ
        ud.put("A", Arrays.asList("ADJ"));

        //NOUN
        ud.put("NC", Arrays.asList("NOUN"));

        //PROPN
        ud.put("NP", Arrays.asList("PROPN"));

        // AUX
        ud.put("VA", Arrays.asList("AUX"));
        ud.put("VS", Arrays.asList("AUX"));

        //INTJ
        ud.put("I", Arrays.asList("INTJ"));

        // VERB
        ud.put("VM", Arrays.asList("VERB"));

        // ADV
        ud.put("R", Arrays.asList("ADV"));

        // ADP
        ud.put("SP", Arrays.asList("ADP"));

        //CC & CS
        ud.put("CC", Arrays.asList("CCONJ"));
        ud.put("CS", Arrays.asList("SCONJ"));

        // DET
        ud.put("D", Arrays.asList("DET"));

        //NUM
        ud.put("Z", Arrays.asList("NUM"));

        // PART TO DO
        // PRON
        ud.put("P", Arrays.asList("PRON"));

        //X
        ud.put("W", Arrays.asList("X"));

        return Collections.unmodifiableMap(ud);
    }

    @Override
    public List<String> fromPos(String pos) throws SRUException {
        List<String> res = null;
        if (pos.length()>=2)
            pos=pos.substring(0,2);
        else
            pos=pos.substring(0,1);
        if (pos.startsWith("A")) {
            pos = "A";
        } else if (pos.startsWith("D")) {
            pos = "D";
        } else if (pos.startsWith("I")) {
            pos = "I";
        } else if (pos.startsWith("P")) {
            pos = "P";
        }else if (pos.startsWith("W")) {
            pos = "W";
        } else if (pos.startsWith("Z")) {
            pos = "Z";
        } else if (pos.startsWith("R")) {
            pos = "R";
        }
        
        res = TO_UD.get(pos.toUpperCase());
        if (res == null) {
            throw new SRUException(
                    SRUConstants.SRU_CANNOT_PROCESS_QUERY_REASON_UNKNOWN,
                    "unknown PoS code from search engine: " + pos);
        }
        return res;
    }

    @Override
    public List<String> toPos(String ud) throws SRUException {
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
