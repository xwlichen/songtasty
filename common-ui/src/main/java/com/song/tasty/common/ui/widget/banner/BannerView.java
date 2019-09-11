package com.song.tasty.common.ui.widget.banner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import androidx.annotation.AttrRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.song.tasty.common.ui.R;
import com.song.tasty.common.ui.widget.banner.holder.BannerHolderCreator;
import com.song.tasty.common.ui.widget.banner.transformer.CoverModeTransformer;
import com.song.tasty.common.ui.widget.banner.transformer.ScaleYTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lichen
 * @date ：2019-08-31 23:33
 * @email : 196003945@qq.com
 * @description :
 */
public class BannerView<T> extends RelativeLayout {
    private static final String TAG = "BannerView";
    private BannerViewPager viewpager;
    private BannerPagerAdapter adapter;
    private List<T> data;
    private boolean isAutoPlay = true;// 是否自动播放
    private int currentItem = 0;//当前位置
    private Handler handler = new Handler();
    private int delayedTime = 3000;// Banner 切换时间间隔
    private ViewPagerScroller viewpagerScroller;//控制ViewPager滑动速度的Scroller
    private boolean isOpenMZEffect = true;// 开启魅族Banner效果
    private boolean isCanLoop = true;// 是否轮播图片
    private LinearLayout indicatorContainer;//indicator容器
    private ArrayList<ImageView> indicators = new ArrayList<>();
    //indicatorRes[0] 为为选中，indicatorRes[1]为选中
    private int[] indicatorRes = new int[]{R.drawable.indicator_normal, R.drawable.indicator_selected};
    private int indicatorPaddingLeft = 0;// indicator 距离左边的距离
    private int indicatorPaddingRight = 0;//indicator 距离右边的距离
    private int indicatorPaddingTop = 0;//indicator 距离上边的距离
    private int indicatorPaddingBottom = 0;//indicator 距离下边的距离
    private int mZModePadding = 0;//在仿魅族模式下，由于前后显示了上下一个页面的部分，因此需要计算这部分padding
    private int indicatorAlign = 1;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private BannerPageClickListener bannerPageClickListener;

    public enum IndicatorAlign {
        /**
         * 左对齐
         */
        LEFT,
        /**
         * 居中对齐
         */
        CENTER,
        /**
         * 右对齐
         */
        RIGHT
    }

    /**
     * 中间Page是否覆盖两边，默认覆盖
     */
    private boolean mIsMiddlePageCover = true;

    public BannerView(@NonNull Context context) {
        super(context);
        init();
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);
        init();
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        readAttrs(context, attrs);
        init();
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        isOpenMZEffect = typedArray.getBoolean(R.styleable.BannerView_open_mz_mode, true);
        mIsMiddlePageCover = typedArray.getBoolean(R.styleable.BannerView_middle_page_cover, true);
        isCanLoop = typedArray.getBoolean(R.styleable.BannerView_canLoop, true);
        indicatorAlign = typedArray.getInt(R.styleable.BannerView_indicatorAlign, IndicatorAlign.CENTER.ordinal());
        indicatorPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorPaddingLeft, 0);
        indicatorPaddingRight = typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorPaddingRight, 0);
        indicatorPaddingTop = typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorPaddingTop, 0);
        indicatorPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorPaddingBottom, 0);
        typedArray.recycle();
    }


    private void init() {
        View view = null;
        if (isOpenMZEffect) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_banner_effect, this, true);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_banner_normal, this, true);
        }
        indicatorContainer = (LinearLayout) view.findViewById(R.id.banner_indicator_container);
        viewpager = (BannerViewPager) view.findViewById(R.id.mzbanner_vp);
        viewpager.setOffscreenPageLimit(4);

        mZModePadding = dpToPx(30);
        // 初始化Scroller
        initViewPagerScroll();

        sureIndicatorPosition();

    }

    /**
     * 是否开启魅族模式
     */
    private void setOpenMZEffect() {
        // 魅族模式
        if (isOpenMZEffect) {
            if (mIsMiddlePageCover) {
                // 中间页面覆盖两边，和魅族APP 的banner 效果一样。
                viewpager.setPageTransformer(true, new CoverModeTransformer(viewpager));
            } else {
                // 中间页面不覆盖，页面并排，只是Y轴缩小
                viewpager.setPageTransformer(false, new ScaleYTransformer());
            }

        }
    }

    /**
     * make sure the indicator
     */
    private void sureIndicatorPosition() {
        if (indicatorAlign == IndicatorAlign.LEFT.ordinal()) {
            setIndicatorAlign(IndicatorAlign.LEFT);
        } else if (indicatorAlign == IndicatorAlign.CENTER.ordinal()) {
            setIndicatorAlign(IndicatorAlign.CENTER);
        } else {
            setIndicatorAlign(IndicatorAlign.RIGHT);
        }
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            viewpagerScroller = new ViewPagerScroller(
                    viewpager.getContext());
            mScroller.set(viewpager, viewpagerScroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay) {
                currentItem = viewpager.getCurrentItem();
                currentItem++;
                if (currentItem == adapter.getCount() - 1) {
                    currentItem = 0;
                    viewpager.setCurrentItem(currentItem, false);
                    handler.postDelayed(this, delayedTime);
                } else {
                    viewpager.setCurrentItem(currentItem);
                    handler.postDelayed(this, delayedTime);
                }
            } else {
                handler.postDelayed(this, delayedTime);
            }
        }
    };


    /**
     * 初始化指示器Indicator
     */
    private void initIndicator() {
        indicatorContainer.removeAllViews();
        indicators.clear();
        for (int i = 0; i < data.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            if (indicatorAlign == IndicatorAlign.LEFT.ordinal()) {
                if (i == 0) {
                    int paddingLeft = isOpenMZEffect ? indicatorPaddingLeft + mZModePadding : indicatorPaddingLeft;
                    imageView.setPadding(paddingLeft + 6, 0, 6, 0);
                } else {
                    imageView.setPadding(6, 0, 6, 0);
                }

            } else if (indicatorAlign == IndicatorAlign.RIGHT.ordinal()) {
                if (i == data.size() - 1) {
                    int paddingRight = isOpenMZEffect ? mZModePadding + indicatorPaddingRight : indicatorPaddingRight;
                    imageView.setPadding(6, 0, 6 + paddingRight, 0);
                } else {
                    imageView.setPadding(6, 0, 6, 0);
                }

            } else {
                imageView.setPadding(6, 0, 6, 0);
            }

            if (i == (currentItem % data.size())) {
                imageView.setImageResource(indicatorRes[1]);
            } else {
                imageView.setImageResource(indicatorRes[0]);
            }

            indicators.add(imageView);
            indicatorContainer.addView(imageView);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isCanLoop) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            // 按住Banner的时候，停止自动轮播
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_DOWN:
                int paddingLeft = viewpager.getLeft();
                float touchX = ev.getRawX();
                // 如果是魅族模式，去除两边的区域
                if (touchX >= paddingLeft && touchX < getScreenWidth(getContext()) - paddingLeft) {
                    pause();
                }
                break;
            case MotionEvent.ACTION_UP:
                start();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /******************************************************************************************************/
    /**                             对外API                                                               **/
    /******************************************************************************************************/
    /**
     * 开始轮播
     * <p>应该确保在调用用了{@link BannerView {@link #setPages(List, BannerHolderCreator)}} 之后调用这个方法开始轮播</p>
     */
    public void start() {
        // 如果Adapter为null, 说明还没有设置数据，这个时候不应该轮播Banner
        if (adapter == null) {
            return;
        }
        if (isCanLoop) {
            pause();
            isAutoPlay = true;
            handler.postDelayed(mLoopRunnable, delayedTime);
        }
    }

    /**
     * 停止轮播
     */
    public void pause() {
        isAutoPlay = false;
        handler.removeCallbacks(mLoopRunnable);
    }

    /**
     * 设置是否可以轮播
     *
     * @param canLoop
     */
    public void setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
        if (!canLoop) {
            pause();
        }
    }

    /**
     * 设置BannerView 的切换时间间隔
     *
     * @param delayedTime
     */
    public void setDelayedTime(int delayedTime) {
        this.delayedTime = delayedTime;
    }

    public void addPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    /**
     * 添加Page点击事件
     *
     * @param bannerPageClickListener {@link BannerPageClickListener}
     */
    public void setBannerPageClickListener(BannerPageClickListener bannerPageClickListener) {
        this.bannerPageClickListener = bannerPageClickListener;
    }

    /**
     * 是否显示Indicator
     *
     * @param visible true 显示Indicator，否则不显示
     */
    public void setIndicatorVisible(boolean visible) {
        if (visible) {
            indicatorContainer.setVisibility(VISIBLE);
        } else {
            indicatorContainer.setVisibility(GONE);
        }
    }

    /**
     * set indicator padding
     *
     * @param paddingLeft
     * @param paddingTop
     * @param paddingRight
     * @param paddingBottom
     */
    public void setIndicatorPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        indicatorPaddingLeft = paddingLeft;
        indicatorPaddingTop = paddingTop;
        indicatorPaddingRight = paddingRight;
        indicatorPaddingBottom = paddingBottom;
        sureIndicatorPosition();
    }

    /**
     * 返回ViewPager
     *
     * @return {@link ViewPager}
     */
    public ViewPager getViewPager() {
        return viewpager;
    }

    /**
     * 设置indicator 图片资源
     *
     * @param unSelectRes 未选中状态资源图片
     * @param selectRes   选中状态资源图片
     */
    public void setIndicatorRes(@DrawableRes int unSelectRes, @DrawableRes int selectRes) {
        indicatorRes[0] = unSelectRes;
        indicatorRes[1] = selectRes;
    }

    /**
     * 设置数据，这是最重要的一个方法。
     * <p>其他的配置应该在这个方法之前调用</p>
     *
     * @param datas           Banner 展示的数据集合
     * @param mzHolderCreator ViewHolder生成器 {@link BannerHolderCreator} And {@link BannerHolderCreator.ViewHoler}
     */
    public void setPages(List<T> datas, BannerHolderCreator mzHolderCreator) {
        if (datas == null || mzHolderCreator == null) {
            return;
        }
        data = datas;
        //如果在播放，就先让播放停止
        pause();

        //增加一个逻辑：由于魅族模式会在一个页面展示前后页面的部分，因此，数据集合的长度至少为3,否则，自动为普通Banner模式
        //不管配置的:open_mz_mode 属性的值是否为true

        if (datas.size() < 3) {
            isOpenMZEffect = false;
            MarginLayoutParams layoutParams = (MarginLayoutParams) viewpager.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            viewpager.setLayoutParams(layoutParams);
            setClipChildren(true);
            viewpager.setClipChildren(true);
        }
        setOpenMZEffect();
        // 2017.7.20 fix：将Indicator初始化放在Adapter的初始化之前，解决更新数据变化更新时crush.
        //初始化Indicator
        initIndicator();

        adapter = new BannerPagerAdapter(datas, mzHolderCreator, isCanLoop);
        adapter.setUpViewViewPager(viewpager);
        adapter.setPageClickListener(bannerPageClickListener);


        viewpager.clearOnPageChangeListeners();
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                int realPosition = position % indicators.size();
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;


                // 切换indicator
                int realSelectPosition = currentItem % indicators.size();
                for (int i = 0; i < data.size(); i++) {
                    if (i == realSelectPosition) {
                        indicators.get(i).setImageResource(indicatorRes[1]);
                    } else {
                        indicators.get(i).setImageResource(indicatorRes[0]);
                    }
                }
                // 不能直接将onPageChangeListener 设置给ViewPager ,否则拿到的position 是原始的position
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(realSelectPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isAutoPlay = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isAutoPlay = true;
                        break;
                    default:
                        break;

                }
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });


    }

    /**
     * 设置Indicator 的对齐方式
     *
     * @param indicatorAlign {@link IndicatorAlign#CENTER }{@link IndicatorAlign#LEFT }{@link IndicatorAlign#RIGHT }
     */
    public void setIndicatorAlign(IndicatorAlign indicatorAlign) {
        this.indicatorAlign = indicatorAlign.ordinal();
        LayoutParams layoutParams = (LayoutParams) indicatorContainer.getLayoutParams();
        if (indicatorAlign == IndicatorAlign.LEFT) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (indicatorAlign == IndicatorAlign.RIGHT) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }

        // 2017.8.27 添加：增加设置Indicator 的上下边距。

        layoutParams.setMargins(0, indicatorPaddingTop, 0, indicatorPaddingBottom);
        indicatorContainer.setLayoutParams(layoutParams);

    }


    public LinearLayout getIndicatorContainer() {
        return indicatorContainer;
    }

    /**
     * 设置ViewPager切换的速度
     *
     * @param duration 切换动画时间
     */
    public void setDuration(int duration) {
        viewpagerScroller.setDuration(duration);
    }

    /**
     * 设置是否使用ViewPager默认是的切换速度
     *
     * @param useDefaultDuration 切换动画时间
     */
    public void setUseDefaultDuration(boolean useDefaultDuration) {
        viewpagerScroller.setUseDefaultDuration(useDefaultDuration);
    }

    /**
     * 获取Banner页面切换动画时间
     *
     * @return
     */
    public int getDuration() {
        return viewpagerScroller.getScrollDuration();
    }


    public static class BannerPagerAdapter<T> extends PagerAdapter {
        private List<T> data;
        private BannerHolderCreator bannerHolderCreator;
        private ViewPager viewpager;
        private boolean canLoop;
        private BannerPageClickListener pageClickListener;
        private final int looperCountFactor = 500;

        public BannerPagerAdapter(List<T> datas, BannerHolderCreator bannerHolderCreator, boolean canLoop) {
            if (data == null) {
                data = new ArrayList<>();
            }
            //data.add(datas.get(datas.size()-1));// 加入最后一个
            for (T t : datas) {
                data.add(t);
            }
            // data.add(datas.get(0));//在最后加入最前面一个
            this.bannerHolderCreator = bannerHolderCreator;
            this.canLoop = canLoop;
        }

        public void setPageClickListener(BannerPageClickListener pageClickListener) {
            this.pageClickListener = pageClickListener;
        }

        /**
         * 初始化Adapter和设置当前选中的Item
         *
         * @param viewPager
         */
        public void setUpViewViewPager(ViewPager viewPager) {
            this.viewpager = viewPager;
            this.viewpager.setAdapter(this);
            this.viewpager.getAdapter().notifyDataSetChanged();
            int currentItem = canLoop ? getStartSelectItem() : 0;
            //设置当前选中的Item
            this.viewpager.setCurrentItem(currentItem);
        }

        private int getStartSelectItem() {
            if (getRealCount() == 0) {
                return 0;
            }
            // 我们设置当前选中的位置为Integer.MAX_VALUE / 2,这样开始就能往左滑动
            // 但是要保证这个值与getRealPosition 的 余数为0，因为要从第一页开始显示
            int currentItem = getRealCount() * looperCountFactor / 2;
            if (currentItem % getRealCount() == 0) {
                return currentItem;
            }
            // 直到找到从0开始的位置
            while (currentItem % getRealCount() != 0) {
                currentItem++;
            }
            return currentItem;
        }

        public void setDatas(List<T> datas) {
            data = datas;
        }

        @Override
        public int getCount() {
            // 2017.6.10 bug fix
            // 如果getCount 的返回值为Integer.MAX_VALUE 的话，那么在setCurrentItem的时候会ANR(除了在onCreate 调用之外)
            return canLoop ? getRealCount() * looperCountFactor : getRealCount();//ViewPager返回int 最大值
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = getView(position, container);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            // 轮播模式才执行
            if (canLoop) {
                int position = viewpager.getCurrentItem();
                if (position == getCount() - 1) {
                    position = 0;
                    setCurrentItem(position);
                }
            }

        }

        private void setCurrentItem(int position) {
            try {
                viewpager.setCurrentItem(position, false);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        /**
         * 获取真实的Count
         *
         * @return
         */
        private int getRealCount() {
            return data == null ? 0 : data.size();
        }

        /**
         * @param position
         * @param container
         * @return
         */
        private View getView(int position, ViewGroup container) {

            final int realPosition = position % getRealCount();
            BannerHolderCreator.ViewHoler holder = null;
            // create holder
            holder = bannerHolderCreator.createViewHolder();

            if (holder == null) {
                throw new RuntimeException("can not return a null holder");
            }
            // create View
            View view = holder.createView(container.getContext());

            if (data != null && data.size() > 0) {
                holder.onBind(container.getContext(), realPosition, data.get(realPosition));
            }

            // 添加点击事件
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pageClickListener != null) {
                        pageClickListener.onPageClick(v, realPosition);
                    }
                }
            });

            return view;
        }


    }

    /**
     * ＊由于ViewPager 默认的切换速度有点快，因此用一个Scroller 来控制切换的速度
     * <p>而实际上ViewPager 切换本来就是用的Scroller来做的，因此我们可以通过反射来</p>
     * <p>获取取到ViewPager 的 mScroller 属性，然后替换成我们自己的Scroller</p>
     */
    public static class ViewPagerScroller extends Scroller {
        private int mDuration = 800;// ViewPager默认的最大Duration 为600,我们默认稍微大一点。值越大越慢。
        private boolean mIsUseDefaultDuration = false;

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mIsUseDefaultDuration ? duration : mDuration);
        }

        public void setUseDefaultDuration(boolean useDefaultDuration) {
            mIsUseDefaultDuration = useDefaultDuration;
        }

        public boolean isUseDefaultDuration() {
            return mIsUseDefaultDuration;
        }

        public void setDuration(int duration) {
            mDuration = duration;
        }


        public int getScrollDuration() {
            return mDuration;
        }
    }

    /**
     * Banner page 点击回调
     */
    public interface BannerPageClickListener {
        void onPageClick(View view, int position);
    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
