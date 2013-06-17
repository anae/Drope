package main;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.MessageDigest;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class sign extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; // id default for serial version

	/**
	 * @param args
	 * @throws SQLException 
	 * This class allow a new user to create a new account
	 */
	public sign() throws SQLException{

	}
	public static void sign_In_DB(String name, String first_name, String mail, String password ) throws SQLException
	{
		try{


			db connexion = new db(); // DB connection
			db.db();
			Statement state;
			state =connexion.getState(); // init connection

			MessageDigest md = MessageDigest.getInstance("SHA-256"); // encryption of password using SHA-256
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest(); // password is crypted

			StringBuffer sb = new StringBuffer(); // convert to HEX format
			for (int i = 0; i < digest.length; i++) 
			{
				sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
			}

			String password_crypt = sb.toString();

			// if the mail or password is already in the db the count will return true 
			ResultSet count = state.executeQuery("SELECT * FROM clients WHERE mail='"+mail+"' and password='"+password_crypt+"'"); 
			//count.beforeFirst();// put the  cursor in the beginning of the table to be sure to see all occurrency

			if (count.next() == true )
			{
				System.out.println("Your mail is already in the Data Base");
				events.newEvent("Warning","Your mail is already in the Data Base",2);

			}
			else
			{
				state.execute("INSERT INTO clients (name,first_name,mail,password,level) VALUES('"+name+"','"+first_name+"','"+mail+"', '"+password_crypt+"','0')"); 
				System.out .println("Your submissions is under review, as soon you will received a mail"); 
				sign frame = new sign();
				frame.dispose();
				//sign.sign_window();
				events.newEvent("Info","Wait few minutes for using Drope",2);
				//return "Your submissions is under review, as soon you will received a mail";
			}
			state.close();
			connexion.closeConnection();

		}
		catch (Exception e) {
			System.out.println("Connection fail, double check your credentials");
			System.out.println("Error message: \n"+e);
			error.to_DB(0, e);
		}

	}

	public static void test(String name, String first_name, String mail, String password ) throws SQLException
	{
		try{

			db connexion = new db(); // DB connection
			db.db();
			Statement state;
			state =connexion.getState(); // init connection

			MessageDigest md = MessageDigest.getInstance("SHA-256"); // encryption of password using SHA-256
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest(); // password is crypted

			StringBuffer sb = new StringBuffer(); // convert to HEX format
			for (int i = 0; i < digest.length; i++) 
			{
				sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
			}

			String password_crypt = sb.toString();

			// if the mail or password is already in the db the count will return true 
			ResultSet count = state.executeQuery("SELECT * FROM clients WHERE mail='"+mail+"' and password='"+password_crypt+"'"); 

//			ResultSet count = connexion.sqlRequest("SELECT * FROM clientsTest WHERE mail='"+mail+"' and password='"+password_crypt+"'"); 
			
//			count.beforeFirst();// put the  cursor in the beginning of the table to be sure to see all occurrency

			if (count.next() == true )
			{
				//System.out.println("Your mail is already in the Data Base");
				events.newEvent("Warning","Your mail is already in the Data Base",2);
				System.out.println("shouldCreateANewUser: ok");
			}
			else
			{
				state.execute("INSERT INTO clients (name,first_name,mail,password,level) VALUES('"+name+"','"+first_name+"','"+mail+"', '"+password_crypt+"','1000')"); 
				state.execute("DELETE FROM clients WHERE level = 1000"); // delete the test client
				System.out .println("Your submissions is under review, as soon you will received a mail"); 
				//sign.sign_window();
				events.newEvent("Info","Wait few minutes for using Drope",1);
				//return "Your submissions is under review, as soon you will received a mail";
			}
			state.close();
			connexion.closeConnection();
			System.out.println("shouldCreateANewUser: ok\n");
		}
		catch (Exception e) 
		{
			System.out.println("shouldCreateANewUser: not ok\n"+e);
		}

	}

	
	
	public void sign_window() throws SQLException
	{
		//---------------- Form --------------------//

		JPanel contentPane;
		final JTextField textName;
		final JTextField textFirstName;
		final JTextField textEMail;
		final JTextField textPassword;
		final JTextField textPassword2;

		final sign frame = new sign();
		frame.setBounds(100, 100, 395, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(17, 33, 61, 16);
		contentPane.add(lblName);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(17, 61, 89, 16);
		contentPane.add(lblFirstName);

		JLabel lblEMail = new JLabel("E-Mail");
		lblEMail.setBounds(17, 89, 61, 16);
		contentPane.add(lblEMail);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(17, 117, 61, 16);
		contentPane.add(lblPassword);

		JLabel lblPassword2 = new JLabel("Retype password");
		lblPassword2.setBounds(17, 145, 115, 16);
		contentPane.add(lblPassword2);

		textName = new JTextField();
		textName.setBounds(133, 27, 134, 28);
		contentPane.add(textName);
		textName.setColumns(10);

		textFirstName = new JTextField();
		textFirstName.setBounds(133, 55, 134, 28);
		contentPane.add(textFirstName);
		textFirstName.setColumns(10);

		textEMail = new JTextField();
		textEMail.setBounds(133, 83, 134, 28);
		contentPane.add(textEMail);
		textEMail.setColumns(10);

		textPassword = new JPasswordField();
		textPassword.setBounds(133, 111, 134, 28);
		contentPane.add(textPassword);
		textPassword.setColumns(10);

		textPassword2 = new JPasswordField();
		textPassword2.setBounds(133, 139, 134, 28);
		contentPane.add(textPassword2);
		textPassword2.setColumns(10);

		JButton btnSignIn = new JButton("Sign in");
		JButton btnConnect = new JButton("Connect");
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		final JLabel labelStatement = new JLabel("");
		labelStatement.setBounds(17, 173, 161, 16);
		labelStatement.setForeground(Color.RED);
		contentPane.add(labelStatement);
		btnSignIn.setBounds(272, 28, 117, 29);
		btnConnect.setBounds(272, 55, 117, 29);
		contentPane.add(btnSignIn);
		contentPane.add(btnConnect);
		
		btnConnect.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				authentification frameAuth = new authentification();
				frameAuth.setVisible(true);
				frame.setVisible(false);
				
			}
		});

		
		
		btnSignIn.addActionListener(new ActionListener() {
			// on button click
			public void actionPerformed(ActionEvent arg0) 
			{
				// set default color onclick
				textEMail.setBackground(Color.white);
				textFirstName.setBackground(Color.white);
				textPassword.setBackground(Color.white);
				textPassword2.setBackground(Color.white);
				textName.setBackground(Color.white);
				try 
				{
					// check if all of the field are filled
					switch(check_if_filled(textName, textFirstName, textEMail, textPassword, textPassword2))
					{
					case 1: // everything is filled
						sign_In_DB(textName.getText(), textFirstName.getText(), textEMail.getText(), textPassword.getText());
						break;
					case 2:
						labelStatement.setText("Password no matched");
						textPassword.setBackground(Color.RED);  // change the color of the incorrect field
						textPassword2.setBackground(Color.RED); // change the color of the incorrect field
						break;
					case 3:
						labelStatement.setText("Enter a password");
						textPassword.setBackground(Color.RED);  // change the color of the incorrect field
						textPassword2.setBackground(Color.RED); // change the color of the incorrect field
						break;
					case 4:
						labelStatement.setText("Retype your mail");
						textEMail.setBackground(Color.RED);     // change the color of the incorrect field
						break;
					case 5:
						labelStatement.setText("Enter your first_name");
						textFirstName.setBackground(Color.RED);  // change the color of the incorrect field
						break;
					case 6:
						labelStatement.setText("Enter your name");
						textName.setBackground(Color.RED);       // change the color of the incorrect field
						textName.setFocusable(true);
						break;
					default:
						labelStatement.setText("Error try again");
						break;
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}

			}});


	}

	public static int check_if_filled(JTextField name, JTextField first_name, JTextField mail, JTextField password, JTextField password2)
	{
		// regex for mail 

		Pattern pattern = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$"); 

		// if name not empty
		if(name.getText().isEmpty() == false)
		{
			// if first_name not empty
			if(first_name.getText().isEmpty()  == false)
			{
				// if correct mail
				if(pattern.matcher(mail.getText()).matches() == true) // return true if mail ok
				{
					// if password not empty
					if(password.getText().isEmpty() == false || password2.getText().isEmpty() == false)
					{
						// if password matched
						if(password.getText().equalsIgnoreCase(password2.getText()))
						{
							System.out.println("Every field is filled");
							return 1;
						}
						else
						{
							System.out.println("Password no matched");
							return 2;
						}
					}
					else
					{
						System.out.println("Enter a password");
						return 3;
					}
				}
				else
				{
					System.out.println("Retype your mail");
					return 4;
				}
			}
			else
			{
				System.out.println("Enter your first_name");
				return 5;
			}
		}
		else
		{
			System.out.println("enter your name");
			return 6;
		}
	}


}

