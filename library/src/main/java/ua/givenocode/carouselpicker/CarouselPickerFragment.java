package ua.givenocode.carouselpicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;


/**
 * Created by oleh on 01.07.16.
 */
public class CarouselPickerFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private HorizontalSpaceItemDecoration decorator;

    private Listener listener;

    // Coordinates of view center
    // Resets in onResume
    private int xmid, ymid;
    private RecyclerView.Adapter adapter;
    private Integer spacing;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setItemSpacing(int spacing) {
        if (decorator != null) {
            decorator.setHorizontalSpacing(spacing);
            recyclerView.invalidateItemDecorations();
        } else {
            // Remember to set in init
            this.spacing = spacing;
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (recyclerView != null) {
            recyclerView.setAdapter(adapter);
        } else {
            // Remember to set in init
            this.adapter = adapter;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getContext());
        // support only horizontal.
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerView.setLayoutParams(params);
        recyclerView.setBackgroundResource(android.R.color.transparent);
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    @Override
    public void onResume() {
        super.onResume();

        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                // Following code must be executed only once
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                // Update center coordinates
                xmid = recyclerView.getMeasuredWidth() / 2;
                ymid = recyclerView.getMeasuredHeight() / 2;
                drawToCenter(false);
                decorateVisibleViews();

                return true;
            }
        });
    }


    private void init() {
        layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
            if (adapter instanceof InfinitiveBaseAdapter) {
                recyclerView.scrollToPosition(InfinitiveBaseAdapter.MIDDLE);
            }
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (spacing != null) {
            decorator = new HorizontalSpaceItemDecoration(spacing);
        } else {
            decorator = new HorizontalSpaceItemDecoration((int) getResources().getDimension(R.dimen.pickerDefaultSpacing));
        }
        recyclerView.addItemDecoration(decorator);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    drawToCenter(true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                decorateVisibleViews();
            }
        });
    }

    // Calculates middle view and draws it to center
    private void drawToCenter(boolean smooth) {
        View midView = null;
        int i = 0;
        while (midView == null) {
            midView = recyclerView.findChildViewUnder(xmid + i, ymid);
            i = Math.abs(i) + 1;
            i = -i;
        }
        int viewXmid = (int) midView.getX() + midView.getWidth() / 2;

        int distFromCenter = -(xmid - viewXmid);
        if (distFromCenter != 0) {
            if (smooth) {
                recyclerView.smoothScrollBy(distFromCenter, 0);
            } else {
                recyclerView.scrollBy(distFromCenter, 0);
            }
        }


        if (listener != null) {
            long itemId = getVisibleItemIdByView(midView);
            if (xmid == viewXmid) {
                listener.onItemSelected(itemId);
            } else {
                listener.onItemPreSelected(itemId);
            }

        }
    }


    // Calculates distance to center and changes alpha & scale relatively
    // For each visible item
    private void decorateVisibleViews() {
        for (int i = layoutManager.findFirstVisibleItemPosition(); i <= layoutManager.findLastVisibleItemPosition(); i++) {
            View view = layoutManager.findViewByPosition(i);

            int viewMid = (int) view.getX() + view.getWidth() / 2;

            int dist = Math.abs(xmid - viewMid);
            if (xmid != 0) {
                float koef = (1 - dist / (float) xmid) / 2f + 0.5f;
                view.setAlpha(koef);
                view.setScaleX(koef);
                view.setScaleY(koef);
            }

            if (listener != null && dist < view.getWidth() / 2) {
                long id = getVisibleItemIdByView(view);
                listener.onScrollOver(id);
            }


        }
    }


    private long getVisibleItemIdByView(View midView) {
        int first = layoutManager.findFirstVisibleItemPosition();
        int last = layoutManager.findLastVisibleItemPosition();
        long itemId = -1;

        for (int pos = first; pos <= last; pos++) {
            View v = layoutManager.findViewByPosition(pos);
            if (v == midView) {
                itemId =  adapter.getItemId(pos);
                break;
            }
        }
        return itemId;
    }

    public abstract class Listener {
        /**
         * Called when user stops scrolling but before item is centrified
         */
        void onItemPreSelected(long itemId) {}

        /**
         * Called after item has been dragged to center
         */
        void onItemSelected(long itemId) {}

        /**
         * View is in focus but user is still scrolling
         */
        void onScrollOver(long itemId) {}
    }
}
