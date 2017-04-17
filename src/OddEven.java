/*Luis Payan
 * Homework 7
 */
import java.util.Scanner;  // Required to accept user input.
import java.util.Random;  // Required to generate random numbers.
import javax.swing.JOptionPane; // Required to generate the GUI

public class OddEven {
	public static void main (String[] args) {

		int roundsPlayed = 0; // rounds played.
		int roundsDesired = 0; // rounds player playing.
		int goodGuessCount = 0; // # player guessed correct.
		int badGuessCount = 0; // # player guessed wrong.
		int playerGuess = 0;  // The players in-game guess.
		int answer = 0;  // dice roll odd or even.
		String playerGuessString;  // string input of the player's guess.
		int roll;  // dice roll between 1-6
		double roundAccuracy = 0.0;  // player's accuracy
		boolean validMenuResponse = false;  // tracks player's valid menu response
		boolean validGameResponse = false;  // tracks player's valid game response
		boolean [] roundResults;  // array to track the round results.	
		String playerMessage;  // store player's feedback strings.
		String playerName = "null"; //Store player's name.
 
		// Welcomes user to the game with some text.
		displayWelcome(); 
		// Create a new player object.
		Player p1 = new Player ();
		// Set the player's name
		p1.setName();

		// This loop ensures the players rounds desired response is vaild input.
		while (!validMenuResponse) 
		{  
				// Ask the user for the number of rounds desired
				roundsDesired = setRoundsDesired ();

				if ( roundsDesired >= 0 ) {
					validMenuResponse = true;
				} else {
					playerMessage = "Invalid response, please try again.";
					JOptionPane.showMessageDialog (null, playerMessage);
				}
			}

		// Setup the results array for all of the rounds. Make it as large as the number of rounds desired.
		roundResults = new boolean [roundsDesired];
		
		// The main game loop.  Runs until we meet the number of desired rounds.
		while ( roundsPlayed < roundsDesired ) 
		{  
			// Create a randon number between 1-6 as the computer's dice roll.
			roll = 1 + (int) ( Math.random() * 6 );  	
			// Tell the player the round number they are on.
			playerMessage = String.format ("Round: %d", roundsPlayed + 1);
			JOptionPane.showMessageDialog (null, playerMessage);

			// This loop enforces a valid player repsonse for the odd / even guess.
			while (!validGameResponse) 
			{
				// Ask the player for a guess between 1 and 6.  Store in playerGuess as an Int
				playerGuessString = (JOptionPane.showInputDialog("Enter 1 to guess odd, 2 for even." ));
				playerGuess = Integer.parseInt (playerGuessString);
				// Set the valid game response variable if they respond with valid input.
				if (playerGuess >= 1 && playerGuess <= 2) 
				{
					validGameResponse = true;
				}
				else 
				{
					JOptionPane.showMessageDialog (null, "Invalid choice, please try again.");
				}
			}  // End valid Game Response loop

			// Set the answer of odd or even so we can determine the winner.
			if (roll % 2 == 0) 
			{
				answer = 2;
			} 
			else 
			{
				answer = 1;
			}

			// Check to see if the player's guess is equal to the answer dice roll.
			if ( playerGuess == answer ) 
			{
				// If correct, display a message telling them they've won.  Increment the goodGuessCount counter.
				JOptionPane.showMessageDialog (null, "You guessed correct!  You win this round!!! ");
				roundResults [roundsPlayed] = true;

			}
			else 
			{  // Player guess is NOT equal to answer.
				// Display a message indicating they lost, and what the real answer was.
				playerMessage = String.format ("Incorrect, you lose this round.  The correct answer was: %d", answer);
				JOptionPane.showMessageDialog (null, playerMessage);
				 
				roundResults [roundsPlayed] = false;
			}

			// Increment the round variable.  This controls the main game loop.
			roundsPlayed++;  

			//Caclulate the player's accuracy percentage this round
			if (playerGuess > answer)
			{
				roundAccuracy = (double) answer / playerGuess;
			}
			else 
			{
				roundAccuracy = (double) playerGuess / answer;
			}

			// Display the player thier guess accuracy for this round. Runs at the end of each round played.
			playerMessage = String.format ("Alright %s, your accuracy percentage this round was: %f.\n", p1.getName(), roundAccuracy * 100);
			JOptionPane.showMessageDialog (null, playerMessage);

			// Reset all valid response checks back to false so the response check loops can run again.
			validMenuResponse = false;  // Resets the Menu loop
			validGameResponse = false;  // Resets the Game loop

		} // End game loop

		//Calculate the number of wins and losses for all the rounds.
		goodGuessCount = countGoodResults (roundResults);
		badGuessCount = countBadResults (roundResults);
		
		// End the game and display the results to the player
		gameOver (roundsPlayed, goodGuessCount, badGuessCount);

	}  // End main

	public static int countBadResults (boolean results[]) {

		int badCounter = 0;

		for (int i = 0; i < results.length; i++) 
		{
			if (results[i] == false) 
			{
				badCounter ++;
			}
		}
		return badCounter;
	}

	/**
	*  good results counter.  
	* returns a count of good (true) results.
	*/
	public static int countGoodResults (boolean results[]) {

		int goodCounter = 0;

		for (int i = 0; i < results.length; i++) 
		{
			if (results[i] == true) 
			{
				goodCounter ++;
			}
		}
		return goodCounter;
	}

	/**
	*   Welcome Message. 
 	*  displays to the player when they first launch the game.
	*/
	public static void displayWelcome () {

		String message = "Welcome to the Odd Even Game.\n\nThe computer will roll a 6-sided die - try and guess if the number rolled is odd or even.";
		JOptionPane.showMessageDialog (null, message);
	}

	/**
	*  The Game Over method.
	*  This accepts three values roundsPlayed, goodGuessCount, badGuessCount and displays the end of game stats.
	*/
	public static void gameOver (int rounds, int good, int bad) {

		double winPercentage;
		String message;

		if (bad == 0) 
		{
			winPercentage = 100.0;
		}
		else if (good == 0) 
		{
			winPercentage = 0.0;
		}
		else if (good > bad) 
		{
			winPercentage = (double) bad / rounds * 100;
		}
		else if (bad > good ) 
		{
			winPercentage = (double) good / rounds * 100;
		} 
		else 
		{
			winPercentage = 50.00;
		}

		message = String.format ("GAME OVER.\n\nYou played a total of %d rounds.\nYou won a total of %d rounds\nYou lost a total of %d rounds.\nYour win/loss ratio was: %f", rounds, good, bad, winPercentage);

		JOptionPane.showMessageDialog (null, message);
	}

	/**
	* The rounds desired to play by user.  
	* The method returns an intiger representing the rounds the users has selected.
	*/
	public static int setRoundsDesired (){

		//  Ask the user for their rounds - input is stored as a string.
		String inputString = (JOptionPane.showInputDialog("How many rounds do you wish to play?" ));

		// Convert the rounds desired string to an intiger.
		int input = Integer.parseInt (inputString);  

		// Return back the player's rounds desired in intiger format.
		return input;
	}

}  // End Class