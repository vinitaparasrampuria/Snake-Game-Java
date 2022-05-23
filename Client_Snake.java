
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.Random;

import javax.swing.*;



	public class Client_Snake extends JFrame {
	  DataOutputStream toServer = null;
	  DataInputStream fromServer = null;
	  Socket socket = null;
	  boolean exit=true;
	  int count=0;
	  String name;
	  String password;
	  GamePanel panel;
	  String highScore;
	  String bestTime;
	  listenScoreMessage m;
	  
	  public Client_Snake() {
		  
		  
		  try {
				socket = new Socket("localhost", 9000);
				Thread t= new Thread();
				m=new listenScoreMessage();
				System.out.println("Connected"+"\n");
				System.out.println("New thread started");
				SnakeGame();
				loginCredentials();
				
		  }catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("connection Failure"+"\n");
			}
	  }
	 

	  
	  public void loginCredentials() {
		  
		  JFrame loginFrame=new Login1();
		  loginFrame.setLocationRelativeTo(null);
		  loginFrame.setResizable(false);
		  //loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  loginFrame.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
			     close();
			     
			   }
			  });
		  loginFrame.setVisible(true);
	  }
	  
	  
	  public void close() {
		  count=0;
		  if(socket==null)
			  System.exit(0);
		 try {
			 
			 toServer = new DataOutputStream(socket.getOutputStream());
			  toServer.writeUTF("exit");
			  exit=false;
			  
			  socket.close();
			System.exit(0);
		  }catch(Exception e1) {
				System.err.println("error");
	  }		 
	  }
	  
	 public class sendLoginDetails extends Thread{
		 String name;
		 String password;
		 
		 sendLoginDetails(String name,String password)
		  {
			 this.name=name;
			 this.password=password;
			 this.start();
		  }
		  public void run() {
				  try {
					   
				      toServer = new DataOutputStream(socket.getOutputStream());
				    }
				    catch (IOException ex) {
				      System.out.println(ex.toString() + '\n');
				    }
				  try {
				        
				        toServer.writeUTF(name);
				        toServer.writeUTF(password);
				        toServer.flush();
				      }
				      catch (IOException ex) {
				        System.err.println(ex);
				      }	
			  
		  }
	  }

	 
	 public class sendScoreDetails extends Thread{
		 String score;
		 String time;
		 
		 sendScoreDetails(String score,String time)
		  {
			 this.score=score;
			 this.time=time;
			 this.start();
		  }
		  public void run() {
				  try {
					   
				      toServer = new DataOutputStream(socket.getOutputStream());
				    }
				    catch (IOException ex) {
				      System.out.println(ex.toString() + '\n');
				    }
				  try {
				        
				        toServer.writeUTF(score);
				        toServer.writeUTF(time);
				        toServer.flush();
				      }
				      catch (IOException ex) {
				        System.err.println(ex);
				      }	
			  
		  }
	  }
	
	public void SnakeGame() {
		
		JFrame frame=new JFrame("Snake Game");
		frame.setBounds(10,10,905,700);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		panel=new GamePanel();
		panel.setBackground(Color.gray);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
			     close();}
			   public void windowIconified(WindowEvent evt) {  
				   panel.pause();
						   }
			   public void windowDeiconified(WindowEvent evt) {
				   panel.restart_pause();
			   }
			    
			  });
		
		frame.add(panel);
		frame.setVisible(true);
	}
public class listenScoreMessage extends Thread{
		
		listenScoreMessage()
		{
			this.start();
		}
		public void run() {
		synchronized (socket){
		while(exit) {
		try {
				fromServer = new DataInputStream(socket.getInputStream());
		}
		catch (IOException e){
			System.err.println(e);
		}
		try {
	    	highScore= fromServer.readUTF();
	    	bestTime=fromServer.readUTF();
	    	JFrame scoreFrame=new ScorePage(name,highScore,bestTime);
	    	scoreFrame.setLocationRelativeTo(null);
	    	scoreFrame.setResizable(false);
	    	scoreFrame.addWindowListener(new WindowAdapter() {
				   public void windowClosing(WindowEvent evt) {
				     close();
				   }
				
				  });
	    	scoreFrame.setVisible(true);
	    }
	    
	    catch(IOException ex) {
	    	System.out.println(ex.toString()+"\n");
	    }
		
	}}
	}
	}
	public static void main(String[] args) {
		Client_Snake c = new Client_Snake();

	}
	
	public class Login1 extends JFrame {

	    private JLabel nameLabel;
	    private JLabel PasswordLabel;
	    private JTextField nameField;
	    private JPasswordField passwordField;
	    private JButton button;
	    private JLabel okLabel;
	    boolean closeFrame=false;

	    private static final int FRAME_WIDTH = 500;
	    private static final int FRAME_HEIGHT = 200;
	    
	   public Login1()
	    {  
	       createNameField();
	       createButton();
	       createPanel();
	 
	       setSize(FRAME_WIDTH, FRAME_HEIGHT);
	    }
	 
	    public void createNameField()
	    {
	       nameLabel = new JLabel("Name         : ");
	       final int FIELD_WIDTH = 20;
	       nameField = new JTextField(FIELD_WIDTH);
	       nameField.setText("");
	       PasswordLabel = new JLabel("Password: ");
	       passwordField = new JPasswordField(FIELD_WIDTH);
	       passwordField.setText("");
	    }
	 
	    public void createButton()
	    {  
	       button = new JButton("OK");
	       button.addActionListener(e->{
	    	 name=nameField.getText();
	      	 char[] passwd=passwordField.getPassword();
	      	 password=new String(passwd);
	      	 new sendLoginDetails(name,password);
	      	dispose();
	       }
	       );
	    }
	 
	    public void createPanel()
	    {    	
	       JPanel panel = new JPanel();
	       
	       JPanel buttonPanel = new JPanel();
	       JPanel NamePanel = new JPanel();
	       NamePanel.add(nameLabel);
	       NamePanel.add(nameField);
	       JPanel PasswordPanel = new JPanel();
	       PasswordPanel.add(PasswordLabel);
	       PasswordPanel.add(passwordField);
	       buttonPanel.add(button);
	       add(buttonPanel, BorderLayout.SOUTH);
	       add(NamePanel, BorderLayout.NORTH);
	       add(PasswordPanel, BorderLayout.CENTER);
	       panel.add(NamePanel);
	       panel.add(PasswordPanel);
	       add(panel);
	    }
	    
	    public String getName() {
	    	return name;
	    }
	    
	    public String getPassword() {
	    	return password;
	    }  
	           
	        }
	
	
	public class GamePanel extends JPanel implements ActionListener, KeyListener{
		
		private int[] snakexlength=new int[750];
		private int[] snakeylength=new int[750];
		private int lengthofSnake=3;
		long StartTime;
		long EndTime;
		int count=1;
		
		
		private boolean left=false;
		private boolean right=true;
		private boolean up=false;
		private boolean down=false;
		
		private int score=0;
		private int tempscore=0;
		private boolean levelup=false;
		long time=0;
		long timenew=0;
		
		private ImageIcon snaketitle=new ImageIcon(getClass().getResource("snaketitle.jpg"));
		private ImageIcon leftmouth=new ImageIcon(getClass().getResource("leftmouth.png"));
		private ImageIcon rightmouth=new ImageIcon(getClass().getResource("rightmouth.png"));
		private ImageIcon upmouth=new ImageIcon(getClass().getResource("upmouth.png"));
		private ImageIcon downmouth=new ImageIcon(getClass().getResource("downmouth.png"));
		private ImageIcon snakeImage=new ImageIcon(getClass().getResource("snakeimage.png"));
		
		private int moves=0;
		boolean gameOver=false;
		private boolean win=false;
		
		private Timer timer;
		private int delay=200;
		private int level=1;
		
		private int[] enemyxpos= {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
		private int[] enemyypos= {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
		
		private ImageIcon enemyimage;
		
		private Random random=new Random();
		private int xpos=random.nextInt(34);
		private int ypos=random.nextInt(23);
		
		GamePanel(){
			
			addKeyListener(this);
			setFocusable(true);
			setFocusTraversalKeysEnabled(true);
			timer=new Timer(delay,this);
			timer.start();
			addWindowListener(new WindowAdapter() {
				  public void windowIconified(WindowEvent evt) {  
				     pause();
				   }
				  });
			
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.WHITE);
			g.drawRect(24,10,851,55);
			g.drawRect(24,74,851,576);
			snaketitle.paintIcon(this, g, 25, 11);
			g.setColor(Color.black);
			g.fillRect(25, 75, 850, 575);
			
			if(moves==0) {
				snakexlength[0]=100;
				snakexlength[1]=75;
				snakexlength[2]=50;
				
				snakeylength[0]=100;
				snakeylength[1]=100;
				snakeylength[2]=100;	
			}
			
			
			
			if(left) {
				leftmouth.paintIcon(this,g, snakexlength[0],snakeylength[0]);
				
			}
			if(right) {
				rightmouth.paintIcon(this,g, snakexlength[0],snakeylength[0]);
				
			}
			if(up) {
				upmouth.paintIcon(this,g, snakexlength[0],snakeylength[0]);
				
			}
			if(down) {
				downmouth.paintIcon(this,g, snakexlength[0],snakeylength[0]);
				
			}
			for(int i=1;i<lengthofSnake;i++) {
					snakeImage.paintIcon(this,g, snakexlength[i],snakeylength[i]);
				}
			
			enemyimage=new ImageIcon(getClass().getResource("enemy.png"));
			enemyimage.paintIcon(this, g, enemyxpos[xpos],enemyypos[ypos]);
			timeElapsed();
			
			if(gameOver) {
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial",Font.BOLD,50));
				g.drawString("GameOver",300,300);
				g.setFont(new Font("Arial",Font.PLAIN,20));
				g.drawString("Press SPACE to Restart",320,350);
				String score1=Integer.toString(score);
				String time1=Long.toString(time);
				
				if(count==1) {
					new sendScoreDetails(score1,time1);
					count++;
				}
				
			}
			
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial",Font.PLAIN,14));
			
			g.drawString("SCORE:"+score,750,30);
			g.drawString("LENGTH:"+lengthofSnake,750,50);
			g.drawString("LEVEL:"+level,50,50);
			g.drawString("Time(s):"+time,50,30);
			
			if(tempscore==2 || tempscore==4 || tempscore==6|| tempscore==8) {
				levelup=true;
				tempscore++;
			}
			
			if(score==15) {
				timer.stop();
				win=true;
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial",Font.BOLD,50));
				g.drawString("You Win!!!", 300,300);
				
				g.setFont(new Font("Arial",Font.PLAIN,30));
				g.drawString("Press SPACE to Restart",250,350);
				
				g.drawString("Score:"+score,350,400);
				if(count==1) {
					new sendScoreDetails(Integer.toString(score),Long.toString(time));
					count++;
			}
			}
				
			if(levelup) {
				timer.stop();
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial",Font.BOLD,50));
				g.drawString("Level:"+(level+1),300,300);
				
				g.setFont(new Font("Arial",Font.PLAIN,30));
				g.drawString("Press ENTER to Continue",250,350);
				if(level>=4)
					g.drawString("Don't hit the walls!!!",300,400);
				timer.setDelay(delay);
			}
			
			g.dispose();
				
			}

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i=lengthofSnake-1;i>0;i--) {
				
				snakexlength[i]=snakexlength[i-1];
				snakeylength[i]=snakeylength[i-1];
			}
			
			if(left) {
				snakexlength[0]=snakexlength[0]-25;
			}
			if(right) {
				snakexlength[0]=snakexlength[0]+25;
			}
			if(up) {
				snakeylength[0]=snakeylength[0]-25;
			}
			if(down) {
				snakeylength[0]=snakeylength[0]+25;
			}
			if(level<=4) {
			if(snakexlength[0]>850)
				snakexlength[0]=25;
			if(snakexlength[0]<25)
				snakexlength[0]=850;
			
			if(snakeylength[0]>625)
				snakeylength[0]=75;
			if(snakeylength[0]<75)
				snakeylength[0]=625;
			}
			
			collidesWithEnemy();
			collidesWithBody();
			if(level>=5)
				collidesWithWall();
			
			repaint();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyCode()==KeyEvent.VK_ENTER && levelup) {
				level++;
				newLevel();
			}
			
			if(e.getKeyCode()==KeyEvent.VK_SPACE && (gameOver||win) ) {
				
				restart();
			}
			
			if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)) {
				left=true;
				right=false;
				up=false;
				down=false;
				if(moves==0 || levelup || gameOver || win) {
					StartTime=System.currentTimeMillis();
					timenew=time;
					//timeElapsed();
				}
				moves++;
				//timeElapsed();
				
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)) {
				left=false;
				right=false;
				up=false;
				down=true;
				if(moves==0 || levelup || gameOver || win) {
					StartTime=System.currentTimeMillis();
					timenew=time;
					//timeElapsed();
			}
				moves++;
				//timeElapsed();
				
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)) {
				left=false;
				right=true;
				up=false;
				down=false;
				if(moves==0 ||levelup || gameOver || win) {
					StartTime=System.currentTimeMillis();
					timenew=time;
					//timeElapsed();
				}
				moves++;
				//timeElapsed();
			}
			if(e.getKeyCode()==KeyEvent.VK_UP && (!down)) {
				left=false;
				right=false;
				up=true;
				down=false;
				if(moves==0 || levelup || gameOver || win) {
					StartTime=System.currentTimeMillis();
					timenew=time;
					//timeElapsed();
				}
				moves++; 
				//timeElapsed();
				
			}
			
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		private void newEnemy() {
			xpos=random.nextInt(34);
			ypos=random.nextInt(23);
			for(int i=lengthofSnake-1;i>0;i--) {
				if(snakexlength[i]==enemyxpos[xpos] && snakeylength[i]==enemyypos[ypos])
					newEnemy();
			}
			
		}
		private void collidesWithEnemy() {
			if(enemyxpos[xpos]==snakexlength[0] && enemyypos[ypos]==snakeylength[0])
			{
				newEnemy();
				lengthofSnake++;
				score++;
				tempscore=score;
			}	
		}
		
		private void collidesWithBody() {
			for(int i=lengthofSnake-1;i>0;i--) {
				if(snakexlength[0]==snakexlength[i] && snakeylength[0]==snakeylength[i])
				{
					timer.stop();
					gameOver=true;
				}
		}
		}
		private void restart(){
			gameOver=false;
			moves=0;
			score=0;
			tempscore=0;
			lengthofSnake=3;
			left=false;
			up=false;
			right=true;
			down=false;
			level=1;
			time=timenew=0;
			win=false;
			delay=200;
			timer.start();
			repaint();
			
		}
		
		private void pause(){
			timer.stop();
			repaint();
			
		}
		private void restart_pause() {
			if(moves!=0) {
			StartTime=System.currentTimeMillis();
			timenew=time;
			}
			timer.start();
		
		}
		
		private void newLevel(){
			levelup=false;
			moves=0;
			lengthofSnake=3;
			left=false;
			up=false;
			right=true;
			down=false;
				
			snakexlength[lengthofSnake]=snakexlength[lengthofSnake-1];
			snakeylength[lengthofSnake]=snakeylength[lengthofSnake-1];
				
			delay=delay-25;
			timer.setDelay(delay);
			timer.start();
			repaint();
			
		}
		private void timeElapsed() {
			if( moves!=0) 
			EndTime=System.currentTimeMillis();
			time=timenew+(EndTime-StartTime)/1000;
			}
		
		private void collidesWithWall() {
			
			if(snakexlength[0]>850 || snakexlength[0]<25 ||snakeylength[0]>625 || snakeylength[0]<75) {
				snakexlength[0]=snakexlength[1];
				snakeylength[0]=snakeylength[1];
				timer.stop();
				gameOver=true;
		
		}
		}
		public int getScore() {
			return score;
		}
		public long getTime() {
			return time;
		}
		
		
	}
	
	
	public class ScorePage extends JFrame {

	    private JLabel scoreLabel;
	    private JLabel timeLabel;
	    private JButton button;
	    private JLabel okLabel;
	    String name;
	    boolean closeFrame=false;
	    String score;
	    String time;
	    
	    

	    private static final int FRAME_WIDTH = 500;
	    private static final int FRAME_HEIGHT = 200;
	    
	   public ScorePage(String name,String score,String time)
	    {  
	       this.name=name;
	       this.score=score;
	       this.time=time;
		   createScoreField();
	       createButton();
	       createPanel();
	 
	       setSize(FRAME_WIDTH, FRAME_HEIGHT);
	    }
	 
	    public void createScoreField()
	    {
	     
	    	scoreLabel = new JLabel("Hii "+name+"!!!"+ " Your highest score is : "+ score+ ".\n"+" You achieved it in time: "+time+ "s");
	    	  scoreLabel.setFont(new Font("Arial",Font.BOLD,15));
	    }
	 
	    public void createButton()
	    {  
	       button = new JButton("OK");
	       button.addActionListener(e->{
	      	 dispose();
	       }
	       );
	    }
	 
	    public void createPanel()
	    {    	
	       JPanel panel = new JPanel();
	       
	       JPanel buttonPanel = new JPanel();
	       JPanel ScorePanel = new JPanel();
	       ScorePanel.add(scoreLabel);
	       
	       buttonPanel.add(button);
	       add(buttonPanel, BorderLayout.SOUTH);
	       add(ScorePanel);
	      
	       panel.add(ScorePanel);
	       
	       add(panel);
	       panel.setBackground(Color.BLACK);
	    }
	}
	}

