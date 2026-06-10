import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class TableFillYListener implements ComponentListener {
    JTable table;

    public TableFillYListener(JTable table) {
        this.table = table;
    }

    public void componentResized(ComponentEvent componentEvent) {
        table.setRowHeight(table.getHeight() / table.getRowCount());
    }

    public void componentMoved(ComponentEvent componentEvent) {}

    public void componentShown(ComponentEvent componentEvent) {}

    public void componentHidden(ComponentEvent componentEvent) {}
}
