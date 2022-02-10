/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Clan;
import domen.Grupa;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;
import konfiguracija.KriterijumWhere;
import static so.OpstaSo.dbbr;

/**
 *
 * @author Ana
 */
public class SONadjiiGrupuSaUslovom extends OpstaSo{
    List<Grupa> grupe;
    KriterijumWhere kriterijum;
    
    public SONadjiiGrupuSaUslovom(KriterijumWhere kriterijum) {
        super();
        grupe=new ArrayList<>();
        this.kriterijum=kriterijum;
    }

    @Override
    void operacija(OpstiDomenskiObjekat od) {
        List<OpstiDomenskiObjekat> lista=new ArrayList<>();
       /*treba*/ if(kriterijum==KriterijumWhere.PRETRAGA_PO_ID_TRENERA)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_ID_TRENERA);
       if(kriterijum==KriterijumWhere.PRETRAGA_PO_ID_GRUPE)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_ID_GRUPE);
        /*treba*/if(kriterijum==KriterijumWhere.PRETRAGA_PO_IMENU)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_IMENU);
       
        
        if(lista==null || lista.isEmpty()){
            setPoruka("Sistem ne moze da nadje " + odo.vratiNazivTabele()+"e po zadatoj vrednosti.");
            setUspesno(false);
            grupe=null;
        }else{
            setPoruka("Sistem je nasao  " + odo.vratiNazivTabele()+"e po zadatoj vrednosti.");
            setUspesno(true);
           List<Grupa> l=new ArrayList(lista);
            grupe=l;
            odo=grupe.get(0);
        }
        
    }

    @Override
    public OpstiDomenskiObjekat vratiODO() {
          return odo;
    }

    @Override
    public List vratiListu() {
        return grupe;
    }
    
}
