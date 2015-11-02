package mr.nrg.ua.gameofwords.holders;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mrNRG on 29.10.2015.
 */
public class MainViewHolder {
    private TextView topView;
    private EditText textInput;
    private TextView timerCount;
    private TextView textRepeat;
    private TextView score;
    private TextView lives;


    public TextView getTopView() {
        return topView;
    }

    public void setTopView(TextView topView) {
        this.topView = topView;
    }

    public EditText getTextInput() {
        return textInput;
    }

    public void setTextInput(EditText textInput) {
        this.textInput = textInput;
    }

    public TextView getTextRepeat() {
        return textRepeat;
    }

    public void setTextRepeat(TextView textRepeat) {
        this.textRepeat = textRepeat;
    }

    public TextView getScore() {
        return score;
    }

    public void setScore(TextView score) {
        this.score = score;
    }

    public TextView getLives() {
        return lives;
    }

    public void setLives(TextView lives) {
        this.lives = lives;
    }

    public TextView getTimerCount() {
        return timerCount;
    }

    public void setTimerCount(TextView timerCount) {
        this.timerCount = timerCount;
    }
}
