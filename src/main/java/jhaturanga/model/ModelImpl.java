package jhaturanga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.timer.TimerFactoryImpl;
import jhaturanga.model.user.User;

public final class ModelImpl implements Model {

    /**
     * 
     */
    private static final long serialVersionUID = 4238470539753666631L;
    private User firstUser;
    private User secondUser;
    private final List<Match> matches = new ArrayList<>();
    private Player whitePlayer;
    private Player blackPlayer;
    private Timer timer;
    private GameTypesEnum selectedType;

    @Override
    public Optional<Match> getActualMatch() {
        if (!this.matches.isEmpty()) {
            return Optional.of(this.matches.get(this.matches.size() - 1));
        }
        return Optional.empty();
    }

    @Override
    public void createMatch() {
        final Match match = new MatchImpl(this.getGameType().get().getGameType(this.whitePlayer, this.blackPlayer),
                this.getTimer());
        this.matches.add(match);
    }

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.selectedType = gameType;
    }

    @Override
    public void setTimer(final DefaultsTimers timer) {
        if (!timer.getIncrement().isPresent()) {
            this.timer = new TimerFactoryImpl().equalTimer(List.of(this.whitePlayer, this.blackPlayer),
                    timer.getSeconds());
        } else {
            this.timer = new TimerFactoryImpl().incrementableTimer(List.of(this.whitePlayer, this.blackPlayer),
                    timer.getSeconds(), timer.getIncrement().get());
        }
    }

    @Override
    public Optional<Timer> getTimer() {
        return Optional.ofNullable(this.timer);
    }

    @Override
    public Optional<GameTypesEnum> getGameType() {
        return Optional.ofNullable(this.selectedType);
    }

    @Override
    public void setWhitePlayer(final Player player) {
        this.whitePlayer = player;
    }

    @Override
    public void setBlackPlayer(final Player player) {
        this.blackPlayer = player;
    }

    @Override
    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    @Override
    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    public Optional<User> getFirstUser() {
        return Optional.ofNullable(this.firstUser);
    }

    @Override
    public Optional<User> getSecondUser() {
        return Optional.ofNullable(this.secondUser);
    }

    @Override
    public void setFirstUser(final User user) {
        this.firstUser = user;
    }

    @Override
    public void setSecondUser(final User user) {
        this.secondUser = user;

    }

    @Override
    public void setMatch(final Match match) {
        this.matches.add(match);
    }

}
