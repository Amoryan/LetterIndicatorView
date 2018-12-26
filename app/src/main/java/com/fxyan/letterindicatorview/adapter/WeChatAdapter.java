package com.fxyan.letterindicatorview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fxyan.letterindicatorview.R;
import com.fxyan.letterindicatorview.entity.WeChatContactItem;

/**
 * @author fxYan
 */
public final class WeChatAdapter extends BaseRecyclerAdapter<WeChatContactItem, WeChatAdapter.ViewHolder> {

    public WeChatAdapter(@NonNull Context context) {
        super(context, R.layout.listitem_wechat_contact);
    }

    @Override
    protected ViewHolder createItemViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindData(ViewHolder h, WeChatContactItem obj, int position) {
        h.name.setText(obj.getName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTv);
        }
    }
}
