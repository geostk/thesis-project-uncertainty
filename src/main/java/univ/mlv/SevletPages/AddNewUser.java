/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.SevletPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import univ.mlv.development.LoginCheckDB;

/**
 *
 * @author fadhela-pc
 */
public class AddNewUser extends HttpServlet {

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
        Map<String, String> map_info = new TreeMap();
        Map<String, String> trusts = new TreeMap<String, String>();
        String user_name = request.getParameter("user_name");

        String pw = request.getParameter("password");
        map_info.put("user_name", user_name);
        map_info.put("first_name", request.getParameter("first_name"));
        map_info.put("last_name", request.getParameter("last_name"));
        map_info.put("organization", request.getParameter("organization"));
        map_info.put("home_page", request.getParameter("home_page"));
        map_info.put("password", pw);

//        //ajouter un utilisateur à la base de données
//        String user_name= request.getParameter("user_name");
//        String password = request.getParameter("password");
//        String first_name = request.getParameter("first_name");
//        String last_name = request.getParameter("last_name");
//        String organization = request.getParameter("organization");
//        String home_page = request.getParameter("home_page");

        //Add trust
        for (String s : allSources) {
            if (request.getParameter("trust" + "_" + s) != null && !"".equals(request.getParameter("trust" + "_" + s))) {
                trusts.put(request.getParameter("user_name") + " " + s, request.getParameter("trust" + "_" + s));
            }
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddNewUser</title>"
                    + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />");

            out.println("</head>");
            out.println("<body>"
                     + "<div id=\"navigation\">"
                    + "<ul>"
                    + "<li><a href=\"index.jsp\">HOME</a></li>"
                    + "<li><a href=\"LoginCheck\">User information</a></li>"
                    + "<li><a href=\"ShowText\">Texts</a></li>"
                    + "<li><a href=\"OntoDisplay\">Ontology</a></li>"
                    + "<li><a href=\"About\">ABOUT</a></li>"
                    + "<li><a href=\"#\">CONTACT</a></li>"
                    + "</ul>"
                    + "<div class=\"cl\">&nbsp;</div>"
                    + "</div>"
                    + "");
            //vérifier si le user_name existe deja 
            if (LoginCheckDB.checkPrimaryKey(user_name, "User")) {

                out.println("<h1>Sorry this user name has been already used, please enter an other one</h1>");
                out.println("<a href=\"LoginCheck?user_name=" + user_name + "?password=" + pw + "\">Back to user page</a>");

            } else {
                //Mettre à jour la BD en ajoutant ces informations
                LoginCheckDB.AddNewUser(map_info);
                LoginCheckDB.AddNewTrust(trusts);

                out.println("<h1>Congratulations : user added !!!</h1>");
                out.println("<a href=\"LoginCheck?user_name=" + user_name + "?password=" + pw + "\">Back to user page</a>");
            }
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
