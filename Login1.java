

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login1 extends JFrame {

    private JLabel nameLabel;
    private JLabel PasswordLabel;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton button;
    private JLabel okLabel;
    String name;
    String passwd;
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
      	 char[] password=passwordField.getPassword();
      	 passwd=new String(password);
      	 System.out.println(name+passwd);
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
    	return passwd;
    }
    
    public static void main(String[] args)
        {  
           JFrame frame = new JFrame("Snake");
           frame=new Login1();
           frame.setLocationRelativeTo(null);
           frame.setResizable(false);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setVisible(true);  
           
        }
}
