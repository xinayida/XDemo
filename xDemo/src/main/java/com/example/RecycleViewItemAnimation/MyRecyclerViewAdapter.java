//package com.example.RecycleViewItemAnimation;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.ValueAnimator;
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.R;
//
//import java.util.List;
//
///**
// * Created by Stefan on 2019/4/12.
// */
//public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.Holder> implements View.OnClickListener {
//
//    private Context context;
//    private List<String> data;
//    private int[] animRes;
//    private final static long FIRE_ANIMATION_DURATION = 2500;
//    public final static long MOVE_DURATION = 500;//需要在移除的移动动画结束之后调用notifyItemRangeChanged,否则移动动画会不正常
//
//    public MyRecyclerViewAdapter(Context context, List<String> data) {
//        this.context = context;
//        this.data = data;
//    }
//
//    @Override
//    public MyRecyclerViewAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new Holder(LayoutInflater.from(context).inflate(R.layout.recycle_anim_item_layout, parent, false));
//
//    }
//
//    @Override
//    public void onBindViewHolder(MyRecyclerViewAdapter.Holder holder, int position) {
//        holder.tv.setText(data.get(position));
//        holder.btn.setOnClickListener(this);
//        holder.itemView.setTag(position);
//        final View animView = holder.itemView.findViewById(R.id.anim_view);
//        final View animCover = holder.itemView.findViewById(R.id.anim_cover_layout);
//        animView.setVisibility(View.GONE);
//        ViewGroup.LayoutParams lp = animCover.getLayoutParams();
//        lp.height = 0;
//        animCover.setLayoutParams(lp);
//    }
//
//    /**
//     * 增加数据
//     */
//    public void addData(int position) {
//        data.add(position, "add");
//        notifyItemInserted(position);//注意这里
//    }
//
//    /**
//     * 移除数据
//     */
//    public void removeData(int position) {
//        data.remove(position);
//        notifyItemRangeChanged(position, data.size() - position);
//        notifyItemRemoved(position);//注意这里
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (animRes == null) {
//            animRes = getRes();
//        }
//        final View animView = ((ViewGroup) v.getParent()).findViewById(R.id.anim_view);
//        final View animCover = ((ViewGroup) v.getParent()).findViewById(R.id.anim_cover_layout);
//        final View itemView = (View) v.getParent();
//        animView.setVisibility(View.VISIBLE);
//        final int pos = (int) itemView.getTag();
//        final int viewHeight = itemView.getHeight();
//        final int fromY = viewHeight - 200;
//        int toY = -165;
//        final int totalY = fromY - toY;
//        final int total = 30;//6-36帧移动，一共是45帧
//
//        animView.setY(fromY);
//        final ValueAnimator fireAnim = ValueAnimator.ofFloat(0f, 45f).setDuration(FIRE_ANIMATION_DURATION);
//        fireAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (float) animation.getAnimatedValue();
//                animView.setBackgroundResource(animRes[(int) ((animRes.length - 1) * animation.getAnimatedFraction())]);
//                if (value > 6 && value < 36.5) {
//                    float percent = Math.min((value - 6) / total, 1f);
//                    ViewGroup.LayoutParams lp = animCover.getLayoutParams();
//                    lp.height = (int) (viewHeight * percent);
//                    animCover.setLayoutParams(lp);
//                    animView.setY(fromY - totalY * percent);
//                }
//            }
//        });
//
//        fireAnim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                removeData(pos);
//                fireAnim.removeAllUpdateListeners();
//                fireAnim.removeAllListeners();
//            }
//        });
//
//        fireAnim.start();
//    }
//
//    class Holder extends RecyclerView.ViewHolder {
//
//        TextView tv;
//        View btn;
//
//        public Holder(View itemView) {
//            super(itemView);
//            tv = (TextView) itemView.findViewById(R.id.tv);
//            btn = itemView.findViewById(R.id.fire);
//        }
//    }
//
//    private int[] getRes() {
////        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.anim_activate);
////        int len = typedArray.length();
//        int len = 45;
//        int[] resId = new int[len];
//        for (int i = 0; i < len; i++) {
//            resId[i] = R.drawable.fire_particular_00000 + i; //typedArray.getResourceId(i, -1);
//        }
////        typedArray.recycle();
//        return resId;
//    }
//}
