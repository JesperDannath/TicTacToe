package tictactoe.KI.KiBerechnerServices;

import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;
import com.dkriesel.snipe.training.TrainingSampleLesson;

import tictactoe.KI.Fachwerte.KiModus;
import tictactoe.fachwerte.Feldwert;
import tictactoe.materialien.Spielfeld;

/**
 * Ein NeuroNetDummyZugberechner berechnet KI-Züge auf Basis eines einfachen Backpropagation Neuronalen
 * Netzes. Die Imputvariablen sind Dummy-codiert für die Besetzung eines Feldes von einem bestimmten Spieler
 * @author Jesper Dannath
 *
 */
public class NeuroNetPropZugberechner extends abstractZugberechner
{
	
	
	private NeuralNetwork _netz;
	KiModus modus = KiModus.NEUROSTANDARDIZE;

	public NeuroNetPropZugberechner(Feldwert feldwert)
	{
		_spieler = feldwert;
		setUpModel(modus);
	}

	@Override
	public int berechneZug(Spielfeld spielstand) 
	{
		int[] spielbareFelder = gibUnbesetzteFelder(spielstand);
		double[] vorhersagen = new double[spielbareFelder.length];
		
		for(int i = 0; i < vorhersagen.length; i++)
		{
			int aktuellesFeld = spielbareFelder[i];
			
			vorhersagen[i] = erstelleVorhersage(spielstand, aktuellesFeld);
		}
		
		//druckeVorhersagen(vorhersagen);
		return spielbareFelder[getMax(vorhersagen)];
	}

	private void druckeVorhersagen(double[] vorhersagen) 
	{
		for(double d : vorhersagen)
		{
			System.out.print(d + ",");
		}
		System.out.print("\n");
	}

	/**
	 * Erstellt die Vorhersage des neuronalen Netzes.
	 * @param spielstand der Spielstand
	 * @param aktuellesFeld das potentiell zu ziehende Feld
	 * @return die Siegwahrscheinlichkeit des gegebenen Zuges!
	 */
	private double erstelleVorhersage(Spielfeld spielstand, int aktuellesFeld) 
	{
		double[] zugArray = spielstand.gibZugarray(_spieler);
		double[] vorhersagenarray;
		
		if(modus == KiModus.NEURODUMMY)
		{
			vorhersagenarray = erstelleDummySpielstand(zugArray);
		}
		else //if(modus == KiModus.NEUROSTANDARDIZE)
		{
			vorhersagenarray = erstelleStandardizedSpielstand(zugArray);
		}
			
		vorhersagenarray[aktuellesFeld] = 1.0;
		
		//Das Netz gibt ausschließlich doublearrays zurück!
		double vorhersage = _netz.propagate(vorhersagenarray)[0];
		
		druckeVorhersagen(vorhersagenarray);
		
		return vorhersage;
	}


	@Override
	public void setUpModel(KiModus modus) 
	{
		double[][] x;
		double[][] y = gibY(modus);
		int input;
		
		if(modus == KiModus.NEURODUMMY)
		{
			x = gibDummySpieldaten(true);
			input = 18;
		}
		else //if(modus == KiModus.NEUROSTANDARDIZE)
		{
			x = gibStandardizedSpieldaten();
			input = 9;
		}
		
		NeuralNetworkDescriptor desc = new NeuralNetworkDescriptor(input, 20, 1);
		desc.setSettingsTopologyFeedForward();
		
		_netz = new NeuralNetwork(desc);
		
		TrainingSampleLesson lektion = new TrainingSampleLesson(x, y);
		
		_netz.trainBackpropagationOfError(lektion, 500, 0.01);
		
		System.out.println("Set Up!");
	}


	/**
	 * Gibt die Y-Variable in der von Smipe geforderten Form
	 * @param modus der Aktuelle Ki-Modus bestimmt, in welchem Format die Daten zurückgegeben werden.
	 * @return einen Array von Doubles, auf zweiter Ebene sind die Y-Werte
	 */
	private double[][] gibY(KiModus modus) 
	{
		double[] var = _datafile.gibVariable(9);
		double[][] result = new double[var.length][1]; 
		
		if(modus == KiModus.NEURODUMMY)
		{
			for(int i = 0; i < result.length; i++)
			{
				if(var[i] != 99)
				{
				  result[i][0] = var[i];
				}
				else if(var[i] == 99)
				{
					result[i][0] = 0;
				}
			}
		}
		//Im Stanndardisierten Modus entspricht 1 gewonnen, -1 verloren und 0 unentschieden.
		else if(modus == KiModus.NEUROSTANDARDIZE)
		{
			for(int i = 0; i < result.length; i++)
			{
				if(var[i] == 1)
				{
					result[i][0] = var[i];
				}
				else if(var[i] == 0)
				{
					result[i][0] = -1;
				}
				else if(var[i] == 99)
				{
					result[i][0] = 0;
				}
			}
		}
		
		return result;
	}

	@Override
	public KiModus gibModus() 
	{
		return modus;
	}

}
