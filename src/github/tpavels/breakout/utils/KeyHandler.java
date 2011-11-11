package github.tpavels.breakout.utils;
import github.tpavels.breakout.GameField;
import github.tpavels.constants.Constants.Key;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    private Key keyPressed;
    private GameField game;

    public KeyHandler(GameField game) {
        this.game = game;
        keyPressed = Key.NONE;
    }

    /**
     * @return keyPressed at the moment
     */
    public Key getKeyPressed() {
        return keyPressed;
    }

    
    @Override public void keyReleased(KeyEvent e) {
        keyPressed = Key.NONE;
    }

    @Override public void keyPressed(KeyEvent e) {
        // exit game
        int keyCode = e.getKeyCode();
        if ((keyCode == KeyEvent.VK_ESCAPE)
            || (keyCode == KeyEvent.VK_Q)
            || (keyCode == KeyEvent.VK_END)
            || ((keyCode == KeyEvent.VK_C) && e.isControlDown())) {
            game.stopGame();
            Frame[] frames = Frame.getFrames();
            for (Frame frame : frames) {
                frame.dispose();
            }
        }

        // movement keys
        if (keyCode == KeyEvent.VK_LEFT){
            keyPressed = Key.LEFT;
        }
        if (keyCode == KeyEvent.VK_RIGHT){
            keyPressed = Key.RIGHT;
        }
        // pause/start keys
        if (keyCode == KeyEvent.VK_SPACE){
            keyPressed = Key.SPACE;
            if (!game.isStartPosition()) {
                game.pause(); // PAUSES/UNPAUSES game
            }
        }
    }

    /** 
     * Resets saved key pressed at the moment
     */
    public void resetKey() {
        keyPressed = Key.NONE;
    }

    @Override public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

}
