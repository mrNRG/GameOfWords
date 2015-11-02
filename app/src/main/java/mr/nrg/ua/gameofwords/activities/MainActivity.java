package mr.nrg.ua.gameofwords.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import mr.nrg.ua.gameofwords.R;
import mr.nrg.ua.gameofwords.fragments.MainFragment;
import mr.nrg.ua.gameofwords.fragments.SettingsFragment;
import mr.nrg.ua.gameofwords.utils.Constants;
import mr.nrg.ua.gameofwords.utils.SharedPreferencesWorker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
