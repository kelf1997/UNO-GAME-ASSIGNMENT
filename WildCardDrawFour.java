package unoGame;

import java.util.InputMismatchException;
import java.util.Scanner;

public class WildCardDrawFour extends Card {
	public WildCardDrawFour(){
		super("ANY","WildCardDrawFour");
	}

	public boolean compatibleWith(Card lastest) {
		return true;
	}

	@SuppressWarnings("resource")
	public void action(){
		Scanner challengeInput = new Scanner(System.in);
		int decision = 0;
		boolean continueInput = true;
		Player current = Game.getCurrentPlayer();
		Player challenger = Game.getNextPlayer();		//GET NEXT PLAYER TO CHALLENGE CURRENT PLAYER
		do{
			try{
				System.out.print("\n"+ challenger.getName()+ " [CHALLENGE? 1.YES 2.NO]"); //PROMPT PLAYER FOR INPUT
				decision = challengeInput.nextInt();
				if(decision == 1 || decision ==2)continueInput = false;
				else System.out.println("INCORRECT INPUT ! ");
			}
			catch(InputMismatchException ex){
				System.out.println("Try again.( Incorrect input: an integer is required )");
				challengeInput.nextLine();
			}
		}while(continueInput);		

		if(decision == 1 && current.containCard(Game.pile.get(Game.pile.size()-2))){ //check with card under the wildCardDrawFour card
			for(int i=0; i<6;i++) Game.drawCard();
			System.out.print("\nPLAYER " + Game.getCurrentPlayer().getName() + " DRAWED 6 CARDS !\n");
		}
		else{
			Game.endTurn();
			for(int i=0; i<4;i++) Game.drawCard();
			System.out.print("\nPLAYER " + Game.getCurrentPlayer().getName() + " DRAWED 4 CARDS !\n");
		}
		
		System.out.println("\n"+current.getName());
		Game.colorSelection();
		Game.endTurn();
	}
}
