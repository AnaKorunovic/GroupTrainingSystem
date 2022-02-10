/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import domen.Clan;
import domen.Grupa;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.Prisustvo;
import domen.Termin;
import domen.Trener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import konfiguracija.KriterijumWhere;
import konfiguracija.Operacije;
import kontroler.Kontroler;
import so.SOPrijava;
import so.*;

import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Ana
 */
public class ObradaKlijentskeNiti extends Thread {

    private Socket socket;
ServerskaNit_KontrolerPL server;
Korisnik korisnik;
    public ObradaKlijentskeNiti(Socket socket,ServerskaNit_KontrolerPL server) {
        this.socket = socket;
        this.server=server;
    }

    @Override
    public void run() {
        KlijentskiZahtev zahtev = null;
        ServerskiOdgovor odgovor = null;
        while (!socket.isClosed()) {
            try {
                zahtev = primiZahtev();
                OpstiDomenskiObjekat odo=null;
                Operacije o=zahtev.getOperacija();
                if(!o.equals(Operacije.ZAPAMTI_LISTU_TERMINA)) odo = (OpstiDomenskiObjekat) zahtev.getPodaci();
                odgovor = new ServerskiOdgovor();
                switch (o) {
                    
                    case PRIJAVA: {
                        odgovor=Kontroler.getInstance().ulogujKorisnika((Korisnik)odo);
                        if(odgovor.isStatusOdgovora()) {
                            korisnik=(Korisnik)odo;
                            Kontroler.getInstance().dodajKorisnika(korisnik);
                        }
                        
                            
                    } break;
                    case ODJAVA: {
                        Kontroler.getInstance().izbaciKorisnika(korisnik);

                        server.izbaciKlijenta(this);
                        this.socket.close();
                        System.out.println(korisnik.getKorisnickoIme());
                        return;
                    } 
                    case KREIRAJ_GRUPU:odgovor=Kontroler.getInstance().kreirajGrupu((Grupa)odo);break;
                    case KREIRAJ_TRENERA:  odgovor=Kontroler.getInstance().sacuvajTrenera((Trener)odo);break;
                    case KREIRAJ_CLANA:  odgovor=Kontroler.getInstance().sacuvajClana((Clan)odo);break;
                    case KREIRAJ_PRISUSTVO:odgovor=Kontroler.getInstance().kreirajPrisustvo((Prisustvo)odo);break;
                    case VRATI_CLANOVE_SAUSLOVOM_IDGRUPE:  odgovor=Kontroler.getInstance().VratiClanoveSaUslovom((Clan)odo,KriterijumWhere.PRETRAGA_PO_ID_GRUPE);break;
                    case VRATI_GRUPU_SAUSLOVOM_IMEGRUPE: odgovor=Kontroler.getInstance().vratiGrupu((Grupa)odo, KriterijumWhere.PRETRAGA_PO_IMENU);break;
                    case VRATI_GRUPU_SAUSLOVOM_IDGRUPE: odgovor=Kontroler.getInstance().vratiGrupu((Grupa)odo,KriterijumWhere.PRETRAGA_PO_ID_GRUPE);break;
                    case VRATI_TRENERE_SAUSLOVOM_IMETRENERA: odgovor=Kontroler.getInstance().vratiTrenereSaUslovom((Trener)odo,KriterijumWhere.PRETRAGA_PO_IMENU);break;
                    case VRATI_TRENERA_SAUSLOVOM_IDTRENERA: odgovor=Kontroler.getInstance().vratiTrenera((Trener)odo);break;
                    case VRATI_CLANA:odgovor=Kontroler.getInstance().vratiClana((Clan)odo,KriterijumWhere.PRIMARAN_KLJUC);break;
                    case VRATI_CLANA_SAUSLOVOM_IDCLANA:odgovor=Kontroler.getInstance().vratiClana((Clan)odo,KriterijumWhere.PRETRAGA_PO_ID_CLANA);break;
                    case VRATI_SVE_TRENERE:odgovor=Kontroler.getInstance().vratiTrenere();break;
                    case OBRISI_TRENERA: odgovor=Kontroler.getInstance().obrisiTrenera((Trener)odo); break;
                    case OBRISI_TERMIN: odgovor=Kontroler.getInstance().obrisiTermin((Termin)odo); break;
                    case VRATI_SVE_GRUPE:   odgovor=Kontroler.getInstance().vratiGrupe();break;
                    case VRATI_SVE_CLANOVE: odgovor=Kontroler.getInstance().vratiClanove();break;
                    case VRATI_SVE_TERMINE: odgovor=Kontroler.getInstance().vratiTermine();break;
                    case VRATI_GRUPE_SAUSLOVOM_IDTRENERA:odgovor=Kontroler.getInstance().vratiGrupeSaUslovom((Grupa)odo,KriterijumWhere.PRETRAGA_PO_ID_TRENERA); break;
                    case VRATI_TERMINE_SAUSLOVOM_DANGRUPA: odgovor=Kontroler.getInstance().vratiTermineSaUslovom((Termin)odo,KriterijumWhere.PRETRAGA_PO_GRUPI_DATUMU);break;
                    case VRATI_TERMINE_SAUSLOVOM_DAN: odgovor=Kontroler.getInstance().vratiTermineSaUslovom((Termin)odo,KriterijumWhere.PRETRAGA_PO_DATUMU);break;
                     case VRATI_TERMINE_SAUSLOVOM_GRUPA: odgovor=Kontroler.getInstance().vratiTermineSaUslovom((Termin)odo,KriterijumWhere.PRETRAGA_PO_ID_GRUPE);break;
                    case VRATI_TERMINE_SAUSLOVOM_DODAN: odgovor=Kontroler.getInstance().vratiTermineSaUslovom((Termin)odo,KriterijumWhere.PRETRAGA_PO_DODATUMA);break;
                    case ZAPAMTI_TERMIN: odgovor=Kontroler.getInstance().sacuvajTermin((Termin)odo);break;  
                    case VRATI_PRISUSTVO_SAUSLOVOM_IDTERMINA: odgovor=Kontroler.getInstance().vratiPrisustvoSaUslovom((Prisustvo)odo,KriterijumWhere.PRETRAGA_PO_ID_TERMINA);break;
                    case ZAPAMTI_CLANA:odgovor=Kontroler.getInstance().sacuvajClana((Clan)odo);break;
                    case VRATI_TERMIN:odgovor=Kontroler.getInstance().vratiTermin((Termin)odo);break;
                    case ZAPAMTI_LISTU_TERMINA:odgovor=Kontroler.getInstance().sacuvajListuTermina((List)zahtev.getPodaci());
                    
                }
                posaljiOdgovor(odgovor);
            } catch (Exception ex) {
               // ex.printStackTrace();
            }
            
        }
        server.izbaciKlijenta(this);
    }

    public KlijentskiZahtev primiZahtev() throws IOException, ClassNotFoundException {
        
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (KlijentskiZahtev) in.readObject();
       
    }

    public void posaljiOdgovor(ServerskiOdgovor odgovor) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(odgovor);
        out.flush();
    }

   
    public Socket getSocket() {
        return socket;
    }
   
}
