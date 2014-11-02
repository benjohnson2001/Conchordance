package com.conchordance.fretted.fingering.list;

import java.util.Comparator;

import com.conchordance.fretted.fingering.ChordFingering;
import com.conchordance.music.IntervalicNote;


/**
 * Interface for objects which compare two ChordFingerings and return an integer value.
 *
 */
public interface ChordFingeringComparator extends Comparator<ChordFingering> {
	/**
	 * Instances of comparators under this interface.
	 */
	public static final ChordFingeringComparator[] instances = {
		new MusicallyDecreasingComparator(),
		new MusicallyIncreasingComparator(),
		new InversionComparator(),
	};
	
	/**
	 * Compare two ChordFingerings and give an integer result.
	 * @param a the first ChordFingering
	 * @param b the second ChordFingering
	 * @return 0 if a is equal to b, a negative value if a is less than b, and a positive value otherwise 
	 */
	public int compare(ChordFingering a, ChordFingering b);
	
	/**
	 * Compares two ChordFingerings by comparing their notes, and returning their ordering relative to a musically increasing order.
	 *
	 */
	public class MusicallyIncreasingComparator implements ChordFingeringComparator {
		public String toString() {
			return "Pitch Increasing";
		}

		public int compare(ChordFingering a, ChordFingering b) {
			
			int i = a.sortedNotes.length-1;
			int j = b.sortedNotes.length-1;

			while(true) {
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

	/**
	 * Compares two ChordFingerings by comparing their notes, and returning their ordering relative to a musically decreasing order.
	 *
	 */
	public class MusicallyDecreasingComparator implements ChordFingeringComparator {
		public String toString() {
			return "Pitch Decreasing";
		}
		
		public int compare(ChordFingering a, ChordFingering b) {
			IntervalicNote[] aNotes = a.sortedNotes;
			IntervalicNote[] bNotes = b.sortedNotes;
			
			int i = aNotes.length-1;
			int j = bNotes.length-1;
			
			while(true) {
				if (i == -1 && j == -1)
					return 0;
				if (i == -1)
					return -1;
				if (j == -1)
					return 1;
				int comp = new Integer(aNotes[i].note.halfSteps).compareTo(bNotes[j].note.halfSteps);
				if (comp != 0)
					return -comp;
				
				--i;
				--j;
			}
		}
	}
	
	public class InversionComparator implements ChordFingeringComparator {
		public String toString() {
			return "Inversion";
		}

		public int compare(ChordFingering a, ChordFingering b) {
			return new Integer(a.inversion).compareTo(b.inversion);
		}
	}
}