package com.conchordance.run;

import com.conchordance.fretted.fingering.ChordFingering;
import com.conchordance.fretted.fingering.list.ChordFingeringComparator;

public class CustomComparator implements ChordFingeringComparator {

   public String toString() {
      return "Pitch Increasing";
   }

   public int compare(ChordFingering a, ChordFingering b) {

      if (a.position < b.position)
         return -1;
      else if (a.position > b.position)
         return 1;

      int i = a.sortedNotes.length - 1;
      int j = b.sortedNotes.length - 1;

      while (true) {
         if (i == 0 && j == 0)
            return 0;
         if (i == 0)
            return -1;
         if (j == 0)
            return 1;
         int comp = new Integer(a.sortedNotes[i].note.halfSteps).compareTo(b.sortedNotes[j].note.halfSteps);
         if (comp != 0)
            return comp;

         --i;
         --j;
      }
   }
}
