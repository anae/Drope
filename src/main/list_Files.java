package main;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class list_Files {

	static SimpleDateFormat date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // for the compare fonction the format need to be like that

	public static void list(String path) throws SQLException
	{
		File root = new File(path);
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
					System.out.println("File : " +f.getName() + " " + f.length() +" " + date_format.format(f.lastModified()));	
					String name = f.getName();
					String date_update = date_format.format(f.lastModified());
					String size = String.valueOf(f.length());

					// call of fonction for each occurency
					file_To_DB.insert(name, date_update, size);


				}
			}
		}	    
	}
}
