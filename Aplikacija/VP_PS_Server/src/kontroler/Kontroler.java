/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import baza.DBBroker;
import domen.Clan;
import domen.Grupa;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.Prisustvo;
import domen.Termin;
import domen.Trener;
import forme.FrmMain;
import forme.ModelKorisnici;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import konfiguracija.KriterijumWhere;
import niti.ServerskaNit_KontrolerPL;
import so.*;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Ana
 */
public class Kontroler {

    private static Kontroler instance;
    ServerskiOdgovor odgovor;
    ServerskaNit_KontrolerPL serverNit;
    FrmMain forma;

    private Kontroler() {

    }

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    public ServerskiOdgovor ulogujKorisnika(Korisnik k) throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sop = new SOPrijava();
            sop.izvrsiOperaciju(k);
            odgovor.setPoruka(sop.getPoruka());
            odgovor.setStatusOdgovora(sop.isUspesno());
        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor sacuvajTrenera(Trener t) {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sokt = new SOZapamtiTrenera();
            sokt.izvrsiOperaciju(t);
            odgovor.setPoruka(sokt.getPoruka());
            odgovor.setStatusOdgovora(sokt.isUspesno());
            odgovor.setPodaci(sokt.vratiODO());

        } catch (Exception ex) {
            odgovor.setException(ex);
            //throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor sacuvajClana(Clan clan) {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sokc = new SOZapamtiClana();
            sokc.izvrsiOperaciju(clan);
            odgovor.setPodaci(sokc.vratiODO());
            odgovor.setPoruka(sokc.getPoruka());
            odgovor.setStatusOdgovora(sokc.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            // throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor sacuvajTermin(Termin termin) throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sozt = new SOAzurirajTermin();
            sozt.izvrsiOperaciju(termin);

            odgovor.setPoruka(sozt.getPoruka());
            odgovor.setStatusOdgovora(sozt.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor kreirajGrupu(Grupa grupa) {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sokg = new SOZapamtiGrupu();
            sokg.izvrsiOperaciju(grupa);
            odgovor.setPoruka(sokg.getPoruka());
            odgovor.setStatusOdgovora(sokg.isUspesno());
            odgovor.setPodaci(sokg.vratiODO());

        } catch (Exception ex) {
            odgovor.setException(ex);
            // throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiClanove() throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovc = new SOUcitajListuClanova();
            sovc.izvrsiOperaciju(new Clan());
            odgovor.setPodaci(sovc.vratiListu());
            odgovor.setPoruka(sovc.getPoruka());
            odgovor.setStatusOdgovora(sovc.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;

    }

    public ServerskiOdgovor vratiTrenere() throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovt = new SOUcitajListuTrenera();
            sovt.izvrsiOperaciju(new Trener());
            odgovor.setPodaci(sovt.vratiListu());
            odgovor.setPoruka(sovt.getPoruka());
            odgovor.setStatusOdgovora(sovt.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiGrupe() throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovg = new SOUcitajListuGrupa();
            sovg.izvrsiOperaciju(new Grupa());
            odgovor.setPodaci(sovg.vratiListu());
            odgovor.setPoruka(sovg.getPoruka());
            odgovor.setStatusOdgovora(sovg.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiTermine() throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovt = new SOUcitajListuTermina();
            sovt.izvrsiOperaciju(new Termin());
            odgovor.setPodaci(sovt.vratiListu());
            odgovor.setPoruka(sovt.getPoruka());
            odgovor.setStatusOdgovora(sovt.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiTrenereSaUslovom(Trener trener, KriterijumWhere kriterijum) throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovtu = new SONadjiTrenereSaUslovom(kriterijum);
            sovtu.izvrsiOperaciju(trener);
            odgovor.setPodaci(sovtu.vratiListu());
            odgovor.setPoruka(sovtu.getPoruka());
            odgovor.setStatusOdgovora(sovtu.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor VratiClanoveSaUslovom(Clan clan, KriterijumWhere kriterijum) throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovcu = new SONadjiClanoveSaUslovom(kriterijum);
            sovcu.izvrsiOperaciju(clan);
            odgovor.setPodaci(sovcu.vratiListu());
            odgovor.setPoruka(sovcu.getPoruka());
            odgovor.setStatusOdgovora(sovcu.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiGrupeSaUslovom(Grupa grupa, KriterijumWhere kriterijum) throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovgu = new SONadjiiGrupuSaUslovom(kriterijum);
            sovgu.izvrsiOperaciju(grupa);
            odgovor.setPodaci(sovgu.vratiListu());
            odgovor.setPoruka(sovgu.getPoruka());
            odgovor.setStatusOdgovora(sovgu.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiTermineSaUslovom(Termin termin, KriterijumWhere krit) {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovt = new SONadjiTermineSaUslovom(krit);
            sovt.izvrsiOperaciju(termin);
            odgovor.setPodaci(sovt.vratiListu());
            odgovor.setPoruka(sovt.getPoruka());
            odgovor.setStatusOdgovora(sovt.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiPrisustvoSaUslovom(Prisustvo prisustvo, KriterijumWhere kriterijum) throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovpu = new SONadjiPrisustvoSaUslovom(kriterijum);
            sovpu.izvrsiOperaciju(prisustvo);
            odgovor.setPodaci(sovpu.vratiListu());
            odgovor.setPoruka(sovpu.getPoruka());
            odgovor.setStatusOdgovora(sovpu.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiGrupu(Grupa grupa, KriterijumWhere krit) throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovgu = new SOUcitajGrupu(krit);
            sovgu.izvrsiOperaciju(grupa);
            odgovor.setPodaci(sovgu.vratiODO());
            odgovor.setPoruka(sovgu.getPoruka());
            odgovor.setStatusOdgovora(sovgu.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiTrenera(Trener t) throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovt = new SOUcitajTrenera();
            sovt.izvrsiOperaciju(t);
            odgovor.setPodaci(sovt.vratiODO());
            odgovor.setPoruka(sovt.getPoruka());
            odgovor.setStatusOdgovora(sovt.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiClana(Clan c, KriterijumWhere kw) {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovc = new SOUcitajClana(kw);
            sovc.izvrsiOperaciju(c);
            odgovor.setPodaci(sovc.vratiODO());
            odgovor.setPoruka(sovc.getPoruka());
            odgovor.setStatusOdgovora(sovc.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor vratiTermin(Termin termin) {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo sovc = new SOUcitajTermin();
            sovc.izvrsiOperaciju(termin);
            odgovor.setPodaci(sovc.vratiODO());
            odgovor.setPoruka(sovc.getPoruka());
            odgovor.setStatusOdgovora(sovc.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor obrisiTrenera(Trener trener) throws Exception {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo soot = new SOObrisiTrenera();
            soot.izvrsiOperaciju(trener);

            odgovor.setPoruka(soot.getPoruka());
            odgovor.setStatusOdgovora(soot.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor kreirajPrisustvo(Prisustvo p) throws Exception {
        odgovor = new ServerskiOdgovor();
        try {

            OpstaSo soklp = new SOKreirajPrisustvo();
            soklp.izvrsiOperaciju(p);

            odgovor.setPoruka(soklp.getPoruka());
            odgovor.setStatusOdgovora(soklp.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;

    }

    public ServerskiOdgovor obrisiTermin(Termin t) {
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo soot = new SOObrisiTermin();
            soot.izvrsiOperaciju(t);

            odgovor.setPoruka(soot.getPoruka());
            odgovor.setStatusOdgovora(soot.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public ServerskiOdgovor sacuvajListuTermina(List list) {
        List<Termin> termini = list;
        odgovor = new ServerskiOdgovor();
        try {
            OpstaSo soot = new SOZapamtiTermin(termini);
            soot.izvrsiOperaciju(new Termin());

            odgovor.setPoruka(soot.getPoruka());
            odgovor.setStatusOdgovora(soot.isUspesno());

        } catch (Exception ex) {
            odgovor.setException(ex);
            throw ex;
        }
        return odgovor;
    }

    public boolean pokreniServer() {

        try {
            serverNit = new ServerskaNit_KontrolerPL();

            if (!serverNit.isAlive()) {
                serverNit.start();
                return true;

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public boolean zaustaviServer() {
        if (serverNit.getServerSocket() != null && serverNit.getServerSocket().isBound()) {
            try {
                serverNit.stopServer();
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void pokreniAplikaciju() {
        forma = new FrmMain();
        forma.getTxtStatus().setText("Server nije pokrenut");
        forma.getTxtStatus().setForeground(Color.red);
        forma.getBtnZaustavi().setEnabled(false);

        dodajOsluskivace();
        ModelKorisnici mk = new ModelKorisnici(new ArrayList<>());
        forma.getTbl().setModel(mk);
        forma.setVisible(true);

    }

    private void dodajOsluskivace() {

        forma.getBtnPokreni().addActionListener(a -> pokreni());
        forma.getBtnZaustavi().addActionListener(a -> zaustavi());

    }

    private void pokreni() {
        if (pokreniServer()) {
            forma.getBtnZaustavi().setEnabled(true);
            forma.getTxtStatus().setText("Server je pokrenut");
            forma.getTxtStatus().setForeground(Color.green);
            forma.getBtnPokreni().setEnabled(false);
        }

    }

    private void zaustavi() {
        if (zaustaviServer()) {
            forma.getBtnPokreni().setEnabled(true);
            forma.getTxtStatus().setText("Server je zaustavljen");
            forma.getTxtStatus().setForeground(Color.red);
            forma.getBtnZaustavi().setEnabled(false);
        }
    }

    public void dodajKorisnika(Korisnik korisnik) {

        ModelKorisnici mk = (ModelKorisnici) forma.getTbl().getModel();
        mk.dodajKorisika(korisnik);
    }

    public void izbaciKorisnika(Korisnik korisnik) {
        ModelKorisnici mk = (ModelKorisnici) forma.getTbl().getModel();
        mk.izbaciKorisnka(korisnik);

    }

}
