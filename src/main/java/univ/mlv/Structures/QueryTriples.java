/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.Structures;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author fadhela-pc
 */
public class QueryTriples {

    List<String> listTriples = new ArrayList<String>();
    String query = "";

    public QueryTriples(String query) {
        this.query = query;
        query=query.toLowerCase().replaceFirst("where\\{", "where \\{");
        String split_where = query.toLowerCase().split("where \\{")[1];//Prendre la partie après le where pour ne garder que les triplets concerné par la clause where
//        String[] split_dot = new String[split_where.split("\\.").length];
        String[] split_dot = split_where.split("\\.");
        //découpage en triplets
        for (String s : split_dot) {
            if(s.startsWith(" ")) {
                s=s.replaceFirst(" ", "");
            }
            if (!s.contains("{") && !s.contains("}")) {
                listTriples.add(s);
            }
        }
    }

    /**
     * réecriture des triplets pour la prise en compte des uncertitudes en
     * ajoutant des optionnels
     *
     * @return
     */
    public String rewriteQuery() {
        String res = query;
        int i = 1;
//        res=res.replaceAll("\\?", "\\\\\\\\?");
        for (String s : listTriples) {
        String rw = "";
            Triples t = new Triples(s, Integer.toString(i));
            rw = rw + "\n" + t.caseO() + "\n" + t.case1() + "\n" + t.case2() + "\n" + t.case3();
//            Pattern p = Pattern.compile(s.replaceAll("\\?", "\\\\\\\\?"));
            s=s.replaceAll("\\?", "\\\\\\?");
 
            res=res.replaceFirst(s+".", rw);
        }
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
        System.out.println("The rewriting of the query : \n"+q.rewriteQuery());
    }
}
