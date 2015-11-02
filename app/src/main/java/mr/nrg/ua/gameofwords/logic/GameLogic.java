package mr.nrg.ua.gameofwords.logic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mr.nrg.ua.gameofwords.R;
import mr.nrg.ua.gameofwords.holders.MainViewHolder;
import mr.nrg.ua.gameofwords.utils.Constants;
import mr.nrg.ua.gameofwords.utils.SharedPreferencesWorker;

/**
 * Created by mrNRG on 30.10.2015.
 */
public class GameLogic {
    private Context context;
    private SettingsLogic settingsLogic;
    private SharedPreferencesWorker spWorker;
    private MainViewHolder mainViewHolder;
    private List<String> words = new ArrayList<>();
    private List<Integer> allowedWordIndexes;
    private Random random;
    private CountDownTimer cdTimer;
    private boolean isTimerStarted;
    private int lives;
    private int score;


    public GameLogic(Context context, SharedPreferencesWorker spWorker, MainViewHolder mainViewHolder) {
        this.context = context;
        this.mainViewHolder = mainViewHolder;
        this.spWorker = spWorker;
        this.words.addAll(Arrays.asList(context.getResources().getStringArray(R.array.words)));
        this.random = new Random();
        initGame();
    }

    private void initGame() {
        timerInit();
        varsInit();
    }

    private void timerInit() {
        cdTimer = new CountDownTimer(Constants.TIMER_IN_FUTURE, Constants.TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isTimerStarted) {
                    mainViewHolder.getTimerCount().setText(String.valueOf(millisUntilFinished / Constants.TIMER_INTERVAL));
                }
            }

            @Override
            public void onFinish() {
                if (isTimerStarted) {
                    lose();
                }
            }
        };
    }


    private void varsInit() {
        lives = Constants.LIVES;
        score = Constants.SCORE;
    }


    public void run() {
        mainViewHolder.getTextInput().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUpdate();
            }
        });


        mainViewHolder.getTextInput().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!isTimerStarted) {
                    if (checkAllowedWords()) {
                        getNextWord();
                        startTimer();
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mainViewHolder.getTopView().setText(mainViewHolder.getTextInput().getText());
                String repeatText = (String) mainViewHolder.getTextRepeat().getText();
                if (repeatText.length() == count) {
                    if (repeatText.equals(s.toString())) {
                        win();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void reset(){
        varsInit();
        viewUpdate();
        mainViewHolder.getTextInput().setText(R.string.some_word);
        mainViewHolder.getTextRepeat().setText(R.string.some_word);
        stopTimer();
        mainViewHolder.getTimerCount().setText(R.string.timer);
        mainViewHolder.getTextInput().selectAll();
        checkAllowedWords();
    }

    private void win() {
        stopTimer();
        score++;
        getNextWord();
        viewUpdate();
        startTimer();
    }

    private void lose() {
        stopTimer();
        lives--;
        viewUpdate();

        if (lives < 1) {
            stopTimer();
            showAlertMess();
        } else {
            startTimer();
        }
    }

    private void randomWord() {
        int i = 0;
        if (allowedWordIndexes.size() > 1) {
            i = random.nextInt(allowedWordIndexes.size() - 1);
        }
        mainViewHolder.getTextRepeat().setText(words.get(allowedWordIndexes.get(i)));
    }

    private void getNextWord() {
        randomWord();
    }

    private void viewUpdate() {
        mainViewHolder.getLives().setText(context.getString(R.string.lives_label) + lives);
        mainViewHolder.getScore().setText(context.getString(R.string.score_label) + score);
        mainViewHolder.getTextInput().setText(Constants.EMPTY);
    }

    private void startTimer() {
        isTimerStarted = true;
        cdTimer.start();
    }

    private void stopTimer() {
        isTimerStarted = false;
        cdTimer.cancel();
    }

    private void hideSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainViewHolder.getTextInput().getWindowToken(), 0);
    }

    private void showAlertMess() {
        hideSoftKeyboard(context.getApplicationContext());
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.game_over))
                .setMessage(context.getString(R.string.you_got)
                        + " " + score + " "
                        + context.getString(R.string.you_scores))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private boolean checkAllowedWords() {
        updateAllowedWords();
        if(allowedWordIndexes.size() == 0) {
            mainViewHolder.getTextRepeat().setText(context.getString(R.string.help_string));
            mainViewHolder.getTextInput().setEnabled(false);
            return false;
        }
        else {
            mainViewHolder.getTextInput().setEnabled(true);
            mainViewHolder.getTextRepeat().setText(context.getString(R.string.some_word));
            return true;
        }
    }

    private void updateAllowedWords() {
        allowedWordIndexes = spWorker.getAllowedWords();
    }

    public void onStop() {
        reset();
    }

    public void onResume() {
        checkAllowedWords();
    }

}
