/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.development;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.jdom2.Namespace;
import univ.mlv.SevletPages.GraphDisplay;
import univ.mlv.SevletPages.TextProcessing;

/**
 *
 * @author Fadhela
 */
public class CreateJSFiles {

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
    public static final String ns_foaf = "http://xmlns.com/foaf/0.1/";
    public static final Namespace foafNamespace = Namespace.getNamespace("foaf", ns_foaf);
    public static final String ns_svg = "http://www.w3.org/2000/svg";
    public static final Namespace svgNamespace = Namespace.getNamespace("svg", ns_svg);

    private static Map<String, Element> conceptMap = new TreeMap<String, Element>();
    private static Map<String, List<Element>> objProperty = new TreeMap<String, List<Element>>();
    private static Map<String, List<Element>> dataProperty = new TreeMap<String, List<Element>>();

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

    public CreateJSFiles(Map<String, Element> conceptMap, Map<String, List<Element>> objProperty, Map<String, List<Element>> dataProperty) {
        this.conceptMap = conceptMap;
        this.objProperty = objProperty;
        this.dataProperty = dataProperty;
    }

    public static String startGraph() {
        return "<script type=\"text/javascript\">"
                + "window.onload = function() {\n"
                + "\n"
                + "  \n"
                + "    var width = $(document).width() - 20;\n"
                + "    var height = $(document).height() - 60;\n"
                + "    var g = new Graph();"
//                + "/* First: Write a custom node render function. */\n"
//                + "var render = function(r, n, img_path) {\n"
//                + "        /* the Raphael set is obligatory, containing all you want to display */\n"
//                + "        var set = r.set().push(\n"
//                + "		//\"../images/Agent2.png\"\n"
//                + "		r.image(img_path, n.point[0], n.point[1]+10, 80, 80)).push(r.text(n.point[0]+80, n.point[1]+50, n.label)\n"
//                + ".attr({\"font-size\":\"15px\"}));\n"
//                + "        //    /* custom objects go here */\n"
//                + "          //  r.rect(n.point[0]-30, n.point[1]-13, 62, 86)\n"
//                + "            //    .attr({\"fill\": \"#fa8\", \"stroke-width\": 2, r : \"9px\"}))\n"
//                + "              //  .push(r.text(n.point[0], n.point[1] + 30, n.label)\n"
//                + "                //    .attr({\"font-size\":\"20px\"}));\n"
//                + "        /* custom tooltip attached to the set */\n"
//                + "        set.items.forEach(\n"
//                + "            function(el) {\n"
//                + "                el.tooltip(r.set().push(r.rect(0, 0, 30, 30)\n"
//                + "                    .attr({\"fill\": \"#fec\", \"stroke-width\": 1, r : \"9px\"})))});\n"
//                + "        return set;\n"
//                + "    };"
                + "";
    }

    public static String endGraph() {
        return "/* layout the graph using the Spring layout implementation */\n"
                + "var layouter = new Graph.Layout.Spring(g);\n"
                + "layouter.layout();\n"
                + " \n"
                + "/* draw the graph using the RaphaelJS draw implementation */\n"
                + "var renderer = new Graph.Renderer.Raphael('canvas', g, width, height);\n"
                + "renderer.draw();"
                + "redraw = function() {\n" +
"        layouter.layout();\n" +
"        renderer.draw();\n" +
"    };"
                + "}"
                + "</script>";
    }

    public static String getId(Element e) {
        String id
                = //récupérer l'id
                e.getAttributeValue("nodeID", rdfNamespace);
        if (id == null) {
            id = e.getAttributeValue("about", rdfNamespace);
        }
        if (id == null) {
            id = e.getAttributeValue("resource", rdfNamespace);
        }
        if (id != null && id.contains(".com/")) {
            id = id.split(".com/")[1].replaceAll("/", "_");
        }
        return id;
    }

    public static String getTooltip(String id) {
        String res = "";
        if(dataProperty.containsKey(id)){
        List<Element> get = dataProperty.get(id);
        for (Element e : get) {
            if (res.isEmpty()) {
                res = e.getName() + " : " + e.getText();
            } else {
                res = res + " \\n" + e.getName() + " : " + e.getText();
            }
        }
        }
        return res;
    }

    public static String getEdges(Map<String, List<Element>> edges) {
        String res = "";
        for (String list_nodeID_concept : edges.keySet()) {
            for (Element prop : edges.get(list_nodeID_concept)) {
//            Element prop= edges.get(list_nodeID_concept);
                String prop_name = prop.getName();
                String domain = list_nodeID_concept;
                String range = getId(prop);
                if (range == null) {
                    range = getId(prop.getChildren().get(0));
                }
                res = res + "\n" + "g.addEdge(\"" + domain + "\",\"" + range + "\", { directed : true, label : \"" + prop_name + "\"});";
            }
        }
        return res;
    }

    public static String getNodes(Map<String, Element> nodes) {
        String res = "";

        for (String nodeID_concept : nodes.keySet()) {
            Element e = nodes.get(nodeID_concept);
            //Déterminer le type du concept pour pouvoir lui associer l'icone correspondante
            String concept_type = e.getName();
            String label = " ";
//          if(null!=e.getChild("label", rdfsNamespace))label= e.getChild("label", rdfsNamespace).getText();
            if (null != e.getChild("label", gsNamespace)) {
                label = e.getChild("label", gsNamespace).getText();
            }
            if ((e.getName().equals("Person")||e.getName().equals("Organization")||e.getName().equals("Agent"))&&
                    null != e.getChild("name", foafNamespace)) {
                label = e.getChild("name", foafNamespace).getText();
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
            if (label.equals(" ") || label.equals("") || label.isEmpty()) {
                for (Element c : (List<Element>) e.getChildren()) {
                    if (c.getName().toLowerCase().contains("type")) {
                        label = c.getText();
                    }
                }
            }

            String img_adr = "Icones/" + concept_type + ".png";
//            String img_adr = "./Icones/" + concept_type + ".png";
//            System.out.println("adresse image : " + img_adr);
            File img = new File(img_adr);
            if (!img.exists()) {
                System.out.println(" attention l'image " + concept_type + " n'existe pas !!!!");
            }
//            r.image("../images/Agent2.png", n.point[0], n.point[1]+10, 80, 80)).push(r.text(n.point[0]+80, n.point[1]+50, n.label)
//.attr({"font-size":"15px"}));
            res = res + "\n" + "g.addNode(\"" + nodeID_concept + "\",{ label: \"" + concept_type + " : " + label + "\", render : function(r, n) {"
                    + "var set = r.set().push("
                    + "	r.image(\"" + img_adr + "\", "
                    + "n.point[0], "
                    + "n.point[1]+10, "
                    + "50, "
                    + "50)"
                    + ")"
                    + ".push(r.text(n.point[0]+80, n.point[1]+50, n.label)"
                    + ".attr({\"font-size\":\"15px\"})"
                    + ");\n"
                    + "        set.items.forEach(\n"
                    + "            function(el) {\n"
                    + "                el.tooltip(r.set().push(r.text(10, 10, \"" + getTooltip(nodeID_concept) + "\")\n"
                    + "                    .attr({\"fill\": \"red\", \"stroke-width\": 1, r : \"9px\"})))});"
                    + ""
                    + "return set; }"
                    + "});";

            //récupérer le nom du concept suivant la langue
//            if (!lang.isEmpty()) {
//                Map<String, String> concept_langue = o.recupClassLabelFrAndEn(getLang());
//                if (concept_langue.containsKey(concept_type)) {
//                    concept_type = concept_langue.get(concept_type);
//                }
//            }
        }
        return res;
    }

    public static String getAppDirectory() {
        String appDirectory = CreateJSFiles.class.getResource("CreateJSFiles.class").getPath().replaceAll("%20", " ");
        appDirectory = appDirectory.substring(0, appDirectory.indexOf("WEB-INF/"));
        File f = new File(appDirectory);
        appDirectory = f.getAbsolutePath() + File.separator;
        return appDirectory;
    }

    private static final String pathProject= TextProcessing.getAppDirectory().replaceAll("\\\\", "/");
    private static String TEMP_DIR =pathProject;
    private static String jsPath ="";
    public static File createJSGraph() {
        File js = null;
           File dir = new File("/js/");
//        dir.mkdirs();                    
        PrintWriter writer;
        try {
            js = File.createTempFile("out", ".js", new File(TEMP_DIR));
            setJsPath(js.getName());
            System.out.println(js.getAbsolutePath()+"\nrelative path : "+js.getPath()); 
            writer = new PrintWriter(js);
            writer.println(startGraph());
            //Add nodes and edges
            writer.println(getNodes(conceptMap));
            writer.println(getEdges(objProperty));
            writer.println(endGraph());
            writer.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateJSFiles.class
                    .getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(CreateJSFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        return js;
    }
    
    public static String getJSGraph() {
        String js = "";
          
             js=js+startGraph();
            //Add nodes and edges
            js=js+getNodes(conceptMap);
            js=js+getEdges(objProperty);
            js=js+endGraph();

        return js;
    }
}
