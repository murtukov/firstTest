package ue_1;

import java.awt.Color;
import java.awt.Graphics;

/**
 * =Klasse Dot - ein gefültes Zirkel =========================
 */
class Dot extends GraphicObject {
    

    public Dot() {
        super();
    }

    public Dot(Coord p, int size) {
        super(p, size);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int)position.getX(), (int)position.getY(), (int)size, (int)size);
        g.setColor(Color.BLACK);
//        g.drawString("#" + exemplarID + "of" + counter,
//                (int) position.getX() - 15, (int) position.getY());
    }
}
