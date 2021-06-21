package tictactoe.KI.Services;

import tictactoe.KI.Fachwerte.KiModus;
import tictactoe.materialien.Spielfeld;

/***
 * Das Interface ZugberechnerSiervice ist die Schnittstelle für konkrete Algorithmen, die
 * für die KI die Besten Züge berechnen soll. 
 * @author Jesper Dannath
 *
 */
public interface ZugberechnerService 
{
	/**
	 * Auf Basis eines geschätzten Modells soll ein Zug entsprechend der Größten Gewinn-
	 * wahrscheinlichkeit für die KI durchgeführt werden. 
	 * @param spielstand ein Spielstand der informationen über die bisher besetzten Felder in Form eines
	 * Spielfeldes enthällt
	 * @return Einen Int-Wert bestimmt welches Feld als nächstes von der KI besetzt wird. 
	 */
	public int berechneZug(Spielfeld spielstand);
	
	/**
	 * Auf Basis der Spieldaten(spieldaten.csv) soll ein Modell geschätzt werden, dass die
	 * Gewinnwahrscheinlichkeit bestimmter Züge voraussagen kann. 
	 */
	public void setUpModel(KiModus modus);

	public KiModus gibModus();
}
