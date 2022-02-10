/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.Trener;
import java.util.regex.Pattern;
import konfiguracija.KriterijumWhere;
import static so.OpstaSo.dbbr;

/**
 *
 * @author Ana
 */
public class SOZapamtiTrenera extends OpstaSo {

    public SOZapamtiTrenera() {
        super();
        //odo=t;
        
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
            setPoruka("Sistem ne moze da zapamti trenera.");
            setUspesno(false);

        }
        if (uspesno == true) {
            setPoruka("Sistem je zapamtio trenera.");
            setUspesno(true);
        }
      
    }

    @Override
    protected boolean proveriVrednosnaOgranicenja() {
            boolean uspesno=true;
        Trener t=(Trener)odo;
        String ime=t.getIme();
        String prezime=t.getPrezime();
        String brojT=t.getBrojTelefona();
        String email=t.getEmailAdresa();
        if(ime.length()<=2 || prezime.length()<2 || brojT.length()<8 || email.length()<9)uspesno=false;
        if(daLiSadrziBrojeve(ime) || daLiSadrziBrojeve(prezime))uspesno=false;
        if(!daLiSadrziBrojeve(brojT))uspesno=false;
        
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(email).matches()) uspesno=false;
         if(uspesno==false) {
             //setPoruka("Sistem ne moze da kreira trenera.\nNaruseno je (prosto) vrednosno ogranicenje.");
             setPoruka("Sistem ne moze da zapamti trenera.");
         }
        return uspesno; 
    }

    
    
    



   

}
