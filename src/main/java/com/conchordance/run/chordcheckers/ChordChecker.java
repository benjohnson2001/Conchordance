package com.conchordance.run.chordcheckers;

public class ChordChecker {


//   {17, -1, -1, 17, -1, 17}
//   {17, 3, 6, 17, -1, -1}
//   {-1, -1, 8, 17, 12, 17}

   public static boolean isNotChordWithOpenStringOutOfPlace(int[] frets) {

      return !isChordWithOpenStringOutOfPlace(frets);
   }

   public static boolean isChordWithOpenStringOutOfPlace(int[] frets) {

      int highestFret = -1;
      boolean containsOpenChord = false;

      for (int i = 0; i < frets.length; i++) {

         if (frets[i] == 0) {
            containsOpenChord = true;
         }

         if (frets[i] > highestFret) {
            highestFret = frets[i];
         }
      }

      if (containsOpenChord && highestFret > 4) {
         return true;
      }

      return false;
   }

   public static boolean isNotBrokenSetChord(int[] frets) {

      return !isBrokenSetChord(frets);
   }

   public static boolean isBrokenSetChord(int[] frets) {

      return thereAreUnplayedStringsAfterPlayedStrings(frets) &&
            thereAreUnplayedStringsBeforePlayedStrings(frets);
   }

   private static boolean thereAreUnplayedStringsAfterPlayedStrings(int[] frets) {

      int numberOfUnplayedStrings = 0;
      int numberOfPlayedStrings = 0;

      for (int i = 0; i < frets.length; i++) {

         if (frets[i] == -1) {
            numberOfUnplayedStrings++;
         } else {
            numberOfPlayedStrings++;
         }

         if (frets[i] == -1 && numberOfUnplayedStrings > 0 && numberOfPlayedStrings > 0) {
            return true;
         }
      }

      return false;
   }


   private static boolean thereAreUnplayedStringsBeforePlayedStrings(int[] frets) {

      int numberOfUnplayedStrings = 0;
      int numberOfPlayedStrings = 0;

      for (int i = frets.length-1; i >= 0; i--) {

         if (frets[i] == -1) {
            numberOfUnplayedStrings++;
         } else {
            numberOfPlayedStrings++;
         }

         if (frets[i] == -1 && numberOfUnplayedStrings > 0 && numberOfPlayedStrings > 0) {
            return true;
         }
      }

      return false;
   }

   public static boolean theFirstTwoStringsAreNotPlayed(int[] frets) {

      if (frets[0] != -1) {
         return false;
      }

      if (frets[1] != -1) {
         return false;
      }

      return true;
   }

   public static boolean theLastTwoStringsAreNotPlayed(int[] frets) {

      if (frets[4] != -1) {
         return false;
      }

      if (frets[5] != -1) {
         return false;
      }

      return true;
   }
}
