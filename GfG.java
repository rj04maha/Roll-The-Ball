class GfG
{
    void printPat(int n)
    {
        // cycles through 3 times between $'s
         for (int i = 0; i < n; i++){
             // cycles through 3 to 0
             for (int j = n; j > 0; j--){
                 // cycles that many times printing them out
                 for (int m = i; m < n; m++){
                     System.out.print(j + " ");
                 }
             }
             System.out.print("$");
         }
         
    }
   
}