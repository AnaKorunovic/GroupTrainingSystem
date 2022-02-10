/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Clan;
import domen.OpstiDomenskiObjekat;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class SOUcitajClana extends OpstaSo{
    KriterijumWhere kw;
    public SOUcitajClana(KriterijumWhere kw) {
        super();
        this.kw=kw;
        
      
    }
   
   
    @Override
    void operacija(OpstiDomenskiObjekat od) {
        
        odo=(dbbr.vratiSveObjekteSaUslovom(od, kw)).get(0);
      if (odo==null) {
            setPoruka("Sistem ne moze da ucita clana.");
            setUspesno(false);
            
            
        }else{
            setPoruka("Sistem je ucitao clana.");
            setUspesno(true);
        }
    }
}
