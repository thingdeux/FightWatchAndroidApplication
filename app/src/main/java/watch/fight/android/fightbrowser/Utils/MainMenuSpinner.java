package watch.fight.android.fightbrowser.Utils;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import watch.fight.android.fightbrowser.R;

@Deprecated
public class MainMenuSpinner {
    public static Spinner Setup(Context context, View v) {
        // Setup the main menu spinner
        Spinner spinner = (Spinner) v.findViewById(R.id.main_menu_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context, R.array.main_menu_options, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }
}
