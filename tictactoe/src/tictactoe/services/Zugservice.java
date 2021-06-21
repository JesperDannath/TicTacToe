package tictactoe.services;



import tictactoe.Beobachtbar;
import tictactoe.fachwerte.Feldwert;
import tictactoe.fachwerte.Spielwert;
import tictactoe.materialien.Spielfeld;

/**
 * Der Zugservice regelt den gesamten Ablauf des Spiels und �ndert seinen Zustand je nachdem welcher Spieler
 * dran ist. Die Indices der Felder werden in dieser Klasse zu den fachlich korrekten Werten 1-9 ge�ndert.
 * Der Zugservice implemntiert das Interface Beobachter und wird vom Zugwerkzeug und dem Kiervice beobachtet.
 * @author Jesper Dannath
 * @version 1.0
 *
 */
public class Zugservice extends Beobachtbar
{
	static Spielfeld _spielfeld;
	static Feldwert _aktuellerSpieler;
	static Spielwert _spielwert;
	private Feldwert _startSpieler;
	private datenerstellService _datenerstellservice;
	//static datenerstellService _datenerstellservice;
	
	
	public Zugservice()
	{
		_spielfeld = new Spielfeld();
		_aktuellerSpieler = Feldwert.KREIS;
		_startSpieler = Feldwert.KREUZ;
		_spielwert = Spielwert.GESTARTET;
		_datenerstellservice = new datenerstellService(this);
	}

	/**
	 * Macht einen Zug, indem ein bestimmtes Feld, wenn es frei ist, vom aktuellen Spieler markiert wird. 
	 * Ist ein Feld nicht frei, passiert nichts und der aktuelle Spieler kann weiter ziehen. 
	 * Die �nderungen werden an die Beobachter berichtet.
	 * @param i der zahlenwert des zu besetztenden Feldes. 
	 */
	public void macheZug(int i)
	{
		if(_spielfeld.gibWertVonFeld(i)  == Feldwert.LEER && _spielwert == Spielwert.GESTARTET)
		{
			_spielfeld.aendereFeld(i, _aktuellerSpieler);
			aktualisiereSpiel();
			_datenerstellservice.schreibeZug();
		}
		meldeAenderungen();
	}
	
	/**
	 * Gibt den Besetzer des angefragten Feldes. Wenn das Feld leer ist, wird Feldwert.LEER zur�ckgegeben.
	 * @param index int-Wert des zu pr�fenden Feldes.
	 * @return aktueller Feldwert des zu pr�fenden Feldes.
	 */
	public Feldwert gibBesetzerVonFeld(int index)
	{
		return _spielfeld.gibWertVonFeld(index);
	}

	/**
	 * Setzt das Spiel zur�ck und berichtet die �nderungen an alle Beobachter. Der neue Startspieler
	 * ist der nicht-startende Spieler der letzten Runde. 
	 */
	public void setzeSpielZurueck()
	{
		_datenerstellservice.schreibeSpiel();
		_spielfeld.leereSpielfeld();
		_spielwert = Spielwert.GESTARTET;
		
		_aktuellerSpieler = _startSpieler;
		
		if(_startSpieler == Feldwert.KREIS)
		{
			_startSpieler = Feldwert.KREUZ;
		}
		else
		{
			_startSpieler = Feldwert.KREIS;
		}
		
		meldeAenderungen();
	}
	
	/**
	 * Wechselt den Aktuellen Spieler, nachdem ein Zug gemacht wurde. 
	 * Sollte es einen Sieger durch den letzten Zug gegeben haben, wird dies ermittelt 
	 * und der Spielwert entsprechend ge�ndert.
	 */
	public void aktualisiereSpiel() 
	{
		if(_aktuellerSpieler == Feldwert.KREIS)
		{
			_aktuellerSpieler = Feldwert.KREUZ;
		}
		else
		{
			_aktuellerSpieler = Feldwert.KREIS;
		}
		
		if(hatGewonnen(Feldwert.KREIS))
		{
			_spielwert = Spielwert.KREISGEWINNT;
		}
		else if(hatGewonnen(Feldwert.KREUZ))
		{
			_spielwert = Spielwert.KREUZGEWINNT;
		}
	}
	
	/**
	 * Kl�rt, ob ein Spieler gewonnen hat.
	 * @param wert Bezeichnung des Spielers der gewonnen hat.
	 * @return ob der Spieler gewonnen hat.
	 */
	private boolean hatGewonnen(Feldwert wert)
	{
		boolean sieg = false;
		int[][] bedingungen = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

		
		for(int[] i :  bedingungen)
		{
			if((_spielfeld.gibWertVonFeld(i[0]) == wert) && (_spielfeld.gibWertVonFeld(i[1]) == wert)
					&& (_spielfeld.gibWertVonFeld(i[2]) == wert))
			{
				sieg = true;
			}
		}
		
		return sieg;
	}

	/**
	 * Gibt das Spielfeld zur�ck.
	 * @return Das Spielfeld
	 */
	public Spielfeld gibSpielfeld()
	{
		return _spielfeld;
	}

	/**
	 * Gibt den aktuellen Spielwert zur�ck.
	 * @return den aktuellen Spielwert
	 */
	public Spielwert gibSpielwert() 
	{
		return _spielwert;
	}

	/**
	 * Gibt den Gewinner als String zur�ck. Wird verwendet in der Ausgabenachricht. 
	 * @return einen String, wer der Gewinner ist.
	 */
	public String gibGewinner() 
	{
		if(_spielwert == Spielwert.KREISGEWINNT)
		{
			return "Kreis";
		}
		else if(_spielwert == Spielwert.KREUZGEWINNT)
		{
			return "Kreuz";
		}
		return "Keiner"; 
	}

	/**
	 * Gibt zur�ck, ob das Spielfeld voll ist. Wird vom Zugwerkzeug genutzt, um ein 
	 * Unentschieden zu entdecken
	 * @return Boolean, ob das Spielfeld voll ist. 
	 */
	public boolean spielfeldIstVoll() 
	{

		return _spielfeld.istVoll();
	}
	
	/**
	 * Gibt zur�ck welcher Spieler mit ziehen an der Reihe ist.
	 * @return den aktuellen Spieler
	 */
	public Feldwert gibAktuellenSpieler()
	{
		return _aktuellerSpieler;
	}
}
