/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ekranske_forme;

import domen.OpstiDomenskiObjekat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Ana
 */
public abstract class OpstaEkranskaForma extends JFrame{
    
     
    public void prikaziPoruku(String sadrzaj, String naslov) {
        JOptionPane.showMessageDialog(this, sadrzaj,naslov,JOptionPane.INFORMATION_MESSAGE);

    }
}
