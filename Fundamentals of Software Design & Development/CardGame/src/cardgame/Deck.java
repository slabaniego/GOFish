import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck() {
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "draw_2", "reverse", "skip", "wild", "wild_draw_4"};
        
        for (String color : colors) {
            for (String value : values) {
                cards.add(new Card(color, value));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null; // No cards left
        }
        return cards.remove(0);
    }

    public int getSize() {
        return cards.size();
    }
}
