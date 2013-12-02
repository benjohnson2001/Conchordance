package chordengine.fingers.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.AbstractListModel;

import chordengine.fingers.ChordFingering;
import chordengine.fingers.validation.ChordFingeringValidator;
import chordengine.fingers.validation.TrivialValidator;
import chordengine.music.Chord;

/**
 * A list of ChordFingerings. Used as a central source for sorting and filtering ChordFingerings.
 *
 */
public class ChordListModel extends AbstractListModel<ChordFingering> {

	private static final long serialVersionUID = 1L;

	public int getSize() {
		return filtered.length;
	}

	public ChordFingering getElementAt(int index) {
		return filtered[index];
	}

	/**
	 * Locate a ChordFingering in the list.
	 * @param c The ChordFingering to find
	 * @return the ChordFingering's index into the list, or -1 if it is not found.
	 */
	public int find(ChordFingering c) {
		return Arrays.binarySearch(filtered, c, comparator);
	}

	/**
	 * Invoked to change the underlying list.
	 */
	public void setChords(ChordFingering[] chords) {
		this.chords = chords;
		sort();
		filter();
		fireContentsChanged(this, 0, filtered.length-1);
	}

	public ChordFingeringValidator getValidator() {
		return validator;
	}

	public void setValidator(ChordFingeringValidator validator) {
		this.validator = validator;
		filter();
		fireContentsChanged(this, 0, filtered.length-1);
	}

	public void setComparator(ChordFingeringComparator c) {
		comparator = c;
		sort();
		filter();
		fireContentsChanged(this, 0, filtered.length-1);
	}
	
	public void setFilterDuplicateShapes(boolean filter) {
		filterDuplicateShapes = filter;
		filter();
		fireContentsChanged(this, 0, filtered.length-1);
	}
	
	/**
	 * Sets the chord that will be used by the ChordFingeringValidators to filter ChordFingerings in the list.
	 */
	public void setFilteringChord(Chord c) {
		this.filteringChord = c;
	}

	/**
	 * Constructor.
	 * Initializes the chord list as empty and the comparator as the default.
	 */
	public ChordListModel() {
		comparator = new ChordFingeringComparator.MusicallyDecreasingComparator();
		chords = new ChordFingering[0];
		filtered = new ChordFingering[0];
		
		filterDuplicateShapes = true;
		
		validator = new TrivialValidator();
	}

	/**
	 * Sort the ChordFingerings contained in chords
	 */
	private void sort() {
		Arrays.sort(chords, comparator);
	}
	
	/**
	 * Passes each ChordFingering that satisfies the filter conditions from chords to filtered
	 */
	private void filter() {
		// Select ChordFingerings from chords to satisfy the fingering-uniqueness criterion
		ArrayList<ChordFingering> firstFiltered = new ArrayList<ChordFingering>();
		if (!filterDuplicateShapes) {
			for (ChordFingering c : chords)
				firstFiltered.add(c);
		} else {
			HashSet<String> patternsFound = new HashSet<>();
			for (ChordFingering chord : chords) {
				String hash = chord.chordShapeHash();
				if (!patternsFound.contains(hash)) {
					firstFiltered.add(chord);
					patternsFound.add(hash);
				}
			}
		}
		// Select ChordFingerings to satisfy the defined filter
		ArrayList<ChordFingering> secondFiltered = new ArrayList<ChordFingering>();
		for (ChordFingering c : firstFiltered) {
			if (validator.validate(c, filteringChord))
				secondFiltered.add(c);
		}
		filtered = secondFiltered.toArray(new ChordFingering[secondFiltered.size()]);
	}

	/**
	 * The validator used to filter chords
	 */
	private ChordFingeringValidator validator;
	
	/**
	 * The Chord used to compare candidate ChordFingerings in the list during filtering
	 */
	private Chord filteringChord;

	/**
	 * The ChordFingerings in the list, sorted but possibly with duplicate shapes
	 */
	private ChordFingering[] chords;
	
	/**
	 * The ChordFingerings in the list, sorted and filtered
	 */
	private ChordFingering[] filtered;
	
	/**
	 * Used for sorting chords in the list
	 */
	private ChordFingeringComparator comparator;

	/**
	 * Whether or not to filter out duplicate chord shapes in the list
	 */
	private boolean filterDuplicateShapes;
}
