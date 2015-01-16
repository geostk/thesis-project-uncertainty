/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.SevletPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import univ.mlv.OntologyManagment.OntologyParsing;

/**
 *
 * @author fadhela-pc
 */
public class OntoViz extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OntologyParsing o = new OntologyParsing();
        List<Map<String, String>> classes = o.getClasses();
        List<Map<String, String>> objProp = o.getObjProp();
        Map<String, List<String>> class_prop = o.getClass_prop();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OntoViz</title>"
                    + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />");
            out.println("</head>");
            out.println("<body>"
                    + "<div id=\"navigation\">"
                    + "<ul>"
                    + "<li><a href=\"index.jsp\">HOME</a></li>"
                    + "<li><a href=\"LoginCheck\">User information</a></li>"
                    + "<li><a href=\"ShowText\">Texts</a></li>"
                    + "<li><a href=\"OntoViz\">Ontology</a></li>"
                    + "<li><a href=\"About\">ABOUT</a></li>"
                    + "<li><a href=\"#\">CONTACT</a></li>"
                    + "</ul>"
                    + "<div class=\"cl\">&nbsp;</div>"
                    + "</div>"
                    + "");
            out.println("<h1>Classes and properties of the ontology</h1>");
            out.println("<div class=\"CSSTableGenerator\" >"
                    + "<table >"
                    + "<tr>"
                    + "<th> Class </th>"
                    + "<th > Property </th> "
                    + "<th> Range </th> ");
            Set<String> keySet = class_prop.keySet();
            for(String s:keySet){
                List<String> l = class_prop.get(s);
//                out.println("<tr> <td>rowspan=\""+l.size()+"\"</td>");
                for(String ls:l){
                    out.println("<tr><td>"+s+"</td><td>"+ls.split("\t")[0]+"</td><td>"+ls.split("\t")[1]+"</td></tr>");
                }
//                out.println("</tr>");
            }
            out.println("</table></div>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
