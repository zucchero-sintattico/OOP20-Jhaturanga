package jhaturanga.model.player;

import java.util.stream.Stream;

public final class PlayerPair {

    private final Player whitePlayer;
    private final Player blackPlayer;

    public PlayerPair(final Player whitePlayer, final Player blackPlayer) {
        if (!whitePlayer.getColor().equals(PlayerColor.WHITE)) {
            throw new IllegalArgumentException("White Player can't be black");
        }
        if (!blackPlayer.getColor().equals(PlayerColor.BLACK)) {
            throw new IllegalArgumentException("Black Player can't be white");
        }
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Stream<Player> stream() {
        return Stream.of(this.whitePlayer, this.blackPlayer);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((blackPlayer == null) ? 0 : blackPlayer.hashCode());
        result = prime * result + ((whitePlayer == null) ? 0 : whitePlayer.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PlayerPair other = (PlayerPair) obj;
        if (blackPlayer == null) {
            if (other.blackPlayer != null) {
                return false;
            }
        } else if (!blackPlayer.equals(other.blackPlayer)) {
            return false;
        }
        if (whitePlayer == null) {
            if (other.whitePlayer != null) {
                return false;
            }
        } else if (!whitePlayer.equals(other.whitePlayer)) {
            return false;
        }
        return true;
    }

}
