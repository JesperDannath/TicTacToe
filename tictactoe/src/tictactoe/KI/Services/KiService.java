package tictactoe.KI.Services;

import tictactoe.Beobachtbar;
import tictactoe.Beobachter;
//Simport tictactoe.KI.FileReader;
import tictactoe.KI.KiBerechnerServices.NeuroNetPropZugberechner;
import tictactoe.KI.KiBerechnerServices.OLSzugberechner;
import tictactoe.KI.KiBerechnerServices.RandomZugberechner;
import tictactoe.KI.KiBerechnerServices.abstractZugberechner;
import tictactoe.fachwerte.Feldwert;
import tictactoe.fachwerte.Spielwert;
import tictactoe.services.Zugservice;

/**
 * Ein KiService stellt Methoden für die Benutzung der Ki bereit. Er beobachtet den 
 * Zugservice und wird vom KiWerkzeug beobachtet.
 * @author Jesper Dannath
 *
 */
public class KiService extends Beobachtbar implements Beobachter
{
	private Zugservice _zugservice;
//	private FileReader _filereader;
	private abstractZugberechner _zugberechner;
	private Feldwert _spieler;

	public KiService(Zugservice zugservice, Feldwert feldwert, String KiModus)
	{
		_zugservice = zugservice;
		_zugservice.setzeBeobachter(this);
		
		if(KiModus.toLowerCase().equals("zufall") || KiModus.toLowerCase().equals("z"))
		{
			_zugberechner = new RandomZugberechner();
		}
		else if(KiModus.toLowerCase().equals("ols-regression") || KiModus.toLowerCase().equals("ols"))
		{
			_zugberechner = new OLSzugberechner(feldwert);
		}
		else if(KiModus.toLowerCase().equals("neuronales netz") || KiModus.toLowerCase().equals("n"))
		{
			_zugberechner = new NeuroNetPropZugberechner(feldwert);
		}

		_spieler = feldwert;
	}

	/**
	 * Beachtet Änderungen im Zugservice. Wenn eine Runde zuende ist, werden 
	 * die Zugdaten(zugdaten.csv) des Spiels in die Spieldaten(spieldaten.csv) geschrieben, die alle
	 * bisherigen Spiele enthalten.
	 * Wenn der Spieler der KI an der Reihe ist, beauftragt der KiService einen ZugBerechner mit 
	 * der Berechnung eines Ki-Zuges.
	 */
	@Override
	public void beachteAenderungen() 
	{
		if(_zugservice.gibSpielwert() != Spielwert.GESTARTET)
		{
			_zugberechner.refreshData();
			_zugberechner.setUpModel(_zugberechner.gibModus());
		}
		
		//Berechnet den KI-Zug auf Basis des aktuellen Spielfeldes
		//TODO Bei einem Unentschieden tritt ein Fehler mit der KI auf!
		if(_zugservice.gibAktuellenSpieler() == _spieler && _zugservice.gibSpielwert() == Spielwert.GESTARTET)
		{
			_zugservice.macheZug(_zugberechner.berechneZug(_zugservice.gibSpielfeld()));
		}
	}
}
