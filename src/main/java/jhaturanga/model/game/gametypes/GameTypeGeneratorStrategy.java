package jhaturanga.model.game.gametypes;

import jhaturanga.commons.PlayerPair;

@FunctionalInterface
public interface GameTypeGeneratorStrategy {

    /**
     * 
     * @param gameTypeFactory
     * @param players
     * @return the generated game type instance
     */
    GameType generate(GameTypeFactory gameTypeFactory, PlayerPair players);
}
