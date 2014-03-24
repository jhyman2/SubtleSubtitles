package subtitles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class Subtitles extends JPanel {

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path)
	{
		java.net.URL imgURL = Subtitles.class.getResource(path);
		if (imgURL != null) 
		{
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField text = new JTextField(
			"Please click \"Open a file\" to load your subtitles");
	private JButton start = new JButton("Start");
	private JButton pause = new JButton("Pause");
	private JButton openFile;
	boolean isPaused = false;
	boolean isHidden = false;
	JFileChooser fc;
	Font f = new Font("serif", Font.BOLD, 32);
	public int counter;
	JSlider slide;
	JMenuBar menubar;
	JMenu menu;
	JMenuItem menuItem, menuItem2, menuItem3, menuItem4;

	public Subtitles() 
	{

		// SETS EMPTY BORDER
		this.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		text.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		// MENU
		menu = new JMenu("Help");
		menubar = new JMenuBar();
		menu.setMnemonic(KeyEvent.VK_H);
		menu.getAccessibleContext().setAccessibleDescription("Halp");
		menuItem = new JMenuItem("How to use application", KeyEvent.VK_U);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JOptionPane
						.showMessageDialog(
								menuItem,
								"1) Click \"Open a file\" and load your subtitles\n"
										+ "2) Click \"Start\" and the play button your movie\n "
										+ "3) Click \"Pause\" to pause the subtitles\n"
										+ "4) Use the slider to move the current time of the subtitles\n "
										+ "5) Use the color picker to change the text color\n"
										+ "6) Press Alt+F and Alt+E to exit\n"
										+ "7) Press Win+H to show/hide the buttons while the movie is playing");
			}
		});

		menu.add(menuItem);

		this.setBackground(new Color(0, 0, 0, 0));
		text.setForeground(Color.blue);
		text.copy();
		text.selectAll();
		text.requestFocusInWindow();
		text.setBackground(new Color(0, 0, 0, 0));
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				System.exit(0);
			}
		});

		final JColorChooser tcc = new JColorChooser(text.getForeground());
		tcc.getSelectionModel().addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				text.setForeground(tcc.getColor());
			}

		});

		menuItem2 = new JMenuItem("Select a text color");
		menuItem2.setToolTipText("Select a text color");
		menuItem2.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {

				JOptionPane.showMessageDialog(menuItem2, tcc);

			}

		});
		
		menuItem3 = new JMenuItem("Load state from file");
		menuItem3.setToolTipText("Select");
		menuItem3.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(menuItem3) == JFileChooser.APPROVE_OPTION) 
				{
				  File file = fileChooser.getSelectedFile();
				  Scanner inFile = null;
				  try 
				  {
					inFile = new Scanner(file);
					int curr = inFile.nextInt();
					counter = curr;
					slide.setValue(counter);
					System.out.println("COUNT FROM FILE: " + curr);
				  } 
				  catch (FileNotFoundException e1) 
				  {
					e1.printStackTrace();
				}
				  inFile.close();
				  // load from file
				}
			}
			
		});
		
		menuItem4 = new JMenuItem("Save current state");
		menuItem4.setToolTipText("Save");
		menuItem4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(menuItem4) == JFileChooser.APPROVE_OPTION) 
				{
				  File file = fileChooser.getSelectedFile();
				  PrintWriter printer = null;
				  try 
				  {
					printer = new PrintWriter(file);
					printer.write(new Integer(counter).toString());
					System.out.println("WROTE SUCCESFULLY: " + counter);
				} 
				  catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				  catch (IOException e) 
				  {
					// TODO Auto-generated catch block
					e.printStackTrace();
				  }
				 printer.close();
				}
			}
		}
		);
		
		file.add(menuItem4);
		file.add(menuItem3);
		file.add(menuItem2);
		file.add(eMenuItem);

		menubar.add(file);
		menubar.add(menu);

		this.add(menubar, BorderLayout.NORTH);
		counter = 0;
		slide = new JSlider(SwingConstants.HORIZONTAL, 0,
				Graphix.subtitles.size(), 0);
		text.setFont(f);
		openFile = new JButton("Open a file...");
		openFile.addActionListener(new ActionListener2());
		fc = new JFileChooser();
		text.setHorizontalAlignment(SwingConstants.CENTER); // CENTERS TEXT!
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension((int) java.awt.Toolkit
				.getDefaultToolkit().getScreenSize().getWidth(), 110));
		start.addActionListener(new ActionListener2());
		pause.addActionListener(new ActionListener2());
		slide.setBackground(new Color(0,0,0,0));
		
		this.add(text, BorderLayout.PAGE_END);
		this.add(start, BorderLayout.CENTER);
		this.add(pause, BorderLayout.LINE_END);
		this.add(fc, BorderLayout.LINE_START);
		this.add(openFile, BorderLayout.LINE_START);

		this.add(menubar, BorderLayout.PAGE_START);
		this.requestFocus();

		JIntellitype.getInstance();
		if (JIntellitype.checkInstanceAlreadyRunning("MyApp")) 
			{
			   System.out.println("An instance of this application is already running");
			   System.exit(1);
			}
		
		JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_WIN, (int)'H');
		JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_WIN, (int)'Q');
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {

			@Override
			public void onHotKey(int arg0) 
			{
				// TODO Auto-generated method stub
				if (arg0 == 1){
					System.out.println("H HAS BEEN PRESSED");
					System.out.println("HIDDEN");
					if (isHidden) {
						slide.setVisible(true);
						pause.setVisible(true);
						openFile.setVisible(true);
						fc.setVisible(true);
						menubar.setVisible(true);
						//listPane.setVisible(true);
						isHidden = false;
					} else {
						menubar.setVisible(false);
						//listPane.setVisible(false);
						slide.setVisible(false);
						pause.setVisible(false);
						openFile.setVisible(false);
						fc.setVisible(false);
						isHidden = true;
					}
				}
				else if (arg0 == 2)
				{
					System.out.println("CLOSING");
					System.exit(0);
				}
			}	
		}
		);
	}

	private void first() 
	{
		Graphix.JFwindow.setBackground(new Color(0, 0, 0, 0));
		try 
		{
			Thread.sleep(Graphix.subtitles.get(counter).getStart());
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		Graphix.JFwindow.setBackground(new Color(0, 0, 0, 0));
	}

	private void second() 
	{
		Graphix.JFwindow.setBackground(new Color(0, 0, 0, 0));

		try 
		{
			Thread.sleep(Graphix.subtitles.get(counter).getStart()
					- Graphix.subtitles.get(counter - 1).getEnd());
		}
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Graphix.JFwindow.setBackground(new Color(0, 0, 0, 0));
	}

	private void third() 
	{
		slide.setValue(counter);

		try 
		{
			Thread.sleep(Graphix.subtitles.get(counter).getEnd()
					- Graphix.subtitles.get(counter).getStart());
		}
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		counter++;
	}

	private class ActionListener2 implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			String actionCommand = arg0.getActionCommand();
			if (actionCommand.equals("Start")) {
				// start.setText("Start Over");
				start.setVisible(false);
				slide.setVisible(true);
				Graphix.JFwindow.setBackground(new Color(0, 0, 0, 0));
				//slide.set
				text.setText(" ");
				System.out.println(1);
				new Thread(new Runnable() 
				{
					@Override
					public void run() 
					{	
						System.out.println(2);
						counter = 0;
						while (counter < Graphix.subtitles.size()) {
							System.out.println(3);
							Graphix.JFwindow
									.setBackground(new Color(0, 0, 0, 0));
							if (!isPaused) {
								//System.out.println("GETTING IN WHILE");
								Graphix.JFwindow.setBackground(new Color(0, 0,
										0, 0));
								text.setText(" ");
								System.out.println(4);
								if (counter == 0) {
									first();
								} else {
									second();
								}

								String next = Graphix.subtitles.get(counter)
										.getText();
								Pattern theNumber = Pattern
										.compile("(<i>)(.*)(</i>)");
								Matcher isMatched = theNumber.matcher(next);
								if (isMatched.find()) {
									Graphix.JFwindow.setBackground(new Color(0,
											0, 0, 0));
									text.setText(isMatched.group(2));
									Font newFont = new Font(text.getFont()
											.getName(), Font.ITALIC, text
											.getFont().getSize());
									text.setFont(newFont);
								} else {
									Graphix.JFwindow.setBackground(new Color(0,
											0, 0, 0));
									text.setText(next);
									Font newTextFieldFont = new Font(text
											.getFont().getName(), Font.PLAIN,
											text.getFont().getSize());
									text.setFont(newTextFieldFont);
									Graphix.JFwindow.setBackground(new Color(0,
											0, 0, 0));
									text.setText(Graphix.subtitles.get(counter)
											.getText());
								}

								third();
								slide.setValue(counter);
							}
						}
					}
				}).start();
			} else if (actionCommand.equals("Pause")
					|| actionCommand.equals("Resume")) {
				if (!isPaused) {
					isPaused = true;
					pause.setText("Resume");
				} else {
					isPaused = false;
					pause.setText("Pause");
				}

			} else if (arg0.getSource() == openFile) {
				int returnVal = fc.showOpenDialog(Subtitles.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					Graphix.populateTitles(file);
					start.setVisible(true);
					slide.setVisible(true);
					pause.setVisible(true);
					text.setText("");
					Graphix.JFwindow.setBackground(new Color(0, 0, 0, 0));
					text.setText("Subtitles loaded.  Press play to begin.");
// could it have something to do with re-declaring it? OHHHH, I see!
					slide = new JSlider(SwingConstants.HORIZONTAL, 0,
							Graphix.subtitles.size(), 0);
					slide.setMaximum(Graphix.subtitles.size());
					slide.setValue(0);
					slide.addChangeListener(new ChangeListener() {

						@Override
						public void stateChanged(ChangeEvent e) {
							// TODO Auto-generated method stub
							JSlider source = (JSlider) e.getSource();
							if (!source.getValueIsAdjusting()) {
								int count = source.getValue();
								System.out.println("Count: " + count);
								counter = count;
							}
							System.out.println("SLIDING");

						}

					});
					Subtitles.this.add(slide, BorderLayout.CENTER);
				//	slide.setBackground(new Color(0,0,0,0));
					System.out.println("Opening: " + file.getName());
				} else {
					System.out.println("Trouble finding the file? Haha!");
				}
			}
		}
	}
}