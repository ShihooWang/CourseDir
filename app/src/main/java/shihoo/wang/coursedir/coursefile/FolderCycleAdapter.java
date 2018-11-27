package shihoo.wang.coursedir.coursefile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shihoo.wang.coursedir.R;
import shihoo.wang.coursedir.bean.FileDirBean;

/**
 * Created by shihoo.wang on 2018/11/21.
 * Email shihu.wang@bodyplus.cc
 */

public class FolderCycleAdapter extends RecyclerView.Adapter {

    private final LayoutInflater inflater;
    private CourseFileDirEnum mType;
    private List<FileDirBean> mData;
    private CourseFileDirViewInterface courseFileDirViewInterface;

    public FolderCycleAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<FileDirBean> mList) {
        this.mData = mList;
    }

    public void setType(CourseFileDirEnum type) {
        this.mType = type;
    }

    public void setCourseFileDirViewInterface(CourseFileDirViewInterface view){
        this.courseFileDirViewInterface = view;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecordViewHolder(inflater.inflate(R.layout.item_course_file, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setRecordInfoValues((RecordViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setRecordInfoValues(RecordViewHolder viewHolder, final int position) {
        FileDirBean bean = mData.get(position);
        if (bean.getFolderId() != null) {
            viewHolder.igIcon.setImageResource(R.drawable.ic_course_folder);
            viewHolder.tvName.setText(bean.getFolderName());
            viewHolder.tvCourseNum.setVisibility(View.GONE);
        } else {
            viewHolder.igIcon.setImageResource(R.drawable.ic_course_file);
            viewHolder.tvName.setText(bean.getTemplateName());
            viewHolder.tvCourseNum.setVisibility(View.VISIBLE);
            viewHolder.tvCourseNum.setText(bean.getTotalStep() + "个动作");
        }
        viewHolder.tvTime.setText(bean.getLastDatetime().substring(0, 11));

        if (mType == CourseFileDirEnum.SHOW || mType == CourseFileDirEnum.BASE) {
            viewHolder.igMore.setVisibility(View.VISIBLE);

            viewHolder.igMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseFileDirViewInterface.onMoreClick(position);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    courseFileDirViewInterface.onItemLongClick(position);
                    return true;
                }
            });

        } else if (mType==CourseFileDirEnum.MOVE || mType==CourseFileDirEnum.SELECT) {
            viewHolder.igMore.setVisibility(View.GONE);
            viewHolder.igMore.setOnClickListener(null);
            viewHolder.itemView.setOnLongClickListener(null);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseFileDirViewInterface.onItemClick(position);
            }
        });
    }



    class RecordViewHolder extends RecyclerView.ViewHolder {
        private ImageView igIcon, igMore;
        private TextView tvName, tvTime, tvCourseNum;

        public RecordViewHolder(View itemView) {
            super(itemView);
            igIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            igMore = (ImageView) itemView.findViewById(R.id.iv_more);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvCourseNum = (TextView) itemView.findViewById(R.id.tv_course_num);
        }
    }

}
