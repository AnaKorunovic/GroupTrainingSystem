/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model_tabele;

import domen.Clan;
import domen.Prisustvo;
import domen.Termin;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ana
 */
public class TableModelPrisustvo extends AbstractTableModel{
    
      private List<Prisustvo> clanovi;
    private String[] columnNames=new String[]{"ClanId","Ime", "Prezime","Prisutan","Poruka"};
    private Class[] columnClass=new Class[]{Integer.class,String.class, String.class,Boolean.class,String.class};
    
    public TableModelPrisustvo(List<Prisustvo> clanovi){
        this.clanovi=clanovi;
    }
    
    @Override
    public int getRowCount() {
        if(clanovi==null){
            return 0;
        }else{
            return clanovi.size();
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
        if(columnIndex==3 || columnIndex==4) return true;
        return false;
    }
    
    
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Prisustvo c=clanovi.get(rowIndex);
        switch(columnIndex){
            case 0:
                return c.getClan().getClanId();
            case 1:
                return c.getClan().getIme();
            case 2:
                return c.getClan().getPrezime();
           case 3:
            return c.isPrisutan();
           case 4:
               return c.getPoruka();
            
            default:
                    return "n/a";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Prisustvo c=clanovi.get(rowIndex);
        switch(columnIndex){
            case 0: c.getClan().setClanId((Integer)aValue);break;
             case 1: c.getClan().setIme(aValue.toString());break;
             case 2: c.getClan().setPrezime(aValue.toString());break;
             
            case 3:
                System.out.println(aValue.toString());
                if(aValue.toString().equals("true"))c.setPrisutan(true);
                else c.setPrisutan(false);
                break;
            case 4:
                c.setPoruka(aValue.toString());
                break;
            
           
        }
    }
   
    
    public List<Prisustvo> vratiClanove(){
        return clanovi;
    }
    
}
