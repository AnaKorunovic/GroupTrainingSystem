/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import java.util.List;
import konfiguracija.KriterijumWhere;


/**
 *
 * @author Ana
 */
public class SOPrijava extends OpstaSo
{
   
    public SOPrijava() {
        super();
        
    }
    @Override
    void operacija(OpstiDomenskiObjekat odo) {
        boolean signal=dbbr.daLiPostojiObjekat(odo,KriterijumWhere.PRIJAVA);
        
        if(signal){
            setPoruka("Sistem je nasao korisnika po zadatoj vrednosti.");
            setUspesno(signal);
        }else{
            setPoruka("Sistem ne moze da nadje korisnika po zadatoj vrednosti.");
            setUspesno(signal);
            
        }
        
    }

   
    
    
}
