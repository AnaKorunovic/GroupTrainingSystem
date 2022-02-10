/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.Trener;
import java.util.ArrayList;
import java.util.List;
import konfiguracija.KriterijumWhere;
import static so.OpstaSo.dbbr;

/**
 *
 * @author Ana
 */
public class SONadjiTrenereSaUslovom extends OpstaSo {

    List<Trener> treneri;
    KriterijumWhere kriterijum;

    public SONadjiTrenereSaUslovom(KriterijumWhere kriterijum) {
        super();
        treneri = new ArrayList<>();
        this.kriterijum=kriterijum;
    }

    @Override
    void operacija(OpstiDomenskiObjekat od) {
        List<OpstiDomenskiObjekat> lista=new ArrayList<>();
      /*upotrebili*/ if(kriterijum==KriterijumWhere.PRETRAGA_PO_IMENU) lista = dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRETRAGA_PO_IMENU);
       if(kriterijum==KriterijumWhere.PRETRAGA_PO_ID_TRENERA) lista = dbbr.vratiSveObjekteSaUslovom(od,KriterijumWhere.PRIMARAN_KLJUC);
       
        if (lista == null || lista.isEmpty()) {
            setPoruka("Sistem ne moze da nadje trenere po zadatoj vrednosti.");

            setUspesno(false);
        } else {
            setPoruka("Sistem je nasao trenere po zadatoj vrednosti.");
            System.out.println(lista);
            setUspesno(true);

            List<Trener> l = new ArrayList(lista);

          treneri=l;
        }

    }

    @Override
    public OpstiDomenskiObjekat vratiODO() {
        return null;
    }

    @Override
    public List vratiListu() {
        return treneri;
    }
}
