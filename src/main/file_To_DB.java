package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class file_To_DB {

	/**
	 * @param args
	 * Each file if its the last version will be writ into the DB
	 * @throws SQLException 
	 */
	public static void insert(String name, String date_update, String size) throws SQLException{

		try{
			db connexion = new db(); // DB connection
			Statement state;
			state = connexion.getState(); // init connection

			// count name's occurency qnd put in variable
			ResultSet count = state.executeQuery("SELECT * FROM files_local WHERE name ='"+name+"'"); 
			// to be sure to see all occurency
			count.beforeFirst(); 


			// Check if this file is already in the DB
			if(count.next()==true)
			{
				ResultSet result = state.executeQuery("SELECT * FROM files_local WHERE name='"+name+"'");
				result.beforeFirst(); // change position to before the first item of the DB
				result.next(); // I go through the till the occurency 
				String dateDB = result.getString("date_update");

				if (dateDB.compareTo(date_update ) == -1) { 
					// if dateDB < date_update 
					System.out.println("dateDB < date_update : DB qnd FTP need to be updated");
					state.execute("UPDATE  `files_local` SET  `date_update` =  '"+date_update+"' WHERE `files_local`.`name` ='"+name+"'"); 
					// #TODO upload file to FTP
					System.out.println(name+" is now updated on the DB!");

				} 

				if (dateDB.compareTo(date_update ) == 0) { 

					// if dateDB = date_update
					System.out.println("Same: everything is fine");
				} 

				if (dateDB.compareTo(date_update ) == 1) { 

					// if dateDB > date_update
					System.out.println("DB plus grand: need to be upload from the ftp");
					download.file(name); // download the file who's missing
				} 
				System.out.println("date DB......"+dateDB);
				System.out.println("date local..."+date_update);

			}
			// else we wrote this in the DB 
			//#TODO add date_create is available, id_clients and file type 
			else
			{
				state.execute("INSERT INTO files_local (name,date_update,size) VALUES('"+name+"','"+date_update+"','"+size+"')"); 
				System.out.println(name+" was correclty write in the db!");
			}




			state.close();
			connexion.closeConnection();
		}
		catch(Exception e)
		{
			System.out.println();
		}
	}

}
