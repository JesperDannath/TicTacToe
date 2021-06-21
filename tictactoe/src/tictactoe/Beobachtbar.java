package tictactoe;

import java.util.LinkedList;

/**
 * Die abstrakte Klasse Beobachtbar setzt das Beobachter Pattern (Entwurfsmuster) um. 
 * Klassen deren Exemplare von anderen Klassnen beobachtet werden sollen, k�nnen von dieser Klasse erben
 * @author Jesper Dannath
 *
 */
public abstract class Beobachtbar
{
	LinkedList<Beobachter> _beobachter;
	
	/**
	 * Eine Liste von Beobachtern wird im Konstruktor initialisiert. 
	 */
	public Beobachtbar()
	{
		_beobachter = new LinkedList<Beobachter>();
	}
	
	/**
	 * Ein neuer Beobachter kann sich �ber diese Funktion anmelden.
	 * @param beobachter
	 */
	public void setzeBeobachter(Beobachter beobachter)
	{
		_beobachter.add(beobachter);
	}
	
	/**
	 * Alle Beobachter werden �ber �nderungen in der boebachteten Klasse informiert. 
	 */
	public void meldeAenderungen() 
	{
		for(Beobachter b : _beobachter)
		{
			b.beachteAenderungen();
		}
	}

}
