<?php
   
   session_start();

   $error='';
   
   if($_SERVER["REQUEST_METHOD"] == "POST") {
      // username and password sent from form 
             // Create connection
            $conn = @mysqli_connect($_POST['server'], $_POST['username'], $_POST['password'], $_POST['database']);

            $myusername = @mysqli_real_escape_string($conn,$_POST['username']);
            $mypassword = @mysqli_real_escape_string($conn,$_POST['password']);

            // Check connection
            if (!$conn) { 
               $error="wrong credentials";
            }
            else {
                $_SESSION['login_user']=$myusername;
                $_SESSION['dbserver']=$_POST['server'];
                $_SESSION['username']=$_POST['username'];
                $_SESSION['password']=$_POST['password'];
                $_SESSION['database']=$_POST['database'];

                if ($_POST['database']=='grupo15_log'){
                     header("location: welcome_grupo15.php");    
                }
                if ($_POST['database']=='grupo12_log'){
                     header("location: welcome_grupo12.php");
                }
            }
   }        
?>
<html>
   
   <head>
      <title>Login Page</title>
      
      <style type = "text/css">
         body {
            font-family:Arial, Helvetica, sans-serif;
            font-size:14px;
         }
         label {
            font-weight:bold;
            width:100px;
            font-size:14px;
         }
         .box {
            border:#666666 solid 1px;
         }
      </style>
      
   </head>
   
   <body bgcolor = "#FFFFFF">
   
      <div align = "center">
         <div style = "width:300px; border: solid 1px #333333; " align = "left">
            <div style = "background-color:#333333; color:#FFFFFF; padding:3px;"><b>Login</b></div>
            
            <div style = "margin:30px">
               
               <form action = "" method = "post">
                  <label>UserName  :</label><input type = "text" name = "username" class = "box"/><br /><br />
                  <label>Password  :</label><input type = "password" name = "password" class = "box" /><br/><br />
                  <label>Database  :</label><input type = "database" name = "database" class = "box" /><br/><br />
                  <label>Server  :</label><input type = "database" name = "server" class = "box" /><br/><br />

                  <input type = "submit" value = " Submit "/><br />
               </form>
               
               <div style = "font-size:11px; color:#cc0000; margin-top:10px"><?php echo $error; ?></div>
               
            </div>
            
         </div>
         
      </div>

   </body>
</html>