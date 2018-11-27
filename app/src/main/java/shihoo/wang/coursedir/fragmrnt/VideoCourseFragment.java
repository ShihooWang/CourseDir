package shihoo.wang.coursedir.fragmrnt;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import shihoo.wang.coursedir.bean.CategoryBean;
import shihoo.wang.coursedir.R;

/**
 * Created by shihoo.wang on 2018/11/18.
 * Email shihu.wang@bodyplus.cc
 */

public class VideoCourseFragment extends Fragment {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<CategoryBean> mList_template = new ArrayList<>();
    private CycleAdapter recommendAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_sport_course, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        initView();
    }

    private void initView(){
        /**
         *  viewHolder.tvName.setText(bean.getTemplateName());
            viewHolder.tvTime.setText(bean.getLastDatetime().substring(0, 11));
            viewHolder.tvCourseNum.setText(bean.getTotalStep()+ "个动作");
         */
        CategoryBean bean = new CategoryBean();
        bean.setTemplateName("孤山寺北贾亭西");
        bean.setLastDatetime("2018-11-22 22:03:14");
        bean.setTotalStep("13");

        CategoryBean bean2 = new CategoryBean();
        bean2.setTemplateName("水面初平云脚低");
        bean2.setLastDatetime("2018-11-25 11:30:26");
        bean2.setTotalStep("21");

        mList_template.add(bean);
        mList_template.add(bean2);

        recommendAdapter = new CycleAdapter(getActivity(), mList_template);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recommendAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1200);
            }
        });
    }



    private class CycleAdapter extends RecyclerView.Adapter{
        private Context mContext;
        private LayoutInflater inflater;
        private List<CategoryBean> mPlanTemplateBean = new ArrayList<>();
        private OnItemClickListener mItemClickListener;

        public CycleAdapter(Context context,List<CategoryBean> mList){
            this.mContext = context;
            this.inflater = LayoutInflater.from(context);
            this.mPlanTemplateBean = mList;
        }

        public void setData(List<CategoryBean> mList) {
            this.mPlanTemplateBean = mList;
            this.notifyDataSetChanged();
        }

        private void setItemClickListener(OnItemClickListener onItemClickListener){
            this.mItemClickListener = onItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecordViewHolder viewHolder = new RecordViewHolder(inflater.inflate(R.layout.item_course_file, parent, false));

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            setRecordInfoValues((RecordViewHolder) holder, position);
            if (position == mPlanTemplateBean.size()-1){
//                if (mPlanTemplateBean.size() >= Config.PullLoadSize) {
////                    loadMore(Config.PullLoadSize);
//                }
            }
            if (mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(position);
                    }
                });
//                if (mType != CourseFileDirEnum.SELECT){
//                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                        @Override
//                        public boolean onLongClick(View v) {
//                            mItemClickListener.onItemLongClick(position);
//                            return true;
//                        }
//                    });
//                }

            }
        }

        @Override
        public int getItemCount() {
            return mPlanTemplateBean.size();
        }

        private void setRecordInfoValues(RecordViewHolder viewHolder, final int position) {
            CategoryBean bean = mPlanTemplateBean.get(position);
            viewHolder.tvName.setText(bean.getTemplateName());
            viewHolder.tvTime.setText(bean.getLastDatetime().substring(0, 11));
            viewHolder.tvCourseNum.setText(bean.getTotalStep()+ "个动作");
            viewHolder.igMore.setVisibility(View.GONE);
        }
    }


    public class RecordViewHolder extends RecyclerView.ViewHolder {
        private ImageView igIcon,igMore;
        private TextView tvName,tvTime,tvCourseNum;

        public RecordViewHolder(View itemView) {
            super(itemView);
            igIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvCourseNum = (TextView) itemView.findViewById(R.id.tv_course_num);
            igMore = (ImageView) itemView.findViewById(R.id.iv_more);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }







}