package com.givenocode.carouselpicker;

import android.support.v7.widget.RecyclerView;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by oleh on 30.06.16.
 */
public abstract class InfinitiveBaseAdapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {

    // User is wery unlikely to scroll 1073741824 items one side
    public static final int COUNT = Integer.MAX_VALUE;
    public static final int MIDDLE = COUNT / 2;

    /**
     * (use position % actualItemsCount to get proper position)
     */
    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    /**
     * Must be overwritten in order to use listener
     * (use position % actualItemsCount to get proper position)
     */
    @Override
    public abstract long getItemId(int position);

    /**
     * @return fake items count
     */
    @Override
    public final int getItemCount() {
        return COUNT;
    }
}
