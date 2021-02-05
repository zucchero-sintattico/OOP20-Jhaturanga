package jhaturanga.model.board;

public class BoardPositionImpl implements BoardPosition {

    private int xPosition;
    private int yPosition;

    public BoardPositionImpl(final int xPosition, final int yPosition) {
	this.xPosition = xPosition;
	this.yPosition = yPosition;
    }

    @Override
    public int getX() {
	return this.xPosition;
    }

    @Override
    public int getY() {
	return this.yPosition;
    }

    @Override
    public void setX(final int xNewPos) {
	this.xPosition = xNewPos;
    }

    @Override
    public void setY(final int yNewPos) {
	this.yPosition = yNewPos;
    }

}
