package main;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class list_Files {

	static SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // for the compare fonction the format need to be like that
	final ArrayList<String> allFiles = new ArrayList<String>();
	
	public void list(String path, int clients_id) throws SQLException
	{
		File root = new File(path); // creation of the root
		File[] list = root.listFiles();
		if (root.list().length>0)
		{
			for ( File f : list ) 
			{
				if ( f.isDirectory() ) 
				{
					//System.out.println( "Dir:" + f.getAbsoluteFile() ); // don't care for the moment, it's the directories
				}
				else  // display the file with: name, size, and date (for complete path f.getAbsoluteFile() )
				{
					if(!f.isHidden()) // if the file isn't hidden we show it
					{
						System.out.println("File : " +f.getName() + " " + f.length() +" " + date_format.format(f.lastModified()));	
						String name = f.getName();
						String date_update = date_format.format(f.lastModified());
						String size = String.valueOf(f.length());

						// call of fonction for each occurency
						file_To_DB.insert(name, date_update, size, clients_id); // comptare the file with the DB and upload/Download if necessary
						allFiles.add(f.getName()); // add to the list all files which is already into the folder
					}
					else // if the folder contain hidden files
					{
						// TODO put the same else than bellow (empty folder)
					}
					
				}
			}
			// TODO Stop the sync bar
			sync_Folder(clients_id);
		}
		else // if empty folder
		{
			System.out.println("Empty folder");
			sync_Folder(clients_id);
			
		}
		  
	}
	
	
	public void sync_Folder(int clients_id)
	{
		try
		{
			
		
		db connexion = new db(); // DB connection
		db.db(); // init the connection
		Statement state;
		Statement state1;
		Statement state2;
		state = connexion.getState(); // init connection
		state1 = connexion.getState(); // init connection 1
		state2 = connexion.getState(); // init connection 2
		
		// count name's occurrence and put in variable

		// list the group's files
		// TODO need pierre to do a inner join
		ResultSet result1= state.executeQuery("SELECT * FROM links WHERE id_clients ='"+clients_id+"'");
		while (result1.next()) // get all group of the client
		{
			int groups_id = result1.getInt("id_groups");
			
			ResultSet result2= state1.executeQuery("SELECT * FROM files WHERE id_groups ='"+groups_id+"'"); // for each group we DL the files corresponding
			
			
			while (result2.next()) // for each group of the client we DL the files
			{
				String name = result2.getString("name"); 	// name of the files
				int id_file = result2.getInt("id_clients");	// id client of the file owner 
				System.out.println("Link: "+name+"\nid_clients: "+id_file); // DEBUG
				if(!allFiles.contains(name)) // if the folder doesn't contain the file
				{
				download.file(name, id_file);
				}
				
			}
		}
		
		
		// list only the files of the client
		ResultSet result= state2.executeQuery("SELECT * FROM files WHERE id_clients ='"+clients_id+"'"); 
		
		while(result.next())
		{
			String name = result.getString("name");
			System.out.println(name); // DEBUG
			if(!allFiles.contains(name)) // if the folder doesn't contain the file
			{
			download.file(name, clients_id);
			}
		}
		}
		
		catch(Exception e)
		{
			System.out.println("Error into list_Files class trying to download all files because of empty folder\n"+e);
			error.to_DB(clients_id, e);
		}
	}
	
	
	// for the unitest
	public static void test(String path, int IDClient) throws SQLException
	{
		File root = new File(path); // creation of the root
		File[] list = root.listFiles();
		for ( File f : list ) {
			if ( f.isDirectory() ) 
			{
				//System.out.println( "Dir:" + f.getAbsoluteFile() ); // don't care for the moment, it's the directories
			}
			else  // display the file with: name, size, and date (for complete path f.getAbsoluteFile() )
			{
				if(!f.isHidden()) // if the file isn't hidden we show it
				{
					//System.out.println("File : " +f.getName() + " " + f.length() +" " + date_format.format(f.lastModified()));	
					String name = f.getName();
					String date_update = date_format.format(f.lastModified());
					String size = String.valueOf(f.length());

					// call of fonction for each occurency
					//file_To_DB.insert(name, date_update, size, IDClient); // comment because it's another Unit test
				}
			}
		}	    
	}

}
