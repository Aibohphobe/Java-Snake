import java.io.*;
import javax.sound.sampled.*;
//import dependencies

public class playSound 
{
    public playSound(String sound) 
    {   
        if ("munch".equals(sound)) //if passed parameter is munch, play munch.wav
        {
            try 
            {
                File file = new File(".//res//munch.wav");
                AudioInputStream s = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(s);
                clip.setFramePosition(0);
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(-25); //turn volume down
                clip.start();
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) //catch all possible exceptions
            {}
        }
        else if ("theme".equals(sound)) //if passed parameter is theme, play theme.wav
        {
            try 
            {
                File file = new File(".//res//theme.wav");
                AudioInputStream s = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(s);
                clip.setFramePosition(0);
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(-25); //turn volume down
                clip.start();
                clip.loop(100); //loop 100 times, effectively playing the theme indefinitely
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) //catch all possible exceptions
            {}
        }
        else if ("death".equals(sound)) //if passed parameter is death, play death.wav
        {
            try 
            {
                File file = new File(".//res//death.wav");
                AudioInputStream s = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(s);
                clip.setFramePosition(0);
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(-25); //turn volume down
                clip.start();
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) //catch all possible exceptions
            {}
        }
    } 
}