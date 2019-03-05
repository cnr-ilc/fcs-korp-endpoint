/**
 *
 * @license http://www.gnu.org/licenses/gpl-3.0.txt
 *  GNU General Public License v3
 */
package se.gu.spraakbanken.fcs.endpoint.korp.cqp;

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
import se.gu.spraakbanken.fcs.endpoint.korp.data.json.pojo.info.CorporaInfo;
import se.gu.spraakbanken.fcs.endpoint.korp.utils.Utilities;

/**
 * A Korp CLARIN FCS 2.0 endpoint example converter of FCS to CQP.
 *
 */
public class Ilc4ClarinFCSToCQPConverter {

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
     *
     * @param query The FCS 2.0 query
     * @return The CQP query
     * @throws eu.clarin.sru.server.SRUException If the query is too complex or
     * it for any other reason cannot be performed
     */
    public static String makeCQPFromFCS(final SRUQuery<QueryNode> query, CorporaInfo contextedCorpora, Properties prop)
            throws SRUException {
        setCorporaInfo(contextedCorpora);
        setProp(prop);
        QueryNode tree = query.getParsedQuery();
        LOG.debug("FCS-Query: {}", tree.toString());
        LOG.debug("FCS-Query Context: {}", contextedCorpora.getCorpora().keySet());
        System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.makeCQPFromFCS() FCS-Query:" + tree.toString());
        System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.makeCQPFromFCS() FCS-Query:" + tree.toString() + " Context: " + getCorporaInfo().getCorpora().keySet());
        System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.makeCQPFromFCS() FCS-Query-Class " + tree.getClass().getCanonicalName());
        // A somewhat crude query translator
        if (tree instanceof QuerySequence) {
            return getQuerySequence(tree);
        } else if (tree instanceof QuerySegment) {
            return getQuerySegment(tree);
        } else {
            throw new SRUException(
                    Constants.FCS_DIAGNOSTIC_GENERAL_QUERY_TOO_COMPLEX_CANNOT_PERFORM_QUERY,
                    "Endpoint only supports sequences or single segment queries");
        }
    }

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

    private static String getQuerySegment(final QueryNode tree) throws SRUException {
        QuerySegment segment = (QuerySegment) tree;
        QueryNode op = segment.getExpression();
        System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.makeCQPFromFCS() FCS-Query-Class " + segment.getClass().getCanonicalName());
        System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.makeCQPFromFCS() FCS-Query-Class " + op.getClass().getCanonicalName());
        System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.makeCQPFromFCS() FCS-Query-Class " + op.getClass().getCanonicalName() + " " + op.toString());
        if (op instanceof ExpressionAnd) {
            System.out.println("STICAAND se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getQuerySegment() " + op.toString());
            return "[" + getExpressionBoolOp(op, " & ") + "]";
        } else if (op instanceof ExpressionOr) {
            System.out.println("STICAOR se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getQuerySegment() " + op.toString());
            return "[" + getExpressionBoolOp(op, " | ") + "]";
        } else if (op instanceof ExpressionGroup) {
            System.out.println("STICAGROUP se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getQuerySegment() " + op.toString());
            return "[" + getExpressionGroupOp(op, " | ") + "]";
        } else {
            String occurrences = getOccurrences(segment.getMinOccurs(), segment.getMaxOccurs());
            QueryNode child = segment.getExpression();
            if (child instanceof Expression) {
                return "[" + getExpression((Expression) child) + "]" + occurrences;
            } else if (child instanceof ExpressionWildcard) {
                return " []" + occurrences;
            } else {
                throw new SRUException(
                        Constants.FCS_DIAGNOSTIC_GENERAL_QUERY_TOO_COMPLEX_CANNOT_PERFORM_QUERY,
                        "Endpoint only supports sequences or single segment expressions");
            }
        }
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

    private static String getExpressionBoolOp(final QueryNode op, final String opString) throws SRUException {
        List<String> children = new ArrayList<String>();
        System.out.println("STICA se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionBoolOp() " + op.toString());
        System.out.println("STICA se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionBoolOp() " + op.getChildCount());

        for (int i = 0; i < op.getChildCount(); i++) {
            QueryNode child = op.getChild(i);
            System.out.println("STICABBOLOP se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionBoolOp() " + child.toString());
            System.out.println("STICABBOLOP se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionBoolOp() " + child.toString() + " has class " + child.getClass().getCanonicalName());
            if (child instanceof Expression) {
                children.add(getExpression((Expression) child));
            } else if (child instanceof ExpressionGroup) {
                return "[" + getExpressionGroupOp(op, " | ") + "]";
            }

        }
        System.out.println("STICABBOLOP se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionBoolOp() SIZE " + children.size());
        String ret = "";
        int i=0;
        for (String s : children) {
            System.out.println("STICABBOLOP se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionBoolOp() CH " + s);
            if (i<children.size()-1)
                ret=ret+s+opString;
            else
                ret=ret+s;
            i++;
        }
        System.out.println("STICABBOLOP se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionBoolOp() FINAL " + ret);
        return ret;//children.get(0) + opString + children.get(1);
    }

    private static String getExpressionGroupOp(final QueryNode op, final String opString) throws SRUException {
        List<String> children = new ArrayList<String>();
        System.out.println("STICA se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionGroupOp() " + op.toString());
        System.out.println("STICA se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionGroupOp() " + op.getChildCount());
        System.out.println("STICACLASSE se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionGroupOp() CLASSE " + op.getClass().getCanonicalName());
        String ret = "";
        for (int i = 0; i < op.getChildCount(); i++) {
            QueryNode child = op.getChild(i);
            System.out.println("STICACLASSE se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionGroupOp() CLASSE " + child.getClass().getCanonicalName());
            if (child instanceof ExpressionOr) {
                ret = getExpressionBoolOp(child, opString);
                System.out.println("STICAFIGA se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.getExpressionGroupOp() " + child.toString());
            }
//            if (child instanceof Expression) {
//                children.add(getExpression((Expression) child));
//            }
        }

        return ret; //children.get(0) + opString + children.get(1);
    }

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

    private static String translatePos(final String layerIdentifier, final String operator, final String pos) throws SRUException {

        CorporaInfo contextedCorpora = getCorporaInfo();

        Properties prop = getProp();

        StringBuffer buf = new StringBuffer();
        List<String> returnList = new ArrayList<String>();

        // GOOD the first 
        /*
        This method loops over passed corpora when the right translation is found
        the for cycle is breaked: otherwise it continues until the translator is found
         */
        for (String corpus : contextedCorpora.getCorpora().keySet()) {
            //buf = new StringBuffer();

            String tagset = Utilities.getTagSetFromCorpus(Utilities.getTagSetFromCorpusList(prop), corpus);
            //System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.translatePos() " + corpus + " and tagset " + tagset);
            LOG.debug("se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.translatePos() " + corpus + " and tagset '{}'", tagset);
            POSTranslator posTranslator = null;
            String logMess = "";
            try {
                posTranslator = POSTranslatorFactory.getPOSTranslator(tagset);
            } catch (SRUException ex) {
                java.util.logging.Logger.getLogger(Ilc4ClarinFCSToCQPConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.translatePos() "+pos+ " tagset "+ tagset);
                LOG.debug("se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.translatePos() corpus " + corpus + " and pos " + pos + " and tagset '{}'", tagset);
                List<String> sucT = posTranslator.toPos(pos);
                //System.out.println("****** se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.translatePos() "+pos+ " tagset "+ tagset+ " List "+returnList.toString());
                LOG.debug("se.gu.spraakbanken.fcs.endpoint.korp.cqp.Ilc4ClarinFCSToCQPConverter.translatePos() corpus " + corpus + " and pos " + pos + " and tagset " + tagset + " returned '{}'", returnList.toString());
                returnList.addAll(sucT);

                //break;
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

}
