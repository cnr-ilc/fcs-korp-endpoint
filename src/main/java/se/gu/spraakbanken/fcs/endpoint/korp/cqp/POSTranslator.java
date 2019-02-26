/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.gu.spraakbanken.fcs.endpoint.korp.cqp;

import eu.clarin.sru.server.SRUException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public abstract class POSTranslator {
   
    abstract Map<String, List<String>> createToPos();
    abstract Map<String, List<String>> createToUD();
    
    public abstract  List<String> fromPos(String pos) throws SRUException;
    public abstract List<String> toPos(String ud) throws SRUException;
    
}
