package jhaturanga.model.game.controller;

import jhaturanga.model.board.Board;
import jhaturanga.model.match.MatchStatus;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.piece.movement.PieceMovementStrategies;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerPair;

public interface GameController {

    // TODO: rifare questa doc
    /**
     * Check if the game is over or not, if it is return the type of endgame.
     * 
     * @param playerTurn - The Player who's turn it is. We in fact need to know
     *                   who's turn it is to determine the outcome of certain
     *                   situations such as draw conditions.
     * @return EndGameType - The situation of the match.
     */
    MatchStatus getGameStatus(Player playerTurn);

    /**
     * Control if the king is under check.
     * 
     * @param player is the player of which to check if the move puts his king in a
     *               check state
     * @return true if the king is under check
     */
    boolean isInCheck(Player player);

    /**
     * Control if the king is under check.
     * 
     * @param movement - the movement to test whether it wouldn't put the executing
     *                 player in check.
     * @return true if the movement wouldn't result in a check by the same player
     *         who executed the movement.
     */
    boolean wouldNotBeInCheck(PieceMovement movement);

    /**
     * Return a boolean that states if the player passed as parameter won the game.
     * 
     * @param player is the player of which check if he won the game
     * @return true if player won false otherwise
     */
    boolean isWinner(Player player);

    /**
     * Return the state of the Board.
     * 
     * @return Board representing the actual state of the board of the match
     */
    Board boardState();

    /**
     * Return the list of the players.
     * 
     * @return List representing the players of the game
     */
    PlayerPair getPlayers();

    /**
     * Return the PieceMovementStrategyFactory of the match's GameType that's been
     * controlled.
     * 
     * @return PieceMovementStrategyFactory representing the
     *         PieceMovementStrategyFactory of the game
     */
    PieceMovementStrategies getPieceMovementStrategies();
}
