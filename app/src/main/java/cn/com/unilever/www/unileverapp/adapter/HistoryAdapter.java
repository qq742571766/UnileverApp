package cn.com.unilever.www.unileverapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.info.ErrorInfo;
import cn.com.unilever.www.unileverapp.utils.ImageLoaderUtil;

import static android.content.ContentValues.TAG;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.mViewHolder> {
    private List<ErrorInfo> datas;
    private OnButtonClickListener listener = null;
    private Context context;
    private static ImageView views;

    public void adds(List<ErrorInfo> datas) {
        this.datas = datas;
    }

    @Override
    public mViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_error_list, null);
        this.context = parent.getContext();
        final mViewHolder holder = new mViewHolder(view);
        holder.newsview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = holder.getAdapterPosition();
                    ErrorInfo info = datas.get(position);
                    listener.OnButtonClick(info);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        ErrorInfo bean = datas.get(position);
        holder.thumbnail_pic_s.setImageResource(R.mipmap.ic_launcher);
        holder.title.setText(bean.title);
        holder.author_name.setText(bean.author_name);
        holder.date.setText(bean.date);
        holder.url.setText(bean.url);
        ImageLoaderUtil util = new ImageLoaderUtil(context);
        Bitmap bitmap = util.loadImage(bean.thumbnail_pic_s, l);
        if (bitmap != null) {
            holder.thumbnail_pic_s.setImageBitmap(bitmap);
        }
    }

    private ImageLoaderUtil.OnLoadImageListener l = new ImageLoaderUtil.OnLoadImageListener() {
        @Override
        public void onImageLoadOK(String url, Bitmap bitmap) {
            views.setImageBitmap(bitmap);
        }

        @Override
        public void onImageLoadError(String url) {
            views.setImageResource(R.mipmap.ic_launcher);
            Toast.makeText(context, "加载出错", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public int getItemCount() {
        if (datas == null || datas.size() <= 0) {
            Log.d(TAG, "数据未加载完成");
        } else {
            return datas.size();
        }
        return 0;
    }

    static class mViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail_pic_s;
        TextView title;
        TextView date;
        TextView author_name;
        TextView url;
        View newsview;

        mViewHolder(View itemView) {
            super(itemView);
            newsview = itemView;
            thumbnail_pic_s = (ImageView) itemView.findViewById(R.id.imageView_f);
            title = (TextView) itemView.findViewById(R.id.textView_title);
            date = (TextView) itemView.findViewById(R.id.f_date);
            author_name = (TextView) itemView.findViewById(R.id.textView_author_name);
            url = (TextView) itemView.findViewById(R.id.textView_url);
            HistoryAdapter.views = thumbnail_pic_s;
        }
    }

    public interface OnButtonClickListener {
        void OnButtonClick(ErrorInfo info);
    }

    public void setOnButtonClickListener(OnButtonClickListener l) {
        this.listener = l;
    }
}