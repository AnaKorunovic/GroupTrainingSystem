/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Clan;
import domen.Grupa;
import domen.OpstiDomenskiObjekat;
import java.util.List;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class SOZapamtiClana extends OpstaSo {

    public SOZapamtiClana() {
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
            setPoruka("Sistem ne moze da zapamti člana.");
            setUspesno(false);

        }
        if (uspesno == true) {
            setPoruka("Sistem je zapamtio člana.");
            setUspesno(true);
        }

    }

    @Override
    protected boolean proveriVrednosnaOgranicenja() {
        boolean uspesno = true;
        Clan clan = (Clan) odo;
        String ime = clan.getIme();
        String prezime = clan.getPrezime();
        String brojT = clan.getBrojTelefona();
        String jmbg=clan.getJmbg();
        if (ime.length() <= 2 || prezime.length() < 2 || brojT.length() < 8 || jmbg.length()<13) {
            uspesno = false;
        }
        if (daLiSadrziBrojeve(ime) || daLiSadrziBrojeve(prezime)) {
            uspesno = false;
        }
        if (!daLiSadrziBrojeve(brojT) || daLiSadrziSlova(jmbg)) {
            uspesno = false;
        }
         
        
        if (uspesno == false) {
            //setPoruka("Sistem ne moze da kreira clana.\nNaruseno je (prosto) vrednosno ogranicenje.");
            setPoruka("Sistem ne moze da zapamti člana.");
        }

        return uspesno;
    }

    @Override
    protected boolean proveriStrukturnaOgranicenja() {
        Clan c=(Clan)odo;
        Grupa g=c.getGrupa();
        if(!dbbr.daLiPostojiObjekat(g, KriterijumWhere.PRETRAGA_PO_ID_GRUPE)){
            setPoruka("Sistem ne moze da zapamti člana.");
            return false;
        
    }return true;
    }

    
}
