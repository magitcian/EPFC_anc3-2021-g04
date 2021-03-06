package mvvm;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import model.Card;
import model.Column;
import mvvm.commands.*;

public class CardViewModel implements TitleManagement{
    private final Card card;
    private final CommandManager cmdManager;

    public CardViewModel(Card card, CommandManager cmdManager){
        this.card = card;
        this.cmdManager = cmdManager;
    }

    public StringProperty getCardTitleProperty(){
        return card.getTitle();
    }

    public void changePosition(int posCard, int posColumn){
        if(posCard == 0) {
            if(posColumn == 1) {
                cmdManager.addCommand(new CardMoveToRight(this.card));
            } else {
                cmdManager.addCommand(new CardMoveToLeft(this.card));
            }
        } else {
            if(posCard == 1) {
                cmdManager.addCommand(new CardMoveToDown(this.card));
            } else {
                cmdManager.addCommand(new CardMoveToUp(this.card));
            }
        }
        cmdManager.execute();
    }

    public BooleanProperty isUpDisabledProperty() {
        Column column= this.card.getColumn();
        return new SimpleBooleanProperty(column.getCardPosition(card) == 0);
    }

    public BooleanProperty isDownDisabledProperty() {
        Column column = this.card.getColumn();
        return new SimpleBooleanProperty(column.getCardPosition(card)
                == column.getNumberOfCards() - 1);
    }

    public BooleanProperty isLeftDisabledProperty() {
        Column column= this.card.getColumn();
        return new SimpleBooleanProperty(column.getBoard().getColumnPosition(column) == 0);
    }

    public BooleanProperty isRightDisabledProperty() {
        Column column= this.card.getColumn();
        return new SimpleBooleanProperty(column.getBoard().getColumnPosition(column)
                                                == column.getBoard().getNumberOfColumn() - 1);
    }

    @Override
    public void changeTitle(String newTitle) {
        cmdManager.addCommand(new CardChangeTitle(card, newTitle));
        cmdManager.execute();
    }

    public void delete(){
        cmdManager.addCommand(new CardDelete(card));
        cmdManager.execute();
    }
}
