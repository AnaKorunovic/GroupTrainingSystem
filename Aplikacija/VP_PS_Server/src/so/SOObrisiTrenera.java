/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Clan;
import domen.Grupa;
import domen.OpstiDomenskiObjekat;
import domen.Trener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import konfiguracija.KriterijumWhere;
import static so.OpstaSo.dbbr;

/**
 *
 * @author Ana
 */
public class SOObrisiTrenera extends OpstaSo{
    
    public SOObrisiTrenera() {
        super();
       
    }
  
   
    @Override
    void operacija(OpstiDomenskiObjekat od) {
       
        if (!dbbr.obrisiObjekat(od)) {
            setPoruka("Sistem ne moze da obrise trenera.");
            setUspesno(false);
            
        }else{
            setPoruka("Sistem je obrisao trenera.");
            setUspesno(true);
            
        }
    }
    


    @Override
    public OpstiDomenskiObjekat vratiODO() {
return null;
    }

    @Override
    protected boolean proveriStrukturnaOgranicenja() {
  
        Trener t=(Trener)odo;
        Grupa grupa=new Grupa();
        grupa.setTrener(t);
        
        List<OpstiDomenskiObjekat> listaGrupeODO=dbbr.vratiSveObjekteSaUslovom(grupa,KriterijumWhere.PRETRAGA_PO_ID_TRENERA);
       
        if(listaGrupeODO==null){
            return true;
        }
        else{
            
            for(OpstiDomenskiObjekat og:listaGrupeODO){
                Grupa g=(Grupa)og;
                Clan c=new Clan();
                c.setGrupa(grupa);
                List<OpstiDomenskiObjekat> listaClanoviODO=dbbr.vratiSveObjekteSaUslovom(c,KriterijumWhere.PRETRAGA_PO_ID_GRUPE);
                if(listaClanoviODO!=null) {
                    
                    //setPoruka("Sistem ne moze da obrise trenera.\nNaruseno je strukturno ogranicenje.");
                    setPoruka("Sistem ne moze da obrise trenera.");
                    return false;
                }
               
            }
        }
        return true;
        
    }
    
}
