import java.util.Arrays;

public class AesTest {
    void main() {
    }

    public AesTest() {
        System.out.print('\u000c');

        byte[][] matrix;

        System.out.print("Testing SubBytes: ");
        matrix = s2b(new String[][]{
            {"19", "a0", "9a", "e9"},
            {"3d", "f4", "c6", "f8"},
            {"e3", "e2", "8d", "48"},
            {"be", "2b", "2a", "08"},
        });
        AesLib.subBytes(matrix);
        System.out.println(Arrays.deepEquals(matrix, s2b(new String[][]{
            {"d4", "e0", "b8", "1e"},
            {"27", "bf", "b4", "41"},
            {"11", "98", "5d", "52"},
            {"ae", "f1", "e5", "30"},
        })));

        System.out.print("Testing ShiftRows: ");
        matrix = s2b(new String[][]{
            {"d4", "e0", "b8", "1e"},
            {"27", "bf", "b4", "41"},
            {"11", "98", "5d", "52"},
            {"ae", "f1", "e5", "30"},
        });
        AesLib.shiftRows(matrix);
        System.out.println(Arrays.deepEquals(matrix, s2b(new String[][]{
            {"d4", "e0", "b8", "1e"},
            {"bf", "b4", "41", "27"},
            {"5d", "52", "11", "98"},
            {"30", "ae", "f1", "e5"},
        })));

        System.out.print("Testing MixColumns: ");
        matrix = s2b(new String[][]{
            {"d4", "e0", "b8", "1e"},
            {"bf", "b4", "41", "27"},
            {"5d", "52", "11", "98"},
            {"30", "ae", "f1", "e5"},
        });
        AesLib.mixColumns(matrix);
        System.out.println(Arrays.deepEquals(matrix, s2b(new String[][]{
            {"04", "e0", "48", "28"},
            {"66", "cb", "f8", "06"},
            {"81", "19", "d3", "26"},
            {"e5", "9a", "7a", "4c"},
        })));

        System.out.print("Testing AddRoundKey: ");
        matrix = s2b(new String[][]{
            {"04", "e0", "48", "28"},
            {"66", "cb", "f8", "06"},
            {"81", "19", "d3", "26"},
            {"e5", "9a", "7a", "4c"},
        });
        AesLib.addRoundKey(matrix, s2b(new String[][]{
            {"a0", "88", "23", "2a"},
            {"fa", "54", "a3", "6c"},
            {"fe", "2c", "39", "76"},
            {"17", "b1", "39", "05"},
        }));
        System.out.println(Arrays.deepEquals(matrix, s2b(new String[][]{
            {"a4", "68", "6b", "02"},
            {"9c", "9f", "5b", "6a"},
            {"7f", "35", "ea", "50"},
            {"f2", "2b", "43", "49"},
        })));

        System.out.print("Testing GenerateRoundKey: ");
        System.out.println(Arrays.deepEquals(AesLib.generateRoundKey(s2b(new String[][]{
            {"2b", "28", "ab", "09"},
            {"7e", "ae", "f7", "cf"},
            {"15", "d2", "15", "4f"},
            {"16", "a6", "88", "3c"},
        }), 0), s2b(new String[][]{
            {"a0", "88", "23", "2a"},
            {"fa", "54", "a3", "6c"},
            {"fe", "2c", "39", "76"},
            {"17", "b1", "39", "05"},
        })));

        System.out.print("Testing Encrypt: ");
        System.out.println(Arrays.deepEquals(AesLib.encrypt(s2b(new String[][]{
            {"32", "88", "31", "e0"},
            {"43", "5a", "31", "37"},
            {"f6", "30", "98", "07"},
            {"a8", "8d", "a2", "34"},
        }), s2b(new String[][]{
            {"2b", "28", "ab", "09"},
            {"7e", "ae", "f7", "cf"},
            {"15", "d2", "15", "4f"},
            {"16", "a6", "88", "3c"},
        })), s2b(new String[][]{
            {"39", "02", "dc", "19"},
            {"25", "dc", "11", "6a"},
            {"84", "09", "85", "0b"},
            {"1d", "fb", "97", "32"},
        })));
    }

    public static byte[][] s2b(String[][] matrix) {
        // Converts a hex string matrix to a byte matrix.
        byte[][] newMatrix = new byte[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                newMatrix[i][j] = (byte) Integer.parseInt(matrix[i][j], 16);
            }
        }
        return newMatrix;
    }

    public static void printStringMatrix(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + ",");
            }
            System.out.println();
        }
    }
}