package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class upload {
	// #TODO use window.form to populated server userName and password
	public static String server= "www.ftp.com";
	public static String userName= "user";
	public static String password = "password";
	public static int port = 21;

	public static void file(String file)
	{  
		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			File f = new File("/Users/elliot/Desktop/"+file);

			String remote_File = file;
			InputStream inputStream = new FileInputStream(f);

			// Start uploading file
			boolean done = ftpClient.storeFile(remote_File, inputStream);
			inputStream.close();
			if (done) 
			{
				System.out.println(file+" uploaded successfully.");
			}

		} 
		catch (IOException ex) 
		{
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

