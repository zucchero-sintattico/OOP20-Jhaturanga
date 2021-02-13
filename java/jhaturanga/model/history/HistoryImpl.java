package jhaturanga.model.history;

import java.util.ArrayList;
import java.util.List;

import jhaturanga.model.movement.Movement;

public class HistoryImpl implements History {

    private final List<Movement> movements = new ArrayList<>();

    @Override
    public final void addMoveToHistory(final Movement movement) {
        this.movements.add(movement);
    }

    @Override
    public final Movement getMoveAtIndex(final int index) {
        return this.movements.get(index);
    }

}
