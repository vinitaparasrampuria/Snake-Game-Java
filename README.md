# Snake-Game-Java
I have made a snake game with following features:

a.	GUI:
This is a graphics and animation-oriented game that takes input from a keyboard to move a “snake” that eats a target and lengthens with each item it eats. The goal is for the snake to avoid eating itself even as it gets longer and longer. 
The GUI has following pages:
1.	Login Page: To enter the name and password. There is no password verification. This data will be stored in a database to keep track of people who played the game.
 
2.	Game page: It has a snake and a target to eat. On the top the current score, level, length of the snake and time taken to play the game is displayed. It has multiple levels, from level 1 to 4 the speed of snake increases, transition from each level takes place when a player eats 10 targets (kept this score as 2 for testing purpose). In level 5 the speed increases. Additionally, the player must prevent the snake from colliding with the wall otherwise he will lose. As soon as a level is achieved, level up is displayed, and player is asked to press “ENTER” button to continue. The timer stops when moving from one level to another or upon minimizing the window. Upon reaching a score of 150 (I have kept this score as 15 for testing purpose), the player wins and his best score along with time is displayed. An option to restart the game by hitting the “SPACEBAR” is given to the player when he wins.
The player loses if he eats itself in any of the levels. Game Over is shown with an option to restart the game.
3.	Score Page: It is displayed when a player loses or wins the game. It shows the player’s best score and the time taken to achieve that score.


b.	Networking: A server is created to accept all the connections and store the record of players. At the end of a game when a player either wins or loses, the server sends the highest score and time to the client’s score page for display. If there are more than one records of the same player with highest score, conflict is resolved by checking the time elapsed parameter. The highest score achieved in minimum time is displayed.
Client is created to send request to server and play the game. Multiple players can play the game simultaneously.
c.	Database: A database is maintained at the server side for recording the highest score. The database has four columns: Name, Password, Score and Time. Whenever a person login, his name and password are added to the database. As the player loses or wins the game his score and time are updated. These fields are not updated if the player closes the game in between.


Procedure to run the game:
1.	Start the server by clicking: Server_Snake.java
 

2.	Start the client by clicking: Client_snake.java. Enter your name and password. Click ok. You can now play the game by pressing the arrow keys. Click on the ‘X’ button to close the game.
