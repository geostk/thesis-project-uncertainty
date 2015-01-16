/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.OntologyManagment;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.iri.impl.Main;

/**
 *    
 * @author fadhela-pc
 */
public class OntologyParsing {

    final String NS = "http://www.geolsemantics.com/onto#";
    String foaf = "http://xmlns.com/foaf/0.1/";
    static OntModel ontologie;
    public static List<Map<String, String>> classes= new ArrayList();
    public static List<Map<String, String>> objProp= new ArrayList();
    public static List<Map<String, String>> dataProp= new ArrayList();
    public static Map<String, List<String>> class_prop= new TreeMap<String, List<String>>();
    public static Map<String, List<String>> subclasses= new TreeMap<String, List<String>>();
    /**
     * @return the classes
     */
    public static List<Map<String, String>> getClasses() {
        ExtendedIterator<OntClass> listClasses = ontologie.listClasses();
        while(listClasses.hasNext()){
            OntClass next = listClasses.next();
            Map<String, String> m= new TreeMap<String, String>();
            m.put("labelfr", next.getLabel("fr"));
            m.put("labelen", next.getLabel("en"));
            m.put("label", next.getLabel(""));
            m.put("classeName", next.getLocalName());
            m.put("namespace", next.getNameSpace());
            classes.add(m);
        }
        return classes;
    }

    /**
     * @param aClasses the classes to set
     */
    public static void setClasses(List<Map<String, String>> aClasses) {
        classes = aClasses;
    }

    /**
     * @return the objProp
     */
    public static List<Map<String, String>> getObjProp() {
        ExtendedIterator<ObjectProperty> listObjProp = ontologie.listObjectProperties();
        while(listObjProp.hasNext()){
            ObjectProperty next = listObjProp.next();
            Map<String, String> m= new TreeMap<String, String>();
            m.put("labelfr", next.getLabel("fr"));
            m.put("labelen", next.getLabel("en"));
            m.put("label", next.getLabel(""));
            m.put("objPropName", next.getLocalName());
            m.put("namespace", next.getNameSpace());
            objProp.add(m);
        }
        return objProp;
    }

    /**
     * @param aObjProp the objProp to set
     */
    public static void setObjProp(List<Map<String, String>> aObjProp) {
        objProp = aObjProp;
    }

    /**
     * @return the dataProp
     */
    public static List<Map<String, String>> getDataProp() {
        ExtendedIterator<DatatypeProperty> listDataProp = ontologie.listDatatypeProperties();
        while(listDataProp.hasNext()){
            DatatypeProperty next = listDataProp.next();
            Map<String, String> m= new TreeMap<String, String>();
            m.put("labelfr", next.getLabel("fr"));
            m.put("labelen", next.getLabel("en"));
            m.put("label", next.getLabel(""));
            m.put("dataPropName", next.getLocalName());
            m.put("namespace", next.getNameSpace());
            dataProp.add(m);
        }
        return dataProp;
    }

    /**
     * @param aDataProp the dataProp to set
     */
    public static void setDataProp(List<Map<String, String>> aDataProp) {
        dataProp = aDataProp;
    }

    
    /**
     * Le schema est du style class-name -> prop-name\trange
     * @return the class_prop
     */
    public static Map<String, List<String>> getClass_prop() {
//        ExtendedIterator<ObjectProperty> listObjProp = ontologie.listObjectProperties();
//        ExtendedIterator<DatatypeProperty> listDataProp = ontologie.listDatatypeProperties();
        ExtendedIterator<OntClass> listClass = ontologie.listClasses();
//        Map<String, List<String>> class_prop =new TreeMap<String, List<String>>();
        while(listClass.hasNext()){
            OntClass next = listClass.next();
            ExtendedIterator<OntProperty> listDeclaredProperties = next.listDeclaredProperties();
            while(listDeclaredProperties.hasNext()){
                OntProperty next1 = listDeclaredProperties.next();
                OntResource range = next1.getRange();
                String range_name="";
                if(range!=null){
                    range_name=range.getLocalName();
                }
                String name=next1.getLocalName();
                
                if(class_prop.containsKey(next.getLocalName())){
                    class_prop.get(next.getLocalName()).add(name+"\t"+range_name);
                }else{
                    List<String> l= new ArrayList<String>();
                    l.add(name+"\t"+range_name);
                    class_prop.put(next.getLocalName(), l);
                }
            }
        }
        return class_prop;
    }

    /**
     * @param aClass_prop the class_prop to set
     */
    public static void setClass_prop(Map<String, List<String>> aClass_prop) {
        class_prop = aClass_prop;
    }
    

    /**
     * @return the subclasses
     */
    public static Map<String, List<String>> getSubclasses() {
        ExtendedIterator<OntClass> listClass = ontologie.listClasses();
        while(listClass.hasNext()){
            OntClass next = listClass.next();
            ExtendedIterator<OntClass> listSubClasses = next.listSubClasses();
            while(listSubClasses.hasNext()){
                OntClass next1 = listSubClasses.next();
                String name= next1.getLocalName();
                if(subclasses.containsKey(next.getLocalName())){
                    subclasses.get(next.getLocalName()).add(name);
                }else{
                    List<String> l= new ArrayList<String>();
                    l.add(name);
                    subclasses.put(next.getLocalName(), l);
                }
            }
        }
        return subclasses;
    }

    /**
     * @param aSubclasses the subclasses to set
     */
    public static void setSubclasses(Map<String, List<String>> aSubclasses) {
        subclasses = aSubclasses;
    }
    
    public OntologyParsing(){
        ontologie = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            //Charger l'ontologie à partie d'un fichier owl
        InputStream in = null;
        try {
            in = OntologyParsing.class.getClassLoader().getResourceAsStream("../GEOLSemanticsOntology.owl");
                  //  new FileInputStream("GEOLSemanticsOntology.owl");
//            URL url  = OntologyParsing.this.getClass().getClassLoader().getResource("../GEOLSemanticsOntology.owl");
         
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("The EXCEPTION : " + ex);
        }
        ontologie.read(in, null, null);
    }

    
    
}
