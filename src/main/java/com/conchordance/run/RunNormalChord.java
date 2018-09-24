package com.conchordance.run;

import com.conchordance.fretted.FretboardModel;
import com.conchordance.fretted.Instrument;
import com.conchordance.fretted.fingering.ChordFingering;
import com.conchordance.fretted.fingering.RecursiveChordFingeringGenerator;
import com.conchordance.fretted.fingering.list.ChordFingeringComparator;
import com.conchordance.fretted.fingering.list.ChordListModel;
import com.conchordance.fretted.fingering.validation.StrummableValidator;
import com.conchordance.music.Chord;
import com.conchordance.music.ChordType;
import com.conchordance.music.Note;
import com.conchordance.music.NoteName;
import com.conchordance.run.chordcheckers.ChordChecker;
import com.conchordance.run.chordcheckers.ChordDuplicateChecker;

import java.util.*;


public class RunNormalChord {

   public static void main(String[] args) {

      List<ChordType> chordTypes = new ArrayList<>();
      chordTypes.add(ChordType.MAJOR);
      chordTypes.add(ChordType.MINOR);
      chordTypes.add(ChordType.POWER);
      chordTypes.add(ChordType.SUS2);
      chordTypes.add(ChordType.SUS4);
      chordTypes.add(ChordType.DIM);
      chordTypes.add(ChordType.AUG);
      chordTypes.add(ChordType.MAJORSIXTH);
      chordTypes.add(ChordType.MINORSIXTH);
      chordTypes.add(ChordType.DOMINANTSEVENTH);
      chordTypes.add(ChordType.MAJORSEVENTH);
      chordTypes.add(ChordType.MINORSEVENTH);

      for (NoteName noteName : NoteName.values()) {

         for (ChordType chordType : chordTypes) {
            printChords(noteName, 0, chordType);
         }

         if (noteName.toString().equals("C") ||
               noteName.toString().equals("D") ||
               noteName.toString().equals("F") ||
               noteName.toString().equals("G") ||
               noteName.toString().equals("A")) {

            for (ChordType chordType : chordTypes) {
               printChords(noteName, 1, chordType);
            }
         }
      }
   }

   private static void printChords(NoteName noteName, int modifier, ChordType chordType) {

      Chord chord = new Chord(new Note(noteName, modifier), chordType);
      FretboardModel fretboardModel = new FretboardModel(Instrument.TELE, chord);

      List<ChordFingering> currentSetOfChords = getCurrentSetOfChords(fretboardModel);
      Collections.sort(currentSetOfChords, new CustomComparator());
//      sortChordsByFretPosition(currentSetOfChords);
//      List<ChordFingering> curatedChords = getDeduplicatedSetOfChords(currentSetOfChords);

      String name = noteName.toString();
      if (modifier == 1) {
         name += "#";
      }

      String chordName = name + chordType.name;

//      if (!chordName.equals("FM")) {
//         return;
//      }
//
//      if (currentSetOfChords.size() == 11) {
//         System.out.println(chordName);
//      }

      for (int i = 0; i < currentSetOfChords.size(); i++) {
//      for (int i = 10; i < 11; i++) {
         System.out.println("[\"" + chordName + "\"," + (i + 1) + "," + currentSetOfChords.get(i) + "],");
      }
   }

   private static List<ChordFingering> getCurrentSetOfChords(FretboardModel fretboardModel) {

      List<ChordFingering> chordFingerings = new RecursiveChordFingeringGenerator().getChordFingerings(fretboardModel);
      ChordListModel chords = new ChordListModel();
      chords.setComparator(new CustomComparator());
      chords.setChords(chordFingerings.toArray(new ChordFingering[chordFingerings.size()]));

      List<ChordFingering> currentSetOfChords = new ArrayList<>();

      int minNumberOfStringsPlayed = 3;
      int maxNumberOfStringsPlayed = 6;

      for (int i = minNumberOfStringsPlayed; i < maxNumberOfStringsPlayed+1; i++) {

         for (int j = 0; j < chords.getSize(); j++) {

            ChordFingering chordFingering = chords.getElementAt(j);

            int[] frets = chordFingering.absoluteFrets;

            if (
                  Util.numberOfStringsPlayed(frets) == i &&
                        ChordChecker.isNotBrokenSetChord(frets) &&
                        ChordChecker.isNotChordWithOpenStringOutOfPlace(frets)
                  ) {

               currentSetOfChords.add(chordFingering);
            }
         }
      }

      return currentSetOfChords;
   }

//   private static List<ChordFingering> getDeduplicatedSetOfChords(List<ChordFingering> currentSetOfChords) {
//
//      List<ChordFingering> curatedChords = new ArrayList<>();
//
//      for (ChordFingering currentChord : currentSetOfChords) {
//
//         if (ChordDuplicateChecker.duplicateChordExists(curatedChords, currentChord)) {
//            continue;
//         }
//
//         curatedChords.add(currentChord);
//      }
//
//      sortChordsByFretPosition(curatedChords);
//
//      return curatedChords;
//   }
//
//   private static void sortChordsByNumberOfStringsPlayed(List<ChordFingering> chords) {
//
//      Collections.sort(chords, new Comparator<ChordFingering>() {
//         @Override
//         public int compare(ChordFingering o1, ChordFingering o2) {
//
//            if (Util.numberOfStringsPlayed(o2.absoluteFrets) < Util.numberOfStringsPlayed(o1.absoluteFrets)) {
//               return -1;
//            } else {
//               return 1;
//            }
//         }
//      });
//   }
//
//   private static void sortChordsByFretPosition(List<ChordFingering> chords) {
//
//      Collections.sort(chords, new Comparator<ChordFingering>() {
//         @Override
//         public int compare(ChordFingering o1, ChordFingering o2) {
//
//            if (o1.position < o2.position) {
//               return -1;
//            } else if (o1.position > o2.position) {
//               return 1;
//            }
//
//            if (Util.numberOfStringsPlayed(o1.absoluteFrets) > Util.numberOfStringsPlayed(o2.absoluteFrets)) {
//               return 1;
//            } else if (Util.numberOfStringsPlayed(o1.absoluteFrets) < Util.numberOfStringsPlayed(o2.absoluteFrets)) {
//               return -1;
//            }
//
//            for (int fret = o1.position; fret <= o1.position+5; ++fret) {
//               // TODO This should not assume that both ChordFingerings have the same number of strings
//               for (int string = 0; string < o1.absoluteFrets.length; ++string) {
//                  boolean aHasNote = o1.absoluteFrets[string] == fret;
//                  boolean bHasNote = o2.absoluteFrets[string] == fret;
//
//                  if (aHasNote && !bHasNote)
//                     return -1;
//                  if (bHasNote && !aHasNote)
//                     return 1;
//               }
//            }
//
//            return 0;
//         }
//      });
//   }
}