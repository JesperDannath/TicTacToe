package tictactoe.KI.KiBerechnerServices;

import java.util.Random;

import tictactoe.KI.Fachwerte.KiModus;
import tictactoe.fachwerte.Feldwert;
import tictactoe.materialien.Spielfeld;

/***
 * Ein Random Zugberechner berechnet KI-züge mit Hilfe eines Zufallsgenerators.
 * ZugberechnerService wird implementiert.
 * @author Jesper Dannath
 */
public class RandomZugberechner extends abstractZugberechner//implements ZugberechnerService 
{
	Random _randomGenerator;
	//Spielfeld _spielstand;
	
	/**
	 * Ein Objekt der Klasse Random wird im Konstruktor erzeugt und später für die generierung der 
	 * Züge genutzt. 
	 */
	public RandomZugberechner()
	{
		_randomGenerator = new Random();
	}

	
	@Override
	/**
	 * Der Zug wird mit dem Zufallsgenerator berechnet
	 */
	public int berechneZug(Spielfeld spielstand) 
	{
		int versuch = _randomGenerator.nextInt(9);
		
		if(spielstand.gibWertVonFeld(versuch) != Feldwert.LEER)
		{
			versuch = berechneZug(spielstand);
		}
		return versuch;
	}

	/**
	 * Es ist nicht notwendig ein Modell mithilfe der Zugdaten zu schätzen.
	 */
	@Override
	public void setUpModel(KiModus modus) 
	{
	}


	@Override
	public KiModus gibModus() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
