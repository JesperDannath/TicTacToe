package tictactoe.werkzeuge;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tictactoe.fachwerte.Feldwert;

/***
 * Die UI Klasse für das TicTac Toe Spielfeld
 * @author Jesper Dannath
 */
public class tttUi
{
	
	JFrame frame = new JFrame("TicTacToe");
	JPanel panel = new JPanel();
	
	JButton[] _spielButton = new JButton[9];
	
	final ImageIcon KREISICON;
	final ImageIcon KREUZICON;
	
	/**
	 * Das Frame wird mit einem GridLayout ausgestattet. Jedes Element enthällt einen JButton, 
	 * der im Konstruktor initialisiert wird (Loop über alle Buttons in einem JButton[])
	 * Die Bilddaten für die Feldmarkierungen werden aus den Dateien "kreuz.png" und "kreis.png" geladen.
	 */
		public tttUi()
		{
			panel.setLayout(new GridLayout(3,3,3,3));
			
			for(int i = 0; i < 9; i++)
			{
				_spielButton[i] = new JButton("");
				panel.add(_spielButton[i]);
			}
			
			frame.add(panel);
			
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.pack();
			frame.setSize(500,500);
			frame.setVisible(true);
			
			
			KREUZICON = new ImageIcon("kreuz.png");
			KREUZICON.setImage(KREUZICON.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
			KREISICON = new ImageIcon("kreis.png");
			KREISICON.setImage(KREISICON.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		}
	

		/**
		 * Eine Feldmarkierung wird mit einem Icon gesetzt (Vom Zugwerkzeug).
		 * @param wert wert des Feldes
		 * @param feld dem Wert enstprechendes Icon, dass gesetzt wird
		 */
		public void setIcon(Feldwert wert, int feld)
		{
			if(wert == Feldwert.KREIS)
			{
				_spielButton[feld].setIcon(KREISICON);
			}
			else if(wert == Feldwert.KREUZ)
			{
				_spielButton[feld].setIcon(KREUZICON);
			}
		}
		
	/**
	 * Alle gesetzten Icons werden entfernt.
	 */
	void leereSpielfeld()
    {
		for(int i = 0; i < 9; i++)
		{
			  _spielButton[i].setIcon(null);

	    }
    }

	/**
	 * Gibt den Array der JButtons der Spielfeld-UI.
	 * @return Array der SpielButtons
	 */
	public JButton[] getFeldButtonArray() 
	{
		return _spielButton;
	}
}

