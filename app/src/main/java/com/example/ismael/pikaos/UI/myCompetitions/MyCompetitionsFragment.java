package com.example.ismael.pikaos.UI.myCompetitions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.adapters.online.MyCompetitionsAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.UI.myCompetitionsManage.MyCompetitionsManageActivity;
import com.example.ismael.pikaos.data.model.online.MyCompetition;

import java.util.List;

public class MyCompetitionsFragment extends BaseFragment implements MyCompetitionsContract.View {

    public static final String TAG = "MyCompetitionsFragmentTAG";

    private RecyclerView rvMyCompetitions;

    private MyCompetitionsPresenter presenter;
    private MyCompetitionsAdapter adapter;

    @Override
    public void setTitleFragment() {
        getActivity().setTitle(R.string.title_mycompetitions);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mycompetitions, container,false);
        rvMyCompetitions = rootView.findViewById(R.id.rvMyCompetitions);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitleFragment();

        presenter = new MyCompetitionsPresenter(this);
        presenter.loadMyCompetitions(getActivity());

    }


    @Override
    public void setLoadedMyCompetitions(List<MyCompetition> competitions) {
        adapter = new MyCompetitionsAdapter(getActivity(),competitions);
        rvMyCompetitions.setAdapter(adapter);
        rvMyCompetitions.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyCompetition myCompetition = adapter.getItem(rvMyCompetitions.getChildAdapterPosition(v));

                Bundle bundle = new Bundle();
                bundle.putSerializable(MyCompetitionsManageActivity.TAG, myCompetition);

                Intent intent = new Intent(getActivity(),MyCompetitionsManageActivity.class);
                intent.putExtras(bundle);

                startActivityForResult(intent, 10001);
            }
        });

    }

    @Override
    public void setMessageError(int message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    //Actiivty result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == MyCompetitionsManageActivity.RESULT_OK)) {
            presenter.loadMyCompetitions(getActivity());
        }
    }

}
