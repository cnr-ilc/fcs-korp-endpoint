/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.gu.spraakbanken.fcs.endpoint.korp.cqp;

import eu.clarin.sru.server.SRUConstants;
import eu.clarin.sru.server.SRUException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class EAGLESTranslator extends POSTranslator {

    private final Map<String, List<String>> TO_POS = createToPos();
    private final Map<String, List<String>> TO_UD = createToUD();

    @Override
    Map<String, List<String>> createToPos() {
        Map<String, List<String>> pos = new HashMap<String, List<String>>();
        
        //PUNCT
        pos.put("PUNCT", Arrays.asList("Faa","Fat","Fc","Fca","Fct","Fd","Fe","Fg","Fh","Fia","Fit","Fla","Flt","Fp","Fpa","Fpt","Fra","Frc","Fs","Ft","Fx","Fz"));
        
        //ADJ
        pos.put("ADJ", Arrays.asList("AO0CN0","AO0CNP","AO0CP0","AO0CPP","AO0CS0","AO0CSP","AO0FN0","AO0FNP","AO0FP0","AO0FPP","AO0FS0","AO0FSP","AO0MN0","AO0MNP","AO0MP0","AO0MPP","AO0MS0","AO0MSP","AOACN0","AOACNP","AOACP0","AOACPP","AOACS0","AOACSP","AOAFN0","AOAFNP","AOAFP0","AOAFPP","AOAFS0","AOAFSP","AOAMN0","AOAMNP","AOAMP0","AOAMPP","AOAMS0","AOAMSP","AOCCN0","AOCCNP","AOCCP0","AOCCPP","AOCCS0","AOCCSP","AOCFN0","AOCFNP","AOCFP0","AOCFPP","AOCFS0","AOCFSP","AOCMN0","AOCMNP","AOCMP0","AOCMPP","AOCMS0","AOCMSP","AODCN0","AODCNP","AODCP0","AODCPP","AODCS0","AODCSP","AODFN0","AODFNP","AODFP0","AODFPP","AODFS0","AODFSP","AODMN0","AODMNP","AODMP0","AODMPP","AODMS0","AODMSP","AOSCN0","AOSCNP","AOSCP0","AOSCPP","AOSCS0","AOSCSP","AOSFN0","AOSFNP","AOSFP0","AOSFPP","AOSFS0","AOSFSP","AOSMN0","AOSMNP","AOSMP0","AOSMPP","AOSMS0","AOSMSP","AQ0CC0","AQ0CN0","AQ0CNP","AQ0CP0","AQ0CPP","AQ0CS0","AQ0CSP","AQ0FN0","AQ0FNP","AQ0FP0","AQ0FPP","AQ0FS0","AQ0FSP","AQ0MN0","AQ0MNP","AQ0MP0","AQ0MPP","AQ0MS0","AQ0MSP","AQACN0","AQACNP","AQACP0","AQACPP","AQACS0","AQACSP","AQAFN0","AQAFNP","AQAFP0","AQAFPP","AQAFS0","AQAFSP","AQAMN0","AQAMNP","AQAMP0","AQAMPP","AQAMS0","AQAMSP","AQCCN0","AQCCNP","AQCCP0","AQCCPP","AQCCS0","AQCCSP","AQCFN0","AQCFNP","AQCFP0","AQCFPP","AQCFS0","AQCFSP","AQCMN0","AQCMNP","AQCMP0","AQCMPP","AQCMS0","AQCMSP","AQDCN0","AQDCNP","AQDCP0","AQDCPP","AQDCS0","AQDCSP","AQDFN0","AQDFNP","AQDFP0","AQDFPP","AQDFS0","AQDFSP","AQDMN0","AQDMNP","AQDMP0","AQDMPP","AQDMS0","AQDMSP","AQSCN0","AQSCNP","AQSCP0","AQSCPP","AQSCS0","AQSCSP","AQSFN0","AQSFNP","AQSFP0","AQSFPP","AQSFS0","AQSFSP","AQSMN0","AQSMNP","AQSMP0","AQSMPP","AQSMS0","AQSMSP"));
        
        //NOUN
        pos.put("NOUN", Arrays.asList("NCCC000","NCCP000","NCCS000","NCFC000","NCFN000","NCFP000","NCFP00A","NCFP00D","NCFS000","NCFS00A","NCFS00D","NCMC000","NCMN000","NCMP000","NCMP00A","NCMP00D","NCMS000","NCMS00A","NCMS00D"));
        
        //PROPN
        pos.put("PROP", Arrays.asList("NP00000","NP00G00","NP00O00","NP00SP0","NP00V00"));
       
        //AUX
        pos.put("AUX", Arrays.asList("VAG0000","VAIC1P0","VAIC1S0","VAIC2P0","VAIC2S0","VAIC3P0","VAIC3S0","VAIF1P0","VAIF1S0","VAIF2P0","VAIF2S0","VAIF3P0","VAIF3S0","VAII1P0","VAII1S0","VAII2P0","VAII2S0","VAII3P0","VAII3S0","VAIP1P0","VAIP1S0","VAIP2P0","VAIP2S0","VAIP3P0","VAIP3S0","VAIS1P0","VAIS1S0","VAIS2P0","VAIS2S0","VAIS3P0","VAIS3S0","VAM01P0","VAM02P0","VAM02S0","VAM03P0","VAM03S0","VAN0000","VAP00PF","VAP00PM","VAP00SF","VAP00SM","VASF1P0","VASF1S0","VASF2P0","VASF2S0","VASF3P0","VASF3S0","VASI1P0","VASI1S0","VASI2P0","VASI2S0","VASI3P0","VASI3S0","VASP1P0","VASP1S0","VASP2P0","VASP2S0","VASP3P0","VASP3S0","VSG0000","VSIC1P0","VSIC1S0","VSIC2P0","VSIC2S0","VSIC3P0","VSIC3S0","VSIF1P0","VSIF1S0","VSIF2P0","VSIF2S0","VSIF3P0","VSIF3S0","VSII1P0","VSII1S0","VSII2P0","VSII2S0","VSII3P0","VSII3S0","VSIP1P0","VSIP1S0","VSIP2P0","VSIP2S0","VSIP3P0","VSIP3S0","VSIS1P0","VSIS1S0","VSIS2P0","VSIS2S0","VSIS3P0","VSIS3S0","VSM01P0","VSM02P0","VSM02S0","VSM03P0","VSM03S0","VSN0000","VSP00SM","VSSF1P0","VSSF1S0","VSSF2P0","VSSF2S0","VSSF3P0","VSSF3S0","VSSI1P0","VSSI1S0","VSSI2P0","VSSI2S0","VSSI3P0","VSSI3S0","VSSP1P0","VSSP1S0","VSSP2P0","VSSP2S0","VSSP3P0","VSSP3S0"));
        
        //VERB
        pos.put("VERB", Arrays.asList("VMSF1P0","VMSF1S0","VMSF2P0","VMSF2S0","VMSF3P0","VMSF3S0","VMSI1P0","VMSI1S0","VMSI2P0","VMSI2S0","VMSI3P0","VMSI3S0","VMSP1P0","VMSP1S0","VMSP2P0","VMSP2S0","VMSP3P0","VMSP3S0","VMG0000","VMIC1P0","VMIC1S0","VMIC2P0","VMIC2S0","VMIC3P0","VMIC3S0","VMIF1P0","VMIF1S0","VMIF2P0","VMIF2S0","VMIF3P0","VMIF3S0","VMII1P0","VMII1S0","VMII2P0","VMII2S0","VMII3P0","VMII3S0","VMIP1P0","VMIP1S0","VMIP2P0","VMIP2S0","VMIP3P0","VMIP3PC","VMIP3S0","VMIS1P0","VMIS1S0","VMIS2P0","VMIS2S0","VMIS3P0","VMIS3S0","VMM01P0","VMM02P0","VMM02S0","VMM03P0","VMM03S0","VMN0000","VMN0000","VMP00PF","VMP00PM","VMP00SF","VMP00SM","VMSF1P0","VMSF1S0","VMSF2P0","VMSF2S0","VMSF3P0","VMSF3S0","VMSI1P0","VMSI1S0","VMSI2P0","VMSI2S0","VMSI3P0","VMSI3S0","VMSP1P0","VMSP1S0","VMSP2P0","VMSP2S0","VMSP3P0","VMSP3S0"));
        
        //CC & CS
        pos.put("CCONJ", Arrays.asList("CC"));
        pos.put("SCONJ", Arrays.asList("CS"));
        
        //INTJ
        pos.put("INTJ", Arrays.asList("I"));
        
        // ADV        
        pos.put("ADV", Arrays.asList("RG", "RN"));
       

        // ADP
        pos.put("ADP", Arrays.asList("SPCMS", "SPCMP","SPCFS","SPCFP","SPS00"));
        
        //DET
        pos.put("DET", Arrays.asList("DA0CS0","DA0FP0","DA0FS0","DA0MP0","DA0MS0","DA0NS0","DD0CP0","DD0CS0","DD0FP0","DD0FS0","DD0MP0","DD0MS0","DE0CC0","DE0CN0","DE0FP0","DE0FS0","DE0MP0","DE0MS0","DI0CN0","DI0CP0","DI0CS0","DI0FP0","DI0FS0","DI0MP0","DI0MS0","DN0CP0","DN0FP0","DN0FS0","DP1CPS","DP1CSS","DP1FPP","DP1FSP","DP1MPP","DP1MSP","DP2CPS","DP2CSS","DP2FPP","DP2FSP","DP2MPP","DP2MSP","DP3CP0","DP3CS0","DT0CN0","DT0FP0","DT0FS0","DT0MP0","DT0MS0"));
        
        //NUM
        pos.put("NUM", Arrays.asList("Zd","Zm","Z","Zp","Zu"));
        
        //PRON
        pos.put("PRON", Arrays.asList("P00CC000","P00CN000","P03CC000","PD0CP000","PD0CS000","PD0FP000","PD0FS000","PD0MP000","PD0MS000","PD0NS000","PE000000","PI0CP000","PI0CS000","PI0FP000","PI0FS000","PI0MP000","PI0MS000","PN0MS000","PP1CP000","PP1CS000","PP1CSN00","PP1CSO00","PP1FP000","PP1MP000","PP2CNO00","PP2CP000","PP2CP00P","PP2CS000","PP2CS00P","PP2CSN00","PP2CSO00","PP2FP000","PP2MP000","PP3CCO00","PP3CN000","PP3CN00P","PP3CNA00","PP3CNO00","PP3CPD00","PP3CS000","PP3CSD00","PP3FP000","PP3FPA00","PP3FS000","PP3FSA00","PP3MP000","PP3MPA00","PP3MS000","PP3MSA00","PP3NS000","PR000000","PR0CC000","PR0CN000","PR0CP000","PR0CS000","PR0FP000","PR0FS000","PR0MP000","PR0MS000","PT000000","PT0CP000","PT0CS000","PT0FP000","PT0FS000","PT0MP000","PT0MS000","PX1FP0P0","PX1FP0S0","PX1FS0P0","PX1FS0S0","PX1MP0P0","PX1MP0S0","PX1MS0P0","PX1MS0S0","PX1NS0P0","PX1NS0S0","PX2FP0P0","PX2FP0S0","PX2FS0P0","PX2FS0S0","PX2MP0P0","PX2MP0S0","PX2MS0P0","PX2MS0S0","PX2NS0P0","PX2NS0S0","PX3FP000","PX3FS000","PX3MP000","PX3MS000","PX3NS000"));
        
        // PART TO DO
        
        
        //X
        //X
        pos.put("X", Arrays.asList("W"));
        
//        pos.put("NOUN", Arrays.asList("NN"));
//        pos.put("PROPN", Arrays.asList("PM"));
//        pos.put("ADJ", Arrays.asList("JJ", "PC", "RO"));
//        pos.put("VERB", Arrays.asList("VB", "PC"));
//        pos.put("AUX", Arrays.asList("VB"));
//        pos.put("NUM", Arrays.asList("RG", "RO")); // No RO?
//        pos.put("PRON", Arrays.asList("PN", "PS", "HP", "HS")); // No PS, HS?
//        pos.put("DET", Arrays.asList("DT", "HD", "HS", "PS"));
//        pos.put("PART", Arrays.asList("IE"));
//        pos.put("ADV", Arrays.asList("AB", "HA", "PL")); // No PL?
//        pos.put("ADP", Arrays.asList("PL", "PP")); // No PL?
//        pos.put("CCONJ", Arrays.asList("KN"));
//        pos.put("SCONJ", Arrays.asList("SN"));
//        pos.put("INTJ", Arrays.asList("IN"));
//        pos.put("PUNCT", Arrays.asList("MAD", "MID", "PAD"));
//        pos.put("X", Arrays.asList("UO"));
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
        ud.put("AO0CN0", Arrays.asList("ADJ"));
        ud.put("AO0CNP", Arrays.asList("ADJ"));
        ud.put("AO0CP0", Arrays.asList("ADJ"));
        ud.put("AO0CPP", Arrays.asList("ADJ"));
        ud.put("AO0CS0", Arrays.asList("ADJ"));
        ud.put("AO0CSP", Arrays.asList("ADJ"));
        ud.put("AO0FN0", Arrays.asList("ADJ"));
        ud.put("AO0FNP", Arrays.asList("ADJ"));
        ud.put("AO0FP0", Arrays.asList("ADJ"));
        ud.put("AO0FPP", Arrays.asList("ADJ"));
        ud.put("AO0FS0", Arrays.asList("ADJ"));
        ud.put("AO0FSP", Arrays.asList("ADJ"));
        ud.put("AO0MN0", Arrays.asList("ADJ"));
        ud.put("AO0MNP", Arrays.asList("ADJ"));
        ud.put("AO0MP0", Arrays.asList("ADJ"));
        ud.put("AO0MPP", Arrays.asList("ADJ"));
        ud.put("AO0MS0", Arrays.asList("ADJ"));
        ud.put("AO0MSP", Arrays.asList("ADJ"));
        ud.put("AOACN0", Arrays.asList("ADJ"));
        ud.put("AOACNP", Arrays.asList("ADJ"));
        ud.put("AOACP0", Arrays.asList("ADJ"));
        ud.put("AOACPP", Arrays.asList("ADJ"));
        ud.put("AOACS0", Arrays.asList("ADJ"));
        ud.put("AOACSP", Arrays.asList("ADJ"));
        ud.put("AOAFN0", Arrays.asList("ADJ"));
        ud.put("AOAFNP", Arrays.asList("ADJ"));
        ud.put("AOAFP0", Arrays.asList("ADJ"));
        ud.put("AOAFPP", Arrays.asList("ADJ"));
        ud.put("AOAFS0", Arrays.asList("ADJ"));
        ud.put("AOAFSP", Arrays.asList("ADJ"));
        ud.put("AOAMN0", Arrays.asList("ADJ"));
        ud.put("AOAMNP", Arrays.asList("ADJ"));
        ud.put("AOAMP0", Arrays.asList("ADJ"));
        ud.put("AOAMPP", Arrays.asList("ADJ"));
        ud.put("AOAMS0", Arrays.asList("ADJ"));
        ud.put("AOAMSP", Arrays.asList("ADJ"));
        ud.put("AOCCN0", Arrays.asList("ADJ"));
        ud.put("AOCCNP", Arrays.asList("ADJ"));
        ud.put("AOCCP0", Arrays.asList("ADJ"));
        ud.put("AOCCPP", Arrays.asList("ADJ"));
        ud.put("AOCCS0", Arrays.asList("ADJ"));
        ud.put("AOCCSP", Arrays.asList("ADJ"));
        ud.put("AOCFN0", Arrays.asList("ADJ"));
        ud.put("AOCFNP", Arrays.asList("ADJ"));
        ud.put("AOCFP0", Arrays.asList("ADJ"));
        ud.put("AOCFPP", Arrays.asList("ADJ"));
        ud.put("AOCFS0", Arrays.asList("ADJ"));
        ud.put("AOCFSP", Arrays.asList("ADJ"));
        ud.put("AOCMN0", Arrays.asList("ADJ"));
        ud.put("AOCMNP", Arrays.asList("ADJ"));
        ud.put("AOCMP0", Arrays.asList("ADJ"));
        ud.put("AOCMPP", Arrays.asList("ADJ"));
        ud.put("AOCMS0", Arrays.asList("ADJ"));
        ud.put("AOCMSP", Arrays.asList("ADJ"));
        ud.put("AODCN0", Arrays.asList("ADJ"));
        ud.put("AODCNP", Arrays.asList("ADJ"));
        ud.put("AODCP0", Arrays.asList("ADJ"));
        ud.put("AODCPP", Arrays.asList("ADJ"));
        ud.put("AODCS0", Arrays.asList("ADJ"));
        ud.put("AODCSP", Arrays.asList("ADJ"));
        ud.put("AODFN0", Arrays.asList("ADJ"));
        ud.put("AODFNP", Arrays.asList("ADJ"));
        ud.put("AODFP0", Arrays.asList("ADJ"));
        ud.put("AODFPP", Arrays.asList("ADJ"));
        ud.put("AODFS0", Arrays.asList("ADJ"));
        ud.put("AODFSP", Arrays.asList("ADJ"));
        ud.put("AODMN0", Arrays.asList("ADJ"));
        ud.put("AODMNP", Arrays.asList("ADJ"));
        ud.put("AODMP0", Arrays.asList("ADJ"));
        ud.put("AODMPP", Arrays.asList("ADJ"));
        ud.put("AODMS0", Arrays.asList("ADJ"));
        ud.put("AODMSP", Arrays.asList("ADJ"));
        ud.put("AOSCN0", Arrays.asList("ADJ"));
        ud.put("AOSCNP", Arrays.asList("ADJ"));
        ud.put("AOSCP0", Arrays.asList("ADJ"));
        ud.put("AOSCPP", Arrays.asList("ADJ"));
        ud.put("AOSCS0", Arrays.asList("ADJ"));
        ud.put("AOSCSP", Arrays.asList("ADJ"));
        ud.put("AOSFN0", Arrays.asList("ADJ"));
        ud.put("AOSFNP", Arrays.asList("ADJ"));
        ud.put("AOSFP0", Arrays.asList("ADJ"));
        ud.put("AOSFPP", Arrays.asList("ADJ"));
        ud.put("AOSFS0", Arrays.asList("ADJ"));
        ud.put("AOSFSP", Arrays.asList("ADJ"));
        ud.put("AOSMN0", Arrays.asList("ADJ"));
        ud.put("AOSMNP", Arrays.asList("ADJ"));
        ud.put("AOSMP0", Arrays.asList("ADJ"));
        ud.put("AOSMPP", Arrays.asList("ADJ"));
        ud.put("AOSMS0", Arrays.asList("ADJ"));
        ud.put("AOSMSP", Arrays.asList("ADJ"));
        ud.put("AQ0CC0", Arrays.asList("ADJ"));
        ud.put("AQ0CN0", Arrays.asList("ADJ"));
        ud.put("AQ0CNP", Arrays.asList("ADJ"));
        ud.put("AQ0CP0", Arrays.asList("ADJ"));
        ud.put("AQ0CPP", Arrays.asList("ADJ"));
        ud.put("AQ0CS0", Arrays.asList("ADJ"));
        ud.put("AQ0CSP", Arrays.asList("ADJ"));
        ud.put("AQ0FN0", Arrays.asList("ADJ"));
        ud.put("AQ0FNP", Arrays.asList("ADJ"));
        ud.put("AQ0FP0", Arrays.asList("ADJ"));
        ud.put("AQ0FPP", Arrays.asList("ADJ"));
        ud.put("AQ0FS0", Arrays.asList("ADJ"));
        ud.put("AQ0FSP", Arrays.asList("ADJ"));
        ud.put("AQ0MN0", Arrays.asList("ADJ"));
        ud.put("AQ0MNP", Arrays.asList("ADJ"));
        ud.put("AQ0MP0", Arrays.asList("ADJ"));
        ud.put("AQ0MPP", Arrays.asList("ADJ"));
        ud.put("AQ0MS0", Arrays.asList("ADJ"));
        ud.put("AQ0MSP", Arrays.asList("ADJ"));
        ud.put("AQACN0", Arrays.asList("ADJ"));
        ud.put("AQACNP", Arrays.asList("ADJ"));
        ud.put("AQACP0", Arrays.asList("ADJ"));
        ud.put("AQACPP", Arrays.asList("ADJ"));
        ud.put("AQACS0", Arrays.asList("ADJ"));
        ud.put("AQACSP", Arrays.asList("ADJ"));
        ud.put("AQAFN0", Arrays.asList("ADJ"));
        ud.put("AQAFNP", Arrays.asList("ADJ"));
        ud.put("AQAFP0", Arrays.asList("ADJ"));
        ud.put("AQAFPP", Arrays.asList("ADJ"));
        ud.put("AQAFS0", Arrays.asList("ADJ"));
        ud.put("AQAFSP", Arrays.asList("ADJ"));
        ud.put("AQAMN0", Arrays.asList("ADJ"));
        ud.put("AQAMNP", Arrays.asList("ADJ"));
        ud.put("AQAMP0", Arrays.asList("ADJ"));
        ud.put("AQAMPP", Arrays.asList("ADJ"));
        ud.put("AQAMS0", Arrays.asList("ADJ"));
        ud.put("AQAMSP", Arrays.asList("ADJ"));
        ud.put("AQCCN0", Arrays.asList("ADJ"));
        ud.put("AQCCNP", Arrays.asList("ADJ"));
        ud.put("AQCCP0", Arrays.asList("ADJ"));
        ud.put("AQCCPP", Arrays.asList("ADJ"));
        ud.put("AQCCS0", Arrays.asList("ADJ"));
        ud.put("AQCCSP", Arrays.asList("ADJ"));
        ud.put("AQCFN0", Arrays.asList("ADJ"));
        ud.put("AQCFNP", Arrays.asList("ADJ"));
        ud.put("AQCFP0", Arrays.asList("ADJ"));
        ud.put("AQCFPP", Arrays.asList("ADJ"));
        ud.put("AQCFS0", Arrays.asList("ADJ"));
        ud.put("AQCFSP", Arrays.asList("ADJ"));
        ud.put("AQCMN0", Arrays.asList("ADJ"));
        ud.put("AQCMNP", Arrays.asList("ADJ"));
        ud.put("AQCMP0", Arrays.asList("ADJ"));
        ud.put("AQCMPP", Arrays.asList("ADJ"));
        ud.put("AQCMS0", Arrays.asList("ADJ"));
        ud.put("AQCMSP", Arrays.asList("ADJ"));
        ud.put("AQDCN0", Arrays.asList("ADJ"));
        ud.put("AQDCNP", Arrays.asList("ADJ"));
        ud.put("AQDCP0", Arrays.asList("ADJ"));
        ud.put("AQDCPP", Arrays.asList("ADJ"));
        ud.put("AQDCS0", Arrays.asList("ADJ"));
        ud.put("AQDCSP", Arrays.asList("ADJ"));
        ud.put("AQDFN0", Arrays.asList("ADJ"));
        ud.put("AQDFNP", Arrays.asList("ADJ"));
        ud.put("AQDFP0", Arrays.asList("ADJ"));
        ud.put("AQDFPP", Arrays.asList("ADJ"));
        ud.put("AQDFS0", Arrays.asList("ADJ"));
        ud.put("AQDFSP", Arrays.asList("ADJ"));
        ud.put("AQDMN0", Arrays.asList("ADJ"));
        ud.put("AQDMNP", Arrays.asList("ADJ"));
        ud.put("AQDMP0", Arrays.asList("ADJ"));
        ud.put("AQDMPP", Arrays.asList("ADJ"));
        ud.put("AQDMS0", Arrays.asList("ADJ"));
        ud.put("AQDMSP", Arrays.asList("ADJ"));
        ud.put("AQSCN0", Arrays.asList("ADJ"));
        ud.put("AQSCNP", Arrays.asList("ADJ"));
        ud.put("AQSCP0", Arrays.asList("ADJ"));
        ud.put("AQSCPP", Arrays.asList("ADJ"));
        ud.put("AQSCS0", Arrays.asList("ADJ"));
        ud.put("AQSCSP", Arrays.asList("ADJ"));
        ud.put("AQSFN0", Arrays.asList("ADJ"));
        ud.put("AQSFNP", Arrays.asList("ADJ"));
        ud.put("AQSFP0", Arrays.asList("ADJ"));
        ud.put("AQSFPP", Arrays.asList("ADJ"));
        ud.put("AQSFS0", Arrays.asList("ADJ"));
        ud.put("AQSFSP", Arrays.asList("ADJ"));
        ud.put("AQSMN0", Arrays.asList("ADJ"));
        ud.put("AQSMNP", Arrays.asList("ADJ"));
        ud.put("AQSMP0", Arrays.asList("ADJ"));
        ud.put("AQSMPP", Arrays.asList("ADJ"));
        ud.put("AQSMS0", Arrays.asList("ADJ"));
        ud.put("AQSMSP", Arrays.asList("ADJ"));

        //NOUN
        ud.put("NCCC000", Arrays.asList("NOUN"));
        ud.put("NCCP000", Arrays.asList("NOUN"));
        ud.put("NCCS000", Arrays.asList("NOUN"));
        ud.put("NCFC000", Arrays.asList("NOUN"));
        ud.put("NCFN000", Arrays.asList("NOUN"));
        ud.put("NCFP000", Arrays.asList("NOUN"));
        ud.put("NCFP00A", Arrays.asList("NOUN"));
        ud.put("NCFP00D", Arrays.asList("NOUN"));
        ud.put("NCFS000", Arrays.asList("NOUN"));
        ud.put("NCFS00A", Arrays.asList("NOUN"));
        ud.put("NCFS00D", Arrays.asList("NOUN"));
        ud.put("NCMC000", Arrays.asList("NOUN"));
        ud.put("NCMN000", Arrays.asList("NOUN"));
        ud.put("NCMP000", Arrays.asList("NOUN"));
        ud.put("NCMP00A", Arrays.asList("NOUN"));
        ud.put("NCMP00D", Arrays.asList("NOUN"));
        ud.put("NCMS000", Arrays.asList("NOUN"));
        ud.put("NCMS00A", Arrays.asList("NOUN"));
        ud.put("NCMS00D", Arrays.asList("NOUN"));

        //PROPN
        ud.put("NP00G00", Arrays.asList("PROPN"));
        ud.put("NP00O00", Arrays.asList("PROPN"));
        ud.put("NP00SP0", Arrays.asList("PROPN"));
        ud.put("NP00V00", Arrays.asList("PROPN"));
        ud.put("NP00000", Arrays.asList("PROPN"));

        // AUX
        ud.put("VAG0000", Arrays.asList("AUX"));
        ud.put("VAIC1P0", Arrays.asList("AUX"));
        ud.put("VAIC1S0", Arrays.asList("AUX"));
        ud.put("VAIC2P0", Arrays.asList("AUX"));
        ud.put("VAIC2S0", Arrays.asList("AUX"));
        ud.put("VAIC3P0", Arrays.asList("AUX"));
        ud.put("VAIC3S0", Arrays.asList("AUX"));
        ud.put("VAIF1P0", Arrays.asList("AUX"));
        ud.put("VAIF1S0", Arrays.asList("AUX"));
        ud.put("VAIF2P0", Arrays.asList("AUX"));
        ud.put("VAIF2S0", Arrays.asList("AUX"));
        ud.put("VAIF3P0", Arrays.asList("AUX"));
        ud.put("VAIF3S0", Arrays.asList("AUX"));
        ud.put("VAII1P0", Arrays.asList("AUX"));
        ud.put("VAII1S0", Arrays.asList("AUX"));
        ud.put("VAII2P0", Arrays.asList("AUX"));
        ud.put("VAII2S0", Arrays.asList("AUX"));
        ud.put("VAII3P0", Arrays.asList("AUX"));
        ud.put("VAII3S0", Arrays.asList("AUX"));
        ud.put("VAIP1P0", Arrays.asList("AUX"));
        ud.put("VAIP1S0", Arrays.asList("AUX"));
        ud.put("VAIP2P0", Arrays.asList("AUX"));
        ud.put("VAIP2S0", Arrays.asList("AUX"));
        ud.put("VAIP3P0", Arrays.asList("AUX"));
        ud.put("VAIP3S0", Arrays.asList("AUX"));
        ud.put("VAIS1P0", Arrays.asList("AUX"));
        ud.put("VAIS1S0", Arrays.asList("AUX"));
        ud.put("VAIS2P0", Arrays.asList("AUX"));
        ud.put("VAIS2S0", Arrays.asList("AUX"));
        ud.put("VAIS3P0", Arrays.asList("AUX"));
        ud.put("VAIS3S0", Arrays.asList("AUX"));
        ud.put("VSG0000", Arrays.asList("AUX"));
        ud.put("VSIC1P0", Arrays.asList("AUX"));
        ud.put("VSIC1S0", Arrays.asList("AUX"));
        ud.put("VSIC2P0", Arrays.asList("AUX"));
        ud.put("VSIC2S0", Arrays.asList("AUX"));
        ud.put("VSIC3P0", Arrays.asList("AUX"));
        ud.put("VSIC3S0", Arrays.asList("AUX"));
        ud.put("VSIF1P0", Arrays.asList("AUX"));
        ud.put("VSIF1S0", Arrays.asList("AUX"));
        ud.put("VSIF2P0", Arrays.asList("AUX"));
        ud.put("VSIF2S0", Arrays.asList("AUX"));
        ud.put("VSIF3P0", Arrays.asList("AUX"));
        ud.put("VSIF3S0", Arrays.asList("AUX"));
        ud.put("VSII1P0", Arrays.asList("AUX"));
        ud.put("VSII1S0", Arrays.asList("AUX"));
        ud.put("VSII2P0", Arrays.asList("AUX"));
        ud.put("VSII2S0", Arrays.asList("AUX"));
        ud.put("VSII3P0", Arrays.asList("AUX"));
        ud.put("VSII3S0", Arrays.asList("AUX"));
        ud.put("VSIP1P0", Arrays.asList("AUX"));
        ud.put("VSIP1S0", Arrays.asList("AUX"));
        ud.put("VSIP2P0", Arrays.asList("AUX"));
        ud.put("VSIP2S0", Arrays.asList("AUX"));
        ud.put("VSIP3P0", Arrays.asList("AUX"));
        ud.put("VSIP3S0", Arrays.asList("AUX"));
        ud.put("VSIS1P0", Arrays.asList("AUX"));
        ud.put("VSIS1S0", Arrays.asList("AUX"));
        ud.put("VSIS2P0", Arrays.asList("AUX"));
        ud.put("VSIS2S0", Arrays.asList("AUX"));
        ud.put("VSIS3P0", Arrays.asList("AUX"));
        ud.put("VSIS3S0", Arrays.asList("AUX"));
        ud.put("VSM01P0", Arrays.asList("AUX"));
        ud.put("VSM02P0", Arrays.asList("AUX"));
        ud.put("VSM02S0", Arrays.asList("AUX"));
        ud.put("VSM03P0", Arrays.asList("AUX"));
        ud.put("VSM03S0", Arrays.asList("AUX"));
        ud.put("VSN0000", Arrays.asList("AUX"));
        ud.put("VSP00SM", Arrays.asList("AUX"));
        ud.put("VSSF1P0", Arrays.asList("AUX"));
        ud.put("VSSF1S0", Arrays.asList("AUX"));
        ud.put("VSSF2P0", Arrays.asList("AUX"));
        ud.put("VSSF2S0", Arrays.asList("AUX"));
        ud.put("VSSF3P0", Arrays.asList("AUX"));
        ud.put("VSSF3S0", Arrays.asList("AUX"));
        ud.put("VSSI1P0", Arrays.asList("AUX"));
        ud.put("VSSI1S0", Arrays.asList("AUX"));
        ud.put("VSSI2P0", Arrays.asList("AUX"));
        ud.put("VSSI2S0", Arrays.asList("AUX"));
        ud.put("VSSI3P0", Arrays.asList("AUX"));
        ud.put("VSSI3S0", Arrays.asList("AUX"));
        ud.put("VSSP1P0", Arrays.asList("AUX"));
        ud.put("VSSP1S0", Arrays.asList("AUX"));
        ud.put("VSSP2P0", Arrays.asList("AUX"));
        ud.put("VSSP2S0", Arrays.asList("AUX"));
        ud.put("VSSP3P0", Arrays.asList("AUX"));
        ud.put("VSSP3S0", Arrays.asList("AUX"));

        //INTJ
        ud.put("I", Arrays.asList("INTJ"));

        // VERB
        ud.put("VMG0000", Arrays.asList("VERB"));
        ud.put("VMIC1P0", Arrays.asList("VERB"));
        ud.put("VMIC1S0", Arrays.asList("VERB"));
        ud.put("VMIC2P0", Arrays.asList("VERB"));
        ud.put("VMIC2S0", Arrays.asList("VERB"));
        ud.put("VMIC3P0", Arrays.asList("VERB"));
        ud.put("VMIC3S0", Arrays.asList("VERB"));
        ud.put("VMIF1P0", Arrays.asList("VERB"));
        ud.put("VMIF1S0", Arrays.asList("VERB"));
        ud.put("VMIF2P0", Arrays.asList("VERB"));
        ud.put("VMIF2S0", Arrays.asList("VERB"));
        ud.put("VMIF3P0", Arrays.asList("VERB"));
        ud.put("VMIF3S0", Arrays.asList("VERB"));
        ud.put("VMII1P0", Arrays.asList("VERB"));
        ud.put("VMII1S0", Arrays.asList("VERB"));
        ud.put("VMII2P0", Arrays.asList("VERB"));
        ud.put("VMII2S0", Arrays.asList("VERB"));
        ud.put("VMII3P0", Arrays.asList("VERB"));
        ud.put("VMII3S0", Arrays.asList("VERB"));
        ud.put("VMIP1P0", Arrays.asList("VERB"));
        ud.put("VMIP1S0", Arrays.asList("VERB"));
        ud.put("VMIP2P0", Arrays.asList("VERB"));
        ud.put("VMIP2S0", Arrays.asList("VERB"));
        ud.put("VMIP3P0", Arrays.asList("VERB"));
        ud.put("VMIP3PC", Arrays.asList("VERB"));
        ud.put("VMIP3S0", Arrays.asList("VERB"));
        ud.put("VMIS1P0", Arrays.asList("VERB"));
        ud.put("VMIS1S0", Arrays.asList("VERB"));
        ud.put("VMIS2P0", Arrays.asList("VERB"));
        ud.put("VMIS2S0", Arrays.asList("VERB"));
        ud.put("VMIS3P0", Arrays.asList("VERB"));
        ud.put("VMIS3S0", Arrays.asList("VERB"));
        ud.put("VMM01P0", Arrays.asList("VERB"));
        ud.put("VMM02P0", Arrays.asList("VERB"));
        ud.put("VMM02S0", Arrays.asList("VERB"));
        ud.put("VMM03P0", Arrays.asList("VERB"));
        ud.put("VMM03S0", Arrays.asList("VERB"));
        ud.put("VMN0000", Arrays.asList("VERB"));
        ud.put("VMN0000", Arrays.asList("VERB"));
        ud.put("VMN0000", Arrays.asList("VERB"));
        ud.put("VMP00PF", Arrays.asList("VERB"));
        ud.put("VMP00PM", Arrays.asList("VERB"));
        ud.put("VMP00SF", Arrays.asList("VERB"));
        ud.put("VMP00SM", Arrays.asList("VERB"));
        ud.put("VMSF1P0", Arrays.asList("VERB"));
        ud.put("VMSF1S0", Arrays.asList("VERB"));
        ud.put("VMSF2P0", Arrays.asList("VERB"));
        ud.put("VMSF2S0", Arrays.asList("VERB"));
        ud.put("VMSF3P0", Arrays.asList("VERB"));
        ud.put("VMSF3S0", Arrays.asList("VERB"));
        ud.put("VMSI1P0", Arrays.asList("VERB"));
        ud.put("VMSI1S0", Arrays.asList("VERB"));
        ud.put("VMSI2P0", Arrays.asList("VERB"));
        ud.put("VMSI2S0", Arrays.asList("VERB"));
        ud.put("VMSI3P0", Arrays.asList("VERB"));
        ud.put("VMSI3S0", Arrays.asList("VERB"));
        ud.put("VMSP1P0", Arrays.asList("VERB"));
        ud.put("VMSP1S0", Arrays.asList("VERB"));
        ud.put("VMSP2P0", Arrays.asList("VERB"));
        ud.put("VMSP2S0", Arrays.asList("VERB"));
        ud.put("VMSP3P0", Arrays.asList("VERB"));
        ud.put("VMSP3S0", Arrays.asList("VERB"));

       

        // ADV
        ud.put("RG", Arrays.asList("ADV"));
        ud.put("RN", Arrays.asList("ADV"));

        // ADP
        ud.put("SPCMS", Arrays.asList("ADP"));
        ud.put("SPCMP", Arrays.asList("ADP"));
        ud.put("SPCFS", Arrays.asList("ADP"));
        ud.put("SPCFP", Arrays.asList("ADP"));
        ud.put("SPS00", Arrays.asList("ADP"));

        //CC & CS
        ud.put("CC", Arrays.asList("CCONJ"));
        ud.put("CS", Arrays.asList("SCONJ"));

        // DET
        ud.put("DD0MS0", Arrays.asList("DET"));
        ud.put("DD0MP0", Arrays.asList("DET"));
        ud.put("DD0FS0", Arrays.asList("DET"));
        ud.put("DD0FP0", Arrays.asList("DET"));
        ud.put("DD0CS0", Arrays.asList("DET"));
        ud.put("DD0CP0", Arrays.asList("DET"));
        ud.put("DP1CSS", Arrays.asList("DET"));
        ud.put("DP1CPS", Arrays.asList("DET"));
        ud.put("DP2CSS", Arrays.asList("DET"));
        ud.put("DP2CPS", Arrays.asList("DET"));
        ud.put("DP3CS0", Arrays.asList("DET"));
        ud.put("DP3CP0", Arrays.asList("DET"));
        ud.put("DP1FSP", Arrays.asList("DET"));
        ud.put("DP1FPP", Arrays.asList("DET"));
        ud.put("DP1MSP", Arrays.asList("DET"));
        ud.put("DP1MPP", Arrays.asList("DET"));
        ud.put("DP2FSP", Arrays.asList("DET"));
        ud.put("DP2FPP", Arrays.asList("DET"));
        ud.put("DP2MSP", Arrays.asList("DET"));
        ud.put("DP2MPP", Arrays.asList("DET"));
        ud.put("DT0MS0", Arrays.asList("DET"));
        ud.put("DT0MP0", Arrays.asList("DET"));
        ud.put("DT0FS0", Arrays.asList("DET"));
        ud.put("DT0FP0", Arrays.asList("DET"));
        ud.put("DT0CN0", Arrays.asList("DET"));
        ud.put("DE0MS0", Arrays.asList("DET"));
        ud.put("DE0MP0", Arrays.asList("DET"));
        ud.put("DE0FS0", Arrays.asList("DET"));
        ud.put("DE0FP0", Arrays.asList("DET"));
        ud.put("DE0CN0", Arrays.asList("DET"));
        ud.put("DI0MS0", Arrays.asList("DET"));
        ud.put("DI0MP0", Arrays.asList("DET"));
        ud.put("DI0FS0", Arrays.asList("DET"));
        ud.put("DI0FP0", Arrays.asList("DET"));
        ud.put("DI0CS0", Arrays.asList("DET"));
        ud.put("DI0CP0", Arrays.asList("DET"));
        ud.put("DI0CN0", Arrays.asList("DET"));
        ud.put("DA0MS0", Arrays.asList("DET"));
        ud.put("DA0MP0", Arrays.asList("DET"));
        ud.put("DA0NS0", Arrays.asList("DET"));
        ud.put("DA0FS0", Arrays.asList("DET"));
        ud.put("DA0FP0", Arrays.asList("DET"));
        ud.put("DN0CP0", Arrays.asList("DET"));
        ud.put("DA0CS0", Arrays.asList("DET"));
        ud.put("DN0FP0", Arrays.asList("DET"));
        ud.put("DN0FS0", Arrays.asList("DET"));
        ud.put("DE0CC0", Arrays.asList("DET"));

        //NUM
        ud.put("Zd", Arrays.asList("NUM"));
        ud.put("Zm", Arrays.asList("NUM"));
        ud.put("Z", Arrays.asList("NUM"));
        ud.put("Zp", Arrays.asList("NUM"));
        ud.put("Zu", Arrays.asList("NUM"));

        // PART TO DO
        // PRON
        ud.put("P00CC000", Arrays.asList("PRON"));
        ud.put("P00CN000", Arrays.asList("PRON"));
        ud.put("P03CC000", Arrays.asList("PRON"));
        ud.put("PD0CP000", Arrays.asList("PRON"));
        ud.put("PD0CS000", Arrays.asList("PRON"));
        ud.put("PD0FP000", Arrays.asList("PRON"));
        ud.put("PD0FS000", Arrays.asList("PRON"));
        ud.put("PD0MP000", Arrays.asList("PRON"));
        ud.put("PD0MS000", Arrays.asList("PRON"));
        ud.put("PD0NS000", Arrays.asList("PRON"));
        ud.put("PE000000", Arrays.asList("PRON"));
        ud.put("PI0CP000", Arrays.asList("PRON"));
        ud.put("PI0CS000", Arrays.asList("PRON"));
        ud.put("PI0FP000", Arrays.asList("PRON"));
        ud.put("PI0FS000", Arrays.asList("PRON"));
        ud.put("PI0MP000", Arrays.asList("PRON"));
        ud.put("PI0MS000", Arrays.asList("PRON"));
        ud.put("PN0MS000", Arrays.asList("PRON"));
        ud.put("PP1CP000", Arrays.asList("PRON"));
        ud.put("PP1CS000", Arrays.asList("PRON"));
        ud.put("PP1CSN00", Arrays.asList("PRON"));
        ud.put("PP1CSO00", Arrays.asList("PRON"));
        ud.put("PP1FP000", Arrays.asList("PRON"));
        ud.put("PP1MP000", Arrays.asList("PRON"));
        ud.put("PP2CNO00", Arrays.asList("PRON"));
        ud.put("PP2CP000", Arrays.asList("PRON"));
        ud.put("PP2CP00P", Arrays.asList("PRON"));
        ud.put("PP2CS000", Arrays.asList("PRON"));
        ud.put("PP2CS00P", Arrays.asList("PRON"));
        ud.put("PP2CSN00", Arrays.asList("PRON"));
        ud.put("PP2CSO00", Arrays.asList("PRON"));
        ud.put("PP2FP000", Arrays.asList("PRON"));
        ud.put("PP2MP000", Arrays.asList("PRON"));
        ud.put("PP3CCO00", Arrays.asList("PRON"));
        ud.put("PP3CN000", Arrays.asList("PRON"));
        ud.put("PP3CN00P", Arrays.asList("PRON"));
        ud.put("PP3CNA00", Arrays.asList("PRON"));
        ud.put("PP3CNO00", Arrays.asList("PRON"));
        ud.put("PP3CPD00", Arrays.asList("PRON"));
        ud.put("PP3CS000", Arrays.asList("PRON"));
        ud.put("PP3CSD00", Arrays.asList("PRON"));
        ud.put("PP3FP000", Arrays.asList("PRON"));
        ud.put("PP3FPA00", Arrays.asList("PRON"));
        ud.put("PP3FS000", Arrays.asList("PRON"));
        ud.put("PP3FSA00", Arrays.asList("PRON"));
        ud.put("PP3MP000", Arrays.asList("PRON"));
        ud.put("PP3MPA00", Arrays.asList("PRON"));
        ud.put("PP3MS000", Arrays.asList("PRON"));
        ud.put("PP3MSA00", Arrays.asList("PRON"));
        ud.put("PP3NS000", Arrays.asList("PRON"));
        ud.put("PR000000", Arrays.asList("PRON"));
        ud.put("PR0CC000", Arrays.asList("PRON"));
        ud.put("PR0CN000", Arrays.asList("PRON"));
        ud.put("PR0CP000", Arrays.asList("PRON"));
        ud.put("PR0CS000", Arrays.asList("PRON"));
        ud.put("PR0FP000", Arrays.asList("PRON"));
        ud.put("PR0FS000", Arrays.asList("PRON"));
        ud.put("PR0MP000", Arrays.asList("PRON"));
        ud.put("PR0MS000", Arrays.asList("PRON"));
        ud.put("PT000000", Arrays.asList("PRON"));
        ud.put("PT0CP000", Arrays.asList("PRON"));
        ud.put("PT0CS000", Arrays.asList("PRON"));
        ud.put("PT0FP000", Arrays.asList("PRON"));
        ud.put("PT0FS000", Arrays.asList("PRON"));
        ud.put("PT0MP000", Arrays.asList("PRON"));
        ud.put("PT0MS000", Arrays.asList("PRON"));
        ud.put("PX1FP0P0", Arrays.asList("PRON"));
        ud.put("PX1FP0S0", Arrays.asList("PRON"));
        ud.put("PX1FS0P0", Arrays.asList("PRON"));
        ud.put("PX1FS0S0", Arrays.asList("PRON"));
        ud.put("PX1MP0P0", Arrays.asList("PRON"));
        ud.put("PX1MP0S0", Arrays.asList("PRON"));
        ud.put("PX1MS0P0", Arrays.asList("PRON"));
        ud.put("PX1MS0S0", Arrays.asList("PRON"));
        ud.put("PX1NS0P0", Arrays.asList("PRON"));
        ud.put("PX1NS0S0", Arrays.asList("PRON"));
        ud.put("PX2FP0P0", Arrays.asList("PRON"));
        ud.put("PX2FP0S0", Arrays.asList("PRON"));
        ud.put("PX2FS0P0", Arrays.asList("PRON"));
        ud.put("PX2FS0S0", Arrays.asList("PRON"));
        ud.put("PX2MP0P0", Arrays.asList("PRON"));
        ud.put("PX2MP0S0", Arrays.asList("PRON"));
        ud.put("PX2MS0P0", Arrays.asList("PRON"));
        ud.put("PX2MS0S0", Arrays.asList("PRON"));
        ud.put("PX2NS0P0", Arrays.asList("PRON"));
        ud.put("PX2NS0S0", Arrays.asList("PRON"));
        ud.put("PX3FP000", Arrays.asList("PRON"));
        ud.put("PX3FS000", Arrays.asList("PRON"));
        ud.put("PX3MP000", Arrays.asList("PRON"));
        ud.put("PX3MS000", Arrays.asList("PRON"));
        ud.put("PX3NS000", Arrays.asList("PRON"));
        
        //X
        ud.put("W", Arrays.asList("X"));

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
