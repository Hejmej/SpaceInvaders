import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame {

	private JButton btnPlay;
	private JButton btnExit;
	private JLabel lblWaves;
	private JLabel lblLives;
	private JLabel lblSLives;
	private JLabel lblSWaves;
	private JLabel lblTitle;
	private ArrayList<JLabel> labels;
	private ArrayList<ImageIcon> images;
	private ArrayList<Invader> invaders;

	/**
	 * @author: Alex Hejmej & Ryan Polkiewicz
	 * @Date: Jan 2017
	 * This is the Main menu class created to start or exit the game
	 * 
	 **/
	public MainMenu() {
		super();
		setTitle("Space Invaders");
		setSize(500, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel background = new JLabel(new ImageIcon(((new ImageIcon("Background.png")).getImage()).getScaledInstance(500,500, java.awt.Image.SCALE_SMOOTH))); // creates the background for the game
		setContentPane(background);

		lblTitle = new JLabel();
		lblTitle.setIcon(new ImageIcon("Title.png"));
		add(lblTitle);
		lblTitle.setSize(getPreferredSize());
		lblTitle.setLocation(100,-150);
		validate();

		btnPlay = new JButton("Start");
		btnPlay.setActionCommand("Play");
		btnPlay.addActionListener(new btnListener());
		add(btnPlay);
		btnPlay.setSize(85,50);
		btnPlay.setLocation(150,250);
		btnPlay.setOpaque(false);
		btnPlay.setContentAreaFilled(false);
		btnPlay.setBorderPainted(true);                                     
		btnPlay.setFont(new Font("Rockwell",Font.BOLD,20));
		btnPlay.setForeground(Color.WHITE);
		
		btnExit = new JButton("Exit");
		btnExit.setActionCommand("Exit");
		btnExit.addActionListener(new btnListener2());
		add(btnExit);
		btnExit.setSize(75,50);
		btnExit.setLocation(275,250);
		btnExit.setOpaque(false);
		btnExit.setContentAreaFilled(false);
		btnExit.setBorderPainted(true);                                     
		btnExit.setFont(new Font("Rockwell",Font.BOLD,20));
		btnExit.setForeground(Color.WHITE);

		setVisible(true);
		setResizable(false);
	}

	//Starts the program on button click
	private class btnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Play")){
					dispose(); //Disposes of Main Menu
					GuiMain start = new GuiMain(); //Creates a new object from GuiMain
					start.move(); //Runs the player control method from GuiMain
			}
		}

	}
	
	//Exits the program on button click
	private class btnListener2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Exit")){
				System.exit(0); //Exits the program on button click
			}
		}

	}
	
	public static void main(String [] args){
		new MainMenu();
	}
}

