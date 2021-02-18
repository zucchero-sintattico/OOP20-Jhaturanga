package jhaturanga.model.match;

import java.util.Optional;

import jhaturanga.commons.network.NetworkInstance;
import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.player.Player;

public class NetworkMatch implements Match {

    private NetworkInstance network;
    private Player player;

    public NetworkMatch() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getMatchID() {
        // TODO Auto-generated method stub
        return null;
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
    public Board getBoardAtIndexFromHistory(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Board getBoard() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GameController getGameController() {
        // TODO Auto-generated method stub
        return null;
    }

}
