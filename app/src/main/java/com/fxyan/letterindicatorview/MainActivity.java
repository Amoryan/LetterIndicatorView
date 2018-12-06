package com.fxyan.letterindicatorview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        Context context;
        LayoutInflater inflater;
        List<String> data;

        public Adapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.data = new ArrayList<>();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(inflater.inflate(R.layout.listitem, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.nameTv.setText(data.get(i));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
        }
    }
}
