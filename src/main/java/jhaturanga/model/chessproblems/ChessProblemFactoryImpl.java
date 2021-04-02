package jhaturanga.model.chessproblems;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jhaturanga.commons.PlayerPair;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.movement.BasicMovement;
import jhaturanga.model.movement.BasicMovementImpl;
import jhaturanga.model.startingboards.StartingBoardFactoryImpl;

public final class ChessProblemFactoryImpl implements ChessProblemFactory {

    private List<BasicMovement> generateMovementsCollectionFromString(final String movementList) {
        return Arrays.stream(movementList.split("/")).map(x -> x.split(",")).map(x -> new BasicMovementImpl(
                new BoardPositionImpl(Integer.parseInt(x[0].split("-")[0]), Integer.parseInt(x[0].split("-")[1])),
                new BoardPositionImpl(Integer.parseInt(x[1].split("-")[0]), Integer.parseInt(x[1].split("-")[1]))))
                .collect(Collectors.toList());
    }

    @Override
    public ChessProblem problemOne(final PlayerPair players) {
        final String correctMoves = "5-3,4-4/3-6,4-6/4-3,7-6";
        return new ChessProblemImpl(this.generateMovementsCollectionFromString(correctMoves),
                new StartingBoardFactoryImpl().problemOneBoard(players));
    }

    @Override
    public ChessProblem problemTwo(final PlayerPair players) {
        final String correctMoves = "3-6,3-7/7-5,5-6/5-7,5-6";
        return new ChessProblemImpl(this.generateMovementsCollectionFromString(correctMoves),
                new StartingBoardFactoryImpl().problemTwoBoard(players));
    }

    @Override
    public ChessProblem problemThree(final PlayerPair players) {
        final String correctMoves = "7-7,1-1/3-0,4-0/1-1,2-0";
        return new ChessProblemImpl(this.generateMovementsCollectionFromString(correctMoves),
                new StartingBoardFactoryImpl().problemThreeBoard(players));
    }

    @Override
    public ChessProblem problemFour(final PlayerPair players) {
        final String correctMoves = "1-6,5-2/6-2,5-2/2-3,4-3";
        return new ChessProblemImpl(this.generateMovementsCollectionFromString(correctMoves),
                new StartingBoardFactoryImpl().problemFourBoard(players));
    }

    @Override
    public ChessProblem problemFive(final PlayerPair players) {
        final String correctMoves = "7-0,7-7/6-7,7-7/3-0,7-4/7-7,6-7/7-4,7-6";
        return new ChessProblemImpl(this.generateMovementsCollectionFromString(correctMoves),
                new StartingBoardFactoryImpl().problemFiveBoard(players));
    }

}
