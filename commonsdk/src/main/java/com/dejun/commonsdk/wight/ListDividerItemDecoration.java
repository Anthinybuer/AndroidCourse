package com.dejun.commonsdk.wight;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
/**
 * Author:DoctorWei
 * Time:2018/12/8 18:25
 * Description:
 * email:1348172474@qq.com
 */

public class ListDividerItemDecoration extends RecyclerView.ItemDecoration {
    private int[] attrs = new int[]{
            android.R.attr.listDivider
    };
    public static final int DIRECTION_VERTICAL = 0;
    public static final int DIRECTION_HORIZONTAL = 1;
    private Drawable divider;
    private int direction;

    public ListDividerItemDecoration(Context context, int direction) {
        super();
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        divider = typedArray.getDrawable(0);
        this.direction = direction;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (divider == null)
            return;
        if (direction == DIRECTION_VERTICAL) {
            drawVerticalDivider(c, parent);
        } else {
            drawHoriziontalDivider(c, parent);
        }
    }

    private void drawHoriziontalDivider(Canvas c, RecyclerView parent) {
        int left = 0;
        int top = 0;
        int right =0;
        int bottom = 0;
        top = parent.getPaddingTop();
        bottom = parent.getHeight() - parent.getPaddingBottom();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            left = child.getRight()+layoutParams.rightMargin;
            right = left + divider.getIntrinsicWidth();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }

    }


    private void drawVerticalDivider(Canvas c, RecyclerView parent) {
        int left = 0;
        int top = 0;
        int right =0;
        int bottom = 0;
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom()+layoutParams.bottomMargin;
            bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        System.out.println("divider:" + divider.getIntrinsicHeight() + "  " + divider.getIntrinsicWidth());
        int p = parent.getChildAdapterPosition(view);
        int p1 = parent.getChildLayoutPosition(view);
        System.out.println("p:" + p + " p1:" + p1);
        if (direction == DIRECTION_VERTICAL) {
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, divider.getIntrinsicHeight(), 0, 0);
            }
        } else {
            if (parent.getChildLayoutPosition(view) == 0) {

                outRect.set(0, 0, 0, 0);
            } else {

                outRect.set(divider.getIntrinsicWidth(), 0, 0, 0);
            }
        }
    }
}
