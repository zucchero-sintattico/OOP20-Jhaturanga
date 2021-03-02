package jhaturanga.model.game.gametypes;

import java.util.List;

import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.movement.PieceSwapVariantMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PawnVariantPieceMovementStrategyFactory;
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

        return gameTypeBuilder.gameController(gameController)
                .movementManager(new ClassicMovementManager(gameController))
                .gameTypeDescription(GameTypeDescription.classicGameType()).build();
    }

    @Override
    public final GameType pawnHordeVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new ClassicGameController(
                PawnVsClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController)
                .movementManager(new ClassicMovementManager(gameController))
                .gameTypeDescription(GameTypeDescription.pawnHordeVariant()).build();
    }

    @Override
    public final GameType pieceSwapVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new ClassicGameController(
                ClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController)
                .movementManager(new PieceSwapVariantMovementManager(gameController))
                .gameTypeDescription(GameTypeDescription.pieceSwapVariant()).build();
    }

    @Override
    public final GameType pawnMovemementVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new ClassicGameController(
                ClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                new PawnVariantPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController)
                .movementManager(new ClassicMovementManager(gameController))
                .gameTypeDescription(GameTypeDescription.pawnMovemementVariant()).build();
    }

    @Override
    public final GameType threeColumnsVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
        final GameController gameController = new ClassicGameController(
                ThreeColumnsBoard.createStartingBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer));

        return gameTypeBuilder.gameController(gameController)
                .movementManager(new ClassicMovementManager(gameController))
                .gameTypeDescription(GameTypeDescription.classicGameType()).build();
    }
}
