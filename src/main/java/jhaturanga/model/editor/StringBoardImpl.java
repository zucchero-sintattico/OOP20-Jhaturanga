package jhaturanga.model.editor;

public class StringBoardImpl implements StringBoard {

    private final String board;
    private final int columns;
    private final int rows;

    public StringBoardImpl(final String board, final int columns, final int rows) {
        this.board = board;
        this.columns = columns;
        this.rows = rows;
    }

    @Override
    public final String getBoard() {
        return this.board;
    }

    @Override
    public final int getRows() {
        return this.rows;
    }

    @Override
    public final int getColumns() {
        return this.columns;
    }

}