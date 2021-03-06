package mvvm.commands;

import model.Board;


public class BoardChangeTitle implements Command{

    private final Board board;
    private final String oldTitle;
    private final String newTitle;

    public BoardChangeTitle(Board board, String newTitle) {
        this.board = board;
        oldTitle = board.toString();
        this.newTitle = newTitle;
    }
    @Override
    public void execute() {
        board.changeTitle(newTitle);
    }

    @Override
    public void unexecute() {
        board.changeTitle(oldTitle);
    }

    @Override
    public String toString() {
        return "Changement de titre du board \"" + oldTitle + "\" vers \"" + newTitle + "\"";
    }

}