package main;

import java.sql.DriverManager;
import java.sql.Statement;

public class db {

	Statement req ; // constituera l'ensembles des requêtes sql
	java.sql.Connection db;

	public db() 
	{
		try //Test la connexion avec la db si ne fonctionne pas renvoie le type d'erreur
		{
			Class.forName("com.mysql.jdbc.Driver"); //charge le drive qui permettra de se connecter ˆ la db
			String url = "jdbc:mysql://localhost/db"; // l'url de notre db locale xamp
			String user = "user"; //l'user de notre db
			String passwd = "password"; // notre password pour la db
			db = DriverManager.getConnection(url, user, passwd);// connexion via le renseignement fournis au dessus
		}
		catch (Exception e) 
		{
			System.out.print(e);
		}
	}
	public Statement getState() // methode de connexion
	{
		try
		{
			req = db.createStatement(); //initialisation de la connexion
		}

		catch (Exception e) 
		{
			System.out.print(e);
		}

		return req;
	}
	public void closeConnection() //ferme la connexion sql
	{
		try
		{
			req.close();
		}

		catch (Exception e) // si n'arrive pas à fermer la connection il le signal
		{
			System.out.println("Error: "+e);
		}
	}       

}

