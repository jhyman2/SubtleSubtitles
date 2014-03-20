package subtitles;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JWindow;

public class Graphix {
	
	public static ArrayList<TestTitle>subtitles = new ArrayList<TestTitle>();
	public final static JFrame JFwindow = new JFrame("Jamison's Subtitles");

	public static void populateTitles(File subFile){
		//File subFile = new File("The Hunger Games.srt");
		Scanner s = null;
		try {
			s = new Scanner(subFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("GETTING HERE");
		while(s.hasNext()){
			String l = s.nextLine();
			Pattern theNumber = Pattern.compile("(.*:.*:.*,.*) --> (.*)");
	    	Matcher isMatched = theNumber.matcher(l);
	    	if (isMatched.find()){
	    		String start = isMatched.group(1);
	    		String end = isMatched.group(2);
	    		String text = "";
	    		while(s.hasNext()){
	    			String input = s.nextLine(); 
	    			Boolean b = true;
	    			try { 
	    				int x = Integer.parseInt(input); 
	    			//	System.out.println(x); 
	    				} 
	    				catch(NumberFormatException nFE) { 
	    				b = false;
	    			}
	    			if (!b){
	    				text += input + " ";
	    			}
	    			else{
	    				DateFormat sdf = new SimpleDateFormat("hh:mm:ss,SSS");
	    				Date startDate = null;
						try {
							startDate = sdf.parse(start);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							System.out.println("Messed up start date parsing.");
							e1.printStackTrace();
						}
	    				Date endDate = null;
						try {
							endDate = sdf.parse(end);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							System.out.println("Messed up end date parsing.");
							e.printStackTrace();
						}
	    				subtitles.add(new TestTitle(startDate, endDate, text, sdf));
	    				break;
	    			}
	    		}
	    			
	    	}
		}
		System.out.println("GETTING END");
		System.out.println(subtitles);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		JFrame win = new JFrame();
		try {
			win.getContentPane().add(new JPanelWithBackground("loading.jpg"));
			win.setSize(485, 315);
			win.setLocationRelativeTo(null);
			win.setUndecorated(true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			win.setVisible(true);
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		win.setVisible(false);
		win.dispose();
		
		System.out.println("Getting here1");
		JFwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	
		JFwindow.getContentPane().add(new Subtitles()); 
		JFwindow.setSize((int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 150);
		JFwindow.setUndecorated(true);
		JFwindow.setBackground(new Color(0,0,0,0));
		System.out.println("Getting here2");
		JFwindow.pack();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int)rect.getMinX();
		int y = (int)rect.getMaxY()-JFwindow.getHeight()-40; // PLACES WINDOW AT BOTTOM
		JFwindow.setLocation(x,y);
		JFwindow.setAlwaysOnTop(true);
		JFwindow.setTitle("Subtle Subtitles");
		System.out.println("Getting here3");
		JFwindow.setVisible(true);	
		System.out.println("Getting here4");
	}
}