/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.OpstiDomenskiObjekat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ana
 */
public abstract class OpstaSo {

    static DBBroker dbbr;
    private String poruka;
    private boolean uspesno;
    OpstiDomenskiObjekat odo;

    public OpstaSo() {
        dbbr = new DBBroker();
    }

    public String getPoruka() {
        return poruka;
    }

    public boolean isUspesno() {
        return uspesno;
    }

    protected void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    protected void setUspesno(boolean uspesno) {
        this.uspesno = uspesno;
    }

   
    void potvrdiTransakciju() {
        dbbr.potvrdiTransakciju();
    }

    void ponistiTransakciju() {
        dbbr.ponistiTransakciju();
    }
  
    private void proveriPreduslov() throws Exception  {
        if (!proveriVrednosnaOgranicenja()) {
            //setPoruka("Sistem ne moze da izvrsi operaciju nad objektom " + odo.vratiNazivTabele() + ". \nNaruseno je (prosto) vrednosno ogranicenje.");
           //poruka se definise u konkretnim SO
            setUspesno(false);
            throw  new Exception("Naruseno je vrednosno ogranicenje.");
        }
        if (!proveriStrukturnaOgranicenja()) {
            //setPoruka("Sistem ne moze da izvrsi operaciju nad objektom " + odo.vratiNazivTabele()+ ".\nNaruseno je strukturno ogranicenje.");
            setUspesno(false);
             throw  new Exception("Naruseno je strukturno ogranicenje.");
        }
       
    }
    abstract void operacija(OpstiDomenskiObjekat odo) throws Exception;

    synchronized public void izvrsiOperaciju(OpstiDomenskiObjekat odo)  {
        try {
            this.odo=odo;
            dbbr.uspostaviKonekciju();
            proveriPreduslov();
            operacija(odo);
            potvrdiTransakciju();
         
        } catch (Exception ex) {
             ponistiTransakciju();
            //throw ex;
            } finally {
            dbbr.raskiniKonekciju();
            }

      
    }
    
    public OpstiDomenskiObjekat vratiODO(){return odo;}
    public List vratiListu(){return null;}

    protected boolean proveriVrednosnaOgranicenja(){
        return true;
    }

    protected boolean proveriStrukturnaOgranicenja() {

        return true;
        
    }
    public boolean daLiSadrziBrojeve(String string){
        char[] slova = string.toCharArray();
      for(char c : slova){
         if(Character.isDigit(c)){
            return true;
         }
      }return false;
    }
    public boolean daLiSadrziSlova(String string){
      char[] slova = string.toCharArray();
      boolean postojiSlovo=false;
      for(char c : slova){
         if(!Character.isDigit(c)){
            postojiSlovo=true;
         }
      }return postojiSlovo;
    }
}