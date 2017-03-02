package buscaminas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class puntaje {

    BufferedReader tec;
    FileWriter f;
    File g;
    int p1, p2, p3;
    String prin, inte, expe;
    boolean lectura = true;

    public puntaje() throws FileNotFoundException, IOException {
        g = new File("C:\\puntajes_buscaminas");
        if (!g.exists()) {
            g.createNewFile();
        }
        leer();
    }

    public void leer() throws IOException {
        tec = new BufferedReader(new FileReader("C:\\puntajes_buscaminas"));
        tec.readLine();
        try {
            String l1[];
            l1 = tec.readLine().split(" ");
            prin = l1[0];
            p1 = Integer.parseInt(l1[1]);
            l1 = tec.readLine().split(" ");
            inte = l1[0];
            p2 = Integer.parseInt(l1[1]);
            l1 = tec.readLine().split(" ");
            expe = l1[0];
            p3 = Integer.parseInt(l1[1]);
        } catch (Exception e) {
            f = new FileWriter(g);
            f.write("puntajes:\r\n");
            f.append("nnn 999\r\nnnn 999\r\nnnn 999\r\n");
            f.close();
            leer();
        }
    }

    public void mostrar() {
        String m = "principiante: " + prin + " , " + p1;
        m = m + "\nintermedio: " + inte + " , " + p2 + "\nexperto: " + expe + " , " + p3;

        JOptionPane.showMessageDialog(null, m, "puntajes", 1);
    }

    public void agregar(String niv, int t) throws IOException {
        f = new FileWriter(g);
        f.write("puntajes:\r\n");
        if (niv.equals("principiante") && t < p1) {
            f.write(JOptionPane.showInputDialog("puntaje alto!!!\ninserte su nombre:") + " " + t + "\r\n");
        } else {
            f.write(prin + " " + p1 + "\r\n");
        }
        if (niv.equals("intermedio") && t < p2) {
            f.write(JOptionPane.showInputDialog("puntaje alto!!!\ninserte su nombre:") + " " + t + "\r\n");
        } else {
            f.write(inte + " " + p2 + "\r\n");
        }
        if (niv.equals("experto") && t < p3) {
            f.write(JOptionPane.showInputDialog("puntaje alto!!!\ninserte su nombre:") + " " + t + "\r\n");
        } else {
            f.write(expe + " " + p3 + "\r\n");
        }
        f.close();
        leer();
    }
}
