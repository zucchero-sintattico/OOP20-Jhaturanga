package jhaturanga.model.match;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.Game;
import jhaturanga.model.game.GameStatus;
import jhaturanga.model.history.History;
import jhaturanga.model.history.HistoryImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.pair.PlayerPair;
import jhaturanga.model.timer.Timer;

public final class MatchImpl implements Match {

    private final String matchID;
    private final Game game;
    private final Timer timer;
    private final PlayerPair players;
    private final History history;
    private final Iterator<Player> playersTurnIterator;

    private Player resignedPlayer;

    public MatchImpl(final Game game, final Timer timer) {
        this.matchID = MatchIdGenerator.getNewMatchId();
        this.game = game;
        this.timer = timer;
        this.players = game.getController().getPlayers();
        this.history = new HistoryImpl(this.getBoard());
        this.playersTurnIterator = Stream.generate(() -> this.players).flatMap(PlayerPair::stream).iterator();
    }

    @Override
    public String getMatchID() {
        return this.matchID;
    }

    @Override
    public PlayerPair getPlayers() {
        return this.players;
    }

    @Override
    public Game getGame() {
        return this.game;
    }

    @Override
    public Timer getTimer() {
        return this.timer;
    }

    @Override
    public History getHistory() {
        return this.history;
    }

    @Override
    public void start() {
        this.timer.start(this.playersTurnIterator.next());
    }

    @Override
    public MovementResult move(final PieceMovement movement) {
        final MovementResult result = this.game.getMovementManager().move(movement);
        if (!result.equals(MovementResult.INVALID_MOVE)) {
            this.history.updateHistory();
            this.updateTimerStatus(movement.getPieceInvolved().getPlayer());
        }
        return result;
    }

    private void updateTimerStatus(final Player player) {
        if (this.getMatchStatus().equals(MatchStatus.ENDED)) {
            this.timer.stop();
        }
        this.timer.addTimeToPlayer(player, this.timer.getIncrement());
        this.timer.switchPlayer(this.playersTurnIterator.next());
    }

    @Override
    public Optional<MatchEndType> getEndType() {
        return this.timer.getPlayersWithoutTime().isPresent() ? Optional.of(MatchEndType.TIMEOUT)
                : this.resignedPlayer != null ? Optional.of(MatchEndType.RESIGN)
                        : this.getWinner().isPresent() ? Optional.of(MatchEndType.CHECKMATE)
                                : this.game.getController()
                                        .getGameStatus(this.game.getMovementManager().getPlayerTurn())
                                        .equals(GameStatus.DRAW) ? Optional.of(MatchEndType.DRAW) : Optional.empty();
    }

    @Override
    public MatchStatus getMatchStatus() {
        return this.getEndType().isPresent() ? MatchStatus.ENDED
                : this.game.getController().isInCheck(this.game.getMovementManager().getPlayerTurn())
                        ? MatchStatus.CHECK
                        : MatchStatus.ACTIVE;
    }

    @Override
    public Optional<Player> getWinner() {
        return this.timer.getPlayersWithoutTime().isPresent()
                ? this.players.stream().filter(x -> !x.equals(this.timer.getPlayersWithoutTime().get())).findAny()
                : this.resignedPlayer != null
                        ? this.players.stream().filter(x -> !x.equals(this.resignedPlayer)).findAny()
                        : this.players.stream().filter(this.game.getController()::isWinner).findAny();

    }

    @Override
    public Board getBoard() {
        return this.game.getController().getBoard();
    }

    @Override
    public Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return this.game.getMovementManager().filterOnPossibleMovesBasedOnGameController(piece);
    }

    @Override
    public void resign(final Player player) {
        this.resignedPlayer = player;
    }

}
