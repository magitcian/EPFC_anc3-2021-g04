package mvvm.commands;

import model.Card;

public class CardMoveToUp implements Command {

    private final Card card;

    public CardMoveToUp(Card card) {
        this.card = card;
    }

    @Override
    public void execute() {
        System.out.println(card.getPositionInColumn()); // test
        this.card.changePositionInColumn(-1, 0);
    }

    @Override
    public void unexecute() {
        this.card.changePositionInColumn(1, 0);
    }

    @Override
    public String toString() {
        return "Déplacement de la carte \"" + this.card + "\" vers le haut";
    }

}
