package shihoo.wang.coursedir.dao;

import java.util.ArrayList;
import java.util.List;

import shihoo.wang.coursedir.App;
import shihoo.wang.coursedir.bean.FileDirBean;
import shihoo.wang.coursedir.bean.netbean.CourseFileNetData;

/**
 * Created by shihoo.wang on 2018/11/19.
 * Email shihu.wang@bodyplus.cc
 *
 * 方便管理数据
 */

public class CourseDirDao {

    public void init(){
        // 初始化的时候 创建文件层级为0的文件夹
        List<FileDirBean> list = App.getDaoInstant().getFileDirBeanDao().queryBuilder()
                .where(FileDirBeanDao.Properties.ParentFolder.eq("0")) // 0为课程，1视频课文件夹，2私教课文件夹
                .orderAsc(FileDirBeanDao.Properties.Id)
                .build().list();
        if (list == null){
            FileDirBean bean = new FileDirBean();
            bean.setParentFolder("--");
            bean.setDepth("0");
            bean.setFolderId("0");
            bean.setFolderName("桌面");
            insertFileDirBean(bean);
        }
    }

    /**
     *  添加数据
     * @param bean
     */
    public static void insertFileDirBean(FileDirBean bean){
        if (bean != null) {
            App.getDaoInstant().getFileDirBeanDao().insertOrReplace(bean);
        }
    }

    /**
     * 删除数据
     * @param bean
     */
    public static void moveFileDirBean(FileDirBean bean){
        if (bean != null){
            List<FileDirBean> list = App.getDaoInstant().getFileDirBeanDao().queryBuilder()
                    .where(FileDirBeanDao.Properties.TemplateId.eq(bean.getTemplateId())).build().list();
            App.getDaoInstant().getFileDirBeanDao().deleteInTx(list);
        }
    }

    /**
     * 更新数据
     * @param bean
     */
    private static void updateFileDirBean(FileDirBean bean){
        if (bean != null){
            App.getDaoInstant().getFileDirBeanDao().update(bean);
        }
    }


    /**
     * 查询全部数据
     * @return
     */
    private static List<FileDirBean> queryAll(){
        return App.getDaoInstant().getFileDirBeanDao().loadAll();
    }


    /**
     * 查询 字段值
     * @param fileId
     * @return
     */
    public static List<FileDirBean> queryFolderData(String fileId){
        List<FileDirBean> list = App.getDaoInstant().getFileDirBeanDao().queryBuilder()
                .where(FileDirBeanDao.Properties.ParentFolder.eq(fileId),FileDirBeanDao.Properties.FolderType.eq("4")) // 0为课程，1视频课文件夹，2私教课文件夹
                .orderAsc(FileDirBeanDao.Properties.Id)
                .build().list();
        return list;
    }

    /**
     * 查询 字段值
     * @param fileId
     * @return
     */
    public static List<FileDirBean> queryFileData(String fileId){
        List<FileDirBean> list = App.getDaoInstant().getFileDirBeanDao().queryBuilder()
                .where(FileDirBeanDao.Properties.ParentFolder.eq(fileId),FileDirBeanDao.Properties.FolderType.eq("0")) // 0为课程，1视频课文件夹，2私教课文件夹
                .orderAsc(FileDirBeanDao.Properties.Id)
                .build().list();
        return list;
    }


    /**
     * 此处希望做一个批量删除和批量插入的功能 目前优化的不够 不太适合大批量数据
     * @param parentFolder
     * @param data
     */
    public static void insertFileNetData(String parentFolder, CourseFileNetData data){
        deleteDatas(parentFolder);
        if (data != null){
            if (data.folderList != null){
                if (!data.folderList.isEmpty()){
                    insertDatas(data.folderList);
                }
            }

            if (data.tplData != null){
                if (!data.tplData.isEmpty()){
                    insertDatas(data.tplData);
                }
            }
        }
    }

    private static void insertDatas(ArrayList<FileDirBean> list){
        App.getDaoInstant().getFileDirBeanDao().insertInTx(list);
    }

    public static void deleteDatas(String parentFolder){
        List<FileDirBean> list = App.getDaoInstant().getFileDirBeanDao().queryBuilder()
                .where(FileDirBeanDao.Properties.ParentFolder.eq(parentFolder))
                .build().list();
        App.getDaoInstant().getFileDirBeanDao().deleteInTx(list);
    }

}
