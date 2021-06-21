package tictactoe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.*;

import tictactoe.fachwerte.Feldwert;
import tictactoe.fachwerte.Spielwert;
import tictactoe.materialien.Spielfeld;

/**
 * Testklasse f�r den Zugservice. Stellt Tests f�r die Funktionalit�t des Zugservice bereit. 
 * @author Jesper Dannath
 *
 */
public class ZugserviceTest 
{
	
	Spielfeld _spielfeld;
	Zugservice _zugservice;
	
	
	public ZugserviceTest()
	{
		_spielfeld = new Spielfeld();
		_zugservice = new Zugservice();
	}
	
	/**
	 * Testet, ob ein neu erzeugtes Spielfeld leer ist. 
	 */
	@Test 
	public void NeuesSpielfeldIstLeerTest()
	{
		assertTrue("Spielfeld nicht leer", _spielfeld.istLeer());
	}
	
	/**
	 * Testet, ob gemachte Z�ge registriert werden. 
	 */
	@Test
	public void markiertesFeldIstMarkiert()
	{
		_zugservice.setzeSpielZurueck();
		_zugservice.macheZug(3);
		assertEquals(Feldwert.KREIS, _zugservice.gibBesetzerVonFeld(3));
		_zugservice.macheZug(4);
		assertEquals(Feldwert.KREUZ, _zugservice.gibBesetzerVonFeld(4));
	}
	
	/*
	 * Testet, dass doppeltes(�berschreibendes) markieren eines Feldes nicht m�glich ist
	 */
	@Test
	public void doppeltesMarkierenNichtM�glich()
	{
		_zugservice.setzeSpielZurueck();
		_zugservice.macheZug(3);
		_zugservice.macheZug(3);
		assertEquals(Feldwert.KREIS, _zugservice.gibBesetzerVonFeld(3));	
	}
	
	/*
	 * Testet, ob Markierungen, die zu einem Sieg f�hren m�ssten, wirklich zum Sieg des
	 * entsprechenden Spielers f�hren. 
	 */
	@Test
	public void gewinnerHatGewonnen()
	{
		//drei nebeneinander waagerecht
		_zugservice.setzeSpielZurueck();
		_zugservice.gibSpielfeld().aendereFeld(0, Feldwert.KREIS);
		_zugservice.gibSpielfeld().aendereFeld(1, Feldwert.KREIS);
		_zugservice.gibSpielfeld().aendereFeld(2, Feldwert.KREIS);
		_zugservice.aktualisiereSpiel();
		assertEquals(_zugservice.gibSpielwert(), Spielwert.KREISGEWINNT);
		
		//drei nebeneinander senkrecht
		_zugservice.setzeSpielZurueck();
		_zugservice.gibSpielfeld().aendereFeld(1, Feldwert.KREUZ);
		_zugservice.gibSpielfeld().aendereFeld(4, Feldwert.KREUZ);
		_zugservice.gibSpielfeld().aendereFeld(7, Feldwert.KREUZ);
		_zugservice.aktualisiereSpiel();
		assertEquals(_zugservice.gibSpielwert(), Spielwert.KREUZGEWINNT);
		
		//diagonal
		_zugservice.setzeSpielZurueck();
		_zugservice.gibSpielfeld().aendereFeld(0, Feldwert.KREIS);
		_zugservice.gibSpielfeld().aendereFeld(4, Feldwert.KREIS);
		_zugservice.gibSpielfeld().aendereFeld(8, Feldwert.KREIS);
		_zugservice.aktualisiereSpiel();
		assertEquals(_zugservice.gibSpielwert(), Spielwert.KREISGEWINNT);
	}
}
