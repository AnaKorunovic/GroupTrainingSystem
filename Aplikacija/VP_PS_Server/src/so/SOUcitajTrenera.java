/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.Trener;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class SOUcitajTrenera extends  OpstaSo{
    public SOUcitajTrenera() {
        super();
        
      
    }
   
   
    @Override
    void operacija(OpstiDomenskiObjekat odo1) {
        odo=(dbbr.vratiSveObjekteSaUslovom(odo1, KriterijumWhere.PRIMARAN_KLJUC)).get(0);
        
      if (odo==null) {
            setPoruka("Sistem ne moze da ucita trenera.");
            setUspesno(false);
            
            
        }else{
            setPoruka("Sistem je ucitao trenera.");
            setUspesno(true);
        }
    }
}
