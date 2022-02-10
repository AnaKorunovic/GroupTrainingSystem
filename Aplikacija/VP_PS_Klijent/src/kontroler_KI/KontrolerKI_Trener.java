/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler_KI;

import domen.Termin;
import domen.Trener;
import ekranske_forme.EkranskaFormaNoviTrener;
import ekranske_forme.EkranskaFormaPregledTrenera;
import ekranske_forme.OpstaEkranskaForma;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import konfiguracija.Operacije;
import model_tabele.TableModelTrener;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Ana
 */
public class KontrolerKI_Trener extends OpstiKKI {

    private static  KontrolerKI_Trener instance;
    EkranskaFormaNoviTrener efnt;
    EkranskaFormaPregledTrenera efpt;

    public static KontrolerKI_Trener getInstance() {
        if(instance==null) instance=new KontrolerKI_Trener();
        return instance;
    }

    private KontrolerKI_Trener() {
        
   }

    KontrolerKI_Trener(Trener trener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    public void dodajTrenera() {
       efnt=new EkranskaFormaNoviTrener(); 
       dodajOsluskivace1();
       efnt.getBtnObrisi().setVisible(false);
       efnt.getBtnAzuriraj().setVisible(false);
       efnt.setVisible(true);
    }
    private void dodajOsluskivace1() {
        efnt.getBtnKreiraj().addActionListener(a->zapamtiTrenera());
        efnt.getBtnZatvori().addActionListener(a->{efnt.dispose();popuniTabelu();});
        efnt.getBtnObrisi().addActionListener(a->obrisiTrenera());
        efnt.getBtnAzuriraj().addActionListener(a->zapamtiTrenera());
        
    }
    private void dodajOsluskivace2() {
        efpt.getBtnUkloni().addActionListener(a->popuniTabelu());
        efpt.getjButton1().addActionListener(a->efpt.dispose());
        efpt.getBtnFiltriraj().addActionListener(a->filtriranjeTabele());
        efpt.getBtnDetalji().addActionListener(a->prikaziDetalje());
        
    }
    
     private void zapamtiTrenera() {
         String ime=efnt.getTxtIme();
         String pr=efnt.getTxtPrezime();
         String email=efnt.getTxtEmail();
         String br=efnt.getTxtMobilni();
        
        String poruka="";
        if(ime.isEmpty())poruka+="Niste uneli ime trenera.\n";
        if(pr.isEmpty())poruka+="Niste uneli prezime trenera.\n";
        if(br.isEmpty())poruka+="Niste uneli broj mobilnog telefona trenera.\n";
        if(email.isEmpty())poruka+="Niste uneli email trenera.\n";
        
        if(!poruka.isEmpty()) {
            efnt.prikaziPoruku(poruka, "Neuspesno operacija");
            return;
        }
        Trener t=new Trener();
        t.setIme(ime);
        t.setPrezime(pr);
        t.setEmailAdresa(email);
        t.setBrojTelefona(br);
        odo=t;
        try{
            odgovor=poziviSO(Operacije.KREIRAJ_TRENERA,t);
            if(odgovor.isStatusOdgovora()) {
                efnt.prikaziPoruku(odgovor.getPoruka(),"Uspesno je sacuvan trener!");
                efnt.dispose();
            }else{
                efnt.prikaziPoruku(odgovor.getPoruka(),"Neuspesno cuvanje trener!");
            }
        } catch (Exception ex) {
            efnt.prikaziPoruku(ex.getMessage(),"Greska prilikom cuvanja trenera!");
        }
        
    }

    void pregledTrenera() {
        efpt=new EkranskaFormaPregledTrenera();
        dodajOsluskivace2();
        popuniTabelu();
        efpt.setVisible(true);
    }

    private void popuniTabelu() {
        odo=new Trener();
        
        try{
        odgovor=SOPretrazi(Operacije.VRATI_SVE_TRENERE);

        if(odgovor.isStatusOdgovora()) {
               efpt.prikaziPoruku(odgovor.getPoruka(),"");
                TableModelTrener tmt=new TableModelTrener((List)odgovor.getPodaci());
                efpt.getTbl().setModel(tmt);
            } else {
                efpt.prikaziPoruku(odgovor.getPoruka(),"");
                efpt.dispose();
            }
        } catch (Exception ex) {
            efpt.prikaziPoruku(ex.getMessage(),"Greska");
            ex.printStackTrace();
        }

    }

    private void filtriranjeTabele() {
         if (!efpt.getTxtIme().isEmpty()) {
             try {
                 Trener t = new Trener();
                 t.setIme(efpt.getTxtIme());
                 odo=t;
                 odgovor=SOPretrazi(Operacije.VRATI_TRENERE_SAUSLOVOM_IMETRENERA);
                 if (odgovor.isStatusOdgovora()) {
                     efpt.prikaziPoruku(odgovor.getPoruka(),"");
                     TableModelTrener tmt = new TableModelTrener((List) odgovor.getPodaci());
                     efpt.getTbl().setModel(tmt);
                 } else {
                     efpt.prikaziPoruku(odgovor.getPoruka(),"");
                 }
             } catch (Exception ex) {
                 efpt.prikaziPoruku(ex.getMessage(),"Greska");
             }
            }else efpt.prikaziPoruku("Polje za unos imena ne sme biti prazno!","Upozorenje");

       
    }

    private void prikaziDetalje() {
        int row = efpt.getTbl().getSelectedRow();
        if (row == -1) {
            efpt.prikaziPoruku("Nije selektovan red.","");
        } else {
            TableModelTrener tmt = (TableModelTrener) efpt.getTbl().getModel();
            Trener t = tmt.vratiTrenere().get(row);
            odo=t;
            try{
            odgovor=SOPretrazi(Operacije.VRATI_TRENERA_SAUSLOVOM_IDTRENERA);
             if (odgovor.isStatusOdgovora()) {
                efpt.prikaziPoruku(odgovor.getPoruka(), "Uspesno ucitavanje trenera!");
               odo=(Trener)odgovor.getPodaci();
                efnt = new EkranskaFormaNoviTrener();
                dodajOsluskivace1();
                
                efnt.setTxtEmail(((Trener)odgovor.getPodaci()).getEmailAdresa());
                efnt.setTxtMobilni(((Trener)odgovor.getPodaci()).getBrojTelefona());
                efnt.setTxtIme(((Trener)odgovor.getPodaci()).getIme());
                efnt.setTxtPrezime(((Trener)odgovor.getPodaci()).getPrezime());
                
               // efnt.getIme().setEditable(false);
                //efnt.getPrezime().setEditable(false);
                //efnt.getEmail().setEditable(false);
                //efnt.getMob().setEditable(false);
                efnt.getBtnKreiraj().setVisible(false);
                efnt.getBtnAzuriraj().setVisible(true);
                efnt.setVisible(true);
               
                //popuniTabelu();
            } else {
                 efpt.prikaziPoruku( odgovor.getPoruka(), "Neuspesno ucitavanje trenera!");
            }
        
            }catch (Exception ex) {
                 efpt.prikaziPoruku( "Sistem ne moze da ucita trenera.", "Greska");
            }

    }
        
    }

    private void obrisiTrenera() {
        try {
           odgovor=SOObrisi(Operacije.OBRISI_TRENERA);
            if(odgovor.isStatusOdgovora()) {
                efnt.prikaziPoruku(odgovor.getPoruka(),"Uspesno brisanje trenera");
                efnt.dispose();
                popuniTabelu();
            }else{
               efnt.prikaziPoruku(odgovor.getPoruka(),"Greska");
            }
         
        } catch (Exception ex) {
           efnt.prikaziPoruku(odgovor.getPoruka(),"Greska");
        }
        
    }

    
    
    

   

    
    
    

   

    



    
    
    
}
