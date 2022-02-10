/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler_KI;

import domen.Korisnik;
import ekranske_forme.EFPregledTermina;
import ekranske_forme.EkranskaFormaNovaGrupa;
import ekranske_forme.EkranskaFormaNoviClan;
import ekranske_forme.EkranskaFormaNoviTermin1;
import ekranske_forme.EkranskaFormaNoviTrener;
import ekranske_forme.EkranskaFormaPregledClanova;
import ekranske_forme.EkranskaFormaPregledGrupa;
import ekranske_forme.EkranskaFormaPregledTrenera;
import ekranske_forme.EkranskaFormaPrijava;
import ekranske_forme.FrmMain;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.Komunikacija;
import konfiguracija.Operacije;
import transfer.KlijentskiZahtev;

/**
 *
 * @author Ana
 */
public class KontrolerKI_Korisnik extends OpstiKKI {

    private static  KontrolerKI_Korisnik instance;
    Korisnik korisnik;
    
    
    public KontrolerKI_Korisnik() {
       
        oef=new EkranskaFormaPrijava();
        ((EkranskaFormaPrijava)oef).getBtnPrijava().addActionListener(a->prijava());
        
    }

    public static KontrolerKI_Korisnik getInstance() {
        if(instance==null)instance=new KontrolerKI_Korisnik();
        return instance;
    }
    public void pokreniAplikaciju() {
        ((EkranskaFormaPrijava)oef).setVisible(true);
    }

    private void prijava() {
        try {
            EkranskaFormaPrijava prijava=((EkranskaFormaPrijava)oef);
            String ki= prijava.getTxtKorisnickoIme();
            String sifra=prijava.getTxtSifra();
            String adresa=prijava.getTxtadresa();
            String port=prijava.getTxtport();
            String greska="";
            if(ki.isEmpty()) greska+="Morate popuniti korisnicko ime.\n";
            if(sifra.isEmpty()) greska+="Morate popuniti polje za sifru.\n";
            if(adresa.isEmpty()) greska+="Morate popuniti polje za IP adresu posta.\n";
            if(port.isEmpty()) greska+="Morate popuniti polje za port.\n";
            if(daliPostojiSlovo(port)) greska+="U polju za unos porta mora biti unet broj.\n";
            
            if(!greska.isEmpty()) {
                prijava.prikaziPoruku(greska,"Greska prilikom prijave");
                return;
            }
            
            upisiUProperies(adresa,port);
            otvoriKomunikaciju();
            
            Korisnik k=new Korisnik();
            k.setKorisnickoIme(ki);
            k.setSifra(sifra);
            odo=k;
            odgovor=poziviSO(Operacije.PRIJAVA,odo);
            
            if(odgovor.isStatusOdgovora()) {
                prijava.prikaziPoruku(odgovor.getPoruka(),"Uspesna prijava");
                FrmMain formaMain=new FrmMain();
                formaMain.setTitle("Dobrodosao "+ki+"!!! ");
                formaMain.setVisible(true);
                korisnik=k;
                prijava.dispose();
                oef=formaMain;
                postaviOsluskivace(formaMain);
            }else{
                prijava.prikaziPoruku(odgovor.getPoruka(),"Neuspesna prijava");
            }
        }    catch (Exception ex) {
            ((EkranskaFormaPrijava)oef).prikaziPoruku(ex.getMessage(),"Greška prilikom prijavljivanja na sistem!");

        }
}
    
    private void postaviOsluskivace(FrmMain formaMain) {
        formaMain.getMiDodajTrenera().addActionListener(a -> KontrolerKI_Trener.getInstance().dodajTrenera());
        formaMain.getMiPretraziTrenere().addActionListener(a -> KontrolerKI_Trener.getInstance().pregledTrenera());
        formaMain.getMiDodajClana().addActionListener(a->KontrolerKI_Clan.getInstance().dodajClana());
        formaMain.getMiPretraziClanove().addActionListener(a->KontrolerKI_Clan.getInstance().pregledClanova());
        formaMain.getjMenuItem6().addActionListener(a->KontrolerKI_Grupa.getInstance().dodajGrupu());
        formaMain.getjMenuItem5().addActionListener(a->KontrolerKI_Grupa.getInstance().pregledGrupa());
        formaMain.getjMenuItem7().addActionListener(a->KontrolerKI_Termin.getInstance().dodajTermin());
        formaMain.getMiPretraziTermine().addActionListener(a-> KontrolerKI_Termin.getInstance().pregledTermina());
        formaMain.getjMenuItem4().addActionListener(a->izlogujKorisnika());
        
        
    }

    public void izlogujKorisnika() {
        try {
            odo=korisnik;
            zahtev = new KlijentskiZahtev(Operacije.ODJAVA, odo);
            Komunikacija.getInstance().posaljiZahtev(zahtev);
            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(KontrolerKI_Korisnik.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean daliPostojiSlovo(String string) {

       char[] slova = string.toCharArray();
      boolean postojiSlovo=false;
      for(char c : slova){
         if(!Character.isDigit(c)){
            postojiSlovo=true;
         }
      }return postojiSlovo;
    }

    private void upisiUProperies(String adresa, String port) throws FileNotFoundException {
        try {
            Properties props = new Properties();
            props.clear();
            props.put("IPAdresa", adresa);
            props.put("port", port);
            String path = "config/serverConfig.properties";
            FileOutputStream outputStrem = new FileOutputStream(path);
            
            props.store(outputStrem,"");
            System.out.println("Kreiran je properi fajl");
        } catch (IOException ex) {
           ex.printStackTrace();
        }
      
    }

    private void otvoriKomunikaciju() {
       try {
            Komunikacija.getInstance();// otvara se klijentski soket
        } catch (IOException ex) {
            Logger.getLogger(OpstiKKI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}