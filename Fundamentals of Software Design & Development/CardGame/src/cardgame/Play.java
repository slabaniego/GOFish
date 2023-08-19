import java.util.Scanner;

public class Play {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Deck deck = new Deck();
        deck.shuffle();

        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        // Distribute initial cards to players
        for (int i = 0; i < 7; i++) {
            player1.drawCard(deck.drawCard());
            player2.drawCard(deck.drawCard());
        }

        Card topCard = deck.drawCard();
        System.out.println("Top card: " + topCard);

        Player currentPlayer = player1;
        boolean gameEnded = false;

        while (!gameEnded) {
            System.out.println(currentPlayer.getName() + "'s turn:");
            System.out.println("Your hand: " + currentPlayer.getHand());

            // Check if the player can play a card
            boolean canPlay = false;
            for (Card card : currentPlayer.getHand()) {
                if (card.getColor().equals(topCard.getColor()) || card.getValue().equals(topCard.getValue())) {
                    canPlay = true;
                    break;
                }
            }

            if (canPlay) {
                // Player can play a card
                System.out.println("Enter the index of the card to play:");
                int cardIndex = scanner.nextInt();
                Card playedCard = currentPlayer.playCard(cardIndex);

                if (playedCard != null && (playedCard.getColor().equals(topCard.getColor()) || playedCard.getValue().equals(topCard.getValue()))) {
                    System.out.println(currentPlayer.getName() + " played: " + playedCard);
                    topCard = playedCard;

                    if (currentPlayer.getHand().isEmpty()) {
                        gameEnded = true;
                        System.out.println(currentPlayer.getName() + " wins!");
                    }
                } else {
                    System.out.println("Invalid card. Try again.");
                }
            } else {
                // Player must draw a card
                System.out.println("You don't have a playable card. Drawing a card...");
                Card drawnCard = deck.drawCard();

                if (drawnCard != null) {
                    System.out.println("You drew: " + drawnCard);
                    currentPlayer.drawCard(drawnCard);
                } else {
                    System.out.println("No cards left in the deck.");
                    gameEnded = true;
                }
            }

            // Switch to the other player's turn
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }

        System.out.println("Game over.");
    }
}
