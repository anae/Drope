package main;
//import java.io.FileOutputStream;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class download {
	// #TODO use window.form to populated server userName and password
	public static String server= "www.ftp.com";
	public static String userName= "user";
	public static String password = "password";

	public static void file(String fileName) throws Exception
	{
		try{
			// Connection to the ftp
			URL url = new URL("ftp://"+userName+":"+password+"@"+server+"/"+fileName+";type=i");
			URLConnection con = url.openConnection();
			BufferedInputStream in = new BufferedInputStream(con.getInputStream());

			//Downloads the selected file to the /Users/elliot/Desktop/
			FileOutputStream out = new FileOutputStream("/Users/elliot/Desktop/" + fileName);

			int i = 0;
			byte[] bytesIn = new byte[1024];
			while ((i = in.read(bytesIn)) >= 0) {
				out.write(bytesIn, 0, i);
			}
			out.close(); // close the fileOutputStream
			in.close();  //  
			// Write the succes message
			System.out.println(fileName+" downloaded successfully.");
		}
		catch(Exception e) // #TODO write the exeption to the DB
		{
			System.out.println(e);
		}
	}


}