/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler_KI;

import domen.Clan;
import domen.Prisustvo;
import domen.Termin;
import ekranske_forme.EkranskaFormaPrisustvo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableColumn;
import javax.swing.JCheckBox;
import konfiguracija.Operacije;
import model_tabele.TableModelPrisustvo;
import model_tabele.TableModelTermini;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Ana
 */
public class KontrolerKI_Prisustvo extends OpstiKKI{
    ekranske_forme.EkranskaFormaPrisustvo efp;
    private static KontrolerKI_Prisustvo instance;
    

    public static KontrolerKI_Prisustvo getInstance() {
        if(instance==null) instance=new KontrolerKI_Prisustvo();
        return instance;
    }

    private KontrolerKI_Prisustvo() {
    }

    void azurirajPrisustvo(Termin t) {
        
        efp= new EkranskaFormaPrisustvo();
        postaviOsluskivace();
        popuniPrisustvo(t);
        
        efp.setVisible(true);

    }

    private void popuniPrisustvo(Termin t) {
      try {
          
           List<Prisustvo> listaPrisustvaTerminu=new ArrayList<>();
           Prisustvo p=new Prisustvo(); p.setTermin(t);
           odo=p;
           odgovor = SOPretrazi(Operacije.VRATI_PRISUSTVO_SAUSLOVOM_IDTERMINA);

          if (odgovor.isStatusOdgovora()) {
              efp.prikaziPoruku( "Prisustvo je vec evidentirano","");
                       pogledajPrisustvo(t);
                       return;
          }else{
              
              Clan c = new Clan();
              c.setGrupa(t.getGrupa());
              odo=c;
              ServerskiOdgovor pom = SOPretrazi(Operacije.VRATI_CLANOVE_SAUSLOVOM_IDGRUPE);
              if (pom.isStatusOdgovora()) {
                efp.prikaziPoruku( odgovor.getPoruka(),"");
                List<Clan> clanoviGrupe = new ArrayList<>((List) odgovor.getPodaci());
                listaPrisustvaTerminu = new ArrayList<>();
                int i=0;
                for(Clan cg:clanoviGrupe){
                    Prisustvo p1=new Prisustvo();
                    p1.setClan(cg);
                    p1.setPrisutan(false);
                    p1.setPoruka("");
                    p1.setTermin(t);
                    listaPrisustvaTerminu.add(p1);
                }
               }
                }

                TableModelPrisustvo tmp = new TableModelPrisustvo(listaPrisustvaTerminu);
                efp.getTbl().setModel(tmp);

                JCheckBox cbPrisutan=new JCheckBox();
                //JComboBox cbPrisutan = new JComboBox(new String[]{"prisutan", "nije prisustvovao"});
                TableColumn chbPrisutan = efp.getTbl().getColumnModel().getColumn(3);
                chbPrisutan.setCellEditor(new DefaultCellEditor(cbPrisutan));

                JComboBox cbPoruka = new JComboBox(new String[]{" ", "opravdano", "neopravdano"});
                TableColumn tcPoruka = efp.getTbl().getColumnModel().getColumn(4);
                tcPoruka.setCellEditor(new DefaultCellEditor(cbPoruka));

            
        } catch (Exception ex) {
            efp.prikaziPoruku(ex.getMessage(), "");
        }

    }

    private void postaviOsluskivace() {

        efp.getjButton1().addActionListener(a->sacuvajPrisustvo());
        efp.getjButton2().addActionListener(a->efp.dispose());
    }

    private void sacuvajPrisustvo() {
        try {
            TableModelPrisustvo tmp = (TableModelPrisustvo) efp.getTbl().getModel();
            List<Prisustvo> listaPrisustva = tmp.vratiClanove();

            
            for (Prisustvo p : listaPrisustva) {
                    odgovor = poziviSO(Operacije.KREIRAJ_PRISUSTVO, p);
                }
                if (odgovor.isStatusOdgovora() == true ){
                    efp.prikaziPoruku("Uspesno je kreirano prisustvo clanova grupe", "Uspesno kreiranje");
                    efp.dispose();
                } else {
                    efp.prikaziPoruku("Neuspesno azuriranje prisustva", "Greska");
                }

            

        } catch (Exception ex) {
            efp.prikaziPoruku(ex.getMessage(), "Greska");
            ex.printStackTrace();
        }
    }

    void pogledajPrisustvo(Termin t) {
       efp = new EkranskaFormaPrisustvo();
       postaviOsluskivace();
       efp.getTbl().setEnabled(false);
       efp.getjButton1().setVisible(false);
      // popuniPrisustvo(t);
        try {
          
           List<Prisustvo> listaPrisustvaTerminu;
           Prisustvo p=new Prisustvo(); p.setTermin(t);
           odo=p;
           odgovor = SOPretrazi(Operacije.VRATI_PRISUSTVO_SAUSLOVOM_IDTERMINA);

          if (!odgovor.isStatusOdgovora()) {
              efp.prikaziPoruku( "Prisustvo ne postoji u bazi","");
                      azurirajPrisustvo(t);
                       return;
          }else{
              listaPrisustvaTerminu=new ArrayList<>((List)odgovor.getPodaci());
              for(Prisustvo pe:listaPrisustvaTerminu){
                  pe.setTermin(t);
                  odo=pe.getClan();
                  ServerskiOdgovor pom=poziviSO(Operacije.VRATI_CLANA_SAUSLOVOM_IDCLANA, odo);
                  pe.setClan((Clan)pom.getPodaci());
              }
          }

                TableModelPrisustvo tmp = new TableModelPrisustvo(listaPrisustvaTerminu);
                efp.getTbl().setModel(tmp);
        } catch (Exception ex) {
            efp.prikaziPoruku(ex.getMessage(), "");
        }

       efp.setVisible(true);
    }
    
    
    
}
