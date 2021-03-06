package com.buct.museumguide.ui.FragmentForMain.MuseumInfo;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.AudioMessage;
import com.buct.museumguide.Service.PlayMessage;
import com.buct.museumguide.Service.StringMessage;
import com.buct.museumguide.bean.Audiolist;
import com.buct.museumguide.ui.FragmentForUsers.Upload.ShowUploadAdapter;
import com.buct.museumguide.ui.FragmentForUsers.Upload.audioitem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class Jianjie extends Fragment {

    private MuseumInfoViewModel mViewModel;
    private RecyclerView recyclerView;
    private List<audioitem>l=new ArrayList<>();
    private int currentpos=-1;
    public static Jianjie newInstance() {
        return new Jianjie();
    }
private ShowUploadAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().register(this);
        }
        View view=inflater.inflate(R.layout.jianjie_fragment, container, false);
        recyclerView=view.findViewById(R.id.showaudio_jianjie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ShowUploadAdapter(l,getActivity());
        adapter.setClick(new ShowUploadAdapter.MyClick() {
            int count=0;
            @Override
            public void click(View v) {
                count++;int pos=recyclerView.getChildAdapterPosition(v);
                if(currentpos!=pos&&currentpos!=-1){
                    //说明点击了其他按钮
                    Toast.makeText(getActivity(),"请先暂停当前播放再播放其他讲解",Toast.LENGTH_SHORT).show();
                    return;
                }
                currentpos=pos;
                if(count%2==1){
                    EventBus.getDefault().post(new PlayMessage(String.valueOf(recyclerView.getChildAdapterPosition(v)+1)));
                    Toast.makeText(getActivity(),String.valueOf(recyclerView.getChildAdapterPosition(v)),Toast.LENGTH_SHORT).show();
                    adapter.isture.set(pos,false);
                }else{
                    adapter.isture.set(pos,true);
                    EventBus.getDefault().post(new PlayMessage(String.valueOf(-1)));
                }
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        System.out.println("jianjieqidngle");
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MuseumInfoViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("jianjieresume");
        EventBus.getDefault().post(new StringMessage("0"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("jianjiedestory");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        System.out.println("jianjiepause");
}

    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void getmsg(AudioMessage msg){
        AudioMessage s=msg;
        System.out.println("数据刷新");
        l.clear();
        for(int i=0;i<s.list.size();i++){
            l.add(new audioitem(s.list.get(i).getDescription().getTitle().toString(),s.list.get(i).getDescription().getMediaId(),"导览"));
        }
        for(int i=0;i<s.list.size();i++){
            l.add(new audioitem(s.list.get(i).getDescription().getTitle().toString(),s.list.get(i).getDescription().getMediaId(),"导览"));
        }
        System.out.println("list"+l.size());
        System.out.println(recyclerView);
        adapter.notifyDataSetChanged();
        Toast.makeText(getActivity(),s.list.get(0).getDescription().getTitle(),Toast.LENGTH_SHORT).show();
    }
}
