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
      <th>log_id</th>
      <th>user</th>
      <th>query_timestamp</th>
      <th>query_id</th>
      <th>query_row</th>
      <th>culture</th>
      <th>variable</th>
      <th>value</th>
      <th>timestamp</th>

      </tr>";

      while ($row = $result->fetch_assoc()) {
         echo ' <tr>
         <td>'.$row["log_id"].'</td>
         <td>'.$row["user"].'</td>
         <td>'.$row["query_timestamp"].'</td>
         <td>'.$row["query_id"].'</td>
         <td>'.$row["query_row"].'</td>
         <td>'.$row["culture"].'</td>
         <td>'.$row["variable"].'</td>
         <td>'.$row["value"].'</td>
         <td>'.$row["timestamp"].'</td>
         </tr>';
      }  
   echo "</table>";
?>