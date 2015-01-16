/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.SevletPages;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fadhela-pc
 */
public class About extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet About</title>"
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
            out.println("<h1> Paris-Est Marne-La-Vallée"
                    + "</h1>"
                    + "<a href=\"http://www.u-pem.fr\"> <img src=\"Logo_UPEM.png\" alt=\"image\" width=\"285px\" align=\"middle\"> </a>");
            out.println("<h1> GeolSemantics"
                    + "</h1>"
                      + "<a href=\"http://www.geolsemantics.com\"> <img src=\"Logo_GEOL.png\" alt=\"image\" width=\"285px\"> </a>");
            out.println("<h1> Laboratoire d'Informatique Gaspard-Monge UMR 8049" 
                    + "</h1>"
                    + "<a href=\"http://ligm.u-pem.fr/\"> <img src=\"Logo_LIGM.png\" alt=\"image\" width=\"285px\"> </a>");
            
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
