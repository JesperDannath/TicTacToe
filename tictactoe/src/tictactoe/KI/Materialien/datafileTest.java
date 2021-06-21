package tictactoe.KI.Materialien;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testet die Klasse Datafile mit Hilfe von Testdaten, die vier Zeilen und drei Spalten enthalten. Die enthaltenen
 * Zahlen sind fortlaufend von 1 bis 12 numeriert. 
 * @author Jesper Dannath
 *
 */
class datafileTest {

	private datafile _datafile;

	@BeforeEach
	void setUp() throws Exception 
	{
		_datafile = new datafile("test.csv");
	}


	@Test
	void testDatenHabenRichtigeDimensionen() 
	{
		assertEquals(_datafile.gibVariable(1).length, 4);
	
		assertEquals(_datafile.gibFall(1).length, 3);
	}
	
	@Test
	void testDatenHabenRichtigeInhalt() 
	{
		double[] variable0 = {1,4,7,10};
		assertTrue(true);
		assertTrue(_datafile.gibVariable(0)[0] == variable0[0]);
		assertTrue(_datafile.gibVariable(0)[1] == variable0[1]);
		assertTrue(_datafile.gibVariable(0)[2] == variable0[2]);
		assertTrue(_datafile.gibVariable(0)[3] == variable0[3]);
		
		double[] fall0 = {1,2,3};
		assertTrue(_datafile.gibFall(0)[0] == fall0[0]);
		assertTrue(_datafile.gibFall(0)[1] == fall0[1]);
		assertTrue(_datafile.gibFall(0)[2] == fall0[2]);
	}
	
	@Test 
	void mehrereVariablenWerdenKorrektIndiziert()
	{
		int[] indices = {0,1};
		double[][] variablen = _datafile.gibVariablen(indices);
		
		assertTrue(variablen.length == 2);
		assertTrue(variablen[0].length == 4);
		
		double[][] variablenTrans = _datafile.gibVariablen(indices, true);
		assertTrue(variablenTrans.length == 4);
		assertTrue(variablenTrans[0].length == 2);
	}
	
	@Test
	void testeGibDummy()
	{
		double[] variable0 = {1,4,7,10};

		assertTrue(_datafile.gibVariable(0)[0] == variable0[0]);
		assertTrue(_datafile.gibVariable(0)[1] == variable0[1]);
		assertTrue(_datafile.gibVariable(0)[2] == variable0[2]);
		assertTrue(_datafile.gibVariable(0)[3] == variable0[3]);
		
		_datafile.gibDummy(0, 1);
		
		assertTrue(_datafile.gibVariable(0)[0] == 1);
		assertTrue(_datafile.gibVariable(0)[1] == 0);
		assertTrue(_datafile.gibVariable(0)[2] == 0);
		assertTrue(_datafile.gibVariable(0)[3] == 0);
	
		assertTrue(_datafile.gibVariable(0).length == 4);
	}
}
