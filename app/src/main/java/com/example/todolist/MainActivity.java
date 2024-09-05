package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;
    private ListView listViewTasks;
    private EditText editTextTask;
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);

        listViewTasks = findViewById(R.id.listViewTasks);
        editTextTask = findViewById(R.id.editTextTask);
        Button buttonAdd = findViewById(R.id.buttonAdd);

        listViewTasks.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrEditTask();
            }
        });

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
                editTextTask.setText(tasks.get(position));
            }
        });

        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirmDeleteTask(position);
                return true;
            }
        });
    }

    private void addOrEditTask() {
        String task = editTextTask.getText().toString();
        if (task.isEmpty()) {
            Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedIndex == -1) {
            tasks.add(task);
        } else {
            tasks.set(selectedIndex, task);
            selectedIndex = -1;
        }

        adapter.notifyDataSetChanged();
        editTextTask.setText("");
    }

    private void confirmDeleteTask(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    tasks.remove(position);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
