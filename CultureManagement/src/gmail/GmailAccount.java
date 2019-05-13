package gmail;

/**
 * This class represents a email account.
 * 
 * @author grupo15
 *
 */

public class GmailAccount {

	private String email;
	private String password;
	
	/**
	 * Contrutor da classe. Instancia os atributos com os argumentos.
	 * 
	 * @param email e-mail do user
	 * @param password password do user
	 */
	public GmailAccount(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/**
	 * Getter para o email do user
	 * 
	 * @return email em formato String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Getter para a password do user
	 * 
	 * @return password em formato String
	 */
	public String getPassword() {
		return password;
	}
	
	
}
