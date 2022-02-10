/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Clan;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;
import konfiguracija.KriterijumWhere;
import static so.OpstaSo.dbbr;

/**
 *
 * @author Ana
 */
public class SONadjiClanoveSaUslovom extends OpstaSo{
      List<Clan> clanovi;
      KriterijumWhere kriterijum;
    
    public SONadjiClanoveSaUslovom(KriterijumWhere kriterijum) {
        super();
       // odo=clan;
        clanovi=new ArrayList<>();
        this.kriterijum=kriterijum;
    }

    @Override
    void operacija(OpstiDomenskiObjekat od) {
        List<OpstiDomenskiObjekat> lista=new ArrayList<>();
        if(kriterijum==KriterijumWhere.PRETRAGA_PO_IMENU)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_IMENU);
        if(kriterijum==KriterijumWhere.PRETRAGA_PO_ID_GRUPE)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_ID_GRUPE);
        if(kriterijum==KriterijumWhere.PRETRAGA_PO_ID_CLANA)lista=dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRIMARAN_KLJUC);
        
        if(lista==null || lista.isEmpty()){
            setPoruka("Sistem ne moze da nadje clanove po zadatoj vrednosti.");
            setUspesno(false);
        }else{
            setPoruka("Sistem je nasao clanove po zadatoj vrednosti.");
            setUspesno(true);
           List<Clan> l=new ArrayList(lista);
            
            clanovi=l;
        }
        
    }

    @Override
    public OpstiDomenskiObjekat vratiODO() {
          return null;
    }

    @Override
    public List vratiListu() {
        return clanovi;
    }
    
}
