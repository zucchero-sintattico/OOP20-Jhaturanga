package jhaturanga.model.game.gametypes;

import jhaturanga.commons.Pair;
import jhaturanga.model.board.Board;
import jhaturanga.model.chessproblems.ChessProblem;
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

public class GameTypeFactoryImpl implements GameTypeFactory {

    private static final boolean CASTLING_NOT_ENABLED = false;

    private GameType allClassicApartFromMovementStrategy(final Player whitePlayer, final Player blackPlayer,
            final PieceMovementStrategyFactory pieceMovementStrategy, final GameTypesEnum type) {
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(whitePlayer, blackPlayer), pieceMovementStrategy,
                new Pair<>(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).type(type)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    private GameType allClassicDifferentBoard(final Player whitePlayer, final Player blackPlayer,
            final Board startingBoard, final GameTypesEnum type) {
        final PieceMovementStrategyFactory movementStrategyFactory = new ClassicPieceMovementStrategyFactory();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(startingBoard, movementStrategyFactory,
                new Pair<>(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).type(type)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    public final GameType classicGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new ClassicPieceMovementStrategyFactory(), GameTypesEnum.CLASSIC_GAME);
    }

    @Override
    public final GameType pawnMovemementVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new PawnVariantPieceMovementStrategyFactory(), GameTypesEnum.PAWN_MOVEMENT_VARIANT);
    }

    @Override
    public final GameType everyPieceMovesLikeRooksVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new EveryoneMovesLikeRooksPieceMovementStrategyFactory(), GameTypesEnum.EVERYONE_MOVES_LIKE_A_ROOK);
    }

    @Override
    public final GameType rookBishopMovementVariantGame(final Player whitePlayer, final Player blackPlayer) {
        return this.allClassicApartFromMovementStrategy(whitePlayer, blackPlayer,
                new RookAndBishopPieceMovementStrategyFactory(), GameTypesEnum.ROOK_AND_BISHOP_MOVEMENT_VARIANT);
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
        final PieceMovementStrategyFactory movementStrategyFactory = new OneDimensionPieceMovementStrategyFactory();
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
                new ClassicPieceMovementStrategyFactory(), new Pair<>(whitePlayer, blackPlayer));

        return new GameTypeBuilderImpl().gameController(gameController).type(GameTypesEnum.PIECE_SWAP_VARIANT)
                .movementManager(new PieceSwapVariantMovementManager(gameController)).build();
    }

    @Override
    public final GameType bombVariantGame(final Player whitePlayer, final Player blackPlayer) {
        final PieceMovementStrategyFactory movementStrategyFactory = new ClassicPieceMovementStrategyFactory();
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
        final GameController gameController = new ClassicGameController(chessProblem.getProblemStartingBoard(),
                new ClassicPieceMovementStrategyFactory(), new Pair<>(whitePlayer, blackPlayer));

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
