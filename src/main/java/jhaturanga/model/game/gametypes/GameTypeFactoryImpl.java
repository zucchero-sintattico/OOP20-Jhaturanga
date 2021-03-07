package jhaturanga.model.game.gametypes;

import java.util.List;

import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.PieceSwapVariantGameController;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.movement.PieceSwapVariantMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PawnVariantPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.ClassicStartingBoard;
import jhaturanga.model.startingboards.PawnVsClassicStartingBoard;
import jhaturanga.model.startingboards.ThreeColumnsBoard;

public class GameTypeFactoryImpl implements GameTypeFactory {

    @Override
    public final GameType classicGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new ClassicGameController(
                ClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Classic Game")
                .movementManager(new ClassicMovementManager(gameController))
                .gameTypeDescription(GameTypeDescription.classicGameType()).build();
    }

    @Override
    public final GameType pawnHordeVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new ClassicGameController(
                PawnVsClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Pawn Horde Variant Game")
                .movementManager(new ClassicMovementManager(gameController))
                .gameTypeDescription(GameTypeDescription.pawnHordeVariant()).build();
    }

    @Override
    public final GameType pieceSwapVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new PieceSwapVariantGameController(
                ClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Piece Swap Variant Game")
                .movementManager(new PieceSwapVariantMovementManager(gameController))
                .gameTypeDescription(GameTypeDescription.pieceSwapVariant()).build();
    }

    @Override
    public final GameType pawnMovemementVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new ClassicGameController(
                ClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                new PawnVariantPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Pawn Movement Variant Game")
                .movementManager(new ClassicMovementManager(gameController))
                .gameTypeDescription(GameTypeDescription.pawnMovemementVariant()).build();
    }

    @Override
    public final GameType threeColumnsVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final PieceMovementStrategyFactory movementStrategyFactory = new ClassicPieceMovementStrategyFactory();
        movementStrategyFactory.setCanCastle(false);
        final GameController gameController = new ClassicGameController(
                ThreeColumnsBoard.createStartingBoard(whitePlayer, blackPlayer), movementStrategyFactory,
                List.of(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController).gameTypeName("Three Columns Variant Game")
                .movementManager(new ClassicMovementManager(gameController))
                .gameTypeDescription(GameTypeDescription.threeColumnsVariant()).build();
    }
}