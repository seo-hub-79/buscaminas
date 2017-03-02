package buscaminas;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class cronometro extends Thread implements Runnable {

    JLabel r;

    public cronometro(JLabel r2) {
        r = r2;
    }

    public void run() {
        try {
            for (int j = 0; j < 1000; j++) {
                r.setText("" + j);
                Thread.sleep(1000);
            }
            r.setText("Infinito");
        } catch (InterruptedException ex) {
            Logger.getLogger(cronometro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
