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
public class Clan implements Serializable, OpstiDomenskiObjekat{
    
    private int clanId;
    private String ime;
    private String prezime;
    private String brojTelefona;
    private String jmbg;
    private Grupa grupa;

    public Clan() {
    }
    public Clan(int clanId, String ime, String prezime, String brojTelefona, Grupa grupa,String jmbg) {
        this.clanId = clanId;
        this.ime = ime;
        this.prezime = prezime;
        this.brojTelefona = brojTelefona;
        this.jmbg=jmbg;
        this.grupa = grupa;
    }

    
    @Override
    public String vratiNazivTabele() {
       return "Clan";
    }

    @Override
    public String vratiVrednostiAtributa() {
        return clanId + ", " + (ime == null ? null : "'" +ime+ "'") + ", "+
                (prezime == null ? null : "'" +prezime+ "'")+", "+
                (brojTelefona == null ? null : "'" +brojTelefona+ "'")+", "+
                 (grupa == null ? null : grupa.getGrupaId())+", "+
                (jmbg == null ? null : "'" +jmbg+ "'");
               
    }

//proveri ovo sa grupom
    @Override
    public List<OpstiDomenskiObjekat> vratiListuSvih(ResultSet rs) throws SQLException {
          List<OpstiDomenskiObjekat> lista=new ArrayList<>();
           while(rs.next()){
               Grupa g=new Grupa();
               g.setGrupaId(rs.getInt("grupaId"));
               lista.add(new Clan(rs.getInt("clanId"),
                       rs.getString("ime"),rs.getString("prezime"),
                       rs.getString("brojTelefona"),g,rs.getString("jmbg")));

           }
           return lista;
    }

    public int getClanId() {
        return clanId;
    }

    public void setClanId(int clanId) {
        this.clanId = clanId;
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

    public Grupa getGrupa() {
        return grupa;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }
    
    
    @Override
    public void postaviVrednostPK(int vrednost) {
        clanId=vrednost;
    }

    @Override
    public String vratiNazivTabeleJoin() {

        return "Grupa";
    }

    @Override
    public String vratiUslovZaWhere(KriterijumWhere kriterijum) {
        String where="";
        switch(kriterijum){
           case PRETRAGA_PO_ID_GRUPE: where+=grupa.vratiUslovZaWhere(kriterijum.PRETRAGA_PO_ID_GRUPE);break;
           case PRIMARAN_KLJUC: where+="jmbg='"+jmbg+"'";break;
           case PRETRAGA_PO_IMENU:where+="ime='"+ime+"'";break;
           case PRETRAGA_PO_ID_CLANA:where+="clanId="+clanId;break;
       
       }
        
        return where;
    }

    @Override
    public boolean autoinkrement() {

        return true;
    }

    @Override
    public String postaviVrednostiAtributa() {

        return "ime='"+ime+"'"+", prezime='"+prezime+"', brojTelefona='"+brojTelefona+"', grupaid="+
                grupa.getGrupaId()+", jmbg='"+jmbg+"'";
    }

   
    
    
}
