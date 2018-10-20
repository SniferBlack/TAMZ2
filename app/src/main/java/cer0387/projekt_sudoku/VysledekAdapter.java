package cer0387.projekt_sudoku;

import java.util.List;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cer0387.projekt_sudoku.Vysledek;

public class VysledekAdapter extends ArrayAdapter<Vysledek>{

    Context context;
    int layoutResourceId;
    List<Vysledek> data = null;


    public VysledekAdapter(Context context, int layoutResourceId, List<Vysledek> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EntryHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new EntryHolder();
            holder.txtNick = (TextView)row.findViewById(R.id.txtNick);
            holder.txtSkore = (TextView)row.findViewById(R.id.txtSkore);

            row.setTag(holder);
        }
        else
        {
            holder = (EntryHolder)row.getTag();
        }

        Vysledek vysledky = data.get(position);
        holder.txtNick.setText(vysledky.nick);
        holder.txtSkore.setText(String.valueOf(vysledky.skore/1000)+" sekund");

        return row;
    }

    static class EntryHolder
    {
        TextView txtNick;
        TextView txtSkore;
    }
}
