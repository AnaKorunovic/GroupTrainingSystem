/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transfer;

import java.io.Serializable;

/**
 *
 * @author Ana
 */
public class ServerskiOdgovor implements Serializable{
    
    private Object podaci;
    private Exception exception;
    private String poruka;
    private boolean statusOdgovora;

    public ServerskiOdgovor() {
    }

    public ServerskiOdgovor(Object podaci, Exception exception, String poruka, boolean statusOdgovora) {
        this.podaci = podaci;
        this.exception = exception;
        this.poruka = poruka;
        this.statusOdgovora = statusOdgovora;
    }

    public boolean isStatusOdgovora() {
        return statusOdgovora;
    }

    public void setStatusOdgovora(boolean statusOdgovora) {
        this.statusOdgovora = statusOdgovora;
    }


    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    

    public Object getPodaci() {
        return podaci;
    }

    public void setPodaci(Object podaci) {
        this.podaci = podaci;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
    
    

}
