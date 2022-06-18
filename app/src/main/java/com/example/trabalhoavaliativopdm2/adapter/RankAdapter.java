package com.example.trabalhoavaliativopdm2.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.trabalhoavaliativopdm2.R;
import com.example.trabalhoavaliativopdm2.RankScore;

import java.util.List;

public class RankAdapter extends BaseAdapter {
    private List<RankScore> listRAnk;
    private Context context;

    private String lastMove;

    public RankAdapter(List<RankScore> listRAnk, Context context, String lastMove) {
        this.listRAnk = listRAnk;
        this.context = context;
        this.lastMove = lastMove;
    }

    @Override
    public int getCount() {
        return listRAnk.size();
    }

    @Override
    public Object getItem(int i) {
        return listRAnk.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View myView = LayoutInflater.from(context).inflate(R.layout.layout_rank, null, false);

        TextView nameUser = myView.findViewById(R.id.nameUser);
        nameUser.setText("Nome: "+listRAnk.get(i).getUserNikeName());
        TextView score = myView.findViewById(R.id.scores);
        score.setText("Score: "+listRAnk.get(i).getScore());

        if (listRAnk.get(i).getDate().equals(lastMove)){
            nameUser.setTextColor(context.getResources().getColor(R.color.yellow, Resources.getSystem().newTheme()));
            score.setTextColor(context.getResources().getColor(R.color.yellow, Resources.getSystem().newTheme()));
        }

        return myView;
    }
}
