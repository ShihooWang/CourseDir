package shihoo.wang.coursedir.coursefile;

import android.content.Context;

import shihoo.wang.coursedir.floatviewgroup.FloatViewGroup;
import shihoo.wang.coursedir.floatviewgroup.FunctionAdapter;


/**
 * Created by shihoo.wang on 2018/11/15.
 * Email shihu.wang@bodyplus.cc
 *
 * 用来管理课程页面悬浮小球的单例
 * 用来创建私教文件夹
 */

public class FloatViewHelper implements FunctionItemClickListener{
    private static FloatViewHelper mInstance;

    private FloatViewGroup floatViewGroup;

    private FloatViewHelper() {

    }

    public static synchronized FloatViewHelper getInstance(){
        if (mInstance == null){
            mInstance = new FloatViewHelper();
        }
        return mInstance;
    }

    public void setFloatViewGroup(FloatViewGroup floatViewGroup){
        this.floatViewGroup = floatViewGroup;
    }

    public void setFloatViewAdapter(Context context){
        FloatViewGroup floatViewGroup = getFloatViewGroup();
        if (floatViewGroup != null){
            floatViewGroup.setAdapter(new FunctionAdapter(context,this));
        }
    }

    public FloatViewGroup getFloatViewGroup() {
        return floatViewGroup;
    }


    @Override
    public void onCreateNewFolder() {
        FunctionItemClickListener listener = CourseFileDirFragmentControl.getInstance().getItemClickListener();
        if (listener != null){
            listener.onCreateNewFolder();
        }
    }

    @Override
    public void onCreateNewFile() {
        FunctionItemClickListener listener = CourseFileDirFragmentControl.getInstance().getItemClickListener();
        if (listener != null){
            listener.onCreateNewFile();
        }
    }
}
