/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.Termin;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ana
 */
public class SOUcitajListuTermina extends OpstaSo{
    
    List<Termin> termini;
    
    public SOUcitajListuTermina() {
        super();
        termini=new ArrayList<>();
    }

    
    

    @Override
    void operacija(OpstiDomenskiObjekat odo) {
        List<OpstiDomenskiObjekat> lista=dbbr.vratiSveObjekteBezUslova(odo);
        
        if(lista==null || lista.isEmpty()){
            setPoruka("Sistem ne moze da ucita termine.");
            setUspesno(false);
        }else{
            setPoruka("Sistem je ucitao termine.");
            setUspesno(true);
           
           
            List<Termin> l=new ArrayList(lista);
            
          termini=l;
        }
        
    }

    
    
    @Override
    public OpstiDomenskiObjekat vratiODO() {
          return null;
    }

    @Override
    public List vratiListu() {
        return termini;
    }
    
}
