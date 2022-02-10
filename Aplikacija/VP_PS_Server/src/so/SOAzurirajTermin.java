/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Clan;
import domen.Grupa;
import domen.OpstiDomenskiObjekat;
import domen.Termin;
import java.util.Date;
import java.util.List;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class SOAzurirajTermin extends OpstaSo {

    public SOAzurirajTermin() {
        super();

    }

    @Override
    void operacija(OpstiDomenskiObjekat od) {
        boolean uspesno = true;
        if (dbbr.daLiPostojiObjekat(od, KriterijumWhere.PRIMARAN_KLJUC)) {
            uspesno = dbbr.azurirajObjekat(od, KriterijumWhere.PRIMARAN_KLJUC);
        } else {
            uspesno = dbbr.UbaciNoviObjekat(od);
        }

        if (uspesno == false) {
            setPoruka("Sistem ne moze da zapamti termin.");
            setUspesno(false);

        }
        if (uspesno == true) {
            setPoruka("Sistem je zapamtio termin.");
            setUspesno(true);
        }

    }

    @Override
    protected boolean proveriVrednosnaOgranicenja() {
        //proveri da li je datum termina u buducnosti
        Termin t = (Termin) odo;
        if (t.getDatum().after(new Date()) && !dbbr.daLiPostojiObjekat(t, KriterijumWhere.PRETRAGA_PO_DATUMU_I_VREMENU)){
               
            return true;
        } else {
            //setPoruka("Sistem ne moze da zapamti termin.\nNaruseno je vrednosno ogranicenje.\n Datum je u proslosti.");
            setPoruka("Sistem ne moze da zapamti termin.");
        }
        return false;
    }

   /*  @Override
    protected boolean proveriStrukturnaOgranicenja() {
  
         Termin t=(Termin)odo;
         Grupa grupa=t.getGrupa();
         
        if(grupa!=null ) {
            List<OpstiDomenskiObjekat> listaGrupeODO=dbbr.vratiSveObjekteSaUslovom(grupa, KriterijumWhere.PRETRAGA_PO_ID_GRUPE);
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
                    setPoruka("Sistem ne moze da zapamti termin.");
                    return false;
                }
               
            }
        }
        }
                return true;

    }
   */
}
