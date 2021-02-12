package jhaturanga.model.game;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public final boolean isOver() {
        return this.isDraw() || this.players.stream().filter(x -> this.isWinner(x)).findAny().isPresent();
    }

    @Override
    public final boolean isDraw() {
        return this.players.stream().filter(x -> this.isBlocked(x) && !this.isInCheck(x)).findAny().isPresent();
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

        return supportBoard.stream()
                // Get only the pieces of the player
                .filter(i -> i.getPlayer().equals(player)).filter(x -> {
                    // Save a copy of the piece's position
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

}
