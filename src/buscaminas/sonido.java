package buscaminas;

import java.applet.AudioClip;
import java.io.InputStream;

public class sonido{

    String audio = "/sonidos/sonido.wav";
    String aplauso = "/sonidos/aplauso.wav";
    String explosion = "/sonidos/explosion_mina.wav";
    InputStream in;
    AudioClip a;

    public sonido(){
    }
    
    public void inicio(){
        a = java.applet.Applet.newAudioClip(getClass().getResource(audio));
        a.play();
    }
    
    public void gana(){
        a = java.applet.Applet.newAudioClip(getClass().getResource(aplauso));
        a.play();
    }
    
    public void pierde(){
        a = java.applet.Applet.newAudioClip(getClass().getResource(explosion));
        a.play();
    }
}
