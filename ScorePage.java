

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


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
    
    public static void main(String[] args)
        {  
           JFrame frame = new JFrame("Score");
           frame=new ScorePage("Rob","5","10");
           frame.setLocationRelativeTo(null);
           frame.setResizable(false);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setVisible(true);      
        }
}
