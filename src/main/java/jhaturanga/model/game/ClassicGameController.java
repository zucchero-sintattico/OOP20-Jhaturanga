package jhaturanga.model.game;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public class ClassicGameController implements GameController {

    private final Board board;
    private final PieceMovementStrategyFactory pieceMovementStrategies;
    private final List<Player> players;

    public ClassicGameController(final Board board, final PieceMovementStrategyFactory pieceMovementStrategies,
            final List<Player> players) {
        this.board = board;
        this.pieceMovementStrategies = pieceMovementStrategies;
        this.players = players;
    }

    @Override
    public final synchronized boolean isOver() {
        return this.isDraw() || this.players.stream().filter(x -> this.isWinner(x)).findAny().isPresent();
    }

    @Override
    public final boolean isDraw() {
        return this.insufficientMaterialToWin()
                || this.players.stream().filter(x -> this.isBlocked(x) && !this.isInCheck(x)).findAny().isPresent();
    }

    /**
     * Here are the piece combinations that lead to a draw by insufficient material:
     * [King vs. King] or [King and Bishop vs. King] or [King and Knight vs. King]
     * or [King and Bishop vs. King and Bishop]
     * 
     * @return True if the board doesn't contain enough pieces to legally let one of
     *         the two players win
     */
    protected boolean insufficientMaterialToWin() {
        final Supplier<Stream<Piece>> boardStreamWithoutKings = () -> this.board.getBoardState().stream()
                .filter(i -> !i.getType().equals(PieceType.KING));

        return boardStreamWithoutKings.get().count() == 0
                || this.areThereLessThanOrEqualTwoNonKingPieces(boardStreamWithoutKings)
                        && boardStreamWithoutKings.get().allMatch(i -> i.getType().equals(PieceType.BISHOP))
                        && boardStreamWithoutKings.get().map(i -> i.getPlayer()).distinct().count() == 2
                || boardStreamWithoutKings.get().count() == 1
                        && boardStreamWithoutKings.get().filter(i -> i.getType().equals(PieceType.KNIGHT)).count() == 1
                || boardStreamWithoutKings.get().filter(i -> i.getType().equals(PieceType.BISHOP)).count() == 1;
    }

    private boolean areThereLessThanOrEqualTwoNonKingPieces(final Supplier<Stream<Piece>> boardStreamWithoutKings) {
        return boardStreamWithoutKings.get().count() > 0 && boardStreamWithoutKings.get().count() <= 2;
    }

    @Override
    public final boolean isInCheck(final Player player) {
        final Optional<Piece> king = this.board.getBoardState().stream()
                .filter(i -> i.getPlayer().equals(player) && i.getType().equals(PieceType.KING)).findAny();
        return king.isPresent()
                && this.board.getBoardState().stream().filter(i -> !i.getPlayer().equals(player))
                        .filter(x -> this.pieceMovementStrategies.getPieceMovementStrategy(x)
                                .getPossibleMoves(this.board).contains(king.get().getPiecePosition()))
                        .findAny().isPresent();

    }

    @Override
    public final boolean isWinner(final Player player) {
        return this.players.stream().filter(x -> !x.equals(player)).filter(x -> this.isInCheck(x) && this.isBlocked(x))
                .findAny().isPresent();
    }

    private boolean isBlocked(final Player player) {
        // We need to create a defensive copy of the board to avoid concurrent
        // modification
        final Set<Piece> supportBoard = new HashSet<>(this.board.getBoardState());

        return supportBoard.stream().filter(i -> i.getPlayer().equals(player)).filter(x -> {

            final BoardPosition oldPiecePosition = new BoardPositionImpl(x.getPiecePosition());
            final Set<BoardPosition> piecePossibleDestinations = this.pieceMovementStrategies
                    .getPieceMovementStrategy(x).getPossibleMoves(this.board);

            for (final BoardPosition pos : piecePossibleDestinations) {

                final Optional<Piece> oldPiece = this.board.getPieceAtPosition(pos);

                if (oldPiece.isPresent()) {
                    this.board.remove(oldPiece.get());
                }

                x.setPosition(pos);

                if (!this.isInCheck(player)) {

                    x.setPosition(oldPiecePosition);

                    if (oldPiece.isPresent()) {
                        this.board.add(oldPiece.get());
                    }
                    return true;
                }

                x.setPosition(oldPiecePosition);

                if (oldPiece.isPresent()) {
                    this.board.add(oldPiece.get());
                }
            }

            return false;
        }).findAny().isEmpty();

    }

    @Override
    public final Board boardState() {
        return this.board;
    }

    @Override
    public final List<Player> getPlayers() {
        return this.players;
    }

    @Override
    public final PieceMovementStrategyFactory getPieceMovementStrategyFactory() {
        return this.pieceMovementStrategies;
    }

}
