/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.GraphBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.transform.JDOMSource;

/**
 *
 * @author Ordinateur1
 */
public class CompleteTheGraph {

    public static final String ns_v = "http://www.w3.org/2006/vcard/ns#";
    public static final Namespace vNamespace = Namespace.getNamespace("v", ns_v);
    public static final String ns_ical = "http://www.w3.org/2002/12/cal/icaltzd#";
    public static final Namespace icalNamespace = Namespace.getNamespace("ical", ns_ical);
    public static final String ns_rdfs = "http://www.w3.org/2000/01/rdf-schema#";
    public static final Namespace rdfsNamespace = Namespace.getNamespace("rdfs", ns_rdfs);
    public static final String ns_rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final Namespace rdfNamespace = Namespace.getNamespace("rdf", ns_rdf);
    public static final String ns_gs = "http://www.geolsemantics.com/onto#";
    public static final Namespace gsNamespace = Namespace.getNamespace("gs", ns_gs);
    public static final String ns_svg = "http://www.w3.org/2000/svg";
    public static final Namespace svgNamespace = Namespace.getNamespace("svg", ns_svg);

    /**
     * @return the nodes_relatations
     */
    public static Map<String, ArrayList<String>> getNodes_relatations() {
        return nodes_relatations;
    }

    /**
     * @param aNodes_relatations the nodes_relatations to set
     */
    public static void setNodes_relatations(Map<String, ArrayList<String>> aNodes_relatations) {
        nodes_relatations = aNodes_relatations;
    }
    static Map<String, List<String>> node_edges = new TreeMap<String, List<String>>();
    static Map<String, String> nodeID_node = new TreeMap<String, String>();
    static Map<String, List<String>> edges = new TreeMap<String, List<String>>();

    /**
     * @return the lang
     */
    public static String getLang() {
        return lang;
    }

    /**
     * @param aLang the lang to set
     */
    public static void setLang(String aLang) {
        lang = aLang;
    }

    Map<String, String> nodes_url = new TreeMap<String, String>();
    private static Map<String, ArrayList<String>> nodes_relatations = new TreeMap<String, ArrayList<String>>();

    public static Map<String, String> nodes_graph = new TreeMap<String, String>();

    private String actual_graph = "";

    private static Map<String, String> allGraphs = new TreeMap<String, String>();

    private static String lang;
    static String relativPath = getAppDirectory().replaceAll("\\\\", "/");//.split("build")[0];

    public CompleteTheGraph(String lang) {
        this.lang = lang;
    }

    public static String getAppDirectory() {
        String appDirectory = CompleteTheGraph.class.getResource("CompleteTheGraph.class").getPath().replaceAll("%20", " ");
        appDirectory = appDirectory.substring(0, appDirectory.indexOf("WEB-INF/"));
        File f = new File(appDirectory);
        appDirectory = f.getAbsolutePath() + File.separator;
        return appDirectory;
    }

//    public static void main(String[] args) {
//        CompleteTheGraph p = new CompleteTheGraph("en");
////      p.start();
//        p.mapNode_url();
////        String g=p.designFullGraph();
////        p.buildTheGraph(g, "");
//    }
    /**
     * Construct a DOT graph in memory, convert it to image and store the image
     * in the file system. For a given node
     *
     * @param root_center node to put in the center
     * @return
     */
    public static String designGraphForOneNode(String root_center) {
        getNodes_relatations().put(root_center, new ArrayList<String>());
        node_edges = new TreeMap<String, List<String>>();
        nodeID_node = new TreeMap<String, String>();
        edges = new TreeMap<String, List<String>>();
        List<String> list_nodeID = new ArrayList<String>();
//        Test gv = new Test();
//        OntologyParsing o = new OntologyParsing();
        String graph_root = "root=" + root_center + ";\n";
        Set<String> keySet = GraphConstructor.getProperty().keySet();
        Iterator<String> iterator = keySet.iterator();
        Map<String, String> listeDatatype = new TreeMap<String, String>();

        while (iterator.hasNext()) {
            String nodeID_concept = iterator.next();

            for (Element e : GraphConstructor.getProperty().get(nodeID_concept)) {
                String name_prop = e.getName();
                String rel_prop = "";
                //Différencier les object prop des datatype
//            if(name_prop.equals("label")) name_prop="TheLabel";
                if (name_prop.equals("n")
                        || name_prop.equals("posBeg")
                        || name_prop.equals("posEnd")
                        || name_prop.equals("label") //the label is the name of the icon
                        ) {
                    continue;
                }
                if (e.getText().isEmpty()) {
                    //object prop, vérifier le concept pointé par le nodeID existe
                    String nodeID_prop = e.getAttributeValue("nodeID", rdfNamespace);
//                    if (!lang.isEmpty()) {
//                        Map<String, String> recupObjectProLabel = o.recupObjectProLabel(getLang());
//                        if (recupObjectProLabel != null && recupObjectProLabel.containsKey(name_prop) && recupObjectProLabel.get(name_prop) != null) {
//                            name_prop = recupObjectProLabel.get(name_prop);
//                        }
//                    }
                    if (!list_nodeID.contains(nodeID_prop)) {
//                        System.out.println("Attention aucun noeud avec le nodeID " + nodeID_concept + "  n'a pas été créé");
                    }
                    if (!nodeID_prop.isEmpty()) { //Il s'agit d'une propriété objet
//                    System.out.println("prop= " + nodeID_prop + "\t nodeID= " + nodeID_concept);
                        rel_prop = nodeID_concept + "->" + nodeID_prop
                                + "[label= \"" + name_prop + "\""
                                + "]"
                                + ";";
                    }
                    if (!rel_prop.isEmpty()) {
                        //arc sortant
                        if (edges.containsKey(nodeID_concept)) {
                            edges.get(nodeID_concept).add(rel_prop + "\t" + nodeID_prop);
                        } else {
                            ArrayList<String> arrayList = new ArrayList<String>();
                            arrayList.add(rel_prop + "\t" + nodeID_prop);
                            edges.put(nodeID_concept, arrayList);
                        }
                        //arc entrant
                        if (edges.containsKey(nodeID_prop)) {
                            edges.get(nodeID_prop).add(rel_prop + "\t" + nodeID_concept);
                        } else {
                            ArrayList<String> arrayList = new ArrayList<String>();
                            arrayList.add(rel_prop + "\t" + nodeID_concept);
                            edges.put(nodeID_prop, arrayList);
                        }
                    }

                } else {
//                    if (!lang.isEmpty()) {
//                        Map<String, String> recupDatatypeProLabel = o.recupDatatypeProLabel(getLang());
//                        if (recupDatatypeProLabel != null && recupDatatypeProLabel.containsKey(name_prop) && recupDatatypeProLabel.get(name_prop) != null) {
//                            name_prop = recupDatatypeProLabel.get(name_prop);
//                        }
//                    }
                    if (listeDatatype.containsKey(nodeID_concept)) {
                        String s = listeDatatype.get(nodeID_concept);
                        listeDatatype.put(nodeID_concept, s + ", " + name_prop + " : " + e.getText());
                    } else {
                        listeDatatype.put(nodeID_concept, name_prop + " : " + e.getText());
                    }
                }

            }
        }
        for (Element e : GraphConstructor.getConcept()) {
            //Déterminer le type du concept pour pouvoir lui associer l'icone correspondante
            String concept_type = e.getName();
            String label = " ";
//          if(null!=e.getChild("label", rdfsNamespace))label= e.getChild("label", rdfsNamespace).getText();
            if (null != e.getChild("label", gsNamespace)) {
                label = e.getChild("label", gsNamespace).getText();
            }
            if (concept_type.equals("Experience")) {
                if (null != e.getChild("roleType", gsNamespace)) {
                    label = e.getChild("roleType", gsNamespace).getText();
                }
            }
            if (concept_type.equals("Date")) {
                if (null != e.getChild("dtstart", icalNamespace)) {
                    label = e.getChild("dtstart", icalNamespace).getText();
                    String year = label.substring(0, 4);
                    String month = label.substring(4, 6);
                    String day = label.substring(6, 8);
                    label = year + "-" + month + "-" + day;
                }
            }
            if (concept_type.equals("Education")) {
                if (null != e.getChild("level", gsNamespace)) {
                    label = e.getChild("level", gsNamespace).getText();
                }
            }
            if (label.equals(" ")|| label.equals("") || label.isEmpty()) {
                for (Element c : (List<Element>) e.getChildren()) {
                    if (c.getName().toLowerCase().contains("type")) {
                        label = c.getText();
                    }
                }
            }
//            if (concept_type.equals("ViolentAct")) {
//                if (null != e.getChild("type", gsNamespace)) {
//                    label = e.getChild("type", gsNamespace).getText();
//                }
//            }
//            String img_adr = "C:/Users/Ordinateur1/Documents/NetBeansProjects/DÃ©monstrateur V0.2/GraphGenerator/Icones/" + concept_type + ".png";
            String img_adr = relativPath + "/Icones/" + concept_type + ".png";
//            String img_adr = "./Icones/" + concept_type + ".png";
//            System.out.println("adresse image : "+img_adr);
            File img = new File(img_adr);
            if (!img.exists()) {
                System.out.println(" attention l'image " + concept_type + " n'existe pas !!!!");
            }
            String nodeID_concept = e.getAttributeValue("nodeID", rdfNamespace);
            list_nodeID.add(nodeID_concept);
            //récupérer le nom du concept suivant la langue
//            if (!lang.isEmpty()) {
//                Map<String, String> concept_langue = o.recupClassLabelFrAndEn(getLang());
//                if (concept_langue.containsKey(concept_type)) {
//                    concept_type = concept_langue.get(concept_type);
//                }
//            }
            String node_head = nodeID_concept + " [ "
                    + "label=<<TABLE border=\"0\" cellborder=\"0\">\n"
                    + "<TR><TD width=\"50\" height=\"50\""
                    + " fixedsize=\"true\""
                    + ">"
                    + "<IMG SRC=\"" + img.getPath() + "\" scale=\"true\"/>"
                    + "</TD><td><font point-size=\"10\"><I>" + concept_type + "</I> : <B>" + label + "</B></font></td></TR>\n"
                    + "</TABLE>>";

            if (listeDatatype.containsKey(nodeID_concept)) {
                node_head = node_head + ", tooltip=\"" + listeDatatype.get(nodeID_concept) + "\"";
            }

            node_head = node_head + ", href=\"ExitCloseGraphsFiles?root_center=" + nodeID_concept + "\"";

            node_head = node_head + "];";
            nodeID_node.put(nodeID_concept, node_head);
            if (edges.containsKey(nodeID_concept)) {
                node_edges.put(node_head, edges.get(nodeID_concept));
            }
        }

        //Construire le graph_root avec node_edge et root_center
        if (nodeID_node.containsKey(root_center)) {
            //get all nodes adjacent to this particular node
            String node = nodeID_node.get(root_center);
            graph_root = graph_root + node + "\n";
            if (node_edges.containsKey(node)) {
                List<String> get = node_edges.get(node);
                for (String s : get) {
                    if (nodeID_node.containsKey(s.split("\t")[1])) {
                        getNodes_relatations().get(root_center).add(s.split("\t")[1]);

                        //add the node conerning by the range of the property
                        String node_range = nodeID_node.get(s.split("\t")[1]);
                        graph_root = graph_root + node_range + "\n";
                        //Add the property
                        graph_root = graph_root + s.split("\t")[0] + "\n";
                    }
                }
            }
        }
        nodes_graph.put(root_center, graph_root);
        return graph_root;

    }

    public void completeHref() throws IOException {
        Set<String> keySet = nodes_graph.keySet();
//        System.out.println("nodes = "+keySet.size());
        for (String nodeID : keySet) {
            //récupérer le href de chaque noeud
            String G_nodeID = nodes_graph.get(nodeID);
            //Récupérer les nodes référencés
//            G_nodeID.split("^id.*\\[");
//            "\nid[a-zA-Z0-9]* \\["

            System.out.println("\n\n" + G_nodeID);
            String[] split = G_nodeID.split(";");
            for (int i = 0; i < split.length; i++) {
                String s2 = split[i];
                if (s2.contains("->") || !s2.contains("[")) {
                    continue;
                }
                //RÃ©cupÃ©rer le nodeID de noeud
                String node = s2.split("\\[")[0];
                String node_content = s2.split("\\[")[1];
                node = node.substring(1, node.lastIndexOf(" "));
//                 System.out.println(node+" ***111111111***** "+node_content);

                //il faut récupérer le graph généré par node et le concaténer au G_node 
                String subGraph2 = nodes_graph.get(node);
                //supprimer l'entête
                String[] split1 = subGraph2.split(";");
                String str2 = G_nodeID;
                for (String x : split1) {
                    if (!str2.contains(x)) {
                        str2 = str2 + x + ";";
                    }
                }
//                subGraph2= subGraph2+G_nodeID;
                String buildTheGraph = buildTheGraph(str2, nodeID + "_" + node);
                //Modifier le href
                G_nodeID = G_nodeID.replace(node + "OutGraph.svg", nodeID + "_" + node + "OutGraph.svg");
                buildTheGraph(str2, nodeID + "_" + node);

            }
            buildTheGraph(G_nodeID, nodeID);
        }
    }

    public void mapNode_url() throws IOException {
        for (Element e : GraphConstructor.getConcept()) {
            String nodeID = e.getAttributeValue("nodeID", rdfNamespace);
            String g = designGraphForOneNode(nodeID);
            nodes_graph.put(nodeID, g);
            String buildTheGraph = buildTheGraph(g, nodeID);
            nodes_url.put(nodeID, buildTheGraph);
        }
    }

    public String getNodeID_Range(Element e) {
        String res = "";
        String nodeID_prop = e.getAttributeValue("nodeID", rdfNamespace);
        if (nodeID_prop == null) {
            nodeID_prop = e.getAttributeValue("about", rdfNamespace);
        }
        if (nodeID_prop == null) {
            nodeID_prop = e.getAttributeValue("resource", rdfNamespace);
        }
        if (nodeID_prop == null) {
            if (!e.getChildren().isEmpty()) {
                Element ee = e.getChildren().get(0);
                nodeID_prop = ee.getAttributeValue("nodeID", rdfNamespace);
                if (nodeID_prop == null) {
                    nodeID_prop = ee.getAttributeValue("about", rdfNamespace);
                }
                if (nodeID_prop == null) {
                    nodeID_prop = ee.getAttributeValue("resource", rdfNamespace);
                }
            }
        }
        if(nodeID_prop != null && nodeID_prop.contains(".com/")){
            nodeID_prop= nodeID_prop.split(".com/")[1].replaceAll("/", "_");
        }
        if(nodeID_prop!=null) return nodeID_prop;
        return res;
    }

    /**
     * Construct a graph for all the text and generate all the nodes
     *
     *
     * @return
     */
    public String designFullGraph() {
//        mapNode_url(lang);
        List<String> list_nodeID = new ArrayList<String>();
//        Test gv = new Test();
//        OntologyParsing o = new OntologyParsing();
        String graphe = "";
        Set<String> keySet = GraphConstructor.getProperty().keySet();
        Map<String, String> listeDatatype = new TreeMap<String, String>();
        Iterator<String> iterator = keySet.iterator();
        
        while (iterator.hasNext()) {
            String nodeID_concept = iterator.next();

            for (Element e : GraphConstructor.getProperty().get(nodeID_concept)) {
                String name_prop = e.getName();
                String rel_prop = "";
                //Différencier les object prop des datatype
//            if(name_prop.equals("label")) name_prop="TheLabel";
                if (name_prop.equals("n")
                        || name_prop.equals("posBeg")
                        || name_prop.equals("posEnd")
                        || name_prop.equals("label") //the label is the name of the icon
                        ) {
                    continue;
                }
                if (e.getText().isEmpty()) {
                    //object prop, vérifier le concept pointé par le nodeID existe
                    String nodeID_prop = getNodeID_Range(e);
//                    if (!lang.isEmpty()) {
//                        Map<String, String> recupObjectProLabel = o.recupObjectProLabel(getLang());
//                        if (recupObjectProLabel != null && recupObjectProLabel.containsKey(name_prop) && recupObjectProLabel.get(name_prop) != null) {
//                            name_prop = recupObjectProLabel.get(name_prop);
//                        }
//                    }
                    if (!list_nodeID.contains(nodeID_prop)) {
//                        System.out.println("Attention aucun noeud avec le nodeID " + nodeID_concept + "  n'a pas été créé");
                    }
                    if (!nodeID_prop.isEmpty()) { //Il s'agit d'une propriété objet
//                    System.out.println("prop= " + nodeID_prop + "\t nodeID= " + nodeID_concept);
                        rel_prop = nodeID_concept + "->" + nodeID_prop
                                + "[label= \"" + name_prop + "\"]"
                                + ";";
                    }

                } else {
//                    if (!lang.isEmpty()) {
//                        Map<String, String> recupDatatypeProLabel = o.recupDatatypeProLabel(getLang());
//                        if (recupDatatypeProLabel != null && recupDatatypeProLabel.containsKey(name_prop) && recupDatatypeProLabel.get(name_prop) != null) {
//                            name_prop = recupDatatypeProLabel.get(name_prop);
//                        }
//                    }
                    if (listeDatatype.containsKey(nodeID_concept)) {
                        String s = listeDatatype.get(nodeID_concept);
                        listeDatatype.put(nodeID_concept, s + ", " + name_prop + " : " + e.getText());
                    } else {
                        listeDatatype.put(nodeID_concept, name_prop + " : " + e.getText());
                    }
                }
                if (!rel_prop.isEmpty()) {
                    graphe = graphe + rel_prop + "\n";
                }
            }
        }
        for (Element e : GraphConstructor.getConcept()) {
            //Déterminer le type du concept pour pouvoir lui associer l'icone correspondante
            String concept_type = e.getName();
            String label = " ";
//          if(null!=e.getChild("label", rdfsNamespace))label= e.getChild("label", rdfsNamespace).getText();
            if (null != e.getChild("label", gsNamespace)) {
                label = e.getChild("label", gsNamespace).getText();
            }
            if (concept_type.equals("Experience")) {
                if (null != e.getChild("roleType", gsNamespace)) {
                    label = e.getChild("roleType", gsNamespace).getText();
                }
            }
            if (concept_type.equals("Date")) {
                if (null != e.getChild("dtstart", icalNamespace)) {
                    label = e.getChild("dtstart", icalNamespace).getText();
                    String year = label.substring(0, 4);
                    String month = label.substring(4, 6);
                    String day = label.substring(6, 8);
                    label = year + "-" + month + "-" + day;
                }
            }
            if (concept_type.equals("Education")) {
                if (null != e.getChild("level", gsNamespace)) {
                    label = e.getChild("level", gsNamespace).getText();
                }
            }
            if (label.equals(" ") || label.equals("") || label.isEmpty() ) {
                for (Element c : (List<Element>) e.getChildren()) {
                    if (c.getName().toLowerCase().contains("type")) {
                        label = c.getText();
                    }
                }
            }
//            if (concept_type.equals("ViolentAct")) {
//                if (null != e.getChild("type", gsNamespace)) {
//                    label = e.getChild("type", gsNamespace).getText();
//                }
//            }
            String img_adr = relativPath + "/Icones/" + concept_type + ".png";
//            String img_adr = "./Icones/" + concept_type + ".png";
//            System.out.println("adresse image : " + img_adr);
            File img = new File(img_adr);
            if (!img.exists()) {
                System.out.println(" attention l'image " + concept_type + " n'existe pas !!!!");
            }
            String nodeID_concept = e.getAttributeValue("nodeID", rdfNamespace);
        if (nodeID_concept == null) {
            nodeID_concept = e.getAttributeValue("about", rdfNamespace);
                }
        if (nodeID_concept == null) {
            nodeID_concept = e.getAttributeValue("resource", rdfNamespace);
            }
        if(nodeID_concept != null && nodeID_concept.contains(".com/")){
            nodeID_concept= nodeID_concept.split(".com/")[1].replaceAll("/", "_");
        }
            list_nodeID.add(nodeID_concept);
            //récupérer le nom du concept suivant la langue
//            if (!lang.isEmpty()) {
//                Map<String, String> concept_langue = o.recupClassLabelFrAndEn(getLang());
//                if (concept_langue.containsKey(concept_type)) {
//                    concept_type = concept_langue.get(concept_type);
//                }
//            }
            String node_head = nodeID_concept + " [ "
                    + "label=<<TABLE border=\"0\" cellborder=\"0\">\n"
                    + "<TR><TD width=\"50\" height=\"50\""
                    + " fixedsize=\"true\""
                    + ">"
                    + "<IMG SRC=\"" + img.getPath() + "\" scale=\"true\"/>"
                    + "</TD><td><font point-size=\"10\"><I>" + concept_type + "</I> : <B>" + label + "</B></font></td></TR>\n"
                    + "</TABLE>>";

            if (listeDatatype.containsKey(nodeID_concept)) {
                node_head = node_head + ", tooltip=\"" + listeDatatype.get(nodeID_concept) + "\"";
            }
//            if (nodes_url.containsKey(nodeID_concept)) {
            node_head = node_head + ", href=\"ExitCloseGraphsFiles?root_center=" + nodeID_concept + "\"";
//            }
            node_head = node_head + "];";
            graphe = graphe + node_head + "\n";
            nodeID_node.put(nodeID_concept, node_head);
//            if(edges.containsKey(nodeID_concept)) node_edges.put(node_head, edges.get(nodeID_concept));
        }

        return graphe;

    }

    public String designGraphWithOptions(String[] opt) {
//        mapNode_url(lang);
        List<String> list_nodeID = new ArrayList<String>();
//        Test gv = new Test();
//        OntologyParsing o = new OntologyParsing();
        String graphe = "";
        Set<String> keySet = GraphConstructor.getProperty().keySet();
        Iterator<String> iterator = keySet.iterator();
        Map<String, String> listeDatatype = new TreeMap<String, String>();
        List<String> nodeID_propList = new ArrayList<String>();
       
        while (iterator.hasNext()) {
            String nodeID_concept = iterator.next();
            boolean exist = false;
            for (String s : opt) {
                if (nodeID_concept.contains(s)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                continue;
            }
            for (Element e : GraphConstructor.getProperty().get(nodeID_concept)) {
                String name_prop = e.getName();
                String rel_prop = "";
                //Différencier les object prop des datatype
//            if(name_prop.equals("label")) name_prop="TheLabel";
                if (name_prop.equals("n")
                        || name_prop.equals("posBeg")
                        || name_prop.equals("posEnd")
                        || name_prop.equals("label") //the label is the name of the icon
                        ) {
                    continue;
                }
                if (e.getText().isEmpty()) {
                    //object prop, vérifier le concept pointé par le nodeID existe
                    String nodeID_prop = e.getAttributeValue("nodeID", rdfNamespace);
//                    if (!lang.isEmpty()) {
//                        Map<String, String> recupObjectProLabel = o.recupObjectProLabel(getLang());
//                        if (recupObjectProLabel != null && recupObjectProLabel.containsKey(name_prop) && recupObjectProLabel.get(name_prop) != null) {
//                            name_prop = recupObjectProLabel.get(name_prop);
//                        }
//                    }
                    if (!list_nodeID.contains(nodeID_prop)) {
//                        System.out.println("Attention aucun noeud avec le nodeID " + nodeID_concept + "  n'a pas été créé");
                    }
                    if (!nodeID_prop.isEmpty()) { //Il s'agit d'une propriÃ©tÃ© objet
//                    System.out.println("prop= " + nodeID_prop + "\t nodeID= " + nodeID_concept);
                        rel_prop = nodeID_concept + "->" + nodeID_prop
                                + "[label= \"" + name_prop + "\"]"
                                + ";";
                        nodeID_propList.add(nodeID_prop);
                    }

                } else {
//                    if (!lang.isEmpty()) {
//                        Map<String, String> recupDatatypeProLabel = o.recupDatatypeProLabel(getLang());
//                        if (recupDatatypeProLabel != null && recupDatatypeProLabel.containsKey(name_prop) && recupDatatypeProLabel.get(name_prop) != null) {
//                            name_prop = recupDatatypeProLabel.get(name_prop);
//                        }
//                    }
                    if (listeDatatype.containsKey(nodeID_concept)) {
                        String s = listeDatatype.get(nodeID_concept);
                        listeDatatype.put(nodeID_concept, s + ", " + name_prop + " : " + e.getText());
                    } else {
                        listeDatatype.put(nodeID_concept, name_prop + " : " + e.getText());
                    }
                }
                if (!rel_prop.isEmpty()) {
                    graphe = graphe + rel_prop + "\n";
                }
            }
        }
        for (Element e : GraphConstructor.getConcept()) {
            //Déterminer le type du concept pour pouvoir lui associer l'icone correspondante
            String concept_type = e.getName();
            String nodeID = e.getAttributeValue("nodeID", rdfNamespace);
            boolean exist = false;
            for (String s : opt) {
                if (concept_type.equals(s)) {
                    exist = true;
                    break;
                }
            }
            for (String s : nodeID_propList) {
                if (nodeID.equals(s)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                continue;
            }
            String label = " ";
//          if(null!=e.getChild("label", rdfsNamespace))label= e.getChild("label", rdfsNamespace).getText();
            if (null != e.getChild("label", gsNamespace)) {
                label = e.getChild("label", gsNamespace).getText();
            }
            if (concept_type.equals("Experience")) {
                if (null != e.getChild("roleType", gsNamespace)) {
                    label = e.getChild("roleType", gsNamespace).getText();
                }
            }
            if (concept_type.equals("Date")) {
                if (null != e.getChild("dtstart", icalNamespace)) {
                    label = e.getChild("dtstart", icalNamespace).getText();
                    String year = label.substring(0, 4);
                    String month = label.substring(4, 6);
                    String day = label.substring(6, 8);
                    label = year + "-" + month + "-" + day;
                }
            }
            if (concept_type.equals("Education")) {
                if (null != e.getChild("level", gsNamespace)) {
                    label = e.getChild("level", gsNamespace).getText();
                }
            }
            if (label.equals("") || label.equals(" ") || label.isEmpty()) {
                for (Element c : (List<Element>) e.getChildren()) {
                    if (c.getName().toLowerCase().contains("type")) {
                        label = c.getText();
                    }
                }
            }
            String img_adr = relativPath + "/Icones/" + concept_type + ".png";
//            String img_adr = "./Icones/" + concept_type + ".png";
//            System.out.println("adresse image : " + img_adr);
            File img = new File(img_adr);
            if (!img.exists()) {
                System.out.println(" attention l'image " + concept_type + " n'existe pas !!!!");
            }
            String nodeID_concept = e.getAttributeValue("nodeID", rdfNamespace);
            list_nodeID.add(nodeID_concept);
            //récupérer le nom du concept suivant la langue
//            if (!lang.isEmpty()) {
//                Map<String, String> concept_langue = o.recupClassLabelFrAndEn(getLang());
//                if (concept_langue.containsKey(concept_type)) {
//                    concept_type = concept_langue.get(concept_type);
//                }
//            }
            String node_head = nodeID_concept + " [ "
                    + "label=<<TABLE border=\"0\" cellborder=\"0\">\n"
                    + "<TR><TD width=\"50\" height=\"50\""
                    + " fixedsize=\"true\""
                    + ">"
                    + "<IMG SRC=\"" + img.getPath() + "\" scale=\"true\"/>"
                    + "</TD><td><font point-size=\"10\"><I>" + concept_type + "</I> : <B>" + label + "</B></font></td></TR>\n"
                    + "</TABLE>>";

            if (listeDatatype.containsKey(nodeID_concept)) {
                node_head = node_head + ", tooltip=\"" + listeDatatype.get(nodeID_concept) + "\"";
            }
//            if (nodes_url.containsKey(nodeID_concept)) {
            node_head = node_head + ", href=\"ExitCloseGraphsFiles?root_center=" + nodeID_concept + "\"";
//            }
            node_head = node_head + "];";
            graphe = graphe + node_head + "\n";
            nodeID_node.put(nodeID_concept, node_head);
//            if(edges.containsKey(nodeID_concept)) node_edges.put(node_head, edges.get(nodeID_concept));
        }
        return graphe;

    }

    public static String getGraph(String G, String node) throws IOException {
        Test gv = new Test();
        String graphe = gv.start_graph("G") + "\n";
        graphe = graphe + G + "\n";
        graphe = graphe + gv.end_graph();
        gv.addln(graphe);
//        System.out.println(gv.getDotSource());
        String type = "svg";

        String repesentationType = "dot";
        File out = new File(getAppDirectory() + node + "OutGraph." + type);

//       gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, repesentationType), out );
        byte[] graph = gv.getGraph(gv.getDotSource(), type, repesentationType); //gv.getDotSource(), type
        if (graph == null) {
            System.out.println(out.getName() + " is null");
            return "";
        } else {
            gv.writeGraphToFile(graph, out);
        }

        //Attention le path absolu ne fonctionne pas, graphViz modifier le href et il ne devient plus accessible
//        System.out.println("svg= " + out.getPath());
//        SVGHandler svghand= new SVGHandler(out);
        String s = afficheUltraRapide(out);
        ParserString(s);
        addHoverToText(list_g);
//        if (getStringFromDocument(document) != null) {
            s = getStringFromDocument(document);
//        }

        s = RenderModify(s);
        getAllGraphs().put(out.getName(), s);
//        System.out.println("absolute path: " + out.getAbsolutePath());
//        out.delete();
        return s;

    }

    public static String buildTheGraph(String G, String node) throws IOException {
        Test gv = new Test();
        String graphe = gv.start_graph("G") + "\n";
        graphe = graphe + G + "\n";
        graphe = graphe + gv.end_graph();
        gv.addln(graphe);
//        System.out.println(gv.getDotSource());
        String type = "svg";

        String repesentationType = "dot";
        File out = new File(getAppDirectory() + node + "OutGraph." + type);

//       gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, repesentationType), out );
        byte[] graph = gv.getGraph(gv.getDotSource(), type, repesentationType); //gv.getDotSource(), type
        if (graph == null) {
            System.out.println(out.getName() + " is null");
        } else {
            gv.writeGraphToFile(graph, out);
        }
        String s = afficheUltraRapide(out);
        ParserString(s);
        addHoverToText(list_g);
        s = getStringFromDocument(document);
        s = RenderModify(s);
        out = writeFile(out, s);
        getAllGraphs().put(out.getName(), s);

        System.out.println("absolute path: " + out.getAbsolutePath());
//        out.delete();
        //Attention le path absolu ne fonctionne pas, graphViz modifier le href et il ne devient plus accessible
        return out.getPath();
    }

    /**
     * Transformer un Document en une chaine de caractÃ¨re
     *
     * @param doc
     * @return
     */
    public static String getStringFromDocument(Document doc) {
        try {
            JDOMSource domSource = new JDOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            return null;
        }
    }

    public static File writeFile(File file, String texte) throws FileNotFoundException {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(texte);
        } catch (Exception ex) {
            System.out.println("Exception généré lors de l'écriture dans le fichier : " + ex);
        }
        return file;
    }
    public static Document document;

    public static void ParserString(String input) {
        
        String ch2="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.26.3 (20100126.1600)  -->\n<!-- Title: G Pages: 1 --> ";
//        input.subSequence(ch.length()+6, input.length());
        input= input.split("Pages: 1 -->")[1];
        try {
            InputStream in = new ByteArrayInputStream(input.getBytes("UTF-8"));
            SAXBuilder parser = new SAXBuilder();
            long start;
//            start = System.nanoTime();
//            System.out.println("DÃ©but "+ start);
            long duree;
            // Parse the document
            document = parser.build(in);
//             duree = System.nanoTime() - start;
//            System.out.println("Avant parsing "+ duree);
            // Process the root element
            process(document.getRootElement());
//            duree = System.nanoTime() - start;
//            System.out.println("Fin du parsing "+ duree);
        } catch (JDOMException ex) {
            System.err.println(ex);
        }
        catch ( IOException ex) {
            System.err.println(ex);
        }

    }// fin de parserText
    static List<Element> list_g = new ArrayList<Element>();

    /**
     * Parser le SVg pour rÃ©cupÃ©rer les Ã©lÃ©ments <g>
     *
     * @param rootElement racine du SVG
     */
    private static void process(Element rootElement) {
        //Une fois qu'on a trouvÃ© le G => Graph on rÃ©cupÃ¨re ses enfants et on arrete, sinon on parse de maniÃ¨re rÃ©cursive
        if (rootElement.getName().equals("g")) {
            list_g.add(rootElement);
            list_g.addAll(rootElement.getChildren());
        } else {
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
    }

    /**
     * Add script for transforming svg
     *
     * @return
     */
    public static String AddScript() {
        return "<script type=\"text/ecmascript\">"
                + "\n<![CDATA["
                + "\nvar transMatrix = [1,0,0,1,4,290];"
                + "\nfunction init(evt)"
                + "\n{"
                + "\nif ( window.svgDocument == null )"
                + "\n{"
                + "\nsvgDoc = evt.target.ownerDocument;"
                + "\n}"
                + "\n	    mapMatrix = svgDoc.getElementById(\"graph1\");"
                + "\n       width  = evt.target.getAttributeNS(null, \"width\");"
                + "\n width=parseFloat(width);"
                + "\n	    height = evt.target.getAttributeNS(null, \"height\");"
                //                +"\n height=height.substring(0,height.length-2);"
                + "\n height=parseFloat(height);"
                + "\n       scale1  = evt.target.getAttributeNS(null, \"translate\");"
                //                + "\n       transMatrix[4]= parseFloat(scale1.split(\" \")[0].substring(1));"
                //                + "\n       transMatrix[5]= parseFloat(scale1.split(\" \")[1].substring(0, scale1.split(\" \")[1].length-1));"
                + "\n	  }"
                + "\nfunction pan(dx, dy)"
                + "\n{"
                + "\ntransMatrix[4] = transMatrix[4] +dx;"
                + "\ntransMatrix[5] += dy;"
                + "\nnewMatrix = \"matrix(\" +  transMatrix.join(' ') + \")\";"
                + "\nmapMatrix.setAttributeNS(null, \"transform\", newMatrix);"
                + "\n}"
                + "\nfunction zoom(scale)"
                + "\n{"
                + "\nfor (var i=0; i<transMatrix.length; i++)"
                + "\n{"
                + "\ntransMatrix[i] *= scale;"
                + "\n}"
                //                + "\ntransMatrix[4] =width;" 
                //               + "\ntransMatrix[5] = height;"
                + "\ntransMatrix[4] += (1-scale)*width/2;"
                + "\ntransMatrix[5] += (1-scale)*height/2;"
                + "\nnewMatrix = \"matrix(\" +  transMatrix.join(' ') + \")\";"
                + "\nmapMatrix.setAttributeNS(null, \"transform\", newMatrix);"
                + "\n}"
                + "\n	]]>"
                + "\n	</script>";
    }

    /**
     * RÃ©cupÃ©rer la liste des positions de chaque nodeID, attention les
     * positions sont en String
     *
     * @param nodeID
     * @return
     */
    public static void addHoverToText(List<Element> gg) {//String nodeID
        for (Element g : gg) {
            if (!g.getName().equals("g")) {
                continue;
            }
            if (g.getAttributeValue("id").startsWith("node")) {
                String nodeID = g.getChild("title", svgNamespace).getText();
                g.setAttribute("onmouseover", "myFunction('" + nodeID + "')");
                g.setAttribute("onmouseout", "myFunctionOut('" + nodeID + "')");
            }
        }


//        List<String> pos = new ArrayList<>();
//        List<Element> concept = GraphConstructor.getConcept();
//        for(Element e:concept){
//            if(e.getAttributeValue("nodeID", rdfNamespace).equals(nodeID)){
//                for(Element c: (List<Element>) e.getChildren()){
//                    if(c.getName().equals("posBeg") || c.getName().equals("posEnd")){
//                        pos.add(c.getText());
//                    }
//                }
//            }
//        }

//        return pos;
    }

    public static String RenderModify(String s) {

        String replaceAll = s.replaceAll("\\\\", "/");
        String replaceAll1 = replaceAll.replaceAll(getAppDirectory().replaceAll("\\\\", "/"), "");
        replaceAll1 = replaceAll1.replaceAll("display=\"block\"", " margin=\"auto\"");
        //        String regeex = "<svg width=\"[^\"]*\" height=\"[^\"]*\"\n" +
//" viewBox=\"[^\"]*\"";
        String addJSScript = "onmouseup=\"handleMouseUp(evt)\" onmousedown=\"handleMouseDown(evt)\""
                + " onmousemove=\"handleMouseMove(evt)\"><script xlink:href=\"SVGPan.js\"></script>";
        String regeex1 = "<svg width=\"[^\"]*\"";
//        String regeex2 = "height=\"[^\"]*\"";
        String regeex3 = "viewBox=\"[^\"]*\"";
        String compas = "<circle cx=\"50\" cy=\"50\" r=\"42\" fill=\"white\" opacity=\"0.75\"></circle>"
                + "<path class=\"button\" onclick=\"pan( 0, 50)\" d=\"M50 10 l12 20 a40, 70 0 0,0 -24, 0z\"></path>"
                + "<path class=\"button\" onclick=\"pan( 50, 0)\" d=\"M10 50 l20 -12 a70, 40 0 0,0 0, 24z\"></path>"
                + "<path class=\"button\" onclick=\"pan( 0,-50)\" d=\"M50 90 l12 -20 a40, 70 0 0,1 -24, 0z\"></path>"
                + "<path class=\"button\" onclick=\"pan(-50, 0)\" d=\"M90 50 l-20 -12 a70, 40 0 0,1 0, 24z\"></path>"
                + "<circle class=\"compass\" cx=\"50\" cy=\"50\" r=\"20\"></circle>"
                + "<circle class=\"button\" cx=\"50\" cy=\"41\" r=\"8\" onclick=\"zoom(0.8)\"></circle>"
                + "<circle class=\"button\" cx=\"50\" cy=\"59\" r=\"8\" onclick=\"zoom(1.25)\"></circle>"
                + "<rect class=\"plus-minus\" x=\"46\" y=\"39.5\" width=\"8\" height=\"3\"></rect>"
                + "<rect class=\"plus-minus\" x=\"46\" y=\"57.5\" width=\"8\" height=\"3\"></rect>"
                + "<rect class=\"plus-minus\" x=\"48.5\" y=\"55\" width=\"3\" height=\"8\"></rect>";
//        replaceAll1= replaceAll1.replaceAll("</svg>", "<link type=\"text/css\" rel=\"stylesheet\" href=\"AccueilStyle.css\"></svg>");
//        replaceAll1= replaceAll1.replaceAll(regeex, "<svg width=\"100%\" height=\"100%\" ");
//        replaceAll1= replaceAll1.replaceAll(regeex1, "<svg width=\"100%\"");
//        replaceAll1= replaceAll1.replaceAll(regeex2, "height=\"100%\"");
        //Ajouter le js pour le zoom
        replaceAll1 = replaceAll1.replaceAll("<svg ", "<svg style=\"display:block; margin:auto;\" ");
        replaceAll1 = replaceAll1.replaceAll("<g id=\"graph0\"", AddScript() + "<g id=\"graph0\"");
        replaceAll1 = replaceAll1.replaceAll(regeex3, "onload=\"init(evt)\""); //viewBox=\"0 0 1500 1000\" preserveAspectRatio=\"none\"
//        replaceAll1= replaceAll1.replaceAll("</svg>", compas+"</svg>");
        replaceAll1 = "<center><table><tr><td><svg width=\"100px\" height=\"100px\">" + compas + "</svg></td><td> " + replaceAll1 + "</td></tr></table></center>";
        return replaceAll1;

    }

    public static String afficheUltraRapide(File f) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        try {
            byte[] buffer = new byte[(int) f.length()];
            in.readFully(buffer);
            return new String(buffer);
        } finally {
            in.close();
        }
    }

    private static void modifierEncodage(String fichier_src, String fichier_dest, String ancien_enc, String nouveau_enc) throws IOException {
        FileInputStream src = new FileInputStream(fichier_src);
        BufferedReader r = new BufferedReader(new InputStreamReader(src, ancien_enc));

        FileOutputStream dest = new FileOutputStream(fichier_dest);
        Writer w = new BufferedWriter(new OutputStreamWriter(dest, nouveau_enc));

        String donnee;
        while ((donnee = r.readLine()) != null) {
            w.write(donnee);
            w.flush();

        }
        w.close();
        r.close();

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

    /**
     * @return the actual_graph
     */
    public String getActual_graph() {
        return actual_graph;
    }

    /**
     * @param actual_graph the actual_graph to set
     */
    public void setActual_graph(String actual_graph) {
        this.actual_graph = actual_graph;
    }
}