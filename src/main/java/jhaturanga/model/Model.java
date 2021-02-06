package jhaturanga.model;

import java.util.Collection;
import java.util.Optional;

import jhaturanga.model.match.Match;

/**
 * The Model class of MVC pattern.
 */
public interface Model {

    /**
     * @return the actual matches if presents.
     */
    Optional<Collection<Match>> getActualMatch();

}
