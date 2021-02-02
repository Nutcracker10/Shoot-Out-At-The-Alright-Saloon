import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
    private boolean mouseLeftClicked = false;
    private boolean mouseRightClicked = false;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public double getAngle(MouseEvent event, int x, int y) {
        double xDist = (event.getX()-x);
        double yDist = (event.getY()-y);
        return (Math.atan2(yDist, xDist) ); // return the angle of the player object to the mouse
    }
}
