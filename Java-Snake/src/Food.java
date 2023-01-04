import java.util.Random;
//import dependency

public class Food 
{

    public int posX; //X coord of Food
    public int posY; //Y coord of Food
    public int value; //length added to snake when ate

    public Food() 
    {
        posX = generatePos(Graphics.WIDTH); //generate a random X coord
        posY = generatePos(Graphics.HEIGHT); //generate a random Y coord
    }

    private int generatePos(int size) 
    {
        Random random = new Random(); //create a new Random object
        return random.nextInt(size / Graphics.SQUARE_SIZE) * Graphics.SQUARE_SIZE; //return a random int within the window parameters
    }

    public int getPosX() 
    {
        return posX; //return the X coord of the Food object
    }

    public int getPosY() 
    {
        return posY; //return the Y coord of the Food object
    }
    
    public int getValue() 
    {
        return value; //return the eat value of the Food object
    }
}

