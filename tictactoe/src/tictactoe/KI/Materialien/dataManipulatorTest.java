package tictactoe.KI.Materialien;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class dataManipulatorTest 
{

	private datafile _datafile;
	private dataManipulator _manipulator;

	@BeforeEach
	void setUp() throws Exception 
	{
		_datafile = new datafile("test.csv"); 
		_manipulator = new dataManipulator(_datafile); 
	}

	@Test
	void testeReplace()
	{
		double[] variable0 = {1,4,7,10};

		assertTrue(_datafile.gibVariable(0)[0] == variable0[0]);
		assertTrue(_datafile.gibVariable(0)[1] == variable0[1]);
		assertTrue(_datafile.gibVariable(0)[2] == variable0[2]);
		assertTrue(_datafile.gibVariable(0)[3] == variable0[3]);
		
		_manipulator.replace(0, 0, 1, 9);
		
		assertTrue(_datafile.gibVariable(0)[0] == 9);
		assertTrue(_datafile.gibVariable(0)[1] == variable0[1]);
		assertTrue(_datafile.gibVariable(0)[2] == variable0[2]);
		assertTrue(_datafile.gibVariable(0)[3] == variable0[3]);
		
		_manipulator.replace(0, 0, 4, 9);
		_manipulator.replace(0, 0, 7, 9);
		_manipulator.replace(0, 0, 10, 9);
		
		assertTrue(_datafile.gibVariable(0)[0] == 9);
		assertTrue(_datafile.gibVariable(0)[1] == 9);
		assertTrue(_datafile.gibVariable(0)[2] == 9);
		assertTrue(_datafile.gibVariable(0)[3] == 9);
	}


}
