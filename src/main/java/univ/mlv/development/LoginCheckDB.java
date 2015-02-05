/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.development;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author fadhela-pc
 */
public class LoginCheckDB {
    // JDBC driver name and database URL

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/trustsource";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    public static List<String> users = new ArrayList<String>();
    public static List<String> sources = new ArrayList<String>();
    public static Map<String, String> trusts = new TreeMap<String, String>();

    
    public static boolean checkPrimaryKey(String key, String table) {
        boolean ok= false;
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql_user;
            sql_user = "SELECT user_name FROM "+table.toLowerCase()+""
                    + "WHERE user_name='"+key+ "';";
            ResultSet rs = stmt.executeQuery(sql_user);
            if(rs!=null){
                ok= true;
            }
            else {
                ok= false;
            }
            
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return ok;
    }//end main    


    public static int updateTrust(String user, String source, String value){
        Connection conn = null;
        Statement stmt = null;
        int i=0;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql_user;
            
            sql_user = "SELECT trust_value FROM trustworthiness "
                    + "WHERE user_name='"+user+ "' AND source_name='"+source+"';";
            ResultSet rs = stmt.executeQuery(sql_user);
            if(rs!=null){
               //excute update
                sql_user = "UPDATE trustworthiness set trust_value= "+value
                    + " WHERE user_name='"+user+ "' AND source_name='"+source+ "';";
            }
            else {
                //execute insert
                sql_user= "INSERT INTO trustworthiness VALUES ('"+user+"_"+source+"', '"+user+"', '"+source+"', '"+value+"');";
            }
            i=stmt.executeUpdate(sql_user);
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return i;
    }
    
    public static Map<String, Map<String, String>> getQueries (String inputID){
        Map<String, Map<String, String>> m = new TreeMap<String, Map<String, String>>();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql_user;
            sql_user = "SELECT * "
                    + "FROM queries "
                    + "WHERE  text_id=\"" + inputID + "\" ;";
            ResultSet rs = stmt.executeQuery(sql_user);
            int i=0;
            //STEP 5: Extract data from result set
            while (rs.next()) {
                Integer.toString(i++);
                if(m.containsKey(Integer.toString(i))){
                    m.get(Integer.toString(i)).put("text_id", rs.getString("text_id"));
                    m.get(Integer.toString(i)).put("query_id", rs.getString("query_id"));
                    m.get(Integer.toString(i)).put("query_text", rs.getString("query_text"));
                    m.get(Integer.toString(i)).put("query_sparql", rs.getString("query_sparql"));
                    
                }
                else {
                    Map<String, String> mm = new TreeMap<String, String>();
                    mm.put("text_id", rs.getString("text_id"));
                    mm.put("query_id", rs.getString("query_id"));
                    mm.put("query_text", rs.getString("query_text"));
                    mm.put("query_sparql", rs.getString("query_sparql"));
                    m.put(Integer.toString(i), mm);
                    
                }
            }
            
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return m;
    }
    public static Map<String, String> check(String login, String pw) {
        Map<String, String> map_info = new TreeMap();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql_user;
            sql_user = "SELECT user_name, first_name, last_name, organization, home_page, password "
                    + "FROM user "
                    + "WHERE  user_name=\"" + login + "\" "
                    + "AND password=\"" + pw + "\";";
            ResultSet rs = stmt.executeQuery(sql_user);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                map_info.put("user_name", rs.getString("user_name"));
                map_info.put("first_name", rs.getString("first_name"));
                map_info.put("last_name", rs.getString("last_name"));
                map_info.put("organization", rs.getString("organization"));
                map_info.put("home_page", rs.getString("home_page"));
                map_info.put("password", rs.getString("password"));

            }
            String sql_source;
            sql_source = "SELECT source_name "
                    + "FROM  source"
                    + ";";
            ResultSet rs2 = stmt.executeQuery(sql_source);

            //STEP 5: Extract data from result set
            while (rs2.next()) {
                //Retrieve by column name
                map_info.put("user_name", rs2.getString("user_name"));
                map_info.put("first_name", rs2.getString("first_name"));
                map_info.put("last_name", rs2.getString("last_name"));
                map_info.put("organization", rs2.getString("organization"));
                map_info.put("home_page", rs2.getString("home_page"));
                map_info.put("password", rs2.getString("password"));

            }
            //STEP 6: Clean-up environment
            rs2.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return map_info;
    }//end main    

    public static void AddNewUser(Map<String, String> user) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql_user;
            String user_name= user.get("user_name");
            String pw= user.get("password");
            //vérifier si ce user-name exist dans la bd
            
            sql_user = "INSERT into user (user_name , first_name, last_name, organization, home_page, password) "
                    + "values ( '" + user.get("user_name") + "' , '" + user.get("first_name") + "' , "
                    + "'" + user.get("last_name") + "'" + " , " + "'" + user.get("organization") + "'" + " , "
                    + "'" + user.get("home_page") + "' , "
                    + "'" + user.get("password") + "' "
                    + ");";
            stmt.executeUpdate(sql_user);
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");

    }

    public static void AddNewTrust(Map<String, String> trust) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql_user;
            for (String key : trust.keySet()) {
                sql_user = "INSERT into trustworthiness (user_source , user_name, source_name, trust_value) "
                        + "values ( '" + key + "' , '" + key.split(" ")[0] + "' , '"
                        + key.split(" ")[1] + "'" + " , " + "'" + trust.get(key) + "'"
                        + ");";
                stmt.executeUpdate(sql_user);
            }
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");

    }

    /**
     * @return the users
     */
    public static List<String> getUsers() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql_user;
            sql_user = "SELECT user_name "
                    + "FROM user "
                    + ";";
            ResultSet rs = stmt.executeQuery(sql_user);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                users.add(rs.getString("user_name"));
            }
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");

        return users;
    }

    /**
     * @param aUsers the users to set
     */
    public static void setUsers(List<String> aUsers) {
        users = aUsers;
    }

    /**
     * @return the sources
     */
    public static List<String> getSources() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql_user;
            sql_user = "SELECT source_name "
                    + "FROM Source "
                    + ";";
            ResultSet rs = stmt.executeQuery(sql_user);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                sources.add(rs.getString("source_name"));
            }
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return sources;
    }

    /**
     * @param aSources the sources to set
     */
    public static void setSources(List<String> aSources) {
        sources = aSources;
    }

    /**
     * @return the trusts
     */
    public static Map<String, String> getTrusts() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql_user;
            sql_user = "SELECT user_name, source_name, trust_value "
                    + "FROM Trustworthiness "
                    + ";";
            ResultSet rs = stmt.executeQuery(sql_user);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                trusts.put(rs.getString("user_name") + "_" + rs.getString("source_name"), rs.getString("trust_value"));
            }
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");

        return trusts;
    }
    
    
    public static Map<String,Text> getAllTexts(){
        Map<String,Text> l= new TreeMap<String, Text>();
        
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql_user;
            sql_user = "SELECT * "
                    + "FROM text "
                    + ";";
            ResultSet rs = stmt.executeQuery(sql_user);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                Text t= new Text(rs.getString("textID"),
                        rs.getString("content"),
                        rs.getString("rdf"),
                        rs.getString("source"),
                        rs.getString("author"),
                        rs.getString("dateRef"));
                l.put(rs.getString("textID"), t);
            }
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return l;
    }

    /**
     * @param aTrusts the trusts to set
     */
    public static void setTrusts(Map<String, String> aTrusts) {
        trusts = aTrusts;
    }
}
