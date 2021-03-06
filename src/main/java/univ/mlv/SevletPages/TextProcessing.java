/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.SevletPages;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import univ.mlv.Structures.Data;
import univ.mlv.development.LoginCheckDB;
import univ.mlv.development.Text;
import univ.mlv.Structures.QueryTriples;
import univ.mlv.development.Queries;

/**
 *
 * @author fadhela-pc
 */
public class TextProcessing extends HttpServlet {

    private static OntModel ontologie;
    static String trustT = "";
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

    public static String getAppDirectory() {
        String appDirectory = TextProcessing.class.getResource("TextProcessing.class").getPath().replaceAll("%20", " ");
        appDirectory = appDirectory.substring(0, appDirectory.indexOf("WEB-INF/"));
        File f = new File(appDirectory);
        appDirectory = f.getAbsolutePath() + File.separator;
        return appDirectory;
    }

    public static String getHead(String text) {
        return ("<html>\n"
                + "	<head>\n"
                + "		<meta charset=\"UTF-8\">\n"
                + "		<title>Servlet TextProcessing</title>"
                + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />\n"
                + "		<script>\n"
                + "			function myFunction() {\n"
                + "				var j = document.getElementById(\"trustJ\").value;\n"
                + "				var a = document.getElementById(\"trustA\").value;\n"
                + "				document.getElementById(\"trustT\").value = (parseFloat(a)+parseFloat(j))/2;\n"
                + "				document.getElementById(\"trustT2\").value =document.getElementById(\"trustT\").value ;\n"
                + "			}\n"
                + "		</script>\n"
                + "		\n"
                + "	</head>\n"
                + "	<body>\n"
                + "		<div id=\"navigation\">\n"
                + "			<ul>\n"
                + "				<li><a href=\"index.jsp\">Home</a></li>\n"
                + "				<li><a href=\"LoginCheck\">User information</a></li>\n"
                + "				<li><a href=\"ShowText\">Texts</a></li>\n"
                + "				<li><a href=\"OntoDisplay.jsp\">Ontology</a></li>\n"
                + "				<li><a href=\"About\">About</a></li>\n"
                + "				<li><a href=\"#\">Contact</a></li>\n"
                + "			</ul>\n"
                + "			<div class=\"cl\">&nbsp;</div>\n"
                + "		</div>\n"
                + "		<div id=\"wrapper\">\n"
                + "			<fieldset><legend>Text</legend>"
                + text 
                +"</fieldset><br>\n"
                + "			");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String inputid = request.getParameter("inputID");
        String query = request.getParameter("query");
        String queryText = "";
        String select = request.getParameter("target1");
//        String trust2 = request.getParameter("trustT");
        String[] check = request.getParameterValues("trust_check");

        if (check == null) {
            trustT = null;
        } else {
            trustT = request.getParameter("trustT");
        }

        Map<String, Map<String, String>> queries = LoginCheckDB.getQueries(inputid);
        Map<String, String> trusts = LoginCheckDB.getTrusts();
        Map<String, Text> allTexts = LoginCheckDB.getAllTexts();
        Text t = allTexts.get(inputid);
        String user = Data.getUser();
        String trustJ = trusts.get(user + "_" + t.source);
        String trustA = trusts.get(user + "_" + t.author);
        String rdf = t.getRdf();
        ontologie = ModelFactory.createOntologyModel();
        InputStream stream = new ByteArrayInputStream(rdf.getBytes(StandardCharsets.UTF_8));
        ontologie.read(stream, null, null);
//        ontologie.write(System.out); 
        if (query != null && !query.isEmpty() && !query.equals(" ")) {
            query = prefixes + query;
        }
        if (query == null && select == null) {
            PrintWriter out = response.getWriter();
            try {
//                /* TODO output your page here. You may use following sample code. */
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Servlet TextProcessing</title>"
//                        + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />"
//                        + "");
//                out.println("<script>\n"
//                        + "function myFunction() {\n"
//                        + "    var j = document.getElementById(\"trustJ\").value;\n"
//                        + "var a = document.getElementById(\"trustA\").value;\n"
//                        + "document.getElementById(\"trustT\").value = (parseFloat(a)+parseFloat(j))/2;\n"
//                        + "document.getElementById(\"trustT2\").value =document.getElementById(\"trustT\").value ;\n"
//                        //                        + "document.getElementById(\"trust_check\").value = (parseFloat(a)+parseFloat(j))/2;\n"
//                        + ""
//                        + "}\n"
//                        + "</script>");
//                out.println("</head>");
//                out.println("<body>"
//                        + "<div id=\"navigation\">"
//                        + "<ul>"
//                        + "<li><a href=\"index.jsp\">HOME</a></li>"
//                        + "<li><a href=\"LoginCheck\">User information</a></li>"
//                        + "<li><a href=\"ShowText\">Texts</a></li>"
//                        + "<li><a href=\"OntoDisplay.jsp\">Ontology</a></li>"
//                        + "<li><a href=\"About\">ABOUT</a></li>"
//                        + "<li><a href=\"#\">CONTACT</a></li>"
//                        + "</ul>"
//                        + "<div class=\"cl\">&nbsp;</div>"
//                        + "</div>"
//                        + "<br>");
//                out.println("<div id=\"wrapper\">");
//                out.println("<fieldset>"
//                        + "<legend>Text</legend> " + t.content + "</fieldset><br><br> ");
                out.println(getHead(t.content));
                out.println("<table><tr> <th colspan=\"2\">Source</th><th>Trust</th></tr>");
                out.println("<tr>"
                        + "<td class=\"titleCell\">The journal :</td> <td>" + t.source + "</td>"
                        + "<td><input title=\"If you modify this value, modify the author and total trust value please.\" "
                        + "id=\"trustJ\" type=\"text\" name=\"trustJ\" value=\"" + trustJ + "\" onchange=\"myFunction()\"></td>"
                        + "</tr>");
                out.println("<tr>"
                        + "<td class=\"titleCell\">The author :" + t.author + "</td><td></td>"
                        + "<td><input title=\"If you modify this value, modify the journal and total trust value please.\" "
                        + "id=\"trustA\" type=\"text\" name=\"trustA\" value=\"" + trustA + " \" onchange=\"myFunction()\"></td>"
                        + "</tr>");

                out.println("<tr>"
                        //                        + "<td></td>"
                        + "<td colspan=\"2\">The trust granted to the source: </td>"
                        + "<td><input id=\"trustT2\" type=\"text\" name=\"trustT2\" \"></td>"
                        + "</tr>");
//                out.println("<br><br>"
//                        + "<form action=\"OntoDisplay.jsp\"> Do you want to visualize the ontlogy? : <input type=\"submit\" name=\"ontoViz\" value=\"Display ontology \">"
//                        + "</form>");
//                out.println("<br>"
//                        + "<form method=\"post\" action=\"GraphDisplay?inputID=" + inputid + "\"> Do you want to visualize the graph? : <input type=\"submit\" name=\"graphViz\" value=\"Display graph\">"
//                        + "</form> "
//                        + ""
//                        + "\n"
//                        + "</table></div>");
                out.println("<tr>"
                        + "	<td class=\"customizedSubmit\" align=\"center\" colspan=\"3\">"
                        + "		<form action=\"OntoDisplay.jsp\">"
                        + "                 <input type=\"submit\" name=\"ontoViz\" value=\"Visualize Ontology\"/>"
                        + "             </form>"
                        + "		<form method=\"post\" action=\"GraphDisplay?inputID="+inputid+"&graph=graphViz\">"
                        + "                 <input type=\"submit\" name=\"graphViz\" value=\"Visualize Graph GraphViz\"/>"
                        + "             </form>"
                        + "		<form method=\"post\" action=\"GraphDisplay?inputID="+inputid+"&graph=js\">"
                        + "                 <input type=\"submit\" name=\"graphViz\" value=\"Visualize Graph Dracula\"/>"
                        + "             </form>"
                        + "	</td>"
                        + "</tr>");
                out.println("</table>");
                if (ontologie == null) {
                    out.println("Can not create the model");
                } else {
                    out.println("<form method=\"post\" action=\"TextProcessing?inputID=" + inputid + "\">");
                    out.println("<div class=\"customizedForm\">");
                    out.println("<h2>Write your query</h2>");
                    out.println("<div class=\"left\">"
                            + "PREFIX owl: http://www.w3.org/2002/07/owl#"
                            + "<br>PREFIX rdf: http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                            + "<br>PREFIX gs: http://www.geolsemantics.com/onto#"
                            + "<br>PREFIX ical: http://www.w3.org/2002/12/cal/icaltzd#"
                            + "<br>PREFIX wn: http://www.w3.org/2006/03/wn/wn20/"
                            + "<br>PREFIX foaf: http://xmlns.com/foaf/0.1/"
                            + "<br>PREFIX rdfg: http://www.w3.org/2004/03/trix/rdfg-1"
                            + "<br>PREFIX rdfs: http://www.w3.org/2000/01/rdf-schema#"
                            + "<br>PREFIX v: http://www.w3.org/2006/vcard/ns#"
                            + "<br>PREFIX doac: http://ramonantonio.net/doac/0.1/"
                            + "<br>PREFIX prov: http://www.w3.org/ns/prov#"
                            + "<br>"
                            + "</div>");
                    out.println("<div class=\"right\">"
                            + "<textarea rows=\"13\" cols=\"60\" name=\"query\"\"> </textarea> <br>"
                            + "<input type=\"checkbox\" id=\"trust_check\" name=\"trust_check\" >Apply confidence degree  "
                            + "<input id=\"trustT\" type=\"text\" name=\"trustT\" \">"
                            + "<br>"
                            + "<input type=\"submit\" value=\"Submit\">"
                            + "</div></div>" //                        + "</form>"
                    );
                    out.println("<div class=\"customizedForm\">");
                    out.println(""
                            + "<h2> Or, select a query </h2>"
                            + "<br>");
                    out.println(
                            //                        "<Form >"+ 
                            "<Select id=\"target1\" name=\"target1\" action=\"TextProcessing\" onselect=\"test()\">"
                            + "<option value=\"0\">Ask if the author is sur about all what he said?</option>"
                            + "<option value=\"00\">Who is the author? and ho much do you trust it?</option>");
                    for (String key : queries.keySet()) {
                        Map<String, String> get = queries.get(key);
                        out.println("<option value=\"" + get.get("query_id") + "\">" + get.get("query_text") + "</option>");
                    }
                    out.println("</Select>"
                            + "<input type=\"submit\" value=\"Run query\">"
                            + "</Form>"
                            + "</div>");
                }
                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
            }
        } else if (select != null 
                && (query == null || query.isEmpty() || query.equals(" "))
                ) {
            {
                String selectedItem = select;
                query = "";
//                if (null != selectedItem) {
                if (selectedItem.equals("0")) {
                    //savoir si l'auteur est sÃ»r de ce qu'il sait
                    query = "PREFIX gs: <http://www.geolsemantics.com/onto#>"
                            + "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                            + "PREFIX  v: <http://www.w3.org/2006/vcard/ns#>"
                            + "Select ?predicatePred ?subject ?pred2 ?object Where {"
                            + "?x rdf:type gs:AuthorUncertainty."
                            + "?x ?predicatePred ?subject."
                            + "?subject ?pred2 ?object."
                            + "}";

                } else if (selectedItem.equals("00")) {
                    //qui est la source, quelle degrÃ© de confiance je lui accorde
                    query = "PREFIX prov: <http://www.w3.org/ns/prov#>"
                            + "Select ?author ?journal Where {"
                            + "?x prov:wasAttributedTo ?author."
                            + "?author prov:OnBehalfOf ?journal."
                            + "}";
                } else {
                    for (String key : queries.keySet()) {
                        Map<String, String> get = queries.get(key);
                        if (get.get("query_id").equals(selectedItem)) {
                            query = prefixes + get.get("query_sparql");
                            queryText = get.get("query_text");
                            break;
                        }
                    }
                }
//                }
            }
        }
        //else 
        //Si la requete n'est pas vide alors l'executer
        {
            if (query != null && !query.isEmpty()) {
                PrintWriter out = response.getWriter();
                try {
//                    /* TODO output your page here. You may use following sample code. */
//                    out.println("<html>");
//                    out.println("<head>");
//                    out.println("<title>Servlet TextProcessing</title>"
//                            + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />");
//                    out.println("</head>");
//                    out.println("<body>"
//                            + "<div id=\"navigation\">"
//                            + "<ul>"
//                            + "<li><a href=\"index.jsp\">HOME</a></li>"
//                            + "<li><a href=\"LoginCheck\">User information</a></li>"
//                            + "<li><a href=\"ShowText\">Texts</a></li>"
//                            + "<li><a href=\"OntoDisplay.jsp\">Ontology</a></li>"
//                            + "<li><a href=\"About\">ABOUT</a></li>"
//                            + "<li><a href=\"#\">CONTACT</a></li>"
//                            + "</ul>"
//                            + "<div class=\"cl\">&nbsp;</div>"
//                            + "</div>"
//                            + "<br>");
                    out.println(getHead(t.content));
                    
                    
                    out.println("&nbsp;&nbsp;<h2>The source :</h2> " + t.source);
                    out.println("&nbsp;&nbsp;<h2>The author :</h2> " + t.author);
                    out.println("<br><b>The trust granted :</b> <input type=\"text\" name=\"trust\" value=\"" + trustT + "\">");
                    if (ontologie == null) {
                        out.println("Can not create the model, there is a problem with the RDF");
                    } else {
//                        query = "";
                        if (!queryText.isEmpty()) {
                            out.println("<h2>The query text is:</h2><br><font color=\"blue\"> " + queryText + "</font>");

                        }
                        if (!query.isEmpty()) {
                            out.println("<h2>The original query is:</h2><br> " + 
                                    query.replaceAll("<", "")
                                            .replaceAll(">", "")
                                            .replaceAll("\n", "<br>")
                                            .replaceAll("\\.\\?", "\\.<br>\\?"));
                            out.println("<h2>Result of the original query:</h2><br>" + sublimResult(executeQuery(query, trustT)));
                            Queries q=new Queries(rdf, query);
                            out.println("new uncertainty : "+q.RunQueryWithUncertainty());
                            String rewriteQuery = new QueryTriples(query).rewriteQuery();
                            out.println("<h2>The rewriting of the query is : </h2><br>" + rewriteQuery.replaceAll("<", "").replaceAll(">", "").replaceAll("\n", "<br>"));
                            out.println("<h2>Result of the rewrited query:</h2><br>" + sublimResult(executeQuery(rewriteQuery, trustT)));
                            //si le rÃ©sultat est vide essayer de chercher les sous propriÃ©tÃ©s ex: isAgent=> isA
                        }
                    }
                } catch (Exception ex) {
                    out.println("<h2>Problem encourted : </h2>"
                            + "<b><font color=\"red\">Sorry a problem accored during the execution of the query :</font></b> <br>" + sublimResult(query));
                    out.println("<h2>The exception: </h2>" + ex);
                } finally {
                     out.println("</div>"); //wrapper
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                }
            }
        }

    }

    public String sublimResult(String res) {

        res = res.replaceAll(": <http", ": http");
        res = res.replaceAll(">\n", "<br>");
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
