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

    static int[][] RCON = new int[][]{
        {1, 2, 4, 8, 16, 32, 64, 128, 27, 54},
        {00, 00, 00, 00, 00, 00, 00, 00, 00, 00},
        {00, 00, 00, 00, 00, 00, 00, 00, 00, 00},
        {00, 00, 00, 00, 00, 00, 00, 00, 00, 00}
    };

    static int[][] GaloisField = new int[][]{
        {02, 03, 01, 01},
        {01, 02, 03, 01},
        {01, 01, 02, 03},
        {03, 01, 01, 02},
    };

    public static String getSboxValues(String hexIn) {
        int x = Integer.parseInt(hexIn.substring(0, 1), 16);
        int y = Integer.parseInt(hexIn.substring(1, 2), 16);
        return SBOX[x][y];
    }

    public static String[] rotWord(String[] arrayIn, int times) {
        if (times == 0) {
            return arrayIn;
        }
        return rotWord(new String[]{arrayIn[1], arrayIn[2], arrayIn[3], arrayIn[0]}, times - 1);
    }

    public static String[][] generateRoundKey(String[][] Key, int round) {
        String[] column1 = {
            Key[0][0],
            Key[1][0],
            Key[2][0],
            Key[3][0],
        };
        String[] column2 = {
            Key[0][1],
            Key[1][1],
            Key[2][1],
            Key[3][1],
        };
        String[] column3 = {
            Key[0][2],
            Key[1][2],
            Key[2][2],
            Key[3][2],
        };
        String[] column4 = {
            Key[0][3],
            Key[1][3],
            Key[2][3],
            Key[3][3],
        };

        String[] oldColumn4 = new String[4];
        oldColumn4 = column4;
        int[] oldColumn4Int = {
            Integer.parseInt(oldColumn4[0], 16),
            Integer.parseInt(oldColumn4[1], 16),
            Integer.parseInt(oldColumn4[2], 16),
            Integer.parseInt(oldColumn4[3], 16)
        };
        column4 = rotWord(column4, 1);
        column4[0] = getSboxValues(column4[0]);
        column4[1] = getSboxValues(column4[1]);
        column4[2] = getSboxValues(column4[2]);
        column4[3] = getSboxValues(column4[3]);

        int[] column1int = {
            Integer.parseInt(column1[0], 16),
            Integer.parseInt(column1[1], 16),
            Integer.parseInt(column1[2], 16),
            Integer.parseInt(column1[3], 16)
        };
        int[] column2int = {
            Integer.parseInt(column2[0], 16),
            Integer.parseInt(column2[1], 16),
            Integer.parseInt(column2[2], 16),
            Integer.parseInt(column2[3], 16)
        };
        int[] column3int = {
            Integer.parseInt(column3[0], 16),
            Integer.parseInt(column3[1], 16),
            Integer.parseInt(column3[2], 16),
            Integer.parseInt(column3[3], 16)
        };
        int[] column4int = {
            Integer.parseInt(column4[0], 16),
            Integer.parseInt(column4[1], 16),
            Integer.parseInt(column4[2], 16),
            Integer.parseInt(column4[3], 16)
        };
        int[] newColumn1Int = column4int;
        int[] newColumn2Int = new int[4];
        int[] newColumn3Int = new int[4];
        int[] newColumn4Int = new int[4];
        for (int i = 0; i < 4; i++) {
            newColumn1Int[i] = (column4int[i] ^ column1int[i] ^ RCON[i][round]);
            //System.out.println(column4int[i] + " " + column1int[i] + " " + rcon[round][i]); test
        }

        String[] newColumn1 = new String[4];
        String[] newColumn2 = new String[4];
        String[] newColumn3 = new String[4];
        String[] newColumn4 = new String[4];

        for (int i = 0; i < 4; i++) {
            newColumn2Int[i] = (column2int[i] ^ newColumn1Int[i]);
        }
        for (int i = 0; i < 4; i++) {
            newColumn3Int[i] = (column3int[i] ^ newColumn2Int[i]);
        }
        for (int i = 0; i < 4; i++) {
            newColumn4Int[i] = (oldColumn4Int[i] ^ newColumn3Int[i]);
        }
        for (int i = 0; i < 4; i++) {
            newColumn1[i] = String.format("%02x", newColumn1Int[i]);
            newColumn2[i] = String.format("%02x", newColumn2Int[i]);
            newColumn3[i] = String.format("%02x", newColumn3Int[i]);
            newColumn4[i] = String.format("%02x", newColumn4Int[i]);
        }

        String[][] roundKey = {
            {newColumn1[0], newColumn2[0], newColumn3[0], newColumn4[0]},
            {newColumn1[1], newColumn2[1], newColumn3[1], newColumn4[1]},
            {newColumn1[2], newColumn2[2], newColumn3[2], newColumn4[2]},
            {newColumn1[3], newColumn2[3], newColumn3[3], newColumn4[3]}
        };

        return roundKey;
    }

    public static String[][] shiftRows(String[][] matrixIn) {
        for (int i = 0; i < matrixIn.length; i++) matrixIn[i] = matrixIn[i].clone();
        for (int i = 0; i < 4; i++) {
            String[] row = matrixIn[i];
            row = rotWord(row, i);
            matrixIn[i] = row;
        }
        return matrixIn;
    }

    public static String[][] mixColumns(String[][] matrixIn) {
        for (int i = 0; i < 4; i++) {
            int[] column = {Integer.parseInt(matrixIn[0][i], 16), Integer.parseInt(matrixIn[1][i], 16), Integer.parseInt(matrixIn[2][i], 16), Integer.parseInt(matrixIn[3][i], 16)};
            column = magicThingThatIDontUnderstandAnymore(column);
            matrixIn[0][i] = String.format("%02x", column[0]);
            matrixIn[1][i] = String.format("%02x", column[1]);
            matrixIn[2][i] = String.format("%02x", column[2]);
            matrixIn[3][i] = String.format("%02x", column[3]);
        }
        return matrixIn;
    }

    public static int[] magicThingThatIDontUnderstandAnymore(int[] c) {
        // I forgot how this works but just input a matrix column, trust me it works
        // - Dominik
        return new int[]{
            2 * c[0] ^ (c[0] > 127 ? 283 : 0) ^ 2 * c[1] ^ (c[1] > 127 ? 283 : 0) ^ c[1] ^ c[2] ^ c[3],
            c[0] ^ 2 * c[1] ^ (c[1] > 127 ? 283 : 0) ^ 2 * c[2] ^ (c[2] > 127 ? 283 : 0) ^ c[2] ^ c[3],
            c[0] ^ c[1] ^ 2 * c[2] ^ (c[2] > 127 ? 283 : 0) ^ 2 * c[3] ^ (c[3] > 127 ? 283 : 0) ^ c[3],
            2 * c[0] ^ (c[0] > 127 ? 283 : 0) ^ c[0] ^ c[1] ^ c[2] ^ 2 * c[3] ^ (c[3] > 127 ? 283 : 0),
        };
    }

    public static String[][] addRoundKey(String[][] matrixIn, String[][] roundKey) {
        for (int i = 0; i < 4; i++) {
            int[] columnMatrix = {Integer.parseInt(matrixIn[0][i], 16),
                Integer.parseInt(matrixIn[1][i], 16),
                Integer.parseInt(matrixIn[2][i], 16),
                Integer.parseInt(matrixIn[3][i], 16)};
            int[] columnKey = {Integer.parseInt(roundKey[0][i], 16),
                Integer.parseInt(roundKey[1][i], 16),
                Integer.parseInt(roundKey[2][i], 16),
                Integer.parseInt(roundKey[3][i], 16)};
            matrixIn[0][i] = String.format("%02x", (columnMatrix[0] ^ columnKey[0]) % 256);
            matrixIn[1][i] = String.format("%02x", (columnMatrix[1] ^ columnKey[1]) % 256);
            matrixIn[2][i] = String.format("%02x", (columnMatrix[2] ^ columnKey[2]) % 256);
            matrixIn[3][i] = String.format("%02x", (columnMatrix[3] ^ columnKey[3]) % 256);
        }
        return matrixIn;
    }

    public static String[][] Encrypt(String[][] Message, String[][] Key) {
        Message = addRoundKey(Message, Key);
        //System.out.println("Initial Round: ");  test
        //AesTest.PrintStringMatrix(Message);
        for (int i = 0; i < 9; i++) {
            Key = generateRoundKey(Key, i);
            //System.out.println("Key Round: "+ i); test
            //AesTest.PrintStringMatrix(Key);
            for (int j = 0; j < 4; j++) {
                Message[j][0] = getSboxValues(Message[j][0]);
                Message[j][1] = getSboxValues(Message[j][1]);
                Message[j][2] = getSboxValues(Message[j][2]);
                Message[j][3] = getSboxValues(Message[j][3]);
            }
            //System.out.println("Subbytes Round: "+ i); test
            //AesTest.PrintStringMatrix(Message);
            Message = shiftRows(Message);
            //System.out.println("shiftRows Round: "+ i); test
            //AesTest.PrintStringMatrix(Message);
            Message = mixColumns(Message);
            //System.out.println("mixColumns Round: "+ i); test
            //AesTest.PrintStringMatrix(Message);
            Message = addRoundKey(Message, Key);
            //System.out.println("addRoundKey Round: "+ i); test
            //AesTest.PrintStringMatrix(Message);
        }
        for (int k = 0; k < 4; k++) {
            Message[0][k] = getSboxValues(Message[0][k]);
            Message[1][k] = getSboxValues(Message[1][k]);
            Message[2][k] = getSboxValues(Message[2][k]);
            Message[3][k] = getSboxValues(Message[3][k]);
        }
        //System.out.println("Subbytes Round: 10"); test
        //AesTest.PrintStringMatrix(Message);
        Key = generateRoundKey(Key, 9);
        //System.out.println("Key Round: 10"); test
        //AesTest.PrintStringMatrix(Message);
        Message = shiftRows(Message);
        //System.out.println("shiftRows Round: 10"); test
        //AesTest.PrintStringMatrix(Message);
        Message = addRoundKey(Message, Key);
        //System.out.println("addRoundKey Round: 10"); test
        //AesTest.PrintStringMatrix(Message);
        return Message;
    }
}