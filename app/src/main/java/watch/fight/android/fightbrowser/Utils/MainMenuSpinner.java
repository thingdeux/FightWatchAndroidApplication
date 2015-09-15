package watch.fight.android.fightbrowser.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/14/15.
 */
public class MainMenuSpinner implements AdapterViewCompat.OnItemSelectedListener {
    public static Spinner Setup(Context context, View v) {
        // Setup the main menu spinner
        Spinner spinner = (Spinner) v.findViewById(R.id.main_menu_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context, R.array.main_menu_options, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }

    public void onItemSelected(AdapterViewCompat<?> parent, View view, int pos, long id) {
        // An item was selected.
        // Get the item at this position
        parent.getItemAtPosition(pos);
        Log.e("JOSHDEV", "Touched: " + parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterViewCompat<?> parent) {
        // Intentionally left blank
    }
}
