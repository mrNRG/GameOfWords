package mr.nrg.ua.gameofwords.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import mr.nrg.ua.gameofwords.R;
import mr.nrg.ua.gameofwords.activities.SettingsActivity;
import mr.nrg.ua.gameofwords.holders.MainViewHolder;
import mr.nrg.ua.gameofwords.logic.GameLogic;
import mr.nrg.ua.gameofwords.logic.SettingsLogic;
import mr.nrg.ua.gameofwords.utils.Constants;
import mr.nrg.ua.gameofwords.utils.SharedPreferencesWorker;

public class MainFragment extends Fragment {

    private SharedPreferencesWorker spWorker;
    private MainViewHolder mainViewHolder;
    private GameLogic gameLogic;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        spWorker = new SharedPreferencesWorker(getActivity().getSharedPreferences(Constants.APP_PREFERENCES,
                Context.MODE_PRIVATE));
        mainViewHolder = new MainViewHolder();
        gameLogic = new GameLogic(getActivity(), spWorker, mainViewHolder);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);

        mainViewHolder.setTopView((TextView) rootView.findViewById(R.id.topView));
        mainViewHolder.setTextInput((EditText) rootView.findViewById(R.id.textInput));
        mainViewHolder.getTextInput().setSelectAllOnFocus(true);
        mainViewHolder.setTimerCount((TextView) rootView.findViewById(R.id.timerCount));
        mainViewHolder.setTextRepeat((TextView) rootView.findViewById(R.id.textRepeat));
        mainViewHolder.setScore((TextView) rootView.findViewById(R.id.score));
        mainViewHolder.setLives((TextView) rootView.findViewById(R.id.lives));

        gameLogic.run();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        gameLogic.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        gameLogic.onResume();
    }
}
