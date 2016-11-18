package com.example.quan.EasyAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by GanQuan on 16/11/18.
 */
public class RecyclerViewActivity extends Activity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(this, getIntent().getIntExtra("type", 0));
        recyclerView.setAdapter(adapter);
        adapter.addList(createList());

    }

    public static List<ItemBean> createList() {
        int[] colors = {R.color.color_0, R.color.color_1, R.color.color_2, R.color.color_3,
                R.color.color_4, R.color.color_5, R.color.color_6, R.color.color_7,
                R.color.color_8, R.color.color_9,};
        List<ItemBean> list = new ArrayList<>();

        for (int i = 0; i < colors.length * 2; i++) {
            list.add(new ItemBean("index" + i, colors[i % colors.length]));

        }
        return list;
    }


    static class MyAdapter extends BaseRecyclerViewAdapter<ItemBean> {
        int mType = 0;

        public MyAdapter(Context context) {
            super(context);
        }

        public MyAdapter(Context context, int type) {
            this(context);
            mType=type;

        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> VhClazzList) {
            if (mType == 0) {
                VhClazzList.add(ViewHolder0.class);
            } else {
                VhClazzList.add(ViewHolder0.class);
                VhClazzList.add(ViewHolder1.class);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (mType == 0)
                return super.getItemViewType(position);
            else return position % 2;

        }
    }

    @BindLayout(id = R.layout.list_item_0)
    static class ViewHolder0 extends BaseRecyclerViewAdapter.BaseViewHolder<ItemBean> {
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.text)
        TextView textView;

        public ViewHolder0(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(ItemBean bean, int position, Context context) throws ParseException {
            img.setImageResource(R.drawable.ic_launcher);
            textView.setText(bean.name);
            ((View) img.getParent()).setBackgroundResource(bean.color);

        }
    }

    @BindLayout(id = R.layout.list_item_1)
    static class ViewHolder1 extends BaseRecyclerViewAdapter.BaseViewHolder<ItemBean> {
        @Bind(R.id.text)
        TextView textView;

        public ViewHolder1(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(ItemBean bean, int position, Context context) throws ParseException {
            textView.setText(bean.name);
            textView.setBackgroundResource(bean.color);

        }
    }
}
