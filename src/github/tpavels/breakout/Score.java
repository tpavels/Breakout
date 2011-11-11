package github.tpavels.breakout;

public class Score {

    private int score;
    private int multiplier;


    public Score() {
        score = 0;
        multiplier = 1;
    }

    /**
     * Sets multiplier to 1(one)
     */
    public void resetMultipier() {
        multiplier = 1;
    }

    /**
     * Adds 10 * multiplier points, multiplier increments by 1 each call time
     */
    public void incrementScore() {
        score +=10 * multiplier++;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

}
