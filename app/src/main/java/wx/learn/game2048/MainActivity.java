package wx.learn.game2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView tvScore;
    private int score = 0;

    private static MainActivity mainActivity = null;

    public MainActivity(){
        System.out.println("MainActivity");
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate 1");
        super.onCreate(savedInstanceState);
        System.out.println("onCreate 2");
        setContentView(R.layout.activity_main);
        System.out.println("onCreate 3");

        System.out.println("onCreate 4");
        tvScore = (TextView) findViewById(R.id.tvScore);
        System.out.println("onCreate 5");

        showScore();
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void addScore(int s) {
        score += s;
        showScore();
    }

    private void showScore() {
        tvScore.setText(score+"");
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }
}
