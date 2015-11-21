package cn.edu.fudan.daoleme.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.util.DeviceUtil;

/**
 * Created by rinnko on 2015/11/15.
 */
public class ExpressItemView extends RelativeLayout {
    private static final String TAG = "ExpressItemView";
    private static final int MAX_ANIMATION_DURATION_MS = 300;

    private int mUid;
    private ViewGroup mViewGroupMain;
    private boolean mActive;
    private Factory mFactory;

    private ExpressItemView(Context context) {
        this(context, null, 0);
    }

    private ExpressItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private ExpressItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.layout_express_item, this, true);
        mViewGroupMain = (ViewGroup)findViewById(R.id.flipper_main);
    }

    // http://stackoverflow.com/questions/22797576/android-view-handle-scroll-gestures-but-ignore-touch-up
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Rect mainRect = new Rect();
        mViewGroupMain.getHitRect(mainRect);
        float x = ev.getX();
        float y = ev.getY();
        if (mainRect.contains((int)ev.getX(), (int)ev.getY())) {
            mViewGroupMain.dispatchTouchEvent(ev);
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void recycle() {
        LayoutParams params = (LayoutParams) mViewGroupMain.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mViewGroupMain.setLayoutParams(params);
        mActive = false;
    }

    private void onStopScroll(float offsetX, float velocityX) {
        if (Math.abs(offsetX) >= mFactory.mScrollThreshold) {
            mActive = !mActive;
        }
        LayoutParams params = (LayoutParams) mViewGroupMain.getLayoutParams();
        int deltaRightMargin = mActive ? mFactory.mMaxRightMargin - params.rightMargin: - params.rightMargin;
        animateMain(params, deltaRightMargin, velocityX);
    }

    private void hideControlBar() {
        if (!mActive) {
            return;
        }
        mActive = false;
        LayoutParams params = (LayoutParams) mViewGroupMain.getLayoutParams();
        animateMain(params, -params.rightMargin, 0);
    }

    private void translateLeft(int distance) {
        LayoutParams params = (LayoutParams) mViewGroupMain.getLayoutParams();
        if ((params.rightMargin == mFactory.mMaxRightMargin && distance > 0) || (params.rightMargin == 0 && distance < 0)) {
            return;
        }
        int newRightMargin = params.rightMargin + distance;
        if (newRightMargin > mFactory.mMaxRightMargin) {
            newRightMargin = mFactory.mMaxRightMargin;
        } else if (newRightMargin < 0) {
            newRightMargin = mFactory.mMaxRightMargin;
        }
        params.rightMargin = newRightMargin;
        params.leftMargin = -newRightMargin;
    }

    private void animateMain(final LayoutParams params, final int deltaRightMargin, float velocityX) {
        final int fromRightMargin = params.rightMargin;
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int newMarginRight = (int)(fromRightMargin + deltaRightMargin * interpolatedTime);
                params.rightMargin = newMarginRight;
                params.leftMargin = -newMarginRight;
                mViewGroupMain.setLayoutParams(params);
            }
        };
        int duration = velocityX == 0 ? MAX_ANIMATION_DURATION_MS : (int)Math.abs(deltaRightMargin * 1000 / velocityX);
        animation.setDuration(duration >= 200 ? 200 : duration);
        mViewGroupMain.startAnimation(animation);
    }

    private enum GestureType {SCROLL_X, SCROLL_Y, LONG_PRESS, UNKNOWN}

    public interface Callbacks {
        void onLongPress(MotionEvent e, ExpressItemView view);
    }

    public static class Factory extends GestureDetector.SimpleOnGestureListener implements OnTouchListener, AbsListView.RecyclerListener {
        private static final int THRESHOLD_X_DP = 20;
        private static final int THRESHOLD_Y_DP = 20;
        private static final int MIN_FLING_X_DP = 80;
        private static final int MAX_DELTA_X_DP = 240;

        private final GestureDetector mGestureDetector;
        private final float mEventThresholdX;
        private final float mEventThresholdY;
        private final Context mContext;
        private final int mMaxRightMargin;
        private final float mScrollThreshold;
        private final Callbacks mCallbacks;
        private int mCounter;

        private float mDeltaX;
        private float mDeltaY;
        private GestureType mGestureType = GestureType.UNKNOWN;
        private ExpressItemView mActiveView;
        private ExpressItemView mFocusedView;
        private ListView mListView;
        private float mTouchStartX;

        public Factory(Context context, ListView listView, Callbacks callbacks) {
            mGestureDetector = new GestureDetector(context, this);
            float pixelsPerDp = DeviceUtil.pixelsPerDip(context);
            mEventThresholdX = pixelsPerDp * THRESHOLD_X_DP;
            mEventThresholdY = pixelsPerDp * THRESHOLD_Y_DP;
            mMaxRightMargin = (int)(pixelsPerDp * MAX_DELTA_X_DP);
            mScrollThreshold = pixelsPerDp * MIN_FLING_X_DP;
            mContext = context;
            mCallbacks = callbacks;
            mListView = listView;
            listView.setRecyclerListener(this);
            listView.setOnTouchListener(this);
        }

        public ExpressItemView create() {
            ExpressItemView view = new ExpressItemView(mContext);
            view.mUid = mCounter++;
            view.mFactory = this;
            view.setLongClickable(true);
            return view;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //Log.d(TAG, ""+ (event.getAction() & MotionEvent.ACTION_MASK));
            if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                Log.d(TAG, "UP");
            }
            return mGestureDetector.onTouchEvent(event);
            /*
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mFocusedView = (ExpressItemView)mListView.getChildAt(mListView.pointToPosition((int)event.getRawX(), (int)event.getY()));
                Log.d(TAG, String.valueOf(mFocusedView));
                mDeltaX = 0;
                mDeltaY = 0;
                mGestureType = GestureType.UNKNOWN;
                mTouchStartX = event.getRawX();
                if (mActiveView != null && mActiveView.mUid != mFocusedView.mUid) {
                    mActiveView.hideControlBar();
                    mActiveView = null;
                }
            }
            boolean handled = mGestureDetector.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_UP && !handled) {
                updateActiveItem();
                if (mFocusedView != null) {
                    mFocusedView.onStopScroll(mDeltaX, 0);
                }
            }
            return false;
            */
        }

        @Override
        public void onMovedToScrapHeap(View view) {
            ExpressItemView v = (ExpressItemView)view;
            if (mActiveView != null && v.mUid != mActiveView.mUid) {
                mActiveView = null;
            }
            v.recycle();
        }

        public void updateActiveItem() {
            if (mActiveView != null && !mActiveView.mActive) {
                mActiveView = null;
            }
        }

        public boolean onDown(MotionEvent event) {
            Log.d(TAG, "onDown");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // determine which type of gesture
          /*  if (mGestureType == GestureType.UNKNOWN) {
                mDeltaX += distanceX;
                mDeltaY += distanceY;
                float absDeltaX = Math.abs(mDeltaX);
                float absDeltaY = Math.abs(mDeltaY);
                if (absDeltaX >= mEventThresholdX && absDeltaY < mEventThresholdY) {
                    mGestureType = GestureType.SCROLL_X;
                } else if (absDeltaX < mEventThresholdY && absDeltaY >= mEventThresholdY) {
                    mGestureType = GestureType.SCROLL_Y;
                } else if (absDeltaX >= mEventThresholdX) {
                    if (absDeltaX / mEventThresholdX > absDeltaY / mEventThresholdY) {
                        mGestureType = GestureType.SCROLL_X;
                    } else {
                        mGestureType = GestureType.SCROLL_Y;
                    }
                }
            }
            // perform scroll in x-axis
            if (mGestureType == GestureType.SCROLL_X) {
                mFocusedView.translateLeft((int)distanceX);
                return true;
            }*/
            Log.d(TAG, "onScroll");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            //mCallbacks.onLongPress(e, mFocusedView);
            Log.d(TAG, "onLongPress");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            /*updateActiveItem();
            if (mGestureType == GestureType.SCROLL_X) {
                mFocusedView.onStopScroll(e2.getRawX() - e1.getRawX(), velocityX);
                return true;
            }*/
            Log.d(TAG, "onFling");
            return false;
        }

    }

}
