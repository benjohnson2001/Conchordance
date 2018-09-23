package com.conchordance.run;

import com.conchordance.fretted.FretboardModel;
import com.conchordance.fretted.Instrument;
import com.conchordance.fretted.fingering.ChordFingering;
import com.conchordance.fretted.fingering.RecursiveChordFingeringGenerator;
import com.conchordance.fretted.fingering.list.ChordFingeringComparator;
import com.conchordance.fretted.fingering.list.ChordListModel;
import com.conchordance.music.Chord;
import com.conchordance.music.ChordType;
import com.conchordance.music.Note;
import com.conchordance.music.NoteName;
import com.conchordance.run.chordcheckers.ChordChecker;

import java.util.ArrayList;
import java.util.List;

public class RunTwoNoteChords {

   public static void main(String[] args) {

      List<ChordType> chordTypes = new ArrayList<>();
      chordTypes.add(ChordType.OCTAVEINTERVAL);
      chordTypes.add(ChordType.SECONDINTERVAL);
      chordTypes.add(ChordType.MINORTHIRDINTERVAL);
      chordTypes.add(ChordType.MAJORTHIRDINTERVAL);
      chordTypes.add(ChordType.FOURTHINTERVAL);
      chordTypes.add(ChordType.FLATFIFTHINTERVAL);
      chordTypes.add(ChordType.FIFTHINTERVAL);
      chordTypes.add(ChordType.SIXTHINTERVAL);
      chordTypes.add(ChordType.MINORSEVENTHINTERVAL);
      chordTypes.add(ChordType.MAJORSEVENTHINTERVAL);

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

      List<ChordFingering> chordFingerings = new RecursiveChordFingeringGenerator().getChordFingerings(fretboardModel);

      ChordListModel chords = new ChordListModel();
      chords.setComparator(new ChordFingeringComparator.ShapeComparator());
      chords.setChords(chordFingerings.toArray(new ChordFingering[chordFingerings.size()]));

      int chordNumber = 1;

      for (int i = 0; i < chords.getSize(); i++) {

         ChordFingering chordFingering = chords.getElementAt(i);

         if (
               Util.numberOfStringsPlayed(chordFingering.absoluteFrets) == 2 &&
               ChordChecker.isNotChordWithOpenStringOutOfPlace(chordFingering.absoluteFrets) &&
               thereAreNotMoreThanTwoUnplayedStringsBetweenNotes(chordFingering.absoluteFrets)) {

            String name = noteName.toString();
            if (modifier == 1) {
               name += "#";
            }

            String chordName = name + chordType.name;

            if (chordType.name == "sixth") {
               chordName = ChordName.getSixthChordName(noteName, modifier);
            }

            if (chordType.name == "minorseventh") {
               chordName = ChordName.getMinorSeventhChordName(noteName, modifier);
            }

//            if (chordNumber > 3) {
//               continue;
//            }

            System.out.println("[\"" + chordName + "\"," + chordNumber + "," + chordFingering + "],");
            chordNumber++;
         }
      }
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
}
