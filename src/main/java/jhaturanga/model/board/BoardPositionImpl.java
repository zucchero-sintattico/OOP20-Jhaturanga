package jhaturanga.model.board;

public class BoardPositionImpl implements BoardPosition {

    private int xPosition;
    private int yPosition;

    public BoardPositionImpl(final int xPosition, final int yPosition) {
	this.xPosition = xPosition;
	this.yPosition = yPosition;
    }

    @Override
    public final int getX() {
	return this.xPosition;
    }

    @Override
    public final int getY() {
	return this.yPosition;
    }

    @Override
    public final void setX(final int xNewPos) {
	this.xPosition = xNewPos;
    }

    @Override
    public final void setY(final int yNewPos) {
	this.yPosition = yNewPos;
    }

}
