/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidad;

import entidades.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jcpleitez
 */
public class CustomTableModel extends AbstractTableModel {

    private List<Empleado> li = new ArrayList();
    private String[] columnNames;

    public CustomTableModel(List<Empleado> list, String[] columnNames) {
        this.li = list;
        this.columnNames = columnNames;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return li.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Empleado empleado = li.get(rowIndex);
        return empleado.columnValue(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
}
