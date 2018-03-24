package unoGame;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.InputMismatchException;

public class Game {
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<Card> pile = new ArrayList<Card>();
	private static ArrayList<Card> deck = new ArrayList<Card>();
	
	private static int currentPlayerPosition = 0;
	private static int gameDirection = 1;
	private static Player currentPlayer;
	public static Card lastestCard;
	public static Card selectedCard;

	public static void main(String[] args) throws InputMismatchException		//MAIN FUNCTION
	{

		initializeGame();
		gameplay();
	}


	@SuppressWarnings("resource")
	public static void initializeGame()
	{
		
		Scanner inputPlayer = new Scanner(System.in);
		int numOfPlayer = 0;
		
		boolean continueInput = true;
		do{
			try{						//ASK USER HOW MANY PLAYER WILL PLAY
				System.out.println("Welcome to UNO GAME !!! \nEnter No. of Player(2-4): ");
				numOfPlayer = inputPlayer.nextInt();
				if(numOfPlayer < 2 || numOfPlayer > 4) System.out.println("INVALID NUMBER OF PLAYER ! MIN:2 player MAX:4 player");
				else{
					continueInput = false;
					inputPlayer.nextLine();
				}
			}
			catch(InputMismatchException ex){
				System.out.println("Try again.( Incorrect input: an integer is required )");
				inputPlayer.nextLine();
			}
		}while(continueInput);
		
		for(int i=0; i < numOfPlayer; i++){
			String playerName;
			System.out.println("Player "+ (i+1) +" Name :");
			playerName = inputPlayer.nextLine();
			
			players.add(new Player(playerName));
		}


		initializeDeck();
		Collections.shuffle(deck);
		distributeCard();
		deck.add(new Poison("RED"));	//ADD ADDITION SPECIAL CARD MANUALLY
		deck.add(new Poison("BLUE"));
		deck.add(new Poison("GREEN"));
		deck.add(new Poison("YELLOW"));
		Collections.shuffle(deck);
		pile.add(deck.get(0));
		deck.remove(0);

	}

	private static void gameplay(){ 

		Scanner input = new Scanner(System.in);
		int move = 0;

		currentPlayer = getCurrentPlayer();	
		lastestCard = pile.get(pile.size()-1);
		while(lastestCard instanceof WildCardDrawFour || lastestCard instanceof WildCard){ //IF STARTING CARD IS WILD+4 OR WILD CHANGE THE STARTING CARD
			pile.add(deck.get(0));
			deck.remove(0);
			lastestCard = pile.get(pile.size()-1);
		}

		do{			
			System.out.print(lastestCard);
			currentPlayer = getCurrentPlayer();

			deckSupply();
			System.out.print(currentPlayer);	//PRINT OUT THE CURRENT PLAYER NAME AND CARDS

			boolean continueInput = true;
			do{
				try{
					System.out.print("What to do? Select card or 0 to draw a card : ");		//ASK PLAYER FOR ACTION -DRAW- OR -SELECT-

					move = input.nextInt();
					if(move < 0 || move > currentPlayer.handsNo()){
						System.out.println("CARD No."+ move +" NOT FOUND ! ENTER BETWEEN 1 -TO- " + currentPlayer.handsNo());
					}
					else continueInput = false;
				}
				catch(InputMismatchException ex){
					System.out.println("Try again.( Incorrect input: an integer is required )");
					input.nextLine();
				}
			}while(continueInput);

			if(move==0){
				deckSupply();		//CHECK IF THE DECK IS ENOUGH FOR DRAW
				currentPlayer.drawCard(deck);
				System.out.print("----------------------------------------------------------------------\n");
			}
			else if(move>0 && move <= currentPlayer.handsNo()){
				selectedCard = currentPlayer.selectCard(move); //CARD SELECT BY PLAYER

				if(!selectedCard.compatibleWith(lastestCard)){	//IF SELECTED WRONG CARD
					System.out.print("\nINVALID INPUT !!! \n");
				}
				else{
					lastestCard = selectedCard;		//PROCEED IF CARD CORRECT
					pile.add(selectedCard);
					currentPlayer.removeCard(move);
					if(currentPlayer.handsNo() !=0) lastestCard.action();	
					System.out.print("----------------------------------------------------------------------\n");
				}
			}			
		}while(currentPlayer.handsNo() != 0);	//END LOOP IF CURRENT PLAYER HAVE NO CARD
		
		System.out.println("\n************************************************************\n");
		System.out.println("END GAME !!! \n WINNER : " + currentPlayer.getName());
		System.out.println("\n************************************************************\n");
		System.out.println("Please press Enter to terminate...");
		input.nextLine();
		input.close();
		System.exit(0);
	}

	public static Player getCurrentPlayer() {
		return players.get(currentPlayerPosition);
	}

	public static Player getNextPlayer(){
		return players.get((currentPlayerPosition + gameDirection + players.size()) % players.size());
	}

	public static void initializeDeck(){
		Card cardCreate = null;

		for(int i = 0; i < 52; i++){ 

			if(i/13 == 0){ //RED CARD
				if(i%13 == 10) cardCreate = new Skip("RED");
				else if(i%13 == 11) cardCreate = new Reverse("RED");
				else if(i%13 == 12) cardCreate = new DrawTwo("RED");
				else cardCreate = new Card("RED",Integer.toString(i%10));
			}
			else if(i/13 == 1){
				if(i%13 == 10) cardCreate = new Skip("YELLOW");
				else if(i%13 == 11) cardCreate = new Reverse("YELLOW");
				else if(i%13 == 12) cardCreate = new DrawTwo("YELLOW");
				else cardCreate = new Card("YELLOW",Integer.toString(i%10));
			}
			else if(i/13 == 2){
				if(i%13 == 10) cardCreate = new Skip("GREEN");
				else if(i%13 == 11) cardCreate = new Reverse("GREEN");
				else if(i%13 == 12) cardCreate = new DrawTwo("GREEN");
				else cardCreate = new Card("GREEN",Integer.toString(i%10));
			}
			else if(i/13 == 3){
				if(i%13 == 10) cardCreate = new Skip("BLUE");
				else if(i%13 == 11) cardCreate = new Reverse("BLUE");
				else if(i%13 == 12) cardCreate = new DrawTwo("BLUE");
				else cardCreate = new Card("BLUE",Integer.toString(i%10));
			}
			deck.add(cardCreate);
			cardCreate = null;
		}

		deck.add(new WildCard());	//wild card
		deck.add(new WildCard());
		deck.add(new WildCardDrawFour());	//wild card +4
		deck.add(new WildCardDrawFour());
		
	}

	public static void distributeCard(){
		Player cPlayer;
		for(int i = 0; i < players.size();i++){
			cPlayer = players.get(i);
			for(int cardsInitial= 0; cardsInitial < 5; cardsInitial++){	//each player 5 cards		
				cPlayer.drawCard(deck);
			}
		}
	}

	public static boolean deckSupply(){
		if(deck.size()<10 && pile.size() > 1){	//prevent deck not enough card to draw
			Card lastestCard;
			lastestCard = pile.get(0);
			pile.remove(0);
			deck = pile;
			Collections.shuffle(deck);
			pile.clear();
			pile.add(lastestCard);
			return true;
		}
		else return false;
	}

	public static void skip(){
		endTurn();
		System.out.print("\nPLAYER " + getCurrentPlayer().getName() + " TURN SKIPPED !\n");
		endTurn();
	}

	public static void reverse(){
		gameDirection = gameDirection*(-1);
		endTurn();
		System.out.print("\nGAME REVERSE !!! "+ getCurrentPlayer().getName() +" TURN !!!\n");
	}

	public static void endTurn(){
		currentPlayerPosition = (currentPlayerPosition + gameDirection + players.size()) % players.size();
	}

	public static void drawCard(){
		getCurrentPlayer().drawCard(deck);
	}

	@SuppressWarnings("resource")
	public static void colorSelection(){
		String[] colors={"BLUE","GREEN","RED","YELLOW"};
		int colorSelected = 0;
		Scanner colorInput = new Scanner(System.in);
		boolean continueInput = true;
		do{
			try{
				System.out.print("Please Select Color to Paint: \n1.BLUE \n2.GREEN \n3.RED \n4.YELLOW \n ANS: ");
				colorSelected = colorInput.nextInt();

				continueInput = false;
			}
			catch(InputMismatchException ex){
				System.out.println("Try again.( Incorrect input: an integer is required )");
				colorInput.nextLine();
			}
		}while(continueInput);

		lastestCard = null;
		lastestCard = new Card(colors[colorSelected - 1], "");
	}
}


