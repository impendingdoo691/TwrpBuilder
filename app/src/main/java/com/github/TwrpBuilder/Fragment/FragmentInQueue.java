package com.github.TwrpBuilder.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.github.TwrpBuilder.R;
import com.github.TwrpBuilder.util.FirebaseProgressBar;
import com.github.TwrpBuilder.model.Queue;

/**
 * Created by androidlover5842 on 18/1/18.
 */

public class FragmentInQueue extends Fragment {
    private Query query;
    private FirebaseListAdapter<Queue> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_builds_common,container,false);

        ListView lvQueue= view.findViewById(R.id.lv_builds);

        query = FirebaseDatabase.getInstance()
                .getReference("InQueue");
        query.keepSynced(true);

        FirebaseListOptions<Queue> options = new FirebaseListOptions.Builder<Queue>()
                .setLayout(R.layout.list_build_common)
                .setQuery(query,Queue.class)
                .build();

        adapter=new FirebaseListAdapter<Queue>(options) {
            @Override
            protected void populateView(View v, Queue model, int position) {
                TextView tvEmail = v.findViewById(R.id.list_user_email);
                TextView tvDevice = v.findViewById(R.id.list_user_device);
                TextView tvBoard = v.findViewById(R.id.list_user_board);
                TextView tvDate= v.findViewById(R.id.list_user_date);
                TextView tvBrand = v.findViewById(R.id.list_user_brand);
                tvDate.setText("Date : "+model.getDate());
                tvEmail.setText("Email : "+model.getEmail());
                tvDevice.setText("Model : " + model.getModel());
                tvBoard.setText("Board : "+model.getBoard());
                tvBrand.setText("Brand : " +model.getBrand());

            }
        };

        ProgressBar progressBar= view.findViewById(R.id.pb_builds);
        TextView textView= view.findViewById(R.id.tv_no_build);
        new FirebaseProgressBar().start(progressBar,textView,adapter,"InQueue");

        lvQueue.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
