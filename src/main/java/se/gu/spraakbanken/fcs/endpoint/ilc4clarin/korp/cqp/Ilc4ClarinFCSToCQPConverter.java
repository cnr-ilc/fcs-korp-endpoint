/**
 * Forked from https://github.com/clarin-eric/fcs-korp-endpoint
 *
 * @license http://www.gnu.org/licenses/gpl-3.0.txt GNU General Public License
 * v3
 */
package se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.cqp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.z3950.zing.cql.CQLBooleanNode;
import org.z3950.zing.cql.CQLNode;
import org.z3950.zing.cql.CQLNotNode;
import org.z3950.zing.cql.CQLTermNode;

import eu.clarin.sru.server.SRUConstants;
import eu.clarin.sru.server.SRUException;
import eu.clarin.sru.server.SRUQuery;
import eu.clarin.sru.server.fcs.Constants;
import eu.clarin.sru.server.fcs.parser.Expression;
import eu.clarin.sru.server.fcs.parser.ExpressionAnd;
import eu.clarin.sru.server.fcs.parser.ExpressionGroup;
import eu.clarin.sru.server.fcs.parser.ExpressionOr;
import eu.clarin.sru.server.fcs.parser.ExpressionWildcard;
import eu.clarin.sru.server.fcs.parser.Operator;
import eu.clarin.sru.server.fcs.parser.QueryNode;
import eu.clarin.sru.server.fcs.parser.QuerySegment;
import eu.clarin.sru.server.fcs.parser.QuerySequence;
import eu.clarin.sru.server.fcs.parser.RegexFlag;
import java.util.Properties;
import java.util.logging.Level;
import se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.data.json.pojo.info.CorporaInfo;
import se.gu.spraakbanken.fcs.endpoint.ilc4clarin.korp.utils.Utilities;

/**
 * A Korp CLARIN FCS 2.0 IL4CLARIN converter of converter of FCS to CQP.
 *
 * In addition to FCSToCQPConverter it manages several types of queries:
 * <ol>
 * <li>Single Expression [ A = B ]</li>
 * <li>Single Sequence [ A = B ] [ C=D ]</li>
 * <li>Single And [ A = B &amp; C = D ]</li>
 * <li>Single And + Or [ A = B &amp; C = D | E = F ]</li>
 * <li>Multiple And [ A = B &amp; C=D &amp; E = F ]</li>
 * <li>Single Or [ A = B | C = D ]</li>
 * <li>Multiple Or [ A = B | C=D | E = F ]</li>
 * <li>Several Group Queries
 * <ol>
 * <li>Group with Or [ (A = B | C = D) ]</li>
 * <li>Group with And [ (A = B &amp; C = D) ]</li>
 * <li>Group with Ors [ (A = B | C = D | E = F) ]</li>
 * <li>Group with Ands [ (A = B &amp; C = D &amp; E = F) ]</li>
 * <li>Group with Or + And [ (A = B | C = D) &amp; F = K ]</li>
 * <li>Group with Or + And (reversed) [ F = K &amp; (A = B | C = D) ]</li>
 * <li>Group with Or + Or [ (A = B | C = D) | F = K ]</li>
 * <li>Group with Or + Or (reversed) [ F = K | (A = B | C = D)]</li>
 * <li>Group with Or + Or [ (A = B | C = D) | F = K ]</li>
 * <li>Group with Ors + And [ (A = B | C = D) &amp; ( F = K | M = N) ]</li>
 * </ol>
 * <li>Complex Sequence and grouped such as [ ( A = B | C = D | F = K ) &amp; ( M =
 * \N | Q = Z ) ] [ ( G = H | J = L ) &amp; S = X ]";
 * </li>
 * </ol>
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it|gmail.com&gt;
 */
public class Ilc4ClarinFCSToCQPConverter {

    private static CorporaInfo corporaInfo;
    private static Properties prop;
    private static final Logger LOG
            = LoggerFactory.getLogger(Ilc4ClarinFCSToCQPConverter.class);

    /**
     *
     * @param query The CQL query
     * @return The CQP query
     * @throws eu.clarin.sru.server.SRUException If the query is too complex or
     * it for any other reason cannot be performed
     */
    public static String makeCQPFromCQL(final SRUQuery<CQLNode> query)
            throws SRUException {
        final CQLNode node = query.getParsedQuery();
        System.out.println("se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.makeCQPFromCQL() " + query.getRawQuery());
        /*
         * Translate the CQL query to a Lucene query. If a CQL feature was used,
         * that is not supported by us, throw a SRU error (with a detailed error
         * message)
         *
         * Right now, we're pretty stupid and only support terms
         */
        if (node instanceof CQLBooleanNode) {
            String operator;
            //if (node instanceof CQLAndNode) {
            //    operator = "AND";
            //} else if (node instanceof CQLOrNode) {
            //    operator = "OR";
            //} else
            if (node instanceof CQLNotNode) {
                operator = "NOT";
            } else {
                operator = node.getClass().getSimpleName();
            }
            throw new SRUException(
                    SRUConstants.SRU_UNSUPPORTED_BOOLEAN_OPERATOR,
                    operator,
                    "Unsupported Boolean operator: " + operator);
        } else if (node instanceof CQLTermNode) {
            CQLTermNode ctn = (CQLTermNode) node;

            String[] terms = ctn.getTerm().toLowerCase().split("\\s+");
            if (terms.length > 1) {
                String phrase = "";
                for (int i = 0; i < terms.length; i++) {
                    if (terms[i].startsWith("\"") || terms[i].startsWith("'") || terms[i].endsWith("\"") || terms[i].endsWith("'")) {
                        String tmp = terms[i];
                        if (terms[i].startsWith("\"") || terms[i].startsWith("'")) {
                            tmp = tmp.substring(1);
                        }
                        if (terms[i].endsWith("\"") || terms[i].endsWith("'")) {
                            tmp = tmp.substring(0, tmp.length() - 1);
                        }
                        phrase += "[word = '" + tmp + "']";
                    } else {
                        phrase += "[word = '" + terms[i] + "']";
                    }
                }
                return phrase;
            } else {
                return "[word = '" + terms[0] + "']";
            }
        } else {
            throw new SRUException(
                    SRUConstants.SRU_CANNOT_PROCESS_QUERY_REASON_UNKNOWN,
                    "unknown cql node: " + node);
        }
    }

    /**
     * Translate the incoming FCS 2.0 into a valid CQP one
     *
     * @param query The FCS 2.0 query
     * @param contextedCorpora the corpora to serach on
     * @param prop the property file
     * @return The CQP query
     * @throws eu.clarin.sru.server.SRUException If the query is too complex or
     * it for any other reason cannot be performed
     */
        public static String Ilc4ClarinMakeCQPFromFCS(final SRUQuery<QueryNode> query, CorporaInfo contextedCorpora, Properties prop)
            throws SRUException {
        String ret = "";
        setCorporaInfo(contextedCorpora);
        setProp(prop);
        QueryNode tree = query.getParsedQuery();
        LOG.debug("FCS-Query: {}", tree.toString());
        LOG.debug("FCS-Query Context: {}", contextedCorpora.getCorpora().keySet());

        // A somewhat crude query translator
        if (tree instanceof QuerySequence) {
            ret = getQuerySequence(tree);

        } else if (tree instanceof QuerySegment) {
            ret = getQuerySegment(tree);

        } else {
            throw new SRUException(
                    Constants.FCS_DIAGNOSTIC_GENERAL_QUERY_TOO_COMPLEX_CANNOT_PERFORM_QUERY,
                    "Endpoint does not support the input query " + query.toString());
        }
        return ret;
    }

    /**
     * To manage queries such as [word='a'][pos='b']...
     *
     * @param tree the query sequence to parse
     * @return a concatenation of querySegments
     * @throws SRUException
     */
    private static String getQuerySequence(final QueryNode tree) throws SRUException {
        List<String> children = new ArrayList<String>();
        QuerySequence sequence = (QuerySequence) tree;

        for (int i = 0; i < sequence.getChildCount(); i++) {
            QueryNode child = sequence.getChild(i);
            if (child instanceof QuerySegment) {
                children.add(getQuerySegment(child));
            }
        }
        StringBuffer sb = new StringBuffer();
        for (String child : children) {
            sb.append(child);
        }
        return sb.toString();
    }

    /**
     * Parse each segment of the query
     *
     * @param tree the query segment to parse
     * @return converted segment
     * @throws SRUException
     */
    private static String getQuerySegment(final QueryNode tree) throws SRUException {
        String ret = "";

        QuerySegment segment = (QuerySegment) tree;
        QueryNode op = segment.getExpression();

        if (op instanceof ExpressionAnd) {
            LOG.debug("From getQuerySegment call getExpressionBoolOp(AND) for Exp '{}'", op.toString());

            ret = getExpressionBoolOp(op, " & ");

        } else if (op instanceof ExpressionOr) {
            LOG.debug("From getQuerySegment call getExpressionBoolOp(OR) for Exp '{}'", op.toString());

            ret = getExpressionBoolOp(op, " | ");

        } else if (op instanceof ExpressionGroup) {
            LOG.debug("From getQuerySegment call getExpressionGroupOp() for Exp '{}'", op.toString());
            ret = getExpressionGroupOp(op);
            //ret = getExpressionGroupOp(op, "-");
        } else {
            String occurrences = getOccurrences(segment.getMinOccurs(), segment.getMaxOccurs());
            QueryNode child = segment.getExpression();
            if (child instanceof Expression) {
                LOG.debug("From getQuerySegment call getExpression for Exp '{}'", child);
                return "[" + getExpression((Expression) child) + "]" + occurrences;
            } else if (child instanceof ExpressionWildcard) {
                return " []" + occurrences;
            } else {
                throw new SRUException(
                        Constants.FCS_DIAGNOSTIC_GENERAL_QUERY_TOO_COMPLEX_CANNOT_PERFORM_QUERY,
                        "Endpoint only supports sequences or single segment expressions");

            }
        }

        return "[ " + ret + " ]";
    }

    private static String getOccurrences(final int min, final int max) {
        if (min == 1 && max == 1) {
            return " ";
        } else if (min == max) {
            return "{" + min + "} ";
        } else {
            return "{" + min + "," + max + "} ";
        }
    }

    /**
     *
     * @param op The query to parse
     * @param opString the boolean operator
     * @return the formatted expression according to opstring
     * @throws SRUException
     */
    private static String getExpressionBoolOp(final QueryNode op, final String opString) throws SRUException {
        String ret = "";
        List<String> children = new ArrayList<String>();

        for (int i = 0; i < op.getChildCount(); i++) {
            QueryNode child = op.getChild(i);
//            
            if (child instanceof Expression) {
                LOG.debug("From getExpressionBoolOp call getExpression for Exp '{}'", child);

                children.add(getExpression((Expression) child));

            } else if (child instanceof ExpressionGroup) {
                LOG.debug("From getExpressionBoolOp call getExpressionGroupOp for Exp '{}'", child);
                children.add(getExpressionGroupOp(child));
                //children.add(getExpressionGroupOp(child, opString));
//              
            } else if (child instanceof ExpressionAnd) {
                LOG.debug("From getExpressionBoolOp call getExpressionBoolOp(AND) for Exp '{}'", child);

                children.add(getExpressionBoolOp(child, " & "));
            } else if (child instanceof ExpressionOr) {
                LOG.debug("From getExpressionBoolOp call getExpressionBoolOp(OR) for Exp '{}'", child);

                children.add(getExpressionBoolOp(child, " & "));
            } else {
                throw new SRUException(
                        Constants.FCS_DIAGNOSTIC_GENERAL_QUERY_TOO_COMPLEX_CANNOT_PERFORM_QUERY,
                        "Parse error: Unknown class " + child.getClass().getCanonicalName());
            }

        }

        int i = 0;
        for (String s : children) {
            //System.out.println("In getExpressionBoolOp CHILDREN CH " + s + " and OPSTRING " + opString);
            if (i < children.size() - 1) {
                ret = ret + s + opString;
            } else {
                ret = ret + s;
            }
            i++;
        }

        return ret;//children.get(0) + opString + children.get(1);
    }

    /**
     * 
     * @param op the query segment to parse
     * @return a formatted segment
     * @throws SRUException 
     */
    private static String getExpressionGroupOp(final QueryNode op) throws SRUException {
        List<String> children = new ArrayList<String>();

        String ret = "";
        for (int i = 0; i < op.getChildCount(); i++) {
            QueryNode child = op.getChild(i);
//           
            if (child instanceof ExpressionOr) {
                LOG.debug("From getExpressionGroupOp call getExpressionBoolOp(OR) for Exp '{}'", child);
                 ret = getExpressionBoolOp(child, " | ");
//                
            } else if (child instanceof ExpressionGroup) {
                LOG.debug("From getExpressionGroupOp call getExpressionGroupOp for Exp '{}'", child);
                
                ret = getExpressionGroupOp(child);

            } else if (child instanceof ExpressionAnd) {
                LOG.debug("From getExpressionGroupOp call getExpressionBoolOp(AND) for Exp '{}'", child);
                ret = getExpressionBoolOp(child, " & ");

            } else if (child instanceof Expression) {
                LOG.debug("From getExpressionGroupOp call getExpression for Exp '{}'", child);
                children.add(getExpression((Expression) child));

            } else {
//               
            }
//            if (child instanceof Expression) {
//                children.add(getExpression((Expression) child));
//            }
        }
//        
//        
        return "(" + ret + ")"; //children.get(0) + opString + children.get(1);
    }
    
    @Deprecated
    private static String getExpressionGroupOp(final QueryNode op, final String opString) throws SRUException {
        List<String> children = new ArrayList<String>();

        String ret = "";
        for (int i = 0; i < op.getChildCount(); i++) {
            QueryNode child = op.getChild(i);
//           
            if (child instanceof ExpressionOr) {
                //System.err.println("DA getExpressionGroupOp INVOCO getExpressionBoolOp con OR "+child);

                ret = getExpressionBoolOp(child, " | ");
//                
            } else if (child instanceof ExpressionGroup) {
                //System.err.println("DA getExpressionGroupOp INVOCO getExpressionGroupOp");
                ret = getExpressionGroupOp(child, opString);

            } else if (child instanceof ExpressionAnd) {
                //System.err.println("DA getExpressionGroupOp INVOCO getExpressionBoolOp con AND");
                ret = getExpressionBoolOp(child, " & ");

            } else if (child instanceof Expression) {
                children.add(getExpression((Expression) child));

            } else {
//               
            }
//            if (child instanceof Expression) {
//                children.add(getExpression((Expression) child));
//            }
        }
        int i = 0;
        for (String s : children) {
            //System.out.println("In getExpressionGroupOp CHILDREN " + s);
            if (i < children.size() - 1) {
                ret = ret + s + opString;
            } else {
                ret = ret + s;
            }
            i++;
        }
//        
        return "(" + ret + ")"; //children.get(0) + opString + children.get(1);
    }
    /**
     * 
     * @param child the expression A=B to evaluate
     * @return an evaluated expression
     * @throws SRUException 
     */
    private static String getExpression(final Expression child) throws SRUException {
        Expression expression = (Expression) child;
        if ((expression.getLayerIdentifier().equals("text") || expression.getLayerIdentifier().equals("token") || expression.getLayerIdentifier().equals("word") || expression.getLayerIdentifier().equals("lemma") || expression.getLayerIdentifier().equals("pos"))
                && (expression.getLayerQualifier() == null)
                && (expression.getOperator() == Operator.EQUALS || expression.getOperator() == Operator.NOT_EQUALS) //&&
                //(expression.getRegexFlags() == null)
                ) {
            // Not really worth it using regexFlags.
            // Still handled in getWordLayerFilter(). /ljo

            // Translate PoS value or just get the text/word layer as is.
            if (expression.getLayerIdentifier().equals("pos")) {
                LOG.debug("From getExpression call translatePos for Exp '{}'", child);
                //System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpression()");
                return translatePos(expression.getLayerIdentifier(), getOperator(expression.getOperator()), expression.getRegexValue());
            } else if (expression.getLayerIdentifier().equals("lemma")) {
                return getLemmaLayerFilter(expression);
            }
            return getWordLayerFilter(expression);

        } else {
            throw new SRUException(
                    Constants.FCS_DIAGNOSTIC_GENERAL_QUERY_TOO_COMPLEX_CANNOT_PERFORM_QUERY,
                    "Endpoint only supports 'text', 'word', 'lemma', and 'pos' layers, the '=' and '!=' operators and no regex flags");
        }
    }

    /**
     * <p> This method accepts a pos in input and creates a list of possible mapped pos from different tagset.</p>
     * <p> The input pos is responsible to instantiate the correct {@link POSTranslator}
     * @param layerIdentifier the layer
     * @param operator boolean and /or
     * @param pos the pos to translate
     * @return the translated pos
     * @throws SRUException 
     */
    private static String translatePos(final String layerIdentifier, final String operator, final String pos) throws SRUException {

        CorporaInfo contextedCorpora = getCorporaInfo();

        Properties prop = getProp();

        StringBuffer buf = new StringBuffer();
        List<String> returnList = new ArrayList<String>();

        
        for (String corpus : contextedCorpora.getCorpora().keySet()) {
            //buf = new StringBuffer();

            String tagset = Utilities.getTagSetFromCorpusMap(Utilities.getTagSetFromCorpusList(prop), corpus);
            
            POSTranslator posTranslator = null;
            String logMess = "";
            try {
                posTranslator = POSTranslatorFactory.getPOSTranslator(tagset);
            } catch (SRUException ex) {
                java.util.logging.Logger.getLogger(Ilc4ClarinFCSToCQPConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {

                List<String> sucT = posTranslator.toPos(pos);
                //System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.translatePos() "+pos+ " tagset "+ tagset+ " List "+returnList.toString());
                LOG.debug("se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.translatePos() corpus " + corpus + " and pos " + pos + " and tagset " + tagset + " returned '{}'", returnList.toString());
                returnList.addAll(sucT);

                
            } catch (Exception se) {
                continue;
            }

            //return buf.append("'").toString();
        }

        buf.append(layerIdentifier);
        buf.append(" ");
        buf.append(operator);
        buf.append(" '");

        if (returnList.size() == 1) {
            buf.append(returnList.get(0));
        } else {
            int i = 0;
            buf.append("(");
            for (String s : returnList) {
                if (i > 0) {
                    buf.append("|");
                }
                buf.append(s);
                i++;
            }
            buf.append(")");
        }
        return buf.append("'").toString();
    }

    private static String getOperator(Operator op) {
        if (op == Operator.NOT_EQUALS) {
            return "!=";
        }
        return "=";
    }

    private static String getWordLayerFilter(Expression expression) {
        boolean contRegexFlag = false;
        StringBuffer buf = new StringBuffer();
        buf.append((expression.getLayerIdentifier().equals("text") || expression.getLayerIdentifier().equals("token")) ? "word" : expression.getLayerIdentifier());
        buf.append(" ");
        buf.append(getOperator(expression.getOperator()));
        buf.append(" '");
        buf.append(expression.getRegexValue());
        buf.append("'");
        if (expression.getRegexFlags() != null) {
            if (expression.getRegexFlags().contains(RegexFlag.CASE_INSENSITIVE)) {
                buf.append(" %c");
                contRegexFlag = true;
            }
            if (expression.getRegexFlags().contains(RegexFlag.CASE_SENSITIVE)) {
            }
            if (expression.getRegexFlags().contains(RegexFlag.LITERAL_MATCHING)) {
                if (!contRegexFlag) {
                    buf.append(" %");
                }
                buf.append("l");
                contRegexFlag = true;
            }
            if (expression.getRegexFlags().contains(RegexFlag.IGNORE_DIACRITICS)) {
                if (!contRegexFlag) {
                    buf.append(" %");
                }
                buf.append("d");
                contRegexFlag = true;
            }
            if (contRegexFlag) {
                //buf.append(" ");
            }
        }
        return buf.toString();
    }

    private static String getLemmaLayerFilter(Expression expression) {
        boolean contRegexFlag = false;

        StringBuffer buf = new StringBuffer();
        buf.append(expression.getLayerIdentifier());
        buf.append(" ");
        if (expression.getOperator() == Operator.NOT_EQUALS) {
            //System.out.println("****** NOTEQ se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getLemmaLayerFilter()");
            buf.append("!=");
        } else if (expression.getOperator() == Operator.EQUALS) {
            //System.out.println("****** EQ se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getLemmaLayerFilter()");
            buf.append("=");
        }

        if (!expression.getRegexValue().startsWith(".*")) {
            //System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getLemmaLayerFilter() ");
            buf.append(" '.*");
        } else {
            buf.append(" '");
        }
        buf.append(expression.getRegexValue());
        if (!expression.getRegexValue().endsWith(".*")) {
            //System.out.println("S****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getLemmaLayerFilter() ");
            buf.append(".*'");
        } else {
            buf.append("' ");
        }

        if (expression.getRegexFlags() != null) {
            if (expression.getRegexFlags().contains(RegexFlag.CASE_INSENSITIVE)) {
                buf.append(" %c");
                contRegexFlag = true;
            }
            if (expression.getRegexFlags().contains(RegexFlag.CASE_SENSITIVE)) {
            }
            if (expression.getRegexFlags().contains(RegexFlag.LITERAL_MATCHING)) {
                if (!contRegexFlag) {
                    buf.append(" %");
                }
                buf.append("l");
                contRegexFlag = true;
            }
            if (expression.getRegexFlags().contains(RegexFlag.IGNORE_DIACRITICS)) {
                if (!contRegexFlag) {
                    buf.append(" %");
                }
                buf.append("d");
                contRegexFlag = true;
            }
            if (contRegexFlag) {
                //buf.append(" ");
            }
        }
        return buf.toString();
    }

    /**
     * @return the prop
     */
    public static Properties getProp() {
        return prop;
    }

    /**
     * @param aProp the prop to set
     */
    public static void setProp(Properties aProp) {
        prop = aProp;
    }

    /**
     * @return the corporaInfo
     */
    public static CorporaInfo getCorporaInfo() {
        return corporaInfo;
    }

    /**
     * @param corporaInfo the corporaInfo to set
     */
    public static void setCorporaInfo(CorporaInfo corporaInfo) {
        Ilc4ClarinFCSToCQPConverter.corporaInfo = corporaInfo;
    }

}
