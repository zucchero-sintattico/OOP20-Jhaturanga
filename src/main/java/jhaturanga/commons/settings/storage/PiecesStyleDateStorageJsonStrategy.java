package jhaturanga.commons.settings.storage;

import java.io.IOException;
import java.util.Optional;

import jhaturanga.commons.settings.config.PieceStyleConfigStrategy;
import jhaturanga.commons.settings.filegetter.PieceStyleListStrategy;

public final class PiecesStyleDateStorageJsonStrategy
        extends SettingDataStorageJson<PieceStyleConfigStrategy> {

    @Override
    public void setSetting(final PieceStyleConfigStrategy value) throws IOException {
        this.put(SettingTypeEnum.PIECES_STYLE, value.toString());

    }

    @Override
    public Optional<PieceStyleConfigStrategy> getSetting() throws IOException {
        final String savedStyle = this.getSettingValue(SettingTypeEnum.PIECES_STYLE);
        final PieceStyleListStrategy myPieceStyleList = new PieceStyleListStrategy();
        return Optional.ofNullable(savedStyle).map(e -> myPieceStyleList.getAll().stream()
                .filter(elem -> elem.getFileName().contentEquals(savedStyle)).findAny().get());
    }

}
