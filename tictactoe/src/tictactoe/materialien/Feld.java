package tictactoe.materialien;
import tictactoe.fachwerte.Feldwert;

/**
 * Ein einzelnes Spielfeld
 * @author Jesper Dannath
 *
 */
class Feld 
{
	private Feldwert _feldwert;
	
	public Feld()
	{
		_feldwert = Feldwert.LEER;
	}

	/**
	 * Aendert den Wert eines einzelnen Feldes
	 * @param wert neuer Feldwert.
	 */
	public void aendereWert(Feldwert wert)
	{
		_feldwert = wert;
	}
	
	public Feldwert gibWert()
	{
		return _feldwert;
	}
}
