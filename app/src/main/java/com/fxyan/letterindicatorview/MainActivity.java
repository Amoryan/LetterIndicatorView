package com.fxyan.letterindicatorview;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;

import com.fxyan.decoration.DecorationConfig;
import com.fxyan.widget.LetterIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LetterIndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        indicatorView = findViewById(R.id.indicatorView);

        final LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        final SparseArray<String> array = new SparseArray<>();
        DecorationConfig config = new DecorationConfig.Builder()
                .setSelectedTextColor(0x1b, 0x8f, 0xe6)
                .setUnSelectTextColor(0x64, 0x64, 0x64)
                .setSelectedBgColor(0xff, 0xff, 0xff)
                .setUnSelectBgColor(0xf5, 0xf5, 0xf5)
                .setTextXOffset(Tools.dp2px(this, 12))
                .setTextSize(Tools.dp2px(this, 14))
                .setHeight((int) Tools.dp2px(this, 30))
                .build();
        indicatorView.attachToRecyclerView(recyclerView, config, array);
        Adapter adapter = new Adapter(this);
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.listheader, recyclerView, false));
        recyclerView.setAdapter(adapter);

        adapter.dataSource.clear();
        ArrayList<String> titles = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : getTestData().entrySet()) {
            titles.add(entry.getKey());
            array.put(adapter.getHeadersCount() + adapter.dataSource.size(), entry.getKey());
            adapter.dataSource.addAll(entry.getValue());
        }
        indicatorView.setIndicators(titles);
        adapter.notifyDataSetChanged();
    }

    private Map<String, List<String>> getTestData() {
        String[] prefixs = {"A", "B", "E", "F", "G", "M", "N", "P", "R", "T", "W", "X", "Z"};
        Map<String, List<String>> map = new ArrayMap<>();

        for (String prefix : prefixs) {
            List<String> list = new ArrayList<>();
            if (prefix.equals("Z")) {
                list.add(String.format("%s%s", prefix, 0));
                map.put(prefix, list);
                continue;
            }
            for (int i = 0; i < 10; i++) {
                list.add(String.format("%s%s", prefix, i));
            }
            map.put(prefix, list);
        }

        return map;
    }
}
