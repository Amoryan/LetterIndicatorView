package com.fxyan.letterindicatorview.adapter.wechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fxyan.letterindicatorview.R;
import com.fxyan.letterindicatorview.adapter.BaseRecyclerAdapter;
import com.fxyan.letterindicatorview.entity.wechat.WeChatContactItem;
import com.fxyan.letterindicatorview.widget.RoundImageView;

/**
 * @author fxYan
 */
public final class WeChatAdapter extends BaseRecyclerAdapter<WeChatContactItem, WeChatAdapter.ViewHolder> {

    private int[] res = {
            R.mipmap.i1,
            R.mipmap.i2,
            R.mipmap.i3,
            R.mipmap.i4,
            R.mipmap.i5,
            R.mipmap.i6,
            R.mipmap.i7,
            R.mipmap.i8,
            R.mipmap.i9,
            R.mipmap.i10
    };

    public WeChatAdapter(@NonNull Context context) {
        super(context, R.layout.listitem_wechat_contact);
    }

    @Override
    protected ViewHolder createItemViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindData(ViewHolder h, WeChatContactItem obj, int position) {
        int i = position % 10;
        h.photo.setImageResource(res[i]);
        h.name.setText(obj.getName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RoundImageView photo;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photoIv);
            name = itemView.findViewById(R.id.nameTv);
        }
    }
}
