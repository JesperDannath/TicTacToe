package tictactoe.KI.KiBerechnerServices;

import static org.junit.Assert.assertTrue;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tictactoe.KI.Materialien.datafile;

class abstractZugberechnerTest 
{

	private datafile _datafile;
	private OLSzugberechner _zugberechner;
	private datafile _datafile2;

	@BeforeEach
	void setUp() throws Exception 
	{
		_datafile = new datafile("test.csv");
		_datafile2 = new datafile("test2.csv");
		_zugberechner = new OLSzugberechner(_datafile);
	}

	@Test
	void testGibDummySpieldaten() 
	{
		double[][] daten = _zugberechner.gibDummySpieldaten(false);
		
		assertTrue(daten[0][0] == 1);
		assertTrue(daten[1][0] == 0);
		assertTrue(daten[1][1] == 0);
	}
	
	@Test
	void testeRegression() 
	{
		_zugberechner = new OLSzugberechner(_datafile2);
		
		double[][] x = _zugberechner.gibDummySpieldaten(true);
		_zugberechner.druckeX(x);
		//double[] y = {1.0,2.0,1.0,1.0,1.0};
		double[] y = _datafile2.gibVariable(3);
		
		OLSMultipleLinearRegression regression = _zugberechner.gibRegression();
		
		regression.newSampleData(y, x);
		
		double[] _koeffizienten = regression.estimateRegressionParameters();
		
		assertTrue( _koeffizienten != null);
	}
	
	//OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
	//double[] y = new double[]{11.0, 12.0, 13.0, 14.0, 15.0, 16.0};
	//double[][] x = new double[6][];
	//x[0] = new double[]{0, 0, 0, 0, 0};
	//x[1] = new double[]{2.0, 0, 0, 0, 0};
	//x[2] = new double[]{0, 3.0, 0, 0, 0};
	//x[3] = new double[]{0, 0, 4.0, 0, 0};
	//x[4] = new double[]{0, 0, 0, 5.0, 0};
	//x[5] = new double[]{0, 0, 0, 0, 6.0};          
	//regression.newSampleData(y, x);
}
