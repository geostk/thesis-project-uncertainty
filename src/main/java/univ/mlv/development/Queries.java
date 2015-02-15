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
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fadhela
 */
public class Queries {
private static OntModel ontologie;
    public Queries( String rdf){
        ontologie = ModelFactory.createOntologyModel();
        InputStream stream = new ByteArrayInputStream(rdf.getBytes(StandardCharsets.UTF_8));
        ontologie.read(stream, null, null);
    }
    
    public static List<String> getAllUncertainResSub(){
//        String query="Select * Where {?s gs:isUncertain ?u. Optional {?s ?p ?o.} Optional {?s1 ?p1 ?s.}}";
        String query="Select * Where {?s gs:isUncertain ?u. ?s ?p ?o.}";
        return executeSelectQuery(query);
    }
 
    public static List<String> getAllUncertainResObj(){
//        String query="Select * Where {?s gs:isUncertain ?u. Optional {?s ?p ?o.} Optional {?s1 ?p1 ?s.}}";
        String query="Select * Where {?s gs:isUncertain ?u. ?s1 ?p1 ?s.}";
        return executeSelectQuery(query);
    }
    
    public static List<String> getAllUncertainPred(){
        String query="Select ?s ?p ?o Where {?s gs:hasUncertainProp ?u. ?s1 ?p ?o.}";
        return executeSelectQuery(query);
    }
    
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
}
