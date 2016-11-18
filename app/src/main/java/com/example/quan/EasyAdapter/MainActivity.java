package com.example.quan.EasyAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView=(ListView)findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item_1, getTestItems()));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it;
                switch (position) {
                    case 0:
                        it = new Intent(view.getContext(), RecyclerViewActivity.class);
                        it.putExtra("type", 0);
                        startActivity(it);
                        break;
                    case 1:
                        it = new Intent(view.getContext(), RecyclerViewActivity.class);
                        it.putExtra("type", 1);
                        startActivity(it);

                        break;

                }
            }
        });
    }

    public List<String> getTestItems() {
        List<String> array = new ArrayList<>();
        array.add("single-type");
        array.add("multi-type");
        return array;
    }
}
