/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Grupa;
import domen.OpstiDomenskiObjekat;
import domen.Termin;
import java.util.ArrayList;
import java.util.List;
import konfiguracija.KriterijumWhere;
import static so.OpstaSo.dbbr;

/**
 *
 * @author Ana
 */
public class SONadjiTermineSaUslovom extends OpstaSo{
    
    List<Termin> termini;
      KriterijumWhere kriterijum;
    
    public SONadjiTermineSaUslovom(KriterijumWhere kriterijum) {
        super();
        termini=new ArrayList<>();
        this.kriterijum=kriterijum;
    }

    @Override
    void operacija(OpstiDomenskiObjekat od) {
        List<OpstiDomenskiObjekat> lista=new ArrayList<>();
        if(kriterijum==KriterijumWhere.PRETRAGA_PO_GRUPI_DATUMU)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_GRUPI_DATUMU);
        if(kriterijum==KriterijumWhere.PRETRAGA_PO_DATUMU)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_DATUMU);
        if(kriterijum==KriterijumWhere.PRETRAGA_PO_DODATUMA)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_DODATUMA);
        if(kriterijum==KriterijumWhere.PRETRAGA_PO_ID_GRUPE)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_ID_GRUPE);
       
        
        if(lista==null || lista.isEmpty()){
            setPoruka("Sistem ne moze da nadje termine po zadatoj vrednosti.");
            setUspesno(false);
            termini=null;
        }else{
            setPoruka("Sistem je nasao termine po zadatoj vrednosti.");
            setUspesno(true);
           List<Termin> l=new ArrayList(lista);
            
           termini=l;
        }
        
    }

    @Override
    public OpstiDomenskiObjekat vratiODO() {
          return null;
    }

    @Override
    public List vratiListu() {
        return termini;
    }
    
}
