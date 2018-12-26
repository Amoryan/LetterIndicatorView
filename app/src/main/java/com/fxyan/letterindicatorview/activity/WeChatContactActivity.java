package com.fxyan.letterindicatorview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.fxyan.decoration.DecorationConfig;
import com.fxyan.letterindicatorview.R;
import com.fxyan.letterindicatorview.Tools;
import com.fxyan.letterindicatorview.adapter.WeChatAdapter;
import com.fxyan.letterindicatorview.entity.WeChatContactRespBean;
import com.fxyan.letterindicatorview.entity.WeChatContactItem;
import com.fxyan.widget.LetterIndicatorView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fxYan
 */
public final class WeChatContactActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LetterIndicatorView indicatorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_contact);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#f1f1f1"));

        recyclerView = findViewById(R.id.recyclerView);
        indicatorView = findViewById(R.id.indicatorView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WeChatAdapter adapter = new WeChatAdapter(this);
        recyclerView.setAdapter(adapter);

        SparseArray<String> array = new SparseArray<>();
        DecorationConfig config = new DecorationConfig.Builder()
                .setSelectedTextColor(0x43, 0xc5, 0x61)
                .setUnSelectTextColor(0x64, 0x64, 0x64)
                .setSelectedBgColor(0xff, 0xff, 0xff)
                .setUnSelectBgColor(0xf1, 0xf1, 0xf1)
                .setTextXOffset(Tools.dp2px(this, 12))
                .setTextSize(Tools.dp2px(this, 14))
                .setHeight((int) Tools.dp2px(this, 30))
                .build();
        indicatorView.attachToRecyclerView(recyclerView, config, array);

        adapter.getDataSource().clear();
        ArrayList<String> titles = new ArrayList<>();
        Map<String, List<WeChatContactItem>> testData = getTestData();
        if (testData != null) {
            for (Map.Entry<String, List<WeChatContactItem>> entry : getTestData().entrySet()) {
                titles.add(entry.getKey());
                array.put(adapter.getHeadersCount() + adapter.getDataSource().size(), entry.getKey());
                adapter.getDataSource().addAll(entry.getValue());
            }
            indicatorView.setIndicators(titles);
        }
        adapter.notifyDataSetChanged();
    }

    private Map<String, List<WeChatContactItem>> getTestData() {
        BufferedReader bufr = null;
        WeChatContactRespBean bean = null;
        try {
            bufr = new BufferedReader(new InputStreamReader(getAssets().open("contact.json")));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufr.readLine()) != null) {
                builder.append(line);
            }
            String json = builder.toString();
            bean = new Gson().fromJson(json, WeChatContactRespBean.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufr != null) {
                try {
                    bufr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean == null ? null : bean.getData();
    }

}
