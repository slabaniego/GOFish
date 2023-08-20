import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    public void drawCard(Card card) {
        hand.add(card);
    }

    public Card playCard(int index) {
        if (index < 0 || index >= hand.size()) {
            return null; 
        }
        return hand.remove(index);
    }

    public List<Card> getHand() {
        return hand;
    }

   
}
