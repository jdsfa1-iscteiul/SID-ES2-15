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

   $result = @mysqli_query($conn, 'select * from grupo15_log.queries_log');
        
  if (!$result){
      echo "Nao resultou";
  }

  echo "
      <table class='table'>
      <tr>
      <th>IdLog</th>
      <th>Utilizador</th>
      <th>DataHoraConsulta</th>
      <th>IdConsulta</th>
      <th>IdLinha</th>
      <th>Cultura</th>
      <th>Variavel</th>
      <th>ValorMedicao</th>
      <th>DataHoraMedicao</th>

      </tr>";

      while ($row = $result->fetch_assoc()) {
         echo ' <tr>
         <td>'.$row["IdLog"].'</td>
         <td>'.$row["Utilizador"].'</td>
         <td>'.$row["DataHoraConsulta"].'</td>
         <td>'.$row["IdConsulta"].'</td>
         <td>'.$row["IdLinha"].'</td>
         <td>'.$row["Cultura"].'</td>
         <td>'.$row["Variavel"].'</td>
         <td>'.$row["ValorMedicao"].'</td>
         <td>'.$row["DataHoraMedicao"].'</td>
         </tr>';
      }  
   echo "</table>";
?>