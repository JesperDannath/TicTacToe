package tictactoe.KI.Materialien;

import java.util.ArrayList;
import java.util.List;

import tictactoe.KI.FileReader;

/**
 * Das Datafile stellt die Daten vergangener Spiele in auswertbarer Form für die KI bereit. 
 * Dazu wird ein FileReader benuztzt und die resultierenden Arraylisten in Arraylisten vom Typ Double/int
 * umgewandelt. 
 * @author Jesper Dannath
 */
public class datafile 
{
	private List<ArrayList<String>> _stringdaten;
	private double[][] _numdaten;

	public datafile(String path) 
	{
		FileReader filereader = new FileReader(path);
	    _stringdaten = filereader.gibDaten();
	    _numdaten = setUpNumdaten();
	}
	
	/**
	 * Initialisiert _numdaten in Form eines verschachtelten double Arrays.
	 * Die Arrays sing Variablenweise aufgelistet
	 * @return double[][] mit den Daten
	 */
	private double[][] setUpNumdaten() 
	{
		double[][] result = new double[_stringdaten.get(1).size()][];
		
		for(int i = 0; i < _stringdaten.get(1).size(); i++)
		{
			result[i] = konvertiereVariable(i);  
		}
		
		return result;
	}

	/**
	 * Gibt eine Datenreihe des datafiles zurück, die einer statistischen Einheit entspricht
	 * @param fall index des Falles
	 * @return die zum Fall gehörenden Daten. 
	 */
	//TODO auf Numdaten umschreiben, sodass Änderungen berücksichtigt werden. Und vorsicht bei gibDummySpieldaten, der braucht das!
	public double[] gibFall(int fall)
	{
		double[] result = null;
		List<String> stringliste = _stringdaten.get(fall);
		result = new double[stringliste.size()];		
		
		for(int i = 0; i < stringliste.size(); i++)
		{
			//wandelt den String in Typ double um
			result[i] = Double.parseDouble(stringliste.get(i));
		}
		
		return result;
	}
	
	/**
	 * Gibt eine Spalte des datafiles zurück, die einer Variable entspricht
	 * @param spalte index des Falles
	 * @return die Variable. 
	 */
	private double[] konvertiereVariable(int spalte)
	{
		double[] result = null;
		result = new double[_stringdaten.size()];
		
		for(int i = 0; i < _stringdaten.size(); i++)
		{
			//wandelt den String in Typ double um
			result[i] = Double.parseDouble(_stringdaten.get(i).get(spalte));
		}
		
		return result;
	}
	
	/**
	 * Gibt eine Spalte des datafiles zurück, die einer Variable entspricht
	 * @param spalte index des Falles
	 * @return die Variable. 
	 */
	public double[] gibVariable(int spalte)
	{
		return _numdaten[spalte];
	}
	
	/**
	 * Gibt eine Spalte des datafiles zurück, die einer Variable entspricht.
	 * Mit Möglichkeit zur Transponierung der Matrix
	 * @param spalte index des Falles
	 * @return die Variable. 
	 */
	public double[][] gibVariablen(int[] spalten, boolean transpose)
	{
		double[][] result = new double[spalten.length][];
		
		for(int i : spalten)
		{
			result[i] = gibVariable(i);
		}
		
		if(transpose == true)
		{
			result = transposeMatrix(result);
		}
		
		return result;
	}
	
	/**
	 * Gibt eine Spalte des datafiles zurück, die einer Variable entspricht
	 * @param spalte index des Falles
	 * @return die Variable. 
	 */
	public double[][] gibVariablen(int[] spalten)
	{
		double[][] result = new double[spalten.length][];
		
		for(int i : spalten)
		{
			result[i] = gibVariable(i);
		}
		
		return result;
	}
	
	/**
	 * Gibt anderen Objekten die Numdaten für weitere Berechnungen zurück.
	 * @return die Daten in numerischer Form.
	 */
	public double[][] gibNumdaten()
	{
		return _numdaten;
	}
	
	/**
	 * Hilfsfunktion zum trnasponieren einer Matrix
	 * @param matrix
	 * @return die transponierte Matrix
	 */
	public static double[][] transposeMatrix(double[][] matrix)
	{
	    int m = matrix.length;
	    int n = matrix[0].length;

	    double[][] transposedMatrix = new double[n][m];

	    for(int x = 0; x < n; x++)
	    {
	        for(int y = 0; y < m; y++)
	        {
	            transposedMatrix[x][y] = matrix[y][x];
	        }
	    }

	    return transposedMatrix;
	}
	
	/**
	 * Recodiert die Kategorie einer bestimmten Variable als Dummy.
	 * @param varindex
	 * @param wert
	 * @return einen Dummy der ausgewählten Kategorie der ausgewählten Variable
	 */
	public double[] gibDummy(int varindex, double wert)
	{
		double[] variable = gibVariable(varindex).clone();
		
		for(int i = 0; i < variable.length; i++)
		{
			if(variable[i] == wert)
			{
				variable[i] = 1;
			}
			else
			{
				variable[i] = 0;
			}
		}
		
		return variable;
	}
	
	/**
	 * Ersetzt eine Variable im num[][] mit dem übergebenen Parameter
	 */
	public void ersetzeVariable(double[] variable, int spalte)
	{
		_numdaten[spalte] = variable;
	}
	
	/**
	 * Fügt dem Datensatz die übergebenen Reihen hinzu.
	 * @param appendData die hinzuzufügenen Daten. 
	 */
	public void appendData(datafile appendData) 
	{
		int laenge = gibVariable(0).length;
		int appendlaenge = appendData.gibVariable(0).length;
		int faelle = gibFall(0).length;
		
		double[][] result = new double[faelle][laenge+appendlaenge];
		
		for(int i = 0; i<laenge+appendlaenge; i++)
		{
			if(i < laenge)
			{
				for(int j = 0; j < faelle; j++)
				{
					result[j][i] = gibVariable(j)[i];
				}
			}
			else
			{
				for(int j = 0; j < faelle; j++)
				{
					result[j][i] = appendData.gibVariable(j)[i-laenge];
				}
			}
		}
		
		_numdaten = result;
	}

	/**
	 * Hilfsmethode, zählt die Häufigkeit des Vorkommens einer bestimmten Ausprägung auf einer Variablen. 
	 * @param index Index der Variablen
	 * @param wert zu zählender Wert.
	 * @return
	 */
	public int count(int index, int wert) 
	{
		double[] variable = gibVariable(index);
		int result = 0;
		
		for(int i = 0; i < variable.length; i++)
		{
			if(variable[i] == wert)
			{
				result++;
			}
		}
		return result;
	}
}

