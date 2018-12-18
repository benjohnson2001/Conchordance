package com.conchordance.music;


/**
 * A type of chord, as in Major, Minor, Diminished, etc.
 * 
 * These are represented as an ordered series of intervals.
 *
 */
public class ChordType {
	
	public static final ChordType OCTAVEINTERVAL = new ChordType("M13", new Interval[]{Interval.UNISON});
	public static final ChordType SECONDINTERVAL = new ChordType("sus2", new Interval[]{Interval.UNISON, Interval.SECOND});
	public static final ChordType MINORTHIRDINTERVAL = new ChordType("m", new Interval[]{Interval.UNISON, Interval.MINOR_THIRD});
	public static final ChordType MAJORTHIRDINTERVAL = new ChordType("M", new Interval[]{Interval.UNISON, Interval.MAJOR_THIRD});
	public static final ChordType FOURTHINTERVAL = new ChordType("M7sus4", new Interval[]{Interval.UNISON, Interval.FOURTH});
	public static final ChordType FLATFIFTHINTERVAL = new ChordType("5-", new Interval[]{Interval.UNISON, Interval.DIMINISHED_FIFTH});
	public static final ChordType FIFTHINTERVAL = new ChordType("fifth", new Interval[]{Interval.UNISON, Interval.PERFECT_FIFTH});
	public static final ChordType SIXTHINTERVAL = new ChordType("sixth", new Interval[]{Interval.UNISON, Interval.SIXTH});
	public static final ChordType MINORSEVENTHINTERVAL = new ChordType("minorseventh", new Interval[]{Interval.UNISON, Interval.MINOR_SEVENTH});
	public static final ChordType MAJORSEVENTHINTERVAL = new ChordType("mM7", new Interval[]{Interval.UNISON, Interval.MAJOR_SEVENTH});



	public static final ChordType MAJOR = new ChordType("M", new Interval[]{Interval.UNISON, Interval.MAJOR_THIRD, Interval.PERFECT_FIFTH});
	public static final ChordType MINOR = new ChordType("m", new Interval[]{Interval.UNISON, Interval.MINOR_THIRD, Interval.PERFECT_FIFTH});
	public static final ChordType POWER = new ChordType("5", new Interval[]{Interval.UNISON, Interval.PERFECT_FIFTH});
	public static final ChordType SUS2 = new ChordType("sus2", new Interval[]{Interval.UNISON, Interval.SECOND, Interval.PERFECT_FIFTH});
	public static final ChordType SUS4 = new ChordType("sus4", new Interval[]{Interval.UNISON, Interval.FOURTH, Interval.PERFECT_FIFTH});
	public static final ChordType DIM = new ChordType("dim", new Interval[]{Interval.UNISON, Interval.MINOR_THIRD, Interval.DIMINISHED_FIFTH});
	public static final ChordType AUG = new ChordType("aug", new Interval[]{Interval.UNISON, Interval.MAJOR_THIRD, Interval.AUGMENTED_FIFTH});
	public static final ChordType MAJORSIXTH = new ChordType("6", new Interval[]{Interval.UNISON, Interval.MAJOR_THIRD, Interval.PERFECT_FIFTH, Interval.SIXTH});
	public static final ChordType MINORSIXTH = new ChordType("m6", new Interval[]{Interval.UNISON, Interval.MINOR_THIRD, Interval.PERFECT_FIFTH, Interval.SIXTH});
	public static final ChordType DOMINANTSEVENTH = new ChordType("7", new Interval[]{Interval.UNISON, Interval.MAJOR_THIRD, Interval.PERFECT_FIFTH, Interval.MINOR_SEVENTH});
	public static final ChordType MAJORSEVENTH = new ChordType("M7", new Interval[]{Interval.UNISON, Interval.MAJOR_THIRD, Interval.PERFECT_FIFTH, Interval.MAJOR_SEVENTH});
	public static final ChordType MINORSEVENTH = new ChordType("m7", new Interval[]{Interval.UNISON, Interval.MINOR_THIRD, Interval.PERFECT_FIFTH, Interval.MINOR_SEVENTH});

	/**
	 * The name of the chord type (i.e. maj, min, etc)
	 */
	public final String name;
	
	/**
	 * The intervals that comprise this chord type, relative to the root
	 */
	public final Interval[] intervals;
	
	public String toString() {
		return name;
	}

	public ChordType(String name, Interval[] intervals) {
		this.name = name;
		this.intervals= intervals;
	}
}
