/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model_tabele;

import domen.Grupa;
import domen.Termin;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ana
 */
public class TableModelTermini extends AbstractTableModel {

    private List<Termin> termini;
    private boolean editable[];
    private String[] columnNames = new String[]{"Datum", "Vreme", "Grupa"};
    private Class[] columnClass = new Class[]{Date.class, String.class, Grupa.class};

    public TableModelTermini(List<Termin> termini) {
        this.termini = termini;
         editable = new boolean[]{false, false, false};
        
    }

    public void setEditable(boolean[]editable) {
        this.editable = editable;
    }

    @Override
    public int getRowCount() {
        if (termini == null) {
            return 0;
        } else {
            return termini.size();
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        if (column > columnNames.length) {
            return "n/a";
        } else {
            return columnNames[column];
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex > columnClass.length) {
            return Object.class;
        } else {
            return columnClass[columnIndex];
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Termin t = termini.get(rowIndex);
        switch (columnIndex) {

            case 0:
                return t.getDatum();
            case 1:
                //String timeFormat = "HH:mm:ss";
                //SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
                //return sdf.format(t.getDatum().getTime
                return t.getVreme();
            case 2:
                return t.getGrupa();

            default:
                return "n/a";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Termin t = termini.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date datum;
                try {
                    datum = sdf.parse(aValue.toString());
                    t.setDatum(datum);

                } catch (ParseException ex) {
                    Logger.getLogger(TableModelTermini.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            break;

            case 1:
                t.setVreme(aValue.toString());
                break;
            case 2:
                t.setGrupa((Grupa) aValue);

            default:

        }
    }

    public Termin getTermin(int row) {
        return termini.get(row);
    }

    public void dodajTermin(Termin t) {
        
        termini.add(t);

        fireTableDataChanged();

    }

    public void izbaciTermin(int row) {
        termini.remove(row);
        fireTableDataChanged();

    }

    public List<Termin> vratiTermine() {
        return termini;
    }
}
