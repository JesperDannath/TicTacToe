package tictactoe.KI.Werkzeuge;

import tictactoe.Beobachter;
import tictactoe.KI.Services.KiService;

/**
 * Das KiWerkzeug ist die Schnittstelle der KI. Sie stellt eine UI zur Verfügung, 
 * über die der Algorithmus ausgewählt werden kann, der für das Spiel genutzt werden soll. 
 * @author Jesper Dannath
 *
 */
public class KiWerkzeug implements Beobachter
{	
	private KiService _kiservice;

	public KiWerkzeug(KiService kiservice)
	{
	}

	@Override
	public void beachteAenderungen() 
	{
		
	}

	public KiService getkiservice() 
	{
		return _kiservice;
	}

}


