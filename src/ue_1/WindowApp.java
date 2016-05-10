package ue_1;
// импортировать библиотеки
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.LinkedList;
import static ue_1.GraphicObject.maxCoord;
import static ue_1.GraphicObject.minCoord;
import static ue_1.GraphicObject.gravity;
import static ue_1.GraphicObject.disappear;
import static ue_1.GraphicObject.counter;

class WindowApp extends JFrame {

    private Coord clickCoord = new Coord();
    
    // Связный список (структура данных, в которй хранятся все объекты фигур)
    LinkedList<Dot> fwDots = new LinkedList();

    // конструктор с одним параметром
    public WindowApp(String s) {
        // вызвать конструктор родительского класса
        super(s);
        
        // установить обработчики событий (слушатели)
        addWindowListener(new MyWindowsListener());
        addMouseListener(new MyMouseListener());

        // Запускать лямбда-выражение каждые 30 милисекунд
        new Timer(30, (e) -> {
            // установить значения мак. и мин. координат
            maxCoord.setX(getWidth() - this.getInsets().right);
            maxCoord.setY(getHeight() - this.getInsets().bottom);
            minCoord.setX(this.getInsets().left);
            minCoord.setY(this.getInsets().top);
            
            // Объект для итерации через связный список
            Iterator<Dot> i = fwDots.iterator();
            // сделать шаг, либо удалить объект, если меньше 1 пикселя
            while(i.hasNext()) {
                Dot dot = i.next();
                if(dot.size <= 1) {
                    i.remove();
                    GraphicObject.counter--;
                } else {
                    dot.doStep();
                }
            }
            
            // альтернативный путь для итерации через список (без удаления)
//            for (Dot d : fwDots) {
//                d.doStep();
//            }
            
            // осуществить рендеринг (перерисовка граф. содержимого окна)
            repaint();
        }).start();
    }

    @Override
    public void paint(Graphics g) {
        // очистить окно
        super.paint(g);

        // прорисовать фигуры из связного списка
        for (Dot d : fwDots) {
            d.draw(g);
        }
        
        // прорисовать тексты
        g.drawString("counter: " + counter,   20, 50);
        g.drawString("gravity: " + gravity,   20, 65);
        g.drawString("vanish: "  + disappear, 20, 80);
    }

    // обработчики событий мыши
    private class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent ev) {
        }

        @Override
        public void mousePressed(MouseEvent ev) { // при нажатии кнопки мыши
            // получить координаты нажатия
            clickCoord.setX(ev.getX());
            clickCoord.setY(ev.getY());
            
            // определить какая клавиша нажата
            if(ev.getButton() == ev.BUTTON1) 
                // если левая, создать 100 нов. фигур
                for (int i = 0; i < 100; i++) {
                    fwDots.add(new Dot(clickCoord, 10));
                }
                // если правая, переключить режим исчезновения между ВКЛ и ВЫКЛ
                else if(ev.getButton() == ev.BUTTON3) {
                    disappear = !disappear;
                }
        }

        @Override
        public void mouseReleased(MouseEvent ev) {
        }

        @Override
        public void mouseExited(MouseEvent ev) {
            // включить гравитацию
            gravity = true;
        }

        @Override
        public void mouseEntered(MouseEvent ev) {
            // выключить гравитацию
            gravity = false;
            
        }
    }
    
    // обработчики событий окна
    private class MyWindowsListener extends WindowAdapter {

        // при закрытии окна, завершить приложение
        @Override
        public void windowClosing(WindowEvent ev) {
            System.exit(0);
        }
    }
}
