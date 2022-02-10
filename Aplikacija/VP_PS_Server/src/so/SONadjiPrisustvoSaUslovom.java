/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.Prisustvo;
import domen.Termin;
import java.util.ArrayList;
import java.util.List;
import konfiguracija.KriterijumWhere;
import static so.OpstaSo.dbbr;

/**
 *
 * @author Ana
 */
public class SONadjiPrisustvoSaUslovom extends OpstaSo{
 
    List<Prisustvo > prisustvo;
      KriterijumWhere kriterijum;
    
    public SONadjiPrisustvoSaUslovom(KriterijumWhere kriterijum) {
        super();
        prisustvo=new ArrayList<>();
        this.kriterijum=kriterijum;
    }

    @Override
    void operacija(OpstiDomenskiObjekat od) {
        List<OpstiDomenskiObjekat> lista=new ArrayList<>();
        if(kriterijum==KriterijumWhere.PRETRAGA_PO_ID_TERMINA)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_ID_TERMINA);
       
        
        if(lista==null || lista.isEmpty()){
            setPoruka("Sistem ne moze da nadje prisustvo po zadatoj vrednosti.");
            setUspesno(false);
            prisustvo=null;
        }else{
            setPoruka("Sistem je nasao prisustvo po zadatoj vrednosti.");
            setUspesno(true);
           List<Prisustvo> l=new ArrayList(lista);
            
           prisustvo=l;
        }
        
    }

    @Override
    public OpstiDomenskiObjekat vratiODO() {
          return null;
    }

    @Override
    public List vratiListu() {
        return prisustvo;
    }
}
