import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
    private static boolean mouseLeftPressed = false;
    private static boolean mouseRightPressed = false;

    private static final Mouse instance = new Mouse();

    public Mouse () {}

    public static Mouse getInstance() {return instance; }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getButton());
        switch (e.getButton() ) {
            case MouseEvent.BUTTON1:setMouseLeftPressed(true); break;
            case MouseEvent.BUTTON3:setMouseRightPressed(true); break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton() ) {
            case MouseEvent.BUTTON1:setMouseLeftPressed(false); break;
            case MouseEvent.BUTTON3:setMouseRightPressed(false); break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean isMouseRightPressed() { return mouseRightPressed; }

    public void setMouseRightPressed(boolean mouseRightPressed) { this.mouseRightPressed = mouseRightPressed; }

    public boolean isMouseLeftPressed() { return mouseLeftPressed; }

    public void setMouseLeftPressed(boolean mouseLeftPressed) { this.mouseLeftPressed = mouseLeftPressed ;}

    public double getAngle(MouseEvent event, int x, int y) {
        double xDist = (event.getX()-x);
        double yDist = (event.getY()-y);
        return (Math.atan2(yDist, xDist) ); // return the angle of the player object to the mouse
    }
}
