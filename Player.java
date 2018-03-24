package unoGame;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
	private String name;
	private ArrayList<Card> hands = new ArrayList<Card>();
	
	public Player(String newName){  
		name = newName;
	}
	
	public Player(String newName, ArrayList<Card> newHands){ //why need this
		name = newName;
		hands = newHands;
	}
	
	public String toString(){
		Collections.sort(hands);
		return "\n\n" + this.name + " : " + this.hands + "\n"; 
	}
	
	public String getName(){
		return this.name;
	}
	
	public int handsNo(){
		return hands.size();
	}
	
	public Card selectCard(int choice){
		Card choiceOfCard;
		choiceOfCard = hands.get(choice - 1);
		return choiceOfCard;	
	}
	
	public void removeCard(int choice){
		hands.remove(choice - 1);
	}
	
	public void drawCard( ArrayList<Card> newDeck){
		Card cardDrew;
		cardDrew = newDeck.get(0);
		newDeck.remove(0);
		if(cardDrew instanceof Poison){
			newDeck.add(cardDrew);
			cardDrew.action();
		}
		else{
			hands.add(cardDrew);
		}		
	}
	
	public boolean containCard(Card lastest){	//for +4 challenge
		 for(int i=0; i < this.handsNo(); i++){
			 if(this.hands.get(i).equals(lastest)) return true;
		 }
		 return false;
	}
}
