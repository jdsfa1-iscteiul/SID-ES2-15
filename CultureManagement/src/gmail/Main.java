package gmail;

public class Main {

	public static void main(String[] args) {
		
		// Email destiny
		String emailTo = "investigadorsides@gmail.com";
		// Email Subject
		String subject = "Alerta - cultura";
		// Email text
		String text = "Cultura em risco (limite ultrapassado).";
		// Email Regards
		String regards = "Melhores cumprimentos";
		// Email signature
		String signature = "O Grupo15"; 
		
		GmailAccount account = new GmailAccount("investigadorSIDES@gmail.com","123grupo15");
		
		BDAGmailClient client = new BDAGmailClient(account);
		
		client.reply(emailTo, subject, text, regards, signature);
	}

}
