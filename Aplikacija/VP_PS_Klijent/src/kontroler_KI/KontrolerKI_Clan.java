/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler_KI;

import domen.Clan;
import domen.Grupa;
import domen.Trener;

import ekranske_forme.EkranskaFormaNoviClan;
import ekranske_forme.EkranskaFormaNoviTrener;
import ekranske_forme.EkranskaFormaPregledClanova;
import ekranske_forme.OpstaEkranskaForma;
import java.util.ArrayList;
import java.util.List;
import konfiguracija.Operacije;
import model_tabele.TableModelClan;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Ana
 */
public class KontrolerKI_Clan extends OpstiKKI{
    
     private static  KontrolerKI_Clan instance;
    EkranskaFormaNoviClan efnc;
    EkranskaFormaPregledClanova efpc;

    public static KontrolerKI_Clan getInstance() {
        if(instance==null) instance=new KontrolerKI_Clan();
        return instance;
    }

    private KontrolerKI_Clan() {
    }
    

    public void dodajClana() {
       efnc=new EkranskaFormaNoviClan(); 
       fillCBGrupe();
       dodajOsluskivace1();
       efnc.getBtnZapamti().setVisible(false);
       efnc.setVisible(true);
       
    }
    private void dodajOsluskivace1() {
        efnc.getBtnKreiraj().addActionListener(a->zapamtiClana());
        efnc.getBtnZatvori().addActionListener(a->{efnc.dispose();popuniTabelu();});
        efnc.getBtnZapamti().addActionListener(a->zapamtiClana());
        
    }
    private void dodajOsluskivace2() {
        efpc.getBtnukloni().addActionListener(a->popuniTabelu());
        efpc.getBtnzatvori().addActionListener(a->efpc.dispose());
        efpc.getBtnFiltriraj().addActionListener(a->filtriranjeTabele());
        efpc.getBtnDetalji().addActionListener(a->prikaziDetalje());
        efpc.getBtnDodaj().addActionListener(a->dodajClana());
        
    }
    
     private void zapamtiClana() {
        String ime=efnc.getTxtIme();
        String pr=efnc.getTxtPrezime();
        String jmbg=efnc.getJMBG();
        String br=efnc.getTxtMobilni();
        Grupa g=efnc.getCbGrupe1();
        
        String poruka="";
        if(ime.isEmpty())poruka+="Niste uneli ime clana.\n";
        if(pr.isEmpty())poruka+="Niste uneli prezime clana.\n";
        if(br.isEmpty())poruka+="Niste uneli broj mobilnog telefona clana.\n";
        if(jmbg.isEmpty())poruka+="Niste uneli jmbg clana.\n";
        
        if(!poruka.isEmpty()) {
            efnc.prikaziPoruku(poruka, "Neuspesno kreiranje clana");
            return;
        }
        Clan c=new Clan();
        c.setIme(ime);
        c.setPrezime(pr);
        c.setJmbg(jmbg);
        c.setBrojTelefona(br);
        c.setGrupa(g);
        odo=c;
        try{
            odgovor=poziviSO(Operacije.KREIRAJ_CLANA,c);
            if(odgovor.isStatusOdgovora()) {
                efnc.prikaziPoruku(odgovor.getPoruka(),"Uspesno kreiranje clana!");
                efnc.dispose();
            }else{
                efnc.prikaziPoruku(odgovor.getPoruka(),"Neuspesno kreiranje clana!");
            }
        } catch (Exception ex) {
            efnc.prikaziPoruku(ex.getMessage(),"Greska prilikom kreiranja clana!");
        }
        
    }

    void pregledClanova() {
        efpc=new EkranskaFormaPregledClanova();
        dodajOsluskivace2();
        popuniTabelu();
        efpc.setVisible(true);
    }

    private void popuniTabelu() {
        odo=new Clan();
        
        try{
        odgovor=SOPretrazi(Operacije.VRATI_SVE_CLANOVE);

        if(odgovor.isStatusOdgovora()) {
               efpc.prikaziPoruku(odgovor.getPoruka(),"");
                List<Clan> clanovi=(List) odgovor.getPodaci();
                for(Clan c:clanovi){
                    
                   c.setGrupa(KontrolerKI_Grupa.getInstance().vratiPotpunuGrupu(c.getGrupa()));
                }
                TableModelClan tmc = new TableModelClan(clanovi);
                efpc.getTbl().setModel(tmc);
            } else {
                efpc.prikaziPoruku(odgovor.getPoruka(),"");
                efpc.dispose();
            }
        } catch (Exception ex) {
            efpc.prikaziPoruku(ex.getMessage(),"Greska");
            ex.printStackTrace();
        }

    }

    private void filtriranjeTabele() {
         try {
            if (!efpc.getTxtgrupa().isEmpty()) {
                Clan c = new Clan();
                Grupa g = new Grupa();

                g.setImeGrupe(efpc.getTxtgrupa());
                odo=g;
                odgovor = SOPretrazi(Operacije.VRATI_GRUPU_SAUSLOVOM_IMEGRUPE);
                
                Grupa grupa=(Grupa)odgovor.getPodaci();
                c.setGrupa(grupa);
                odo=c;
                ServerskiOdgovor pom = SOPretrazi(Operacije.VRATI_CLANOVE_SAUSLOVOM_IDGRUPE);
                    if (pom.isStatusOdgovora()) {
                        efpc.prikaziPoruku(pom.getPoruka(),"Uspesno");

                        List<Clan> clanovi=(List) pom.getPodaci();
                        for(Clan cl:clanovi){
                            cl.setGrupa(grupa);
                        }
                        TableModelClan tmc = new TableModelClan(clanovi);
                        efpc.getTbl().setModel(tmc);
                    
                } else {
                        efpc.prikaziPoruku(pom.getPoruka(),"Greska prilikom nalazenja clanova");

                    }
            }

        }  catch (Exception ex) {
           efpc.prikaziPoruku(ex.getMessage(),"Greska");
        }

       
    }

    private void prikaziDetalje() {
        int row = efpc.getTbl().getSelectedRow();
        if (row == -1) {
            efpc.prikaziPoruku("Nije selektovan red.","");
        } else {
            TableModelClan tmc = (TableModelClan) efpc.getTbl().getModel();
            Grupa grupa = new Grupa();
            grupa.setGrupaId((int) tmc.getValueAt(row, 3));
            grupa = vratiPotpunuGrupu(grupa);
            Clan c = tmc.vratiClanove().get(row);
            c.setGrupa(grupa);
            odo=c;
            try{
            odgovor=SOPretrazi(Operacije.VRATI_CLANA);
             if (odgovor.isStatusOdgovora()) {
                efpc.prikaziPoruku(odgovor.getPoruka(), "Uspesno ucitavanje clana!");
               
               
               otvoriFormuClan((Clan)odo);
                
               
                //popuniTabelu();
            } else {
                 efpc.prikaziPoruku( odgovor.getPoruka(), "Neuspesno ucitavanje clana!");
            }
        
            }catch (Exception ex) {
                 efpc.prikaziPoruku( "Sistem ne moze da ucita clana.", "Greska");
            }

    }
        
    }

   

    private void fillCBGrupe() {
        efnc.getCbGrupe().removeAllItems();
        try {
          odo=new Grupa();
           odgovor=SOPretrazi(Operacije.VRATI_SVE_GRUPE);
            if(odgovor.isStatusOdgovora()) {
                efnc.prikaziPoruku(odgovor.getPoruka(),"");
               List<Grupa> grupe=new ArrayList<>((List)odgovor.getPodaci());
                for(Grupa g:grupe){
                   odo=g.getTrener();
                   ServerskiOdgovor pomocni=SOPretrazi(Operacije.VRATI_TRENERA_SAUSLOVOM_IDTRENERA);
                   g.setTrener((Trener)pomocni.getPodaci());
                    
                   efnc.getCbGrupe().addItem(g);
                }
            }
            
            else {
                efnc.prikaziPoruku( odgovor.getPoruka(),"");
                efnc.dispose();
            }
        } catch (Exception ex) {
            efnc.prikaziPoruku(ex.getMessage(),"Greska");
        }
    }

    private Grupa vratiPotpunuGrupu(Grupa grupa) {
       try {
           odo=grupa;
            odgovor = SOPretrazi(Operacije.VRATI_GRUPU_SAUSLOVOM_IDGRUPE);
            if (odgovor.isStatusOdgovora()) {
                grupa = (Grupa)odgovor.getPodaci();
                odo=grupa.getTrener();
                 ServerskiOdgovor pomocni = SOPretrazi(Operacije.VRATI_TRENERA_SAUSLOVOM_IDTRENERA);
                if (pomocni.isStatusOdgovora()) {
                    Trener trener = (Trener) pomocni.getPodaci();
                    grupa.setTrener(trener);
                    return grupa;
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return grupa;
    }

    private void otvoriFormuClan(Clan clan) {
        efnc = new EkranskaFormaNoviClan();
        dodajOsluskivace1();
                
         efnc.setJMBG(((Clan)odgovor.getPodaci()).getJmbg());
         efnc.setTxtMobilni(((Clan)odgovor.getPodaci()).getBrojTelefona());
         efnc.setTxtIme(((Clan)odgovor.getPodaci()).getIme());
         efnc.setTxtPrezime(((Clan)odgovor.getPodaci()).getPrezime());
         efnc.getJMBG1().setEditable(false);
         efnc.getBtnKreiraj().setVisible(false);
         fillCBGrupe();
         efnc.getCbGrupe().setSelectedItem(clan.getGrupa());
         efnc.setVisible(true);
         
    }
}
