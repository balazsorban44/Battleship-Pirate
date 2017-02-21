package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GameController {

	@FXML
	TextArea board, console;
	@FXML
	TextField inputField;
	@FXML
	BattleshipBoard playerOne, playerTwo;

	private int turn;

	public void initialize() {
		turn = 1;
		playerOne = new BattleshipBoard(10, 10,
				"...XX........XX..XXX...XX..........................X.........X...XXX...X.........X..XX..............");
		playerTwo = new BattleshipBoard(10, 10,
				"................................XX.........XXX........XXXX.....XXX.....XXX.......XXX................");
		console.setText("\n                             Welcome to the game!\n\n" + "  INSTRUCTIONS:\n\n"
				+ "  - '~' the field has not been attacked yet\n\n"
				+ "  - '☠' the field has been attacked, and is part of a boat\n\n"
				+ "  - '○' the field has been attacked, but it was empty\n\n"
				+ "  - The accepted syntax for giving the coordinates is two numbers\n"
				+ "    from 1 to 10, connected together with a comma',', for example: \"3,10\"\n\n"
				+ "  - The board numbering begins in the top left, which is represented\n"
				+ "    by 1,1, while the bottom right corner is represented by 10,10\n\n"
				+ "  - If a player goes out of the board, they may try to attack again\n\n"
				+ "  - A player cannot attack the same field twice\n\n"
				+ "  - Whoever finds the opponents' all boats, wins the game" + "\n\n  Happy fighting!\n"
				+ "\n  Player 1 begins...\n");
		board.setText(playerOne.toString());
	}

	@FXML
	public void sendInput() {
		String in = inputField.getText();
		if (turn == 1) {
			board.setText(playerOne.toString());
			playerOne.getInput(in);
			board.setText(playerOne.toString());
			if (playerOne.getNextTurn()) {
				if (playerOne.getHitCounter() > 17) {
					if (playerOne.getIsE()) {
						console.setStyle("-fx-text-fill: #65cd9d");
						console.setText(playerOne.getMsg() + "\n  Player 1 won! Nice job!");
					} else {
						console.setText("Player 1 won! Nice job!");

					}
					turn = 0;
					return;
				}
				turn = 2;
				console.setText("\n  Player 1 attacked " + in + " and it was a " + playerOne.getCellStatus()
						+ "\n  Player 2's turn...");
			} else {
				console.setText(console.getText() + "\n" + playerOne.getCellStatus());
			}

		} else if (turn == 2) {
			board.setText(playerTwo.toString());
			playerTwo.getInput(in);
			board.setText(playerTwo.toString());
			if (playerTwo.getNextTurn()) {
				if (playerTwo.getHitCounter() > 17) {
					if (playerTwo.getIsE()) {
						console.setStyle("-fx-text-fill: #65cd9d");
						console.setText(playerTwo.getMsg() + "\n  Player 2 won! Nice job!");
					} else {
						console.setText("  Player 2 won! Nice job!");

					}
					turn = 0;
					return;
				}
				turn = 1;
				console.setText("\n  Player 2 attacked " + in + " and it was a " + playerTwo.getCellStatus()
						+ "\n  Player 1's turn...");
			} else {
				console.setText(console.getText() + "\n" + playerTwo.getCellStatus());
			}
		}
	}
}
