package com.example.animaladoption.ui.request;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animaladoption.R;
import com.example.animaladoption.user;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class requests extends Fragment {

    private RequestsViewModel mViewModel;

    Context context;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Adapter adapter;
    private ArrayList<ModelClassreq> reqs;
    private ArrayList<ModelClassreq> petss;
    public static requests newInstance() {
        return new requests();
    }

    private void getanimals(){
        ref.child("animal").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ModelClassreq pet = snapshot.getValue(ModelClassreq.class);
                pet.setId(snapshot.getKey());
                petss.add(pet);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //TODO
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //TODO
                ModelClassreq pet=snapshot.getValue(ModelClassreq.class);
                petss.remove(pet);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getrequests(){
        ref.child("request").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ModelClassreq req = snapshot.getValue(ModelClassreq.class);
                req.setParentid(snapshot.getKey());
                if(req.getPhoneno().equals(user.phoneno))
                {
                    boolean flag=false;
                    ModelClassreq reserve=req;

                    //Check if that animal is still in database
                    for(ModelClassreq tempanimal : petss)
                    {
                        if(tempanimal.getId().equals(req.getId()))
                        {
                            flag=true;
                            reserve = tempanimal;
                            break;
                        }
                    }
                    if(flag) {
                        req.setGender(reserve.getGender());
                        req.setName(reserve.getName());
                        req.setAge(reserve.getAge());
                        req.setAtype(reserve.getAtype());
                        req.setUrl(reserve.getUrl());
                        reqs.add(req);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //TODO
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //TODO
                ModelClassreq req=snapshot.getValue(ModelClassreq.class);
                reqs.remove(req);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(RequestsViewModel.class);
        View root = inflater.inflate(R.layout.requests_fragment, container, false);
        context=root.getContext();

        recyclerView= root.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        reqs = new ArrayList<>();
        petss = new ArrayList<>();
        adapter = new Adapter(context , reqs);
        recyclerView.setAdapter(adapter);

        getanimals();
        getrequests();

        adapter.setOnItemClickListener(position -> {
            Bundle b = new Bundle();

            b.putParcelable("request", reqs.get(position));
            Navigation.findNavController(root).navigate(R.id.action_nav_request_to_request_description, b);
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RequestsViewModel.class);
        // TODO: Use the ViewModel
    }

}