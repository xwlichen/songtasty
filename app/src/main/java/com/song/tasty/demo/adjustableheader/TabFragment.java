package com.song.tasty.demo.adjustableheader;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.song.tasty.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liyongan on 19/1/31.
 */

public class TabFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        RecyclerView mRecyclerView = root.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String[] data = new String[]{
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
        };
        mRecyclerView.setAdapter(new TestRecycleViewAdapter(getContext(), new ArrayList<>(Arrays.asList(data))));
        return root;
    }

    public class TestRecycleViewAdapter extends RecyclerView.Adapter<TestRecycleViewAdapter.ViewHolder> {
        private Context mContext;
        private List<String> mList;

        public TestRecycleViewAdapter(Context context, List<String> list) {
            mContext = context;
            mList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(mList.get(position));
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView mTextView;
            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView;
            }
        }
    }
}
