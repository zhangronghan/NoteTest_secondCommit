package com.example.administrator.notetest.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.notetest.Listener.OnItemClickListener;
import com.example.administrator.notetest.Listener.OnLongClickListener;
import com.example.administrator.notetest.R;
import com.example.administrator.notetest.SQLite.NoteMsg;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.MyHolder> implements View.OnClickListener,View.OnLongClickListener{
    private List<NoteMsg> list;
    private OnItemClickListener mOnItemClickListener;
    private OnLongClickListener mOnLongClickListener;


    public MyNoteAdapter(List<NoteMsg> list,OnLongClickListener mOnLongClickListener){
        this.list=list;
        this.mOnLongClickListener=mOnLongClickListener;
    }

    public MyNoteAdapter(List<NoteMsg> list){
        this.list=list;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        MyHolder myHolder=new MyHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final NoteMsg note=list.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvType.setText(note.getType());
        holder.tvDate.setText(note.getDate().toString());

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(mOnLongClickListener != null){
            mOnLongClickListener.OnLongItemClick(v, (int) v.getTag());
        }
        return true;
    }


    class MyHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private TextView tvType;
        private TextView tvDate;

        public MyHolder(View itemView) {
            super(itemView);
            tvTitle= (TextView) itemView.findViewById(R.id.tv_title);
            tvType= (TextView) itemView.findViewById(R.id.tv_type);
            tvDate= (TextView) itemView.findViewById(R.id.tv_date);
        }

    }



}
