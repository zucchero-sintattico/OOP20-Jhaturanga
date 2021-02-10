package jhaturanga.model.match;

import java.util.Optional;

import jhaturanga.model.movement.Movement;
import jhaturanga.model.player.Player;

public class MatchImpl implements Match {

    @Override
    public int getMatchID() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean move(Movement movement) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCompleted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<Player> winner() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movement getMoveAtIndexFromHistory(int index) {
        // TODO Auto-generated method stub
        return null;
    }

}
