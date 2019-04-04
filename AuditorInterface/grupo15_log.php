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

   $result = @mysqli_query($conn, 'select * from grupo15_log.log');
        
  if (!$result){
      echo "Nao resultou";
  }

  echo "
      <table class='table'>
      <tr>
      <th>log_id</th>
      <th>edited_table</th>
      <th>operation</th>
      <th>user</th>
      <th>timestamp</th>
      <th>field</th>
      <th>old_value</th>
      <th>new_value</th>
      </tr>";

      while ($row = $result->fetch_assoc()) {
         echo ' <tr>
         <td>'.$row["op_id"].'</td>
         <td>'.$row["edited_table"].'</td>
         <td>'.$row["operation"].'</td>
         <td>'.$row["user"].'</td>
         <td>'.$row["timestamp"].'</td>
         <td>'.$row["field"].'</td>
         <td>'.$row["old_value"].'</td>
         <td>'.$row["new_value"].'</td>
         </tr>';
      }  
   echo "</table>";
?>