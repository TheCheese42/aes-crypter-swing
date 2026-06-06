import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ComponentEvent;

public class HexGrid extends JTable implements TableModelListener {
    private byte[][] data = {
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    private boolean initialized = false;
    private boolean changeIsInternal = true; // Don't handle table events when changed in code

    public void resizeRows(ComponentEvent _event) {
        setRowHeight(getHeight());
    }

    public HexGrid(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);

        addComponentListener(new HexGridComponentListener(this));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        updateUi();
        initialized = true;
    }
    
    public void setData(byte[][] data) {
        this.data = data;
        updateUi();
    }
    
    public byte getCell(int x, int y) {
        return data[y][x];
    }
    
    private void updateUi() {
        changeIsInternal = true;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                dataModel.setValueAt(Integer.toHexString(Math.floorMod((int) data[y][x], 256)), y, x);
            }
        }
        changeIsInternal = false;
    }

    public void tableChanged(TableModelEvent event) {
        super.tableChanged(event);
        if (!initialized || changeIsInternal) return;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                data[y][x] = (byte) Long.parseLong(dataModel.getValueAt(y, x).toString());
            }
        }
    }
}