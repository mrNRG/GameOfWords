package mr.nrg.ua.gameofwords.utils;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by mrNRG on 30.10.2015.
 */
public class SharedPreferencesWorker {

    private SharedPreferences sharedPreferences;

    public SharedPreferencesWorker(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveSharedPreferences(List<Boolean> checkBoxStatusList) {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < checkBoxStatusList.size(); i++) {
            if (checkBoxStatusList.get(i)) {
                set.add(Integer.toString(i));
            }
        }
        sharedPreferences.edit().putStringSet(Constants.PREFERENCES_KEY, set).apply();

    }

    public List<Boolean> loadSharedPreferences(List<Boolean> checkBoxStatusList) {
        List<Integer> allowedIndexes = getAllowedWords();
        for (int index = 0; index < allowedIndexes.size(); index++) {
            checkBoxStatusList.set(allowedIndexes.get(index), true);
        }
        return checkBoxStatusList;
    }

    public List<Integer> getAllowedWords() {
        List<Integer> allowedIndexes = new ArrayList<Integer>();
        if (sharedPreferences.contains(Constants.PREFERENCES_KEY)) {
            Set<String> set = sharedPreferences.getStringSet(Constants.PREFERENCES_KEY, new HashSet<String>());
            for (String aSet : set) {
                allowedIndexes.add(Integer.parseInt(aSet));
            }
        }
        return allowedIndexes;
    }
}
