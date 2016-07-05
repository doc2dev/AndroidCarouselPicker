package ua.givenocode.carouselpicker;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by oleh on 30.06.16.
 */
class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int horizontalSpacing;

    public HorizontalSpaceItemDecoration(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = horizontalSpacing / 2;
        outRect.left = horizontalSpacing / 2;
    }
}
