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
    private JTextField messageField;
    private JButton generateButton;
    private JTextField encryptedField;
    private HexGrid messageTable;
    private HexGrid keyTable;
    private HexGrid encryptedTable;
    private JButton decryptButton;
    private JButton quitButton;

    private final Random random = new Random();

    private final byte[] key;

    void main() {
    }

    public MainWindow() {
        key = new byte[16];
        $$$setupUI$$$();
        setupUi();
    }

    private void setupUi() {
        setTitle("Rijndael Encrypter");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);

        messageField.addActionListener(_event -> updateMessageTable());
        encryptedField.addActionListener(_event -> updateEncryptedTable());
        messageTable.getModel().addTableModelListener(_event -> updateMessageField());
        generateButton.addActionListener(_event -> generateKey());
        encryptButton.addActionListener(_event -> encryptMessage());
        analyseButton.addActionListener(_event -> openAnalysisDialog());
        decryptButton.addActionListener(_event -> decryptMessage());
        quitButton.addActionListener(_event -> dispose());
    }

    private void updateMessageTable() {
        // Update hex matrix when input field was changed
        String message = messageField.getText();
        if (message.length() > 16) messageField.setText(message.substring(0, 16));
        updateTable(messageTable, messageField.getText());
    }

    private void updateEncryptedTable() {
        // Update hex matrix when input field was changed
        String message = encryptedField.getText();
        if (message.length() > 16) encryptedField.setText(message.substring(0, 16));
        updateTable(encryptedTable, encryptedField.getText());
    }

    private void updateMessageField() {
        // Update input field when hex matrix was changed
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
        // Update hex data of any HexGrid instance with a plain text string
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
        // Update hex data of any HexGrid instance with a byte array
        // Returns true if updating succeeded and false otherwise (e.g. too many characters)
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
        // Pseudo-randomly generate a new cipher key
        // Not cryptographically secure!
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        panel.setMinimumSize(new Dimension(1000, 450));
        panel.setPreferredSize(new Dimension(1000, 450));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel.add(panel1, BorderLayout.CENTER);
        final JPanel spacer1 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 2.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.weightx = 2.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipady = 20;
        panel1.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel1.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 4;
        gbc.weightx = 2.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer6, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        encryptButton = new JButton();
        encryptButton.setText("Encrypt");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(encryptButton, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel3, gbc);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 26, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setHorizontalAlignment(0);
        label1.setText("↔");
        panel3.add(label1, BorderLayout.CENTER);
        analyseButton = new JButton();
        analyseButton.setText("Analyse");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(analyseButton, gbc);
        decryptButton = new JButton();
        decryptButton.setText("Decrypt");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(decryptButton, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        panel1.add(spacer7, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        panel1.add(spacer8, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        panel1.add(spacer9, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer10, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel4, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Message: ");
        panel4.add(label2, BorderLayout.WEST);
        messageField = new JTextField();
        panel4.add(messageField, BorderLayout.CENTER);
        final JLabel label3 = new JLabel();
        label3.setHorizontalAlignment(0);
        label3.setText("Press ENTER to confirm.");
        panel4.add(label3, BorderLayout.SOUTH);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel5, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Key: ");
        panel5.add(label4, BorderLayout.WEST);
        generateButton = new JButton();
        generateButton.setText("Generate");
        panel5.add(generateButton, BorderLayout.CENTER);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel6, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Encrypted (HEX): ");
        panel6.add(label5, BorderLayout.WEST);
        encryptedField = new JTextField();
        panel6.add(encryptedField, BorderLayout.CENTER);
        final JLabel label6 = new JLabel();
        label6.setHorizontalAlignment(0);
        label6.setText("Press ENTER to confirm.");
        panel6.add(label6, BorderLayout.SOUTH);
        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer11, gbc);
        final JPanel spacer12 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel1.add(spacer12, gbc);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel7, gbc);
        Font messageTableFont = this.$$$getFont$$$(null, -1, 36, messageTable.getFont());
        if (messageTableFont != null) messageTable.setFont(messageTableFont);
        panel7.add(messageTable, BorderLayout.CENTER);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel8, gbc);
        keyTable.setFillsViewportHeight(true);
        Font keyTableFont = this.$$$getFont$$$(null, -1, 36, keyTable.getFont());
        if (keyTableFont != null) keyTable.setFont(keyTableFont);
        panel8.add(keyTable, BorderLayout.CENTER);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel9, gbc);
        Font encryptedTableFont = this.$$$getFont$$$(null, -1, 36, encryptedTable.getFont());
        if (encryptedTableFont != null) encryptedTable.setFont(encryptedTableFont);
        panel9.add(encryptedTable, BorderLayout.CENTER);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new BorderLayout(0, 0));
        panel.add(panel10, BorderLayout.NORTH);
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$(null, -1, 36, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setHorizontalAlignment(0);
        label7.setText("Rijndael Encrypter");
        panel10.add(label7, BorderLayout.CENTER);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new BorderLayout(0, 0));
        panel11.setMinimumSize(new Dimension(102, 0));
        panel11.setPreferredSize(new Dimension(102, 0));
        panel10.add(panel11, BorderLayout.WEST);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridBagLayout());
        panel10.add(panel12, BorderLayout.EAST);
        quitButton = new JButton();
        quitButton.setMaximumSize(new Dimension(80, 30));
        quitButton.setMinimumSize(new Dimension(80, 30));
        quitButton.setPreferredSize(new Dimension(80, 30));
        quitButton.setText("Quit");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(quitButton, gbc);
        final JPanel spacer13 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(spacer13, gbc);
        final JPanel spacer14 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer14, gbc);
        final JPanel spacer15 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel12.add(spacer15, gbc);
        final JPanel spacer16 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel12.add(spacer16, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
