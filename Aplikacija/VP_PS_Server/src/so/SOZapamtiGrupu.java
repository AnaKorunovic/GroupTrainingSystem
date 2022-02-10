/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Grupa;
import domen.OpstiDomenskiObjekat;
import domen.Trener;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class SOZapamtiGrupu extends OpstaSo{
    
    public SOZapamtiGrupu() {
        super();
       // odo=g;
      
        
    }

    @Override
    void operacija(OpstiDomenskiObjekat od) {
      
        if (!dbbr.UbaciNoviObjekat(od)) {
            setPoruka("Sistem ne moze da zapamti novu grupu.");
            setUspesno(false);
           
        }
        setPoruka("Sistem je zapamtio novu grupu.");
        setUspesno(true);
        

    }

    @Override
    protected boolean proveriVrednosnaOgranicenja() {
       boolean uspesno=true;

       String ime=((Grupa)odo).getImeGrupe();
       if (ime.length() <= 2 || daLiSadrziBrojeve(ime)) {
            uspesno = false;
       }
        
        if (dbbr.daLiPostojiObjekat(odo, KriterijumWhere.PRETRAGA_PO_IMENU)){
            uspesno=false;
            setPoruka("Sistem ne moze da kreira grupu.\nNaruseno je (prosto) vrednosno ogranicenje.");
        }
       return uspesno;
    }
    @Override
    protected boolean proveriStrukturnaOgranicenja() {
        
        Grupa g=(Grupa)odo;
        Trener t=g.getTrener();
        if(!dbbr.daLiPostojiObjekat(t, KriterijumWhere.PRIMARAN_KLJUC)){
            setPoruka("Sistem ne moze da zapamti grupu.");
            return false;
        
    }return true;
    }
    
}
