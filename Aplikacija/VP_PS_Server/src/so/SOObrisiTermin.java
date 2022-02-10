/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Clan;
import domen.Grupa;
import domen.OpstiDomenskiObjekat;
import domen.Termin;
import java.util.List;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class SOObrisiTermin extends OpstaSo {

    public SOObrisiTermin() {
        super();

    }

    @Override
    void operacija(OpstiDomenskiObjekat od) {

        if (!dbbr.obrisiObjekat(od)) {
            setPoruka("Sistem ne moze da obrise termin.");
            setUspesno(false);

        } else {
            setPoruka("Sistem je obrisao termin.");
            setUspesno(true);

        }
    }

    @Override
    public OpstiDomenskiObjekat vratiODO() {
        return null;
    }

    @Override
    protected boolean proveriStrukturnaOgranicenja() {

        Termin t = (Termin) odo;
        Grupa grupa = t.getGrupa();

        if (grupa != null) {
            List<OpstiDomenskiObjekat> listaGrupeODO = dbbr.vratiSveObjekteSaUslovom(grupa, KriterijumWhere.PRETRAGA_PO_ID_GRUPE);
            if (listaGrupeODO == null) {
                return true;
            } else {

                for (OpstiDomenskiObjekat og : listaGrupeODO) {
                    Grupa g = (Grupa) og;
                    Clan c = new Clan();
                    c.setGrupa(grupa);
                    List<OpstiDomenskiObjekat> listaClanoviODO = dbbr.vratiSveObjekteSaUslovom(c, KriterijumWhere.PRETRAGA_PO_ID_GRUPE);
                    if (listaClanoviODO != null) {

                        //setPoruka("Sistem ne moze da obrise trenera.\nNaruseno je strukturno ogranicenje.");
                        setPoruka("Sistem ne moze da obrise termin.");
                        return false;
                    }

                }
            }
        }
        return true;

    }

}
