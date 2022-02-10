/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ekranske_forme;

import domen.OpstiDomenskiObjekat;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Ana
 */
public class FrmMain extends OpstaEkranskaForma {

    /**
     * Creates new form FrmMain
     */
    public FrmMain() {
        initComponents();
        this.setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        postaviPozadinu();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        mbar = new javax.swing.JMenuBar();
        mTreneri = new javax.swing.JMenu();
        miDodajTrenera = new javax.swing.JMenuItem();
        miPretraziTrenere = new javax.swing.JMenuItem();
        mClanovi = new javax.swing.JMenu();
        miDodajClana = new javax.swing.JMenuItem();
        miPretraziClanove = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        mTermini = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        miPretraziTermine = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenu3.setText("jMenu3");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Meni");

        mbar.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        mbar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        mTreneri.setBackground(new java.awt.Color(51, 204, 255));
        mTreneri.setText(" Treneri");

        miDodajTrenera.setText(" Kreiraj trenera");
        mTreneri.add(miDodajTrenera);

        miPretraziTrenere.setText(" Pretraži trenere");
        mTreneri.add(miPretraziTrenere);

        mbar.add(mTreneri);

        mClanovi.setBackground(new java.awt.Color(153, 153, 255));
        mClanovi.setText("Članovi");

        miDodajClana.setText("Kreiraj Člana");
        mClanovi.add(miDodajClana);

        miPretraziClanove.setText(" Pretraži članove");
        mClanovi.add(miPretraziClanove);

        mbar.add(mClanovi);

        jMenu2.setText("Grupe");

        jMenuItem6.setText(" Kreiraj grupu");
        jMenu2.add(jMenuItem6);

        jMenuItem5.setText("Vidi sve grupe");
        jMenu2.add(jMenuItem5);

        mbar.add(jMenu2);

        mTermini.setText("Termini");

        jMenuItem7.setText("Kreiraj termin");
        mTermini.add(jMenuItem7);

        miPretraziTermine.setText(" Pretraži termine");
        mTermini.add(miPretraziTermine);

        mbar.add(mTermini);

        jMenu1.setText("Dodatak");

        jMenuItem4.setText(" Izloguj se");
        jMenu1.add(jMenuItem4);

        mbar.add(jMenu1);

        setJMenuBar(mbar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 223, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenu mClanovi;
    private javax.swing.JMenu mTermini;
    private javax.swing.JMenu mTreneri;
    private javax.swing.JMenuBar mbar;
    private javax.swing.JMenuItem miDodajClana;
    private javax.swing.JMenuItem miDodajTrenera;
    private javax.swing.JMenuItem miPretraziClanove;
    private javax.swing.JMenuItem miPretraziTermine;
    private javax.swing.JMenuItem miPretraziTrenere;
    // End of variables declaration//GEN-END:variables

    private void postaviPozadinu() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("C:\\Users\\Ana\\Desktop\\Seminarski_Projektovanje softvera\\VP_PS_Klijent\\src\\slike\\logo1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dimension dimenzije = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) dimenzije.getHeight();
        int width = (int) dimenzije.getWidth();

        Image pozadina = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(pozadina);
        setContentPane(new JLabel(imageIcon));
    }

    @Override
    public void dispose() {
       
            kontroler_KI.KontrolerKI_Korisnik.getInstance().izlogujKorisnika();
        
    }


    public JMenuItem getjMenuItem1() {
        return jMenuItem1;
    }

    public JMenuItem getjMenuItem2() {
        return jMenuItem2;
    }

    public JMenuItem getjMenuItem3() {
        return jMenuItem3;
    }

    public JMenuItem getjMenuItem4() {
        return jMenuItem4;
    }

    public JMenuItem getjMenuItem5() {
        return jMenuItem5;
    }

    public JMenuItem getjMenuItem6() {
        return jMenuItem6;
    }

    public JMenuItem getjMenuItem7() {
        return jMenuItem7;
    }

    public JMenuItem getMiPretraziTrenere() {
        return miPretraziTrenere;
    }

    public JMenuItem getMiDodajClana() {
        return miDodajClana;
    }

    public JMenuItem getMiDodajTrenera() {
        return miDodajTrenera;
    }

    public JMenuItem getMiPretraziClanove() {
        return miPretraziClanove;
    }

    public JMenuItem getMiPretraziTermine() {
        return miPretraziTermine;
    }

}
