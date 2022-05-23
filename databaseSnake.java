

import java.sql.*;

public class databaseSnake {
	
	String name;
	String password;
	int[] arr=new int[2];
	
		public int[] scoreSnake(String name, String password) 
		{
		
		PreparedStatement preparedStatement;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection
				      ("jdbc:sqlite:ScoreRecord.db");
		
			System.out.println("Database connected"); 
			
			String queryString = "select * from Player where Name = ? and Password=?";
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			
			
			ResultSet rset = preparedStatement.executeQuery();
			
			System.out.println("Results:");
			int max=rset.getInt(3);
			int maxTime=rset.getInt(4);
			rset.next();
			while (rset.next() ) {
				if(rset.getInt("Score")>max) {
					max=rset.getInt("Score");
					maxTime=rset.getInt("Time");
				}
				else if(rset.getInt("Score")==max) {
					{
						if(rset.getInt("Time")< maxTime) {
							max=rset.getInt("Score");
							maxTime=rset.getInt("Time");
						}
					}
			
			}
				continue;
			}
			System.out.println(max);
			System.out.println(maxTime);
			arr[0]=max;
			arr[1]=maxTime;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return arr;
		
	}
		
		public void storeNamePass(String name, String password) {
			Connection connection = null;
			try {
				connection = DriverManager.getConnection
					      ("jdbc:sqlite:ScoreRecord.db");
			
			System.out.println("Database connected");
	        String query = "INSERT INTO Player(Name,Password)"+ "VALUES(?,?)";
	 
	        PreparedStatement myStmt= connection.prepareStatement(query);
	 
	        myStmt.setString(1, name);
	        myStmt.setString(2, password);
	        int res = myStmt.executeUpdate();
	        
	        System.out.println(res + " records inserted");
	 
	        connection.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		}
		
		public void storeScore(String name, String Password, int score, int time) {
			
			Connection connection = null;
			try {
				connection = DriverManager.getConnection
					      ("jdbc:sqlite:ScoreRecord.db");
			
			System.out.println("Database connected");
			
			
	        String query = "UPDATE Player Set Score=?,Time=? where Name=? and Password=? and Score IS NULL and Time IS NULL";
	 
	        PreparedStatement myStmt= connection.prepareStatement(query);
	        
	        myStmt.setInt(1, score);
	        myStmt.setInt(2, time);
	        myStmt.setString(3, name);
	        myStmt.setString(4, Password);
	        //myStmt.setString(5, "IS NULL");
	        //myStmt.setString(6, "IS NULL");
	        int res = myStmt.executeUpdate();
	        
	        System.out.println(res + " records inserted");
	 
	        connection.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		}
}



