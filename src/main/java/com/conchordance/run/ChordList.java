package com.conchordance.run;

import java.util.List;

public class ChordList {

   String chordName;

   List<int[]> chordVoicings;

   public ChordList(String chordName, List<int[]> chordVoicings) {
      this.chordName = chordName;
      this.chordVoicings = chordVoicings;
   }
}
