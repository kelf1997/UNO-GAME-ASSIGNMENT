package unoGame;

public class DrawTwo extends Card {
	public DrawTwo(String color){
		super(color,"DrawTwo");
	}
	
	public boolean compatibleWith(Card lastest) {
		return super.equals(lastest);
	}
	
	public void action(){
		Game.endTurn();
		Game.drawCard();
		Game.drawCard();
		System.out.print("\nPLAYER " + Game.getCurrentPlayer().getName() + " DRAWED 2 CARDS !\n");
		Game.endTurn();
	}
}


