package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* Fields */
    // Contains recycler view object
    private RecyclerView mRecyclerView;

    // Bridge between ArrayList and RecycleView
    private ExampleAdapter mAdapter;

    // Responsible for laying out each item in the view (align single item in the list)
    private RecyclerView.LayoutManager mLayoutManager;

    // Create a list of ExampleItem list
    private ArrayList<ExampleItem> mExampleItemList = new ArrayList<>();

    private Button mButtonInsert;
    private Button mButtonRemove;
    private EditText mEditTextInsert;
    private EditText mEditTextRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecyclerView();
        setButtons();
    }

    public void insertItem(int insertPosition) {
        if (insertPosition > mExampleItemList.size()) {
            mExampleItemList.add(new ExampleItem(R.drawable.ic_done_all, "New Item At Position " + insertPosition, "This is Line 2"));
        } else {
            mExampleItemList.add(insertPosition, new ExampleItem(R.drawable.ic_done_all, "New Item At Position " + insertPosition, "This is Line 2"));
        }
        /*
        * 'mAdapter.notifyDataSetChanged()' will work, but without insert animation. This method is going to refresh the recyclerview
        * android provides 'mAdapter.notifyItemInserted()' method, this method will perform an animation when item is inserting to the RecyclerView
        * */
        mAdapter.notifyItemInserted(insertPosition);
    }

    public void removeItem(int removePosition) {
        if (mExampleItemList.isEmpty()) {
            return;
        }
        mExampleItemList.remove(removePosition);
        /*
         * 'mAdapter.notifyDataSetChanged()' will work, but without insert animation. This method is going to refresh the recyclerview
         * android provides 'mAdapter.notifyItemRemoved()' method, this method will perform an animation when item is removing to the RecyclerView
         * */
        mAdapter.notifyItemRemoved(removePosition);
    }

    public void changeItemName(int position, String text) {
        mExampleItemList.get(position).changeText1(text);
    }

    /* Add items to ArrayList */
    public void createExampleList() {
        mExampleItemList.add(new ExampleItem(R.drawable.ic_android, "Line 1-1", "Line 1-2"));
        mExampleItemList.add(new ExampleItem(R.drawable.ic_audiotrack, "Line 2-1", "Line 2-2"));
        mExampleItemList.add(new ExampleItem(R.drawable.ic_sunny, "Line 3-1", "Line 3-2"));
    }

    public void buildRecyclerView() {
        // Bind Java object with XML layout file
        mRecyclerView = findViewById(R.id.recyclerView);

        // Increase performance of the RecyclerView, cause RecyclerView will not change the size
        mRecyclerView.setHasFixedSize(true);

        // The 'LinearLayoutManager' allows you to specify an orientation
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleItemList);

        // set layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        // set adapter
        mRecyclerView.setAdapter(mAdapter);

        // handle the button click events
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // variable 'position' is the index we clicked
                changeItemName(position, "Text Changed");
                // notify the adapter, one of the item is changed
                mAdapter.notifyItemChanged(position);
            }

            @Override
            public void onDeleteClick(int position) {
                // variable 'position' is the index we clicked
                removeItem(position);
            }
        });
    }

    public void setButtons() {
        /* Bind Java object with widget */
        mButtonInsert = findViewById(R.id.button_insert);
        mButtonRemove = findViewById(R.id.button_remove);
        mEditTextInsert = findViewById(R.id.edittext_insert);
        mEditTextRemove = findViewById(R.id.edittext_remove);

        /* On Click Listener */
        mButtonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int insertposition = Integer.parseInt(mEditTextInsert.getText().toString());
                insertItem(insertposition);
            }
        });

        mButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int removePosition = Integer.parseInt(mEditTextRemove.getText().toString());
                removeItem(removePosition);
            }
        });
    }
}
