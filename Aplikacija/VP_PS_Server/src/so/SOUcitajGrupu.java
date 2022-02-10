/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Grupa;
import domen.OpstiDomenskiObjekat;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class SOUcitajGrupu extends OpstaSo{
    
    KriterijumWhere kriterijum;
    public SOUcitajGrupu(KriterijumWhere krit) {
        super();
        this.kriterijum=krit;
      
    }
   
   
    @Override
    void operacija(OpstiDomenskiObjekat od) {
        if(kriterijum==KriterijumWhere.PRETRAGA_PO_IMENU) odo=(dbbr.vratiSveObjekteSaUslovom(od, KriterijumWhere.PRETRAGA_PO_IMENU)).get(0);
        if(kriterijum==KriterijumWhere.PRIMARAN_KLJUC) odo=(dbbr.vratiSveObjekteSaUslovom(od, KriterijumWhere.PRIMARAN_KLJUC)).get(0);
        if(kriterijum==KriterijumWhere.PRETRAGA_PO_ID_GRUPE) odo=(dbbr.vratiSveObjekteSaUslovom(od, KriterijumWhere.PRETRAGA_PO_ID_GRUPE)).get(0);
      if (odo==null) {
            setPoruka("Sistem ne moze da ucita grupu.");
            setUspesno(false);
            
            
        }else{
            setPoruka("Sistem je ucitao grupu.");
            setUspesno(true);
        }
    }
}
