package unoGame;

public class Card implements Comparable<Card>{
	private  String color;
	private String value;
	
	public Card(){
		color = null;
		value = null;
	}
	
	public Card(String newColor,String newValue){
		color = newColor;
		value = newValue;
	}
	
	public String getColor(){
		return color;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setColor(String color) {
		this.color = color;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString(){
		return this.color + "_" + this.value;
	}
	
	public void action(){
		Game.endTurn();
	}

	@Override
	public int compareTo(Card compareCard)
    {
		int nameCompare = color.toUpperCase().compareTo(compareCard.color.toUpperCase());
		if(nameCompare == 0) return value.compareTo(compareCard.value);
		else return nameCompare;
	}

	public boolean compatibleWith(Card lastest) {
		return this.equals(lastest);
	}
	
	public boolean equals(Card lastest) {
		return (this.color.equals(lastest.color) || this.value.equals(lastest.value));
	}
}