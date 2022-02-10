/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.Termin;
import konfiguracija.KriterijumWhere;
import static so.OpstaSo.dbbr;

/**
 *
 * @author Ana
 */
public class SOUcitajTermin extends OpstaSo{
    public SOUcitajTermin() {
        super();
        
      
    }
   
   
    @Override
    void operacija(OpstiDomenskiObjekat od) {
               odo=(dbbr.vratiSveObjekteSaUslovom(od, KriterijumWhere.PRIMARAN_KLJUC)).get(0);

      if (odo==null) {
            setPoruka("Sistem ne moze da ucita termin.");
            setUspesno(false);
            
            
        }else{
            setPoruka("Sistem je ucitao termin.");
            setUspesno(true);
        }
    }
}
