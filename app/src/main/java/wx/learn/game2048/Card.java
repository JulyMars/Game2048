package wx.learn.game2048;

import android.app.Fragment;
import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by wangxiong on 2017/3/18.
 */

public class Card extends FrameLayout {
    private int num;
    private TextView tv;

    public Card(Context context) {
        super(context);

        tv = new TextView(getContext());
        tv.setTextSize(32);
        tv.setBackgroundColor(0xff00ee00);
        tv.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(tv, lp);

        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num <= 0) {
            tv.setText("");
        } else {
            tv.setText(num + "");
        }
    }

    public boolean equals(Card c) {
        return c.getNum() == getNum();
    }

}