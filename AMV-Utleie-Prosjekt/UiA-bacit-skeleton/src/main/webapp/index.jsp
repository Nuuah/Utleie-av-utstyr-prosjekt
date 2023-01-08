<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
    <body>
        <h2 style="text-align: center; font-family: Verdana; color:#11165a;"><%= "Velkommen til AMV-Velferden!" %></h2><br/>

        <br><h2 style="text-align: center; font-family: Verdana;color:#11165a;"><%= "Logg inn" %></h2><br/>

        <br><br/>

        <style >
            form {display:flex;justify-content:center;align-items:center; flex-direction:column;}
            label{width:130px;display:inline-block;margin:10px;}

        </style>
<div>
    <form action="login" method="post">
         <div class="container">
              <div>
                    <br><label><b style="font-family:Verdana; font-size:14px;color:#11165a;">Telefonnummer</b></label><br>
                        <input type="text" placeholder="Skriv inn telefonnummer" name="phoneNumber" required>
              <br></div>
              <div>
                    <label><b style="font-family:Verdana;font-size:14px;color:#11165a;">Passord</b></label><br>
                    <input type="password" placeholder="Skriv inn passord" name="password" required>
              </div><br>

             <div style="text-align: center">


             <button type="submit" style="float:none; background-color:#ffb300;border-color:#f2ad0c; color:#11165a">Logg inn</button><br><br>
             </div>

         </div>
        <small style="color: red" class="text-center text-danger"><span id="errorMessage">${param.errorMessage}</span></small>
   </form>
</div>
</body>
</html>