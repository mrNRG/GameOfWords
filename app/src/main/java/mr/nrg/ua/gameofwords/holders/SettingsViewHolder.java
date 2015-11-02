package mr.nrg.ua.gameofwords.holders;

import android.widget.ListView;

/**
 * Created by mrNRG on 30.10.2015.
 */
public class SettingsViewHolder {
    private ListView wordsList;

    private static SettingsViewHolder instance;

    private SettingsViewHolder() {
    }

    public static SettingsViewHolder getInstance() {
        if (instance == null) {
            instance = new SettingsViewHolder();
        }
        return instance;
    }

    public ListView getWordsList() {
        return wordsList;
    }

    public void setWordsList(ListView wordsList) {
        this.wordsList = wordsList;
    }
}
