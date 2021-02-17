package jhaturanga.model.history;

import java.util.ArrayList;
import java.util.List;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementImpl;

public class HistoryImpl implements History {

    private final List<Movement> movements = new ArrayList<>();
    private int currentMovementIndexViewed;

    @Override
    public final void addMoveToHistory(final Movement movement) {
        System.out.println(new MovementImpl(movement.getPieceInvolved(), new BoardPositionImpl(movement.getOrigin()),
                new BoardPositionImpl(movement.getDestination())));
        this.movements.add(new MovementImpl(movement.getPieceInvolved(), new BoardPositionImpl(movement.getOrigin()),
                new BoardPositionImpl(movement.getDestination())));
        this.currentMovementIndexViewed = this.movements.size() - 1;
    }

    @Override
    public final Movement getMoveAtIndex(final int index) {
        this.currentMovementIndexViewed = index;
        return this.movements.get(this.currentMovementIndexViewed);
    }

    @Override
    public final Movement getPreviousMove() {
        if (this.currentMovementIndexViewed > 1) {
            this.currentMovementIndexViewed--;
        }
        System.out.println(this.movements.get(this.currentMovementIndexViewed));
        return this.movements.get(this.currentMovementIndexViewed);
    }

    @Override
    public final Movement getNextMove() {
        if (this.currentMovementIndexViewed < this.movements.size() - 1) {
            this.currentMovementIndexViewed++;
        }
        return this.movements.get(this.currentMovementIndexViewed);
    }

}
