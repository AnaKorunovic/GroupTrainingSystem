/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;


import domen.Grupa;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;
import static so.OpstaSo.dbbr;

/**
 *
 * @author Ana
 */
public class SOUcitajListuGrupa extends  OpstaSo{
    
     List<Grupa> grupe;
    
    public SOUcitajListuGrupa() {
        super();
        grupe=new ArrayList<>();
    }

    
    

    @Override
    void operacija(OpstiDomenskiObjekat odo) {
        List<OpstiDomenskiObjekat> lista=dbbr.vratiSveObjekteBezUslova(odo);
        
        if(lista==null || lista.isEmpty()){
            setPoruka("Sistem ne moze da ucita grupe.");
            setUspesno(false);
        }else{
            setPoruka("Sistem je ucitao grupe.");
            setUspesno(true);
           
           
            List<Grupa> l=new ArrayList(lista);
            
          grupe=l;
        }
        
    }

    
    
    @Override
    public OpstiDomenskiObjekat vratiODO() {
          return null;
    }

    @Override
    public List vratiListu() {
        return grupe;
    }
    
}
