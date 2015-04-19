package org.lee.android.doodles.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.lee.android.doodles.R;
import org.lee.android.doodles.bean.Doodle;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder implements
        DoodleRecyclerAdapter.Attachable, View.OnClickListener {

    public interface OnRecyclerItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnRecyclerItemChildClickListener extends OnRecyclerItemClickListener {
        void onItemChildClick(View clickView, int position);
    }

    private final NetworkImageView imageView;
    private final TextView titleText;
    private final TextView dateText;
    private final TextView hoverText;
    private final OnRecyclerItemChildClickListener mOnRecylerItemClickListener;

    public RecyclerItemViewHolder(final View parent,
                                  NetworkImageView imageView,
                                  TextView titleText,
                                  TextView dateText,
                                  TextView hoverText, OnRecyclerItemChildClickListener clicks) {
        super(parent);
        this.imageView = imageView;
        this.titleText = titleText;
        this.dateText = dateText;
        this.hoverText = hoverText;
        this.mOnRecylerItemClickListener = clicks;

        parent.setOnClickListener(this);
        parent.findViewById(R.id.Search).setOnClickListener(this);
    }

    public static RecyclerItemViewHolder newInstance(View convertView, OnRecyclerItemChildClickListener clicks) {
        NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.ImageView);
        TextView titleText = (TextView) convertView.findViewById(R.id.Title);
        TextView dateText = (TextView) convertView.findViewById(R.id.Date);
        TextView hoverText = (TextView) convertView.findViewById(R.id.HoverText);


        imageView.setDefaultImageResId(R.drawable.GRAY_DARK);
        imageView.setErrorImageResId(R.drawable.ic_google_birthday);
        return new RecyclerItemViewHolder(convertView, imageView, titleText, dateText, hoverText, clicks);
    }

    @Override
    public void onClick(View v) {
        if (mOnRecylerItemClickListener != null)
            switch (v.getId()) {
                case R.id.Search:
                    mOnRecylerItemClickListener.onItemChildClick(v, getAdapterPosition());
                    return;
                default:
                    mOnRecylerItemClickListener.onItemClick(v, getAdapterPosition());
                    return;
            }
    }

    @Override
    public void attachData(Object o) {
        Doodle doodle = (Doodle) o;
        dateText.setText(doodle.getDate());
//        imageView.setImageUrl(doodle.hires_url, VolleyLoader.getInstance().getImageLoader());
        titleText.setText(doodle.title);
        hoverText.setText("#" + doodle.query + "#");
//        hoverText.setText(doodle.getTranslations().getItem(0).hover_text);
    }
}