package com.sunli.week3_demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunli.week3_demo.R;
import com.sunli.week3_demo.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<NewsBean.ResultBean.DataBean> list;

    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_IMAGE_TEXT = 0;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnLongItemClickListener {
        void onItemLongClick(View itemView, int position);
    }

    private OnItemClickListener clickListener;
    private OnLongItemClickListener longItemClickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener longItemClickListener) {
        this.longItemClickListener = longItemClickListener;
    }

    public NewsAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_IMAGE) {
            View v = View.inflate(context, R.layout.ptrl_item_image, null);
            holder = new ViewHolderImage(v);
        } else {
            View v = View.inflate(context, R.layout.ptrl_item_image_text, null);
            holder = new ViewHolderImageText(v);
        }
        return holder;
    }

    public void addDatas(List<NewsBean.ResultBean.DataBean> datas) {
        this.list=datas;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_IMAGE:
                ViewHolderImage holderImage = (ViewHolderImage) holder;
                Glide.with(context).load(list.get(position).getThumbnail_pic_s()).into(holderImage.img);
                break;
            case TYPE_IMAGE_TEXT:
                ViewHolderImageText holderImageText = (ViewHolderImageText) holder;
                Glide.with(context).load(list.get(position).getThumbnail_pic_s()).into(holderImageText.img);
                holderImageText.textView.setText(list.get(position).getTitle());
                break;
            default:
                break;
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(v, position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longItemClickListener != null) {
                    longItemClickListener.onItemLongClick(v, position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2 == TYPE_IMAGE) {
            return TYPE_IMAGE;
        } else {
            return TYPE_IMAGE_TEXT;
        }
    }

    class ViewHolderImage extends RecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolderImage(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.p_icon);
        }
    }

    class ViewHolderImageText extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView textView;

        public ViewHolderImageText(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.image_text1);
            img = itemView.findViewById(R.id.image_icon);
        }
    }
}
