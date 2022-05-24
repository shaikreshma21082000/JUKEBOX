import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;

public class AudioPlayer{
    public static void playMusic(String link){
        Scanner sc=new Scanner(System.in);
        int response;
        String status;
        long currentFrame=0;
        try{
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(link));
            Clip clip =AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            status="PLAYING";
            System.out.print("\n\nENTER CHOICE:");
            do{
                System.out.println("1-PAUSE 2-RESUME 3-RESTART 4-STOP ");
                response=sc.nextInt();
                switch(response){
                    case 1:
                        if(status.equalsIgnoreCase("PAUSED"))
                        {
                            System.out.println("SONG IS ALREADY PAUSED");
                            break;
                        }else{
                            currentFrame= clip.getMicrosecondPosition();
                            clip.stop();
                            status="PAUSED";
                            break;
                        }
                    case 2:
                        if(status.equalsIgnoreCase("PLAYING"))
                        {
                            System.out.println("AUDIO IS ALREADY PLAYING.");
                            break;
                        }else{
                            clip.setMicrosecondPosition(currentFrame);
                            clip.start();
                            status="PLAYING";
                            break;
                        }

                    case 3:clip.setMicrosecondPosition(0);
                        status="PLAYING";
                        break;
                    case 4:clip.stop();
                        break;

                }

            }while(!(response==4));

        }catch (Exception e){e.printStackTrace();}
    }
}