package application;

import java.util.ArrayList;

public class BattleshipBoard {

	private int size, hitCounter = 0;
	private String msg, by, cellStatus;
	private ArrayList<String> state = new ArrayList<>();
	private boolean nextTurn = true, isE = false;
	private ArrayList<ArrayList<BoardCell>> cells;
	private ArrayList<Ship> ships = new ArrayList<>();
	
	public BattleshipBoard(int size, String state, String shipTypes,String shipsString) {
		EasterEgg e = new EasterEgg();
		by = e.by();
		msg = e.msg();
		this.size = size;
		int i,j;
		
		//Initial state of the board
		for (i = 0; i < size; i++) {
			this.state.add(state.substring(i*10, i*10+10));
		}
		//Ships on the board
		for (i = 0; i < shipsString.split(",").length; i++) {
			ships.add(new Ship(shipTypes.split(",")[i], shipsString.split(",")[i]));
		}
		//Initializing board.
		cells = new ArrayList<ArrayList<BoardCell>>();
		for (i = 0; i < size; i++) {
			cells.add(new ArrayList<BoardCell>());
			for (j = 0; j < size; j++) {
				cells.get(i).add(new BoardCell());
			}
		}
		
		//Placing ships on the board.
		for (Ship ship : ships) {
			int shipX = ship.getX()-1,
				shipY = ship.getY()-1;

			for (i = shipY; i < shipY + ship.getShipType().getHeight(); i++) {
				for (j = shipX; j < shipX + ship.getShipType().getWidth(); j++) {					
					cells.get(i).get(j).setIsShip();
				}
			}
		}
		
		//Setting state of the cells.
		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				if (this.state.get(i).charAt(j) != '.') {
					if (cells.get(i).get(j).getIsShip()) {
						setHitCounter();
						cells.get(i).get(j).setCellValue('☠');
					}
					else {
						cells.get(i).get(j).setCellValue('○');
					}
				}
			}
		}
	}

	public String getState() {
		String result = "";
		for (ArrayList<BoardCell> row : cells) {
			for (BoardCell cell : row) {
				result+= cell.getCellValue() == '~' ? "." : "X";
			}
		}
		return result;
	}


	boolean getIsE() {
		return this.isE;
	}

	void setIsE() {
		this.isE = true;
	}

	String getMsg() {
		return this.msg;
	}

	String getCellStatus() {
		return cellStatus;
	}

	void setCellStatus(String cellStatus) {
		this.cellStatus = cellStatus;
	}

	void setNextTurn(boolean nextTurn) {
		this.nextTurn = nextTurn;
	}

	boolean getNextTurn() {
		return this.nextTurn;
	}

	int getHitCounter() {
		return hitCounter;
	}

	void setHitCounter() {
		this.hitCounter += 1;
	}
	void getInput(String c) {
		int x, y;
		if (!c.equals(by)) {
			x = Integer.parseInt((c.substring(0, c.indexOf(','))));
			y = Integer.parseInt((c.substring(c.indexOf(',') + 1)));
			if (x >= 1 && x <= size && y >= 1 && y <= size) {

				if (cells.get(x - 1).get(y - 1).getCellValue() == '~') {
					if (cells.get(x - 1).get(y - 1).getIsShip()) {
						cells.get(x - 1).get(y - 1).setCellValue('☠');
						setCellStatus("success! Nice mate!");
						setHitCounter();
					} else {
						cells.get(x - 1).get(y - 1).setCellValue('○');
						setCellStatus("miss! Maybe next time, mate...");

					}
					setNextTurn(true);
				} else {
					setCellStatus("  You have already been here, mate! Go and search on other waters!");
					setNextTurn(false);
				}

			} else {
				throw new OutOfBoardException("Please, don't sail out of the sea!");
			}

		} else {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (cells.get(i).get(j).getIsShip()) {
						cells.get(i).get(j).setCellValue('☠');
						setHitCounter();
					} else {
						cells.get(i).get(j).setCellValue('○');
					}
				}
			}
			setNextTurn(true);
			setIsE();
		}
	}

	public String toString() {
		String boardString = "";
		for (ArrayList<BoardCell> row : cells) {
			
			for (BoardCell c : row) {
				if (boardString.length() % 10 == 0) {
					boardString += c.getCellValue();
				}
				else{
					boardString += " " + c.getCellValue();
				}
			}
			boardString += "\n";
		}
		boardString = "\n" + boardString;
		return boardString.substring(0, boardString.length() - 1);
	}
}
