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
public class Grupa implements  Serializable,OpstiDomenskiObjekat{

    private int grupaId;
    private String imeGrupe;
    private Trener trener;

    public Grupa() {
    }

    public Grupa(int grupaId, String ime, Trener trener) {
        this.grupaId = grupaId;
        this.imeGrupe = ime;
        this.trener = trener;
    }

    
    
    
    @Override
    public String vratiNazivTabele() {

        return "Grupa";
    }

    @Override
    public String vratiNazivTabeleJoin() {

        return "Trener";
    }

    @Override
    public String vratiVrednostiAtributa() {
        return grupaId + ", '" + ( imeGrupe== null ? null : imeGrupe)+"'"
                + ", " + (trener == null ? null : trener.getTrenerId());
    }

    @Override
    public String vratiUslovZaWhere(KriterijumWhere kriterijum) {
       String where="";
        switch(kriterijum){
           case PRETRAGA_PO_ID_TRENERA: where+=trener.vratiUslovZaWhere(kriterijum.PRIMARAN_KLJUC);break;
           case PRIMARAN_KLJUC: where+="imeGrupe='"+imeGrupe+"'";break;
           case PRETRAGA_PO_IMENU: where+="imeGrupe='"+imeGrupe+"'";break;
           case PRETRAGA_PO_ID_GRUPE:where+="grupaId="+grupaId;break;
       
       }
        
        return where;
    }

    @Override
    public void postaviVrednostPK(int vrednost) {

        setGrupaId(vrednost);
    }

    //Ovo proveri
    @Override
    public List<OpstiDomenskiObjekat> vratiListuSvih(ResultSet rs) throws SQLException {
        List<OpstiDomenskiObjekat> lista=new ArrayList<>();
           while(rs.next()){
               Trener t=new Trener();
               t.setTrenerId(rs.getInt("trenerId"));
              lista.add(new Grupa(rs.getInt("grupaId"),rs.getString("imeGrupe"),t));

           }
           return lista;
    }

    @Override
    public boolean autoinkrement() {
        return true; 
    }

    public int getGrupaId() {
        return grupaId;
    }

    public void setGrupaId(int grupaId) {
        this.grupaId = grupaId;
    }

    public Trener getTrener() {
        return trener;
    }

    public void setTrener(Trener trener) {
        this.trener = trener;
    }

    public void setImeGrupe(String imeGrupe) {
        this.imeGrupe = imeGrupe;
    }

    public String getImeGrupe() {
        return imeGrupe;
    }
    

    @Override
    public String toString() {
        return grupaId+"|"+imeGrupe+"|Trener: "+trener.getIme()+" "+trener.getPrezime();
        //"|"+trener.getEmailAdresa()+"|"+trener.getBrojTelefona();
    }

    @Override
    public String postaviVrednostiAtributa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    
}
