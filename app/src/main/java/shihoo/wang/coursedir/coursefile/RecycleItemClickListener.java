package shihoo.wang.coursedir.coursefile;

/**
 * Created by shihoo.wang on 2018/11/21.
 * Email shihu.wang@bodyplus.cc
 */

public interface RecycleItemClickListener extends FunctionItemClickListener{

    void onMoreClick(int position);

    void onItemLongClick(int position);

    void onItemClick(int position);
}
