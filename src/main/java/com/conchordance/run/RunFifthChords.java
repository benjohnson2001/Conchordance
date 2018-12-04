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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunFifthChords {

   public static void main(String[] args) throws Exception {

      List<ChordType> chordTypes = new ArrayList<>();
      chordTypes.add(ChordType.FLATFIFTHINTERVAL);
      chordTypes.add(ChordType.POWER);

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

      FileUtils.writeStringToFile(new File("output/teleFifthChords.txt"), stringBuilder.toString(), Charset.forName("UTF-8"));
   }

   private static void printChords(NoteName noteName, int modifier, ChordType chordType, StringBuilder stringBuilder) {

      Chord chord = new Chord(new Note(noteName, modifier), chordType);
      FretboardModel fretboardModel = new FretboardModel(Instrument.TELE, chord);

      List<ChordFingering> currentSetOfChords = new ArrayList<>();
      List<ChordFingering> normalChords = getNormalChords(fretboardModel);
      List<ChordFingering> twoNoteChords = getTwoNoteChords(fretboardModel, chordType);
      currentSetOfChords.addAll(normalChords);
      currentSetOfChords.addAll(twoNoteChords);

      Collections.sort(currentSetOfChords, new CustomComparator());

      String name = noteName.toString();
      if (modifier == 1) {
         name += "#";
      }

      String chordName = name + chordType.name;

      if (chordType.name.equals("fifth")) {
         chordName = IntervalChordName.getFifthIntervalChordName(noteName, modifier);
      }

      if (chordType.name.equals("sixth")) {
         chordName = IntervalChordName.getSixthIntervalChordName(noteName, modifier);
      }

      if (chordType.name.equals("minorseventh")) {
         chordName = IntervalChordName.getMinorSeventhIntervalChordName(noteName, modifier);
      }

      for (int i = 0; i < currentSetOfChords.size(); i++) {
         //System.out.println("[\"" + chordName + "\"," + (i + 1) + "," + currentSetOfChords.get(i) + "],");
         stringBuilder.append("[\"" + chordName + "\"," + (i + 1) + "," + currentSetOfChords.get(i) + "],");
         stringBuilder.append("\n");
      }
   }

   private static List<ChordFingering> getNormalChords(FretboardModel fretboardModel) {

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

   private static List<ChordFingering> getTwoNoteChords(FretboardModel fretboardModel, ChordType chordType) {

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
                     thereAreNotMoreThanTwoUnplayedStringsBetweenNotes(chordFingering.absoluteFrets)) {

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
}
