package application;

public class BoardCell {

	private boolean isShip = false;
	private char cellValue = '~';

	public boolean getIsShip() {
		return isShip;
	}

	public void setIsShip() {
		this.isShip = true;
	}

	public char getCellValue() {
		return cellValue;
	}

	public void setCellValue(char cellValue) {
		this.cellValue = cellValue;
	}
}
