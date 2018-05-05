package com.example.administrator.android50test.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.administrator.android50test.DealWithRyView.PicResource;
import com.example.administrator.android50test.DealWithRyView.RecyclerItemClickListener;
import com.example.administrator.android50test.DealWithRyView.SpaceGridItemDecoration;
import com.example.administrator.android50test.DealWithRyView.SpaceListItemDecoration;
import com.example.administrator.android50test.R;
import com.example.administrator.android50test.adapter.ItemAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/5/4.
 */

public class TabPagerFragment extends Fragment {

    public static TabPagerFragment newInstance(int count, String title) {

        TabPagerFragment fragment = new TabPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEM_COUNT, count);
        bundle.putString(TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected Context mContext;

    private static final String ITEM_COUNT = "item_count";
    private static final String TITLE      = "title";


    private RefreshLayout mRefreshLayout;
    private String        mTitle;
    private int           mCount;


    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private int height =0;
    private AnimationSet mAnimationSet;

    private HomeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coordinator_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initViews(view);
        initListeners();

        //实现拖拽和删除
        setDragAndDelete();


    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mTitle = getArguments().getString(TITLE);
        mCount = getArguments().getInt(ITEM_COUNT);
    }



    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_coordinator_tab_content);

        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));




        //设置随机高度
        height = (int) (Math.random() * 100);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.HORIZONTAL));

        mRecyclerView.addItemDecoration(new SpaceGridItemDecoration(getActivity(),5));
        mRecyclerView.addItemDecoration(new SpaceListItemDecoration(
                getActivity(),5,true,
                getResources().getColor(R.color.color_fb5428),false,true,false));


        mAdapter= new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // ...

                //Toast.makeText(HomeActivity.this, "条目点击：" + position, Toast.LENGTH_SHORT).show();

                // 点击放大

                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
                final AlertDialog dialog = new AlertDialog.Builder(getActivity()
                ).create();
                ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
                Picasso.with(getActivity()).
                        load(mDatas.get(position)).fit()
                        .error(R.mipmap.ic_launcher).into(img); // 这个是加载网络图片的，可以是自己的图片设置方法
                dialog.setView(imgEntryView); // 自定义dialog
                dialog.show();
                //增加点击放大效果
                AnimationSet animationSet = new AnimationSet(true);
                if(mAnimationSet!=null && mAnimationSet != animationSet){
                    ScaleAnimation scaleAnimation = new ScaleAnimation(2,0.5f,2,0.5f,
                            Animation.RELATIVE_TO_PARENT,0.5f,//使用动画播放图片
                            Animation.RELATIVE_TO_PARENT,0.5f);
                    scaleAnimation.setDuration(500);
                    mAnimationSet.addAnimation(scaleAnimation);
                    mAnimationSet.setFillAfter(false); //让其保持动画结束时的状态。
                    img.startAnimation(mAnimationSet);
                }
                ScaleAnimation scaleAnimation = new ScaleAnimation((float) 0,1f, (float) 0,1f,
                        Animation.RELATIVE_TO_SELF,0.5f,
                        Animation.RELATIVE_TO_SELF,0.5f);
                scaleAnimation.setDuration(3000);
                animationSet.addAnimation(scaleAnimation);
                animationSet.setFillAfter(true);
                img.startAnimation(animationSet);
                mAnimationSet = animationSet;

                // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
                imgEntryView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramView) {
                        dialog.cancel();
                    }
                });
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
                //Toast.makeText(HomeActivity.this,"条目长按："+position,Toast.LENGTH_SHORT).show();
            }
        }));
    }





    private void initListeners() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });

    }

    private void initData() {
        mDatas= PicResource.getPic();
    }

    private void setDragAndDelete() {

        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            private RecyclerView.ViewHolder vh;

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder
                    viewHolder) {
                // 拖拽的标记，这里允许上下左右四个方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
                // 滑动的标记，这里允许左右滑动
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            /*
              这个方法会在某个Item被拖动和移动的时候回调，这里我们用来播放动画，当viewHolder不为空时为选中状态
              否则为释放状态
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    vh = viewHolder;
                    pickUpAnimation(viewHolder.itemView);
                } else {
                    if (vh != null) {
                        putDownAnimation(vh.itemView);
                    }
                }
            }




            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // 移动时更改列表中对应的位置并返回true
                Collections.swap(mDatas, viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
                return true;
            }

            /*
              当onMove返回true时调用
             */
            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int
                    fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                // 移动完成后刷新列表
                mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 将数据集中的数据移除
                mDatas.remove(viewHolder.getAdapterPosition());
                // 刷新列表
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(mRecyclerView);
    }



    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            //holder.iv_pic.setText(mDatas.get(position));
            //默认显示placeholder，加载错误显示ic_launcher，指定placeholder大小显示

            Log.e("picInfo:::",mDatas.get(position));
            Picasso.with(getActivity()).
                    load(mDatas.get(position))
                    .placeholder(R.mipmap.abc)
                    .centerCrop()
                     .resize(500+height,500+height)
                    .error(R.mipmap.abc).into(holder.iv_pic);
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            ImageView iv_pic;

            public MyViewHolder(View view)
            {
                super(view);
                iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            }
        }
    }


    private void pickUpAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 1f, 10f);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }

    private void putDownAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 10f, 1f);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }
}
