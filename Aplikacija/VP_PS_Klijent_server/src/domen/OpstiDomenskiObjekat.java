/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public interface OpstiDomenskiObjekat {
    
    String vratiNazivTabele();
    String vratiNazivTabeleJoin();
    String vratiVrednostiAtributa();
    String vratiUslovZaWhere(KriterijumWhere kriterijum);
    void postaviVrednostPK(int vrednost);
    List<OpstiDomenskiObjekat> vratiListuSvih(ResultSet rs) throws SQLException;
    boolean autoinkrement();
    
    String postaviVrednostiAtributa();

    
    

    
    
   
}
