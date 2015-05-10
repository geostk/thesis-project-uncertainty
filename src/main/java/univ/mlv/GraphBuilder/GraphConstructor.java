/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package univ.mlv.GraphBuilder;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import univ.mlv.development.CreateJSFiles;

/**
 *
 * @author Ordinateur1
 */
public class GraphConstructor {
    public static final String ns_rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final Namespace rdfNamespace = Namespace.getNamespace("rdf", ns_rdf);
    public static final String ns_v = "http://www.w3.org/2006/vcard/ns#";
    public static final Namespace vNamespace = Namespace.getNamespace("rdf", ns_v);
   public static final String ns_gs = "http://www.geolsemantics.com/onto#";
    public static final Namespace gsNamespace = Namespace.getNamespace("gs", ns_gs);
   
    private static List<Element> concept= new ArrayList<Element>();
    private static Map<String, Element> conceptMap= new TreeMap<String, Element>();
    private static Map<String,List<Element>> objProperty= new TreeMap<String,List<Element>>();
    private static Map<String,List<Element>> dataProperty= new TreeMap<String,List<Element>>();
    static Map<String,List<String>> instances= new TreeMap<String,List<String>>();
    private static String svg;
    private static String input_text="";
    private static Map<String, String> allGraphs = new TreeMap<String, String>();
    static int i=1; 
    // Document type JDom
    public static Document document;
    /**
     * *******************************************************************************
 Parser le documconceptt à partir d'une url contant le path du fichier à
 analyser
     *
     * @param url : chemin du documconceptt RDF
 *******************************************************************************
     */
    public static void ParserDoc(String url) throws JDOMException {
        try {
            SAXBuilder parser = new SAXBuilder();
            // Parse the document
            document = parser.build(url);
            // Process the root element
            process(document.getRootElement());
        } catch (IOException e) {
            System.out.println("IOException, the parser could not encode " + url + "\n L'exception genérée : " + e);
        }
    }
    
    public static void ParserString(String input) {

        try {
            InputStream in = new ByteArrayInputStream(input.getBytes("UTF-8"));
            SAXBuilder parser = new SAXBuilder();
            // Parse the document
            document = parser.build(in);
            // Process the root element
            process(document.getRootElement());
        } catch (Exception ex) {
            System.err.println(ex);
        }

    }// fin de parserText
        
    public static String getId(Element e){
        String id=
        //récupérer l'id
        e.getAttributeValue("nodeID", rdfNamespace);
        if (id == null) {
            id = e.getAttributeValue("about", rdfNamespace);
        }
        if (id == null) {
            id = e.getAttributeValue("resource", rdfNamespace);
        }
        if(id != null && id.contains(".com/")){
            id= id.split(".com/")[1].replaceAll("/", "_");
        }
        return id;
    }
    private static void process(Element rootElement) {
//        Element root2=rootElement;
//        //récupérer l'id
//        String nodeID_prop = rootElement.getAttributeValue("nodeID", rdfNamespace);
//        if (nodeID_prop == null) {
//            nodeID_prop = rootElement.getAttributeValue("about", rdfNamespace);
//        }
//        if (nodeID_prop == null) {
//            nodeID_prop = rootElement.getAttributeValue("resource", rdfNamespace);
//        }
//        if(nodeID_prop != null && nodeID_prop.contains(".com/")){
//            nodeID_prop= nodeID_prop.split(".com/")[1].replaceAll("/", "_");
//        }
        if(!rootElement.getName().equals("RDF") 
                && !rootElement.getName().equals("graph")
                && !rootElement.getName().equals("Text")){
          String name= rootElement.getName();
          boolean isConcept = false, isProperty= false;
          if(Character.isUpperCase(name.charAt(0))){
              isConcept=true;
          }else{
              isProperty=true;
          }
        String id = getId(rootElement);
        if(id==null){
            //c'est une property, si il n'a pas d'enfant alors c'est une propriété object
            //il faut récupérer son parent et sa valeur
            if(isProperty && rootElement.getChildren().isEmpty()){
                //c'est une datatype, l'ajouter à la liste des datatype
                if(dataProperty.keySet().contains(getId(rootElement.getParentElement()))){
                    dataProperty.get(getId(rootElement.getParentElement())).add(rootElement);
                }else{
                    List<Element> newList= new ArrayList<Element>();
                    newList.add(rootElement);
                    dataProperty.put(getId(rootElement.getParentElement()),newList);
                }
            }else if(isProperty ){
                //Si il y a des enfants, c'est que c'est une propriété object, le range n'est pas en id mais est l'enfant qui suit
                if(null!=rootElement.getParentElement() && null != getId(rootElement.getParentElement())
                        && objProperty.keySet().contains(getId(rootElement.getParentElement()))){
                    objProperty.get(getId(rootElement.getParentElement())).add(rootElement);
                }else{
                    List<Element> newList= new ArrayList<Element>();
                    newList.add(rootElement);
                    if(null==getId(rootElement.getParentElement())){
                        objProperty.put(rootElement.getParentElement().getName(),newList);
                    }
                    else{
                        objProperty.put(getId(rootElement.getParentElement()),newList);
                    }
                }
            }
        }else{
            //Si l'id est présent mais qu'il n'y a pas d'enfent, alors c'est une propriété objet avec un rdf about décrit
            if(rootElement.getChildren().isEmpty() && isProperty){
                if(objProperty.keySet().contains(getId(rootElement.getParentElement()))){
                    objProperty.get(getId(rootElement.getParentElement())).add(rootElement);
                }else if(isProperty ){
                    List<Element> newList= new ArrayList<Element>();
                    newList.add(rootElement);
                    objProperty.put(getId(rootElement.getParentElement()),newList);
                }
            }else if(isConcept){
                if(conceptMap.keySet().contains(id)){
//                    conceptMap.get(id).add(rootElement);
                }else{
                    conceptMap.put(id,rootElement);
                    concept.add(rootElement);
                }
            }
        }
             
        } 
        List content = rootElement.getContent();
        Iterator iterator = content.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof Element) {
                Element child = (Element) o;
                process(child);
            }
        }
        }

    /**
     * @return the input_text
     */
    public static String getInput_text() {
        return input_text;
    }

    /**
     * @param aInput_text the input_text to set
     */
    public static void setInput_text(String aInput_text) {
        input_text = aInput_text;
    }

    /**
     * @return the concept
     */
    public static List<Element> getConcept() {
        return concept;
    }

    /**
     * @param aConcept the concept to set
     */
    public static void setConcept(List<Element> aConcept) {
        concept = aConcept;
    }

    /**
     * @return the property
     */
    public static Map<String,List<Element>> getProperty() {
        return objProperty;
    }

    /**
     * @param aProperty the property to set
     */
    public static void setProperty(Map<String,List<Element>> aProperty) {
        objProperty = aProperty;
    }

    /**
     * @return the conceptMap
     */
    public static Map<String, Element> getConceptMap() {
        return conceptMap;
    }

    /**
     * @param aConceptMap the conceptMap to set
     */
    public static void setConceptMap(Map<String, Element> aConceptMap) {
        conceptMap = aConceptMap;
    }
    
    private static String jsPath ="";

    /**
     * @return the jsPath
     */
    public static String getJsPath() {
        return jsPath;
    }

    /**
     * @param aJsPath the jsPath to set
     */
    public static void setJsPath(String aJsPath) {
        jsPath = aJsPath;
    }
    
    public GraphConstructor(String rdf_input) throws IOException{
        concept= new ArrayList<Element>();
        conceptMap= new TreeMap<String, Element>();
        objProperty= new TreeMap<String,List<Element>>();
        ParserString(rdf_input);
        CreateJSFiles c= new CreateJSFiles(conceptMap, objProperty, dataProperty);
            CreateJSFiles.createJSGraph();
    
        setJsPath(CreateJSFiles.getJsPath());
//        CompleteTheGraph p = new CompleteTheGraph("en");
//        String g=p.designFullGraph(); 
//        String buildTheGraph = p.buildTheGraph(g, "");
//        setSvg(buildTheGraph);
//        svg=buildTheGraph;
    }
    public GraphConstructor(String rdf_input, String lang, String input) throws JDOMException, IOException{
        concept= new ArrayList<Element>();
        objProperty= new TreeMap<String,List<Element>>();
        input_text=input;
        ParserString(rdf_input);
        CompleteTheGraph p = new CompleteTheGraph(lang);
//        p.mapNode_url();
//        p.completeHref();
        String g=p.designFullGraph();
//        p.buildTheGraph(g, "");
        svg= p.getGraph(g, "");
        allGraphs=p.getAllGraphs();
        
    }
    public GraphConstructor(String rdf_input, String lang, String input, String[] opt) throws JDOMException, IOException{
        concept= new ArrayList<Element>();
        objProperty= new TreeMap<String,List<Element>>();
        input_text=input;
        ParserString(rdf_input);
        CompleteTheGraph p = new CompleteTheGraph(lang);
        p.mapNode_url();
//        p.completeHref();
        String g=p.designGraphWithOptions(opt);
//        p.buildTheGraph(g, "");
        svg= p.getGraph(g, "");
        allGraphs=p.getAllGraphs();
        
    }
    
    /**
     * @param args the command line argumconceptts
     */
    public static void main(String[] args) throws JDOMException, IOException {
        // TODO code application logic here
        GraphConstructor d= new GraphConstructor();
        GraphConstructor.ParserDoc("./test.rdf");
        CompleteTheGraph p = new CompleteTheGraph("en");
//        p.test();
//        String g= p.designGraphForOneNode("id4Company");
//        p.buildTheGraph(g, "id4Company");
//        p.mapNode_url();
//        p.completeHref();
        String g=p.designFullGraph();
        p.buildTheGraph(g, "");
    }

    private GraphConstructor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the svg
     */
    public String getSvg() {
        return svg;
    }

    /**
     * @param svg the svg to set
     */
    public void setSvg(String svg) {
        this.svg = svg;
    }

    /**
     * @return the allGraphs
     */
    public static Map<String, String> getAllGraphs() {
        return allGraphs;
    }

    /**
     * @param allGraphs the allGraphs to set
     */
    public void setAllGraphs(Map<String, String> allGraphs) {
        this.allGraphs = allGraphs;
    }

    
    
}
