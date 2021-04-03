package jhaturanga.model.match;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.Game;
import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.history.History;
import jhaturanga.model.history.HistoryImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.movement.manager.MovementManager;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerPair;
import jhaturanga.model.timer.Timer;

public final class MatchImpl implements Match {

    private final String matchID;
    private final Game game;
    private final Timer timer;
    private final PlayerPair players;
    private final History history;
    private final Iterator<Player> playersTurnIterator;

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
    public GameType getType() {
        return this.game.getType();
    }

    @Override
    public Timer getTimer() {
        return this.timer;
    }

    @Override
    public PlayerPair getPlayers() {
        return this.players;
    }

    @Override
    public void start() {
        this.timer.start(this.playersTurnIterator.next());
    }

    @Override
    public MovementResult move(final PieceMovement movement) {
        final MovementResult result = this.game.getMovementManager().move(movement);
        if (!result.equals(MovementResult.INVALID_MOVE)) {
            this.history.addMoveToHistory();
            this.updateTimerStatus(movement.getPieceInvolved().getPlayer());
        }
        return result;
    }

    private void updateTimerStatus(final Player playerForOptionalTimeGain) {
        if (!this.getMatchStatus().equals(MatchStatus.ACTIVE)) {
            this.timer.stop();
        }
        this.timer.addTimeToPlayer(playerForOptionalTimeGain, this.timer.getIncrement());
        this.timer.switchPlayer(this.playersTurnIterator.next());
    }

    @Override
    public MatchStatus getMatchStatus() {
        return this.timer.getPlayerWithoutTime().map(e -> MatchStatus.ENDED_FOR_TIME)
                .orElseGet(() -> this.game.getController().getGameStatus(this.getMovementManager().getPlayerTurn()));
    }

    @Override
    public Optional<Player> getWinner() {
        return this.timer.getPlayerWithoutTime().isPresent()
                ? this.players.stream().filter(x -> !x.equals(this.timer.getPlayerWithoutTime().get())).findAny()
                : this.players.stream().filter(this.game.getController()::isWinner).findAny();

    }

    @Override
    public Board getBoardAtIndexFromHistory(final int index) {
        return this.history.getBoardAtIndex(index);
    }

    @Override
    public Board getBoard() {
        return this.game.getController().boardState();
    }

    @Override
    public GameController getGameController() {
        return this.game.getController();
    }

    @Override
    public List<Board> getBoardFullHistory() {
        return this.history.getAllBoards();
    }

    @Override
    public MovementManager getMovementManager() {
        return this.game.getMovementManager();
    }

    @Override
    public Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return this.getMovementManager().filterOnPossibleMovesBasedOnGameController(piece);
    }

}
