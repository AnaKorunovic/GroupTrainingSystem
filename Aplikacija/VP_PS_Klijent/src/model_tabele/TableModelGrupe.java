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
public class TableModelGrupe extends AbstractTableModel{
    
      private List<Grupa> grupe;
    private String[] columnNames=new String[]{"GrupaId","Ime trenera", "Prezime trenera","Email trenera"};
    private Class[] columnClass=new Class[]{Integer.class,String.class, String.class,String.class};
    
    public TableModelGrupe(List<Grupa> grupe){
        this.grupe=grupe;
    }
    
    @Override
    public int getRowCount() {
        if(grupe==null){
            return 0;
        }else{
            return grupe.size();
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
       Grupa g=grupe.get(rowIndex);
        switch(columnIndex){
            case 0:
                return g.getGrupaId();
            case 1:
                return g.getTrener().getIme();
            case 2:
                return g.getTrener().getPrezime();
                case 3:
            return g.getTrener().getEmailAdresa();
            
            default:
                    return "n/a";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Grupa g=grupe.get(rowIndex);
        switch(columnIndex){
            case 0:
                g.setGrupaId((int)aValue);
                break;
            case 1:
                g.getTrener().setIme(aValue.toString());
                break;
            case 2: 
                g.getTrener().setPrezime(aValue.toString());
                break;
            case 3: 
               g.getTrener().setEmailAdresa(aValue.toString());
                break;
           
        }
    }
    
    public void dodajGrupu(Grupa g){
        this.grupe.add(g);
        fireTableDataChanged();
    }
    
    public void izbaciGrupu(int row){
        grupe.remove(row);
        fireTableDataChanged();
    }
    
    public List<Grupa> vratiGrupe(){
        return grupe;
}
}
