package com.conchordance.run;

import com.conchordance.music.NoteName;

public class ChordName {

   public static String getSixthChordName(NoteName noteName, int modifier) {

      if (noteName.toString().equals("C")) {

         if (modifier == 0) {
            return "AMadd#9";
         } else {
            return "A#Madd#9";
         }
      }


      if (noteName.toString().equals("D")) {

         if (modifier == 0) {
            return "BMadd#9";
         } else {
            return "CMadd#9";
         }
      }


      if (noteName.toString().equals("E")) {
         return "C#Madd#9";
      }


      if (noteName.toString().equals("F")) {

         if (modifier == 0) {
            return "DMadd#9";
         } else {
            return "D#Madd#9";
         }
      }


      if (noteName.toString().equals("G")) {

         if (modifier == 0) {
            return "EMadd#9";
         } else {
            return "FMadd#9";
         }
      }


      if (noteName.toString().equals("A")) {

         if (modifier == 0) {
            return "F#Madd#9";
         } else {
            return "GMadd#9";
         }
      }


      if (noteName.toString().equals("B")) {
         return "G#Madd#9";
      }
      
      return null;
   }


   public static String getMinorSeventhChordName(NoteName noteName, int modifier) {

      if (noteName.toString().equals("C")) {

         if (modifier == 0) {
            return "A#madd9";
         } else {
            return "Bmadd9";
         }
      }


      if (noteName.toString().equals("D")) {

         if (modifier == 0) {
            return "Cmadd9";
         } else {
            return "C#madd9";
         }
      }


      if (noteName.toString().equals("E")) {
         return "Dmadd9";
      }


      if (noteName.toString().equals("F")) {

         if (modifier == 0) {
            return "D#madd9";
         } else {
            return "Emadd9";
         }
      }


      if (noteName.toString().equals("G")) {

         if (modifier == 0) {
            return "Fmadd9";
         } else {
            return "F#madd9";
         }
      }


      if (noteName.toString().equals("A")) {

         if (modifier == 0) {
            return "Gmadd9";
         } else {
            return "G#madd9";
         }
      }


      if (noteName.toString().equals("B")) {
         return "Amadd9";
      }

      return null;
   }
}
