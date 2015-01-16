/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.SevletPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import univ.mlv.development.Text;
import univ.mlv.development.LoginCheckDB;

/**
 *
 * @author fadhela-pc
 */
public class ShowText extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Text> allTexts = LoginCheckDB.getAllTexts();
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowText</title>"
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

                    + "<br>");
            out.println("<h1>Please select a text : </h1>");
            out.println("<form name=\"inputId\" method=\"POST\" "
                    + "action=\"TextProcessing\""
                    + ">");
            if (allTexts.isEmpty()) {
                out.println("<h2>Sorry there is a problem, we can not retrieve the texts</h2>");
            } else {
                for (String id : allTexts.keySet()) {
                    Text t = allTexts.get(id);
                    out.println("<div class=\"input\">");
                    out.println("<INPUT TYPE=\"radio\" NAME=\"inputID\" VALUE=\"" + id + "\">" + t.getContent()
                            + "<BR><BR>"
                            + "<B>Date:</B> " + t.getDateRef()
                            + "<BR>"
                            + "<B>Source:</B> " + t.getSource()
                            + "<BR>"
                            + "<B>Auteur:</B> " + t.getAuthor());
                    out.println("</div>");
                    out.println("<br><br>");
                }

            }

                out.println(""
                    + "        \n"
                    + "            <br>\n"
                    + "            <INPUT TYPE=\"submit\" VALUE=\"Submit\" >"
                    + "</form>\n");

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
