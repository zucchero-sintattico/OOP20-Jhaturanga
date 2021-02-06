package jhaturanga.model.game;

import java.util.Collection;
import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public class NormalGameController implements GameController {

    private final Board board;
    private final PieceMovementStrategyFactory pieceMovementStrategies;
    private final Collection<Player> players;

    public NormalGameController(final Board board, final PieceMovementStrategyFactory pieceMovementStrategies,
            final Collection<Player> players) {
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
        return this.players.stream().filter(x -> this.isBlocked(x) && !this.isCheck(x)).findAny().isPresent();
    }

    @Override
    public final boolean isCheck(final Player player) {
        final Piece king = this.board.getBoardState().stream()
                .filter(i -> i.getPlayer().equals(player) && i.getType().equals(PieceType.KING)).findAny().get();

        return this.board
                .getBoardState().stream().filter(i -> !i.getPlayer().equals(player)).filter(x -> this.pieceMovementStrategies
                        .getPieceMovementStrategy(x).getPossibleMoves(this.board).contains(king.getPiecePosition()))
                .findAny().isPresent();

    }

    @Override
    public final boolean isWinner(final Player player) {
        return this.players.stream().filter(x -> !x.equals(player)).filter(x -> this.isCheck(x) && this.isBlocked(x)).findAny()
                .isPresent();
    }

    private boolean isBlocked(final Player player) {
        return this.board.getBoardState().stream().filter(i -> i.getPlayer().equals(player)).filter(x -> {
            final BoardPosition oldPosition = new BoardPositionImpl(x.getPiecePosition().getX(), x.getPiecePosition().getY());
            final Set<BoardPosition> possibleDestinations = this.pieceMovementStrategies.getPieceMovementStrategy(x)
                    .getPossibleMoves(this.board);

            for (final BoardPosition pos : possibleDestinations) {
                x.setPosition(pos);
                if (!this.isCheck(player)) {
                    return true;
                }
                x.setPosition(oldPosition);
            }

            return false;

        }).findAny().isEmpty();
    }

}
