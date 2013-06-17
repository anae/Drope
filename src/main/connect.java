package main;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class connect {

	public int connection(String mail, String password_uncrypted) throws SQLException
	{
		try
		{
			db connexion = new db(); // DB connection
			db.db();
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


			// count name's occurrences and put it in variable
//			ResultSet count = state.executeQuery("SELECT * FROM clientsTest WHERE mail='"+mail+"' and Password= '"+password+"'"); // for debug
			ResultSet count = state.executeQuery("SELECT * FROM clients WHERE mail='"+mail+"' and Password= '"+password+"'");
			//count.beforeFirst();// put the  cursor in the beginning of the table to be sure to see all occurrences

			if(count.next()== true) // the user is on the DB
			{
				
				main_menu frame = new main_menu(getId(mail), getFirstName(mail));
				frame.setVisible(true);
//				System.out.println(getId(mail)); 			// DEBUG
//				System.out.println(getFirstName(mail));		// DEBUG
				
				return 1;
			}
			if(mail.length()<2||password_uncrypted.length()<2) // nothing in the char
			{
				System.out.println("moins de 3!!!!!");
				return 3;
			}
			else // the user enter a wrong mail or password 
			{
				System.out.println("Your credentials didn't match");
				return 2;
			}
		}
		catch(Exception e)
		{
			System.out.println(e); 
			error.to_DB(0, e);
		}
		return 0; // if error

	}
	
	public int test(String mail, String password_uncrypted) throws SQLException
	{
		try
		{
			db connexion = new db(); // DB connection
			db.db();
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


			// count name's occurrences and put it in variable
//			ResultSet count = state.executeQuery("SELECT * FROM clientsTest WHERE mail='"+mail+"' and Password= '"+password+"'"); // for debug
			ResultSet count = state.executeQuery("SELECT * FROM clients WHERE mail='"+mail+"' and Password= '"+password+"'");
			//count.beforeFirst();// put the  cursor in the beginning of the table to be sure to see all occurrences

			if(count.next()== true) // the user is on the DB
			{
				//new_window.setVisible(true);
				System.out.println("shouldConnectAClient: ok");
				return 1;
			}
			if(mail.length()<2||password_uncrypted.length()<2) // nothing in the char
			{
				System.out.println("moins de 3!!!!!");
				return 3;
			}
			else // the user enter a wrong mail or password 
			{
//				System.out.println("Your credentials didn't match");
				System.out.println("shouldConnectAClient: not ok");
				return 2;
			}
		}
		catch(Exception e)
		{
			System.out.println("shouldConnectAClient: not ok"+e);
		}
		return 0; // if error

	}
	
	public static int getId(String mail) // get the ID of the user
	{
		db connexion = new db(); // DB connection
		db.db();
		Statement state;
		state = connexion.getState(); // init connection

		try 
		{
			ResultSet results = state.executeQuery("SELECT * FROM clients WHERE mail='"+mail+"'");
			results.next();
			int id = results.getInt("id");
			return id;
			
		} 
		catch (SQLException e) 
		{
			System.out.println("Error getId() in connect class\n"+e);
			error.to_DB(0, e);
			return 0;
		}
		
		
		
	}
	
	public static String getFirstName(String mail) // get the first name of the user
	{
		db connexion = new db(); // DB connection
		db.db();
		Statement state;
		state = connexion.getState(); // init connection

		try 
		{
			ResultSet results = state.executeQuery("SELECT * FROM clients WHERE mail='"+mail+"'");
			String name ="";
			while(results.next())
			{
				name = results.getString("first_name");
			}
			return name;
			
		} 
		catch (SQLException e) 
		{
			System.out.println("Error getFirstName() in connect class\n"+e);
			error.to_DB(0, e);
			return null;
		}
		
		
		
	}


}
