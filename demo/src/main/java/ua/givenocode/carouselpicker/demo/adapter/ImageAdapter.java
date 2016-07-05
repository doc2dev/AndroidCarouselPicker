package ua.givenocode.carouselpicker.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ua.givenocode.carouselpicker.InfinitiveBaseAdapter;
import ua.givenocode.carouselpicker.demo.R;

/**
 * Created by oleh on 05.07.16.
 */
public class ImageAdapter extends InfinitiveBaseAdapter<ImageAdapter.ViewHolder> {
    private  int[] items ;
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
        items = new int[] {
                R.drawable.pic_1,
                R.drawable.pic_2,
                R.drawable.pic_3,
                R.drawable.pic_4,
                R.drawable.pic_5,
                R.drawable.pic_6,
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView view = new ImageView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Position passed is fake
        int actualPosition = position % items.length;
        holder.mImageView.setImageResource(items[actualPosition]);
    }

    @Override
    public long getItemId(int position) {
        // Listener not used so leave it
        int actualPosition = position % items.length;
        return items[actualPosition];
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView;
        }
    }
}
