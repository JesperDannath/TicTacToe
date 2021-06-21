package tictactoe.KI.KiBerechnerServices;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import tictactoe.KI.Fachwerte.KiModus;
import tictactoe.KI.Materialien.datafile;
import tictactoe.fachwerte.Feldwert;
import tictactoe.materialien.Spielfeld;

/**
 * Ein Zugberechner der eine einfache OLS Reression zur Vorhersage des besten Zuges nutzt. 
 * @author Jesper Dannath
 */
public class OLSzugberechner extends abstractZugberechner// implements ZugberechnerService
{
	private OLSMultipleLinearRegression regression;

	private double[] _koeffizienten;

	public OLSzugberechner(Feldwert feldwert)
	{
		regression = new OLSMultipleLinearRegression();
		setUpModel(KiModus.OLS);
		druckeKoeffizienten();
		_spieler = feldwert;
	}
	
	/**
	 * Dieser Konstruktor wird nur genutz, um die Klasse zu testen.
	 * @param data
	 */
	public OLSzugberechner(datafile data)
	{
		regression = new OLSMultipleLinearRegression();
		_datafile = data;
	}

	@Override
	public int berechneZug(Spielfeld spielstand) 
	{
		int[] spielbareFelder = gibUnbesetzteFelder(spielstand);
		double[] vorhersagen = new double[spielbareFelder.length];
		
		for(int i = 0; i < vorhersagen.length; i++)
		{
			int aktuellesFeld = spielbareFelder[i];
			
			vorhersagen[i] = erstelleVorhersage(spielstand, aktuellesFeld);
		}	
		
		return spielbareFelder[getMax(vorhersagen)];
	}
	

	/**
	 * Erstellt die Vorhersage eines potentiellen Zuges (gibt die Siegwahrscheinlichkeit zurück).
	 * @param spielstand aktueller Spielstand
	 * @param aktuellesFeld das potentielle Feld
	 * @return Siegwahrscheinlichkeit
	 */
	private double erstelleVorhersage(Spielfeld spielstand, int aktuellesFeld) 
	{
		double[] zugArray = spielstand.gibZugarray(_spieler);
		double[] vorhersagenarray = erstelleDummySpielstand(zugArray);
		vorhersagenarray[aktuellesFeld] = 1.0;
		
		double wahrscheinlichkeit = _koeffizienten[0];
		
		assert _koeffizienten.length == (vorhersagenarray.length+1) : "Vorbedingung verletzt: ungleiche Koeffizienten";
		
		for(int i = 1; i < _koeffizienten.length; i++)
		{
			wahrscheinlichkeit += _koeffizienten[i]*vorhersagenarray[i-1];
		}
		
		return wahrscheinlichkeit;
	}

	/**
	 * In diesem Fall wird eine MLR aus den bestehenden Daten berechnet.
	 * Anschließend werden die Koeffizienten gespeichert und weiter verwendet. 
	 */
	@Override
	public void setUpModel(KiModus modus) 
	{
		//int[] indep = {0,1,2,3,4,5,6,7,8};
		
		double[] y = _datafile.gibVariable(9);
		double[][] x = gibDummySpieldaten(true);
		druckeX(x);
		//double[][] x = _datafile.gibVariablen(indep, true);
		
		regression = new OLSMultipleLinearRegression();	
		regression.newSampleData(y, x);
		
		_koeffizienten = regression.estimateRegressionParameters();
	}
	
	public void druckeKoeffizienten()
	{
	
		//Es muss sichergestellt werden, dass es sich am Ende auch wirklich um die selben Felder handelt!
		for(double d : _koeffizienten)
		{
			System.out.println(d);
		}
	}
	
	public void druckeX(double[][] x)
	{
	
		//Es muss sichergestellt werden, dass es sich am Ende auch wirklich um die selben Felder handelt!
		for(double[] array : x)
		{
			for(double d : array)
			{
				System.out.print(d + ",");
			}
			System.out.print("\n");
		}
	}
	
	public OLSMultipleLinearRegression gibRegression()
	{
		return regression;
	}

	@Override
	public KiModus gibModus() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}

