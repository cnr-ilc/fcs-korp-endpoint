/**
 * Added to the Forked from https://github.com/clarin-eric/fcs-korp-endpoint
 * @license http://www.gnu.org/licenses/gpl-3.0.txt
 *  GNU General Public License v3
 */
package se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.cqp;

import eu.clarin.sru.server.SRUException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it|gmail.com&gt;
 */
public abstract class POSTranslator {
   
    /**
     * 
     * @return the map between UD and pos
     */
    abstract Map<String, List<String>> createToPos();
    
    /**
     * 
     * @return the map between pos and UD
     */
    abstract Map<String, List<String>> createToUD();
    
    /**
     * 
     * @param pos incoming pos
     * @return the list of UD mapped on pos
     * @throws SRUException the exception if no pos is retrieved
     */
    public abstract  List<String> fromPos(String pos) throws SRUException;
    
    /**
     * 
     * @param ud incoming ud
     * @return the list of pos mapped on ud
     * @throws SRUException SRUException the exception if no pos is retrieved
     */
    public abstract List<String> toPos(String ud) throws SRUException;
    
}
