/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model_tabele;

import javax.swing.table.AbstractTableModel;
import domen.Trener;
import java.util.List;
/**
 *
 * @author Ana
 */
public class TableModelTrener extends AbstractTableModel{
    
    private List<Trener> treneri;
    private String[] columnNames=new String[]{"TrenerId", "Ime", "Prezime"};
    private Class[] columnClass=new Class[]{Integer.class,String.class, String.class};
    
    public TableModelTrener(List<Trener> treneri){
        this.treneri=treneri;
    }
    
    @Override
    public int getRowCount() {
        if(treneri==null){
            return 0;
        }else{
            return treneri.size();
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
        Trener t=treneri.get(rowIndex);
        switch(columnIndex){
            case 0:
                return t.getTrenerId();
            case 1:
                return t.getIme();
            case 2:
                return t.getPrezime();
            
            default:
                    return "n/a";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Trener t=treneri.get(rowIndex);
        switch(columnIndex){
            case 0:
                t.setTrenerId((int)aValue);
                break;
            case 1:
                t.setIme(aValue.toString());
                break;
            case 2: 
                t.setPrezime(aValue.toString());
                break;
           
        }
    }
    
    public void dodajTrenera(Trener t){
        this.treneri.add(t);
        fireTableDataChanged();
    }
    
    public Trener izbaciTrenera(int row){
        Trener t=treneri.get(row);
        treneri.remove(row);
        fireTableDataChanged();
        return t;
    }
    
    public List<Trener> vratiTrenere(){
        return treneri;
    }

    public Trener vratiTrenera(int row) {
       return treneri.get(row);
    }
}
