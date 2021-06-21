package tictactoe.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tictactoe.KI.FileReader;
import tictactoe.fachwerte.Spielwert;
import tictactoe.materialien.Spielfeld;

/**
 * Schreibt Daten einzelner Züge in "zugdaten.csv" und Daten von ganzen Spielen in "spieldaten.csv"
 * Ist eine Schnittstelle zwischen dem Eigendlichen Spiel und der KI.
 * @author Jesper Dannath
 */
public class datenerstellService
{
	File zugFile;
	File spielFile;
	Zugservice _zugservice; 
	Spielfeld _zug;
	
	/**
	 * Konstruktor des datenerstellService
	 * @param zugservice der Zugservice
	 */
	public datenerstellService(Zugservice zugservice) 
	{
		_zug = new Spielfeld();
		_zugservice = zugservice;
		
	      zugFile = new File("zugdaten.csv");
	      spielFile = new File("spieldaten.csv");
		    try 
		    {
		    	if(!zugFile.exists())
		    	{
		    		zugFile.createNewFile();
		    	}
		    	if(!spielFile.exists())
		    	{
		    		spielFile.createNewFile();
		    		//schreibeHeader();
		    	}
			} catch (IOException e) 
		    {
				System.out.println("Zugdatei konnte nicht erstellt werden");
			}

	}
	
	/**
	 * Schreibt mit einem FileWriter einen Zug in "zugdaten.csv"
	 */
	public void schreibeZug()
	{
		_zug = _zugservice.gibSpielfeld();
		
		try(FileWriter filewriter = new FileWriter(zugFile , true)) 
		{
			filewriter.write(_zug.gibString());
			filewriter.write(System.lineSeparator());
			filewriter.close();
			System.out.println("Schreibe Zug!");
		} 
		catch (IOException e) 
		{
			System.out.println("Fehler beim Schreiben der Zugdaten");
		}
	}

	/**
	 * Löscht die aktuellen Zugdaten am Ende eines Spiels.
	 */
	private void loescheZugdaten() 
	{
		try 
	    {    
				zugFile.delete();
	    		zugFile.createNewFile();
		} 
		catch (IOException e) 
	    {
			System.out.println("Zugdatei konnte nicht gelöscht werden");
		}
	}

	/**
	 * Schreibt die aktuellen Zugdaten zusammen mit den Metadaten des Spiels in die Spieldaten
	 * Metadaten sind: Wer hat gewonnen?
	 */
	public void schreibeSpiel() 
	{
		try(FileWriter filewriter = new FileWriter(spielFile , true)) 
		{
			List<ArrayList<String>> zugdaten = leseZugdatenEin();
			erweitereSpieldaten(zugdaten);
			loescheZugdaten();
			System.out.println("Schreibe Spiel!");
		} 
		catch (IOException e) 
		{
			System.out.println("Fehler beim Schreiben der Spieldaten");
		}
	}

	/**
	 * Diese Funktion fügt den Zugdaten vor der Einspeisung in die Spieldaten eine neue Spalte mit den
	 * Spieldaten hinzu. 
	 * @param zugdaten Die Zugdaten in Fiorm einer Liste von String Arraylisten
	 */
	private void erweitereSpieldaten(List<ArrayList<String>> zugdaten) 
	{
		for(ArrayList<String> array : zugdaten)
		{
			String neuerString = "";
			
			for(String s : array)
			{
				neuerString += s + ",";
			}
				if(_zugservice.gibSpielwert() == Spielwert.KREISGEWINNT)
				{
					neuerString += "1";
				}
				else if(_zugservice.gibSpielwert() == Spielwert.KREUZGEWINNT)
				{
					neuerString += "0";
				}
				else
				{
					neuerString += "99";
				}
			schreibeDaten(spielFile, neuerString);
		}
	}

	/**
	 * Hilfsfunktion für das Schreiben einer Datenreihe in einem File
	 * @param file Das File in dem geschrieben werden soll
	 * @param string Die Zeile die in dem File hinzugefügt werden soll. 
	 */
	private void schreibeDaten(File file, String string) 
	{
		try(FileWriter filewriter = new FileWriter(spielFile , true)) 
		{
			filewriter.write(string);
			filewriter.write(System.lineSeparator());
			filewriter.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Fehler beim Erweitern der Spieldaten");
		}
	}

	/**
	 * Liest mit Hilfe eines FileReaders die Zugdaten ein
	 * @return eine Liste von String Arraylisten. Die inneren Arraylisten sind die Zeilen der Zugdaten. 
	 */
	private List<ArrayList<String>> leseZugdatenEin() 
	{
		FileReader filereader = new FileReader("zugdaten.csv");
		return filereader.gibDaten();
	}
}
