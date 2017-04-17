 // Required to generate the GUI
import javax.swing.JOptionPane;

public class Player {

	private String playerName;
	private int playerGuess;
	private int roundsWon = 0;

	// Constructor method - sets the players name.
	public void Player (String name) {

		this.playerName = name;
	}

	// Getter method for the player's name.
	public String getName () {
		return this.playerName;
	}

	// Setter method for the player's name.
	public void setName () { 

		String name = (JOptionPane.showInputDialog("What is your name?" ));
		this.playerName = name;
	}

}