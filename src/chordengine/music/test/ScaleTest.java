package chordengine.music.test;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;

import org.junit.Test;

import chordengine.music.Interval;
import chordengine.music.MusicException;
import chordengine.music.Note;
import chordengine.music.Scale;

public class ScaleTest {

	@Test
	public void testGetMajorScale() {
		try {
			String cScaleString = Scale.getMajorScale(new Note('C', 0)).toString();
			assertEquals("C Scale", "C, D, E, F, G, A, B", cScaleString);
	
			String bScaleString = Scale.getMajorScale(new Note('B', 0)).toString();
			String bScaleManual = MessageFormat.format("B, C{0}, D{0}, E, F{0}, G{0}, A{0}", Note.SHARP);
			assertEquals("B Scale", bScaleManual, bScaleString);
		} catch (MusicException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testApplyIntervals() {
		try {
			Scale cScale = Scale.getMajorScale(new Note('C', 0));
			Note[] cMajor = cScale.applyIntervals(Interval.UNISON, Interval.MAJOR_THIRD, Interval.PERFECT_FIFTH);
			assertEquals("Major chord intervals applied to C scale", new Note('C', 0), cMajor[0]);
			assertEquals("Major chord intervals applied to C scale", new Note('E', 0), cMajor[1]);
			assertEquals("Major chord intervals applied to C scale", new Note('G', 0), cMajor[2]);
		} catch (MusicException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testApplyInterval() {
		try {
			Scale cScale = Scale.getMajorScale(new Note('C', 0));
	
			Note cFifth = cScale.applyInterval(Interval.PERFECT_FIFTH);
			assertEquals("Fifth degree of C major scale is G", new Note('G', 0), cFifth);
			
			Note cFlatFlatSeven = cScale.applyInterval(new Interval(6, -2));
			assertEquals("Double-flat-Seventh degree of C major scale is Bbb", new Note('B', -2), cFlatFlatSeven);
		} catch (MusicException e) {
			e.printStackTrace();
		}
	}
}
