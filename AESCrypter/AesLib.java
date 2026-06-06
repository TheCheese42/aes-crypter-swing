public class AesLib
{
    
static String[][] sBox = new String[][] {
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

static int[][] rcon = new int[][] {
{2,4,8,16,32,64,128,27,54},
{00,00,00,00,00,00,00,00,00},
{00,00,00,00,00,00,00,00,00},
{00,00,00,00,00,00,00,00,00},
};

static int[][] GaloisField = new int[][] {
{02,03,01,01},
{01,02,03,01},
{01,01,02,03},
{03,01,01,02},
};

public static String getSboxValues(String hexIn){
    int x = Integer.parseInt(hexIn.substring(0,1), 16);
    int y = Integer.parseInt(hexIn.substring(1,2), 16);
    return sBox[x][y];
}

public static String[] rotWord(String[] arrayIn, int times){
    if(times == 0){
        return arrayIn;
    }
    return rotWord(new String[] {arrayIn[1], arrayIn[2], arrayIn[3], arrayIn[0]}, times-1);
}

public static String[][] generateRoundKeys(){
    //gehirnfick
    return new String[0][0];
}

public static String[][] shiftRows(String[][] matrixIn){
    for (int i = 0; i < matrixIn.length; i++) matrixIn[i] = matrixIn[i].clone();
    for (int i = 0; i<4;i++) {
        String[] row = matrixIn[i];
        row = rotWord(row, i);
        matrixIn[i] = row;
    }
    return matrixIn;
}

public static String[][] mixColumns(String[][] matrixIn){
for (int i = 0; i < matrixIn.length; i++) matrixIn[i] = matrixIn[i].clone();
for (int i = 0; i<4;i++){
    int[] column = {Integer.parseInt(matrixIn[0][i], 16),Integer.parseInt(matrixIn[1][i], 16),Integer.parseInt(matrixIn[2][i], 16),Integer.parseInt(matrixIn[3][i], 16)};
    column = magicThingThatIDontUnderstandAnymore(column);
    matrixIn[0][i] = String.format("%02x",column[0]);
    matrixIn[1][i] = String.format("%02x",column[1]);
    matrixIn[2][i] = String.format("%02x",column[2]);
    matrixIn[3][i] = String.format("%02x",column[3]);
}
return matrixIn;
}

public static int[] magicThingThatIDontUnderstandAnymore(int[] c) {
        // I forgot how this works but just input a matrix column, trust me it works
        // - Dominik
        return new int[]{
            2 * c[0] ^ (c[0] > 127 ? 283 : 0) ^ 2 * c[1] ^ (c[1] > 127 ? 283 : 0) ^ c[1] ^ 1 * c[2] ^ 1 * c[3],
            1 * c[0] ^ 2 * c[1] ^ (c[1] > 127 ? 283 : 0) ^ 2 * c[2] ^ (c[2] > 127 ? 283 : 0) ^ c[2] ^ 1 * c[3],
            1 * c[0] ^ 1 * c[1] ^ 2 * c[2] ^ (c[2] > 127 ? 283 : 0) ^ 2 * c[3] ^ (c[3] > 127 ? 283 : 0) ^ c[3],
            2 * c[0] ^ (c[0] > 127 ? 283 : 0) ^ c[0] ^ 1 * c[1] ^ 1 * c[2] ^ 2 * c[3] ^ (c[3] > 127 ? 283 : 0),
        };
    
}

public static String[][] addRoundKey(String[][] matrixIn, String[][] roundKey){
    for (int i = 0; i < matrixIn.length; i++) matrixIn[i] = matrixIn[i].clone();
    for (int i=0;i<4;i++){
        int[] columnMatrix = {Integer.parseInt(matrixIn[0][i],16),
        Integer.parseInt(matrixIn[1][i],16),
        Integer.parseInt(matrixIn[2][i],16),
        Integer.parseInt(matrixIn[3][i],16)};
        int[] columnKey = {Integer.parseInt(roundKey[0][i],16),
        Integer.parseInt(roundKey[1][i],16),
        Integer.parseInt(roundKey[2][i],16),
        Integer.parseInt(roundKey[3][i],16)};
        matrixIn[0][i] = String.format("%02x",(columnMatrix[0] ^ columnKey[0]) % 256);
        matrixIn[1][i] = String.format("%02x",(columnMatrix[1] ^ columnKey[1]) % 256);
        matrixIn[2][i] = String.format("%02x",(columnMatrix[2] ^ columnKey[2]) % 256);
        matrixIn[3][i] = String.format("%02x",(columnMatrix[3] ^ columnKey[3]) % 256);
    }
    return matrixIn;
}
}