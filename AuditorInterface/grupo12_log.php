<!DOCTYPE html>
<html>
<head>
<style>
{
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #ddd;
  padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2;}

tr:hover {background-color: #ddd;}

th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4CAF50;
  color: white;
}
</style>
</head>
<body>



<?php
   include('session.php');

   include('connection.php');

   $result = @mysqli_query($conn, 'select * from grupo12.log');
        
  if (!$result){
      echo "Nao resultou";
  }

  echo "
      <table class='table'>
      <tr>
      <th>IdLog</th>
      <th>Operacao</th>
      <th>Tabela</th>
      <th>Utilizador</th>
      <th>DataHora</th>
      <th>Campo</th>
      <th>ValorAntigo</th>
      <th>ValorNovo</th>
      </tr>";

      while ($row = $result->fetch_assoc()) {
         echo ' <tr>
         <td>'.$row["IdLog"].'</td>
         <td>'.$row["Operacao"].'</td>
         <td>'.$row["Tabela"].'</td>
         <td>'.$row["Utilizador"].'</td>
         <td>'.$row["DataHora"].'</td>
         <td>'.$row["Campo"].'</td>
         <td>'.$row["ValorAntigo"].'</td>
         <td>'.$row["ValorNovo"].'</td>
         </tr>';
      }  
   echo "</table>";
?>