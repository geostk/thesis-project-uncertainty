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
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author Fadhela
 */
public class Queries {
private static OntModel ontologie;
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


    /**créer le model RDF pour pouvoir exécuter les requêtes
     * 
     * @param rdf 
     */
    public Queries( String rdf){
        ontologie = ModelFactory.createOntologyModel();
        InputStream stream = new ByteArrayInputStream(rdf.getBytes(StandardCharsets.UTF_8));
        ontologie.read(stream, null, null);
    }
    
    /**
     * Get All resources which are uncertain in the rdf graph
     * @return list of all uncertain resources
     */
    public static List<String> getAllUncertainResSub(){
//        String query="Select * Where {?s gs:isUncertain ?u. Optional {?s ?p ?o.} Optional {?s1 ?p1 ?s.}}";
        String query="Select * Where {?s gs:isUncertain ?u. ?s ?p ?o.}";
        return executeSelectQuery(query);
    }
 
    /**
     * Get all uncertain resources which are object uncertain
     * @return 
     */
    public static List<String> getAllUncertainResObj(){
//        String query="Select * Where {?s gs:isUncertain ?u. Optional {?s ?p ?o.} Optional {?s1 ?p1 ?s.}}";
        String query="Select * Where {?s gs:isUncertain ?u. ?s1 ?p1 ?s.}";
        return executeSelectQuery(query);
    }
    
    /**
     * Get all uncertain predicates which are gs:hasUncertainProp
     * @return 
     */
    public static List<String> getAllUncertainPred(){
        String query="Select ?s ?p ?o Where {?s gs:hasUncertainProp ?u. ?s1 ?p ?o.}";
        return executeSelectQuery(query);
    }
    
    /**
     * Execute an ASK query
     * @param query
     * @return 
     */
    public boolean executeAskQuery(String query){
        Query query1 = QueryFactory.create(query);
        QueryExecution qexec = QueryExecutionFactory.create(query1, ontologie);
        try {
                if (query.toLowerCase().contains("ask")) {
                    return(qexec.execAsk());
                }
        } finally {
            qexec.close();
        }
        return false;
    }
    
    /**
     * Execute a SELECT query
     * @param query
     * @return 
     */
    public static List<String> executeSelectQuery(String query) {
        List<String> res = new ArrayList<String>(); 
        Query query1 = QueryFactory.create(query);
        QueryExecution qexec = QueryExecutionFactory.create(query1, ontologie);
        try {
                if (query.toLowerCase().contains("select")) {
                ResultSet results = qexec.execSelect();
//                res="<table border=\"1\"><tr><th>Variable</th><th>Value</th></tr>";
                while (results.hasNext()) {
                    QuerySolution next = results.next();
                    String sol="";
                    Iterator<String> varNames = next.varNames();
                    while (varNames.hasNext()) {
                        String next1 = varNames.next(); // nomde la variable dans le select
                        String e = next.get(next1).toString(); // valeur que prend  la variable
                        {
                            if(sol.equals("")){
                                sol=next1 + ":" + e;
                            }
                            else{
                                sol=sol+"\t"+next1 + ":" + e;
                            }
                        }
                    }
                        res.add(sol);
                }
                
                ResultSetFormatter.out(System.out, results, query1);
            } 
        } finally {
            qexec.close();
        }

        return res;
    }

    /**
     * List of all subproperties of the predicate parameter
     * @param prop prefix namespace : name of the propoety
     * @return list<prefix:prop_name>
     */
    public static List<String> listSubProp(String prop){
        List<String> l =new ArrayList<String>();
    ExtendedIterator<OntProperty> listAllOntProperties = ontologie.listAllOntProperties();
    while(listAllOntProperties.hasNext()){
            OntProperty next = listAllOntProperties.next();
        if(next.getLocalName().equals(prop.split(":")[1]) && next.getNameSpace().equals(prop.split(":")[0])){
                ExtendedIterator<? extends OntProperty> listSubProperties = next.listSubProperties();
            while(listSubProperties.hasNext()){
                    OntProperty next2 = listSubProperties.next();
                    l.add(next2.getNameSpace()+":"+next2.getLocalName());
            }
        }
    }
    return l;
    }
    
    
    /**
     * Proposer des sous propriétés du prédicat de triplet, si le résultat du ASK=true c'est à dire que le triplet existe dans le graphe
     * @param triple
     * @return 
     */
    public List<String> hintPredicats(String triple){
        String pred= triple.split(" ")[1];
        List<String> listSubProp = listSubProp(pred);
        List<String> propositions = new ArrayList<String>();
        for(String s:listSubProp){
            //execute an ask of the triple and if the result is true than keep this subprop
            String askQuery= prefixes+"\n Ask Where { "+triple.replaceFirst(pred, s)+" }";
            if(executeAskQuery(askQuery)) {
                propositions.add(s);
            }
        }
        return propositions;
    }
    
    
    
}
