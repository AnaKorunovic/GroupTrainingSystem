package domen;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import konfiguracija.KriterijumWhere;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ana
 */
public class Korisnik implements Serializable, OpstiDomenskiObjekat {

    private int korisnikId;
    private String korisnickoIme;
    private String sifra;
    

    public Korisnik() {
    }

    public Korisnik(int korisnikId, String korisnickoIme, String sifra) {
        this.korisnikId = korisnikId;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    @Override
    public String vratiNazivTabele() {
        return "Korisnik";
    }


    @Override
    public String vratiVrednostiAtributa() {
        return korisnikId+", '" + (korisnickoIme == null ? null : korisnickoIme) + "', '" + (sifra == null ? null : sifra) + "'";

    }

    @Override
    public List<OpstiDomenskiObjekat> vratiListuSvih(ResultSet rs) throws SQLException {
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new Korisnik(rs.getInt("korisnikid"),rs.getString("korisnickoIme"),
                    rs.getString("sifra")));

        }
        return lista;
    }

   
    @Override
    public void postaviVrednostPK(int vrednost) {
        setKorisnikId(vrednost);
        
    }

    @Override
    public String vratiNazivTabeleJoin() {
        return null;
    }

    @Override
    public String vratiUslovZaWhere(KriterijumWhere kriterijum) {
          String where="";
        switch(kriterijum){
           
           case PRIMARAN_KLJUC: where+="korisnikid="+korisnikId;break;
           case PRIJAVA:where+="korisnickoIme='" + (korisnickoIme == null ? null : korisnickoIme) + "' and "
                + "sifra='" + (sifra == null ? null : sifra) + "'";break;
       
       }
        
        return where; 
    }

    @Override
    public boolean autoinkrement() {
         return true;
    }

    @Override
    public String postaviVrednostiAtributa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
