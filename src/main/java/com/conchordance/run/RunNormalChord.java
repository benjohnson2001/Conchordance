package com.conchordance.run;

import com.conchordance.fretted.FretboardModel;
import com.conchordance.fretted.Instrument;
import com.conchordance.fretted.fingering.ChordFingering;
import com.conchordance.fretted.fingering.RecursiveChordFingeringGenerator;
import com.conchordance.fretted.fingering.list.ChordListModel;
import com.conchordance.music.Chord;
import com.conchordance.music.ChordType;
import com.conchordance.music.Note;
import com.conchordance.music.NoteName;
import com.conchordance.run.chordcheckers.ChordChecker;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;


public class RunNormalChord {

   public static void main(String[] args) throws Exception {

      List<ChordType> chordTypes = new ArrayList<>();

      chordTypes.add(ChordType.SUS2);
         chordTypes.add(ChordType.SECONDINTERVAL);

      chordTypes.add(ChordType.MINOR);
         chordTypes.add(ChordType.MINORTHIRDINTERVAL);

      chordTypes.add(ChordType.MAJOR);
         chordTypes.add(ChordType.MAJORTHIRDINTERVAL);


      chordTypes.add(ChordType.SUS4);
      chordTypes.add(ChordType.DIM);
      chordTypes.add(ChordType.AUG);
      chordTypes.add(ChordType.MINORSIXTH);
      chordTypes.add(ChordType.MAJORSIXTH);
      chordTypes.add(ChordType.DOMINANTSEVENTH);
      chordTypes.add(ChordType.MAJORSEVENTH);
      chordTypes.add(ChordType.MINORSEVENTH);

      StringBuilder stringBuilder = new StringBuilder();

      for (NoteName noteName : NoteName.values()) {

         for (ChordType chordType : chordTypes) {
            printChords(noteName, 0, chordType, stringBuilder);
         }

         if (noteName.toString().equals("C") ||
               noteName.toString().equals("D") ||
               noteName.toString().equals("F") ||
               noteName.toString().equals("G") ||
               noteName.toString().equals("A")) {

            for (ChordType chordType : chordTypes) {
               printChords(noteName, 1, chordType, stringBuilder);
            }
         }
      }

      FileUtils.writeStringToFile(new File("output/normalChords.txt"), stringBuilder.toString(), Charset.forName("UTF-8"));
   }

   private static List<ChordFingering> getAdditionalChords(NoteName noteName, int modifier, ChordType chordType, Instrument instrument) {

      List<ChordFingering> additionalChords = new ArrayList<>();

      ChordType additionalChordType = null;

      if (chordType.name.equals("sus2")) {
         additionalChordType = ChordType.SECONDINTERVAL;
      }

      if (chordType.name.equals("m")) {
         additionalChordType = ChordType.MINORTHIRDINTERVAL;
      }

      if (chordType.name.equals("M")) {
         additionalChordType = ChordType.MAJORTHIRDINTERVAL;
      }

      if (additionalChordType != null) {
         Chord additionalChord = new Chord(new Note(noteName, modifier), additionalChordType);
         FretboardModel additionalFretboardModel = new FretboardModel(instrument, additionalChord);
         additionalChords = getCurrentSetOfDyadChords(additionalFretboardModel);
      }

      return additionalChords;
   }

   private static void printChords(NoteName noteName, int modifier, ChordType chordType, StringBuilder stringBuilder) {

      Instrument instrument = Instrument.TELE;

      Chord chord = new Chord(new Note(noteName, modifier), chordType);
      FretboardModel fretboardModel = new FretboardModel(instrument, chord);
      List<ChordFingering> currentSetOfChords = getCurrentSetOfChords(fretboardModel);
      // List<ChordFingering> additionalChords = getAdditionalChords(noteName, modifier, chordType, instrument);
      // currentSetOfChords.addAll(additionalChords);


      Collections.sort(currentSetOfChords, new CustomComparator());
//      sortChordsByFretPosition(currentSetOfChords);
//      List<ChordFingering> curatedChords = getDeduplicatedSetOfChords(currentSetOfChords);

      String name = noteName.toString();
      if (modifier == 1) {
         name += "#";
      }

      String chordName = name + chordType.name;

      for (int i = 0; i < currentSetOfChords.size(); i++) {

         stringBuilder.append("[\"" + chordName + "\"," + (i + 1) + "," + currentSetOfChords.get(i) + "],");
         stringBuilder.append("\n");
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
                        ChordChecker.isNotChordWithOpenStringOutOfPlace(frets) &&
                        ChordChecker.theLastTwoStringsAreNotPlayed(frets)
                  ) {

               currentSetOfChords.add(chordFingering);
            }
         }
      }


      return currentSetOfChords;
   }


   private static List<ChordFingering> getCurrentSetOfDyadChords(FretboardModel fretboardModel) {

      List<ChordFingering> currentSetOfChords = new ArrayList<>();

      List<ChordFingering> chordFingerings = new RecursiveChordFingeringGenerator().getChordFingerings(fretboardModel);

      ChordListModel chords = new ChordListModel();
      chords.setComparator(new CustomComparator());
      chords.setChords(chordFingerings.toArray(new ChordFingering[chordFingerings.size()]));

      for (int i = 0; i < chords.getSize(); i++) {

         ChordFingering chordFingering = chords.getElementAt(i);

         if (
               Util.numberOfStringsPlayed(chordFingering.absoluteFrets) == 2 &&
                     ChordChecker.isNotChordWithOpenStringOutOfPlace(chordFingering.absoluteFrets) &&
                     thereAreNotMoreThanTwoUnplayedStringsBetweenNotes(chordFingering.absoluteFrets) &&
                     ChordChecker.theLastTwoStringsAreNotPlayed(chordFingering.absoluteFrets)) {

            currentSetOfChords.add(chordFingering);
         }
      }
      return currentSetOfChords;
   }


   private static boolean thereAreNotMoreThanTwoUnplayedStringsBetweenNotes(int[] frets) {

      int numberOfUnplayedStrings = 0;
      int numberOfPlayedStrings = 0;

      for (int i = 0; i < frets.length; i++) {

         if (numberOfPlayedStrings > 0 && numberOfUnplayedStrings > 2) {
            return false;
         }

         if (frets[i] == -1) {
            numberOfUnplayedStrings++;
         } else {
            numberOfPlayedStrings++;
            numberOfUnplayedStrings = 0;
         }
      }

      return true;
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