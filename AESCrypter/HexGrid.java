import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
    private boolean editingEnabled = false;

    public void resizeRows(ComponentEvent _event) {
        setRowHeight(getHeight());
    }

    public HexGrid() {
        super(
            new DefaultTableModel(new Object[][] { {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0} },
            new String[] {"", "", "", ""})
        );

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
    
    public byte[][] getData() {
        return data;
    }

    public void setEditingEnabled(boolean editingEnabled) {
        this.editingEnabled = editingEnabled;
    }

    public boolean isEditingEnabled() {
        return editingEnabled;
    }

    @Override
    public boolean isCellEditable(int _row, int _column) {
        return editingEnabled;
    }
    
    private void updateUi() {
        changeIsInternal = true;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                dataModel.setValueAt(String.format("%02x", Math.floorMod((int) data[y][x], 256)), y, x);
            }
        }
        changeIsInternal = false;
    }

    public void tableChanged(TableModelEvent event) {
        super.tableChanged(event);
        if (!initialized || changeIsInternal) return;
        changeIsInternal = true;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                try {
                    data[y][x] = (byte) Long.parseLong(dataModel.getValueAt(y, x).toString(), 16);
                    dataModel.setValueAt(String.format("%02x", Math.floorMod((int) data[y][x], 256)), y, x);
                } catch (NumberFormatException | StackOverflowError _e) {
                    dataModel.setValueAt(String.format("%02x", data[y][x]), y, x);
                }
            }
        }
        changeIsInternal = false;
    }
}