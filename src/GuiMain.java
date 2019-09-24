import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import javax.swing.Timer;
import javax.swing.*;
import javax.swing.JOptionPane;

public class GuiMain extends JFrame {

	private JButton btnPlay;
	private JLabel lblWaves;
	private JLabel lblLives;
	private JLabel lblSLives;
	private JLabel lblSWaves;
	private JLabel lblPlayer;
	private JLabel lblBullet;
	private boolean readyToFire = true;
	private ArrayList<JLabel> labels;
	private ArrayList<ImageIcon> images;
	private ArrayList<Invader1> invader1s;
	private ArrayList<Invader2> invader2s;
	private ArrayList<Invader3> invader3s;
	private ArrayList<Invader4> invader4s;
	private ArrayList<JLabel> bombs;
	private ArrayList<JLabel> missiles;
	int holder; //holding variable
	int move; //counts the number of moves 
	Player player;

	public GuiMain() {
		super();
		setTitle("Space Invaders");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel background = new JLabel(new ImageIcon(((new ImageIcon("Background.png")).getImage()).getScaledInstance(500,500, java.awt.Image.SCALE_SMOOTH))); // creates the background for the game
		setContentPane(background);

		lblWaves = new JLabel("Waves:");
		add(lblWaves);
		lblWaves.setLocation(400,10);
		lblWaves.setSize(60,20);
		lblWaves.setFont(new Font("Rockwell",Font.BOLD,14));
		lblWaves.setForeground(Color.WHITE);

		lblSWaves = new JLabel("1/5");
		add(lblSWaves);
		lblSWaves.setLocation(470,10);
		lblSWaves.setSize(60,20);
		lblSWaves.setFont(new Font("Rockwell",Font.BOLD,14));
		lblSWaves.setForeground(Color.WHITE);

		lblLives = new JLabel("Lives:");
		add(lblLives);
		lblLives.setLocation(400,30);
		lblLives.setSize(60,20);
		lblLives.setFont(new Font("Rockwell",Font.BOLD,14));
		lblLives.setForeground(Color.WHITE);

		lblSLives = new JLabel("3/3");
		add(lblSLives);
		lblSLives.setLocation(470,30);
		lblSLives.setSize(60,20);
		lblSLives.setFont(new Font("Rockwell",Font.BOLD,14));
		lblSLives.setForeground(Color.WHITE);

		setVisible(true);
		setResizable(false);
	}

	/**
	 * This method is the main back bone behind the moving and shooting for the player.
	 * @param
	 * @return null
	 */
	public void move() {
		this.Play();
		//Sets icon for the player on the JFrame
		lblPlayer = new JLabel();
		lblPlayer.setIcon(new ImageIcon("Ship.png"));
		add(lblPlayer);
		lblPlayer.setSize(getPreferredSize());
		player = new Player(225,175);
		lblPlayer.setLocation(player.getX(),player.getY());
		lblPlayer.setFocusable(true);
		validate();

		//sets icon for the bullet on the JFrame
		lblBullet = new JLabel();
		lblBullet.setIcon(new ImageIcon("Missle.png"));
		add(lblBullet);
		lblBullet.setSize(getPreferredSize());
		lblBullet.setFocusable(true);
		lblBullet.setVisible(false);
		validate();

		//Adds a KeyLsitener to the player to find out if a key has been pressed.
		lblPlayer.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent ke){

				int key = ke.getKeyCode(); //Simply set as a variable to be used for ease

				//This is to see if the left key is pressed and to move the ship left
				if(key == KeyEvent.VK_LEFT && player.getX() >= 0)
				{
					lblPlayer.setLocation(player.getX()-5, player.getY());  //Sets the new location slightly to the left
					lblPlayer.repaint(); //Repaints the image to the screen
					System.out.println("Left"); //prints to the console displaying the job has been completed
				}
				
				//This is to see if the right key is pressed and to move the ship right
				if(key == KeyEvent.VK_RIGHT && lblPlayer.getX() <= 450)
				{
					lblPlayer.setLocation(lblPlayer.getX()+5, lblPlayer.getY()); //Sets the new location slightly to the right
					lblPlayer.repaint(); //Repaints the image to the screen
					System.out.println("Right");  //prints to the console displaying the job has been completed
				}
				
				//This is to see if the space key is pressed and to shoot a bullet
				if(key == KeyEvent.VK_SPACE && readyToFire == true) 
				{
					lblBullet.setLocation(player.getX() + 19, player.getY() - 15); //Sets bullet initial starting point slightly above the ships cannon
					lblBullet.repaint(); //Repaints the image to the screen
					lblBullet.setVisible(true); //sets the bullet as visible

					//Timer function holding the instructions to make the bullet move
					Timer timerBullet = new Timer(100, new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) {

							System.out.println(lblBullet.getY());  //prints out the y value of the bullet to the screen
							lblBullet.setLocation(player.getX() + 19, player.getY() - 10); //Sets the location of the bullet slightly higher each time
							lblBullet.repaint(); //Repaints the bullet to the screen
							readyToFire = false; //Sets readyToFire as false so next bullet cannot be fired
							
							//When the bullets y is off screen it stops the timer and resets readyToFire
							if(lblBullet.getY() <= -260) {
								readyToFire = true; //Sets readyToFire as true so the next bullet can be fired
								lblBullet.setVisible(false); //sets the bullet to non-visible
								((Timer)e.getSource()).stop(); //Stops the timer
							}
						}
					});
					
					timerBullet.start(); //Starts the timer
					
				}
			}	
		});

		//Adds a KeyLsitener to the player to find out if a key has been released.
		lblPlayer.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent e) {

				int key = e.getKeyCode(); //Simply set as a variable to be used for ease

				if (key == KeyEvent.VK_LEFT) {
					lblPlayer.setLocation(lblPlayer.getX(), lblPlayer.getY()); //Leaves player at position when key released	
				}

				if (key == KeyEvent.VK_RIGHT) {
					lblPlayer.setLocation(lblPlayer.getX(), lblPlayer.getY()); //Leaves player at position when key released
				}

				if(key == KeyEvent.VK_SPACE) 
				{
					//Does nothing when space bar released, as timer takes care of everything.
				}
			}

		});

	}
	
	/**
	 * starts the programs 
	 */
	public void Play(){
		//creates each arraylist
		invader1s = new ArrayList<Invader1>();
		invader2s = new ArrayList<Invader2>();
		invader3s = new ArrayList<Invader3>();
		invader4s = new ArrayList<Invader4>();
		labels = new ArrayList<JLabel>();
		images = new ArrayList<ImageIcon>();
		int i;

		//these loops create all the imageicons and jlabels for the invaders 
		//row one of invaders 
		for (i = 0; i <7; i++){
			images.add(i, new ImageIcon("Invader1.png"));
			labels.add(i, new JLabel(images.get(i)));
			invader1s.add(i, new Invader1(100 + i*20, 100));
			(labels.get(i)).setLocation((invader1s.get(i)).getX(),(invader1s.get(i)).getY());
			(labels.get(i)).setSize(getPreferredSize());
			(labels.get(i)).setFocusable(true);
			validate();
			
		}
		//second row of invaders
		for (i = 7; i < 14; i++){
			images.add(i, new ImageIcon("Invader1.png"));
			labels.add(i, new JLabel(images.get(i)));
			invader1s.add(i, new Invader1(100 + (i-7)*20, 150));
			(labels.get(i)).setLocation((invader1s.get(i)).getX(),(invader1s.get(i)).getY());
			(labels.get(i)).setSize(getPreferredSize());
			(labels.get(i)).setFocusable(true);
			validate();
		}
		//third row of invaders
		for (i = 14 ; i < 21; i++){
			images.add(i, new ImageIcon("Invader1.png"));
			labels.add(i, new JLabel(images.get(i)));
			invader1s.add(i, new Invader1(100 + (i-14)*20, 200));
			(labels.get(i)).setLocation((invader1s.get(i)).getX(),(invader1s.get(i)).getY());
			(labels.get(i)).setSize(getPreferredSize());
			(labels.get(i)).setFocusable(true);
			validate();
		}
		//fourth row of invaders
		for (i = 21 ; i < 28; i++){
			images.add(i, new ImageIcon("Invader1.png"));
			labels.add(i, new JLabel(images.get(i)));
			invader1s.add(i, new Invader1(100 + (i-21)*20, 250));
			(labels.get(i)).setLocation((invader1s.get(i)).getX(),(invader1s.get(i)).getY());
			(labels.get(i)).setSize(getPreferredSize());
			(labels.get(i)).setFocusable(true);
			validate();
		}

		//this starts the wave which is always checking when the invaders are all defeated
		Timer timerWave = new Timer(10, new waveControl());
		timerWave.start();
		//this starts the movement
		Timer timerInvaders = new Timer(3000, new invaderMovement());
		timerInvaders.start();
		//this starts the dropping of bombs by the invaders
		Timer timerBombs = new Timer(200, new invaderBomb());
		timerBombs.start();
	}
	
	private class invaderMovement implements ActionListener {
		public void actionPerformed(ActionEvent e) {
				move++; //counts how many times the invaders moved east or left
				String direction = new String("east");
				
				//creates listiterators for each arraylist
				ListIterator<Invader1> liInv1 = invader1s.listIterator();
				ListIterator<Invader2> liInv2 = invader2s.listIterator();
				ListIterator<Invader3> liInv3 = invader3s.listIterator();
				ListIterator<Invader4> liInv4 = invader4s.listIterator();
				
				//an if statement for determining which invaders to use for the movement depending on the wave
				//wave 1
				if ((lblSWaves.getText()).equals("1/5")){
				while (liInv1.hasNext()){
					holder = liInv1.nextIndex(); //holds the next index number to avoid removed invader elements
					//depending on direction, invaders will either increase or decrease their x-coordinate (move left or right)
					if (direction.equals("east")){
						(invader1s.get(holder)).setX((invader1s.get(holder).getX() + 10));
						(labels.get(holder)).setLocation((invader1s.get(holder)).getX(),(invader1s.get(holder)).getY());
						(labels.get(holder)).repaint();
						}
					else if(direction.equals("west")){
						(invader1s.get(holder)).setX((invader1s.get(holder)).getX() -  10);
						(labels.get(holder)).setLocation((invader1s.get(holder)).getX(),(invader1s.get(holder)).getY());
						(labels.get(holder)).repaint();
					}
				//every 15 moves left/right, all the aliens go down 30 pixels 
				if(move % 15 == 0){
						(invader1s.get(holder)).setY((invader1s.get(holder)).getY() + 30);
						(labels.get(holder)).setLocation((invader1s.get(holder)).getX(),(invader1s.get(holder)).getY());
						(labels.get(holder)).repaint();
						//checks whether the invaders are far down enough that they beat the player
						if((invader1s.get(holder)).getY() == 300){
							if((invader4s.get(holder)).getY() == 300){
								JOptionPane.showOptionDialog(null, "You Lose! Try Again?", "Defeat!", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.INFORMATION_MESSAGE,  null, 
							             new String[]{"Play Again", "Exit"}, "default");
							}
						}
						//every time it goes down, it switches direction
						if (direction.equals("east")){
							direction = "west";
						}
						else
							direction = "east";
					}
				}
				}
				//wave 2
				else if((lblSWaves.getText()).equals("2/5")){
					while (liInv2.hasNext()){
						holder = liInv2.nextIndex(); //holds the next index number to avoid removed invader elements
						//depending on direction, invaders will either increase or decrease their x-coordinate (move left or right)
						if (direction.equals("east")){
							(invader2s.get(holder)).setX((invader2s.get(holder)).getX() + 10);
							(labels.get(holder)).setLocation((invader2s.get(holder)).getX(),(invader2s.get(holder)).getY());
							(labels.get(holder)).repaint();
							}
						else if(direction.equals("west")){
							(invader2s.get(holder)).setX((invader2s.get(holder)).getX() -  10);
							(labels.get(holder)).setLocation((invader2s.get(holder)).getX(),(invader2s.get(holder)).getY());
							(labels.get(holder)).repaint();
						}
					//every 15 moves left/right, all the aliens go down 30 pixels 
					if(move % 15 == 0){
							(invader2s.get(holder)).setY((invader2s.get(holder)).getY() + 30);
							(labels.get(holder)).setLocation((invader2s.get(holder)).getX(),(invader2s.get(holder)).getY());
							(labels.get(holder)).repaint();
							//checks whether the invaders are far down enough that they beat the player
							if((invader2s.get(holder)).getY() == 300){
								if((invader4s.get(holder)).getY() == 300){
									JOptionPane.showOptionDialog(null, "You Lose! Try Again?", "Defeat!", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.INFORMATION_MESSAGE,  null, 
								             new String[]{"Play Again", "Exit"}, "default");
								}
							}
							//every time it goes down, it switches direction
							if (direction.equals("east")){
								direction = "west";
							}
							else
								direction = "east";
						}
					}
				}
				//wave 3
				else if((lblSWaves.getText()).equals("3/5")){
					while (liInv3.hasNext()){
						holder = liInv3.nextIndex(); //holds the next index number to avoid removed invader elements
						//depending on direction, invaders will either increase or decrease their x-coordinate (move left or right)
						if (direction.equals("east")){
							(invader3s.get(holder)).setX((invader3s.get(holder)).getX() + 10);
							(labels.get(holder)).setLocation((invader3s.get(holder)).getX(),(invader3s.get(holder)).getY());
							(labels.get(holder)).repaint();
							}
						else if(direction.equals("west")){
							(invader3s.get(holder)).setX((invader3s.get(holder)).getX() -  10);
							(labels.get(holder)).setLocation((invader3s.get(holder)).getX(),(invader3s.get(holder)).getY());
							(labels.get(holder)).repaint();
						}
					//every 15 moves left/right, all the aliens go down 30 pixels 
					if(move % 15 == 0){
							(invader3s.get(holder)).setY((invader3s.get(holder)).getY() + 30);
							(labels.get(holder)).setLocation((invader3s.get(holder)).getX(),(invader3s.get(holder)).getY());
							(labels.get(holder)).repaint();
							//checks whether the invaders are far down enough that they beat the player
							if((invader3s.get(holder)).getY() == 300){
								if((invader4s.get(holder)).getY() == 300){
									JOptionPane.showOptionDialog(null, "You Lose! Try Again?", "Defeat!", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.INFORMATION_MESSAGE,  null, 
								             new String[]{"Play Again", "Exit"}, "default");
								}
							}
							//every time it goes down, it switches direction
							if (direction.equals("east")){
								direction = "west";
							}
							else
								direction = "east";
						}
					}
				}
				//wave 4
				else if((lblSWaves.getText()).equals("4/5")){
					while (liInv4.hasNext()){
						holder = liInv4.nextIndex(); //holds the next index number to avoid removed invader elements
						//depending on direction, invaders will either increase or decrease their x-coordinate (move left or right)
						if (direction.equals("east")){
							(invader4s.get(holder)).setX((invader4s.get(holder)).getX() + 10);
							(labels.get(holder)).setLocation((invader4s.get(holder)).getX(),(invader4s.get(holder)).getY());
							(labels.get(holder)).repaint();
							}
						else if(direction.equals("west")){
							(invader4s.get(holder)).setX((invader4s.get(holder)).getX() -  10);
							(labels.get(holder)).setLocation((invader4s.get(holder)).getX(),(invader4s.get(holder)).getY());
							(labels.get(holder)).repaint();
						}
					//every 15 moves left/right, all the aliens go down 30 pixels 
					if(move % 15 == 0){
							(invader4s.get(holder)).setY((invader4s.get(holder)).getY() + 30);
							(labels.get(holder)).setLocation((invader4s.get(holder)).getX(),(invader4s.get(holder)).getY());
							(labels.get(holder)).repaint();
							//checks whether the invaders are far down enough that they beat the player
							if((invader4s.get(holder)).getY() == 300){
								if((invader4s.get(holder)).getY() == 300){
									JOptionPane.showOptionDialog(null, "You Lose! Try Again?", "Defeat!", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.INFORMATION_MESSAGE,  null, 
								             new String[]{"Play Again", "Exit"}, "default");
								}
							}
							//every time it goes down, it switches direction
							if (direction.equals("east")){
								direction = "west";
							}
							else
								direction = "east";
						}
					}
				}
				//wave 5
				else if((lblSWaves.getText()).equals("5/5")){
					//first row of invader 1's
					while (liInv1.hasNext()){
						holder = liInv1.nextIndex(); //holds the next index number to avoid removed invader elements
						//depending on direction, invaders will either increase or decrease their x-coordinate (move left or right)
						if (direction.equals("east")){
							(invader1s.get(holder)).setX((invader1s.get(holder)).getX() + 10);
							(labels.get(holder)).setLocation((invader1s.get(holder)).getX(),(invader1s.get(holder)).getY());
							(labels.get(holder)).repaint();
							}
						else if(direction.equals("west")){
							(invader1s.get(holder)).setX((invader1s.get(holder)).getX() -  10);
							(labels.get(holder)).setLocation((invader1s.get(holder)).getX(),(invader1s.get(holder)).getY());
							(labels.get(holder)).repaint();
						}
					//every 15 moves left/right, all the aliens go down 30 pixels 
					if(move % 15 == 0){
							(invader1s.get(holder)).setY((invader1s.get(holder)).getY() + 30);
							(labels.get(holder)).setLocation((invader1s.get(holder)).getX(),(invader1s.get(holder)).getY());
							(labels.get(holder)).repaint();
							//checks whether the invaders are far down enough that they beat the player
							if((invader1s.get(holder)).getY() == 300){
								if((invader4s.get(holder)).getY() == 300){
									JOptionPane.showOptionDialog(null, "You Lose! Try Again?", "Defeat!", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.INFORMATION_MESSAGE,  null, 
								             new String[]{"Play Again", "Exit"}, "default");
								}
							}
							//every time it goes down, it switches direction
							if (direction.equals("east")){
								direction = "west";
							}
							else
								direction = "east";
						}
					}
					//second row of invader 2's
					while (liInv2.hasNext()){
						holder = liInv2.nextIndex(); //holds the next index number to avoid removed invader elements
						//depending on direction, invaders will either increase or decrease their x-coordinate (move left or right)
						if (direction.equals("east")){
							(invader2s.get(holder)).setX((invader2s.get(holder)).getX() + 10);
							(labels.get(holder)).setLocation((invader2s.get(holder)).getX(),(invader2s.get(holder)).getY());
							(labels.get(holder)).repaint();
							}
						else if(direction.equals("west")){
							(invader2s.get(holder)).setX((invader2s.get(holder)).getX() -  10);
							(labels.get(holder)).setLocation((invader2s.get(holder)).getX(),(invader2s.get(holder)).getY());
							(labels.get(holder)).repaint();
						}
					//every 15 moves left/right, all the aliens go down 30 pixels 
					if(move % 15 == 0){
							(invader2s.get(holder)).setY((invader2s.get(holder)).getY() + 30);
							(labels.get(holder)).setLocation((invader2s.get(holder)).getX(),(invader2s.get(holder)).getY());
							(labels.get(holder)).repaint();
							//checks whether the invaders are far down enough that they beat the player
							if((invader2s.get(holder)).getY() == 300){
								if((invader4s.get(holder)).getY() == 300){
									JOptionPane.showOptionDialog(null, "You Lose! Try Again?", "Defeat!", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.INFORMATION_MESSAGE,  null, 
								             new String[]{"Play Again", "Exit"}, "default");
								}
							}
							//every time it goes down, it switches direction
							if (direction.equals("east")){
								direction = "west";
							}
							else
								direction = "east";
						}
					}
					//third row of invader 3's
					while (liInv3.hasNext()){
						holder = liInv3.nextIndex(); //holds the next index number to avoid removed invader elements
						//depending on direction, invaders will either increase or decrease their x-coordinate (move left or right)
						if (direction.equals("east")){
							(invader3s.get(holder)).setX((invader3s.get(holder)).getX() + 10);
							(labels.get(holder)).setLocation((invader3s.get(holder)).getX(),(invader3s.get(holder)).getY());
							(labels.get(holder)).repaint();
							}
						else if(direction.equals("west")){
							(invader3s.get(holder)).setX((invader3s.get(holder)).getX() -  10);
							(labels.get(holder)).setLocation((invader3s.get(holder)).getX(),(invader3s.get(holder)).getY());
							(labels.get(holder)).repaint();
						}
					//every 15 moves left/right, all the aliens go down 30 pixels 
					if(move % 15 == 0){
							(invader3s.get(holder)).setY((invader3s.get(holder)).getY() + 30);
							(labels.get(holder)).setLocation((invader3s.get(holder)).getX(),(invader3s.get(holder)).getY());
							(labels.get(holder)).repaint();
							//checks whether the invaders are far down enough that they beat the player
							if((invader3s.get(holder)).getY() == 300){
								if((invader4s.get(holder)).getY() == 300){
									JOptionPane.showOptionDialog(null, "You Lose! Try Again?", "Defeat!", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.INFORMATION_MESSAGE,  null, 
								             new String[]{"Play Again", "Exit"}, "default");
								}
							}
							//every time it goes down, it switches direction
							if (direction.equals("east")){
								direction = "west";
							}
							else
								direction = "east";
						}
					}
					//fourth row of invader 4's
					while (liInv4.hasNext()){
						holder = liInv4.nextIndex(); //holds the next index number to avoid removed invader elements
						//depending on direction, invaders will either increase or decrease their x-coordinate (move left or right)
						if (direction.equals("east")){
							(invader4s.get(holder)).setX((invader4s.get(holder)).getX() + 10);
							(labels.get(holder)).setLocation((invader4s.get(holder)).getX(),(invader4s.get(holder)).getY());
							(labels.get(holder)).repaint();
							}
						else if(direction.equals("west")){
							(invader4s.get(holder)).setX((invader4s.get(holder)).getX() -  10);
							(labels.get(holder)).setLocation((invader4s.get(holder)).getX(),(invader4s.get(holder)).getY());
							(labels.get(holder)).repaint();
						}
					//every 15 moves left/right, all the aliens go down 30 pixels 
					if(move % 15 == 0){
							(invader4s.get(holder)).setY((invader4s.get(holder)).getY() + 30);
							(labels.get(holder)).setLocation((invader4s.get(holder)).getX(),(invader4s.get(holder)).getY());
							(labels.get(holder)).repaint();
							//checks whether the invaders are far down enough that they beat the player
							if((invader4s.get(holder)).getY() == 300){
								JOptionPane.showOptionDialog(null, "You Lose! Try Again?", "Defeat!", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.INFORMATION_MESSAGE,  null, 
							             new String[]{"Play Again", "Exit"}, "default");
							}
							//every time it goes down, it switches direction
							if (direction.equals("east")){
								direction = "west";
							}
							else
								direction = "east";
						}
					}
				}
		}
	}

	private class invaderBomb implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//this timer is the timer for the bullet speed
			ArrayList<Timer> timerSpeeds = new ArrayList<Timer>();
			
			//wave 1
			if ((lblSWaves.getText()).equals("1/5")){
					//creates iterators for each arraylist
					ListIterator<Invader1> liInv1 = invader1s.listIterator();
					
					int i = 0; //counts the number of elements left in the arraylist
					while(liInv1.hasNext()){
						i++;
						liInv1.next(); //used just to skip to next element in arraylist
					}
					int[] random = new int[i]; //sets the array size to the number of invaders left
					liInv1 = invader1s.listIterator(); //resets the iterator just in case
					//adds each index reference point in the arraylist that's left to an array
					for (int j = 0; j < i; j++){
					random[j] = liInv1.nextIndex();
				}
					Random R = new Random(0,i); //creates a Random object to generate random numbers
					int rNum = R.genRandom(); //generates random number
					
					//creates the bomb at the randomly picked index in the arraylist of invader1s
					bombs.add(0, new JLabel(new ImageIcon("missile.png")));
					(bombs.get(0)).setLocation((invader1s.get(random[rNum])).getX() - 19,(invader1s.get(random[rNum])).getY() + 15);
					(bombs.get(0)).setSize(getPreferredSize());
					(bombs.get(0)).setFocusable(true);
					validate();
					//creates the timer and starts it
					timerSpeeds.add(0,new Timer(100, new bombMovement()));
					(timerSpeeds.get(0)).start();
			}
			
			//wave 2
			else if((lblSWaves.getText()).equals("2/5")){
				//creates iterators for each arraylist
				ListIterator<Invader2> liInv2 = invader2s.listIterator();
				
				int i = 0; //counts the number of elements left in the arraylist
				while(liInv2.hasNext()){
					i++;
					liInv2.next(); //used just to skip to next element in arraylist
				}
				int[] random = new int[i]; //sets the array size to the number of invaders left
				liInv2 = invader2s.listIterator(); //resets the iterator just in case
				//adds each index reference point in the arraylist that's left to an array
				for (int j = 0; j < i; j++){
				random[j] = liInv2.nextIndex();
			}
				Random R = new Random(0,i); //creates a Random object to generate random numbers
				int rNum = R.genRandom(); //generates random number
		
				//creates the bomb at the randomly picked index in the arraylist of invader1s
				bombs.add(1, new JLabel(new ImageIcon("missile.png")));
				(bombs.get(1)).setLocation((invader2s.get(random[rNum])).getX() - 19,(invader2s.get(random[rNum])).getY() + 15);
				(bombs.get(1)).setSize(getPreferredSize());
				(bombs.get(1)).setFocusable(true);
				validate();
				//creates the timer and starts it
				timerSpeeds.add(1,new Timer(100, new bombMovement()));
				(timerSpeeds.get(1)).start();
			}
			 
			//wave 3
			else if((lblSWaves.getText()).equals("3/5")){
				//creates iterators for each arraylist
				ListIterator<Invader3> liInv3 = invader3s.listIterator();
				
				int i = 0; //counts the number of elements left in the arraylist
				while(liInv3.hasNext()){
					i++;
					liInv3.next(); //used just to skip to next element in arraylist
				}
				int[] random = new int[i]; //sets the array size to the number of invaders left
				liInv3 = invader3s.listIterator(); //resets the iterator just in case
				//adds each index reference point in the arraylist that's left to an array
				for (int j = 0; j < i; j++){
				random[j] = liInv3.nextIndex();
			}
				Random R = new Random(0,i); //creates a Random object to generate random numbers
				int rNum = R.genRandom(); //generates random number
		
				//creates the bomb at the randomly picked index in the arraylist of invader1s
				bombs.add(2, new JLabel(new ImageIcon("missile.png")));
				(bombs.get(2)).setLocation((invader3s.get(random[rNum])).getX() - 19,(invader3s.get(random[rNum])).getY() + 15);
				(bombs.get(2)).setSize(getPreferredSize());
				(bombs.get(2)).setFocusable(true);
				validate();
				//creates the timer and starts it
				timerSpeeds.add(2,new Timer(100, new bombMovement()));
				(timerSpeeds.get(2)).start();
			}
			//wave 2
			else if((lblSWaves.getText()).equals("4/5")){
				//creates iterators for each arraylist
				ListIterator<Invader4> liInv4 = invader4s.listIterator();
				
				int i = 0; //counts the number of elements left in the arraylist
				while(liInv4.hasNext()){
					i++;
					liInv4.next(); //used just to skip to next element in arraylist
				}
				int[] random = new int[i]; //sets the array size to the number of invaders left
				liInv4 = invader4s.listIterator(); //resets the iterator just in case
				//adds each index reference point in the arraylist that's left to an array
				for (int j = 0; j < i; j++){
				random[j] = liInv4.nextIndex();
			}
				Random R = new Random(0,i); //creates a Random object to generate random numbers
				int rNum = R.genRandom(); //generates random number
		
				//creates the bomb at the randomly picked index in the arraylist of invader1s
				bombs.add(3, new JLabel(new ImageIcon("missile.png")));
				(bombs.get(3)).setLocation((invader4s.get(random[rNum])).getX() - 19,(invader4s.get(random[rNum])).getY() + 15);
				(bombs.get(3)).setSize(getPreferredSize());
				(bombs.get(3)).setFocusable(true);
				validate();
				//creates the timer and starts it
				timerSpeeds.add(3,new Timer(100, new bombMovement()));
				(timerSpeeds.get(3)).start();
			}
			//wave 2
			else if((lblSWaves.getText()).equals("5/5")){
				//creates iterators for each arraylist
				ListIterator<Invader1> liInv1 = invader1s.listIterator();
				ListIterator<Invader2> liInv2 = invader2s.listIterator();
				ListIterator<Invader3> liInv3 = invader3s.listIterator();
				ListIterator<Invader4> liInv4 = invader4s.listIterator();
				
				int a = 0; //counts the number of elements left in the arraylist1
				int b = 0; //counts the number of elements left in the arraylist2
				int c = 0; //counts the number of elements left in the arraylist3
				int d = 0; //counts the number of elements left in the arraylist4
				
				while(liInv1.hasNext()){
					a++;
					liInv1.next(); //used just to skip to next element in arraylist
				}
				while(liInv2.hasNext()){
					b++;
					liInv2.next(); //used just to skip to next element in arraylist
				}
				while(liInv3.hasNext()){
					c++;
					liInv3.next(); //used just to skip to next element in arraylist
				}
				while(liInv4.hasNext()){
					d++;
					liInv4.next(); //used just to skip to next element in arraylist
				}
				
				//calculates the total number of elements in all 4 arraylists
				int i = a + b + c + d;
				int[] random = new int[i]; //sets the array size to the number of invaders left
				
				//resets the iterators just in case
				liInv1 = invader1s.listIterator(); 
				liInv2 = invader2s.listIterator(); 
				liInv3 = invader3s.listIterator(); 
				liInv4 = invader4s.listIterator(); 
				
				//adds each index reference point in the arraylist that's left to an array
				for (int j = 0; j < (i ); j++){
				random[j] = liInv1.nextIndex();
				}
				for (int j = 0; j < (i / 4 * 2); j++){
					random[j] = liInv2.nextIndex();
				}
				for (int j = 0; j < (i / 4 * 3); j++){
					random[j] = liInv3.nextIndex();
				}
				for (int j = 0; j < (i / 4 * 4); j++){
					random[j] = liInv4.nextIndex();
				}
				Random R = new Random(0,i); //creates a Random object to generate random numbers
				int rNum = R.genRandom(); //generates random number
		
				
				
				//creates the bomb at the randomly picked index in the arraylist of invader1s
				bombs.add(1, new JLabel(new ImageIcon("missile.png")));
				(bombs.get(1)).setLocation((invader2s.get(random[rNum])).getX() - 19,(invader2s.get(random[rNum])).getY() + 15);
				(bombs.get(1)).setSize(getPreferredSize());
				(bombs.get(1)).setFocusable(true);
				validate();
				//creates the timer and starts it
				timerSpeeds.add(1,new Timer(100, new bombMovement()));
				(timerSpeeds.get(1)).start();
			}
		}
	}
	private class bombMovement implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			}
		}
	
	private class waveControl implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//this checks if all the invaders are destroyed and will start the next wave... 
			//...if you beat all five waves

			if (invader1s.isEmpty() && invader2s.isEmpty() && invader3s.isEmpty() && invader4s.isEmpty()){
				int i; //represents the invader in the arraylist
				move = 0; //resets the moves
				
				if ((lblSWaves.getText()).equals("1/5")){
					labels.clear();
					images.clear();
					lblSWaves.setText("2/5");
					//these loops create all the imageicons and jlabels for the invaders 
					//row one of invaders 
					for (i = 0; i <7; i++){
						images.add(i, new ImageIcon("Invader2.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader2s.add(i, new Invader2(100 + i*20, 100));
						(labels.get(i)).setLocation((invader2s.get(i)).getX(),(invader2s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//second row of invaders
					for (i = 7; i < 14; i++){
						images.add(i, new ImageIcon("Invader2.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader2s.add(i, new Invader2(100 + (i-7)*20, 150));
						(labels.get(i)).setLocation((invader2s.get(i)).getX(),(invader2s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//third row of invaders
					for (i = 14 ; i < 21; i++){
						images.add(i, new ImageIcon("Invader2.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader2s.add(i, new Invader2(100 + (i-14)*20, 200));
						(labels.get(i)).setLocation((invader2s.get(i)).getX(),(invader2s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//fourth row of invaders
					for (i = 21 ; i < 28; i++){
						images.add(i, new ImageIcon("Invader2.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader2s.add(i, new Invader2(100 + (i-14)*20, 200));
						(labels.get(i)).setLocation((invader2s.get(i)).getX(),(invader2s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
				}
				else if((lblSWaves.getText()).equals("2/5")){
					labels.clear();
					images.clear();
					lblSWaves.setText("3/5");
					//these loops create all the imageicons and jlabels for the invaders 
					//row one of invaders 
					for (i = 0; i <7; i++){
						images.add(i, new ImageIcon("Invader3.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader3s.add(i, new Invader3(100 + i*20, 100));
						(labels.get(i)).setLocation((invader3s.get(i)).getX(),(invader3s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//second row of invaders
					for (i = 7; i < 14; i++){
						images.add(i, new ImageIcon("Invader3.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader3s.add(i, new Invader3(100 + (i-7)*20, 150));
						(labels.get(i)).setLocation((invader3s.get(i)).getX(),(invader3s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//third row of invaders
					for (i = 14 ; i < 21; i++){
						images.add(i, new ImageIcon("Invader3.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader3s.add(i, new Invader3(100 + (i-14)*20, 200));
						(labels.get(i)).setLocation((invader3s.get(i)).getX(),(invader3s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//fourth row of invaders
					for (i = 21 ; i < 28; i++){
						images.add(i, new ImageIcon("Invader3.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader3s.add(i, new Invader3(100 + (i-14)*20, 200));
						(labels.get(i)).setLocation((invader3s.get(i)).getX(),(invader3s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
				}
				else if((lblSWaves.getText()).equals("3/5")){
					labels.clear();
					images.clear();
					lblSWaves.setText("4/5");
					//these loops create all the imageicons and jlabels for the invaders 
					//row one of invaders 
					for (i = 0; i <7; i++){
						images.add(i, new ImageIcon("Invader4.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader4s.add(i, new Invader4(100 + i*20, 100));
						(labels.get(i)).setLocation((invader4s.get(i)).getX(),(invader4s.get(i)).getY()); 
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//second row of invaders
					for (i = 7; i < 14; i++){
						images.add(i, new ImageIcon("Invader4.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader4s.add(i, new Invader4(100 + (i-7)*20, 150));
						(labels.get(i)).setLocation((invader4s.get(i)).getX(),(invader4s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//third row of invaders
					for (i = 14 ; i < 21; i++){
						images.add(i, new ImageIcon("Invader4.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader4s.add(i, new Invader4(100 + (i-14)*20, 200));
						(labels.get(i)).setLocation((invader4s.get(i)).getX(),(invader4s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//fourth row of invaders
					for (i = 21 ; i < 28; i++){
						images.add(i, new ImageIcon("Invader4.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader4s.add(i, new Invader4(100 + (i-14)*20, 200));
						(labels.get(i)).setLocation((invader4s.get(i)).getX(),(invader4s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
				}
				else if((lblSWaves.getText()).equals("4/5")){
					labels.clear();
					images.clear();
					lblSWaves.setText("5/5");
					//these loops create all the imageicons and jlabels for the invaders 
					//row one of invaders 
					for (i = 0; i <7; i++){
						images.add(i, new ImageIcon("Invader1.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader1s.add(i, new Invader1(100 + i*20, 100));
						(labels.get(i)).setLocation((invader1s.get(i)).getX(),(invader1s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//second row of invaders
					for (i = 7; i < 14; i++){
						images.add(i, new ImageIcon("Invader2.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader2s.add(i, new Invader2(100 + (i-7)*20, 150));
						(labels.get(i)).setLocation((invader2s.get(i)).getX(),(invader2s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//third row of invaders
					for (i = 14 ; i < 21; i++){
						images.add(i, new ImageIcon("Invader3.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader3s.add(i, new Invader3(100 + (i-14)*20, 200));
						(labels.get(i)).setLocation((invader3s.get(i)).getX(),(invader3s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
					//fourth row of invaders
					for (i = 21 ; i < 28; i++){
						images.add(i, new ImageIcon("Invader4.png"));
						labels.add(i, new JLabel(images.get(i)));
						invader4s.add(i, new Invader4(100 + (i-14)*20, 200));
						(labels.get(i)).setLocation((invader4s.get(i)).getX(),(invader4s.get(i)).getY());
						(labels.get(i)).setSize(getPreferredSize());
						(labels.get(i)).setFocusable(true);
						validate();
					}
				}
				else if((lblSWaves.getText()).equals("5/5")){
					JOptionPane.showOptionDialog(null, "Congratulations! You win!", "Winner!", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.INFORMATION_MESSAGE,  null, 
				             new String[]{"Play Again", "Exit"}, "default");
				}

			}
		}
	}
}

