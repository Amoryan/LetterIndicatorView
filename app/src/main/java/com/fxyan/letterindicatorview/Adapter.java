package com.fxyan.letterindicatorview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fxyan.letterindicatorview.adapter.BaseRecyclerAdapter;

/**
 * @author fxYan
 */
public final class Adapter extends BaseRecyclerAdapter<String, Adapter.ViewHolder> {

    public Adapter(@NonNull Context context) {
        super(context, R.layout.listitem);
    }

    @Override
    protected ViewHolder createItemViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindData(ViewHolder h, String obj, int position) {
        h.nameTv.setText(obj);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
        }
    }
}
