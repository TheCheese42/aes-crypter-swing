public class AesTest
{
     public AesTest(){
         System.out.print('\u000c');
        //System.out.print(AesLib.getSboxValues("19"));             success
        
        //PrintStringMatrix(AesLib.shiftRows(                       success
        //new String[][]
        //{
        //    {"d4","e0","b8","1e"},
        //    {"27","bf","b4","41"},
        //    {"11","98","5d","52"},
        //    {"ae","f1","e5","30"}
        //}));
        //PrintStringMatrix(AesLib.mixColumns(new String[][]        success
        //{
        //    {"d4","e0","b8","1e"},
        //    {"bf","b4","41","27"},
        //    {"5d","52","11","98"},
        //    {"30","ae","f1","e5"}
        //}));
        //PrintStringMatrix(AesLib.addRoundKey(new String[][]       success 
        //    {
        //    {"04","e0","48","28"},
        //    {"66","cb","f8","06"},
        //    {"81","19","d3","26"},
        //    {"e5","9a","7a","4c"},
        //},new String[][]
        //{
        //    {"a0","88","23","2a"},
        //    {"fa","54","a3","6c"},
        //    {"fe","2c","39","76"},
        //    {"17","b1","39","05"}
        //}));  
        
     }
     public void PrintStringMatrix(String[][] StringIn){
         for(int i = 0;i<StringIn.length;i++){
             for(int j = 0; j<StringIn[i].length;j++){
                 System.out.print(StringIn[i][j]+",");
                }
             System.out.println();
         }
        }
}