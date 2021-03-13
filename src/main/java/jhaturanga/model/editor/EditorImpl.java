package jhaturanga.model.editor;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jhaturanga.commons.Pair;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public class EditorImpl implements Editor {

    private static final int DEFAULT_COLUMNS = 8;
    private static final int DEFAULT_ROWS = 8;
    private Board board;
    private String stringBoard = "";
    private final Map<PieceType, String> pieceTypeToLetter = Map.of(PieceType.KING, "k", PieceType.QUEEN, "q",
            PieceType.BISHOP, "b", PieceType.ROOK, "r", PieceType.PAWN, "p", PieceType.KNIGHT, "n");

    public EditorImpl() {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();
        this.board = boardBuilder.columns(DEFAULT_COLUMNS).rows(DEFAULT_ROWS).build();
    }

    @Override
    public final void addPieceToBoard(final Piece piece) {
        this.board.add(piece);
    }

    @Override
    public final Board getBoardStatus() {
        return this.board;
    }

    @Override
    public final void changeBoardDimensions(final int columns, final int rows) {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();
        this.board = boardBuilder.columns(columns).rows(rows).build();
    }

    @Override
    public final boolean changePiecePosition(final Piece piece, final BoardPosition position) {
        if (this.board.getPieceAtPosition(position).isPresent()) {
            return false;
        }
        piece.setPosition(position);
        return true;
    }

    @Override
    public final void removePiece(final BoardPosition position) {
        this.getBoardStatus().removeAtPosition(position);
    }

    @Override
    public final void createStartingBoard() {
        this.stringBoard = this.board.getBoardState().stream()
                .map(i -> this.getPieceStringCap(i) + i.getPiecePosition().getX() + i.getPiecePosition().getY() + "/")
                .collect(Collectors.joining());

    }

    private String getPieceStringCap(final Piece piece) {
        final String letter = this.pieceTypeToLetter.get(piece.getType());
        return piece.getPlayer().getColor().equals(PlayerColor.WHITE) ? letter.toUpperCase(Locale.ITALIAN)
                : letter.toLowerCase(Locale.ITALIAN);
    }

    @Override
    public final Optional<Pair<String, Pair<Integer, Integer>>> getCreatedBoard() {
        if (this.stringBoard.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(new Pair<>(this.stringBoard, new Pair<>(this.board.getColumns(), this.board.getRows())));
    }

}
