package tictactoe.KI.Services;

import tictactoe.KI.Fachwerte.KiModus;
import tictactoe.materialien.Spielfeld;

/***
 * Das Interface ZugberechnerSiervice ist die Schnittstelle f�r konkrete Algorithmen, die
 * f�r die KI die Besten Z�ge berechnen soll. 
 * @author Jesper Dannath
 *
 */
public interface ZugberechnerService 
{
	/**
	 * Auf Basis eines gesch�tzten Modells soll ein Zug entsprechend der Gr��ten Gewinn-
	 * wahrscheinlichkeit f�r die KI durchgef�hrt werden. 
	 * @param spielstand ein Spielstand der informationen �ber die bisher besetzten Felder in Form eines
	 * Spielfeldes enth�llt
	 * @return Einen Int-Wert bestimmt welches Feld als n�chstes von der KI besetzt wird. 
	 */
	public int berechneZug(Spielfeld spielstand);
	
	/**
	 * Auf Basis der Spieldaten(spieldaten.csv) soll ein Modell gesch�tzt werden, dass die
	 * Gewinnwahrscheinlichkeit bestimmter Z�ge voraussagen kann. 
	 */
	public void setUpModel(KiModus modus);

	public KiModus gibModus();
}
