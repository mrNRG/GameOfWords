package mr.nrg.ua.gameofwords.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import mr.nrg.ua.gameofwords.R;
import mr.nrg.ua.gameofwords.holders.SettingsViewHolder;
import mr.nrg.ua.gameofwords.logic.SettingsLogic;
import mr.nrg.ua.gameofwords.utils.Constants;
import mr.nrg.ua.gameofwords.utils.SharedPreferencesWorker;

/**
 * Created by mrNRG on 29.10.2015.
 */
public class SettingsFragment extends Fragment {

    private SettingsViewHolder svHolder;
    private SharedPreferencesWorker spWorker;
    private SettingsLogic settingsLogic;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        spWorker = new SharedPreferencesWorker(getActivity().getSharedPreferences(Constants.APP_PREFERENCES,
                Context.MODE_PRIVATE));

        settingsLogic = new SettingsLogic(getActivity(), spWorker);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        svHolder = SettingsViewHolder.getInstance();

        svHolder.setWordsList((ListView) rootView.findViewById(R.id.fragment_settings));
        svHolder.getWordsList().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.words, android.R.layout.simple_list_item_multiple_choice);
        svHolder.getWordsList().setAdapter(adapter);
        settingsLogic.loadSharedPreferences();


        setHasOptionsMenu(true);
        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_settings, menu);
        settingsLogic.setMenu(menu);
        settingsLogic.verifySelection();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.selection:
                if (item.getTitle().toString().equals(getString(R.string.select_all))) {
                    settingsLogic.selectAll(item);
                } else
                if (item.getTitle().toString().equals(getString(R.string.deselect_all))) {
                    settingsLogic.deselectAll(item);
                }
                break;
            case android.R.id.home:
                settingsLogic.saveSharedPreferences();
                getActivity().finish();
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        settingsLogic.saveSharedPreferences();
    }
}
