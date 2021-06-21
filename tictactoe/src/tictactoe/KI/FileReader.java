package tictactoe.KI;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Ein FileReader kann eine CSV Datei in ein Java Objekt einlesen.
 * @author Jesper Dannath
 */
public class FileReader 
{
	List<ArrayList<String>> _records;
	
	/**
	 * Schon bei der Erzeugung liest der FileReader die Daten ein, sofern ein gültiger Dateipfad als
	 * Konstruktorparamenter übergeben wurde.
	 * @param path
	 */
	public FileReader(String path)
	{
		_records = new ArrayList<ArrayList<String>>();
	try(
            Reader reader = Files.newBufferedReader(Paths.get(path));
            CSVReader csvReader = new CSVReader(reader);
        ) 
	{ 	
		   List<String[]> rawRecords = csvReader.readAll();
		   for(String[] s : rawRecords)
		   {
			   _records.add(new ArrayList<>(Arrays.asList(s)));
		   }
	}  
	catch(IOException i)
	{
		System.out.println("Lesen der daten nicht möglich");
	}
   }
	
	/**
	 * Druckt die eingelesenen Daten auf die Konsole.
	 */
	public void druckeDaten()
	{
		for(ArrayList<String> array : _records)
		{
			for(String s : array)
			{
				System.out.print(s+ ",");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Gibt die Daten im String Format als Java Objekt zurück. 
	 * @return EIn Objekt mit den vollständigen Daten des CSV Files
	 */
	public List<ArrayList<String>> gibDaten()
	{
		return _records;
	}
}

