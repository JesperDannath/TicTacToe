package tictactoe.KI.KiBerechnerServices;

import tictactoe.KI.Fachwerte.KiModus;
import tictactoe.KI.Materialien.dataManipulator;
import tictactoe.KI.Materialien.datafile;
import tictactoe.fachwerte.Feldwert;
import tictactoe.materialien.Spielfeld;

/**
 * Ein Abstact Zugberechner stellt unterschiedlichen Zugberechnern Methoden und Felder zur
 * Verfügung, die diese brauchen, um mit ihren je eigenen Methoden KI-Züge zu berechnen. 
 * @author Jesper Dannath
 *
 */
public abstract class abstractZugberechner 
{
	protected datafile _datafile;
	protected Feldwert _spieler;
	protected dataManipulator _manipulator;

	public abstractZugberechner()
	{
		_datafile = new datafile("spieldaten.csv");
		_manipulator = new dataManipulator(_datafile);
		_manipulator.erzeugeUmkehrdaten();
		druckeDaten();
	}
	
	private void druckeDaten() 
	{
		double[][] daten = trasposeMatrix(_datafile.gibNumdaten());
		
		for(int i = 0; i<daten.length; i++)
		{
			for(double d : daten[i])
			{
				System.out.print(d + ",");
			}
			System.out.print("\n");
		}
	}

	//TODO Es muss verhindert werden das eine singular Matrix und das eine zu kleine Matrix dem OLS-Berechner übergeben werden! (Vertragsmodell?)
	public double[][] gibDummySpieldaten(boolean transpose)
	{
		int laenge = _datafile.gibFall(0).length-1;
		double[][] result = new double[laenge*2][];
		//double[][] result = new double[laenge][];
		
		for(int i = 0; i < laenge; i++)
		{
			result[i] = _datafile.gibDummy(i, 1);
			result[i+laenge] =  _datafile.gibDummy(i, 0);
		}
		
		if(transpose == true)
		{
			result = trasposeMatrix(result);
		}

		return result;		
	}
	
	/**
	 * Gibt die Spieldaten in standardisierter Form zurück. 0 = Leer, 1 = besetzt von Kreis, 
	 * -1 = besetzt von Kreuz
	 * @return Standadisierte Spieldaten.
	 */
	public double[][] gibStandardizedSpieldaten() 
	{
		int laenge = _datafile.gibFall(0).length-1;
		double[][] result = new double[laenge][];
		
		dataManipulator manipulator = new dataManipulator(_datafile);
		
		//Die Werte im Datensatz werden so recodiert, dass sie der Standardisierung entsprechen und
		//anschließend in den Spieldatensatz geladen
		for(int i = 0; i < laenge; i++)
		{
			manipulator.replace(i, i, 0, -1);
			manipulator.replace(i, i, 9, 0);
			result[i] = _datafile.gibVariable(i);
		}
		
		result = trasposeMatrix(result);
		return result;		
	}
	
	/**
	 * Gibt die unbesetzten Felder eines Spielfeldes zurück
	 * @param spielstand
	 * @return
	 */
	public int[] gibUnbesetzteFelder(Spielfeld spielstand)
	{
		int[] welcheFelder = new int[spielstand.gibFeldgroeße()];
		int anzahlSpielbareFelder = 0;
		
		for(int i = 0; i < welcheFelder.length; i++)
		{
			if(spielstand.gibWertVonFeld(i) == Feldwert.LEER)
			{
				welcheFelder[i] = 1;
				anzahlSpielbareFelder = anzahlSpielbareFelder+1;
			}
		}
		
		int[] result = new int[anzahlSpielbareFelder];
		
		for(int i = 0; i < anzahlSpielbareFelder; i++)
		{
			boolean geaendert = false;
			for(int j = 0; j < welcheFelder.length; j++)
			{
				if(welcheFelder[j] == 1 && geaendert == false)
				{
					result[i] = j;
					welcheFelder[j] = 0;
					geaendert = true;
				}
			}
		}
			
		return result;
	}
	
	/**
	 * Erstellt aus einem Spielfeld einen Spielstand als Array im Format der Dummydaten
	 * @param zugArray
	 * @return einen Array mit dem aktuellen Spielstand.
	 */
	protected double[] erstelleDummySpielstand(double[] zugArray) 
	{
		double[] result = new double[zugArray.length*2];
		
		for(int i = 0; i < zugArray.length; i++)
		{
			if(zugArray[i] == 1)
			{
				result[i] = 1;
				result[i + zugArray.length] = 0;
			}
			else if(zugArray[i] == 0)
			{
				result[i] = 0;
				result[i + zugArray.length] = 1;
			}
			else
			{
				result[i] = 0;
				result[i + zugArray.length] = 0;
			}
		}
		
		return result;
	}
	
	/**
	 * Erstellt einen Standardisierten Spielstand, sodass der Spielstand zu den standardisierten
	 * Daten passt ind Vorhersagen getroffen werden können.
	 * @param zugArray Ein Array mit dem Spielstand im Ursprungsformat.
	 * @return Einen Array im Zielformat.
	 */
	public double[] erstelleStandardizedSpielstand(double[] zugArray) 
	{
		double[] result = new double[zugArray.length];
		
		for(int i = 0; i < zugArray.length; i++)
		{
			if(zugArray[i] == 1)
			{
				result[i] = 1;
			}
			else if(zugArray[i] == 0)
			{
				result[i] = -1;
			}
			else
			{
				result[i] = 0;
			}
		}
		
		return result;
	}
	
	/**
	 * Hilfsmethode: Gibt den Index des Maximums aus einem Array wieder. 
	 * @param inputArray
	 * @return
	 */
	public static int getMax(double[] inputArray)
	{ 
	    double maxValue = inputArray[0]; 
	    int maxIndex = 0;
	    
	    for(int i=1;i < inputArray.length;i++)
	    { 
	      if(inputArray[i] > maxValue)
	      { 
	         maxValue = inputArray[i]; 
	         maxIndex = i;
	      } 
	    } 
	    return maxIndex; 
	  }
	
	/**
	 * Hilfsmethode zum transponieren einer Matrix.
	 * @param matrix die zu transopopnierende Matrix
	 * @return die transponierte Matrix
	 */
	public static double[][] trasposeMatrix(double[][] matrix)
	{
	    int m = matrix.length;
	    int n = matrix[0].length;

	    double[][] trasposedMatrix = new double[n][m];

	    for(int x = 0; x < n; x++)
	    {
	        for(int y = 0; y < m; y++)
	        {
	            trasposedMatrix[x][y] = matrix[y][x];
	        }
	    }

	    return trasposedMatrix;
	}
	
	public void refreshData()
	{
		_datafile = new datafile("spieldaten.csv");
		_manipulator = new dataManipulator(_datafile);
		_manipulator.erzeugeUmkehrdaten();
		druckeDaten();
	}
	
	/**
	 * Auf Basis eines geschätzten Modells soll ein Zug entsprechend der Größten Gewinn-
	 * wahrscheinlichkeit für die KI durchgeführt werden. 
	 * @param spielstand ein Spielstand der informationen über die bisher besetzten Felder in Form eines
	 * Spielfeldes enthällt
	 * @return Einen Int-Wert bestimmt welches Feld als nächstes von der KI besetzt wird. 
	 */
	public abstract int berechneZug(Spielfeld spielstand);
	
	/**
	 * Auf Basis der Spieldaten(spieldaten.csv) soll ein Modell geschätzt werden, dass die
	 * Gewinnwahrscheinlichkeit bestimmter Züge voraussagen kann. 
	 */
	public abstract void setUpModel(KiModus modus);

	public abstract KiModus gibModus();
	
	}
