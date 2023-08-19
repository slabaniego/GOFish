import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class UnoCard {
    private final String color;
    private final String value;

    public UnoCard(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return color + " " + value;
    }

    public void setColor(String newColor) {
    }
}

class UnoPlayer {
    private final List<UnoCard> hand;

    public UnoPlayer() {
        hand = new ArrayList<>();
    }

    public List<UnoCard> getHand() {
        return hand;
    }
}

public class UnoGame {
    private static final List<String> colors = Arrays.asList("Red", "Yellow", "Green", "Blue");
    private static final List<String> values = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Skip", "Reverse", "Draw Two");
    private static final List<String> specialCards = Arrays.asList("Skip", "Reverse", "Draw Two");

    private List<UnoCard> deck;
    private List<UnoCard> discardPile;
    private UnoPlayer player;
    private UnoPlayer opponent;
    private String currentPlayer;

    public UnoGame() {
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
        player = new UnoPlayer();
        opponent = new UnoPlayer();
        currentPlayer = "player";
    }

    private void initializeDeck() {
        for (String color : colors) {
            for (String value : values) {
                deck.add(new UnoCard(color, value));
            }
            for (String specialCard : specialCards) {
                deck.add(new UnoCard(color, specialCard));
                deck.add(new UnoCard(color, specialCard));
            }
        }
        deck.add(new UnoCard("Wild", "Wild"));
        deck.add(new UnoCard("Wild", "Wild Draw Four"));
        Collections.shuffle(deck, new Random());
    }

    private boolean canPlay(UnoCard card, UnoCard topCard) {
        return card.getColor().equals(topCard.getColor()) || card.getValue().equals(topCard.getValue()) || card.getColor().equals("Wild");
    }

    private String chooseWildColor() {
        System.out.println("Choose a color for the Wild card:");
        for (int i = 0; i < colors.size(); i++) {
            System.out.println((i + 1) + ": " + colors.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        return colors.get(choice - 1);
    }

    private void displayGameState() {
        UnoCard topCard = discardPile.get(discardPile.size() - 1);
        System.out.println("Top Card: " + topCard.getColor() + " " + topCard.getValue());
        System.out.print("Your Hand: ");
        for (UnoCard card : player.getHand()) {
            System.out.print(card + ", ");
        }
        System.out.println();
    }

    public void playGame() {
        initializeDeck();
        for (int i = 0; i < 7; i++) {
            player.getHand().add(deck.remove(deck.size() - 1));
            opponent.getHand().add(deck.remove(deck.size() - 1));
        }
        discardPile.add(deck.remove(deck.size() - 1));

        while (true) {
            displayGameState();

            if ("player".equals(currentPlayer)) {
                boolean validPlay = false;
                for (int i = 0; i < player.getHand().size(); i++) {
                    UnoCard card = player.getHand().get(i);
                    if (canPlay(card, discardPile.get(discardPile.size() - 1))) {
                        validPlay = true;
                        System.out.println("Your play: " + card);
                        discardPile.add(player.getHand().remove(i));

                        if (player.getHand().size() == 1) {
                            System.out.println("UNO!");
                        }

                        if (player.getHand().isEmpty()) {
                            System.out.println("Player wins!");
                            return;
                        }

                        if ("Wild".equals(card.getValue())) {
                            String newColor = chooseWildColor();
                            discardPile.get(discardPile.size() - 1).setColor(newColor);
                        }

                        break;
                    }
                }

                if (!validPlay) {
                    System.out.println("No valid plays. Drawing a card...");
                    player.getHand().add(deck.remove(deck.size() - 1));
                }

                currentPlayer = "opponent";
            } else {
                for (int i = 0; i < opponent.getHand().size(); i++) {
                    UnoCard card = opponent.getHand().get(i);
                    if (canPlay(card, discardPile.get(discardPile.size() - 1))) {
                        System.out.println("Opponent's play: " + card);
                        discardPile.add(opponent.getHand().remove(i));

                        if (opponent.getHand().size() == 1) {
                            System.out.println("Opponent says UNO!");
                        }

                        if (opponent.getHand().isEmpty()) {
                            System.out.println("Opponent wins!");
                            return;
                        }

                        if ("Wild".equals(card.getValue())) {
                            String newColor = colors.get(new Random().nextInt(colors.size()));
                            discardPile.get(discardPile.size() - 1).setColor(newColor);
                        }

                        break;
                    }
                }

                if (currentPlayer.equals("opponent")) {
                    System.out.println("Opponent has no valid plays. Drawing a card...");
                    opponent.getHand().add(deck.remove(deck.size() - 1));
                }

                currentPlayer = "player";
            }
        }
    }

    public static void main(String[] args) {
        UnoGame game = new UnoGame();
        game.playGame();
    }
}