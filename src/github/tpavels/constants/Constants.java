package github.tpavels.constants;

public interface Constants {

	public static final int BORDER = 5;
	public static final String FRAME_TITLE = "Breakout";

	public enum Direction {
		RIGHT,
		LEFT,
	}

	public enum Collision {
	    NONE,
	    VERTICAL,
	    HORIZONTAL,
	    CORNER,

	}

	public enum Key {
	    LEFT,
	    RIGHT,
	    SPACE,
	    NONE,

	}

}
