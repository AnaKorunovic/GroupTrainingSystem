/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.OpstiDomenskiObjekat;
import domen.Trener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ana
 */
public class SOUcitajListuTrenera extends  OpstaSo{

       List<Trener> treneri=new ArrayList<>();
    
    public SOUcitajListuTrenera() {
        super();
        odo=new Trener();
    }

   @Override
    void operacija(OpstiDomenskiObjekat odo) {
        List<OpstiDomenskiObjekat> lista=dbbr.vratiSveObjekteBezUslova(odo);
        
        if(lista==null || lista.isEmpty()){
            setPoruka("Sistem ne moze da ucita listu trenera.");
            setUspesno(false);
            
        }else{
            setPoruka("Sistem je ucitao trenere.");
            setUspesno(true);
            List<Trener> l=new ArrayList(lista);
            
            treneri=l;
        }
        
    }

    
    
    @Override
    public OpstiDomenskiObjekat vratiODO() {
          return null;
    }

    @Override
    public List vratiListu() {
        return treneri;
    }
    
}
