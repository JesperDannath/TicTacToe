package tictactoe.KI.Materialien;

public class dataManipulator 
{
	datafile _datafile;
	
	/**
	 * Ein dataManipulator dient dazu die Variablen oder Listen von Variablen eines Datafiles
	 * zu modifizieren. COnditionale und unconditionale Änderungen in den Daten können vorgenommen werden.
	 */
	public dataManipulator(datafile data)
	{	
		_datafile = data;
	}
	
	/**
	 * Ersetzt alle Ausprägungen einer Bestimmten Variable konditional auf die Ausprägungen
	 * einer anderen Variable mit einem bestimmten Wert. 
	 * @param zielvariable
	 * @param referenzvariable
	 * @param referenzwert
	 * @param neuerwert
	 */
	public void replace(int zielvariable, int referenzvariable, double referenzwert, double neuerwert)
	{
		double[] variable = _datafile.gibVariable(zielvariable);
		double[] referenz = _datafile.gibVariable(referenzvariable);
		
		for(int i = 0; i < variable.length; i++)
		{
			if(referenz[i] == referenzwert)
			{
				variable[i] = neuerwert;
			}
		}
		_datafile.ersetzeVariable(variable, zielvariable);
	}
	
	/**
	 * Gibt eine definierte Liste von Variablen als Dummy zurück, konditonal auf einen bestimmten Wert
	 * @param transpose
	 * @return
	 */
	public double[] gibDummy(int varindex, double wert)
	{
		return _datafile.gibDummy(varindex, wert);
	}
	

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
	 * Erzeugt einen Umkehrdatensatz zu den vorhendenen mit 0 und 1 codierten Spieldaten.
	 * Ruft appenData am ursprünglichen Datafile auf, um den Vorgang abzuschließen. 
	 */
	public void erzeugeUmkehrdaten() 
	{
		datafile datacopy = new datafile("spieldaten.csv");
		dataManipulator copymanipulator = new dataManipulator(datacopy);
		
		int laenge = datacopy.gibFall(0).length;
				
		for(int i = 0; i < laenge; i++)
		{
			//Die 7 ist ein Zwischenwert, damit es nicht zur Gleichcodierung aller Werte kommt. Reihenfolge wichtig!
			copymanipulator.replace(i, i, 0, 7);
			copymanipulator.replace(i, i, 1, 0);
			copymanipulator.replace(i, i, 7, 1);
		}
		
		_datafile.appendData(datacopy);
	}

}
