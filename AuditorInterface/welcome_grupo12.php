<?php
   include('session.php');
?>
<html">
   
   <head>
      <title>Welcome </title>
   </head>
   
   <body>
   	<center> 
		      <h1>Welcome <?php echo $_SESSION['login_user']; ?></h1> 

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
				
			  <table class="tg">
				  <tr>
				    <th class="tg-0lax">Tabelas de Auditoria</th>
				  </tr>
				  <tr>
				    <td class="tg-0lax"><a href = "grupo12_log.php">log</td>
				  </tr>
				  <tr>
				    <td class="tg-0lax"><a href = "grupo12_log_consulta.php">log_consulta</td>
				  </tr>
			  </table>
		      
		      <h2><a href = "selectCommandPage.php">Make a select sql command</a></h2>

		      <h3><a href = "logout.php">Sign Out</a></h3>
	 </center> 	      
   </body>
   
</html>