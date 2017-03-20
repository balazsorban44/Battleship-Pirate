package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class GameController {
	@FXML
	TextArea board, console;
	@FXML TextField fileName;
	@FXML
	TextField inputField;
	@FXML
	BattleshipBoard playerOne, playerTwo;
	private int turn;
	private int size=10;
	private String
	shipTypesP1="H23,S14,U31,F31,P21",
	shipsP1="H41,S26,U82,F67,P59",
	p1="....................................................................................................",
	shipTypesP2="H23,S14,U31,F31,P21",
	shipsP2="H41,S26,U82,F67,P59",
	p2="....................................................................................................",
	consoleText ="\nWelcome to the game!\n\n" + "  INSTRUCTIONS:\n\n"
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
			+ "\n  Player 1 begins...\n";
	
	public void initialize() {
		turn = 1;
		playerOne = new BattleshipBoard(size,p1,shipTypesP1, shipsP1);
		playerTwo = new BattleshipBoard(size,p2, shipTypesP2, shipsP2);
		console.setText(consoleText);
		board.setText(playerOne.toString());
		inputField.setPromptText("X,Y");
		if (playerOne.getHitCounter() > 17) {
			console.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer 1 won! Nice job!");
			return;
		}
		else if (playerTwo.getHitCounter() > 17) {
			console.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer 2 won! Nice job!");
			return;
		}

	}
	@FXML
	public void save() throws IOException {
		FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Find your ships, mate!");
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Battleship save file", "*.pir"));
		 File selectedFile = fileChooser.showSaveDialog(console.getScene().getWindow());
		 FileWriter fw = new FileWriter(selectedFile);
		 try {
			 fw.write(
					 this.size + "\n"
					 + this.shipTypesP1 + "\n"
					 + this.shipsP1 + "\n"
					 + playerOne.getState() + "\n"
					 + this.shipTypesP2 + "\n"
					 + this.shipsP2 + "\n"
					 + playerTwo.getState() + "\n"
			);
		} catch (FileNotFoundException fnfe) {
			console.setText("File " + selectedFile.getName() + " not found.");
			System.err.println("File not found.");
		} finally {
			try {
				fw.close();
				console.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\nFile " + selectedFile.getName() + " saved.");
			} catch (IOException e) {
				System.err.println("Some other IO error");
			}
		}
	}

	
	@FXML
	public void load() throws IOException {
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Find your ships, mate!");
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Battleship save file", "*.pir"));
		 File selectedFile = fileChooser.showOpenDialog(console.getScene().getWindow());
		 FileReader reader = new FileReader(selectedFile);
		 BufferedReader br = null;
		 String loadedString = "";
		 try {
			 br = new BufferedReader(reader);
			 while (br.ready()) {
				loadedString += br.readLine();
				loadedString += "\n";
			}

		} catch (FileNotFoundException fnfe) {
			console.setText("File " + selectedFile.getName() + " not found.");
			System.err.println("File not found.");
		} finally {
			try {
				br.close();
				String[] lS = loadedString.split("\n");
				
				this.size = Integer.parseInt(lS[0]);
				this.shipTypesP1= lS[1];
				this.shipsP1=lS[2];
				this.p1=lS[3];
				this.shipTypesP2=lS[4];
				this.shipsP2=lS[5];
				this.p2=lS[6];
				console.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\nFile " + selectedFile.getName() + " loaded.");
				initialize();
			} catch (IOException e) {
				System.err.println("Some other IO error");
			}
		}
	}
	
	@FXML
	public void reset() throws IOException {
		this.p2 = this.p1 = "....................................................................................................";
		initialize();
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
						console.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer 1 won! Nice job!");

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
						console.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer 2 won! Nice job!");

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
