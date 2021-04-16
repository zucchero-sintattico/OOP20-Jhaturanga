package jhaturanga.views.commons.image;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import jhaturanga.commons.Pair;
import jhaturanga.commons.settings.SettingMediator;
import jhaturanga.commons.settings.media.style.PieceStyle;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

/**
 * The loader of image of the piece.
 */
public final class PieceImageLoader {

    /**
     * All images representing all the Pieces of every color must be loaded at the
     * start and creation of the board. This is done to improve performances. Also,
     * we cannot generate only the images from the starting board, because some
     * PieceTypes might not be present at the start of the game but may be needed in
     * a second moment. So all images must be loaded.
     */
    private final Map<Pair<PieceType, PlayerColor>, Image> pieceImageMapper = new HashMap<>();

    public PieceImageLoader() {
        Arrays.stream(PieceType.values()).forEach(pieceType -> {

            try {

                final Image whitePieceImage = new Image(
                        PieceStyle.getPieceStylePath(SettingMediator.getSavedPieceStyle(), pieceType, PlayerColor.WHITE)
                                .toUri().toString());
                this.pieceImageMapper.put(new Pair<>(pieceType, PlayerColor.WHITE), whitePieceImage);
                final Image blackPieceImage = new Image(
                        PieceStyle.getPieceStylePath(SettingMediator.getSavedPieceStyle(), pieceType, PlayerColor.BLACK)
                                .toUri().toString());
                this.pieceImageMapper.put(new Pair<>(pieceType, PlayerColor.BLACK), blackPieceImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public Image getPieceImage(final Piece piece) {
        return this.pieceImageMapper.get(new Pair<>(piece.getType(), piece.getPlayer().getColor()));
    }
}
