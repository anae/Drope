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
	public static void insert(String name, String date_update, String size, int id_clients) throws SQLException{

		try{
			db connexion = new db(); // DB connection
			db.db(); // init the connection
			Statement state;
			state = connexion.getState(); // init connection

			// count name's occurrence and put in variable
			ResultSet count = state.executeQuery("SELECT * FROM files WHERE name ='"+name+"' AND id_clients='"+id_clients+"'"); 


			// Check if this file is already in the DB
			if(count.next()==true) // if the file is already in the db
			{
				ResultSet result = state.executeQuery("SELECT * FROM files WHERE name='"+name+"' AND id_clients='"+id_clients+"'");
				//result.beforeFirst(); // change position to before the first item of the DB
				result.next(); // I go through the DB till the occurrence 
				String dateDB = result.getString("date_update"); // put the date_update into dateDB for comparison 

				if (dateDB.compareTo(date_update ) == -1) // if the local file if more updated than the DB
				{ 
					// if dateDB < date_update 
					System.out.println("dateDB < date_update : DB and FTP need to be updated");
					upload.file(name, id_clients);
					state.execute("UPDATE  files SET  date_update =  '"+date_update+"' WHERE name ='"+name+"' AND id_clients='"+id_clients+"'"); 

					//System.out.println(name); // DEBUG
					//System.out.println(id_clients); // DEBUG
					System.out.println(name+" is now updated on the DB!");
					events.newEvent("Updated",name+" is now updated on the DB!" , 1);

				} 

				if (dateDB.compareTo(date_update ) == 0) // Folder up to date
				{ 

					// if dateDB = date_update
					System.out.println("Folder up to date");

				} 

				if (dateDB.compareTo(date_update ) == 1) // The folder into the FTP is more updated than the local one
				{ 

					// if dateDB > date_update
					System.out.println("Need to download the file from the DB");
					download.file(name, id_clients); // download the file who's missing
				} 
				//System.out.println("date DB......"+dateDB); // for debug
				//System.out.println("date local..."+date_update); // for debug

			}
			// else we wrote this in the DB 
			//#TODO add date_create is available and file type 
			else
			{
				state.execute("INSERT INTO files (name,date_update,size,id_clients) VALUES('"+name+"','"+date_update+"','"+size+"','"+id_clients+"')"); 
				System.out.println(name+" was correclty write in the db!");
				upload.file(name, id_clients);
			}

			state.close();
			connexion.closeConnection();
		}
		catch(Exception e)
		{
			System.out.println();
			error.to_DB(id_clients, e);
		}
	}





	// unit test
	public static void test(String name, String date_update, String size, int IDClient) throws SQLException{

		try
		{
			db connexion = new db(); // DB connection
			db.db();
			Statement state;
			state = connexion.getState(); // init connection

			// count name's occurrence and put in variable
			ResultSet count = state.executeQuery("SELECT * FROM files WHERE name ='"+name+"' AND id_clients='"+IDClient+"'"); 
			// to be sure to see all occurrence
			//count.beforeFirst(); 


			// Check if this file is already in the DB
			if(count.next()==true)
			{
				ResultSet result = state.executeQuery("SELECT * FROM files WHERE name='"+name+"' AND id_clients='"+IDClient+"'");
				result.beforeFirst(); // change position to before the first item of the DB
				result.next(); // I go through the DB till the occurrence 
				String dateDB = result.getString("date_update"); // put the date_update into dateDB for comparison 

				if (dateDB.compareTo(date_update ) == -1) { 
					// if dateDB < date_update 
					//System.out.println("dateDB < date_update : DB qnd FTP need to be updated");

					state.execute("UPDATE  `files` SET  `date_update` =  '"+date_update+"' WHERE `files`.`name` ='"+name+"' AND id_clients='"+IDClient+"'"); 

					// #TODO upload file to FTP
					//System.out.println(name+" is now updated on the DB!");
					events.newEvent("Updated",name+" is now updated on the DB!" , 1);

				} 

				if (dateDB.compareTo(date_update ) == 0) { 

					// if dateDB = date_update
					//System.out.println("Same: everything is fine");
				} 

				if (dateDB.compareTo(date_update ) == 1) { 
					int clients_id = 0;
					// if dateDB > date_update
					//System.out.println("DB plus grand: need to be upload from the ftp");
					download.file(name, clients_id); // download the file who's missing
				} 
				//System.out.println("date DB......"+dateDB); // for debug
				//System.out.println("date local..."+date_update); // for debug

			}
			// else we wrote this in the DB 
			//#TODO add date_create is available and file type 
			else
			{
				state.execute("INSERT INTO files (name,date_update,size,id_clients) VALUES('"+name+"','"+date_update+"','"+size+"','"+IDClient+"')"); 
				//System.out.println(name+" was correclty write in the db!");
			}
			state.execute("DELETE FROM files WHERE size = 01"); //delete the testfile after test

			//			DELETE FROM files_local WHERE date_update = 2050/50/50 17:01:08 //#TODO delete the testfile after test
			state.close();
			connexion.closeConnection();
			System.out.println("shouldInsertAFileInFiles: ok");
		}
		catch(Exception e)
		{
			System.out.println("shouldInsertAFileInFiles: not ok \n"+e);
		}
	}

}
