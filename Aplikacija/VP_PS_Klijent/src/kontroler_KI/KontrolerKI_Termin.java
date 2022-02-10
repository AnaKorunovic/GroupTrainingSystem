/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler_KI;

import domen.Grupa;
import domen.Termin;
import domen.Trener;
import ekranske_forme.EFPregledTermina;
import ekranske_forme.EkranskaFormaNoviTermin1;
import ekranske_forme.OpstaEkranskaForma;
import ekranske_forme.POMEkranskaFormaTermin;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import konfiguracija.Operacije;
import model_tabele.TableModelTermini;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Ana
 */
public class KontrolerKI_Termin extends  OpstiKKI{
    
    private static  KontrolerKI_Termin instance;
    EkranskaFormaNoviTermin1 efnt;
    EFPregledTermina efpt;
    POMEkranskaFormaTermin peft;
    List<Termin> listaZaBazu;


    public static KontrolerKI_Termin getInstance() {
        if(instance==null) instance=new KontrolerKI_Termin();
        return instance;
    }

    private KontrolerKI_Termin() {
    }
    

    public void dodajTermin() {
       efnt=new EkranskaFormaNoviTermin1(); 
       listaZaBazu = new ArrayList<>();
       dodajOsluskivace1();
       efnt.getPnl1().setVisible(false);
       efnt.setVisible(true);
    }
    private void dodajOsluskivace1() {
        efnt.getBtnUcitaj().addActionListener(a->UcitajTermineNov());
        efnt.getBtnVrati().addActionListener(a->efnt.dispose());
        efnt.getBtnKreiraj().addActionListener(a->kreirajTermin());
         efnt.getBtnObtisi().addActionListener(a->obrisiTermin());
         efnt.getBtnSacuvaj().addActionListener(a->sacuvajSveTermine());
    }
    private void dodajOsluskivace2() {
        efpt.getBtnFDatum().addActionListener(a->vratiTermineZaSelDat());
        efpt.getBtnGrupa().addActionListener(a->vratiTermineZaSelGrupu());
        efpt.getjButton1().addActionListener(a->ucitajTermin());
        efpt.getBtnAzurirajP().addActionListener(a->azurirajPrisustvo());
        efpt.getBtnPogledajP().addActionListener(a->pogledajPrisustvo());
        efpt.getjButton2().addActionListener(a->vidiClanoveGrupe());
         efpt.getBtnIzadji().addActionListener(a->efpt.dispose());
         efpt.getBtnukloni().addActionListener(a->popuniTabelePregled());
        
        
    }
    
     
    void pregledTermina() {
        efpt=new EFPregledTermina();
        dodajOsluskivace2();
        popuniCbGrupe();
        popuniTabelePregled();
        efpt.setVisible(true);
    }

    private void popuniTabelePregled() {
        try {
            
            Date trenutniDatum = new Date();
            List<Termin> odrzaniTermini = new ArrayList<>();
            List<Termin> buduciTermini = new ArrayList<>();
            odo=new Termin();
            odgovor = SOPretrazi(Operacije.VRATI_SVE_TERMINE);
            if (odgovor.isStatusOdgovora()) {
                efpt.prikaziPoruku(odgovor.getPoruka(),"");
                List<Termin> termini = new ArrayList<>((List) odgovor.getPodaci());
                for (Termin t : termini) {
                    
                    t.setGrupa(KontrolerKI_Grupa.getInstance().vratiPotpunuGrupu(t.getGrupa()));
                    
                    Date datumTermina = t.getDatum();//ovo je datum
                    String vremeTermina = t.getVreme();

                    Calendar vremeCal = Calendar.getInstance();
                    Calendar datumCal = Calendar.getInstance();
                    datumCal.setTime(datumTermina);
                    LocalTime vreme = LocalTime.parse(vremeTermina);
                    datumCal.set(Calendar.HOUR, vreme.getHour());
                    datumCal.set(Calendar.MINUTE, vreme.getMinute());
                    datumTermina = datumCal.getTime();

                    if (datumTermina.before(new Date())) {
                        odrzaniTermini.add(t);
                    } else {
                        buduciTermini.add(t);
                    }
                }
                TableModelTermini tmt = new TableModelTermini(termini);
                TableModelTermini tmt1 = new TableModelTermini(odrzaniTermini);
                TableModelTermini tmt2 = new TableModelTermini(buduciTermini);

                efpt.getTblTermini().setModel(tmt); 
                efpt.getTbl1().setModel(tmt1);
                efpt.getTbl2().setModel(tmt2);
                

            } else {
                efpt.prikaziPoruku(odgovor.getPoruka(),"");
                efpt.dispose();
            }
        } catch (Exception ex) {
           efpt.prikaziPoruku(ex.getMessage(), "Greska");
        }

    }

    

    

   

    private void vratiTermineZaSelDat() {
        try {
           Termin t=new Termin();
           t.setDatum(efpt.getDcDatum());
           odo=t;
            odgovor = SOPretrazi(Operacije.VRATI_TERMINE_SAUSLOVOM_DAN);
            if (odgovor.isStatusOdgovora()) {
                efpt.prikaziPoruku(odgovor.getPoruka(), "Uspesno ucitavanje liste termina!");
                List<Termin> lista = (List) odgovor.getPodaci();
                for (Termin t1 : lista) {
                    Grupa g=t1.getGrupa();
                    g=KontrolerKI_Grupa.getInstance().vratiPotpunuGrupu(g);
                    t1.setGrupa(g);
                }
                    TableModelTermini tmt = new TableModelTermini(lista);
                    efpt.getTblTermini().setModel(tmt);
                
            } else {
                efpt.prikaziPoruku(odgovor.getPoruka(), "Neuspesno!");
            }

        } catch (Exception ex) {
             efpt.prikaziPoruku(ex.getMessage(),"Greska");
        }


    }

    private void azurirajPrisustvo() {
       int row = efpt.getTbl1().getSelectedRow();
        if (row == -1)
            efpt.prikaziPoruku("Nije selektovan red u tabeli odrzanih termina", "Upozorenje");
        else {
            KontrolerKI_Prisustvo.getInstance().azurirajPrisustvo(((TableModelTermini) efpt.getTbl1().getModel()).getTermin(row));
        }
    }

    private void pogledajPrisustvo() {
        
        int row = efpt.getTbl1().getSelectedRow();
        if (row == -1) {
            efpt.prikaziPoruku( "Nije selektovan tabele proslih termina.","");
        } else {
            TableModelTermini tmt = (TableModelTermini) efpt.getTbl1().getModel();
            Termin t = tmt.vratiTermine().get(row);

            KontrolerKI_Prisustvo.getInstance().pogledajPrisustvo(t);
            
        }

    }

    private void ucitajTermin() {
       TableModelTermini tmt = (TableModelTermini) efpt.getTblTermini().getModel();
        int row = efpt.getTblTermini().getSelectedRow();
        if (row == -1)
             efpt.prikaziPoruku( "Nije selektovan red.","Upozorenje");
        else {
            try {
                odo= tmt.getTermin(row);

                odgovor = SOPretrazi(Operacije.VRATI_TERMIN);
                if (odgovor.isStatusOdgovora()) {
                     efpt.prikaziPoruku( odgovor.getPoruka(), "Uspesno ucitavanje termina");
                    peft=new POMEkranskaFormaTermin();
                    prikaziSelektovaniTermin((Termin) odgovor.getPodaci());
                    
                    peft.setVisible(true);
                    

                } else {
                     efpt.prikaziPoruku( odgovor.getPoruka(), "Neuspesno ucitavanje termina");

                }
            } catch (Exception ex) {
               efpt.prikaziPoruku( "Sistem ne moze da ucita termin.", "Greska");
            }
        }
    }

    private void prikaziSelektovaniTermin(Termin termin) {
        peft.getTxtDatum().setText(termin.getDatum().toString());
        peft.getTxtDatum().setEditable(false);
      peft.getTxtVreme().setText((termin.getVreme()));
      peft.getTxtVreme().setEditable(false);
      peft.getjButton1().addActionListener(a->POMzapamtiTermin());
        try {
            odo=new Grupa();
            odgovor=SOPretrazi(Operacije.VRATI_SVE_GRUPE);
            if(odgovor.isStatusOdgovora()) {
                peft.prikaziPoruku( odgovor.getPoruka(),"");
               
                List<Grupa> grupe=new ArrayList<>((List)odgovor.getPodaci());
                for(Grupa g:grupe){
                   odo=g.getTrener();
               ServerskiOdgovor pomocni=SOPretrazi(Operacije.VRATI_TRENERA_SAUSLOVOM_IDTRENERA);
               g.setTrener((Trener)pomocni.getPodaci());
                  peft.getCbGrupe().addItem(g);
                   if(termin.getGrupa().getGrupaId()==g.getGrupaId())peft.getCbGrupe().getModel().setSelectedItem(g);
                }
                odo=termin;
                
            }else {
                peft.prikaziPoruku(odgovor.getPoruka(),"");
            }

        } catch (Exception ex) {
            peft.prikaziPoruku(ex.getMessage(),"Greska");
            ex.printStackTrace();
        }
    } 

    private void POMzapamtiTermin() {
         try {
           
         Grupa  grupa= (Grupa)peft.getCbGrupe().getSelectedItem();
         Termin t=(Termin) odo;
         t.setGrupa(grupa);
         odgovor =poziviSO(Operacije.ZAPAMTI_TERMIN, t);

            if (odgovor.isStatusOdgovora()) {
                peft.prikaziPoruku(odgovor.getPoruka(), "Uspesno zakazivanje");
                popuniTabelePregled();
                peft.dispose();
               

            } else {
                peft.prikaziPoruku(odgovor.getPoruka(), "Greska");
            }

        } catch (Exception ex) {
             peft.prikaziPoruku( ex.getMessage(), "Greška prilikom zakazivanja treninga!");
    }
    }

    private void vidiClanoveGrupe() {
        int row = efpt.getTbl2().getSelectedRow();
        if (row == -1) {
            efpt.prikaziPoruku("Nije selektovan tabele buducih termina.","");
        } else {
            Termin t = ((TableModelTermini) efpt.getTbl2().getModel()).getTermin(row);

            if (t.getGrupa().getGrupaId() == 0) {
                efpt.prikaziPoruku( "Ovaj termin nije zakazan.\nNe mogu se videti clanovi grupe.","");
            } else {
                KontrolerKI_Grupa.getInstance().otvoriFormuGrupa(t.getGrupa());
            }

        }
    }

    private void UcitajTermineNov() {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date datum = new Date();
        datum = java.sql.Date.valueOf(sdf.format(efnt.getDcDatum1().getSelectedDate().getTime()));
        if (datum.before(new Date()))
            efnt.prikaziPoruku( "Ne mozete izabrati datum u proslosti","");
        else {
            try {
                Termin t=new Termin(); t.setDatum(datum);
                odo=t;
                odgovor = SOPretrazi(Operacije.VRATI_TERMINE_SAUSLOVOM_DODAN);
                efnt.getDcDatum1().setEnabled(false);
                if (odgovor.isStatusOdgovora()) {
                    efnt.prikaziPoruku(odgovor.getPoruka(), "Uspesno ucitavanje liste termina!");
                    List<Termin> lista = (List) odgovor.getPodaci();

                    for (Termin t1 : lista) {
                        t1.setGrupa(KontrolerKI_Grupa.getInstance().vratiPotpunuGrupu(t1.getGrupa()));
                        
                        t1.setStatus(1);
                        listaZaBazu.add(t1);
                                

                    }
                    
                    TableModelTermini tmt = new TableModelTermini(lista);
                    efnt.getTbl().setModel(tmt);

                } else {
                    efnt.prikaziPoruku(odgovor.getPoruka(), "Baza ne poseduje termine.");
                    TableModelTermini tmt = new TableModelTermini(new ArrayList<>());
                    efnt.getTbl().setModel(tmt);
                }
                efnt.getPnl2().setEnabled(true);

                efnt.getPnl1().setVisible(true);
                pripremiTabeluNovi();

            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(EkranskaFormaNoviTermin1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void pripremiTabeluNovi() {
         LocalDate end=LocalDate.parse(efnt.getDcDatum().toString());
        LocalDate start = LocalDate.now();
        Stream<LocalDate> datumi = start.datesUntil(end.plusDays(1));
        List<LocalDate> listDatumaPom = datumi.collect(Collectors.toList());
        List<String> listaDatuma=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(LocalDate d:listDatumaPom){
            listaDatuma.add(d.toString());
        }
        
        JComboBox cbDatumi = new JComboBox(listaDatuma.toArray());
        TableColumn tcDat = efnt.getTbl().getColumnModel().getColumn(0);
        tcDat.setCellEditor(new DefaultCellEditor(cbDatumi));
        
        
        List<Grupa> grupe = new ArrayList<>();
        grupe=KontrolerKI_Grupa.getInstance().vratiSveGrupe();
        System.out.println(grupe.get(0));
        
        
        if (grupe!=null) {
            
        JComboBox cbGrupe1 = new JComboBox(grupe.toArray());
        TableColumn tcGrupe = efnt.getTbl().getColumnModel().getColumn(2);
        tcGrupe.setCellEditor(new DefaultCellEditor(cbGrupe1));
        }
        String[] nizVreme = new String[15];
        int j = 8;
        for (int i = 0; i < 14; i++) {
            nizVreme[i] = j + ":00:00";
            j++;
        }
        JComboBox cbVreme = new JComboBox(nizVreme);
        TableColumn tcVreme = efnt.getTbl().getColumnModel().getColumn(1);
        tcVreme.setCellEditor(new DefaultCellEditor(cbVreme));

        boolean[] editable = new boolean[]{true, true, true};
        TableModelTermini tmt = (TableModelTermini) efnt.getTbl().getModel();
        tmt.setEditable(editable);
    }

    private void kreirajTermin() {
       TableModelTermini tmt = (TableModelTermini) efnt.getTbl().getModel();
        Termin t = new Termin();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date datum = new Date();
        datum = java.sql.Date.valueOf(sdf.format(efnt.getDcDatum1().getSelectedDate().getTime()));
        t.setDatum(datum);
        t.setStatus(0);
        //listaZaBazu.add(t);
        tmt.dodajTermin(t);
        System.out.println("dodat je temin u tabelu sa statusom"+t.getStatus());
    }

    private void obrisiTermin() {
       int row = efnt.getTbl().getSelectedRow();
        if (row != -1) {
            TableModelTermini tmt = (TableModelTermini) efnt.getTbl().getModel();
            if (tmt.vratiTermine().get(row).getStatus() == 0) {
                tmt.izbaciTermin(row);
                
            }
            if (listaZaBazu.get(row).getStatus() == 1) {
                tmt.izbaciTermin(row);
                listaZaBazu.get(row).setStatus(2);
            }
        } else {
            efnt.prikaziPoruku("Nije selektovan red!", "Upozorenje");
        }
    }

    private void sacuvajSveTermine() {
         try {
            TableModelTermini tmt=(TableModelTermini)efnt.getTbl().getModel();
            for(Termin t:tmt.vratiTermine()){
                
               if(t.getStatus()==0) {
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                t.setDatum( java.sql.Date.valueOf(sdf.format(t.getDatum().getTime())));
                   listaZaBazu.add(t);
               }
            }
            List<Object> lista=new ArrayList<>(listaZaBazu);
            odgovor = SOZapamtiListu(lista,Operacije.ZAPAMTI_LISTU_TERMINA);

            if (odgovor.isStatusOdgovora()) {
                efnt.prikaziPoruku(odgovor.getPoruka(), "Uspesno");
                efnt.dispose();
            } else {
                efnt.prikaziPoruku(odgovor.getPoruka(), "Neuspesno");
            }
        } catch (Exception ex) {
            efnt.prikaziPoruku(ex.getMessage(), "Greska!");
        }
    }

    private void vratiTermineZaSelGrupu() {
        try {
           Termin t=new Termin();
           t.setGrupa((Grupa)efpt.getCbGrupa().getSelectedItem());
            System.out.println(t.getGrupa().getGrupaId());
           odo=t;
            odgovor = SOPretrazi(Operacije.VRATI_TERMINE_SAUSLOVOM_GRUPA);
            if (odgovor.isStatusOdgovora()) {
                efpt.prikaziPoruku(odgovor.getPoruka(), "Uspesno ucitavanje liste termina!");
                List<Termin> lista = (List) odgovor.getPodaci();
                for (Termin t1 : lista) {
                    Grupa g=t1.getGrupa();
                    g=KontrolerKI_Grupa.getInstance().vratiPotpunuGrupu(g);
                    t1.setGrupa(g);
                }
                    TableModelTermini tmt = new TableModelTermini(lista);
                    efpt.getTblTermini().setModel(tmt);
                
            } else {
                efpt.prikaziPoruku(odgovor.getPoruka(), "Neuspesno!");
            }

        } catch (Exception ex) {
             efpt.prikaziPoruku(ex.getMessage(),"Greska");
        }

    }

    private void popuniCbGrupe() {
       efpt.getCbGrupa().removeAllItems();
        try {
          odo=new Grupa();
           odgovor=SOPretrazi(Operacije.VRATI_SVE_GRUPE);
            if(odgovor.isStatusOdgovora()) {
                efpt.prikaziPoruku(odgovor.getPoruka(),"");
               List<Grupa> grupe=new ArrayList<>((List)odgovor.getPodaci());
                for(Grupa g:grupe){
                   odo=g.getTrener();
                   ServerskiOdgovor pomocni=SOPretrazi(Operacije.VRATI_TRENERA_SAUSLOVOM_IDTRENERA);
                   g.setTrener((Trener)pomocni.getPodaci());
                    
                   efpt.getCbGrupa().addItem(g);
                }
            }
            
            else {
                efpt.prikaziPoruku( odgovor.getPoruka(),"");
                efpt.dispose();
            }
        } catch (Exception ex) {
            efpt.prikaziPoruku(ex.getMessage(),"Greska");
        }
    }

    
    

    
     
}
