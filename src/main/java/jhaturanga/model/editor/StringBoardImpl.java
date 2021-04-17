package jhaturanga.model.editor;

public final class StringBoardImpl implements StringBoard {

    private final String board;
    private final int columns;
    private final int rows;

    public StringBoardImpl(final String board, final int columns, final int rows) {
        this.board = board;
        this.columns = columns;
        this.rows = rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBoard() {
        return this.board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRows() {
        return this.rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumns() {
        return this.columns;
    }

}
