<?php
   include('session.php');

   include('connection.php');

   $error='';

   if($_SERVER["REQUEST_METHOD"] == "POST") {

         $SQL_COMMAND = $_POST['command'];
         $result = mysqli_query($conn, $SQL_COMMAND);
         
         if (!$result){
            echo "Nao resultou";
         }

         if (mysqli_num_rows($result) > 0) {
                echo mysqli_num_rows($result);
         } else {
            echo "0 results";
         }
   }      
?>

<html>
   
   <head>
      <title>Select Command Page</title>
      
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
         <div style = "width:500px; border: solid 1px #333333; " align = "left">
            <div style = "background-color:#333333; color:#FFFFFF; padding:3px;"><b>Control Panel</b></div>
            
            <div style = "margin:30px">
               
               <h5>Logged has: <?php echo $_SESSION['login_user']; ?></h5>

               <form action = "" method = "post">
                  <label>Insert your command here:  </label>
                  <textarea rows="4" cols="50" name="command"></textarea>
                  <input type = "submit" value = "execute"/><br />
               </form>
                

               <h3><a href = "logout.php">Sign Out</a></h3>

               
               <div style = "font-size:11px; color:#cc0000; margin-top:10px"><?php echo $error; ?></div>
               
            </div>
            
         </div>
         
      </div>

   </body>
</html>
