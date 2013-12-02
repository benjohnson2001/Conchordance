package chordengine.fingers.validation;

import chordengine.fingers.ChordFingering;
import chordengine.music.Chord;
import chordengine.music.IntervalicNote;
import chordengine.music.Note;

/**
 * Acts as a filter for potential chord fingerings, compared to the notes of the target chord.
 * Accepts only chords satisfying the [arbitrary] bare minimum conditions, of having at least four notes, and having each of the notes in the target chord at least once.
 *
 */
public class BaseValidator implements ChordFingeringValidator {
	public boolean validate(ChordFingering candidate, Chord compareTo) {
		if (candidate.numNotes <= 3)
			return false;
		
		for (Note i : compareTo.notes) {
			boolean found = false;
			for (IntervalicNote j : candidate.sortedNotes)
				found |= i.samePitchClass(j.note);
			if (!found)
				return false;
		}
		return true;
	}
}
