package shihoo.wang.coursedir.floatviewgroup;

import android.content.Context;

import shihoo.wang.coursedir.R;
import shihoo.wang.coursedir.coursefile.FloatViewHelper;
import shihoo.wang.coursedir.coursefile.FunctionItemClickListener;

/**
 * Created by shihoo.wang on 2018/11/17.
 * Email shihu.wang@bodyplus.cc
 */

public class FunctionAdapter extends FloatAdapter {

    private String[] mHints = {"新建教案专题","新建训练教案"};
    private int[] mDrawables = {R.drawable.ic_rectangle, R.drawable.ic_oval};
    private Context mContext;
    private FunctionItemClickListener functionItemClickListener;

    public FunctionAdapter(Context context, FunctionItemClickListener listener) {
        super(context);
        this.mContext = context;
        this.functionItemClickListener = listener;
    }


    @Override
    public int getCount() {
        return mHints.length;
    }

    @Override
    public String getItemHint(int position) {
        return mHints[position];
    }

    @Override
    public int getItemResource(int position) {
        return mDrawables[position];
    }

    @Override
    public int getMainResource() {
        return R.drawable.ic_float_switch;
    }

    @Override
    public void onItemClick(int position) {
        if (functionItemClickListener != null){
            if(position == 0){
                functionItemClickListener.onCreateNewFolder();
            }else{
                functionItemClickListener.onCreateNewFile();
            }
        }

        final FloatViewGroup floatViewGroup = FloatViewHelper.getInstance().getFloatViewGroup();
        if (floatViewGroup != null){
            floatViewGroup.postDelayed(new Runnable() {
                @Override
                public void run() {
                    floatViewGroup.checkShrinkViews();
                }
            },200);
        }

    }

}
