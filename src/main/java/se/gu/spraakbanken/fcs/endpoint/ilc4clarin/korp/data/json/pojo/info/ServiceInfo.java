/**
 * Added to the Forked from https://github.com/clarin-eric/fcs-korp-endpoint
 *
 * @license http://www.gnu.org/licenses/gpl-3.0.txt GNU General Public License
 * v3
 */
package se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.data.json.pojo.info;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.utils.ManageProperties;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "corpora",
    "cqp-version",
    "protected_corpora",
    "time"
})
/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it|gmail.com&gt;
 */
public class ServiceInfo {

    @JsonProperty("corpora")
    private List<String> corpora = new ArrayList<String>();
    @JsonProperty("cqp-version")
    private String cqpVersion;
    @JsonProperty("protected_corpora")
    private List<String> protectedCorpora = new ArrayList<String>();
    @JsonProperty("time")
    private Double time;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    private static List<String> ILC4CLARIN_CORPORA = new ArrayList<String>();

    private static final Logger LOG
            = LoggerFactory.getLogger(ServiceInfo.class);

    /**
     *
     * @return The corpora
     */
    @JsonProperty("corpora")
    public List<String> getCorpora() {
        return corpora;
    }

    /**
     *
     * @param corpora The corpora
     */
    @JsonProperty("corpora")
    public void setCorpora(List<String> corpora) {
        this.corpora = corpora;
    }

    /**
     *
     * @return The cqpVersion
     */
    @JsonProperty("cqp-version")
    public String getCqpVersion() {
        return cqpVersion;
    }

    /**
     *
     * @param cqpVersion The cqp-version
     */
    @JsonProperty("cqp-version")
    public void setCqpVersion(String cqpVersion) {
        this.cqpVersion = cqpVersion;
    }

    /**
     *
     * @return The protectedCorpora
     */
    @JsonProperty("protected_corpora")
    public List<String> getProtectedCorpora() {
        return protectedCorpora;
    }

    /**
     *
     * @param protectedCorpora The protected_corpora
     */
    @JsonProperty("protected_corpora")
    public void setProtectedCorpora(List<String> protectedCorpora) {
        this.protectedCorpora = protectedCorpora;
    }

    /**
     *
     * @return The time
     */
    @JsonProperty("time")
    public Double getTime() {
        return time;
    }

    /**
     *
     * @param time The time
     */
    @JsonProperty("time")
    public void setTime(Double time) {
        this.time = time;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    /**
     * @return the ILC4CLARIN_CORPORA
     */
    public static List<String> getILC4CLARIN_CORPORA() {
        return ILC4CLARIN_CORPORA;
    }

    /**
     * @param aILC4CLARIN_CORPORA the ILC4CLARIN_CORPORA to set
     */
    public static void setILC4CLARIN_CORPORA(List<String> aILC4CLARIN_CORPORA) {
        ILC4CLARIN_CORPORA = aILC4CLARIN_CORPORA;
    }

    /**
     *
     * @param prop the property file
     * @return the list of available corpora
     */
    public static List<String> getIlc4ClarinOpenCorpora(Properties prop) {
        ObjectMapper mapper = new ObjectMapper();

        final String wsString = ManageProperties.createKorpUrl(prop);
        final String queryString = "info";

        LOG.debug("se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.data.json.pojo.info.ServiceInfo.getIlc4ClarinOpenCorpora() query '{}'", wsString + queryString);
        ServiceInfo si = null;

        try {
            URL korp = new URL(wsString + queryString);
            si = mapper.readerFor(ServiceInfo.class).readValue(korp.openStream());

        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<String> openCorpora = new ArrayList<String>();
        boolean isPC = false;
        for (String corpus : si.getCorpora()) {
            for (String pCorpus : si.getProtectedCorpora()) {
                if (corpus.equals(pCorpus)) {
                    isPC = true;
                }
            }
            if (!isPC) {
                openCorpora.add(corpus);
            }
            isPC = false;
        }
        LOG.debug("se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.data.json.pojo.info.ServiceInfo.getIlc4ClarinOpenCorpora() corpora '{}'", openCorpora);
        return openCorpora;

    }

    /**
     * Check the consistency between the retrieved corpora and the one in the
     * config file according to the property ILC4CLARIN_CORPORA
     *
     * @param prop the properties
     * @return the list of corpora
     */
    public static List<String> getIlc4ClarinCorpora(Properties prop) {
        List<String> filteredCorpora = new ArrayList<String>();
        List<String> openCorpora = ServiceInfo.getIlc4ClarinOpenCorpora(prop);
        
         List<String> corporaFromFile = null;
        String ilc4ClarinCorpora = prop.getProperty("ILC4CLARIN_CORPORA");
        if (ilc4ClarinCorpora == null || ilc4ClarinCorpora.length()==0 ){
            return openCorpora;
        } else{
        
       
        if (ilc4ClarinCorpora.split(",").length == 1) {
            corporaFromFile = Collections.unmodifiableList(Arrays.asList(ilc4ClarinCorpora));
        } else {
            corporaFromFile = Collections.unmodifiableList(Arrays.asList(ilc4ClarinCorpora.split(",")));
        }
        //System.err.println("  getIlc4ClarinCorpora "+corporaFromFile+ " "+openCorpora);

        // sets available corpora. This filters out potential test corpora in the korp backend
        setILC4CLARIN_CORPORA(corporaFromFile);
        LOG.debug("se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.data.json.pojo.info.ServiceInfo.getIlc4ClarinCorpora() corpora from file '{}'", ilc4ClarinCorpora);
        for (String corpus : openCorpora) {
            if (getILC4CLARIN_CORPORA().contains(corpus)) {
                filteredCorpora.add(corpus);
            }
        }
        LOG.debug("se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.data.json.pojo.info.ServiceInfo.getIlc4ClarinCorpora() filtered '{}'", filteredCorpora);
        }
        return filteredCorpora;
    }

}
