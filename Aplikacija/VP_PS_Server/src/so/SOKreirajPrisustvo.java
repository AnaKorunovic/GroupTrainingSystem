/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.Prisustvo;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class SOKreirajPrisustvo extends OpstaSo{
    
    public SOKreirajPrisustvo() {
        super();
       
    }
   
   
    @Override
    void operacija(OpstiDomenskiObjekat od) {
      if (!dbbr.UbaciNoviObjekat(od)) {
            setPoruka("Sistem ne moze da kreira novo prisustvo.");
            setUspesno(false);
            
        }else{
            setPoruka("Sistem je kreirao novo prisustvo.");
            setUspesno(true);
        }
    }

    @Override
    protected boolean proveriVrednosnaOgranicenja() {
       if(dbbr.daLiPostojiObjekat(odo, KriterijumWhere.PRIMARAN_KLJUC)) {
           setPoruka("Sistem ne moze da kreira novo prisustvo.");
           return false;
    }return true;
    }

    @Override
    protected boolean proveriStrukturnaOgranicenja() {
      boolean uspesno=true;
      if(!dbbr.daLiPostojiObjekat(((Prisustvo)odo).getClan(), KriterijumWhere.PRETRAGA_PO_ID_CLANA)) uspesno=false;
      if(!dbbr.daLiPostojiObjekat(((Prisustvo)odo).getTermin(), KriterijumWhere.PRIMARAN_KLJUC)) uspesno=false;
      if(uspesno==false){setPoruka("Sistem ne moze da kreira novo prisustvo.");
           return false;
    }return true;
          
      }
        
    
    
    
}
