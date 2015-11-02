package mr.nrg.ua.gameofwords.logic;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mr.nrg.ua.gameofwords.R;
import mr.nrg.ua.gameofwords.holders.SettingsViewHolder;
import mr.nrg.ua.gameofwords.utils.SharedPreferencesWorker;

/**
 * Created by mrNRG on 30.10.2015.
 */
public class SettingsLogic {

    private SettingsViewHolder svHolder;
    private List<Boolean> checkBoxStatusList;
    private SharedPreferencesWorker spWorker;
    private Context context;
    private MenuItem item;

    public void setMenu(Menu menu) {
        this.item = menu.findItem(R.id.selection);
    }

    private List<String> words = new ArrayList<String>();
    private int checkedWords = 0;
    private int size;

    public SettingsLogic(Context context, SharedPreferencesWorker spWorker) {
        this.svHolder = SettingsViewHolder.getInstance();
        this.spWorker = spWorker;
        this.context = context;
        this.words.addAll(Arrays.asList(context.getResources().getStringArray(R.array.words)));
        this.size = words.size();
        this.checkBoxStatusList = new ArrayList<>(size);
        this.spWorker = spWorker;

        for (int i = 0; i < size; i++) {
            checkBoxStatusList.add(false);
        }
    }


    public void saveSharedPreferences() {
        SparseBooleanArray arr = svHolder.getWordsList().getCheckedItemPositions();
        clearChBoxList();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.valueAt(i)) {
                checkBoxStatusList.set(arr.keyAt(i), true);
            }
        }
        spWorker.saveSharedPreferences(checkBoxStatusList);
    }

    public void loadSharedPreferences() {
        checkBoxStatusList = spWorker.loadSharedPreferences(checkBoxStatusList);
        for (int i = 0; i < size; i++)
            if (checkBoxStatusList.get(i)) {
                svHolder.getWordsList().setItemChecked(i, true);
                checkedWords++;
            }
    }

    public void selectAll(MenuItem item) {
        for (int i = 0; i < checkBoxStatusList.size(); i++) {
            svHolder.getWordsList().setItemChecked(i, true);
        }
        item.setTitle(R.string.deselect_all);
    }

    public void deselectAll(MenuItem item) {
        svHolder.getWordsList().clearChoices();
        svHolder.getWordsList().requestLayout();
        item.setTitle(R.string.select_all);
    }

    private void clearChBoxList() {
        for (int i = 0; i < checkBoxStatusList.size(); i++) {
            checkBoxStatusList.set(i, false);
        }
    }

    public void verifySelection() {
        if (checkedWords == size) {
            item.setTitle(R.string.deselect_all);
        }
    }


}
