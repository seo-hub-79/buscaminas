package buscaminas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class puntaje {

    FileWriter f;
    File g;
    int p1, p2, p3;
    String prin, inte, expe, ruta;

    public puntaje() throws FileNotFoundException, IOException {

        g = new File(System.getProperty("user.dir") + "/info");
        System.out.println(g.getPath());
        ruta = g.getPath() + "\\puntajes";
        if (!g.exists()) {
            g.mkdirs();
            reset();
        }

        BufferedReader tec = new BufferedReader(new FileReader(ruta));
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
        }
    }

    public void reset() throws IOException {
        f = new FileWriter(ruta);
        f.write("puntajes:\r\n");
        f.append("ninguno 999\r\nninguno 999\r\nninguno 999\r\n");
        f.close();

    }

    public void mostrar() {
        String m = "principiante: " + prin + " , " + p1;
        m = m + "\nintermedio: " + inte + " , " + p2 + "\nexperto: " + expe + " , " + p3;
        JOptionPane.showMessageDialog(null, m, "puntajes", 1);
    }

    public void agregar(String niv, int t) throws IOException {
        f = new FileWriter(ruta);
        f.write("puntajes:\r\n");
        if (niv.equals("principiante") && t < p1) {
            prin = JOptionPane.showInputDialog("puntaje alto!!!\ninserte su nombre:");
            p1 = t;
            if (prin == null) {
                prin = "NN";
            }
            f.write(prin + " " + t + "\r\n");
        } else {
            f.write(prin + " " + p1 + "\r\n");
        }
        if (niv.equals("intermedio") && t < p2) {
            inte = JOptionPane.showInputDialog("puntaje alto!!!\ninserte su nombre:");
            p2 = t;
            if (inte == null) {
                inte = "NN";
            }
            f.write(inte + " " + t + "\r\n");
        } else {
            f.write(inte + " " + p2 + "\r\n");
        }
        if (niv.equals("experto") && t < p3) {
            expe = JOptionPane.showInputDialog("puntaje alto!!!\ninserte su nombre:");
            p3 = t;
            if (expe == null) {
                expe = "NN";
            }
            f.write(expe + " " + t + "\r\n");
        } else {
            f.write(expe + " " + p3 + "\r\n");
        }
        f.close();
    }
}
