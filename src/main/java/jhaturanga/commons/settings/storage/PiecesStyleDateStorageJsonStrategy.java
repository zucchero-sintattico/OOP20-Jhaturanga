package jhaturanga.commons.settings.storage;

import java.io.IOException;
import java.util.Optional;

import jhaturanga.commons.settings.media.style.piece.PieceStyleEnum;

public final class PiecesStyleDateStorageJsonStrategy extends SettingDataStorageJson
        implements SettingsDataStorageJsonStrategy<PieceStyleEnum> {

    @Override
    public void setSetting(final PieceStyleEnum value) throws IOException {
        this.put(SettingTypeEnum.PIECES_STYLE, value.toString());

    }

    @Override
    public Optional<PieceStyleEnum> getSetting() throws IOException {
        return Optional.ofNullable(this.getSettingValue(SettingTypeEnum.PIECES_STYLE))
                .map(PieceStyleEnum::valueOf);
    }

}
