package application;

public class ShipType {
 private char type;
 private int width, height;

 ShipType(String type) {
	 this.type = type.substring(0, 1).charAt(0);
	 this.width = Integer.parseInt(type.substring(1, 2));
	 this.height = Integer.parseInt(type.substring(2));
 }

int getWidth() {
	return width;
}

int getHeight() {
	return height;
}

char getType() {
	return type;
}

@Override
public String toString() {
	return "" +type+width+height;
}
 
 
}