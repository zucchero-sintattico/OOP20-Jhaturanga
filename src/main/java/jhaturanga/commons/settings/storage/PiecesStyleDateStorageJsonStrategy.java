package jhaturanga.commons.settings.storage;

import java.io.IOException;
import java.util.Optional;

import jhaturanga.commons.settings.dynamicconfiguration.configuratonobject.PieceStyleconfigurationObjectStrategy;
import jhaturanga.commons.settings.filegetter.ApplicationStyleListStrategy;

public final class PiecesStyleDateStorageJsonStrategy
        extends SettingDataStorageJson<PieceStyleconfigurationObjectStrategy> {

    @Override
    public void setSetting(final PieceStyleconfigurationObjectStrategy value) throws IOException {
        this.put(SettingTypeEnum.PIECES_STYLE, value.toString());

    }

    @Override
    public Optional<PieceStyleconfigurationObjectStrategy> getSetting() throws IOException {
        final String savedStyle = this.getSettingValue(SettingTypeEnum.PIECES_STYLE);
        final ApplicationStyleListStrategy myApplicationStyleList = new ApplicationStyleListStrategy();
        if (myApplicationStyleList.getAll().stream().filter(e -> e.getFileName().contentEquals(savedStyle))
                .count() > 0) {
            return Optional.of(new PieceStyleconfigurationObjectStrategy(savedStyle));
        }
        return Optional.empty();
    }

}
