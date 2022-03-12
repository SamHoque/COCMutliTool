package lynx.coc.multitool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

/**
 * Self Explanatory
 */

public class XP_Fragment extends Fragment {
    Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xp_calculator, parent, false);
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
        final Spinner s = view.findViewById(R.id.spinner);
        final EditText from = view.findViewById(R.id.editText2);
        final EditText to = view.findViewById(R.id.editText3);
        String[] arraySpinner = new String[]{"Total XP", "XP Per Level"};
        Drawable spinnerDrawable = s.getBackground().getConstantState().newDrawable();
        spinnerDrawable.setColorFilter(Color.parseColor("#f62222"), PorterDuff.Mode.SRC_ATOP);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            s.setBackground(spinnerDrawable);
        } else {
            s.setBackgroundDrawable(spinnerDrawable);
        }
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1) {
                    from.setVisibility(View.GONE);
                    to.setHint("Current XP");
                } else {
                    from.setVisibility(View.VISIBLE);
                    to.setHint("To XP");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        final TextView result = view.findViewById(R.id.textView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, arraySpinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        s.setAdapter(adapter);
        Button calc = view.findViewById(R.id.button);
        PushDownAnim.e(calc).i(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromText = from.getText().toString().trim();
                String toText = to.getText().toString().trim();
                if (s.getSelectedItemPosition() == 0) {
                    if (!fromText.isEmpty() && !toText.isEmpty()) {
                        long from = 0;
                        long to = 0;
                        try {
                            from = Long.parseLong(fromText);
                            to = Long.parseLong(toText);
                        } catch (NumberFormatException e) {
                            result.setText("Inputs can only be numbers!");
                            result.setTextColor(Color.RED);
                            return;
                        }
                        DecimalFormat df = new DecimalFormat("#,###");
                        long totalxp = 0;
                        while (from < to) {
                            totalxp += calculate_XP_PerLevel(from);
                            from++;
                        }
                        String resultXP = df.format(totalxp);
                        Spannable wordtoSpan = new SpannableString("You need " + resultXP + " experience to reach level " + toText + " from level " + fromText);
                        wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#41ABD3")), wordtoSpan.toString().indexOf(resultXP), wordtoSpan.toString().indexOf(resultXP) + resultXP.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#41ABD3")), wordtoSpan.toString().indexOf(toText), wordtoSpan.toString().indexOf(toText) + toText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#41ABD3")), wordtoSpan.toString().lastIndexOf(fromText), wordtoSpan.toString().lastIndexOf(fromText) + fromText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        result.setTextColor(Color.WHITE);
                        result.setText(wordtoSpan);
                    } else {
                        result.setText("Inputs cannot be blank!");
                        result.setTextColor(Color.RED);
                    }
                } else {
                    if (!toText.isEmpty()) {
                        if (!fromText.isEmpty() && !toText.isEmpty()) {
                            long to;
                            try {
                                to = Long.parseLong(toText);
                            } catch (NumberFormatException e) {
                                result.setText("Input can only be numbers!");
                                result.setTextColor(Color.RED);
                                return;
                            }
                            DecimalFormat df = new DecimalFormat("#,###");
                            String resultXP = df.format(calculate_XP_PerLevel(to));
                            Spannable wordtoSpan = new SpannableString("You need " + resultXP + " experience to reach level " + toText + " from level " + fromText);
                            wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#41ABD3")), wordtoSpan.toString().indexOf(resultXP), wordtoSpan.toString().indexOf(resultXP) + resultXP.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#41ABD3")), wordtoSpan.toString().indexOf(toText), wordtoSpan.toString().indexOf(toText) + toText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#41ABD3")), wordtoSpan.toString().lastIndexOf(toText), wordtoSpan.toString().lastIndexOf(toText) + toText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            result.setTextColor(Color.WHITE);
                            result.setText(wordtoSpan);
                        } else {
                            result.setText("Input cannot be blank!");
                            result.setTextColor(Color.RED);
                        }
                    }
                }
            }
        });
    }

    private static long calculate_XP_PerLevel(long xp) {
        if (xp == 0) {
            return 0;
        } else if (xp == 1) {
            return 30;
        } else if (xp < 202) {
            return (xp - 1) * 50;
        } else if (xp < 300) {
            return 10000 + ((xp - 201) * 500);
        } else {
            return 59000 + ((xp - 299) * 1000);
        }
    }
}
