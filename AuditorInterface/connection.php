<?php
   
   $conn = @mysqli_connect($_SESSION['dbserver'],$_SESSION['username'],$_SESSION['password'],$_SESSION['database']);

    if (!$conn) { 
        $error="Erro a ligar à base de dados";
        echo $error;
    }
?>    