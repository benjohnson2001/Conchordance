package com.conchordance.run;

public class Util {


   public static int numberOfStringsPlayed(int[] frets) {

      int numberOfStringsPlayed = 0;

      for (int i = 0; i < frets.length; i++) {

         if (frets[i] != -1) {
            numberOfStringsPlayed++;
         }
      }

      return numberOfStringsPlayed;
   }
}
