package jhaturanga.model.match;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import jhaturanga.commons.Pair;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.MatchStatusEnum;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.history.History;
import jhaturanga.model.history.HistoryImpl;
import jhaturanga.model.idgenerator.MatchIdGenerator;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementManager;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

public final class MatchImpl implements Match {

    private final String matchID;
    private final GameType gameType;
    private final Timer timer;
    private final Pair<Player, Player> players;
    private final History history;

    public MatchImpl(final GameType gameType, final Timer timer) {
        this.matchID = MatchIdGenerator.getNewMatchId();
        this.gameType = gameType;
        this.timer = timer;
        this.players = gameType.getGameController().getPlayers();
        this.history = new HistoryImpl(this.getBoard());
    }

    @Override
    public String getMatchID() {
        return this.matchID;
    }

    @Override
    public GameTypesEnum getType() {
        return this.gameType.getType();
    }

    @Override
    public Timer getTimer() {
        return this.timer;
    }

    @Override
    public Pair<Player, Player> getPlayers() {
        return this.players;
    }

    @Override
    public void start() {
        this.timer.start(this.players.getX());
    }

    @Override
    public MovementResult move(final Movement movement) {
        final MovementResult result = this.gameType.getMovementManager().move(movement);
        if (!result.equals(MovementResult.INVALID_MOVE)) {
            this.history.addMoveToHistory(
                    new MovementImpl(movement.getPieceInvolved(), movement.getOrigin(), movement.getDestination()));
            this.updateTimerStatus(movement.getPieceInvolved().getPlayer());
        }
        return result;
    }

    private void updateTimerStatus(final Player playerForOptionalTimeGain) {

        if (!this.getMatchStatus().equals(MatchStatusEnum.ACTIVE)) {
            this.timer.stop();
        }

        this.timer.addTimeToPlayer(playerForOptionalTimeGain, this.timer.getIncrement());
    }

    @Override
    public MatchStatusEnum getMatchStatus() {
        if (this.timer.getPlayerWithoutTime().isPresent()) {
            return MatchStatusEnum.ENDED_FOR_TIME;
        }
        return this.gameType.getGameController().checkGameStatus(this.getMovementManager().getPlayerTurn());
    }

    @Override
    public Optional<Player> getWinner() {

        final Optional<Player> playerWonByCheckMate = Stream.of(this.players.getX(), this.players.getY())
                .filter(x -> this.gameType.getGameController().isWinner(x)).findAny();

        if (playerWonByCheckMate.isPresent()) {
            return playerWonByCheckMate;
        }

        final Optional<Player> playerWithoutTime = this.timer.getPlayerWithoutTime();

        if (playerWithoutTime.isPresent()) {
            return this.players.getX().equals(playerWithoutTime.get()) ? Optional.of(this.players.getY())
                    : Optional.of(this.players.getX());
        }

        return Optional.empty();
    }

    @Override
    public Board getBoardAtIndexFromHistory(final int index) {
        return this.history.getBoardAtIndex(index);
    }

    @Override
    public Board getBoard() {
        return this.gameType.getGameController().boardState();
    }

    @Override
    public GameController getGameController() {
        return this.gameType.getGameController();
    }

    @Override
    public Pair<Player, Integer> getPlayerTimeRemaining() {
        final Player player = this.gameType.getMovementManager().getPlayerTurn();
        final int timeRemaining = (int) this.timer.getRemaningTime(player);
        return new Pair<>(player, timeRemaining);
    }

    @Override
    public List<Board> getBoardFullHistory() {
        return this.history.getAllBoards();
    }

    @Override
    public MovementManager getMovementManager() {
        return this.gameType.getMovementManager();
    }

    @Override
    public Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return this.getMovementManager().filterOnPossibleMovesBasedOnGameController(piece);
    }

    @Override
    public void uploadMatchHistory(final List<Board> boardHistory) {
        this.history.updateWithNewHistory(boardHistory);
    }

}
