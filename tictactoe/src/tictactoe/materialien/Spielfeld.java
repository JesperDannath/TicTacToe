package tictactoe.materialien;

import tictactoe.fachwerte.Feldwert;

/**
 * EinSpielfed verknüpft verschiedene Felder zu einem Spielfeld mit Abnessungen 3*3
 * und führt Operationen auf diesen Feldern aus. 
 * @author Jesper Dannath
 */
public class Spielfeld 
{
	Feld[] _feldarray;
	
	public Spielfeld()
	{
		_feldarray = new Feld[9];
		
		for(int i = 0; i < 9; i++)
		{
			_feldarray[i] = new Feld();
		}
	}
	
	/**
	 * Gibt den aktuellen Wert eines bestimmten Feldes zurück. 
	 * @param index Das zu evaluierende Feld
	 * @return der aktuelle Feldwert dieses Feldes
	 */
	public Feldwert gibWertVonFeld(int index)
	{
		return _feldarray[index].gibWert();
	}

	/**
	 * Ändert einen bestimmten Wert des Spielfeldes. 
	 * @param index
	 * @param wert
	 */
	public void aendereFeld(int index, Feldwert wert)
	{
		_feldarray[index].aendereWert(wert);
	}	
	
	/**
	 * Leert das Spielfeld. Alle Feldwerte nehmen den Wert LEER an.
	 */
	public void leereSpielfeld()
	{
		_feldarray = new Feld[9];
		
		for(int i = 0; i < 9; i++)
		{
			_feldarray[i] = new Feld();
		}
	}
	
	/**
	 * Prüft, ob der Feldarray leer ist.
	 * @return einen Boolean, ob das Feld leer ist.
	 */
	public boolean istLeer()
	{
	    boolean istLeer = true;
		Feldwert leer = Feldwert.LEER;
		
		for(Feld f : _feldarray)
		{
			if(!(f.gibWert() == leer))
			{
				istLeer = false;
			}
		}
		
		return istLeer;
		//return true;
	}

	/**
	 * Prüft, ob der Feldarray voll ist.
	 * @return einen Boolean, ob das Feld voll ist.
	 */
	public boolean istVoll() 
	{
		for(Feld i : _feldarray)
		{
			if(Feldwert.LEER == i.gibWert())
			{
				return false;
			}
		}
		return true;
	}
	

	/**
	 * Gibt den Feldarray als String im CSV Format wieder. Wenn das Feld von Kreis besetzt 
	 * ist wird "1" ausgegeben, ist es von Kreuz besetzt "0". Ist das Feld unbesetzt wird "9"
	 * ausgegeben. 
	 * Diese Daten werden genutzt, um die Spielergebnisse für die KI zu speichern. 
	 * @return
	 */
	public String gibString()
	{
		String feldstring = "";
		
		for(Feld f : _feldarray)
		{
			if(f.gibWert() == Feldwert.KREIS)
			{
				feldstring += 1;
			}
			else if(f.gibWert() == Feldwert.KREUZ)
			{
				feldstring += 0;
			}
			else
			{
				feldstring += 9;
			}
			if(f != _feldarray[_feldarray.length-1])
			{
				feldstring += ",";
			}
		}
		
		return feldstring;
	}
	
	/**
	 * Gibt die Feldgröße an die KI weiter.
	 * @return
	 */
	public int gibFeldgroeße()
	{
		return _feldarray.length;
	}

	/**
	 * Gibt einen Array, welche Felder besetzt sind
	 * @param _spieler
	 * @return
	 */
	public double[] gibZugarray(Feldwert _spieler) 
	{
		double[] zugarray = new double[gibFeldgroeße()];
		
		for(int i = 0; i < zugarray.length; i++)
		{
			if(_spieler == _feldarray[i].gibWert())
			{
				zugarray[i] = 1;
			}
			else if(Feldwert.LEER == _feldarray[i].gibWert())
			{
				zugarray[i] = 9;
			}
			else
			{
				zugarray[i] = 0;
			}
		}
		return zugarray;
	}
}
