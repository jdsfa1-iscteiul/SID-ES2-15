package gmail;



import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailConnection;
import com.googlecode.gmail4j.http.HttpGmailConnection;
import com.googlecode.gmail4j.rss.RssGmailClient;

import gmail.CredentialTesting;

/**
 * This class is responsible of extrat the emails of Gmail
 * @author grupo15
 *
 */
public class BDAGmailClient{

	private GmailClient client;
	private GmailConnection connection;
	private GmailAccount account;

	/**
	 * Constructor
	 */
	public BDAGmailClient(GmailAccount account) {
		this.account = account;
		client = new RssGmailClient();
		connection = new HttpGmailConnection(CredentialTesting.getInstance().show("Enter Gmail Login", account));
		client.setConnection(connection);
	}

	/**
	 * This method create and send emails
	 */

	public void reply(String to, String subject, String text, String regards, String signature) {
		GmailSender sender = new GmailSender();
		try {
			sender.generateAndSendEmail(to, subject, text, regards, signature, account);
		} catch (AddressException e) {
		} catch (MessagingException e) {
		}

	}
}