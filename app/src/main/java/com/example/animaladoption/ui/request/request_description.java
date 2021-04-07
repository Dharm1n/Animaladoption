package com.example.animaladoption.ui.request;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.animaladoption.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class request_description extends Fragment {

    private RequestDescriptionViewModel mViewModel;

    private ModelClassreq req;

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private Context context;

    public static request_description newInstance() {
        return new request_description();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.request_description_fragment, container, false);


        context=root.getContext();
        req = getArguments().getParcelable("request");


        ImageView imagev= (ImageView) root.findViewById(R.id.imageviewt);
        Glide.with(context).load(req.getUrl()).into(imagev);
        TextView _name,_age,_type,_gender,_desc,_status,_contact;
        _contact=root.findViewById(R.id.contact);
        _desc=root.findViewById(R.id.desct);
        _status=root.findViewById(R.id.status);
        _name=root.findViewById(R.id.namet);
        _age=root.findViewById(R.id.aget);
        _type=root.findViewById(R.id.typet);
        _gender=root.findViewById(R.id.gendert);

        _desc.setText(req.getDesc());
        _name.setText(req.getName());
        _age.setText(req.getAge());
        _type.setText(req.getAtype());
        _gender.setText(req.getGender());

        if(req.getStatus().equals("accepted"))
        {
            _status.setText("Request ACCEPTED");
            _contact.setText("Please send your documents to +919879856052 on whatsapp");
        }
        else
        {
            _status.setText(req.getStatus());
            _contact.setText("");
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RequestDescriptionViewModel.class);
        // TODO: Use the ViewModel
    }

}