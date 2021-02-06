package jhaturanga.model.player;

public class PlayerImpl implements Player {

    private PlayerColor color;

    public PlayerImpl(final PlayerColor color) {
        this.color = color;
    }

    @Override
    public PlayerColor getColor() {
        return this.color;
    }

}
