package com.example.trabalhoavaliativopdm2.adapter;

import android.content.Context;
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

    public RankAdapter(List<RankScore> listRAnk, Context context) {
        this.listRAnk = listRAnk;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View myView = LayoutInflater.from(context).inflate(R.layout.layout_rank, viewGroup, false);

        TextView nameUser = myView.findViewById(R.id.nameUser);
        nameUser.setText("Nome: "+listRAnk.get(i).getUserNikeName());
        TextView score = myView.findViewById(R.id.scores);
        score.setText("Score: "+listRAnk.get(i).getScore());

        return myView;
    }
}
