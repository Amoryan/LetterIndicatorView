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
        ItemDecoration itemDecoration = new ItemDecoration(this, array);
        recyclerView.addItemDecoration(itemDecoration);
        Adapter adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);

        indicatorView.setOnTitleIndexChangeListener(new LetterIndicatorView.OnTitleIndexChangeListener() {
            @Override
            public void onTitleIndexChanged(int index) {
                if (index >= 0) {
                    lm.scrollToPositionWithOffset(array.keyAt(index), 0);
                }
            }
        });
        itemDecoration.setOnTitleIndexChangeListener(new ItemDecoration.OnTitleIndexChangeListener() {
            @Override
            public void onTitleIndexChanged(int index) {
                indicatorView.setOutChangeIndex(index);
            }
        });

        ArrayList<String> titles = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : getTestData().entrySet()) {
            titles.add(entry.getKey());
            array.put(adapter.data.size(), entry.getKey());
            adapter.data.addAll(entry.getValue());
        }
        indicatorView.setTitles(titles);
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
