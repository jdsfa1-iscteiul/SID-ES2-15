<?php

	//prolongar tempo de execução do scriptt
	set_time_limit(3600);

	//variáveis de conexão às DBs
	$url="127.0.0.1";
	$dbOrigem="grupo15_main";
	$dbAuditor="grupo15_log";
	$user="root";
	$password="";
	$numeroTentativasConexao = 5;

	//variáveis de apoio à migração
	$last_log_id = 0;
	$last_query_log_id = 0;

	//estabelecimento de ligações
	$connDbOrigem = mysqli_connect($url, $user, $password, $dbOrigem);
	$connDbAuditor = mysqli_connect($url, $user, $password, $dbAuditor);

	//se conexão falhar com BD Auditor tenta restabelecer
	while (!$connDbAuditor  && numeroTentativasConexao > 0) {
		echo "Conexão à base de dados do auditor falhada, a tentar restabeler dentro de 5min...";
		sleep(300);
		$connDbOrigem = mysqli_connect($url, $user, $password, $dbAuditor);
		$numeroTentativasConexao -= 1;
	}

	//se não tiver sido possível estabelecer conexão, indica o erro e termina a execução do script
	if (!$connDbAuditor){
		die ("Conexão à base de dados de origem falhou: ".$connDbOrigem->connect_error);
	}

	$numeroTentativasConexao = 5;

	//consulta do último log migrado
	$sql = "SELECT MAX(log_id) from log";
	$result = mysqli_query($connDbOrigem, $sql);
	if ($result) {
		$row = mysqli_fetch_array($result, 'MYSQLI_ASSOC');
		$last_log_id = $row['log_id'];
		}	
	}

	//consulta do último query_log migrado
	$sql = "SELECT MAX(log_id) from query_log";
	$result = mysqli_query($connDbOrigem, $sql);
	if ($result) {
		$row = mysqli_fetch_array($result, 'MYSQLI_ASSOC');
		$last_query_log_id = $row['log_id'];
		}	
	}



	//se conexão falhar com BD origem tenta restabelecer
	while (!$connDbOrigem  && numeroTentativasConexao > 0) {
		echo "Conexão à base de dados de origem falhada, a tentar restabeler dentro de 5min...";
		sleep(300);
		$connDbOrigem = mysqli_connect($url, $user, $password, $dbOrigem);
		$numeroTentativasConexao -= 1;
	}

	//se não tiver sido possível estabelecer conexão, indica o erro
	if (!$connDbOrigem){
		$numeroTentativasConexao = 5;
		die ("Conexão à base de dados de origem falhou: ".$connDbOrigem->connect_error);
	}

	$selectLogStartingTime = microtime(true);
	
	$sql = "Select * from log";
	$result = mysqli_query($connDbOrigem, $sql);
	$rows = array();
	if ($result) {
		if (mysqli_num_rows($result)>0){
			while($r=mysqli_fetch_assoc($result)){
				array_push($rows, $r);
			}
		}	
	}
	$selectLogEndTime = microtime(true);
	$selectLogElapsedTime = $selectLogEndTime - $selectLogStartingTime;
	echo $selectLogElapsedTime;
	echo "<br>";
	echo sizeof($rows);
	echo "<br>";


	//se conexão falhar com BD Auditor tenta restabelecer
	while (!$connDbAuditor  && numeroTentativasConexao > 0) {
		sleep(300);
		$connDbAuditor = mysqli_connect($url, $Operacaoname, $password, $dbOrigem);
		$numeroTentativasConexao -= 1;
	}
	if (!$connDbAuditor){
		die ("Connection Failed: ".$connDbAuditor->connect_error);
	}

	$insertIntoLogStartTime = microtime(true);

	$sqlInsert = "";
	for($i = 0; $i < sizeof($rows); $i = $i + 1) {
			$ValorAntigo = !empty($rows[$i]['old_value']) ? "'" . $rows[$i]['old_value'] . "'" : "NULL";
			$ValorNovo = !empty($rows[$i]['new_value']) ? "'" . $rows[$i]['new_value'] . "'" : "NULL";
			$sqlInsert .= '(' . $rows[$i]['log_id'] . ',' . "'" . $rows[$i]['edited_table'] . "'" . ',' . $rows[$i]['operation'] . ',' . "'" . $rows[$i]['user'] . "'" . ',' . "'" . $rows[$i]['timestamp'] . "'" . ',' . "'" . $rows[$i]['field'] . "'" . ',' . $ValorAntigo . ',' . $ValorNovo . ")";

		
			if( ($i+1)%1000 == 0 || $i == sizeof($rows) -1 ) {

				$callProcedure = mysqli_prepare($connDbAuditor, "CALL insert_data_into_log(?);");
				mysqli_stmt_bind_param($callProcedure, 's', $sqlInsert);
				$res = mysqli_stmt_execute($callProcedure);
				
				if(!$res){
					echo $i;
					$result = new stdClass();
					$result->status = false;
					$result->msg = mysqli_error($connDbAuditor);
					echo json_encode($result);
					exit;
				}
				$sqlInsert = "";
			}
			else {
				$sqlInsert = $sqlInsert . ',';
			}
	}
	$endtime = microtime(true);
	$timediff = $endtime - $insertIntoLogStartTime;
	echo "<br>";
	echo $timediff;

?>