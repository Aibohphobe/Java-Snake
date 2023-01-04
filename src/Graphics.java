import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
//import dependencies

public class Graphics extends JPanel implements ActionListener 
{

    static final int WIDTH = 1200; //screen width
    static final int HEIGHT = 600; //screen height
    static final int SQUARE_SIZE = 50; // square size
    static final int BOARD_SIZE = (WIDTH * HEIGHT) / (SQUARE_SIZE * SQUARE_SIZE); //total size of the board

    final Font font = new Font("TimesRoman", Font.BOLD, 30); //universal font definition

    int[] snakePosX = new int[BOARD_SIZE]; //initializes starting X coord for Snake
    int[] snakePosY = new int[BOARD_SIZE]; //initializes starting Y coord for Snake
    int snakeLength; //initializes length variable

    Food food; //initializes Food object
    int foodEaten; //initializes variable to track how much Food has been eaten
    public boolean startTheme = true; //initializes variable to play theme when TRUE

    char direction = 'R'; //initializes Snake starting direction when the game starts
    boolean isMoving = false; //initializes Snake movement variable
    final Timer timer = new Timer(100, this); //initializes foundational screen update time through use of a Timer object
    

    public Graphics() 
    {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); //set window size based on WIDTH and HEIGHT variables
        this.setBackground(Color.BLACK); //set background color
        this.setFocusable(true); //set the ability to target the window
        this.addKeyListener(new KeyAdapter() //listen to keystrokes for arrow key inputs
        {
            @Override
            public void keyPressed(KeyEvent e) 
            {
                if (isMoving) //Is player alive? check
                {
                    switch (e.getKeyCode()) //get last keystroke
                    {
                        case KeyEvent.VK_LEFT -> //if last keystroke was Left arrow
                        {
                            if (direction != 'R') //make sure Snake cannot turn into itself
                            {
                                direction = 'L'; //change Snake direction to Left
                            }
                        }
                        case KeyEvent.VK_RIGHT -> //if last keystroke was Right arrow
                        {
                            if (direction != 'L') //make sure Snake cannot turn into itself
                            {
                                direction = 'R'; //change Snake direction to Right
                            }
                        }
                        case KeyEvent.VK_UP -> //if last keystroke was Up arrow
                        {
                            if (direction != 'D') //make sure Snake cannot turn into itself
                            {
                                direction = 'U'; //change Snake direction to Up
                            }
                        }
                        case KeyEvent.VK_DOWN -> //if last keystroke was Down arrow
                        {
                            if (direction != 'U') //make sure Snake cannot turn into itself
                            {
                                direction = 'D'; //change Snake direction to Down
                            }
                        }
                    }
                } 
                else 
                {
                    startTheme = false; //make sure Theme does not play again when Game restarts
                    start(); //restart the Game
                }
            }
        });

        start(); //start the game for the first time
    }

    protected void start() 
    {
        snakePosX = new int[BOARD_SIZE]; //initializes starting X coord for Snake 
        snakePosY = new int[BOARD_SIZE]; //initializes starting Y coord for Snake
        snakeLength = 5; //sets starting Snake length to 5
        foodEaten = 0; //sets starting score to 0
        direction = 'R'; //sets starting direction to Right
        isMoving = true; //sets Alive check to true
        if (startTheme == true) //only play the Theme upon first start
        {
            new playSound("theme"); //play theme.wav
        }
        spawnFood(); //spawn a random food
        timer.start(); //start the screen update timer
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) 
    {
        super.paintComponent(g); //start drawing

        if (isMoving) //Is player alive? check
        {
            if (food instanceof Apple) //Is the current Food an Apple?
            {
                g.setColor(Color.RED); //set brush color to RED
            }
            else if (food instanceof Orange) //Is the current Food an Orange?
            {
                g.setColor(Color.ORANGE); //set brush color to ORANGE
            }
            else if (food instanceof Melon) //Is the current Food a Melon?
            {
                g.setColor(Color.MAGENTA); //set brush color to MAGENTA
            }
            g.fillOval(food.getPosX(), food.getPosY(), SQUARE_SIZE, SQUARE_SIZE); //fill the Food oval with the brush color

            for (int i = 0; i < snakeLength; i++) //iterate over the current snakeLength
            {
                if (i % 2 == 0) //if the current square is stored in an even index
                {
                    g.setColor(Color.GREEN); //set brush color to GREEN
                    g.fillRect(snakePosX[i], snakePosY[i], SQUARE_SIZE, SQUARE_SIZE); //fill the Snake square with the brush color
                }
                else 
                {
                    g.setColor(new Color(39, 138, 44)); //set brush color to DARK_GREEN
                    g.fillRect(snakePosX[i], snakePosY[i], SQUARE_SIZE, SQUARE_SIZE); //fill the Snake square with the brush color
                }
            } 
        } 
        else 
        {
            String scoreText = String.format("YOU DIED. Score: %d... Press any key to play again!", foodEaten); //death message initialization
            g.setColor(Color.WHITE); //set font color
            g.setFont(font); //set font to universal font
            g.drawString(scoreText, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText)) / 2, HEIGHT / 2); //put String on screen, centered
        }
    }

    protected void move() 
    {
        for (int i = snakeLength; i > 0; i--) //iterate over snake length in reverse order until 0
        {
            snakePosX[i] = snakePosX[i - 1]; //set X coord to the previous index's X coord
            snakePosY[i] = snakePosY[i - 1]; //set Y coord to previous index's Y coord
        }

        switch (direction) //get direction
        {
            case 'U' -> snakePosY[0] -= SQUARE_SIZE; //if user pressed Up arrow, decrease the Snake head's Y coord by 1 square size
            case 'D' -> snakePosY[0] += SQUARE_SIZE; //if user pressed Down arrow, increase the Snake head's Y coord by 1 square size.
            case 'L' -> snakePosX[0] -= SQUARE_SIZE; //if user pressed Left arrow, decrease the Snake head's X coord by 1 square size.
            case 'R' -> snakePosX[0] += SQUARE_SIZE; //if user pressed Right arrow, increase the Snake head's X coord by 1 square size.
        }
    }

    protected void spawnFood() 
    {
        Random r = new Random(); //create a new Random object
        int max = 11; //create a max ceiling for random numbers
        int randomNum = r.nextInt(max); //generate a random number between 0 and the ceiling
        food = switch (randomNum) //generate Food type based on random number output and replace the old one
        {
            case 0 -> new Melon(); // 1/10 chance for a Melon object
            case 1, 2 -> new Orange(); // 2/10 chance for an Orange object
            default -> new Apple(); // 7/10 chance for an Apple object
        };
    }
    
    protected void eatFood() 
    {
        if ((snakePosX[0] == food.getPosX()) && (snakePosY[0] == food.getPosY())) //if Snake head and Food overlap
        {
            snakeLength += food.getValue(); //increase snake length by Food's value
            foodEaten += food.getValue(); //increase score by Food's value
            new playSound("munch"); //play munch.wav
            spawnFood(); //create a new Food object to replace the eat
        }
    }

    protected void collisionTest() 
    {
        for (int i = snakeLength; i > 0; i--)  //iterate over snake length in reverse order until 0
        {
            if ((snakePosX[0] == snakePosX[i]) && (snakePosY[0] == snakePosY[i])) //if Snake head and the current body segment overlap
            {
                isMoving = false; //player has died
                new playSound("death"); //play death.wav
                break; //break from for loop as rest of collision checks do not matter
            }
        }

        if (snakePosX[0] < 0 || snakePosX[0] > WIDTH - SQUARE_SIZE || snakePosY[0] < 0 || snakePosY[0] > HEIGHT - SQUARE_SIZE) // if Snake head is out of bounds
        {
            isMoving = false; //player had died
            new playSound("death"); //play death.wav
        }

        if (!isMoving) //if player has passed above checks,
        {
            timer.stop(); //stop screen update timer
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (isMoving) //if player is alive
        {
            move(); //move the Snake
            collisionTest(); //check to see if player has died
            eatFood(); //check to see if player has eaten a Food
        }

        repaint(); //repaint the screen
    }
}