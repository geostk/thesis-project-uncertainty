/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.development;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import univ.mlv.Structures.QueryTriples;

/**
 *
 * @author Fadhela
 */
public class Queries {

    private OntModel ontologie;
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
    private String query;

    /**
     * crÃ©er le model RDF pour pouvoir exÃ©cuter les requÃªtes
     *
     * @param rdf
     * @param query
     */
    public Queries(String rdf, String query) {
        ontologie = ModelFactory.createOntologyModel();
        InputStream stream = new ByteArrayInputStream(rdf.getBytes(StandardCharsets.UTF_8));
        ontologie.read(stream, null, null);
        this.query = query;
    }

    public List<String> triples() {
        QueryTriples t = new QueryTriples(query);
        return t.getListTriples();
    }

    /**
     * Create a hasUncertainProp Option on the property of the given triple
     *
     * @param t
     * @return
     */
    public String addHasUncertainProp(String t) {

        String s = t.split("\t")[0];
        String p = t.split("\t")[1];
        String o = t.split("\t")[2];
        return s + " gs:hasUncertainProp ?u."
                + "?u " + p + " " + o + "."
                + "?u gs:weight ?weight" + p + ".";

    }

    public String RunQueryWithUncertainty() {
        String result = "<table>";
        List<String> allUncertainProp = getAllUncertainPred();
        List<String> triples = triples();
        List<String> optToAdd = new ArrayList<String>();
        for (String t : triples) {
            if(t.contains("rdf:type")){
                continue;
            }
            String s = t.split(" ")[0];
            String p = t.split(" ")[1];
            String o = t.split(" ")[2];
            for (String ups : allUncertainProp) { //attention d'un coté on a http://www.geolsemantics.com/onto#event-cause et de l'autre gs:event-cause faut trouver un moeyn de gérer ça
                if (ups.split("\t")[1].equals(p)) {
                    optToAdd.add(t);
                }
            }
        }
        //ajouter les optionnels
        String res = "";
        for (String s : optToAdd) {
            if (res.equals("")) {
                res = "UNION {" + addHasUncertainProp(s) + " } ";
            } else {
                res = res + " UNION { " + addHasUncertainProp(s) + " } ";
            }

        }
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
        query = query.replace("}", res + "}");
        String select = "";

        String querySelectAll = "";
        if (query.toLowerCase().contains("select distinct ")) {
            select = query.substring(query.toLowerCase().indexOf("select distinct ")
                    + "select distinct ".length(), query.toLowerCase().indexOf("where"));
        } else {
            if (query.toLowerCase().contains("select")) {
                select = query.substring(query.toLowerCase().indexOf("select ")
                        + "select ".length(), query.toLowerCase().indexOf("where"));
            }
        }

        if (!select.contains("*") && !weights.equals("")) {
            select = select.replaceFirst(select.replaceAll("\\?", "\\\\\\?"), weights.substring(1) + " " + select);

        }
        querySelectAll = query.replace(select, "* ");
        List<String> uncertainties = new ArrayList<String>();
        List<String> executeSelectQuery = executeSelectQuery(querySelectAll);
        List<String> allUncertainResSub = getAllUncertainResSub();
        for (String s : executeSelectQuery) {
            if (s.split("\t").length > 1) {
                for (String UncSub : allUncertainResSub) {
                    if (UncSub.split("\t").length > 1) {
                        if (s.split("\t")[0].equals(UncSub.split("\t")[0])) {
                            uncertainties.add(s + "\t" + UncSub.split("\t")[3]);
                        }
                    }
                }

            }
        }
        //Construct new query
//        res = prefixes + "\nSelect distinct " + select + " Where{\n" + res + "\n}";
        List<String> executeSelectQuery1 = executeSelectQuery(query);
        executeSelectQuery1.addAll(uncertainties);
        for (String s : executeSelectQuery1) {
            result = result + "<tr><td>" + s + "</td>"
                    //                    + "<td>"++"</td>"
                    + "</tr>";
        }
        return result + "</table>";
    }

    /**
     * Get All resources which are uncertain in the rdf graph
     *
     * @return list of all uncertain resources
     */
    public List<String> getAllUncertainResSub() {
//        String query="Select * Where {?s gs:isUncertain ?u. Optional {?s ?p ?o.} Optional {?s1 ?p1 ?s.}}";
        String query = "Select ?s ?p ?o ?w \n"
                + "Where {\n"
                + "?u gs:isUncertain ?s. \n"
                + "?s ?p ?o.\n"
                + "?u gs:weight ?w.}";
        return executeSelectQuery(prefixes + query);
    }

    /**
     * Get all uncertain resources which are object uncertain
     *
     * @return
     */
    public List<String> getAllUncertainResObj() {
//        String query="Select * Where {?s gs:isUncertain ?u. Optional {?s ?p ?o.} Optional {?s1 ?p1 ?s.}}";
        String query = "Select ?s ?p ?o ?w Where {"
                + "?s gs:isUncertain ?u. "
                + "?s1 ?p1 ?s."
                + "?u gs:weight ?w}";
        return executeSelectQuery(query);
    }

    /**
     * Get all uncertain predicates which are gs:hasUncertainProp
     *
     * @return
     */
    public List<String> getAllUncertainPred() {
        String query = "Select ?s ?p ?o ?w "
                + "Where {\n"
                + "?s gs:hasUncertainProp ?u. \n"
                + "?u ?p ?o. \n"
                + "?u gs:weight ?w.\n"
                + "}";
        return executeSelectQuery(prefixes + query);
    }

    /**
     * Execute an ASK query
     *
     * @param query
     * @return
     */
    public boolean executeAskQuery(String query) {
        Query query1 = QueryFactory.create(query);
        QueryExecution qexec = QueryExecutionFactory.create(query1, ontologie);
        try {
            if (query.toLowerCase().contains("ask")) {
                return (qexec.execAsk());
            }
        } finally {
            qexec.close();
        }
        return false;
    }

    /**
     * Execute a SELECT query
     *
     * @param query
     * @return
     */
    public List<String> executeSelectQuery(String query) {
        List<String> res = new ArrayList<String>();
        Query query1 = QueryFactory.create(query);
        QueryExecution qexec = QueryExecutionFactory.create(query1, ontologie);
        try {
            if (query.toLowerCase().contains("select")) {
                ResultSet results = qexec.execSelect();
//                List<String> resultVars = results.getResultVars();
//                 ResultSetFormatter.out(System.out, results, query1);
//                res = "<table border=\"1\"><tr><th>Variable</th><th>Value</th></tr>";
                while (results.hasNext()) {
                    QuerySolution next = results.next();
                    Iterator<String> varNames = next.varNames();
                    String var = "";
                    String values = "";
                    while (varNames.hasNext()) {
                        String next1 = varNames.next(); // nom de la variable dans le select
                        if (next.get(next1).equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#")) {
                            continue;
                        }
                        if (var.equals("")) {
                            var = next1;
                        } else {
                            var = var + "\t" + next1;
                        }
                        if (values.equals("")) {
                            values = next.get(next1).toString();
                        } else {
                            values = values + "\t" + next.get(next1).toString();
                        }
                    }
                    res.add(values);
                }
            }
        } finally {
            qexec.close();
        }
        return res;
    }

    public String executeQuery(String query, String t) {
        String res = "";
        Query query1 = QueryFactory.create(query);
        QueryExecution qexec = QueryExecutionFactory.create(query1, ontologie);
        Pattern p = Pattern.compile("([0-9]+\\.[0-9])");

        try {
            if (query.toLowerCase().contains("ask")) {
                boolean resultsASK = qexec.execAsk();
                if (resultsASK) {
                    return "true";
                } else {
                    return "false";
                }
            } else if (query.toLowerCase().contains("select")) {
                ResultSet results = qexec.execSelect();
                res = "<table border=\"1\"><tr><th>Variable</th><th>Value</th></tr>";
                while (results.hasNext()) {
                    QuerySolution next = results.next();
                    Iterator<String> varNames = next.varNames();

                    while (varNames.hasNext()) {
                        String next1 = varNames.next(); // nomde la variable dans le select
                        System.out.println("next1= " + next1);
                        String e = next.get(next1).toString(); // valeur que prend  la variable
                        //ignorer les rdf:type de type resource, owl:prop....
                        if (e.equals("http://www.w3.org/2000/01/rdf-schema#Class")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#Property")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#Datatype")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#List")
                                || e.equals("http://www.w3.org/2004/03/trix/rdfg-1Graph")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#subPropertyOf")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#range")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#Resource")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#Literal")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#label")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#Statement")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#subClassOf")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#subject")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#object")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#nil")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#range")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#rest")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#first")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#comment")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#ContainerMembershipProperty")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#seeAlso")
                                || e.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#seeAlso")
                                || e.equals("http://www.w3.org/2000/01/rdf-schema#domain")) {
                            continue;
                        }
                        if (t != null && !t.isEmpty()) { // appliquer le trust
//                            String[] split_res=next.get(next1).toString().split(" ");
//                            for(String s:split_res){
                            Matcher m = p.matcher(next.get(next1).toString());
                            if (m.find()) {
                                float parseInt = Float.parseFloat(next.get(next1).toString());
                                float parseInt1 = Float.parseFloat(t);
                                e = Float.toString(parseInt * parseInt1);
                            }
//                            res = res + "<tr> <td><b>" + next1 + ": </b></td><td>" + e+"</td></tr>";
//                            }
                        }// else 
                        {
                            res = res = res + " <tr> <td><b>" + next1 + ": </b></td><td>" + e + "</td></tr>";
                        }
                    }
                    res = res = res + " <tr> <td>New line</td></tr>";
                }

                res = res + "</table>";
                ResultSetFormatter.out(System.out, results, query1);
            } else {//Cas du describe
                Model describeModel = qexec.execDescribe();

                res = describeModel.toString();
            }
        } finally {
            qexec.close();
        }

        return res;
    }

    /**
     * List of all subproperties of the predicate parameter
     *
     * @param prop prefix namespace : name of the propoety
     * @return list<prefix:prop_name>
     */
    public List<String> listSubProp(String prop) {
        List<String> l = new ArrayList<String>();
        ExtendedIterator<OntProperty> listAllOntProperties = ontologie.listAllOntProperties();
        while (listAllOntProperties.hasNext()) {
            OntProperty next = listAllOntProperties.next();
            if (next.getLocalName().equals(prop.split(":")[1]) && next.getNameSpace().equals(prop.split(":")[0])) {
                ExtendedIterator<? extends OntProperty> listSubProperties = next.listSubProperties();
                while (listSubProperties.hasNext()) {
                    OntProperty next2 = listSubProperties.next();
                    l.add(next2.getNameSpace() + ":" + next2.getLocalName());
                }
            }
        }
        return l;
    }

    /**
     * Proposer des sous propriÃ©tÃ©s du prÃ©dicat de triplet, si le rÃ©sultat
     * du ASK=true c'est Ã  dire que le triplet existe dans le graphe
     *
     * @param triple
     * @return
     */
    public List<String> hintPredicats(String triple) {
        String pred = triple.split(" ")[1];
        List<String> listSubProp = listSubProp(pred);
        List<String> propositions = new ArrayList<String>();
        for (String s : listSubProp) {
            //execute an ask of the triple and if the result is true than keep this subprop
            String askQuery = prefixes + "\n Ask Where { " + triple.replaceFirst(pred, s) + " }";
            if (executeAskQuery(askQuery)) {
                propositions.add(s);
            }
        }
        return propositions;
    }

}
