package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Column {
    private Board board;
    private StringProperty title;
    private final ObservableList<Card> listCards = FXCollections.observableArrayList();

    Column(String title, Board board){
        this.title= new SimpleStringProperty(title);
        this.board = board;
        //listCards.add(null);
    }
    public StringProperty getTitle(){
        return this.title;
    }

    public void addCard(Card c){
        this.listCards.add(c);
    }

    public void addCard() {
        Card cardToAdd = new Card("Card", this); //new Card("Card " + (listCards.size() + 1), this);
        this.addCard(cardToAdd);
    }

    public Board getBoard(){
        return this.board;
    }

    public int getPosition(){
        return board.getColumnPosition(this);
    }

    public void removeCard(Card c){
        this.listCards.remove(c);
    }

    public ObservableList<Card> getCards() {
        return FXCollections.unmodifiableObservableList(listCards);
    }

    public void changePositioninBoard(int pos){
        this.board.changeColumnPosition(this, pos);
    }

    //TODO: à supprimer dès que sera changer dans les cartes
    public BooleanProperty isLastInBoard(){
        return new SimpleBooleanProperty(this.board.isColumnLast(this));
    }
    public BooleanProperty isFirstInBoard(){
        return new SimpleBooleanProperty(this.board.isColumnFirst(this));
    }

    public boolean isCardFirst(Card card){
        return getCards().indexOf(card) == 0;
    }

    public boolean isCardLast(Card card){
        return getCards().indexOf(card) == listCards.size() - 1;
    }

    public void delete(){
        board.deleteColumn(this);
    }

    // Called method by CardAdd for unexecute() method
    public void deleteCard(int index){
        this.listCards.remove(index);
    }

    // Called method by CardDelete for execute() method
    public void deleteCard(Card card){
        this.listCards.remove(card);
    }

    // Called method by CardAdd for unexecute() method
    // A card is added at the end of the column so should be remove at the end too
    public int getNumberOfCards(){
        return this.listCards.size();
    }

    // Called method by Card
    public int getCardPosition(Card card){
        return this.listCards.indexOf(card);
    }

    // Called method by CardDelete for unexecute() method
    public void addCardAtPosition(Card card, int pos){
        listCards.add(null);
        for(int i = listCards.size() - 1; i > pos; --i){
            listCards.set(i, listCards.get(i - 1));
        }
        listCards.set(pos, card);
    }

    /*
    Method to change the card position: 4 options:
    (1) up      posCard: -1     posColumn:  0
    (2) right   posCard:  0     posColumn: +1
    (3) down    posCard: +1     posColumn:  0
    (4) left    posCard:  0     posColumn: -1
     */
    public void changeCardPosition(Card card, int posCard, int posColumn) {
        // current card position on the column
        int curPosCard = this.listCards.indexOf(card);
        // if card moves up or down, same column => posColumn == 0
        if (posColumn == 0) {
            // card position to reach (current one -1 if up or +1 if down)
            int posCardToReach = curPosCard + posCard;
            if (posCardToReach >= 0 && posCardToReach < listCards.size()) {
                Card cardToReplace = listCards.set(posCardToReach, card);
                listCards.set(curPosCard, cardToReplace);
            }
        } else {
            // column where the card is
            Column column = card.getColumn();
            // current column position on the board
            int curPosColumn = this.board.getColumns().indexOf(column); //this.board.getColumns().indexOf(card.getColumn());
            // column position to reach (current one -1 if left or +1 if right)
            int posColumnToReach = curPosColumn + posColumn;
            // if to check column position to reach (is in the range)
            if(posColumnToReach >= 0 && posColumnToReach < this.board.getColumns().size()) {
                // column to reach
                Column columnToReach = this.board.getColumns().get(posColumnToReach);
                // set columnToReach as attribute of the card (if not, the card keeps column)
                card.setColumn(columnToReach);
                // add card on the column to reach
                columnToReach.addCard(card);
                // remove card on the column before the move
                column.removeCard(card);
            }
        }
    }

    @Override
    public String toString() {
        return this.title.getValue();
    }

    public void changeTitle(String newTitle){
        title.setValue(newTitle);
    }

}
