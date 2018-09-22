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

import java.util.List;

public class RunTwoNoteChord {

   public static void main(String[] args) {


      for (NoteName noteName : NoteName.values()) {

         System.out.println(noteName);
      }


      Chord fOctave = new Chord(new Note(NoteName.C, 0), ChordType.OCTAVE);
      FretboardModel fretboardModel = new FretboardModel(Instrument.STRAT, fOctave);

      List<ChordFingering> chordFingerings = new RecursiveChordFingeringGenerator().getChordFingerings(fretboardModel);

      ChordListModel chords = new ChordListModel();
      chords.setComparator(new ChordFingeringComparator.ShapeComparator());
      chords.setChords(chordFingerings.toArray(new ChordFingering[chordFingerings.size()]));

      int chordNumber = 1;

      for (int i = 0; i < chords.getSize(); i++) {

         ChordFingering chordFingering = chords.getElementAt(i);

         if (Util.numberOfStringsPlayed(chordFingering.absoluteFrets) == 2 &&
               thereAreNotMoreThanTwoUnplayedStringsBetweenNotes(chordFingering.absoluteFrets)) {

            System.out.println("[\"CM13\"," + chordNumber + "," + chordFingering + "],");
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
