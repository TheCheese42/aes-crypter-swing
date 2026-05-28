import javax.swing.*;
import javax.swing.event.*;

public class HexGrid extends JTable implements TableModelListener {
    private byte[][] data = new byte[4][4];
    
    public void setData(byte[][] data) {
        this.data = data;
        updateUi();
    }
    
    public byte getCell(int x, int y) {
        return data[y][x];
    }
    
    private void updateUi() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                dataModel.setValueAt(Integer.toHexString(data[y][x]), y, x);
            }
        }
    }

    public void tableChanged(TableModelEvent _event) {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                data[y][x] = (byte) Long.parseLong(dataModel.getValueAt(y, x).toString());
            }
        }
    }
}