import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;
import java.util.Random;

public class MainWindow extends JFrame {
    private JPanel panel;
    private JButton encryptButton;
    private JButton analyseButton;
    private JButton decryptButton;
    private JTextField messageField;
    private JButton generateButton;
    private JTextField encryptedField;
    private HexGrid messageTable;
    private HexGrid keyTable;
    private HexGrid encryptedTable;

    private Random random = new Random();

    private byte[] key;

    void main() {
    }

    public MainWindow() {
        key = new byte[16];

        //$$$setupUI$$$();
        setupUi();
    }

    private void setupUi() {
        setTitle("AES Crypter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);

        messageField.addActionListener(_event -> updateTable(messageTable, messageField.getText()));
        generateButton.addActionListener(_event -> generateKey());
    }

    // Returns true if updating succeeded and false otherwise (e.g. too many characters)
    private boolean updateTable(HexGrid table, String textContent) {
        if (textContent.length() > 16) return false;
        byte[] byteContent = new byte[16];
        for (int i = 0; i < textContent.length(); i++) {
            if ((int) textContent.charAt(i) > 255) return false;
            byteContent[i] = (byte) textContent.charAt(i);
        }
        return updateTable(table, byteContent);
    }

    private boolean updateTable(HexGrid table, byte[] byteContent) {
        if (byteContent.length > 16) return false;
        table.setData(new byte[][] {
            {byteContent[0], byteContent[1], byteContent[2], byteContent[3]},
            {byteContent[4], byteContent[5], byteContent[6], byteContent[7]},
            {byteContent[8], byteContent[9], byteContent[10], byteContent[11]},
            {byteContent[12], byteContent[13], byteContent[14], byteContent[15]},
        });
        return true;
    }

    private void generateKey() {
        random.nextBytes(key);
        updateTable(keyTable, key);
    }

    private void createUIComponents() {
        Object[][] initData = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        String[] columnNames = {"", "", "", ""};
        messageTable = new HexGrid(initData, columnNames);
        keyTable = new HexGrid(initData, columnNames);
        encryptedTable = new HexGrid(initData, columnNames);
    }
}
