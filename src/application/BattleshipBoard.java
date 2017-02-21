package application;

import java.util.ArrayList;

public class BattleshipBoard {

	private int rowCount, colCount, hitCounter = 0;
	private String msg, by, cellStatus;
	private boolean nextTurn = true, isE = false;
	private ArrayList<ArrayList<BoardCell>> cells;

	public BattleshipBoard(int row, int col, String init) {
		this.rowCount = row;
		this.colCount = col;
		EasterEgg e = new EasterEgg();
		by = e.by();
		msg = e.msg();
		cells = new ArrayList<ArrayList<BoardCell>>();
		for (int i = 1; i <= rowCount; i++) {
			cells.add(new ArrayList<BoardCell>());
			for (int j = 1; j <= colCount; j++) {
				BoardCell cell = new BoardCell();
				if (init.substring((i - 1) * 10, (i - 1) * 10 + 10).substring(j - 1).charAt(0) == 'X') {
					cell.setIsShip();
				}
				cells.get(i - 1).add(cell);
			}
		}
	}

	public boolean getIsE() {
		return this.isE;
	}

	public void setIsE() {
		this.isE = true;
	}

	public String getMsg() {
		return this.msg;
	}

	public String getCellStatus() {
		return cellStatus;
	}

	public void setCellStatus(String cellStatus) {
		this.cellStatus = cellStatus;
	}

	public void setNextTurn(boolean nextTurn) {
		this.nextTurn = nextTurn;
	}

	public boolean getNextTurn() {
		return this.nextTurn;
	}

	public int getHitCounter() {
		return hitCounter;
	}

	public void setHitCounter() {
		this.hitCounter += 1;
	}

	public void getInput(String c) {
		int x, y;
		if (!c.equals(by)) {
			x = Integer.parseInt((c.substring(0, c.indexOf(','))));
			y = Integer.parseInt((c.substring(c.indexOf(',') + 1)));
			if (x >= 1 && x <= rowCount && y >= 1 && y <= colCount) {

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
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < colCount; j++) {
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
		String boardString = "\n";
		for (ArrayList<BoardCell> row : cells) {
			boardString += "|";
			for (BoardCell c : row) {
				boardString += " " + c.getCellValue();
			}
			boardString += "|\n";
		}
		return boardString.substring(0, boardString.length() - 1);
	}
}
