package com.conchordance.fretted;

import com.conchordance.music.Clef;
import com.conchordance.music.Note;
import static com.conchordance.music.NoteName.*;

/**
 * Models the string configuration of a fretted instrument.
 *
 */
public class Instrument {

	/**
	 * Guitar in standard tuning.
	 */
	public static final Instrument GUITAR = new Instrument("Guitar", 17, Clef.TREBLE,
			new Note[] {new Note(E, 0, 5), new Note(B, 0, 4), new Note(G, 0, 4), new Note(D, 0, 4), new Note(A, 0, 3), new Note(E, 0, 3)});


	public static final Instrument SHORTGUITAR = new Instrument("Guitar", 16, Clef.TREBLE,
			new Note[] { new Note(E, 0, 3),  new Note(A, 0, 3), new Note(D, 0, 4), new Note(G, 0, 4), new Note(B, 0, 4), new Note(E, 0, 5) });


	public static final Instrument TELE = new Instrument("Guitar", 18, Clef.TREBLE,
			new Note[] { new Note(E, 0, 3),  new Note(A, 0, 3), new Note(D, 0, 4), new Note(G, 0, 4), new Note(B, 0, 4), new Note(E, 0, 5) });


	public static final Instrument STRAT = new Instrument("Guitar", 20, Clef.TREBLE,
			new Note[] { new Note(E, 0, 3),  new Note(A, 0, 3), new Note(D, 0, 4), new Note(G, 0, 4), new Note(B, 0, 4), new Note(E, 0, 5) });
	/**
     * 5-String banjo (GCGBD)
     *
     */
    public static final Instrument BANJO = new Instrument("5-String Banjo", 12, Clef.TREBLE,
            new Note[] {new Note(D, 0, 5), new Note(B, 0, 4), new Note(G, 0, 4), new Note(C, 0, 4), new Note(G, 0, 5)},
            new int[] {0, 0, 0, 0, 5});
	
	/**
	 * The name of the instrument
	 */
	public final String name;
	
	/**
	 * The tuning of the instrument, from lowest-numbered string to highest-numbered string.
	 * The tuning is considered to be the note conceptually at the 0th fret. If a string is tuned to a G at the fifth fret (as in the five-string banjo) that string conceptually tunes to a D at the 0th fret.
	 */
	public final Note[] tuning;

    /**
     * The clef that this instrument's sheet music should be rendered in.
     *
     * Note that the note values shouldn't change because of this. All notes should be stored as if they are in the treble clef,
     * it is up to a UI to convert them to bass if necessary
     */
    public final String clef;

	/**
	 * The number of frets up the neck where each string begins.
	 * For most instruments, every string extends the full extent of the neck, and so the nut is at 0. The fifth string of a five-string banjo starts at the fifth fret, and so should have the value 5 in this array.
	 * The tuning of an instrument should compensate for any non-zero fret nut position. For example if a string is tuned to G and has its nut at the fifth fret, the tuning should compensate that string to a D. This ensures that the instrument has a G on the fifth fret for that string.
	 */
	public final int[] fretNutPositions;
	
	/**
	 * The number of frets to depict for the instrument
	 */
	public final int frets;
	
	/**
	 * The number of strings the instrument has
	 */
	public final int strings;
	
	/**
	 * The name of the instrument
	 */
	public String toString() {
		return name;
	}

	public Instrument(String name, int frets, Clef clef, Note[] tuning, int[] fretNutPositions) {
		this.name = name;
		this.tuning = tuning;
		this.fretNutPositions = fretNutPositions;
		this.frets = frets;
        this.clef = clef.name().toLowerCase();
		strings = tuning.length;
	}

	public Instrument(String name, int frets, Clef clef, Note... tuning) {
		this(name, frets, clef, tuning, new int[tuning.length]);
	}
}
