public class AesLib {
    static String[][] SBOX = new String[][]{
        {"63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76"},
        {"ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0"},
        {"b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15"},
        {"04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75"},
        {"09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84"},
        {"53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf"},
        {"d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8"},
        {"51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2"},
        {"cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73"},
        {"60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db"},
        {"e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79"},
        {"e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08"},
        {"ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a"},
        {"70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e"},
        {"e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df"},
        {"8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"},
    };
    
    static String[][] InverseSBOX = new String[][]{
        
        {"52","09","6A","D5","30","36","A5","38","BF","40","A3","9E","81","F3","D7","FB"},
        {"7C","E3","39","82","9B","2F","FF","87","34","8E","43","44","C4","DE","E9","CB"},
        {"54","7B","94","32","A6","C2","23","3D","EE","4C","95","0B","42","FA","C3","4E"},
        {"08","2E","A1","66","28","D9","24","B2","76","5B","A2","49","6D","8B","D1","25"},
        {"72","F8","F6","64","86","68","98","16","D4","A4","5C","CC","5D","65","B6","92"},
        {"6C","70","48","50","FD","ED","B9","DA","5E","15","46","57","A7","8D","9D","84"},
        {"90","D8","AB","00","8C","BC","D3","0A","F7","E4","58","05","B8","B3","45","06"},
        {"D0","2C","1E","8F","CA","3F","0F","02","C1","AF","BD","03","01","13","8A","6B"},
        {"3A","91","11","41","4F","67","DC","EA","97","F2","CF","CE","F0","B4","E6","73"},
        {"96","AC","74","22","E7","AD","35","85","E2","F9","37","E8","1C","75","DF","6E"},
        {"47","F1","1A","71","1D","29","C5","89","6F","B7","62","0E","AA","18","BE","1B"},
        {"FC","56","3E","4B","C6","D2","79","20","9A","DB","C0","FE","78","CD","5A","F4"},
        {"1F","DD","A8","33","88","07","C7","31","B1","12","10","59","27","80","EC","5F"},
        {"60","51","7F","A9","19","B5","4A","0D","2D","E5","7A","9F","93","C9","9C","EF"},
        {"A0","E0","3B","4D","AE","2A","F5","B0","C8","EB","BB","3C","83","53","99","61"},
        {"17","2B","04","7E","BA","77","D6","26","E1","69","14","63","55","21","0C","7D"}
    };
    
    static int[][] RCON = new int[][]{
        {1, 2, 4, 8, 16, 32, 64, 128, 27, 54},
        {00, 00, 00, 00, 00, 00, 00, 00, 00, 00},
        {00, 00, 00, 00, 00, 00, 00, 00, 00, 00},
        {00, 00, 00, 00, 00, 00, 00, 00, 00, 00},
    };

    static byte[][] GALOIS_FIELD = new byte[][]{
        {02, 03, 01, 01},
        {01, 02, 03, 01},
        {01, 01, 02, 03},
        {03, 01, 01, 02},
    };

    private static byte getSBoxValue(byte hex) {
        int x = Math.floorMod(hex >> 4, 16);
        int y = hex & 15;
        return (byte) Integer.parseInt(SBOX[x][y], 16);
    }
    
    private static byte getInverseSBoxValue(byte hex) {
        int x = Math.floorMod(hex >> 4, 16);
        int y = hex & 15;
        return (byte) Integer.parseInt(InverseSBOX[x][y], 16);
    }
    
    private static byte[] rotWord(byte[] arr, int times) {
        if (times == 0) {
            return arr;
        }
        return rotWord(new byte[]{arr[1], arr[2], arr[3], arr[0]}, times - 1);
    }
    
    private static byte[] InverseRotWord(byte[] arr, int times) {
        if (times == 0) {
            return arr;
        }
        return InverseRotWord(new byte[]{arr[3], arr[0], arr[1], arr[2]}, times - 1);
    }
    
    private static int[] magicThingThatIDontUnderstandAnymore(int[] c) {
        // Applies the mixColumns calculation.
        // I forgot how this works but just input a matrix column, trust me it works.
        // - Dominik
        return new int[]{
            2 * c[0] ^ (c[0] > 127 ? 283 : 0) ^ 2 * c[1] ^ (c[1] > 127 ? 283 : 0) ^ c[1] ^ c[2] ^ c[3],
            c[0] ^ 2 * c[1] ^ (c[1] > 127 ? 283 : 0) ^ 2 * c[2] ^ (c[2] > 127 ? 283 : 0) ^ c[2] ^ c[3],
            c[0] ^ c[1] ^ 2 * c[2] ^ (c[2] > 127 ? 283 : 0) ^ 2 * c[3] ^ (c[3] > 127 ? 283 : 0) ^ c[3],
            2 * c[0] ^ (c[0] > 127 ? 283 : 0) ^ c[0] ^ c[1] ^ c[2] ^ 2 * c[3] ^ (c[3] > 127 ? 283 : 0),
        };
    }

    public static void subBytes(byte[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = getSBoxValue(matrix[i][j]);
            }
        }
    }
    
    public static void InverseSubBytes(byte[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = getInverseSBoxValue(matrix[i][j]);
            }
        }
    }
    
    public static void shiftRows(byte[][] matrix) {
        for (int i = 0; i < 4; i++) {
            byte[] row = matrix[i];
            matrix[i] = rotWord(row, i);
        }
    }
    
    public static void InverseShiftRows(byte[][] matrix) {
        for (int i = 0; i < 4; i++) {
            byte[] row = matrix[i];
            matrix[i] = InverseRotWord(row, i);
        }
    }

    public static void mixColumns(byte[][] matrix) {
        for (int i = 0; i < 4; i++) {
            int[] column = {
                Math.floorMod(matrix[0][i], 256),
                Math.floorMod(matrix[1][i], 256),
                Math.floorMod(matrix[2][i], 256),
                Math.floorMod(matrix[3][i], 256),
            };
            column = magicThingThatIDontUnderstandAnymore(column);
            matrix[0][i] = (byte) column[0];
            matrix[1][i] = (byte) column[1];
            matrix[2][i] = (byte) column[2];
            matrix[3][i] = (byte) column[3];
        }
    }
    
    public static void InverseMixColumns(byte[][] matrix) {
        for (int i = 0; i < 4; i++) {
            int[] column = {
                Math.floorMod(matrix[0][i], 256),
                Math.floorMod(matrix[1][i], 256),
                Math.floorMod(matrix[2][i], 256),
                Math.floorMod(matrix[3][i], 256),
            };
            
            //column = magicThingThatIDontUnderstandAnymore(column);
            // muss dominik noch machen bitti
            
            matrix[0][i] = (byte) column[0];
            matrix[1][i] = (byte) column[1];
            matrix[2][i] = (byte) column[2];
            matrix[3][i] = (byte) column[3];
        }
    }
    
    public static void addRoundKey(byte[][] matrix, byte[][] key) {
        for (int i = 0; i < 4; i++) {
            int[] columnMatrix = {
                matrix[0][i],
                matrix[1][i],
                matrix[2][i],
                matrix[3][i],
            };
            int[] columnKey = {
                key[0][i],
                key[1][i],
                key[2][i],
                key[3][i],
            };
            matrix[0][i] = (byte) ((columnMatrix[0] ^ columnKey[0]) % 256);
            matrix[1][i] = (byte) ((columnMatrix[1] ^ columnKey[1]) % 256);
            matrix[2][i] = (byte) ((columnMatrix[2] ^ columnKey[2]) % 256);
            matrix[3][i] = (byte) ((columnMatrix[3] ^ columnKey[3]) % 256);
        }
    }

    public static byte[][] generateRoundKey(byte[][] key, int round) {
        byte[] column1 = {
            key[0][0],
            key[1][0],
            key[2][0],
            key[3][0],
        };
        byte[] column2 = {
            key[0][1],
            key[1][1],
            key[2][1],
            key[3][1],
        };
        byte[] column3 = {
            key[0][2],
            key[1][2],
            key[2][2],
            key[3][2],
        };
        byte[] column4 = {
            key[0][3],
            key[1][3],
            key[2][3],
            key[3][3],
        };

        byte[] oldColumn4 = column4;
        column4 = rotWord(column4, 1);
        column4[0] = getSBoxValue(column4[0]);
        column4[1] = getSBoxValue(column4[1]);
        column4[2] = getSBoxValue(column4[2]);
        column4[3] = getSBoxValue(column4[3]);

        byte[] newColumn1 = column4;
        byte[] newColumn2 = new byte[4];
        byte[] newColumn3 = new byte[4];
        byte[] newColumn4 = new byte[4];
        for (int i = 0; i < 4; i++) {
            newColumn1[i] = (byte) (column4[i] ^ column1[i] ^ RCON[i][round]);
        }

        for (int i = 0; i < 4; i++) {
            newColumn2[i] = (byte) (column2[i] ^ newColumn1[i]);
        }
        for (int i = 0; i < 4; i++) {
            newColumn3[i] = (byte) (column3[i] ^ newColumn2[i]);
        }
        for (int i = 0; i < 4; i++) {
            newColumn4[i] = (byte) (oldColumn4[i] ^ newColumn3[i]);
        }

        byte[][] roundKey = {
            {newColumn1[0], newColumn2[0], newColumn3[0], newColumn4[0]},
            {newColumn1[1], newColumn2[1], newColumn3[1], newColumn4[1]},
            {newColumn1[2], newColumn2[2], newColumn3[2], newColumn4[2]},
            {newColumn1[3], newColumn2[3], newColumn3[3], newColumn4[3]},
        };
        return roundKey;
    }

    public static byte[][] encrypt(byte[][] message, byte[][] key) {
        byte[][] encrypted = new byte[4][4];
        for (int i = 0; i < message.length; i++) encrypted[i] = message[i].clone();

        addRoundKey(encrypted, key);
        for (int i = 0; i < 10; i++) {
            subBytes(encrypted);
            shiftRows(encrypted);
            if (i != 9) mixColumns(encrypted);
            key = generateRoundKey(key, i);
            addRoundKey(encrypted, key);
        }

        return encrypted;
    }
    
    public static byte[][] decrypt(byte[][] message, byte[][] CipherKey){
        byte[][] decrypted = new byte[4][4];
        for (int i = 0; i < message.length; i++) decrypted[i] = message[i].clone();
        
        byte[][] key = CipherKey;
        for (int j = 0; j < 10; j++){
             key = generateRoundKey(key,j);
        }
        addRoundKey(decrypted, key);
        key = CipherKey;
        for(int i = 9; i > 0; i--){
            InverseShiftRows(decrypted);
            InverseSubBytes(decrypted);
            for (int j = 0; j < i; j++){
             key = generateRoundKey(key, j);
        }
            addRoundKey(decrypted, key);
            InverseMixColumns(decrypted);
        }
        InverseShiftRows(decrypted);
        InverseSubBytes(decrypted);
        key = generateRoundKey(CipherKey, 0);
        addRoundKey(decrypted, key);
        
        return decrypted;
    }
}