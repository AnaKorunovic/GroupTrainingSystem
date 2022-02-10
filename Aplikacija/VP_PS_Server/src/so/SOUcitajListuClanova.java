/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Clan;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ana
 */
public class SOUcitajListuClanova extends  OpstaSo{
    List<Clan> clanovi;
    
    public SOUcitajListuClanova() {
        super();
        clanovi=new ArrayList<>();
    }

    
    

    @Override
    void operacija(OpstiDomenskiObjekat odo) {
        List<OpstiDomenskiObjekat> lista=dbbr.vratiSveObjekteBezUslova(odo);
        
        if(lista==null || lista.isEmpty()){
            setPoruka("Sistem ne moze da ucita " + odo.vratiNazivTabele()+"ove.");
            setUspesno(false);
        }else{
            setPoruka("Sistem je ucitao  " + odo.vratiNazivTabele()+"ove.");
            setUspesno(true);
           
           
            List<Clan> l=new ArrayList(lista);
            
           clanovi=l;
        }
        
    }

    
    
    @Override
    public OpstiDomenskiObjekat vratiODO() {
          return null;
    }

    @Override
    public List vratiListu() {
        return clanovi;
    }
    
    
}
