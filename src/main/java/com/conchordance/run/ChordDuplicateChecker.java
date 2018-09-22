package com.conchordance.run;

import com.conchordance.fretted.fingering.ChordFingering;

import java.util.Arrays;
import java.util.List;

public class ChordDuplicateChecker {


   public static boolean duplicateChordExists(List<ChordFingering> chords, ChordFingering potentialChord) {

      return chordIsDuplicatedFromTheLeft(chords, potentialChord) || chordIsDuplicatedFromTheRight(chords, potentialChord);
   }

   private static boolean chordIsDuplicatedFromTheLeft(List<ChordFingering> chords, ChordFingering potentialChord) {

      for (ChordFingering chord : chords) {

         int[] frets = chord.absoluteFrets;
         int[] fretsOfPotentialChord = potentialChord.absoluteFrets;

         for (int i = 0; i < Util.numberOfStringsPlayed(fretsOfPotentialChord); i++) {

            if (frets[i] != fretsOfPotentialChord[i]) {
               break;
            }

            if (i == Util.numberOfStringsPlayed(fretsOfPotentialChord)-1) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean chordIsDuplicatedFromTheRight(List<ChordFingering> chords, ChordFingering potentialChord) {

      for (ChordFingering chord : chords) {

         int[] frets = chord.absoluteFrets;
         int[] fretsOfPotentialChord = potentialChord.absoluteFrets;

         for (int i = 6-1; i >= (6-Util.numberOfStringsPlayed(fretsOfPotentialChord)); i--) {

            if (frets[i] != fretsOfPotentialChord[i]) {
               break;
            }

            if (i == Util.numberOfStringsPlayed(fretsOfPotentialChord)-1) {
               return true;
            }
         }
      }

      return false;
   }
}
