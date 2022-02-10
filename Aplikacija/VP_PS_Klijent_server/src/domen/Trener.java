/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class Trener implements Serializable, OpstiDomenskiObjekat {

    private int trenerId;
    private String ime;
    private String prezime;
    private String brojTelefona;
    private String emailAdresa;

    public Trener() {
    }

    public Trener(int trenerId, String ime, String prezime, String brojTelefona, String emailAdresa) {
        this.trenerId = trenerId;
        this.ime = ime;
        this.prezime = prezime;
        this.brojTelefona = brojTelefona;
        this.emailAdresa = emailAdresa;
    }

    @Override
    public String vratiNazivTabele() {
        return "Trener";
    }

    
    @Override
    public String vratiVrednostiAtributa() {
        return trenerId + ", " + (ime == null ? null : "'" + ime + "', ")
                + (prezime == null ? null : "'" + prezime + "'")+", "
                + (brojTelefona == null ? null : "'" + brojTelefona + "'")+", "
                + (emailAdresa == null ? null : "'" + emailAdresa + "'");

    }


    @Override
    public List<OpstiDomenskiObjekat> vratiListuSvih(ResultSet rs) throws SQLException {
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new Trener(rs.getInt("trenerId"),
                    rs.getString("ime"), rs.getString("prezime"),
                    rs.getString("brojTelefona"), rs.getString("emailAdresa")));

        }
        return lista;

    }


    public int getTrenerId() {
        return trenerId;
    }

    public void setTrenerId(int trenerId) {
        this.trenerId = trenerId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getEmailAdresa() {
        return emailAdresa;
    }

    public void setEmailAdresa(String emailAdresa) {
        this.emailAdresa = emailAdresa;
    }

    
    @Override
    public void postaviVrednostPK(int vrednost) {
        setTrenerId(vrednost);
    }

    @Override
    public String vratiNazivTabeleJoin() {

        return null;
    }

    @Override
    public String vratiUslovZaWhere(KriterijumWhere kriterijum) {
          String where="";
        switch(kriterijum){
           //case PRIMARAN_KLJUC: where+="email='"+emailAdresa+"' and brojtelefona='"+brojTelefona+"'";break;
           case PRIMARAN_KLJUC: where+="trenerid="+trenerId;break;
           case PRETRAGA_PO_IMENU:where+="ime='"+ime+"'";break;
           case PRETRAGA_SVE:where+="email='"+emailAdresa+"' and brojtelefona='"+brojTelefona+"'";break;
       
       }
        
        return where; 
    }

    @Override
    public boolean autoinkrement() {

        return true;
    }

    @Override
    public String toString() {
        return ime+" "+prezime+" | "+emailAdresa+" | "+brojTelefona;
    }

    @Override
    public String postaviVrednostiAtributa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
