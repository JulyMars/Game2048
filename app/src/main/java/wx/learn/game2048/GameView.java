package wx.learn.game2048;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiong on 2017/3/18.
 */

public class GameView extends GridLayout {

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();

    private void initGameView() {
        setColumnCount(4);
        setBackgroundColor(0xffeeeeee);

        setOnTouchListener(new OnTouchListener() {
            private float evtx, evty, offsetx, offsety;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        evtx = event.getX();
                        evty = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetx = event.getX() - evtx;
                        offsety = event.getY() - evty;
                        if (Math.abs(offsetx) >= Math.abs(offsety)) {
                            if (offsetx < -5) {
                                swipLeft();
                            } else if (offsetx > 5) {
                                swipRight();
                            }
                        } else {
                            if (offsety < -5) {
                                swipUp();
                            } else if (offsety > 5) {
                                swipDown();
                            }
                        }

                        break;
                }
                return true;
            }
        });
    }

    private void swipDown() {
        boolean changed = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() == 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            changed = true;
                        } else if (cardsMap[x][y].getNum() == cardsMap[x][y1].getNum()) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
                            cardsMap[x][y1].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum() * 2);
                            changed = true;
                        }
                        break;
                    }
                }
            }
        }
        if (changed) {
            addRandomPoint();
            checkGameOver();
        }
    }

    private void swipUp() {
        boolean changed = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() == 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            changed = true;
                        } else if (cardsMap[x][y].getNum() == cardsMap[x][y1].getNum()) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
                            cardsMap[x][y1].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum() * 2);
                            changed = true;
                        }
                        break;
                    }
                }
            }
        }
        if (changed) {
            addRandomPoint();
            checkGameOver();
        }
    }

    private void swipRight() {
        boolean changed = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() == 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            changed = true;
                        } else if (cardsMap[x][y].getNum() == cardsMap[x1][y].getNum()) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum() * 2);
                            changed = true;
                        }
                        break;
                    }
                }
            }
        }
        if (changed) {
            addRandomPoint();
            checkGameOver();
        }
    }

    private void swipLeft() {
        boolean changed = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() == 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            changed = true;
                        } else if (cardsMap[x][y].getNum() == cardsMap[x1][y].getNum()) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum() * 2);
                            changed = true;
                        }
                        break;
                    }
                }
            }
        }
        if (changed) {
            addRandomPoint();
            checkGameOver();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        System.out.println("onSizeChanged 1");

        int cardW = (Math.min(w, h) - 10) / 4;
        addCards(cardW, cardW);

        startGame();

        System.out.println("onSizeChanged 2");
    }

    private void addCards(int cardW, int cardH) {
        Card c;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                addView(c, cardW, cardH);
                cardsMap[x][y] = c;
            }
        }
    }

    private void addRandomPoint() {
        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        Point p = emptyPoints.get((int) (Math.random() * emptyPoints.size()));
        Card c = cardsMap[p.x][p.y];
        c.setNum(Math.random() > 0.2 ? 2 : 4);
    }

    private void startGame() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomPoint();
        addRandomPoint();
    }

    private void checkGameOver() {
        boolean over = true;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0 ||
                        (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y])) ||
                        (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y])) ||
                        (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1])) ||
                        (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))
                        ) {
                    over = false;
                }
            }
        }
        if (over) {
            MainActivity.getMainActivity().clearScore();
            startGame();
        }
    }

}