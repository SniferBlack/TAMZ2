package cer0387.projekt_sudoku;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Bunky extends Fragment {
    private int groupID;
    private OnFragmentInteractionListener myListener;
    private View view;

    public Bunky() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.bunky, container, false);

        int textViews[] = new int[]{    R.id.textView1,
                                        R.id.textView2,
                                        R.id.textView3,
                                        R.id.textView4,
                                        R.id.textView5,
                                        R.id.textView6,
                                        R.id.textView7,
                                        R.id.textView8,
                                        R.id.textView9
        };

        for (int textView1 : textViews) {
            TextView textView = view.findViewById(textView1);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myListener.onFragmentInteraction(groupID, Integer.parseInt(view.getTag().toString()), view);
                }
            });
        }

        return view;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setHodnota(int pozice, int hodnota) {
        int textViews[] = new int[]{    R.id.textView1,
                R.id.textView2,
                R.id.textView3,
                R.id.textView4,
                R.id.textView5,
                R.id.textView6,
                R.id.textView7,
                R.id.textView8,
                R.id.textView9
        };
        TextView textView = view.findViewById(textViews[pozice]);
        textView.setText(String.valueOf(hodnota));
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(null, Typeface.BOLD);
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int groupId, int cellId, View view);
    }
}
