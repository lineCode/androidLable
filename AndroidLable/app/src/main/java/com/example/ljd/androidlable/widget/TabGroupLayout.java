package com.example.ljd.androidlable.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ljd.androidlable.R;

/**
 * Created by liujiandong on 16/5/31.
 */
public class TabGroupLayout extends ViewGroup {

    private static final int DEFAULT_LINE_SPACING = 5;
    private static final int DEFAULT_TAG_SPACING = 10;
    private static final int DEFAULT_FIXED_COLUMN_SIZE = 3; //默认列数

    private int columnSize;
    private boolean isFixed;


    private int mLineSpacing;
    private int mTagSpacing;

    private BaseAdapter mAdapter;

    private DataChangeObserve mObserver ;

    private TabOnItemClickListener mListener ;

    public TabGroupLayout(Context context) {
        super(context);
    }

    public TabGroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arry = context.obtainStyledAttributes(attrs, R.styleable.TagCloudLayout) ;

        try {
            mLineSpacing = arry.getDimensionPixelSize(R.styleable.TagCloudLayout_lineSpacing, DEFAULT_LINE_SPACING);
            mTagSpacing = arry.getDimensionPixelSize(R.styleable.TagCloudLayout_tagSpacing, DEFAULT_TAG_SPACING);
            columnSize = arry.getInteger(R.styleable.TagCloudLayout_columnSize, DEFAULT_FIXED_COLUMN_SIZE);
            isFixed = arry.getBoolean(R.styleable.TagCloudLayout_isFixed,false);
        } finally {
            arry.recycle();
        }
    }

    public TabGroupLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 主要是为了测量当前容器的宽高,如果容器宽度不足以显示最后一个标签,则另起一行
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wantHeight = 0;
        int wantWidth = resolveSize(0, widthMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int chiladTop = paddingTop;

        int lineHeight = 0;

        //固定列的数量所需要的代码
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            LayoutParams params = childView.getLayoutParams();
            childView.measure(getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, params.width), getChildMeasureSpec(heightMeasureSpec, paddingBottom + paddingTop, params.height));
            // 获取测量过的meaure宽高
            int childHeight = childView.getMeasuredHeight();
            int childWidth = childView.getMeasuredWidth();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > wantWidth) {
                // 超出屏幕
                childLeft = paddingLeft ;
                chiladTop += mLineSpacing + childHeight ;
                lineHeight  = childHeight ;
            }
            childLeft += childWidth + mTagSpacing ;
        }
        wantHeight += chiladTop+lineHeight+paddingBottom ;
        setMeasuredDimension(wantWidth,resolveSize(wantHeight,heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 固定列数所需要的代码
        int width = r - l ;
        int paddingLeft = getPaddingLeft() ;
        int paddingTop = getPaddingTop() ;
        int paddingRight = getPaddingRight() ;
        int paddingBottom = getPaddingBottom() ;

        int childLeft = paddingLeft ;
        int childTop = paddingTop ;
        int lineHeight = 0 ;

        int childCount = getChildCount() ;
        for (int i = 0 ; i < childCount ; i ++){
            View childView = getChildAt(i) ;
            if(childView.getVisibility() == View.GONE){
                continue;
            }

            int childWidth = childView.getMeasuredWidth() ;
            int childHeight = childView.getMeasuredHeight() ;

            lineHeight = Math.max(childHeight,lineHeight) ;

            if(childLeft + childWidth +paddingRight >width){
                childLeft = paddingLeft ;
                childTop += mLineSpacing + lineHeight ;
                lineHeight = childHeight ;
            }

            childView.layout(childLeft,childTop,childLeft+childWidth,childTop+childHeight);
            childLeft += childWidth + mTagSpacing ;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet p) {
        return new LayoutParams(this.getContext(),p);
    }

    public void setAdapter(BaseAdapter adapter){
        if(adapter != null){
            mAdapter = adapter ;
            if(mObserver == null){
                mObserver = new DataChangeObserve() ;
            }
            drawLayout() ;
        }
    }

    private void drawLayout() {
        if(mAdapter == null || mAdapter.getCount() == 0){
            return ;
        }

        this.removeAllViews(); ;

        int count = mAdapter.getCount() ;
        for (int i = 0 ; i < count ; i ++){
            View view = mAdapter.getView(i,null,null) ;
            final int position = i ;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.itemClick(position) ;
                    }
                }
            });
            this.addView(view);
        }
    }

    class DataChangeObserve extends DataSetObserver{
        @Override
        public void onChanged() {
            TabGroupLayout.this.drawLayout() ;
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }

    public void setItemClickListener(TabOnItemClickListener listener){
        mListener = listener ;
    }

    public interface TabOnItemClickListener{
        void itemClick(int position) ;
    }
}
