package kontroler_KI;

import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.Trener;
import ekranske_forme.OpstaEkranskaForma;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import konfiguracija.Operacije;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;
import komunikacija.Komunikacija;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ana
 */
public abstract class OpstiKKI {

    OpstaEkranskaForma oef;
    OpstiDomenskiObjekat odo;
    ServerskiOdgovor odgovor;
    KlijentskiZahtev zahtev;

    public OpstiKKI() {
        
    }

    protected ServerskiOdgovor poziviSO(Operacije o,Object odo) throws Exception {
       zahtev = new KlijentskiZahtev(o, odo);
        Komunikacija.getInstance().posaljiZahtev(zahtev);
        odgovor=Komunikacija.getInstance().primiOdgovor();
        
        if(odgovor.getException()!=null){
           throw odgovor.getException();
           
        }
       
        
        return odgovor;
    }
   
    


    public ServerskiOdgovor SOPretrazi(Operacije o) throws Exception {
      
        return poziviSO(o,odo);
        
    }
    

    public ServerskiOdgovor SOObrisi(Operacije o) throws Exception {
       
        return poziviSO(o,odo);
        
    }
    public ServerskiOdgovor SOZapamtiListu(List<Object> listaZaBazu, Operacije operacije) throws Exception {
        try {
            zahtev = new KlijentskiZahtev(operacije, listaZaBazu);
            Komunikacija.getInstance().posaljiZahtev(zahtev);
            odgovor=Komunikacija.getInstance().primiOdgovor();
            
            if(odgovor.getException()!=null){
                throw odgovor.getException();
                
            }
            
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OpstiKKI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return odgovor;
    }
   
    
   

   
}
