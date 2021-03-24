package jhaturanga.model.game.gametypes;

import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.editor.StringBoard;
import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.PieceSwapVariantGameController;
import jhaturanga.model.movement.BombVariantMovementManager;
import jhaturanga.model.movement.ChessProblemsMovementManagerDecorator;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.movement.PieceSwapVariantMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.EveryoneMovesLikeRooksPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.OneDimensionPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PawnVariantPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.RookAndBishopPieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.StartingBoardFactoryImpl;
import jhaturange.model.chessproblems.ChessProblem;

public class GameTypeFactoryImpl implements GameTypeFactory {

    private static final boolean CASTLING_NOT_ENABLED = false;

    private GameType allClassicApartFromMovementStrategy(final Player whitePlayer, final Player blackPlayer,
            final PieceMovementStrategyFactory pieceMovementStrategy, final String gameTypeName) {
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer), pieceMovementStrategy,
                List.of(whitePlayer, blackPlayer));
        return new GameTypeBuilderImpl().gameController(gameController).gameTypeName(gameTypeName)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    private GameType allClassicDifferentBoard(final Player whitePlayer, final Player blackPlayer,
            final Board startingBoard, final String gameTypeName) {
        final PieceMovementStrategyFactory movementStrategyFactory = new ClassicPieceMovementStrategyFactory();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(startingBoard, movementStrategyFactory,
                List.of(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).gameTypeName(gameTypeName)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public final GameType classicGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new ClassicPieceMovementStrategyFactory(), "Classic Game");
    }

    @Override
    public final GameType pawnMovemementVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new PawnVariantPieceMovementStrategyFactory(), "Pawn Movement Variant Game");
    }

    @Override
    public final GameType everyPieceMovesLikeRooksVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new EveryoneMovesLikeRooksPieceMovementStrategyFactory(), "Everyone is a Rook");
    }

    @Override
    public final GameType rookBishopMovementVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new RookAndBishopPieceMovementStrategyFactory(), "Rook and Bishop Hybrid movement");
    }

    @Override
    public final GameType pawnHordeVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicDifferentBoard(whitePlayer, blackPlayer,
                new StartingBoardFactoryImpl().pawnHordeBoard(whitePlayer, blackPlayer), "Pawn Horde Variant Game");
    }

    @Override
    public final GameType threeColumnsVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicDifferentBoard(whitePlayer, blackPlayer,
                new StartingBoardFactoryImpl().threeColumnsBoard(whitePlayer, blackPlayer),
                "Three Columns Variant Game");
    }

    @Override
    public final GameType customizedBoardVariantGame(final Player whitePlayer, final Player blackPlayer,
            final StringBoard startingBoardInfo) {
        return this.allClassicDifferentBoard(whitePlayer, blackPlayer,
                new StartingBoardFactoryImpl().customizedBoard(startingBoardInfo.getBoard(),
                        startingBoardInfo.getColumns(), startingBoardInfo.getRows(), whitePlayer, blackPlayer),
                "Customized Board Game");
    }

    @Override
    public final GameType oneDimensionVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final PieceMovementStrategyFactory movementStrategyFactory = new OneDimensionPieceMovementStrategyFactory();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().oneDimensionBoard(whitePlayer, blackPlayer), movementStrategyFactory,
                List.of(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).gameTypeName("1D Variant Game")
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public final GameType pieceSwapVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameController gameController = new PieceSwapVariantGameController(
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).gameTypeName("Piece Swap Variant Game")
                .movementManager(new PieceSwapVariantMovementManager(gameController)).build();
    }

    @Override
    public final GameType bombVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final PieceMovementStrategyFactory movementStrategyFactory = new ClassicPieceMovementStrategyFactory();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer), movementStrategyFactory,
                List.of(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).gameTypeName("Bomb Variant Game")
                .movementManager(new BombVariantMovementManager(gameController)).build();
    }

    @Override
    public final GameType chessProblemGameType(final Player whitePlayer, final Player blackPlayer,
            final ChessProblem chessProblem) {
        final GameController gameController = new ClassicGameController(chessProblem.getProblemStartingBoard(),
                new ClassicPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).gameTypeName("Chess problem game")
                .movementManager(
                        new ChessProblemsMovementManagerDecorator(gameController, chessProblem.getCorrectMoves()))
                .build();
    }

}
