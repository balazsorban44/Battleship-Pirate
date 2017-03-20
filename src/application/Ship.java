package application;

public class Ship {
	private ShipType shipType;
	private int x, y;
	
	Ship(String type,String ship) {
		this.shipType = new ShipType(type);
		this.x = Integer.parseInt(ship.substring(1, 2));
		this.y = Integer.parseInt(ship.substring(2));
	}
	
	ShipType getShipType() {
		return shipType;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "" + shipType + " "+shipType.getType() + x + y;
	}
	
	
	
}
