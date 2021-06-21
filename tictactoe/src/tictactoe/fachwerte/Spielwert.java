package tictactoe.fachwerte;

/**
 * Ein Spielwert gibt an, in welchem Zustand sich das Spiel befindet. Solange das Spiel läuft ist der Zustand 
 * GESTARTET. Am Ende des Spiels gewinnt einer der beiden Spieler oder es gibt unentschieden. 
 * @author Jesper Dannath
 *
 */
public enum Spielwert 
{
	GESTARTET, KREISGEWINNT, KREUZGEWINNT, UNENTSCHIEDEN;
}


