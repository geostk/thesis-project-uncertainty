/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.SevletPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import univ.mlv.development.LoginCheckDB;
import univ.mlv.Structures.Data;

/**
 *
 * @author fadhela-pc
 */
public class LoginCheck extends HttpServlet {

    public static String username = "";
    public String password = "";

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

        String username = request.getParameter("user_name");
        if(username!=null){
            Data.setUser(username);
        }
        else{
            username=Data.getUser();
        }
        String password = request.getParameter("password");
        Map<String, String> check = LoginCheckDB.check(username, password);
        List<String> sources = LoginCheckDB.getSources();
        Map<String, String> trusts = LoginCheckDB.getTrusts();
        boolean b = false;
        String modify_opt= request.getParameter("modifySources");
        if (modify_opt!=null && modify_opt.equals("true")) {
            b = true;
        }
        if (b) {
            int j = 0;
            //Modification de la base de données
            for(String source:sources){
                String trust_val= request.getParameter(username+"_"+source);
                if(trust_val!=null){
                    j= LoginCheckDB.updateTrust(username, source, trust_val);
                    
                }
            }
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                /* TODO output your page here. You may use following sample code. */
                out.println("<html>");
                out.println("<head>");
                out.println("<title>User Interface</title>"
                        + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />");
                out.println(checkInputValue_script());
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

                if (check.isEmpty()) {
                    out.println("<h1>Sorry your login and password doesn't extist</h1>");
                } else {
                    out.println("<form action=\"NewUser?user_name=" + username + "\" >");
                    out.println("<h1>User Information </h1>");
                    out.println("<h2>Welcome to " + check.get("first_name") + check.get("last_name") + "</h2>");
                    out.println("<fieldset>"
                            + "<legend>Personal information:</legend>"
                            + "User name: " + check.get("user_name")
                            + "<br>"
                            + "<b>First name:</b> <i>" + check.get("first_name")
                            + "</i><br>"
                            + "<b>Last name:</b><i> " + check.get("last_name")
                            + "</i><br>"
                            + "<b>Organization:</b><i> " + check.get("organization")
                            + "</i><br>"
                            + "<b>Homepage:</b><i> " + check.get("home_page")
                            + "</i>"
                            + "<br>"
                            + " <input type=\"submit\" value=\"Modify\">"
                            + "<br> </fieldset>"
                            + "</form>");
                    out.println("<h1>User Preferences </h1>" + "<br>");
                    if (sources.isEmpty()) {
                        out.println("Sorry No Source Available");
                    } else {
                        if(j==1){
                            out.println("<h2>The trusts have been updated</h2>");
                        }
                        else{
                            out.println("<h2>Sorry, the trusts can't be updated</h2>");
                        }
                        out.println("<form method=\"post\" "
                                + "action=\"LoginCheck"
                                + "> "
                                + "<table><tr>"
                                + "<th><b>Source</b></th><th><b>Trust</b></th></tr>");
                        int i = 0;
                        for (String s : sources) {
                            String value = trusts.get(check.get("user_name") + "_" + s);
                            String val = Integer.toString(i++);
                            out.println("<tr>"
                                    + "<td>" + s + "</td>"
                                    + "<td> <input type=\"text\" value=\"" + value + "\" "
                                    + "id=\"input" + val + "\" onblur=\"myFunction('input" + val + "')\" "
                                    + "name=\""+username+"_"+s+"\">"
                                    + "<div style=\"display:inline;\" id=\"control_input" + val + "\"></div> </td>"
                                    //                                + "<td><input type=\"submit\" value=\"modify value\"></td>"
                                    + "</tr>");
                        }
                        out.println("</table>"
                                + "<input type=\"submit\" value=\"modify value\">"
                                + "<input type=\"hidden\" name=\"modifySources\" value=\"true\">"
                                + "</form>"
                                + "<form action=\"ShowText\""
                                + ">"
                                + "<input type=\"submit\" value=\"Show texts\">"
                                + "</form>");

                    }
                }

                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
            }
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                /* TODO output your page here. You may use following sample code. */
                out.println("<html>");
                out.println("<head>");
                out.println("<title>User Interface</title>"
                        + "<link rel=\"stylesheet\" href=\"Style.css\" type=\"text/css\" media=\"all\" />");
                out.println(checkInputValue_script());
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

                if (check.isEmpty()) {
                    out.println("<h1>Sorry your login and password doesn't extist</h1>");
                } else {
                    out.println("<form action=\"NewUser?user_name=" + username + "\" >");
                    out.println("<h1>User Information </h1>");
                    out.println("<h2>Welcome to " + check.get("first_name") + check.get("last_name") + "</h2>");
                    out.println("<fieldset>"
                            + "<legend>Personal information:</legend>"
                            + "User name: " + check.get("user_name")
                            + "<br>"
                            + "<b>First name:</b> <i>" + check.get("first_name")
                            + "</i><br>"
                            + "<b>Last name:</b><i> " + check.get("last_name")
                            + "</i><br>"
                            + "<b>Organization:</b><i> " + check.get("organization")
                            + "</i><br>"
                            + "<b>Homepage:</b><i> " + check.get("home_page")
                            + "</i>"
                            + "<br>"
                            + " <input type=\"submit\" value=\"Modify\">"
                            + "<br> </fieldset>"
                            + "</form>");
                    out.println("<h1>User Preferences </h1>" + "<br>");
                    if (sources.isEmpty()) {
                        out.println("Sorry No Source Available");
                    } else {
                        out.println("<form method=\"post\""
                                + "action=\"LoginCheck\""
                                + "> "
                                + "<table><tr>"
                                + "<th><b>Source</b></th><th><b>Trust</b></th></tr>");
                        int i = 0;
                        for (String s : sources) {
                            String value = trusts.get(check.get("user_name") + "_" + s);
                            String val = Integer.toString(i++);
                            out.println("<tr>"
                                    + "<td>" + s + "</td>"
                                    + "<td> <input type=\"text\" value=\"" + value + "\" "
                                    + "id=\"input" + val + "\" "
                                    + "onblur=\"myFunction('input" + val + "')\""
                                    + "name=\""+username+"_"+s+"\">"
                                    + "<div style=\"display:inline;\" id=\"control_input" + val + "\"></div> </td>"
                                    //                                + "<td><input type=\"submit\" value=\"modify value\"></td>"
                                    + "</tr>");
                        }
                        out.println("</table>"
                                + "<input type=\"submit\" value=\"modify value\">"
                                + "<input type=\"hidden\" name=\"modifySources\" value=\"true\">"
                                + "</form>"
                                + "<form action=\"ShowText\""
                                + ">"
                                + "<input type=\"submit\" value=\"Show texts\">"
                                + "</form>");
                    }
                }
                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
            }

        }
    }

    public String checkInputValue_script() {
        return "<script>"
                + "function myFunction(myname) {"
                + "var x = document.getElementById(myname).value;"
                + "if (isNaN(x) || x < 0 || x > 1) {"
                + "document.getElementById(\"control_\" + myname).innerHTML = \"Invalid value, it must be in [0-1]\";"
                + "} else {"
                + "document.getElementById(\"control_\" + myname).innerHTML = \"Ok\";"
                + "}"
                + "}"
                + "</script>";


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

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
