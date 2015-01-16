/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.SevletPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import univ.mlv.development.LoginCheckDB;

/**
 *
 * @author fadhela-pc
 */
public class NewUser extends HttpServlet {

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
        List<String> allSources = LoginCheckDB.getSources();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddNewUser</title>"
                     + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />");            
             out.println("<script  type=\"text/javascript\">"
                    + 
            "<!-- var requete;"
                    + "function valider"
                    + "() {"
                    + "var donnees = document.getElementById(\"user_name\");"
                    + "var url = \"valider?valeur=\" + escape(donnees.value);"
                    + "if (window.XMLHttpRequest) {"
                    + "requete = new XMLHttpRequest();"
                    + "                } else if (window.ActiveXObject) {"
                    + "requete = new ActiveXObject(\"Microsoft.XMLHTTP\");"
                    + "               }"
                    + "requete.open(\"GET\", url, true);"
                    + "requete.onreadystatechange = majIHM;"
                    + "requete.send(null);"
                    + "           }            "
                    + "</script>");
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
            out.println("<form method=\"post\" action=\"AddNewUser\">\n"
                    + "<table>"
                    + "<tr><td>User name: </td><td><input type=\"text\" id=\"user_name\" name=\"user_name\"></td></tr><br>"
                    + "<tr><td>Password: </td><td><input type=\"password\" name=\"password\"> </td></tr><br>\n"
                    + "<tr><td>First name:</td><td> <input type=\"text\" name=\"first_name\"></td></tr><br>"
                    + "<tr><td>Last name:</td><td> <input type=\"text\" id=\"user\" name=\"last_name\"></td></tr><br>"
                    + "<tr><td>Organization:</td><td> <input type=\"text\" id=\"user\" name=\"organization\"></td></tr><br>"
                    + "<tr><td>Homepage: </td><td><input type=\"text\" id=\"user\" name=\"home_page\"></td></tr>"
                    + "</table>"
                    + "<br>"
                    + "<br>"
                    + "<br>"
                    //                    + "            Source/Trust : "
                    + "<table><tr><td><b>Source</b></td><td><b>Trust</b></td></tr>");
            if (!allSources.isEmpty()) {
                for (String source : allSources) {
                    out.println("<tr><td>" + source + "</td>"
                            + "<td><input type=\"text\" id=\"trust_" + source + "\" name=\"trust_" + source + "\"> </td></tr>");
                }
            } else {
                out.println("<tr><td>No sources</td></tr>");
            }


//                    + "<select id=\"source\"  name=\"source\">\n");
//            if(!allSourceIndividuals.isEmpty()){
//            for (String source : allSourceIndividuals) {
//                out.println("<option name=\""+source +"\">"+source +"</option>\n");
//            }    
//            }
//            else{
//                out.println("<option name=\"No source \">No source </option>\n");
//            }

            out.println(" </table><br><br>\n"
                    //                        + "            Trust: <input type=\"text\" name=\"trust\"><br>\n"
                    + "            <input type=\"submit\" value=\"Submit\" onclick=\"\"/>\n"
                    + "<p><b>Note:</b> The characters in trust field is a real number between [0-1].</p>"
                    + "            \n"
                    + "        </form>");
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
