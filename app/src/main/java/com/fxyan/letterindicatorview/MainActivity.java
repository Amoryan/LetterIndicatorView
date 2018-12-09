package com.fxyan.letterindicatorview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SparseArray<String> array = new SparseArray<>();
        recyclerView.addItemDecoration(new ItemDecoration(this, array));
        Adapter adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);

        for (Map.Entry<String, List<String>> entry : getTestData().entrySet()) {
            array.put(adapter.data.size(), entry.getKey());
            adapter.data.addAll(entry.getValue());
        }
        adapter.notifyDataSetChanged();
    }

    private Map<String, List<String>> getTestData() {
        String[] prefixs = {"A", "B", "E", "F", "G", "M", "N", "P", "R", "T", "W", "X", "Z"};
        Map<String, List<String>> map = new HashMap<>();

        for (String prefix : prefixs) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(String.format("%s%s", prefix, i));
            }
            map.put(prefix, list);
        }

        return map;
    }
}
