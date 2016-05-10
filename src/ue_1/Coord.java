package ue_1;


class Coord {

    private float x;
    private float y;

    float getX() {
        return this.x;
    }

    float getY() {
        return this.y;
    }

    void setX(float x) {
        this.x = x;
    }

    void setY(float y) {
        this.y = y;
    }

    public Coord() {
        x = 0;
        y = 0;
    }

    public Coord(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coord(Coord p) {
        this.x = p.getX();
        this.y = p.getY();
    }
    // Евклидово расстояние
    public double getDistance(Coord that) {
        float dx, dy;
        dx = this.x - that.getX();
        dy = this.y - that.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}