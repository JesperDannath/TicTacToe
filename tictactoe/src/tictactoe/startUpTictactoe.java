package tictactoe;

import tictactoe.KI.Services.KiService;
import tictactoe.KI.Werkzeuge.KiWerkzeug;
import tictactoe.fachwerte.Feldwert;
import tictactoe.services.Zugservice;
import tictactoe.werkzeuge.Zugwerkzeug;

/**
 * Main Methode für das Spiel TicTacToe. Alle Hauptwerkzeuge und Servicec werden erzeugt.
 * Kann ausgewählt werden, erst danach werden die anderen Services und Werkzeuge erzeugt. 
 * @author Jesper Dannath
 *
 */
public class startUpTictactoe 
{
	static Zugservice _zugservice;
	static Zugwerkzeug _zugwerkzeug;
	static KiWerkzeug _kiwerkzeug;
	private static KiService _kiservice;
	private static modiService _modiService;

	public static void main(String[] args) 
	{
		_modiService = new modiService();
		
		while(_modiService.gibWeiter() == false)
			{
			}
		
			_zugservice = new Zugservice();
		
		if(_modiService.gibModus() == true)
		{
			_kiservice = new KiService(_zugservice, Feldwert.KREUZ, _modiService.gibKiModus());
			_kiwerkzeug = new KiWerkzeug(_kiservice);
		}
		
		_zugwerkzeug = new Zugwerkzeug(_zugservice);
	}
}
