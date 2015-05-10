/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.SevletPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom2.JDOMException;
import univ.mlv.GraphBuilder.GraphConstructor;
import univ.mlv.development.LoginCheckDB;
import univ.mlv.development.Text;
import univ.mlv.development.CreateJSFiles;

/**
 *
 * @author Fadhela
 */
public class GraphDisplay extends HttpServlet {

    private static String TEMP_DIR = "";

    public static String getNavigation(String content) {
        return "		<div id=\"navigation\">"
                + "			<ul>"
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
                + content
                + "</fieldset><br>\n"
                + "			";

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

        String inputid = request.getParameter("inputID");
        String drawing = request.getParameter("graph");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(drawing!=null && drawing.equals("js")){
        try {
            //Solution avec Dracula.js
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Graph Displaying</title>"
                    + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />\n"
                    + "<script type=\"text/javascript\" src=\"js/DraculaJS/raphael-min.js\"></script>\n"
                    + "    <script type=\"text/javascript\" src=\"js/DraculaJS/dracula_graffle.js\"></script>\n"
                    + "    <script type=\"text/javascript\" src=\"js/DraculaJS/jquery-1.4.2.min.js\"></script>\n"
                    + "    <script type=\"text/javascript\" src=\"js/DraculaJS/dracula_graph.js\"></script>\n");
//                        + "    <script type=\"text/javascript\" src=\""+TEMP_DIR+"\"></script>"
            if (null != inputid && !inputid.isEmpty() && !inputid.equals("")) {
                Map<String, Text> allTexts = LoginCheckDB.getAllTexts();
                Text t = allTexts.get(inputid);
                String rdf = t.getRdf();
                String jsGraph = "";
                if (null != rdf && !rdf.isEmpty()) {
                    GraphConstructor g = new GraphConstructor(rdf);
                    jsGraph = CreateJSFiles.getJSGraph();
//                    TEMP_DIR=CreateJSFiles.getJsPath();
                    TEMP_DIR = //CreateJSFiles.getJsPath()
                            g.getJsPath();
//                    if (null != g.getSvg()) {
//                        out.println(g.getSvg());
//                    } else {
//                        out.println("Sorry, the graph can not be created ");
//                    }
                }
                out.println(jsGraph
                        + "");

                out.println("</head>");

                out.println("<body>");
                Text text = allTexts.get(inputid);
                out.println(getNavigation(text.getContent()));
                out.println("<div id=\"canvas\"></div>");
                out.println("<button id=\"redraw\" onclick=\"redraw();\">redraw</button>");
                out.println("</div>"); //wrapper
            } else {
                out.println("<body>");
                out.println("Impossible d'accéder à l'entrée demandée " + inputid);
            }
        } finally {
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
        }
        else if(drawing!=null && drawing.equals("graphViz")){
        //solution avec graphviz
        try {
            //Solution avec Dracula.js
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Graph Displaying</title>"
                    + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />\n"
            );

            out.println("</head>");
            out.println("<body>");

//                        + "    <script type=\"text/javascript\" src=\""+TEMP_DIR+"\"></script>"
            if (null != inputid && !inputid.isEmpty() && !inputid.equals("")) {
                Map<String, Text> allTexts = LoginCheckDB.getAllTexts();
                Text t = allTexts.get(inputid);
                String rdf = t.getRdf();
//                Text text = allTexts.get(inputid);
                out.println(getNavigation(t.getContent()));

                if (null != rdf && !rdf.isEmpty()) {
                    try {
                        GraphConstructor g = new GraphConstructor(rdf, "en", t.getContent());
                        String svg = g.getSvg();
                        if (svg == null || svg.isEmpty()) {
                            out.println("<h1>Désolé impossible de créer le svg </h1>");
                            out.println("<div>" + rdf + "</div>");
                        } else {
                            out.println("<div id=\"canvas\">" + svg + "</div>");
                        }
                    } catch (JDOMException ex) {
                        Logger.getLogger(GraphDisplay.class.getName()).log(Level.SEVERE, null, ex);
                        out.println("exception : " + ex);
                    }
                    out.println("</div>"); //wrapper
                } else {
                    out.println("Impossible d'accéder à l'entrée demandée " + inputid);
                }
            }
        } finally {
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
        } else if(drawing == null){
         try {
            //Solution avec Dracula.js
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Graph Displaying</title>"
                    + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />\n"
            );

            out.println("</head>");
            out.println("<body>");

//                        + "    <script type=\"text/javascript\" src=\""+TEMP_DIR+"\"></script>"
            if (null != inputid && !inputid.isEmpty() && !inputid.equals("")) {
                Map<String, Text> allTexts = LoginCheckDB.getAllTexts();
                Text t = allTexts.get(inputid);
                String rdf = t.getRdf();
//                Text text = allTexts.get(inputid);
                out.println(getNavigation(t.getContent()));

                if (null != rdf && !rdf.isEmpty()) {
                    out.println("display graph not found");
                    out.println("</div>"); //wrapper
                } else {
                    out.println("Impossible d'accéder à l'entrée demandée " + inputid);
                }
            }
        } finally {
            out.println("</body>");
            out.println("</html>");
            out.close();
        }   
        }
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
