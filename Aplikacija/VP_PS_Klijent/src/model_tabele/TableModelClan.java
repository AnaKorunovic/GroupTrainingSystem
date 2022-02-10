/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model_tabele;

import domen.Clan;
import domen.Grupa;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ana
 */
public class TableModelClan extends AbstractTableModel{
     private List<Clan> clanovi;
    private String[] columnNames=new String[]{"ClanId","Ime", "Prezime","GrupaId","imeGrupe"};
    private Class[] columnClass=new Class[]{Integer.class,String.class, String.class,Integer.class,String.class};
    
    public TableModelClan(List<Clan> clanovi){
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
        return false;
    }
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Clan c=clanovi.get(rowIndex);
        switch(columnIndex){
            case 0:
                return c.getClanId();
            case 1:
                return c.getIme();
            case 2:
                return c.getPrezime();
             case 3:
            return c.getGrupa().getGrupaId();
            case 4:
            return c.getGrupa().getImeGrupe();
            
            default:
                    return "n/a";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Clan c=clanovi.get(rowIndex);
        switch(columnIndex){
            case 0:
                c.setClanId((int)aValue);
                break;
            case 1:
                c.setIme(aValue.toString());
                break;
            case 2: 
                c.setPrezime(aValue.toString());
                break;
            case 3: 
                c.getGrupa().setGrupaId((int)aValue);
                break;
            case 4: 
                c.getGrupa().setImeGrupe(aValue.toString());
                break;
              
           
        }
    }
    
    public void dodajClana(Clan c){
        this.clanovi.add(c);
        fireTableDataChanged();
    }
    
    public void izbaciClana(int row){
        clanovi.remove(row);
        fireTableDataChanged();
    }
    
    public List<Clan> vratiClanove(){
        return clanovi;
    }
}
