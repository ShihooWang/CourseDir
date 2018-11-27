package shihoo.wang.coursedir.coursefile;


import shihoo.wang.coursedir.bean.netbean.CourseFileNetData;

/**
 * Created by shihoo.wang on 2018/11/17.
 * Email shihu.wang@bodyplus.cc
 */

public interface CourseFileDirViewInterface extends RecycleItemClickListener{

    void setFolderDirData(CourseFileNetData data);

    void setFolderAndType(CourseTagBean bean);

    void closedSelf();

    String getParentFolder();

}
