package jhaturanga.model.chessproblems;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.movement.BasicMovement;
import jhaturanga.model.movement.BasicMovementImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.StartingBoardFactoryImpl;

public final class ChessProblemFactoryImpl implements ChessProblemFactory {

    private List<BasicMovement> fromString(final String movementList) {
        return Arrays.stream(movementList.split("/")).map(x -> x.split(",")).map(x -> new BasicMovementImpl(
                new BoardPositionImpl(Integer.parseInt(x[0].split("-")[0]), Integer.parseInt(x[0].split("-")[1])),
                new BoardPositionImpl(Integer.parseInt(x[1].split("-")[0]), Integer.parseInt(x[1].split("-")[1]))))
                .collect(Collectors.toList());
    }

    @Override
    public ChessProblem problemOne(final Player whitePlayer, final Player blackPlayer) {
        final String correctMoves = "5-3,4-4/3-6,4-6/4-3,7-6";
        return new ChessProblemImpl(this.fromString(correctMoves),
                new StartingBoardFactoryImpl().problemOneBoard(whitePlayer, blackPlayer));
    }

    @Override
    public ChessProblem problemTwo(final Player whitePlayer, final Player blackPlayer) {
        final String correctMoves = "3-6,3-7/7-5,5-6/5-7,5-6";
        return new ChessProblemImpl(this.fromString(correctMoves),
                new StartingBoardFactoryImpl().problemTwoBoard(whitePlayer, blackPlayer));
    }

    @Override
    public ChessProblem problemThree(final Player whitePlayer, final Player blackPlayer) {
        final String correctMoves = "1-1,1-3/2-6,2-4";
        return new ChessProblemImpl(this.fromString(correctMoves),
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer));
    }

    @Override
    public ChessProblem problemFour(final Player whitePlayer, final Player blackPlayer) {
        final String correctMoves = "4-1,4-2/2-6,2-5";
        return new ChessProblemImpl(this.fromString(correctMoves),
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer));
    }

}
