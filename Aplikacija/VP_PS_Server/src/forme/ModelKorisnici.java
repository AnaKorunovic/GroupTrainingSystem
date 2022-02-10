/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import domen.Korisnik;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ana
 */
public class ModelKorisnici extends AbstractTableModel{

    private List<Korisnik> korisnici;
    private String[] columnNames=new String[]{"Korisničko ime","Šifra"};
    private Class[] columnClass=new Class[]{String.class, String.class};
    
    public ModelKorisnici(List<Korisnik> k){
        this.korisnici=k;
    }
    
    @Override
    public int getRowCount() {
        if(korisnici==null){
            return 0;
        }else{
            return korisnici.size();
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        if(column>columnNames.length){
            return "n/a";
        }else{
            return columnNames[column];
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex>columnClass.length){
            return Object.class;
        }else{
            return columnClass[columnIndex];
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Korisnik k=korisnici.get(rowIndex);
        switch(columnIndex){
            case 0:
                return k.getKorisnickoIme();
            case 1:
                return k.getSifra();
            
            default:
                    return "n/a";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Korisnik k=korisnici.get(rowIndex);
        switch(columnIndex){
            
            case 0: 
               k.setKorisnickoIme(aValue.toString());
                break;
            case 1: 
               k.setSifra(aValue.toString());
                break;
              
           
        }
    }
    
    public void dodajKorisika(Korisnik k){
        this.korisnici.add(k);
        fireTableDataChanged();
    }
    
    public void izbaciKorisnka(Korisnik k1){
        int i=0;
        for(Korisnik k:korisnici){
            if(k.getKorisnickoIme().equals(k1.getKorisnickoIme()) &&
               k.getSifra().equals(k1.getSifra())){

                this.korisnici.remove(i);
                
                fireTableDataChanged();
                return;


            }i++;
            
        }
    }
    
    public List<Korisnik> vratiKorisnike(){
        return korisnici;
    }
    
}
