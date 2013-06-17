
package main;


import java.awt.Color;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import javax.swing.ImageIcon;

import org.eclipse.swt.widgets.DateTime;
import org.jachievement.Achievement;
import org.jachievement.AchievementConfig;
import org.jachievement.AchievementQueue;

public class events {

	public static void newEvent(String title, String message, int icon) {


		AchievementConfig config = new AchievementConfig();         // config of the interface
		config.setBackgroundColor(Color.white); 			        // config Background Color
		config.setTitleColor(Color.GRAY);       			        // config Title Color
		config.setDescriptionColor(Color.GRAY); 			        // config Description Color
		config.setBorderColor(Color.lightGray); 			        // config Border Color
		config.setDuration(1800);
		config.setAudioEnabled(false);							    // config the audio notification to false
		config.setIcon(new ImageIcon("src/main/dropeFail.png"));    // config icon of the notification in case of the swich receive an error
		switch(icon)
		{
		case 1:
			config.setIcon(new ImageIcon("src/main/dropeOK.png"));  // config icon of the notification

			break;

		case 2:
			config.setIcon(new ImageIcon("src/main/dropeFail.png")); // config icon of the notification

			break;

		default:
			config.setIcon(new ImageIcon("src/main/dropeFail.png")); // config icon of the notification

			break;

		}
		config.setDistanceFromScreen(25);						     // config distance from the top left of the screen
		Achievement achievement = new Achievement(title, message, config); // new notification screen with the parameter
		AchievementQueue queue = new AchievementQueue();				   // creation of the queue 
		queue.add(achievement); // add to the queue in case of they are more than one event


	}

	public static void checkEvents(int id_clients,ArrayList <Integer> listId){ // the point of this fonction is check every time if they are an event, call once and still connected
		try
		{
			db connexion = new db(); // DB connection
			db.db();
			Statement state;
			state =connexion.getState(); // init connection
			//System.out.println(clients_id); // DEBUG
			//ResultSet result= state.executeQuery("SELECT * FROM files WHERE id_groups =''"); // OLD ONE //for each group we DL the files corresponding
			//ArrayList <Integer> listId = new ArrayList<Integer>(); // create a list to put all id already displayed // OLD one
			
				ResultSet result = state.executeQuery("SELECT * FROM events INNER JOIN links ON events.id_groups=links.id_groups WHERE links.id_clients = "+id_clients+" ORDER BY events.hour DESC");
				while(result.next())
				{
					Date  hourNow = new Date();
					Date hourDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result.getString("hour")); // get the date from the DB parse it to Date format
					//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // creation of a specific format for the date

					if(hourNow.getTime() - hourDB.getTime() <= 10*60*1000) // if the difference between now and the DB is less than 10 minutes ( 2 for the minute, 60 for 60x1sec, and 1000 for 1000x0,001sec )
					{
						if(!listId.contains(result.getInt("id"))) // if the list don't contains the id 
						{
							events.newEvent(result.getString("title"), result.getString("message"), 1);
							listId.add(result.getInt("id"));
							Thread.sleep(4000); // wait the end of the animation
						}
					}
				}
				System.out.println("Check event");
			
			state.close(); // close the statement
		}
		catch(Exception e)
		{
			System.out.println(e);
			error.to_DB(id_clients, e); // put the error to the DB
		}

	}


}