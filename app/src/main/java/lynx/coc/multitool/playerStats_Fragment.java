package lynx.coc.multitool;

import static lynx.coc.multitool.url_LYNX.getContent_LYNX;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Self Explanatory
 */

public class playerStats_Fragment extends Fragment {
    Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playerstats_lynx, parent, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        final Spinner player_min_league = view.findViewById(R.id.player_min_league);
        final EditText player_Tag = view.findViewById(R.id.player_Tag);
        final EditText to = view.findViewById(R.id.editText3);
        final Button search_Tag = view.findViewById(R.id.search_Tag);
        String[] arraySpinner = new String[]{"Any",
                "Unranked",
                "Bronze League III",
                "Bronze League II",
                "Bronze League I",
                "Silver League III",
                "Silver League II",
                "Silver League I",
                "Gold League III",
                "Gold League II",
                "Gold League I",
                "Crystal League III",
                "Crystal League II",
                "Crystal League I",
                "Master League III",
                "Master League II",
                "Master League I",
                "Champion League III",
                "Champion League II",
                "Champion League I",
                "Titan League III",
                "Titan League II",
                "Titan League I",
                "Legend League"};
        Drawable spinnerDrawable = player_min_league.getBackground().getConstantState().newDrawable();
        spinnerDrawable.setColorFilter(Color.parseColor("#f62222"), PorterDuff.Mode.SRC_ATOP);
        player_min_league.setBackground(spinnerDrawable);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, arraySpinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        player_min_league.setAdapter(adapter);
        PushDownAnim.e(search_Tag).i(view1 -> Log.wtf("player", getContent_LYNX("#Y2CG20QG")));
    }
}
