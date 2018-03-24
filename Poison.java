package unoGame;

public class Poison extends Card {
	public Poison(String color){
		super(color,"Poison");
	}
	
	public boolean compatibleWith(Card lastest) {
		return true;
	}
	
	public void action(){
		System.out.print("\nPLAYER " + Game.getCurrentPlayer().getName() + " TOUCHED POISON CARD !\n");
		Game.drawCard();
		Game.drawCard();
		System.out.print("\nPLAYER " + Game.getCurrentPlayer().getName() + " DRAWED 2 CARDS !\n");
		Game.endTurn();
	}
}
