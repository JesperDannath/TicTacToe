package tictactoe;

import javax.swing.JOptionPane;

/***
 * Der modiService nimmt eine einfache Abfrage des Spielmodus vor. Er frag ab, ob im KI-Modus gespielt werden soll
 * Wenn dies der Fall ist, fragt er ab, welcher KI-Modus genutzt werden soll.
 * @author Jesper Dannath
 *
 */
public class modiService 
{
	///JOptionPane _modusabfrage;
	boolean _mitKi;
	boolean _weiter;
	private String _KiModus;
	
	public modiService()
	{
		_weiter = false;
		
		String Modus = JOptionPane.showInputDialog("Soll im KI Modus gespielt werden? (Ja/Nein)");
		
		if(Modus.equals("ja") || Modus.equals("Ja"))
		{
			_mitKi = true;
		}
		if(_mitKi == true)
		{
			_KiModus = JOptionPane.showInputDialog("Welche KI soll benutzt werden? \n"
					+ " tippe:  zufall/ ols-regression/ neuronales Netz");
		}
		
		_weiter = true;
	}
	
	public String gibKiModus()
	{
		return _KiModus;
	}
	
	
	public boolean gibModus()
	{
		return _mitKi;
	}
	
	public boolean gibWeiter()
	{
		return _weiter;
	}
}
