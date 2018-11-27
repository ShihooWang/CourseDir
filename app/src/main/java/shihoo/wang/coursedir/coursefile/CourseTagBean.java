package shihoo.wang.coursedir.coursefile;

import android.support.annotation.IdRes;

import java.io.Serializable;

/**
 * Created by shihoo.wang on 2018/11/19.
 * Email shihu.wang@bodyplus.cc
 */

public class CourseTagBean implements Serializable {

    /**
     * @param type 功能 类型
     * @param parentFolder 文件夹的ID
     * @param viewId fragment 所占的位置
     * @param addToBackStack 是否加入到返回键
     */
    public String parentFolder = "0";
    public CourseFileDirEnum type = CourseFileDirEnum.BASE;
    @IdRes
    public int viewId;
    public boolean addToBackStack;
    public String folderName;
    public String depth;
}
