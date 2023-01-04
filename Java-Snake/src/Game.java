import javax.swing.*;
//import dependency

public class Game extends JFrame 
{
    public Game() 
    {
        this.add(new Graphics()); //add a new graphics object
        this.setTitle("Snake Game"); //title the window "Snake Game"
        this.pack(); //make sure everything fits in the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close operation definition
        this.setResizable(false); //window size cannot be changed
        this.setVisible(true); //window is visible
        this.setLocationRelativeTo(null); //location is not relative to any position
    }
}