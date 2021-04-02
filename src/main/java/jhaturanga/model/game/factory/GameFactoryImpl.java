package jhaturanga.model.game.factory;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.starting.StartingBoardFactoryImpl;
import jhaturanga.model.editor.StringBoard;
import jhaturanga.model.game.Game;
import jhaturanga.model.game.GameBuilderImpl;
import jhaturanga.model.game.controller.ClassicGameController;
import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.game.controller.PieceSwapVariantGameController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.movement.manager.BombVariantMovementManager;
import jhaturanga.model.movement.manager.ChessProblemsMovementManagerDecorator;
import jhaturanga.model.movement.manager.ClassicMovementManager;
import jhaturanga.model.movement.manager.PieceSwapVariantMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategies;
import jhaturanga.model.piece.movement.EveryoneMovesLikeRooksPieceMovementStrategies;
import jhaturanga.model.piece.movement.KingAsQueenPieceMovementStrategies;
import jhaturanga.model.piece.movement.OneDimensionPieceMovementStrategies;
import jhaturanga.model.piece.movement.PawnVariantPieceMovementStrategies;
import jhaturanga.model.piece.movement.PieceMovementStrategies;
import jhaturanga.model.piece.movement.RookAndBishopPieceMovementStrategies;
import jhaturanga.model.player.PlayerPair;
import jhaturanga.model.problems.Problem;

public final class GameFactoryImpl implements GameFactory {

    private static final boolean CASTLING_NOT_ENABLED = false;

    private Game allClassicApartFromMovementStrategy(final PlayerPair players,
            final PieceMovementStrategies pieceMovementStrategy, final GameType type) {
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(players), pieceMovementStrategy, players);

        return new GameBuilderImpl().gameController(gameController).type(type)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    private Game allClassicDifferentBoard(final PlayerPair players, final Board startingBoard,
            final GameType type) {
        final PieceMovementStrategies movementStrategyFactory = new ClassicPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(startingBoard, movementStrategyFactory,
                players);

        return new GameBuilderImpl().gameController(gameController).type(type)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public Game classicGame(final PlayerPair players) {
        return this.allClassicApartFromMovementStrategy(players, new ClassicPieceMovementStrategies(),
                GameType.CLASSIC_GAME);
    }

    @Override
    public Game pawnMovemementVariantGame(final PlayerPair players) {
        return this.allClassicApartFromMovementStrategy(players, new PawnVariantPieceMovementStrategies(),
                GameType.PAWN_MOVEMENT_VARIANT);
    }

    @Override
    public Game kingMovesAsQueenVariantGame(final PlayerPair players) {
        return this.allClassicApartFromMovementStrategy(players, new KingAsQueenPieceMovementStrategies(),
                GameType.KING_MOVES_LIKE_QUEEN);
    }

    @Override
    public Game everyPieceMovesLikeRooksVariantGame(final PlayerPair players) {
        return this.allClassicApartFromMovementStrategy(players, new EveryoneMovesLikeRooksPieceMovementStrategies(),
                GameType.EVERYONE_MOVES_LIKE_A_ROOK);
    }

    @Override
    public Game rookBishopMovementVariantGame(final PlayerPair players) {
        return this.allClassicApartFromMovementStrategy(players, new RookAndBishopPieceMovementStrategies(),
                GameType.ROOK_AND_BISHOP_MOVEMENT_VARIANT);
    }

    @Override
    public Game pawnHordeVariantGame(final PlayerPair players) {
        return this.allClassicDifferentBoard(players, new StartingBoardFactoryImpl().pawnHordeBoard(players),
                GameType.PAWN_HORDE_VARIANT);
    }

    @Override
    public Game threeColumnsVariantGame(final PlayerPair players) {
        return this.allClassicDifferentBoard(players, new StartingBoardFactoryImpl().threeColumnsBoard(players),
                GameType.THREE_COLUMNS_VARIANT);
    }

    @Override
    public Game oneDimensionVariantGame(final PlayerPair players) {
        final PieceMovementStrategies movementStrategyFactory = new OneDimensionPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().oneDimensionBoard(players), new OneDimensionPieceMovementStrategies(),
                players);

        return new GameBuilderImpl().gameController(gameController).type(GameType.ONE_DIMENSION_VARIANT)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public Game pieceSwapVariantGame(final PlayerPair players) {
        final PieceMovementStrategies movementStrategyFactory = new ClassicPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new PieceSwapVariantGameController(
                new StartingBoardFactoryImpl().classicBoard(players), movementStrategyFactory, players);

        return new GameBuilderImpl().gameController(gameController).type(GameType.PIECE_SWAP_VARIANT)
                .movementManager(new PieceSwapVariantMovementManager(gameController)).build();
    }

    @Override
    public Game bombVariantGame(final PlayerPair players) {
        final PieceMovementStrategies movementStrategyFactory = new ClassicPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(players), movementStrategyFactory, players);

        return new GameBuilderImpl().gameController(gameController).type(GameType.BOMB_VARIANT)
                .movementManager(new BombVariantMovementManager(gameController)).build();
    }

    @Override
    public Game chessProblemGameType(final PlayerPair players, final Problem chessProblem) {
        final PieceMovementStrategies pmsf = new ClassicPieceMovementStrategies();
        pmsf.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(chessProblem.getStartingBoard(), pmsf,
                players);

        return new GameBuilderImpl().gameController(gameController).type(GameType.CHESS_PROBLEM)
                .movementManager(
                        new ChessProblemsMovementManagerDecorator(gameController, chessProblem.getCorrectMoves()))
                .build();
    }

    @Override
    public Game customizedBoardVariantGame(final PlayerPair players, final StringBoard startingBoardInfo) {
        return this.allClassicDifferentBoard(players,
                new StartingBoardFactoryImpl().customizedBoard(startingBoardInfo.getBoard(),
                        startingBoardInfo.getColumns(), startingBoardInfo.getRows(), players),
                GameType.CUSTOM_BOARD_VARIANT);
    }

}
