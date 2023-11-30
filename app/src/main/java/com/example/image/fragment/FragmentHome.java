package com.example.image.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.image.show.InforMovieActivity;
import com.example.image.R;

import com.example.image.adapter.ShowAllAdapter;
import com.example.image.model.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentHome extends Fragment implements ShowAllAdapter.ItemListener {

    private RecyclerView recyclerView;
    private ArrayList<Movie> list;
    private ShowAllAdapter adapter;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("movie");




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new ShowAllAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Movie movie = dataSnapshot.getValue(Movie.class);
                    list.add(movie);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter.setItemListener(new ShowAllAdapter.ItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Movie movie = adapter.getMovie(position);
                Intent intent = new Intent(getActivity(), InforMovieActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}