package unoGame;

public class Reverse extends Card {
	public Reverse(String color){
		super(color,"Reverse");
	}
	
	public boolean compatibleWith(Card lastest) {
		return super.equals(lastest);
	}
	
	public void action(){
		Game.reverse();
	}
}
