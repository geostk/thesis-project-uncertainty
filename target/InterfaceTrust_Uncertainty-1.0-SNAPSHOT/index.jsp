<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <title>CodePen - Login & Register form</title> 
         <link rel="stylesheet" href="StyleLogin.css" media="screen" type="text/css" />
    </head> 
    <body> 
        <h1>Login</h1> 
<!--        <center> <h2>Signup Details</h2> 
            <div class="login-wrap">
                <form action="LoginCheck" method="post" > 
                    <br/>
                    <table>
                        <tr>
                            <td>Username:</td><td><input type="text" name="user_name"> </td>
                        </tr>
                        <tr>
                            <td>Password</td><td><input type="password" name="password"> </td>
                        </tr>
                        <tr>
                            <td></td><td><input class="button" type="submit" value="Submit"> </td>
                        </tr>
                        <tr>
                            <td></td><td><a href="NewUser"> New user?</a> </td>
                        </tr>
                    </table>

                </form> 
            </div>
        </center> -->

<div class="login-wrap">
  <h2>Login</h2>
  <form  action="LoginCheck" method="post" >
  <div class="form" >
    <input type="text" placeholder="Username" name="user_name" />
    <input type="password" placeholder="Password" name="password" />
    <input class="bou" type="submit" value="Submit">
    <a href="NewUser"> <p> Don't have an account? Register </p></a>
  </div>
      </form>
</div>
    </body>


</html>
