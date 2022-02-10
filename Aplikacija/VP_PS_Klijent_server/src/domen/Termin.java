/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class Termin implements  Serializable,OpstiDomenskiObjekat{
    
    private int terminId;
    private Date datum;
    private String vreme;
    private Grupa grupa;
    private List<Prisustvo> prisustvo;
    private int status; //0-ne postoji u bazi; 1-postoji u bazi; 2-postoji u bazi,obrisan je

    public Termin() {
        prisustvo=new ArrayList<>();
    }

    public Termin(int terminId, Date datum, String vreme, Grupa grupa) {
        this.terminId = terminId;
        this.datum = datum;
        this.vreme = vreme;
        this.grupa = grupa;
        this.prisustvo = new ArrayList<>();
    }


    
    
    @Override
    public String vratiNazivTabele() {
       return "Termin";
    }

    @Override
    public String vratiVrednostiAtributa() {
        return terminId + ","+ 
                (datum == null ? null : "'" +datum+ "'")+", "+
                (vreme == null ? null : "'" +vreme+ "'")+", "+
                (grupa == null ? null : grupa.getGrupaId());
               
    }


    @Override
    public List<OpstiDomenskiObjekat> vratiListuSvih(ResultSet rs) throws SQLException {
           List<OpstiDomenskiObjekat> lista=new ArrayList<>();
           while(rs.next()){
              // Trener trener=new Trener();
              // trener.setTrenerId(rs.getInt("trenerid"));
                Grupa grupa=new Grupa();
                grupa.setGrupaId(rs.getInt("grupaId"));
               // grupa.setTrener(trener);
               
              
               Termin termin=new Termin();
               termin.setTerminId(rs.getInt("terminid"));
               termin.setGrupa(grupa);
               termin.setDatum(rs.getDate("datum"));
               termin.setVreme(rs.getString("vreme"));
               
               lista.add(termin);

           }
           return lista;
    }

    
    public int getTerminId() {
        return terminId;
    }

    public void setTerminId(int terminId) {
        this.terminId = terminId;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

   

    @Override
    public String vratiNazivTabeleJoin() {

        return "Grupa";
    }

    @Override
    public String vratiUslovZaWhere(KriterijumWhere kriterijum) {
       String where="";
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date datum1 = java.sql.Date.valueOf(sdf.format(datum.getTime()));
      
        switch(kriterijum){
           case PRETRAGA_PO_ID_GRUPE: where+="grupaid="+grupa.getGrupaId();break;
           case PRIMARAN_KLJUC: where+="terminid="+terminId;break;
           case PRETRAGA_PO_GRUPI_DATUMU: where+="grupaid="+grupa.getGrupaId()+" and datum='"+datum+"'";break;
           case PRETRAGA_PO_IMENU: where+=grupa.vratiUslovZaWhere(kriterijum.PRETRAGA_PO_IMENU);break;
           case PRETRAGA_PO_DATUMU:where+="datum='"+datum1+"'"; break;
           case PRETRAGA_PO_DODATUMA: where+="datum between curdate() and '"+datum+"'";break;
            case PRETRAGA_PO_DATUMU_I_VREMENU: where+="datum='"+datum1+"' and vreme='"+vreme+"'" ; break;
       }
        
        return where; 
    }

    @Override
    public void postaviVrednostPK(int vrednost) {

        setTerminId(vrednost);
    }

    @Override
    public boolean autoinkrement() {

        return true;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    public Grupa getGrupa() {
        return grupa;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    public List<Prisustvo> getPrisustvo() {
        return prisustvo;
    }

    public void setPrisustvo(List<Prisustvo> prisustvo) {
        this.prisustvo = prisustvo;
    }

    @Override
    public String postaviVrednostiAtributa() {
       return "datum='"+datum+"', vreme='"+vreme+"', grupaId="+grupa.getGrupaId();
    }

       
}
