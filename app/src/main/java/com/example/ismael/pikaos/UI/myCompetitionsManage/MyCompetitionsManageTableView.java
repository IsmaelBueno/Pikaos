package com.example.ismael.pikaos.UI.myCompetitionsManage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.adapters.online.RvCompetitionsTableAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.data.model.online.MyCompetition;
import com.example.ismael.pikaos.data.model.online.PlayerPoint;

import java.util.List;

public class MyCompetitionsManageTableView extends BaseFragment implements MyCompetitionManageTableContract.view{

    private static final String TAG = "MyCompetitionsManageTableView";
    private RecyclerView table;
    private MyCompetition myCompetition;

    private RvCompetitionsTableAdapter adapter;
    private MyCompetitionManageTablePresenter presenter;

    public static MyCompetitionsManageTableView newInstance(MyCompetition competition) {

        MyCompetitionsManageTableView fragment = new MyCompetitionsManageTableView();

        Bundle args = new Bundle();
        args.putSerializable(TAG, competition);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mycompetitions_manage_table, container,false);
        table = rootView.findViewById(R.id.rvMyCompetitionConfrontationTablePoints);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCompetition = (MyCompetition) getArguments().getSerializable(TAG);

        presenter = new MyCompetitionManageTablePresenter(this);
        presenter.getPoints(myCompetition.getId());

    }

    @Override
    public void setTitleFragment() {
        getActivity().setTitle(myCompetition.getName());
    }


    //CONTRACT
    @Override
    public void setPoints(List<PlayerPoint> points) {
        adapter = new RvCompetitionsTableAdapter(getActivity(),points);
        table.setLayoutManager(new LinearLayoutManager(getActivity()));
        table.setAdapter(adapter);
    }

    @Override
    public void setMessageError(int messageError) {
        Toast.makeText(getActivity(), messageError, Toast.LENGTH_SHORT).show();
    }
}