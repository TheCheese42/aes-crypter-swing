import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class TableFillYListener implements ComponentListener {
    // Listens to resize events and stretches the table accordingly
    // to take the whole vertical space.
    JTable table;

    public TableFillYListener(JTable table) {
        this.table = table;
    }

    public void componentResized(ComponentEvent componentEvent) {
        table.setRowHeight(table.getHeight() / table.getRowCount());
    }

    // Other methods to fully implement the interface
    public void componentMoved(ComponentEvent componentEvent) {}

    public void componentShown(ComponentEvent componentEvent) {}

    public void componentHidden(ComponentEvent componentEvent) {}
}
