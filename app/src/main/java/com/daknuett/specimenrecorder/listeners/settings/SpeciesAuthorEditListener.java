package com.daknuett.specimenrecorder.listeners.settings;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daknuett.specimenrecorder.model.settings.SettingsStorageManager;

import java.io.IOException;

/**
 * Created by daniel on 05.08.17.
 */

public class SpeciesAuthorEditListener
implements TextView.OnEditorActionListener
{
        private SettingsStorageManager storageManager;
    private AppCompatActivity activity;

    public SpeciesAuthorEditListener(SettingsStorageManager storageManager, AppCompatActivity activity) {
        this.storageManager = storageManager;
        this.activity = activity;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {

        switch (actionId)
        {
            case EditorInfo.IME_ACTION_DONE:
            case EditorInfo.IME_ACTION_GO:
            case EditorInfo.IME_ACTION_NEXT:
            case EditorInfo.IME_ACTION_SEND:
            case EditorInfo.IME_ACTION_SEARCH:
            case EditorInfo.IME_ACTION_UNSPECIFIED: {
                saveSpeciesAuthor((EditText) v);
                return true;
            }
        }
        return false;

    }


    private void saveSpeciesAuthor(EditText editText)
    {
        storageManager.speciesSettings.author = editText.getText().toString();

        try {
            storageManager.dump();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(), "Unable to store settings", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(activity.getApplicationContext(),
                "Stored species author: " + storageManager.speciesSettings.author,
                Toast.LENGTH_SHORT).show();
    }

}
