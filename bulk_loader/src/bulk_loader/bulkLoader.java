package bulk_loader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class bulkLoader {
	
	
	public String getSQLCommand(int numberOfInserts) {
		
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		String user = "'user_teste'";
		int op = 1;
		String table = "'medicoes'";
		String field = "'id_medicao'";
		int migrated=0;
		
		String sqlCommand = "INSERT INTO `log`(`Utilizador`, `DataHora`, `Operacao`, `Tabela`, `Campo`, `ValorAntigo`, `ValorNovo`, `Migrado`) VALUES ";
		
		for (int i=0; i<numberOfInserts; i++) {
			sqlCommand+="(" + user + "," + "'" + timestamp + "'" + "," + op + "," + table + "," + field + "," + "'" + i + "'" + "," + "'" + i + "'" + "," + migrated + ")" ;
			if (i%100==0 && i!=0) {
				sqlCommand+=";\n";
				System.out.println(sqlCommand);			
				
		        try {
		        	PrintWriter pw = new PrintWriter(new FileOutputStream(
		        		    new File("C:/Users/jonic/OneDrive/Documentos/sql/comando.sql"), 
		        		    true /* append = true */)); 
		        	
		            pw.append(sqlCommand);
		            sqlCommand = "INSERT INTO `log`(`Utilizador`, `DataHora`, `Operacao`, `Tabela`, `Campo`, `ValorAntigo`, `ValorNovo`, `Migrado`) VALUES ";
		            pw.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
			else {
				sqlCommand+=",";
			}
		}
		return null;
		
	}
	
	public static void main(String[] args){
		bulkLoader bl = new bulkLoader();
		bl.getSQLCommand(1000001);
	}
}
