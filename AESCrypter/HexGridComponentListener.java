import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class HexGridComponentListener implements ComponentListener {
    JTable table;

    public HexGridComponentListener(JTable table) {
        this.table = table;
    }
    public void componentResized(ComponentEvent componentEvent) {
        table.setRowHeight(table.getHeight() / 4);
    }

    public void componentMoved(ComponentEvent componentEvent) {}

    public void componentShown(ComponentEvent componentEvent) {}

    public void componentHidden(ComponentEvent componentEvent) {}
}
