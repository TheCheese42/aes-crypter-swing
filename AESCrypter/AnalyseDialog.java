import javax.swing.*;

public class AnalyseDialog extends JFrame {
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTabbedPane tabbedPane3;
    private JTabbedPane tabbedPane4;
    private HexGrid encryption0ParamMessageGrid;
    private HexGrid encryption0ParamCipherGrid;
    private JTextPane thisIsTheMessageTextPane;
    private JTextPane thisIsOurCipherTextPane;
    private HexGrid encryption0AddMessageGrid;
    private HexGrid encryption0AddCipherGrid;
    private JTextPane oneAgainThisIsTextPane;
    private JTextPane toAddTheCipherTextPane;
    private HexGrid encryption0ResultMessageGrid;
    private JTextPane thisIsOurMessageTextPane;

    public AnalyseDialog() {
        setupUi();
    }

    public void main() {}

    private void setupUi() {
        setTitle("Analyse Encryption Process");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);
    }

    private void createUIComponents() {
        encryption0ParamMessageGrid = new HexGrid();
        encryption0ParamCipherGrid = new HexGrid();
        encryption0AddMessageGrid = new HexGrid();
        encryption0AddCipherGrid = new HexGrid();
        encryption0ResultMessageGrid = new HexGrid();
    }
}
