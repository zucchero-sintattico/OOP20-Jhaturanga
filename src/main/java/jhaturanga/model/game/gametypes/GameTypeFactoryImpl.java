package jhaturanga.model.game.gametypes;

import java.util.List;

import jhaturanga.commons.Pair;
import jhaturanga.model.editor.StringBoard;
import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.PieceSwapVariantGameController;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.movement.NoCastlingMovementManager;
import jhaturanga.model.movement.PieceSwapVariantMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.OneDimensionPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PawnVariantPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.StartingBoardFactoryImpl;

public class GameTypeFactoryImpl implements GameTypeFactory {

    private static final boolean IS_CASTLING_ENABLED = false;

    @Override
    public final GameType classicGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategyFactory(), new Pair<>(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Classic Game")
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public final GameType pawnHordeVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().pawnHordeBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategyFactory(), new Pair<>(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Pawn Horde Variant Game")
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public final GameType pieceSwapVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new PieceSwapVariantGameController(
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategyFactory(), new Pair<>(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Piece Swap Variant Game")
                .movementManager(new PieceSwapVariantMovementManager(gameController)).build();
    }

    @Override
    public final GameType pawnMovemementVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer),
                new PawnVariantPieceMovementStrategyFactory(), new Pair<>(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Pawn Movement Variant Game")
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public final GameType threeColumnsVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final PieceMovementStrategyFactory movementStrategyFactory = new ClassicPieceMovementStrategyFactory();
        movementStrategyFactory.setCanCastle(false);
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().threeColumnsBoard(whitePlayer, blackPlayer), movementStrategyFactory,
                new Pair<>(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Three Columns Variant Game")
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public final GameType oneDimensionVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final PieceMovementStrategyFactory movementStrategyFactory = new OneDimensionPieceMovementStrategyFactory();
        movementStrategyFactory.setCanCastle(false);
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().oneDimensionBoard(whitePlayer, blackPlayer), movementStrategyFactory,
                new Pair<>(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("1D Variant Game")
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public final GameType customizedBoardVariantGame(final Player whitePlayer, final Player blackPlayer,
            final StringBoard startingBoardInfo) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final int columns = startingBoardInfo.getColumns();
        final int rows = startingBoardInfo.getRows();
        final PieceMovementStrategyFactory pieceMovementStrategyFactory = new ClassicPieceMovementStrategyFactory();
        pieceMovementStrategyFactory.setCanCastle(IS_CASTLING_ENABLED);
        final GameController gameController = new ClassicGameController(new StartingBoardFactoryImpl()
                .customizedBoard(startingBoardInfo.getBoard(), columns, rows, whitePlayer, blackPlayer),
                pieceMovementStrategyFactory, new Pair<>(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Customized Board Game")
                .movementManager(new NoCastlingMovementManager(gameController))
                .gameTypeName("Customizable Board Variant").build();
    }

}
