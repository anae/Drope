package main;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// # TODO check if the fields are not empty and if the mail is like test@test.com

public class connect {

	public void connection(String mail, String password_uncrypted) throws SQLException
	{
		window new_window = new window(); // #TODO change the window ensure that the window is not displayed
		new_window.setVisible(false);
		try{
		db connexion = new db(); // DB connection
		Statement state;
		state = connexion.getState(); // init connection
		
		MessageDigest md = MessageDigest.getInstance("SHA-256"); // encryption of password using SHA-256
		md.update(password_uncrypted.getBytes("UTF-8"));
		byte[] digest = md.digest(); // password is crypted

		StringBuffer sb = new StringBuffer(); // convert to HEX format
		for (int i = 0; i < digest.length; i++) 
		{
			sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
		}

		String password = sb.toString(); // countain the crypted password
		
		
		// count name's occurency qnd put in variable
		ResultSet count = state.executeQuery("SELECT * FROM clients WHERE mail='"+mail+"' and Password= '"+password+"'");
		count.beforeFirst();// put the  cursor in the beginning of the table to be sure to see all occurrency
		
		if(count.next()== true) // the user is on the DB
		{
			new_window.setVisible(true);
		}
		else // the user enter a wrong mail or password 
		{
			System.out.println("Your credentials didn't match");
		}
		}
		catch(Exception e)
		{
			System.out.println(e); //#TODO put that in a table of the db
		}
			
	}
	
}
