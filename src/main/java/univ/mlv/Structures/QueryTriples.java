/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.Structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author fadhela-pc
 */
public class QueryTriples {

    List<String> listTriples = new ArrayList<String>();
    String query = "";
    String prefixes = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
            + "\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
            + "\nPREFIX gs: <http://www.geolsemantics.com/onto#>"
            + "\nPREFIX ical: <http://www.w3.org/2002/12/cal/icaltzd#>"
            + "\nPREFIX wn: <http://www.w3.org/2006/03/wn/wn20/>"
            + "\nPREFIX foaf: <http://xmlns.com/foaf/0.1/>"
            + "\nPREFIX rdfg: <http://www.w3.org/2004/03/trix/rdfg-1>"
            + "\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
            + "\nPREFIX v: <http://www.w3.org/2006/vcard/ns#>"
            + "\nPREFIX doac: <http://ramonantonio.net/doac/0.1/>"
            + "\nPREFIX prov: <http://www.w3.org/ns/prov#>\n";

    /**
     * récupérer les triplets contenus dans la requête, chaque triplet se
     * termine par un .
     *
     * @param query
     */
    public QueryTriples(String query) {
        this.query = query;
        query = query.replaceFirst("where\\{", "where \\{");
        query = query.replaceFirst("Where\\{", "where \\{");
        query = query.replaceFirst("Where \\{", "where \\{");
        query = query.replaceFirst("WHERE \\{", "where \\{");
        String split_where = query.split("where \\{")[1];//Prendre la partie après le where pour ne garder que les triplets concerné par la clause where
//        String[] split_dot = new String[split_where.split("\\.").length];
        String[] split_dot = split_where.split("\\.");
        //découpage en triplets
        for (String s : split_dot) {
            if (s.startsWith(" ")) {
                s = s.replaceFirst(" ", "");
            }
            if (!s.contains("{") && !s.contains("}")) {
                listTriples.add(s);
            }
        }
    }

    /**
     * Keep all the rdf:type if the query
     *
     * @return
     */
    public Map<String, String> keepRdfType() {
        Map<String, String> res = new TreeMap<String, String>();
        for (String s : listTriples) {
            s = s.replaceAll("\r|\t|\n", "");
            if (s.contains("rdf:type")) {
                res.put(s.split(" ")[0], s);
            }
        }
        return res;
    }

    /**
     * keep all the triples of the form ?s ?p ?o
     *
     * @return
     */
    public Map<String, String> keepUnknownTriples() {
        Map<String, String> res = new TreeMap<String, String>();
        for (String s : listTriples) {
            s = s.replaceAll("\r|\t|\n", "");
            boolean find = true;
            String[] split_espace = s.split(" ");
            for (String ss : split_espace) {
                if (!ss.startsWith("?")) {
                    find = false;
                    break;
                }
            }
            if (find) {
                res.put(s, s);
            }
        }
        return res;
    }

    /**
     * réecriture des triplets pour la prise en compte des uncertitudes en
     * ajoutant des optionnels
     *
     * @return
     */
    public String rewriteQuery() {
        String res = "";
        int i = 1;
        Map<String, String> m = keepRdfType();
        Map<String, String> d = keepUnknownTriples();
//        res=res.replaceAll("\\?", "\\\\\\\\?");
        for (String s : listTriples) {
            s = s.replaceAll("\r|\t|\n", "");
            String rw = "";
            Triples t = new Triples(s, Integer.toString(i));
//            if(!s.contains("rdf:type"))
            String s_type = "";
            String o_type = "";
            if (!s.contains("rdf:type") && !d.keySet().contains(s)) {
                String findS = "";
                String findO = "";
                //Vérifier si on veut détailler le sujet et/ou l'objet ==> ?s ?p ?o
                for (String dd : d.keySet()) {
                    for (String d1 : dd.split(" ")) {
                        if (d1.contains(s.split(" ")[0])) {
                            findS = dd;
                        }
                        if (d1.contains(s.split(" ")[2])) {
                            findO = dd;
                        }
                    }
                }
                if (m.containsKey(s.split(" ")[0])) {
                    s_type = m.get(s.split(" ")[0]) + ".";
                }
                if (m.containsKey(s.split(" ")[2])) {
                    o_type = m.get(s.split(" ")[2]) + ".";
                }
                rw = rw + "\n{" + t.caseO(s_type, o_type, findS, findO) + "} "
                        + "\tUNION {\n" + t.case1(s_type, o_type, findS, findO) + "}\n\t"
                        + "UNION{\n\t\t" + t.case2(s_type, o_type, findS, findO) + "\t\t} \n\t"
                        + "UNION {\n\t\t" + t.case3(s_type, o_type, findS, findO) + "\t\t}";
            } else {
//                res=res.replaceFirst(s.replaceAll("\\?", "\\\\\\?")+"\\.", "");
                continue;
            }
            res = res + rw;
//            Pattern p = Pattern.compile(s.replaceAll("\\?", "\\\\\\\\?"));
//            s = s.replaceAll("\\?", "\\\\\\?");
//
//            res = res.replaceFirst(s + ".", rw);
        }

        //récupérer les weight 
        String weights = "";
        for (String s : res.split(" ")) {
            if (s.contains("weight") && !s.contains(":")) {
                if (s.contains(".")) {
                    s = s.split("\\.")[0];
                }
                if (!weights.contains(s)) {
                    weights = weights + " " + s;
                }
            }
        }
        String select = "";
        if (query.toLowerCase().contains("select distinct ")) {
            select = query.substring(query.toLowerCase().indexOf("select distinct ")
                    + "select distinct ".length(), query.toLowerCase().indexOf("where"));
        } else {
            if (query.toLowerCase().contains("select")) {
                select = query.substring(query.toLowerCase().indexOf("select ")
                        + "select ".length(), query.toLowerCase().indexOf("where"));
            }
        }

        if (!select.contains("*")) {
            select = select.replaceFirst(select.replaceAll("\\?", "\\\\\\?"), weights.substring(1) + " " + select);
        }

        //Construct new query
        res = prefixes + "\nSelect distinct " + select + " Where{\n" + res + "\n}";

//        res.replaceAll("", res);
        return res;
    }

    public static void main(String[] args) {
        String query = "PREFIX gs: <http://www.geolsemantics.com/onto#>\n"
                + " PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + " PREFIX  v: <http://www.w3.org/2006/vcard/ns#>\n"
                + " Select * \n"
                + "Where { "
                + "?s ?p ?o."
                + "?s1 ?p1 ?o1."
                + "} ";

        QueryTriples q = new QueryTriples(query);
        System.out.println("The rewriting of the query : \n" + q.rewriteQuery());
    }
}
