package com.example.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    // This variable holds list of ExampleItem that passed from MainActivity class
    private ArrayList<ExampleItem> mExampleItemList;
    // Create a reference to our own OnItemClickListener interface
    private OnItemClickListener mListener;

    /* ExampleAdapter Class Constructor */
    public ExampleAdapter(ArrayList<ExampleItem> exampleItemList) {
        mExampleItemList = exampleItemList;
    }

    /* Override methods */
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Instantiates a layout XML file into its corresponding View Java Object
        // parent.getContext()  ---  returns the context the view is running in
        // inflate  ---  bind with XML layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);

        // Create an ExampleViewHolder that we just wrote down below
        // And pass the view and the OnItemClickListener object
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(view, mListener);

        return exampleViewHolder;
    }

    /* Get each ExampleItem from mExampleItemList that we passed from MainActivity class
    *  onBindViewHolder will look at each item inside mExampleItemList,
    *  and set the view to the RecyclerView by using ExampleViewHolder
    */
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        // Get each ExampleItem
        ExampleItem currentItem = mExampleItemList.get(position);

        // Set each ExampleItem into RecyclerList by setting the ExampleViewHolder
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    /* Define how many items will be in our ExampleItemList */
    @Override
    public int getItemCount() {
        return mExampleItemList.size();
    }

    /* These method will be overrided in MainActivity class */
    public interface OnItemClickListener {
        // when the whole CardView is clicked
        void onItemClick(int position);
        // when the delete icon is clicked
        void onDeleteClick(int position);
    }

    /* This method will be used in MainActivity class */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /* Create ViewHolder inner class - this inner class is needed when creating the RecyclerView adapter */
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        /* Create ViewHolder for each view inside our example_item.xml file */
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mDeleteImage;

        private String TAG = "MyDebug";

        /* Constructor */
        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            /* Create reference to our views */
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.TextViewLineOne);
            mTextView2 = itemView.findViewById(R.id.TextViewLineTwo);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            // let RecyclerView Adapter calls the onItemClick in the MainActivity class with the position that we clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        // Get position from RecyclerView - which item is clicked
                        int position = getAdapterPosition();
                        // Check the position is valid
                        if (position != RecyclerView.NO_POSITION) {
                            // call onItemClick() method is MainActivity Class
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        // Get position from RecyclerView - which item is clicked
                        int position = getAdapterPosition();
                        // Check the position is valid
                        if (position != RecyclerView.NO_POSITION) {
                            // call onItemClick() method is MainActivity Class
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
