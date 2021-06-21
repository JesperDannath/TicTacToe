package tictactoe.KI.KiBerechnerServices;

import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;
import com.dkriesel.snipe.training.TrainingSampleLesson;

import tictactoe.KI.Fachwerte.KiModus;
import tictactoe.fachwerte.Feldwert;
import tictactoe.materialien.Spielfeld;

public class NeuroNetPatternZugberechner extends abstractZugberechner
{
	private NeuralNetwork _netz;
	KiModus modus = KiModus.NEUROZUGPATTERN;

	public NeuroNetPatternZugberechner(Feldwert feldwert)
	{
		_spieler = feldwert;
		setUpModel(modus);
	}

	@Override
	public int berechneZug(Spielfeld spielstand) 
	{
		int[] spielbareFelder = gibUnbesetzteFelder(spielstand);
		double[] vorhersagen = new double[spielbareFelder.length];
		
		vorhersagen = erstelleVorhersage(spielstand);
		
		//druckeVorhersagen(vorhersagen);
		return getMax(vorhersagen);
	}

	private double[] erstelleVorhersage(Spielfeld spielstand) 
	{
		double[] vorhersagen = _netz.propagate(erstelleStandardizedSpielstand(spielstand.gibZugarray(_spieler)));
		return vorhersagen;
	}

	@Override
	public void setUpModel(KiModus modus) 
	{
		double[][] x;
		double[][] y = gibY(modus);
		int input;
		
		x = gibStandardizedSpieldaten();
		input = 9;
		
		
		NeuralNetworkDescriptor desc = new NeuralNetworkDescriptor(input, 20, 9);
		desc.setSettingsTopologyFeedForward();
		
		_netz = new NeuralNetwork(desc);
		
		TrainingSampleLesson lektion = new TrainingSampleLesson(x, y);
		
		_netz.trainBackpropagationOfError(lektion, 500, 0.01);
		
		System.out.println("Set Up!");
	}

	/**
	 * Gibt einen zweidimensionalen Vektor mit erwarteten Ausgabedaten zurück.
	 * Die erwartete Ausgabe ist ein Vektor der alle Spielfelder repräsentiert 
	 * und das zu einem Sieg nötige Spielfeld nutzt. 
	 * @param modus2
	 * @return
	 */
	private double[][] gibY(KiModus modus) 
	{
		double[] var = _datafile.gibVariable(9);
		int laenge = _datafile.count(9, 1);
		double[][] result = new double[laenge][9]; 
		
		int fallindex = 0;
		
		//vorsicht vor ArrayOutOfBounds
		for(int i = 0; i<var.length-1; i++)
		{
			if(var[i] == 1)
			{
				result[fallindex][gibErfolgtenZug(i)] = 1;
			}
		}
		
		result = fuelleAusgabedatenAuf(result);

		return result;
	}

	/**
	 * Füllt alle leeren Stellen in den Ausgabedaten auf. 
	 * @param result
	 * @return
	 */
	private double[][] fuelleAusgabedatenAuf(double[][] result) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	private int gibErfolgtenZug(int i) 
	{
		return 0;
	}

	@Override
	public KiModus gibModus() 
	{
		return modus;
	}


}
