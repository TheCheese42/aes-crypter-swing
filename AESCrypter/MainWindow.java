import javax.swing.*;
import java.util.Random;

public class MainWindow extends JFrame {
    private JPanel panel;
    private JButton encryptButton;
    private JButton analyseButton;
    private JTextField messageField;
    private JButton generateButton;
    private JTextField encryptedField;
    private HexGrid messageTable;
    private HexGrid keyTable;
    private HexGrid encryptedTable;
    private JButton decryptButton;

    private final Random random = new Random();

    private final byte[] key;

    void main() {
    }

    public MainWindow() {
        key = new byte[16];
        setupUi();
    }

    private void setupUi() {
        setTitle("Rijndael Encryptor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);

        messageField.addActionListener(_event -> updateMessageTable());
        messageTable.getModel().addTableModelListener(_event -> updateMessageField());
        generateButton.addActionListener(_event -> generateKey());
        encryptButton.addActionListener(_event -> encryptMessage());
        analyseButton.addActionListener(_event -> openAnalysisDialog());
        decryptButton.addActionListener(_event -> decryptMessage());
    }

    private void updateMessageTable() {
        String message = messageField.getText();
        if (message.length() > 16) messageField.setText(message.substring(0, 16));
        updateTable(messageTable, messageField.getText());
    }

    private void updateMessageField() {
        byte[][] data = messageTable.getData();
        char[] text = new char[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                text[i * 4 + j] = (char) data[i][j];
            }
        }
        messageField.setText(new String(text));
    }

    private boolean updateTable(HexGrid table, String textContent) {
        // Returns true if updating succeeded and false otherwise (e.g. too many characters)
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
        table.setData(new byte[][]{
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

    private void encryptMessage() {
        byte[][] data = messageTable.getData();
        byte[][] key = keyTable.getData();
        byte[][] encrypted = AesLib.encrypt(data, key);
        encryptedTable.setData(encrypted);
        String encryptedText = new String();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                encryptedText = encryptedText.concat(String.format("%02x", Math.floorMod(encrypted[i][j], 256)));
            }
        }
        encryptedField.setText(encryptedText);
    }

    private void decryptMessage() {
        byte[][] data = encryptedTable.getData();
        byte[][] key = keyTable.getData();
        byte[][] decrypted = AesLib.decrypt(data, key);
        messageTable.setData(decrypted);
        byte[] decryptedText = new byte[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                decryptedText[i * 4 + j] = decrypted[i][j];
            }
        }
        messageField.setText(new String(decryptedText));
    }

    private void openAnalysisDialog() {
        new AnalyseDialog(messageTable.getData(), keyTable.getData());
    }

    private void createUIComponents() {
        messageTable = new HexGrid();
        messageTable.setEditingEnabled(true);
        keyTable = new HexGrid();
        keyTable.setEditingEnabled(true);
        encryptedTable = new HexGrid();
        encryptedTable.setEditingEnabled(true);
    }
}
