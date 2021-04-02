package jhaturanga.model.game.gametypes;

import jhaturanga.commons.PlayerPair;
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
import jhaturanga.model.piece.movement.KingAsQueenPieceMovementStrategies;
import jhaturanga.model.piece.movement.OneDimensionPieceMovementStrategies;
import jhaturanga.model.piece.movement.PawnVariantPieceMovementStrategies;
import jhaturanga.model.piece.movement.PieceMovementStrategies;
import jhaturanga.model.piece.movement.RookAndBishopPieceMovementStrategies;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.StartingBoardFactoryImpl;

public final class GameTypeFactoryImpl implements GameTypeFactory {

    private static final boolean CASTLING_NOT_ENABLED = false;

    private GameType allClassicApartFromMovementStrategy(final PlayerPair players,
            final PieceMovementStrategies pieceMovementStrategy, final GameTypesEnum type) {
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(players), pieceMovementStrategy, players);

        return new GameTypeBuilderImpl().gameController(gameController).type(type)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    private GameType allClassicDifferentBoard(final PlayerPair players, final Board startingBoard,
            final GameTypesEnum type) {
        final PieceMovementStrategies movementStrategyFactory = new ClassicPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(startingBoard, movementStrategyFactory,
                players);

        return new GameTypeBuilderImpl().gameController(gameController).type(type)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public GameType classicGame(final PlayerPair players) {
        return this.allClassicApartFromMovementStrategy(players, new ClassicPieceMovementStrategies(),
                GameTypesEnum.CLASSIC_GAME);
    }

    @Override
    public GameType pawnMovemementVariantGame(final PlayerPair players) {
        return this.allClassicApartFromMovementStrategy(players, new PawnVariantPieceMovementStrategies(),
                GameTypesEnum.PAWN_MOVEMENT_VARIANT);
    }

    @Override
    public GameType kingMovesAsQueenVariantGame(final PlayerPair players) {
        return this.allClassicApartFromMovementStrategy(players, new KingAsQueenPieceMovementStrategies(),
                GameTypesEnum.KING_MOVES_LIKE_QUEEN);
    }

    @Override
    public GameType everyPieceMovesLikeRooksVariantGame(final PlayerPair players) {
        return this.allClassicApartFromMovementStrategy(players, new EveryoneMovesLikeRooksPieceMovementStrategies(),
                GameTypesEnum.EVERYONE_MOVES_LIKE_A_ROOK);
    }

    @Override
    public GameType rookBishopMovementVariantGame(final PlayerPair players) {
        return this.allClassicApartFromMovementStrategy(players, new RookAndBishopPieceMovementStrategies(),
                GameTypesEnum.ROOK_AND_BISHOP_MOVEMENT_VARIANT);
    }

    @Override
    public GameType pawnHordeVariantGame(final PlayerPair players) {
        return this.allClassicDifferentBoard(players, new StartingBoardFactoryImpl().pawnHordeBoard(players),
                GameTypesEnum.PAWN_HORDE_VARIANT);
    }

    @Override
    public GameType threeColumnsVariantGame(final PlayerPair players) {
        return this.allClassicDifferentBoard(players, new StartingBoardFactoryImpl().threeColumnsBoard(players),
                GameTypesEnum.THREE_COLUMNS_VARIANT);
    }

    @Override
    public GameType oneDimensionVariantGame(final PlayerPair players) {
        final PieceMovementStrategies movementStrategyFactory = new OneDimensionPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().oneDimensionBoard(players), new OneDimensionPieceMovementStrategies(),
                players);

        return new GameTypeBuilderImpl().gameController(gameController).type(GameTypesEnum.ONE_DIMENSION_VARIANT)
                .movementManager(new ClassicMovementManager(gameController)).build();
    }

    @Override
    public GameType pieceSwapVariantGame(final PlayerPair players) {
        final PieceMovementStrategies movementStrategyFactory = new ClassicPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new PieceSwapVariantGameController(
                new StartingBoardFactoryImpl().classicBoard(players), movementStrategyFactory, players);

        return new GameTypeBuilderImpl().gameController(gameController).type(GameTypesEnum.PIECE_SWAP_VARIANT)
                .movementManager(new PieceSwapVariantMovementManager(gameController)).build();
    }

    @Override
    public GameType bombVariantGame(final PlayerPair players) {
        final PieceMovementStrategies movementStrategyFactory = new ClassicPieceMovementStrategies();
        movementStrategyFactory.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(
                new StartingBoardFactoryImpl().classicBoard(players), movementStrategyFactory, players);

        return new GameTypeBuilderImpl().gameController(gameController).type(GameTypesEnum.BOMB_VARIANT)
                .movementManager(new BombVariantMovementManager(gameController)).build();
    }

    @Override
    public GameType chessProblemGameType(final PlayerPair players, final ChessProblem chessProblem) {
        final PieceMovementStrategies pmsf = new ClassicPieceMovementStrategies();
        pmsf.setCanCastle(CASTLING_NOT_ENABLED);
        final GameController gameController = new ClassicGameController(chessProblem.getProblemStartingBoard(), pmsf,
                players);

        return new GameTypeBuilderImpl().gameController(gameController).type(GameTypesEnum.CHESS_PROBLEM)
                .movementManager(
                        new ChessProblemsMovementManagerDecorator(gameController, chessProblem.getCorrectMoves()))
                .build();
    }

    @Override
    public GameType customizedBoardVariantGame(final PlayerPair players, final StringBoard startingBoardInfo) {
        return this.allClassicDifferentBoard(players,
                new StartingBoardFactoryImpl().customizedBoard(startingBoardInfo.getBoard(),
                        startingBoardInfo.getColumns(), startingBoardInfo.getRows(), players),
                GameTypesEnum.CUSTOM_BOARD_VARIANT);
    }

}
