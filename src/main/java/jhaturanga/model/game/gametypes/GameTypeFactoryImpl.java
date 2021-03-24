package jhaturanga.model.game.gametypes;

import jhaturanga.commons.Pair;
import jhaturanga.model.board.Board;
import jhaturanga.model.chessproblems.ChessProblem;
import jhaturanga.model.editor.StringBoard;
import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.PieceSwapVariantGameController;
import jhaturanga.model.movement.manager.BombVariantMovementManager;
import jhaturanga.model.movement.manager.ChessProblemsMovementManagerDecorator;
import jhaturanga.model.movement.manager.ClassicMovementManager;
import jhaturanga.model.movement.manager.PieceSwapVariantMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategies;
import jhaturanga.model.piece.movement.EveryoneMovesLikeRooksPieceMovementStrategies;
import jhaturanga.model.piece.movement.OneDimensionPieceMovementStrategies;
import jhaturanga.model.piece.movement.PawnVariantPieceMovementStrategies;
import jhaturanga.model.piece.movement.PieceMovementStrategies;
import jhaturanga.model.piece.movement.RookAndBishopPieceMovementStrategies;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.StartingBoardFactoryImpl;

public class GameTypeFactoryImpl implements GameTypeFactory {

    private static final boolean CASTLING_NOT_ENABLED = false;

    private GameType allClassicApartFromMovementStrategy(final Player whitePlayer, final Player blackPlayer,
            final PieceMovementStrategies pieceMovementStrategy, final GameTypesEnum type) {
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer), pieceMovementStrategy,
                new Pair<>(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).type(type)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    private GameType allClassicDifferentBoard(final Player whitePlayer, final Player blackPlayer,
            final Board startingBoard, final GameTypesEnum type) {
        final PieceMovementStrategies movementStrategyFactory = new ClassicPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(startingBoard, movementStrategyFactory,
                new Pair<>(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).type(type)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    public final GameType classicGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer, new ClassicPieceMovementStrategies(),
                GameTypesEnum.CLASSIC_GAME);
    }

    @Override
    public final GameType pawnMovemementVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new PawnVariantPieceMovementStrategies(), GameTypesEnum.PAWN_MOVEMENT_VARIANT);
    }

    @Override
    public final GameType everyPieceMovesLikeRooksVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new EveryoneMovesLikeRooksPieceMovementStrategies(), GameTypesEnum.EVERYONE_MOVES_LIKE_A_ROOK);
    }

    @Override
    public final GameType rookBishopMovementVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new RookAndBishopPieceMovementStrategies(), GameTypesEnum.ROOK_AND_BISHOP_MOVEMENT_VARIANT);
    }

    @Override
    public final GameType pawnHordeVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicDifferentBoard(whitePlayer, blackPlayer,
                new StartingBoardFactoryImpl().pawnHordeBoard(whitePlayer, blackPlayer),
                GameTypesEnum.PAWN_HORDE_VARIANT);
    }

    @Override
    public final GameType threeColumnsVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicDifferentBoard(whitePlayer, blackPlayer,
                new StartingBoardFactoryImpl().threeColumnsBoard(whitePlayer, blackPlayer),
                GameTypesEnum.THREE_COLUMNS_VARIANT);
    }

    @Override
    public final GameType oneDimensionVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final PieceMovementStrategies movementStrategyFactory = new OneDimensionPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().oneDimensionBoard(whitePlayer, blackPlayer), movementStrategyFactory,
                new Pair<>(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).type(GameTypesEnum.ONE_DIMENSION_VARIANT)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    public final GameType pieceSwapVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final GameController gameController = new PieceSwapVariantGameController(
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer),
                new ClassicPieceMovementStrategies(), new Pair<>(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).type(GameTypesEnum.PIECE_SWAP_VARIANT)
                .movementManager(new PieceSwapVariantMovementManager(gameController)).build();
    }

    @Override
    public final GameType bombVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final PieceMovementStrategies movementStrategyFactory = new ClassicPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer), movementStrategyFactory,
                new Pair<>(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).type(GameTypesEnum.BOMB_VARIANT)
                .movementManager(new BombVariantMovementManager(gameController)).build();
    }

    @Override
    public final GameType chessProblemGameType(final Player whitePlayer, final Player blackPlayer,
            final ChessProblem chessProblem) {

        final PieceMovementStrategies pmsf = new ClassicPieceMovementStrategies();
        pmsf.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(chessProblem.getProblemStartingBoard(), pmsf,
                new Pair<>(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).type(GameTypesEnum.CHESS_PROBLEM)
                .movementManager(
                        new ChessProblemsMovementManagerDecorator(gameController, chessProblem.getCorrectMoves()))
                .build();
    }

    public final GameType customizedBoardVariantGame(final Player whitePlayer, final Player blackPlayer,
            final StringBoard startingBoardInfo) {
        return this.allClassicDifferentBoard(whitePlayer, blackPlayer,
                new StartingBoardFactoryImpl().customizedBoard(startingBoardInfo.getBoard(),
                        startingBoardInfo.getColumns(), startingBoardInfo.getRows(), whitePlayer, blackPlayer),
                GameTypesEnum.CUSTOM_BOARD_VARIANT);
    }

}
