package buscaminas;

//import UpperEssential.UpperEssentialLookAndFeel;
//import UpperEssential.UpperTheme;
//import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ventana extends JFrame implements ActionListener, KeyListener, MouseListener {

    botones[][] b;
    JButton carita = new JButton();
    JLabel tiempo = new JLabel();
    JLabel minas = new JLabel("10");

    JMenuBar barra = new JMenuBar();

    JMenuItem about = new JMenuItem("acerca de");
    JMenuItem instrucciones = new JMenuItem("como jugar");
    JMenuItem salir = new JMenuItem("salir");
    JMenuItem nuevo = new JMenuItem("nuevo (f2)");
    JMenuItem reiniciar = new JMenuItem("reiniciar");
    JMenuItem puntajes = new JMenuItem("puntajes");
    JMenuItem principiante = new JMenuItem("principiante"); //9 *9
    JMenuItem intermedio = new JMenuItem("intermedio"); //16 * 16
    JMenuItem experto = new JMenuItem("experto");   //30 * 30
    JMenuItem personalizado = new JMenuItem("personalizado");
    JRadioButtonMenuItem act_sonido = new JRadioButtonMenuItem("sonido");

    JMenu nivel = new JMenu("nivel");
    JMenu file = new JMenu("juego");
    JMenu aiuda = new JMenu("ayuda");

    cronometro c = new cronometro(tiempo);
    grillas gri = new grillas(375, 375);
    String n = "principiante";
    int x = 9, y = 9, can_minas = 10;
    ArrayList<Integer> pos_minas = new ArrayList<>();
    Icon pre;
    sonido son = new sonido();
    boolean fin, ya_gano;
    puntaje pun = null;
    //UpperEssentialLookAndFeel look = new UpperEssentialLookAndFeel();

    int n1[] = {1, 1, 0, -1, -1, -1, 0, 1};
    int n2[] = {0, 1, 1, 1, 0, -1, -1, -1};

    int n3[] = {1, 0, -1, -1, 0, 0, 1, 1};
    int n4[] = {0, 1, 0, 0, -1, -1, 0, 0};

    public boolean isFocusable() {
        return true;
    }

    void fin_juego() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (b[i][j].usado) {
                    continue;
                }
                if (b[i][j].mina) {
                    if (b[i][j].flag) {
                        b[i][j].cambiarimagen("/imagenes/mina_2.png");
                    } else {
                        b[i][j].cambiarimagen("/imagenes/mina123.png");
                    }
                    b[i][j].setBorderPainted(false);
                    b[i][j].setContentAreaFilled(false);
                    continue;
                }
                if (b[i][j].minas_adyacentes > 0) {
                    cambiar(i, j);
                    continue;
                }
                b[i][j].setVisible(false);
                b[i][j].setEnabled(false);
            }
        }
        if (!fin) {
            if (act_sonido.isSelected()) {
                son.pierde();
            }
            fin = true;
        }
    }

    boolean gana() {
        if (!minas.getText().equals("0")) {
            return false;
        }
        if (ya_gano) {
            return false;
        }
        for (int i = 0; i < x; i++) {
            for (int q = 0; q < y; q++) {
                if (b[i][q].usado || !b[i][q].isVisible() || b[i][q].flag) {
                    continue;
                }
                return false;
            }
        }
        if (act_sonido.isSelected()) {
            son.gana();
        }

        c.stop();
        try {
            pun.agregar(n, Integer.parseInt(tiempo.getText()));
        } catch (Exception ex) {
        }
        return true;
    }

    void carita_feliz(boolean x) {
        String q;
        if (x) {
            q = "/imagenes/carita_si.jpg";
        } else {
            q = "/imagenes/carita_no.jpg";
        }
        ImageIcon icon1 = new ImageIcon(getClass().getResource(q));
        Icon icono1 = new ImageIcon(icon1.getImage().getScaledInstance(carita.getWidth(), carita.getHeight(), Image.SCALE_DEFAULT));
        carita.setText(null);
        carita.setIcon(icono1);
    }

    void crearminas() {
        pos_minas.clear();
        int s;
        for (int i = 0; i < can_minas; i++) {
            s = (int) (Math.random() * (x * y));
            if (pos_minas.contains(s)) {
                i--;
            } else {
                pos_minas.add(s);
                b[s / x][s % y].mina = true;
            }
        }
    }

    void reiniciar() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                b[i][j].setVisible(true);
                b[i][j].setBorderPainted(true);
                b[i][j].setContentAreaFilled(true);
                b[i][j].mina = false;
                b[i][j].setIcon(pre);
                b[i][j].setEnabled(true);
                b[i][j].visitado = false;
                b[i][j].flag = false;
                b[i][j].usado = false;
            }
        }
        crearminas();
        numeros();
        c.stop();
        carita_feliz(true);
        tiempo.setText("0");
        minas.setText("" + can_minas);
        if (act_sonido.isSelected()) {
            son.inicio();
        }
        fin = false;
        ya_gano = false;
    }

    void nivel() {
        crear_botones();
        if (x > 25) {
            setSize(555, 670);
            gri.tablero.setSize(525, 525);
            barra.setBounds(0, 0, 555, 25);
            carita.setLocation(250, 35);
            tiempo.setLocation(440, 35);
            minas.setLocation(50, 35);
        } else if (x > 19) {
            setSize(505, 620);
            gri.tablero.setSize(475, 475);
            barra.setBounds(0, 0, 505, 25);
            carita.setLocation(225, 35);
            tiempo.setLocation(400, 35);
            minas.setLocation(50, 35);
        } else {
            setSize(405, 520);
            gri.tablero.setSize(375, 375);
            barra.setBounds(0, 0, 375, 25);
            carita.setLocation(180, 35);
            tiempo.setLocation(310, 35);
            minas.setLocation(20, 35);
        }

        gri.tablero.removeAll();
        reiniciar();
        gri.crear(x, y);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                gri.tablero.add(b[i][j]);
            }
        }
    }

    void numeros() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int con = 0, aux1, aux2;
                aux1 = i;
                aux2 = j;
                for (int k = 0; k < 8; k++) {
                    aux1 += n3[k];
                    aux2 += n4[k];
                    if (aux1 > -1 && aux1 < x && aux2 > -1 && aux2 < y) {
                        if (b[aux1][aux2].mina) {
                            con++;
                        }
                    }
                }
                b[i][j].minas_adyacentes = con;
            }
        }
    }

    void cambiar(int q, int w) {
        b[q][w].cambiarimagen("/imagenes/n_" + b[q][w].minas_adyacentes + ".png");
        b[q][w].setBorderPainted(false);
        b[q][w].setContentAreaFilled(false);
        b[q][w].enable(false);
        b[q][w].usado = true;
    }

    void dfs(int f, int c) {
        if (!(f > -1 && f < x && c > -1 && c < y) || b[f][c].visitado) {
            return;
        }
        if (b[f][c].mina) {
            return;
        }
        if (b[f][c].minas_adyacentes > 0) {
            cambiar(f, c);
            return;
        }

        b[f][c].setVisible(false);
        b[f][c].visitado = true;
        b[f][c].usado = true;
        for (int i = 0; i < 8; i++) {
            dfs(f + n1[i], c + n2[i]);
        }
    }

    void crear_botones() {
        b = new botones[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                b[i][j] = new botones(i);
                final int y2 = i;
                final int y3 = j;

                b[i][j].addMouseListener(new java.awt.event.MouseListener() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            if (b[y2][y3].usado || ya_gano) {
                                return;
                            }
                            if (b[y2][y3].flag) {
                                b[y2][y3].setIcon(pre);
                                b[y2][y3].setEnabled(true);
                                b[y2][y3].flag = false;
                                minas.setText((Integer.parseInt(minas.getText()) + 1) + "");
                            } else {
                                b[y2][y3].cambiarimagen("/imagenes/bandera.png");
                                b[y2][y3].setEnabled(false);
                                b[y2][y3].flag = true;
                                minas.setText((Integer.parseInt(minas.getText()) - 1) + "");
                            }
                            if (gana()) {
                                ya_gano = true;
                                JOptionPane.showMessageDialog(null, "gano!!!");
                            }
                        }
                    }

                    public void mousePressed(MouseEvent e) {
                    }

                    public void mouseReleased(MouseEvent e) {
                    }

                    public void mouseEntered(MouseEvent e) {
                    }

                    public void mouseExited(MouseEvent e) {
                    }
                });

                b[i][j].addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!ya_gano) {
                            if (!c.isAlive()) {
                                c = new cronometro(tiempo);
                                c.start();
                            }
                        }
                        if (b[y2][y3].mina) {
                            b[y2][y3].cambiarimagen("/imagenes/mina123.png");
                            b[y2][y3].setBorderPainted(false);
                            b[y2][y3].setContentAreaFilled(false);
                            carita_feliz(false);
                            c.stop();
                            fin_juego();
                        } else {
                            dfs(y2, y3);
                            b[y2][y3].usado = true;
                            if (gana()) {
                                ya_gano = true;
                                JOptionPane.showMessageDialog(null, "gano!!!");
                            }
                        }

                    }
                });
            }
        }
    }

    public ventana() throws IOException {
        setSize(405, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setTitle("Buscaminas");

        /*try {
            pun = new puntaje();
        } catch (java.io.IOException e) {
        }

        UpperTheme ut = new UpperTheme();
        ut.setPrimary1(Color.red);
        //ut.setPrimary2(Color.blue);
        ut.setPrimary3(Color.orange);
        ut.setSecondary1(Color.green);
        ut.setSecondary2(Color.LIGHT_GRAY);
        ut.setSecondary3(Color.cyan);
        ut.setBlack(Color.magenta);
        ut.setWhite(Color.yellow);
        look.setCurrentTheme(ut);
        try {
            UIManager.setLookAndFeel(look);
        } catch (UnsupportedLookAndFeelException e) {
        }*/

        carita.setSize(40, 40);
        carita.setLocation(180, 35);
        carita_feliz(true);
        son.inicio();

        tiempo.setSize(75, 30);
        tiempo.setLocation(310, 35);
        tiempo.setFont(new Font(null, (int) LEFT_ALIGNMENT, 25));
        tiempo.setText("0: 0");
        minas.setLocation(20, 35);
        minas.setSize(75, 30);
        minas.setFont(new Font(null, (int) LEFT_ALIGNMENT, 25));

        nivel();
        pre = b[0][0].getDisabledIcon();

        file.add(nuevo);
        file.add(nivel);
        file.add(act_sonido);
        act_sonido.setSelected(true);
        file.add(puntajes);
        file.add(salir);
        barra.add(file);
        barra.setBounds(0, 0, 405, 25);

        barra.add(aiuda);
        aiuda.add(instrucciones);
        aiuda.add(about);

        nivel.add(principiante);
        nivel.add(intermedio);
        nivel.add(experto);
        nivel.add(personalizado);

        principiante.addActionListener(this);
        intermedio.addActionListener(this);
        experto.addActionListener(this);
        personalizado.addActionListener(this);
        salir.addActionListener(this);
        about.addActionListener(this);
        nuevo.addActionListener(this);
        puntajes.addActionListener(this);
        instrucciones.addActionListener(this);
        carita.addActionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);

        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/icono.png"));
        setIconImage(icon);

        add(barra);
        add(carita);
        add(gri.tablero);
        add(minas);
        add(tiempo);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == salir) {
            System.exit(0);
            return;
        }
        if (evt.getSource() == about) {
            JOptionPane.showMessageDialog(null, "buscaminas, v1.21");
            return;
        }
        if (evt.getSource() == instrucciones) {
            JOptionPane.showMessageDialog(null, "click izquierdo selecciona la casilla\n click derecho coloca bandera");
            return;
        }
        if (evt.getSource() == principiante) {
            can_minas = 10;
            x = y = 9;
            try {
                nivel();
            } catch (Exception e) {
            }
            n = "principiante";
            return;
        }
        if (evt.getSource() == intermedio) {
            can_minas = 40;
            x = y = 16;
            try {
                nivel();
            } catch (Exception e) {
            }
            n = "intermedio";
            return;
        }
        if (evt.getSource() == experto) {
            can_minas = 80;
            x = y = 20;
            try {
                nivel();
            } catch (Exception e) {
            }
            n = "experto";
            return;
        }
        if (evt.getSource() == personalizado) {
            String aux1 = JOptionPane.showInputDialog(null, "inserte longitud");
            String aux2 = JOptionPane.showInputDialog(null, "inserte numero de minas");
            if (aux1 != null && aux2 != null) {
                try {
                    x = y = Integer.parseInt(aux1);
                    can_minas = Integer.parseInt(aux2);
                    if (can_minas > x * y || can_minas < 3) {
                        JOptionPane.showMessageDialog(null, "error en las minas!!!");
                        return;
                    }
                    if (x > 30 || x < 3) {
                        JOptionPane.showMessageDialog(null, "error en la longitud");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "error en la entrada");
                    return;
                }
            } else {
                return;
            }

            nivel();
            n = "...";
            return;
        }
        if (evt.getSource() == puntajes) {
            if (pun == null) {
                JOptionPane.showMessageDialog(null, "Puntajes no disponibles");
                return;
            }
            pun.mostrar();
        }
        if (evt.getSource() == carita || evt.getSource() == nuevo) {
            reiniciar();
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            reiniciar();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        this.requestFocus();
    }

    public void mouseExited(MouseEvent e) {
    }
}
