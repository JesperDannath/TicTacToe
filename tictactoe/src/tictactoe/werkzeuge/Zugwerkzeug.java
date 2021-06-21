package tictactoe.werkzeuge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import tictactoe.Beobachter;
import tictactoe.fachwerte.Feldwert;
import tictactoe.fachwerte.Spielwert;
import tictactoe.services.Zugservice;

/**
 * Ein Zugwerkzeug verwaltet die UI des Spiels und beobachtet den Zugservice, sodass Änderungen
 * im Spiel in der UI umgesetzt werden. 
 * @author Jesper Dannath
 *
 */
public class Zugwerkzeug implements Beobachter
{
	Zugservice _zugservice;
	tttUi _ui;
	
	/**
	 * Das Zugwerkzeug erzeugt die UI im eigenen Konstruktor.
	 * @param zugservice der zu beobachtende und zu benutzende Zugservice
	 */
	public Zugwerkzeug(Zugservice zugservice)
	{
		_zugservice = zugservice;
		_ui = new tttUi();
		_zugservice.setzeBeobachter(this);		
		//setAsBeobachter();
		registriereZug();
	}
	
	
	//private void setAsBeobachter() 
	//{
	//       _zugservice.setzeBeobachter(new Beobachter()
	//        {
   // 
   //         @Override
	//            public void beachteAenderungen()
	//           {
	//        	  aktualisiereMarkierung();
	//           }
	//       });
	//}
	
	/**
	 * Gibt den verwendeten Zugservice zurück. Wird verwendet, um Klassen auf höherer
	 * Ebene direkten Zugriff auf den Zugservice zu geben. 
	 * @return den Zugservice.
	 */
	public Zugservice gibZugservice()
	{
		return _zugservice;
	}
	
	/**
	 * Nimmt eine Zug-Event auf und verarbeitet die Informationen.
	 * Um dies zu leisten wird das Interface Action-Listener mit einer anonymen
	 * inneren Klasse implementiert. 
	 */
	public void registriereZug()
	{
		JButton[] buttonarray = _ui.getFeldButtonArray();
		ActionListener listener = new ActionListener() 
				{
					
					@Override
	                public void actionPerformed(ActionEvent e)
	                {
						for(int i = 0; i <9; i++)
						{
							if(e.getSource() == buttonarray[i])
							{
								//System.out.println("Zug");
								_zugservice.macheZug(i);
								aktualisiereMarkierung();
							}
						}
	                }
				};

		for(int i = 0; i < 9; i++)
		{
			buttonarray[i].addActionListener(listener);
		}
	}
	
	/**
	 * Aktualisiert alle Markierungen des UI-Spielfeldes (Synchronisert Spielfeld und UI)
	 */
	public void aktualisiereMarkierung() 
	{

		for(int i = 0; i < 9; i++)
		{
			Feldwert besetzer = gibZugservice().gibBesetzerVonFeld(i);
			if(besetzer == Feldwert.KREIS)
			{
			  _ui.setIcon(Feldwert.KREIS, i);
			}
			else if(besetzer == Feldwert.KREUZ)
			{
				_ui.setIcon(Feldwert.KREUZ, i);
			} 
		}
		if(gibZugservice().gibSpielwert() != Spielwert.GESTARTET | gibZugservice().spielfeldIstVoll())
		{
			JOptionPane.showMessageDialog(null , gibZugservice().gibGewinner() + " hat gewonnen!", null, JOptionPane.PLAIN_MESSAGE);
			gibZugservice().setzeSpielZurueck();
			_ui.leereSpielfeld();
		}
	}

	/**
	 * Beachtet die Änderungen im mZugservice indem eine Synchronisation der UI in Gang gesetzt wird.
	 */
	@Override
	public void beachteAenderungen() 
	{
		aktualisiereMarkierung();
	}
}

