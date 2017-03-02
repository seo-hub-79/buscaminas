package buscaminas;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class grillas {

    JPanel tablero;

    public grillas(int tx, int ty) {
        tablero = new JPanel();
        tablero.setSize(375, 375);
        tablero.setLocation(10, 95);
        tablero.setBackground(Color.YELLOW);
    }

    public void crear(int x, int y) {
        tablero.setLayout(null);
        tablero.setLayout(new GridLayout(x, y));
    }
}
