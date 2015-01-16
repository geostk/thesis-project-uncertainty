<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <title>JSP Page</title> 
    </head> 
    <body> 
        <h1>Login Page</h1> 
        <center> <h2>Signup Details</h2> 
            <form action="LoginCheck" method="post"> 
                <br/>
                <table>
                    <tr>
                        <td>Username:</td><td><input type="text" name="user_name"> </td>
                    </tr>
                    <tr>
                        <td>Password</td><td><input type="password" name="password"> </td>
                    </tr>
                    <tr>
                        <td></td><td><input type="submit" value="Submit"> </td>
                    </tr>
                    <tr>
                        <td></td><td><a href="NewUser"> New user?</a> </td>
                    </tr>
                </table>
                
            </form> 
        </center> 
    </body>


</html>
