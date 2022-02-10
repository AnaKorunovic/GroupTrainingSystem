/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transfer;

import java.io.Serializable;
import konfiguracija.Operacije;

/**
 *
 * @author Ana
 */
public class KlijentskiZahtev implements Serializable{
    
    private Operacije operacija;
    private Object podaci;

    public KlijentskiZahtev() {
    }

    public KlijentskiZahtev(Operacije operacija, Object podaci) {
        this.operacija = operacija;
        this.podaci = podaci;
    }

    public Operacije getOperacija() {
        return operacija;
    }

    public void setOperacija(Operacije operacija) {
        this.operacija = operacija;
    }

    public Object getPodaci() {
        return podaci;
    }

    public void setPodaci(Object podaci) {
        this.podaci = podaci;
    }

    
}
