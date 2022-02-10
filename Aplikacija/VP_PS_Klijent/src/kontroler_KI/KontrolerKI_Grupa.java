/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler_KI;

import domen.Clan;
import domen.Grupa;
import domen.Trener;
import ekranske_forme.EkranskaFormaGrupa;
import ekranske_forme.EkranskaFormaNovaGrupa;
import ekranske_forme.EkranskaFormaPregledClanova;
import ekranske_forme.EkranskaFormaPregledGrupa;
import ekranske_forme.EkranskaFormaPregledTrenera;
import ekranske_forme.OpstaEkranskaForma;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import konfiguracija.Operacije;
import model_tabele.TableModelClan;
import model_tabele.TableModelGrupe;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Ana
 */
public class KontrolerKI_Grupa extends  OpstiKKI{
    private static  KontrolerKI_Grupa instance;
    EkranskaFormaNovaGrupa efng;
    EkranskaFormaPregledGrupa efpg;
    EkranskaFormaGrupa efg;

    public static KontrolerKI_Grupa getInstance() {
        if(instance==null) instance=new KontrolerKI_Grupa();
        return instance;
    }

    private KontrolerKI_Grupa() {
    }

    
    public void dodajGrupu() {
       efng=new EkranskaFormaNovaGrupa();
       fillCBTreneri();
       dodajOsluskivace1();
       efng.setVisible(true);
       
    }
    private void dodajOsluskivace1() {
        efng.getBtn().addActionListener(a->zapamtiGrupu());
        efng.getjButton1().addActionListener(a->{efng.dispose();});
        
    }
    private void dodajOsluskivace2() {
        efpg.getjButton1().addActionListener(a->vidiGrupe());
        efpg.getjButton2().addActionListener(a->efpg.dispose());
       
        
    }
    
     private void zapamtiGrupu() {
         String ime=efng.getTxtIme();
         Trener t=efng.getCbtreneri1();
        
        String poruka="";
        if(ime.isEmpty())poruka+="Niste uneli ime grupe.\n";
        if(ime.length()<3)poruka+="Ime mora imati najmanje 4 znaka.\n";
        
        
        if(!poruka.isEmpty()) {
            efng.prikaziPoruku(poruka, "Neuspesno kreiranje grupe");
            return;
        }
        Grupa grupa = new Grupa();
        grupa.setImeGrupe(ime);
        grupa.setTrener(t);
        odo=grupa;
        try{
            odgovor=poziviSO(Operacije.KREIRAJ_GRUPU,grupa);
            if(odgovor.isStatusOdgovora()) {
                efng.prikaziPoruku(odgovor.getPoruka(),"Uspesno kreiranje grupe!");
                efng.dispose();
            }else{
                efng.prikaziPoruku(odgovor.getPoruka(),"Neuspesno kreiranje grupe!");
            }
        } catch (Exception ex) {
            efng.prikaziPoruku(ex.getMessage(),"Greska prilikom kreiranja grupe!");
        }
        
    }

    void pregledGrupa() {
        efpg=new EkranskaFormaPregledGrupa();
        dodajOsluskivace2();
        popuniTabelu();
        efpg.setVisible(true);
    }

    private void popuniTabelu() {
        odo=new Grupa();
        
        try{
        odgovor=SOPretrazi(Operacije.VRATI_SVE_GRUPE);

        if(odgovor.isStatusOdgovora()) {
               efpg.prikaziPoruku(odgovor.getPoruka(),"");
                List<Grupa> grupe=(List) odgovor.getPodaci();
                for(Grupa g:grupe){
                    Trener t=g.getTrener();
                    odo=t;
                   ServerskiOdgovor pomocni=SOPretrazi(Operacije.VRATI_TRENERA_SAUSLOVOM_IDTRENERA);
                   g.setTrener((Trener)pomocni.getPodaci());
                    
                }
                TableModelGrupe tmg=new TableModelGrupe(grupe);
                efpg.getTbl().setModel(tmg);
            } else {
                efpg.prikaziPoruku(odgovor.getPoruka(),"");
                efpg.dispose();
            }
        } catch (Exception ex) {
            efpg.prikaziPoruku(ex.getMessage(),"Greska");
            ex.printStackTrace();
        }

    }

    
 private void fillCBTreneri() {
        efng.getCbtreneri().removeAllItems();

        try {
            Trener tr=null;
            odo=new Trener();
            odgovor=SOPretrazi(Operacije.VRATI_SVE_TRENERE);
            if (odgovor.isStatusOdgovora()) {
                efng.prikaziPoruku(odgovor.getPoruka(),"");

                List<Trener> treneri = new ArrayList<>((List) odgovor.getPodaci());
                for (Trener t : treneri) {
                    efng.getCbtreneri().addItem(t);
                }
            } else {
                efng.prikaziPoruku(odgovor.getPoruka(),"");
                efng.dispose();
            }
        } catch (Exception ex) {
            efng.prikaziPoruku(ex.getMessage(),"Greska");

        }
    }

    public Grupa vratiPotpunuGrupu(Grupa grupa) {
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

    

    private void vidiGrupe() {
        int row=efpg.getTbl().getSelectedRow();
       if(row==-1){
           efpg.prikaziPoruku( "Nije selektovan red.","");
       }else{
        
        TableModelGrupe tmg=(TableModelGrupe)efpg.getTbl().getModel();
        Grupa g=tmg.vratiGrupe().get(row);
          
        otvoriFormuGrupa(g);
        
       }
    }

    public void otvoriFormuGrupa(Grupa g) {
        efg=new EkranskaFormaGrupa();
        efg.getjButton1().addActionListener(a->efg.dispose());
       if( pripremaTabeleGrupa(g)) efg.setVisible(true);
    }

    private boolean pripremaTabeleGrupa(Grupa g) {
        try {
            Clan c=new Clan();
            c.setGrupa(vratiPotpunuGrupu(g));
            odo=c;
            odgovor=SOPretrazi(Operacije.VRATI_CLANOVE_SAUSLOVOM_IDGRUPE);
            if(odgovor.isStatusOdgovora()) {
                efg.prikaziPoruku( odgovor.getPoruka(),"");
                TableModelClan tmc=new TableModelClan((List)odgovor.getPodaci());
                List<Clan> clanovi=new ArrayList<>((List)odgovor.getPodaci());
                for(Clan c1:clanovi){
                    c1.setGrupa(vratiPotpunuGrupu(g));
                }
                efg.getTbl().setModel(tmc);
                return true;
            }
            
            else {
                efg.prikaziPoruku( odgovor.getPoruka(),"Greska");
                efg.dispose();
                
            }
           
            
        
       
        } catch (Exception ex) {
            efg.prikaziPoruku( ex.getMessage(),"Greska");
        } 
        return false;
    }

    List<Grupa> vratiSveGrupe() {
        try {
            List<Grupa> grupe;
            odo=new Grupa();
            odgovor = SOPretrazi(Operacije.VRATI_SVE_GRUPE);
            if(odgovor.isStatusOdgovora()) {
                grupe=new ArrayList<>((List)odgovor.getPodaci());
                List<Grupa> original=new ArrayList<>();
                for (Grupa g : grupe) {
                    g=vratiPotpunuGrupu(g);
                    original.add(g);

                }
                
                return original;
            }else return null;
        } catch (Exception ex) {
            Logger.getLogger(KontrolerKI_Grupa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
      
}
