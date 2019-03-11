package ca.bvc.employeeconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import ca.bvc.employeeconnect.library.Message;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Message> messages;
    private final Message message = new Message("Brijesh Patel", "Hii there!", "2019-3-19 6:27pm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize properties
        messages = new ArrayList<>();
        messages.add(message);

        //Initialize Recycler View
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.communication_recycler_container);
        ChatListAdapter adapter = new ChatListAdapter(this, messages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
