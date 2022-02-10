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
public class Prisustvo implements  Serializable,OpstiDomenskiObjekat{

    private Clan clan;
    private Termin termin;
    private boolean prisutan;
    private String poruka;

    public Prisustvo() {
    }

    public Prisustvo(Clan clan, Termin termin, boolean prisutan, String poruka) {
        this.clan = clan;
        this.termin = termin;
        this.prisutan = prisutan;
        this.poruka = poruka;
    }
    
    
    
    
    @Override
    public String vratiNazivTabele() {

        return "Prisustvo";
    }

    //kako ovo
    @Override
    public String vratiNazivTabeleJoin() {
         return null;
    }

    @Override
    public String vratiVrednostiAtributa() {

         return (clan== null ? null : clan.getClanId())+ ", " +
                 (termin== null ? null :termin.getTerminId())+ ", " +
                 (prisutan==true ? 1: 0) + ", "+
                (poruka == null ? null : "'" +poruka+ "'");
    }

    @Override
    public String vratiUslovZaWhere(KriterijumWhere kriterijum) {
       String where="";
        switch(kriterijum){
           case PRETRAGA_PO_ID_CLANA: where+=clan.vratiUslovZaWhere(kriterijum.PRIMARAN_KLJUC);break;
           case PRETRAGA_PO_ID_TERMINA: where+=termin.vratiUslovZaWhere(kriterijum.PRIMARAN_KLJUC);break;
           case PRIMARAN_KLJUC: where+=clan.vratiUslovZaWhere(kriterijum.PRETRAGA_PO_ID_CLANA)+" and "+
                   termin.vratiUslovZaWhere(kriterijum.PRIMARAN_KLJUC);break;
       
       }
        
        return where;
    }

    @Override
    public void postaviVrednostPK(int vrednost) {
        //To change body of generated methods, choose Tools | Templates.
    }

    //proveri ovo
    @Override
    public List<OpstiDomenskiObjekat> vratiListuSvih(ResultSet rs) throws SQLException {
        List<OpstiDomenskiObjekat> lista=new ArrayList<>();
           while(rs.next()){
              /* Trener trener=new Trener();
              trener.setTrenerId(rs.getInt("trenerid"));
                Grupa grupa=new Grupa();
                grupa.setGrupaId(rs.getInt("grupaId"));
                grupa.setTrener(trener);*/
               
              Clan clan=new Clan();
               clan.setClanId(rs.getInt("clanId"));
              // clan.setIme(rs.getString("c.ime"));
               //clan.setPrezime(rs.getString("c.prezime"));
              // clan.setBrojTelefona(rs.getString("c.brojTelefona"));
              // clan.setGrupa(grupa);
               
               Termin termin=new Termin();
               termin.setTerminId(rs.getInt("terminid"));
               //termin.setGrupa(grupa);
               
               lista.add(new Prisustvo(clan,termin,rs.getBoolean("prisutan"),rs.getString("poruka")));

           }
           return lista;
    }

    @Override
    public boolean autoinkrement() {

        return false;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public boolean isPrisutan() {
        return prisutan;
    }

    public void setPrisutan(boolean prisutan) {
        this.prisutan = prisutan;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    @Override
    public String postaviVrednostiAtributa() {
       return "clanId="+clan.getClanId()+", terminid="+termin.getTerminId()+", prisutan="+prisutan+", poruka='"+poruka+"'";
    }
    
}
