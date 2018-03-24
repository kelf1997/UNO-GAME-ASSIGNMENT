package unoGame;

public class Skip extends Card {
	
	public Skip(String color){
		super(color,"Skip");
	}
    
	public boolean compatibleWith(Card lastest) {
		return super.equals(lastest);
	}
	
	public void action(){
		Game.skip();
	}
}
