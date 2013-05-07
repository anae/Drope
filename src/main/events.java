
package main;


import java.awt.Color;

import javax.swing.ImageIcon;

import org.jachievement.Achievement;
import org.jachievement.AchievementConfig;
import org.jachievement.AchievementQueue;

public class events {

	public static void newEvent(String title, String message, int icon) {
		
	
		AchievementConfig config = new AchievementConfig();        // config of the interface
		config.setBackgroundColor(Color.white); 			       // config Background Color
		config.setTitleColor(Color.GRAY);       			       // config Title Color
		config.setDescriptionColor(Color.GRAY); 			       // config Description Color
		config.setBorderColor(Color.lightGray); 			       // config Border Color
		config.setDuration(1600);
		config.setIcon(new ImageIcon("src/main/dropeFail.png"));   // config icon of the notification in case of the swich receive an error
		switch(icon)
		{
		case 1:
			config.setIcon(new ImageIcon("src/main/dropeOK.png")); // config icon of the notification
			
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
		AchievementQueue queue = new AchievementQueue();				   // creatio of the queue 
		queue.add(achievement); // add to the queue in case of they are more than one event
	
	
	}
	}