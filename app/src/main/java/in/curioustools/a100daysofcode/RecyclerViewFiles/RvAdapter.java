package in.curioustools.a100daysofcode.RecyclerViewFiles;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import in.curioustools.a100daysofcode.R;

public class RvAdapter extends RecyclerView.Adapter<RvHolderEachrow> {
    private LinkedList<RvMainData> datArr;

    public RvAdapter(LinkedList<RvMainData> datArr) {
        this.datArr = datArr;
    }

    @NonNull
    @Override
    public RvHolderEachrow onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v =LayoutInflater
                .from(parent.getContext()).inflate(R.layout.eachrow_rvmain,parent,false);
        return new RvHolderEachrow(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RvHolderEachrow rvHolderEachrow, int i) {
        rvHolderEachrow.bindData(datArr.get(i));

    }

    public LinkedList<RvMainData> getDatArr() {
        return datArr;
    }

    public void setDatArr(LinkedList<RvMainData> datArr) {
        this.datArr = datArr;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datArr.size();
    }
}
