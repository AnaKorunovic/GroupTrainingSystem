/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.Termin;
import java.util.Date;
import java.util.List;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class SOZapamtiTermin extends OpstaSo {

    List<Termin> termini;

    public SOZapamtiTermin(List<Termin> t) {
        super();
        termini = t;

        
    }

    @Override
    void operacija(OpstiDomenskiObjekat od) {

        boolean uspesno = true;
        for (Termin t : termini) {
          
            if (t.getStatus() == 0) {
                uspesno = dbbr.UbaciNoviObjekat(t);
                System.out.println(uspesno);
            }
            if (t.getStatus() == 1) {
                uspesno = dbbr.azurirajObjekat(t, KriterijumWhere.PRIMARAN_KLJUC);
                System.out.println(uspesno);
                
            }
            if (t.getStatus() == 2) {
                uspesno = dbbr.obrisiObjekat(t);
                System.out.println(uspesno);
            }
            if (uspesno == false) {
                break;
            }

        }

        if (uspesno == false) {
            setPoruka("Sistem ne moze da zapamti listu termina.");
            setUspesno(false);

        }else{
            setPoruka("Sistem je zapamtio listu termina.");
            setUspesno(true);
        }

    }

    @Override
    protected boolean proveriVrednosnaOgranicenja() {
        //proveri da li je datum termina u buducnosti
        boolean uspesno=true;
        for (Termin t : termini) {
            if (!t.getDatum().after(new Date())) {
               uspesno=false;
               break;
            }
            if(t.getStatus()==0 && dbbr.daLiPostojiObjekat(t, KriterijumWhere.PRETRAGA_PO_DATUMU_I_VREMENU)){
                uspesno=false;
                System.out.println("so.SOZapamtiTermin.proveriVrednosnaOgranicenja()");
                       
                break;
            }
        }
        for(int i=0;i<termini.size()-1;i++){
            for(int j=i+1;j<termini.size();j++){
               if(termini.get(i).getStatus()==0 && termini.get(j).getStatus()==0 &&
                       termini.get(i).getVreme().equals(termini.get(j).getVreme()) &&
                               termini.get(i).getDatum().equals(termini.get(j).getDatum())) uspesno=false; 

            }
        }
        
        if(uspesno==false){
//setPoruka("Sistem ne moze da zapamti termin.\nNaruseno je vrednosno ogranicenje.\n Datum je u proslosti.");
                setPoruka("Sistem ne moze da zapamti listu termina1.");
                  return false;
        }return true;

    }
    }
