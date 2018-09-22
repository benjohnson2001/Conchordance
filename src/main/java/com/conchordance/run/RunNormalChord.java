package com.conchordance.run;

import com.conchordance.fretted.FretboardModel;
import com.conchordance.fretted.Instrument;
import com.conchordance.fretted.fingering.ChordFingering;
import com.conchordance.fretted.fingering.RecursiveChordFingeringGenerator;
import com.conchordance.fretted.fingering.list.ChordFingeringComparator;
import com.conchordance.fretted.fingering.list.ChordListModel;
import com.conchordance.fretted.fingering.validation.ChordFingeringValidator;
import com.conchordance.fretted.fingering.validation.StrummableValidator;
import com.conchordance.music.Chord;
import com.conchordance.music.ChordType;
import com.conchordance.music.Note;
import com.conchordance.music.NoteName;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public class RunNormalChord {

   public static void main(String[] args) {

      Chord fMajorChord = new Chord(new Note(NoteName.F, 0), ChordType.MAJOR);
      FretboardModel fretboardModel = new FretboardModel(Instrument.TELE, fMajorChord);

      List<ChordFingering> currentSetOfChords = getCurrentSetOfChords(fretboardModel);
      List<ChordFingering> curatedChords = getDeduplicatedSetOfChords(currentSetOfChords);

      for (ChordFingering curatedChord : curatedChords) {

         System.out.println(curatedChord);
      }
   }

   private static List<ChordFingering> getCurrentSetOfChords(FretboardModel fretboardModel) {

      List<ChordFingering> chordFingerings = new RecursiveChordFingeringGenerator().getChordFingerings(fretboardModel);
      ChordListModel chords = new ChordListModel();
      chords.setComparator(new ChordFingeringComparator.ShapeComparator());
      chords.setValidator(new StrummableValidator());
      chords.setChords(chordFingerings.toArray(new ChordFingering[chordFingerings.size()]));

      List<ChordFingering> currentSetOfChords = new ArrayList<>();

      for (int i = 0; i < chords.getSize(); i++) {

         ChordFingering chordFingering = chords.getElementAt(i);

         int[] frets = chordFingering.absoluteFrets;

         if (  ChordChecker.isNotBrokenSetChord(frets) &&
               ChordChecker.isNotChordWithOpenStringOutOfPlace(frets)) {

            currentSetOfChords.add(chordFingering);
         }
      }

      sortChordsByNumberOfStringsPlayed(currentSetOfChords);

      return currentSetOfChords;
   }

   private static List<ChordFingering> getDeduplicatedSetOfChords(List<ChordFingering> currentSetOfChords) {

      List<ChordFingering> curatedChords = new ArrayList<>();

      for (ChordFingering currentChord : currentSetOfChords) {

         if (ChordDuplicateChecker.duplicateChordExists(curatedChords, currentChord)) {
            continue;
         }

         curatedChords.add(currentChord);
      }

      sortChordsByFretPosition(curatedChords);

      return curatedChords;
   }

   private static void sortChordsByNumberOfStringsPlayed(List<ChordFingering> chords) {

      Collections.sort(chords, new Comparator<ChordFingering>() {
         @Override
         public int compare(ChordFingering o1, ChordFingering o2) {

            if (Util.numberOfStringsPlayed(o2.absoluteFrets) < Util.numberOfStringsPlayed(o1.absoluteFrets)) {
               return -1;
            } else {
               return 1;
            }
         }
      });
   }

   private static void sortChordsByFretPosition(List<ChordFingering> chords) {

      Collections.sort(chords, new Comparator<ChordFingering>() {
         @Override
         public int compare(ChordFingering o1, ChordFingering o2) {

            if (o1.position < o2.position) {
               return -1;
            } else {
               return 1;
            }
         }
      });
   }
}
