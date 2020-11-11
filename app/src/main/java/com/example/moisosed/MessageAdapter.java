package com.example.moisosed;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private List<ModelClass> modelClassList;

    public MessageAdapter(List<ModelClass> modelClassListList) {
        this.modelClassList = modelClassListList;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message_item, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder viewholder, int position) {
        int resource = modelClassList.get(position).getImageIcon();
        String title = modelClassList.get(position).getTitle();
        String body = modelClassList.get(position).getBody();
        int resource2 = modelClassList.get(position).getImageIcon2();
        viewholder.setData(resource, title, body, resource2);
    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
    }
    class MessageHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView title;
        private TextView body;
        private ImageView imageView2;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);
             imageView = itemView.findViewById(R.id.image);
             title = itemView.findViewById(R.id.name);
             body = itemView.findViewById(R.id.hi);
             imageView2 = itemView.findViewById(R.id.Like);


        }
        private void setData(int ImageResourse, String titleText, String bodyText, int ImageResourse2){
            imageView.setImageResource(ImageResourse);
            title.setText(titleText);
            body.setText(bodyText);
            imageView2.setImageResource(ImageResourse2);

        }
    }
}
