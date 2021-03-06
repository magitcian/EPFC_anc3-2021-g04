package mvvm.commands;

import model.Card;

public class CardDelete implements Command {

    private final Card card;
    private final int positionInList;

    public CardDelete(Card card) {
        this.card = card;
        positionInList = card.getPosition();
    }

    @Override
    public void execute() {
        this.card.delete();
    }

    @Override
    public void unexecute() {
        this.card.getColumn().addCardAtPosition(card, positionInList);
    }

    @Override
    public String toString() {
        return "Supprimer la carte \"" + this.card + "\"";
    }

}
