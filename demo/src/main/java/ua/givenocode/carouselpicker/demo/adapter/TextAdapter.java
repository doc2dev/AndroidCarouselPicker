package ua.givenocode.carouselpicker.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.givenocode.carouselpicker.InfinitiveBaseAdapter;
import ua.givenocode.carouselpicker.demo.R;

/**
 * Created by oleh on 05.07.16.
 */
public class TextAdapter extends InfinitiveBaseAdapter<TextAdapter.ViewHolder> {

    private String[] items;
    private Context context;

    public TextAdapter(Context context) {
        this.context = context;

        items = context.getResources().getStringArray(R.array.string_items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new TextView(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int actualPosition = position % items.length;
        holder.mText.setText(items[actualPosition]);
    }

    @Override
    public long getItemId(int position) {
        int actualPosition = position % items.length;
        return actualPosition;
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText;

        public ViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView;
        }
    }
}
