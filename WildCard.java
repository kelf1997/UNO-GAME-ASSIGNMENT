package unoGame;

public class WildCard extends Card {
	public WildCard(){
		super("ANY","WildCard");
	}
	
	public boolean compatibleWith(Card lastest) {
		return true;
	}
	
	public void action(){
		Game.colorSelection();
		Game.endTurn();
	}
}
