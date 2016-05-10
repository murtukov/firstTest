package ue_1;

import java.awt.Color;
import java.awt.Graphics;

/**
 * = Klasse Circle - ein leeres Zirkel ==================
 */
class Circle extends GraphicObject {

    public Circle() {
        super();
    }

    public Circle(Coord p, int size) {
        super(p, size);
    }

    @Override
    public void draw(Graphics g) {
        g.drawOval((int)position.getX(), (int)position.getY(), (int)size, (int)size);
        g.setColor(Color.BLACK);
//        g.drawString("#" + exemplarID + "of" + counter,
//                (int) position.getX() - 15, (int) position.getY());
    }
}
