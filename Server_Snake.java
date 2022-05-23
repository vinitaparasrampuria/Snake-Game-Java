



import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.*;



public class Server_Snake extends JFrame implements Runnable {

	private int clientNo=0;
	JLabel label;
	JPanel p;
	private List<HandleAClient> clients=Collections.synchronizedList(new ArrayList<HandleAClient>());
	ServerSocket serverSocket;
	boolean exit=true;
	  
	
	public Server_Snake() {
		super("Snake Server");
		this.setSize(500, 150);
		this.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
			     close();
			   }
			  });
		this.setVisible(true);
		Thread t = new Thread(this);
		  t.start();
		
	}
	

	public static void main(String[] args) {
		new Server_Snake();
	}

	@Override
	public void run() {
		try {
	        serverSocket = new ServerSocket(9000);
	        System.out.println("Game server started at " + new Date() + '\n'); 
	        label= new JLabel("Game server started at " + new Date() + '\n');
	        p=new JPanel();
	        p.add(label);
	        add(p);
	        setVisible(true);
	        while (true) {
	          Socket socket = serverSocket.accept();
	          clientNo++;
	          HandleAClient newClient = new HandleAClient(socket, clientNo);
              clients.add(newClient);
	          
              System.out.println("Starting thread for client " + clientNo +
	              " at " + new Date() + '\n');

	            InetAddress inetAddress = socket.getInetAddress();
	            System.out.println("Client " + clientNo + "'s host name is "
	              + inetAddress.getHostName() + "\n");
	            System.out.println("Client " + clientNo + "'s IP Address is "
	              + inetAddress.getHostAddress() + "\n");
	          
	          new Thread(new HandleAClient(socket, clientNo)).start();
	        }
	        }
		
	      catch(IOException ex) {
	        System.err.println(ex);
	      }
		    
	  }
	
	public void close() {
		try {
		serverSocket.close();
	}catch(Exception e) {
		System.out.println(e.toString()+"\n");
	}
	}
		
	class HandleAClient implements Runnable {
	    private Socket socket; // A connected socket
	    private int clientNum;
	    String str;
	    int count=1;
	    String name;
	    String password;
	    int score;
	    int time;
	    
	    public HandleAClient(Socket socket, int clientNum) {
	      this.socket = socket;
	      this.clientNum = clientNum;
	    }

	    public void run() {
	      try {
	        DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
	          while (true) {  
	          str = inputFromClient.readUTF();
	          System.out.println(str);
	          if(str.equals("exit")) 
	          {
	        System.out.println("Closing connection for client " + this.clientNum + " " +
		              str + '\n');
	          
	  			removeUser(this);
	         break;
	          }  
	          if(count==1) {
	        	  name=str;
	          System.out.println("Name received from client: " + this.clientNum + ": " +
	              name + '\n');
	          
	          count++;
	          
	        } 
	          else if(count==2) {
	        	  
	          password=str;
	        	  System.out.println("Password received from client: " + this.clientNum + ": " +
	    	              str + '\n');
	    	          count++;
	    	          databaseSnake sn=new databaseSnake();
	    	          sn.storeNamePass(name,password);
	        	  
	          }
	          else if(count==3) {
	        	  score=Integer.parseInt(str);
	        	  System.out.println("Score received from client: " + this.clientNum + ": " +
	    	              str + '\n');
	    	          count++;
	        	  
	          }
	          else if(count==4) {
	        	  time=Integer.parseInt(str);
	        	  System.out.println("Time received from client: " + this.clientNum + ": " +
	    	              str + '\n'); 
	    	      new SendMessage(clients, clientNum, name, password, score, time);
	        	  
	          }   
	      }
	      }
	      catch(IOException ex) {
	        ex.printStackTrace();
	      }
	    }
	    
	    public void removeUser(HandleAClient c) {
	    	
	    	clients.remove(c);
	    	System.out.println("Quit");
	    	try{
	    		this.socket.close();
	    	}catch(Exception e){
	    		System.out.println(e);
	    		
	    	}
	    }
	    
	 @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(clientNum, socket);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			HandleAClient other = (HandleAClient) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return clientNum == other.clientNum && Objects.equals(socket, other.socket);
		}
		private Server_Snake getEnclosingInstance() {
			return Server_Snake.this;
		}
	}



	public class SendMessage extends Thread{
	        protected List<HandleAClient> clients;
	        String str;
	        int clientNum;
	        String name;
	        String password;
	        int score;
	        int time;

	        public SendMessage(List<HandleAClient> clients, int clientNum, String name, String password, int score, int time) {
	            this.clients = clients;
	            this.clientNum=clientNum;
	            this.name=name;
	            this.password=password;
	            this.score=score;
	            this.time=time;
	            this.start();
	        }

	        public void run() {
	            try {
	             for (HandleAClient client : clients) {
	            	 if(client.clientNum==clientNum)
	            	 {
	            	 Socket s=client.socket;
	            	 if (s!=null){
	            	 DataOutputStream outputToClient = new DataOutputStream(s.getOutputStream());
	            	 databaseSnake sn=new databaseSnake();
	            	 sn.storeScore(name,password,score,time);
		        	 sn.scoreSnake(name,password);
	            	 outputToClient.writeUTF(Integer.toString(sn.arr[0]));
	            	 outputToClient.writeUTF(Integer.toString(sn.arr[1]));
	            	 
	                  }
	            	 }
	             }
	                }catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	}
	
	}



