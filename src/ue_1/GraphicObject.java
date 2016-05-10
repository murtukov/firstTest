package ue_1;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

abstract class GraphicObject {
    static boolean gravity = false;             // гравитация
    static boolean disappear = false;           // исчезновение
    static int counter     = 0;                 // счетчик объектов
    static Coord maxCoord  = new Coord(1, 1);   // мак. координата (позиция)
    static Coord minCoord  = new Coord(2, 2);   // минимальная координата
    Coord position;                             // позиция объекта
    protected Coord step   = new Coord();       // шаг
    protected float   size   = 100;             // размер
    protected Color color  = Color.BLUE;        // цвет
    private Random rdm     = new Random();      // генератор случ. чисел

    // конструктор без параметров по умолчанию)
    public GraphicObject() {
        // установить случайную позицию
        position = new Coord(rdm.nextInt((int) maxCoord.getX()),
                               rdm.nextInt((int) maxCoord.getY()));
        
        // установить случайное значение шага
        step.setX((rdm.nextInt(50)-25) * rdm.nextFloat());
        step.setY((rdm.nextInt(50)-25) * rdm.nextFloat());
        
        // установить цвет
        int red     = rdm.nextInt(255);
        int green   = rdm.nextInt(255);
        int blue    = rdm.nextInt(255);
        color = new Color(red, green, blue);
        
        size  = 5; // установить размер по умолчанию
        counter++; // увеличить количество обхектов на 1
    }

    // конструктор с параметрами (перегруженный конструктор)
    public GraphicObject(Coord p, int size) {
        this(); // вызвать конструктор по умолчанию
        
        // установить позицию
        position.setX(p.getX());
        position.setY(p.getY());
        
        // установить размер
        this.size = size;
    }

    // выполнить шаг (движение объекта)
    public void doStep() {
        // если гравитация включена
        if(gravity) {
            step.setY(step.getY()+1);  // увеличить шаг на 1      
        }
        // если включено исчезновение
        if(disappear) {
            size *= 0.92; // умножить размер на 0.92 (т.е. уменьшить на 0.8%)
        }
        
        // проверка выхода объекта за установленные границы (макс. и мин. коорд)
        if(!coordCheck()) {
            coordMove(step.getX(), step.getY());
        } 
    }

    // алгоритмы преломления движения и уменьшения кинетической энергии от удара
    public boolean coordCheck() {
        // промежуток между нижней границей окна и объектом. Если число
        // положительное, то объект вышел за пределы допустимого11
        int bOuter = (int)(position.getY() + size + step.getY() - maxCoord.getY());
        
        // аналогично для остальных трех сторон окна
        int rOuter = (int)(position.getX() + size + step.getX() - maxCoord.getX());
        int lOuter = (int)(position.getX() + step.getX() - minCoord.getX());
        int tOuter = (int)(position.getY() + step.getY() - minCoord.getY());
        
        if(bOuter > 0) {
            int delta = (int)(maxCoord.getY() - bOuter - position.getY() - size);
            step.setY(-step.getY() / 1.2F); // отразить, уменьшить кин. энергию по оси Y
            coordMove(step.getX(), delta); // сделать вычисленный преломленный шаг
            step.setX(step.getX() * 0.9F); // уменьшить кин. энергию по оси Х
            return true;
        }
        if (rOuter > 0) {
            int delta = (int)(maxCoord.getX() - rOuter - position.getX() - size);
            step.setX(-step.getX() / 1.5F);
            coordMove(delta, step.getY());
            return true;
//            step.setX(step.getX() * 0.9);
        }
        if (lOuter < 0) {
            step.setX(-step.getX() / 1.5F);
            coordMove(-lOuter, step.getY());
//            step.setX(step.getX() * 0.9);
            return true;
        }
        if (tOuter < 0) {
            step.setY(-step.getY() / 1.5F);
            coordMove(step.getX(), -tOuter);
//            step.setX(step.getX() * 0.9);
            return true;
        }
        return false;
    }

    public void coordReplace(Coord p) {
        position.setX((int)p.getX());
        position.setY((int)p.getY());
    }
    
    public void coordMove(float x, float y) {
        position.setX(position.getX() + x);
        position.setY(position.getY() + y);
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public Coord coordGet() {
        return position;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getSize() {
        return size;
    }

    /**
     * - bu Methode — wir sind ängstlich, aber brav! :-D ----------
     */
    protected void bu(Coord p) {
        double distance = p.getDistance(this.position);
        if (distance > 3 * this.size) {
            size *= 2;
        } else if (distance < this.size / 2) {
            size /= 2;
        }
    }

    public abstract void draw(Graphics g);
}
