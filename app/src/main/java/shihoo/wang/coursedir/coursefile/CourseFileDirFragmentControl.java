package shihoo.wang.coursedir.coursefile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import shihoo.wang.coursedir.utils.DateUtil;
import shihoo.wang.coursedir.utils.FastClickUtil;
import shihoo.wang.coursedir.R;
import shihoo.wang.coursedir.bean.FileDirBean;
import shihoo.wang.coursedir.bean.netbean.CourseFileNetData;
import shihoo.wang.coursedir.bean.netbean.NetFileCopy;
import shihoo.wang.coursedir.bean.netbean.NetFolderCreate;
import shihoo.wang.coursedir.bean.netbean.NetFolderDelete;
import shihoo.wang.coursedir.bean.netbean.NetFolderMove;
import shihoo.wang.coursedir.bean.netbean.NetFolderRename;
import shihoo.wang.coursedir.dao.CourseDirDao;
import shihoo.wang.coursedir.fragmrnt.CourseFileDirFragment;


/**
 * Created by shihoo.wang on 2018/11/17.
 * Email shihu.wang@bodyplus.cc
 *
 * 用来管理课程文件夹相关fragment
 *
 * 适用同一个Activity中所有的Fragment
 *
 * 单例模式
 */

public class CourseFileDirFragmentControl {



    private static CourseFileDirFragmentControl instance;
    private Vector<CourseFileDirViewInterface> dirViewVector;
    private Vector<CourseFileDirViewInterface> moveViewVector;
    private Vector<CourseFileDirViewInterface> selectViewVector;
//    private FragmentManager fragmentManager;
    private FileDirBean selectMoveFile;

    private CourseFileDirFragmentControl(){
        this.dirViewVector = new Vector<>();
        this.moveViewVector = new Vector<>();
        this.selectViewVector = new Vector<>();
    }

    public static synchronized CourseFileDirFragmentControl getInstance(){
        if (instance == null){
            instance = new CourseFileDirFragmentControl();
        }
        return instance;
    }

//    public void setFragmentManager(FragmentManager fragmentManager) {
//        this.fragmentManager = fragmentManager;
//    }

    public FunctionItemClickListener getItemClickListener(){
        if (dirViewVector != null) {
            return dirViewVector.lastElement();
        }else {
            return null;
        }
    }

    public void removeCourseFileDirView(CourseFileDirViewInterface courseFileDirViewInterface){
        if (dirViewVector!=null && courseFileDirViewInterface !=null) {
            if (dirViewVector.contains(courseFileDirViewInterface)){
                dirViewVector.remove(courseFileDirViewInterface);
            }
        }
        if (moveViewVector!=null && courseFileDirViewInterface !=null) {
            if (moveViewVector.contains(courseFileDirViewInterface)){
                moveViewVector.remove(courseFileDirViewInterface);
            }
        }
        if (selectViewVector!=null && courseFileDirViewInterface !=null) {
            if (selectViewVector.contains(courseFileDirViewInterface)){
                selectViewVector.remove(courseFileDirViewInterface);
            }
        }
    }

    /**
     * 关闭掉所有的移动文件夹页面
     */
    private void closedAllMoveView(){
        if (moveViewVector != null) {
            for (CourseFileDirViewInterface view : moveViewVector){
                view.closedSelf();
            }
            moveViewVector.clear();
        }

    }

    /**
     * 关闭掉所有的选择文件页面
     */
    private void closedAllSelectView(){
        if (selectViewVector != null) {
            for (CourseFileDirViewInterface view : selectViewVector){
                view.closedSelf();
            }
            selectViewVector.clear();
        }
    }

    /**
     * 移动文件
     */
    public void selectMoveFile(FragmentManager fragmentManager, FileDirBean bean){
        // 展示 移动页面
        selectMoveFile = bean;
        CourseTagBean tagBean = new CourseTagBean();
        tagBean.type = CourseFileDirEnum.MOVE;
        tagBean.viewId = R.id.full_screen;
        tagBean.parentFolder = "0";
        tagBean.addToBackStack = true;
        tagBean.folderName = "桌面";
        tagBean.depth = "0";
        CourseFileDirFragmentControl.getInstance().addNewFragment(fragmentManager,null, tagBean);
    }

    /**
     * 点击移动到此专题时 调用的方法
     * @param toFolder 目标文件的ID
     */
    public void moveCourseFileToHere(String toFolder) {
        if (selectMoveFile != null){
            moveFileToNet(toFolder,selectMoveFile);
        }
    }

    /**
     * 成功创建教案
     */
    public void createNewFileSucceed(String parentFolder){
        updateDataFromNet(parentFolder);
    }

    /**
     * 成功创建专题
     */
    private void createNewFolderToDao(FileDirBean bean, NetFolderCreate data){
        updateDataFromNet(bean.getParentFolder());
    }

    /**
     * 成功删除专题
     */
    private void deleteFolderBean(FileDirBean bean,NetFolderDelete data){
        updateDataFromNet(bean.getParentFolder());
    }

    /**
     * 成功修改名称
     */
    private void reNameFolderToDao(FileDirBean bean, NetFolderRename data){
        updateDataFromNet(bean.getParentFolder());
    }

    /**
     * 成功移动教案
     * @param toFolderId 移动到的目标
     */
    private void moveFileToDao(FileDirBean bean, NetFolderMove data, String toFolderId){
        try {
            CourseDirDao.moveFileDirBean(bean);
            updateMoveLocalData(bean.getParentFolder());
            CourseDirDao.deleteDatas(toFolderId);
            updateDataFromNet(toFolderId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closedAllMoveView();
            selectMoveFile = null;
        }
    }

    /**
     * 成功复制教案
     */
    private void copyFileToDao(FileDirBean bean, NetFileCopy data){
        updateDataFromNet(bean.getParentFolder());
    }

    /**
     * fragment页面跳转
     */
    public void addNewFragment(FragmentManager fragmentManager, CourseFileDirViewInterface dirView, CourseTagBean bean){
        if (fragmentManager==null || bean==null){
            return;
        }
        if (dirView != null){
            dirView.setFolderAndType(bean);
            if (dirViewVector != null) {
                dirViewVector.addElement(dirView);
            }
        }
        // 第一步 读取本地数据库
        List<FileDirBean> folders = CourseDirDao.queryFolderData(bean.parentFolder);
        List<FileDirBean> tplDatas = CourseDirDao.queryFileData(bean.parentFolder);
        CourseFileNetData data = new CourseFileNetData();

        boolean hasData = false;
        if (folders!=null && !folders.isEmpty()) {
            data.folderList = new ArrayList<>(folders);
            hasData = true;
        }
        if (tplDatas!=null && !tplDatas.isEmpty()) {
            data.tplData = new ArrayList<>(tplDatas);
            hasData = true;
        }
        if (hasData) {
            setFragmentData(fragmentManager,dirView,bean,data,false);
        }else {
            data.folderList = new ArrayList<>();
            data.tplData = new ArrayList<>();
            setFragmentData(fragmentManager,dirView,bean,data,false);
            // 从网络或本地数据库读取
//            getDataFromNet(fragmentManager,dirView,bean);
        }
    }

    private void notifyFragment(String parentFolder){
        CourseFileNetData data = new CourseFileNetData();
        data.folderList = new ArrayList<>(CourseDirDao.queryFolderData(parentFolder));
        data.tplData = new ArrayList<>(CourseDirDao.queryFileData(parentFolder));
        CourseFileDirViewInterface viewInterface = getCourseFileDirViewInterface(parentFolder);
        if (viewInterface == null){
            return;
        }
        viewInterface.setFolderDirData(data);
    }

    /**
     * 更新后请求网络的返回
     */
    private void updateFragmentData(String parentFolder, CourseFileNetData data){
        CourseDirDao.insertFileNetData(parentFolder,data);
        CourseFileDirViewInterface viewInterface = getCourseFileDirViewInterface(parentFolder);
        if (viewInterface == null){
            return;
        }
        viewInterface.setFolderDirData(data);
    }

    private void updateMoveLocalData(String parentFolder){ // 使用本本低缓存数据
        List<FileDirBean> folders = CourseDirDao.queryFolderData(parentFolder);
        List<FileDirBean> tplDatas = CourseDirDao.queryFileData(parentFolder);
        CourseFileNetData data = new CourseFileNetData();

        if (folders!=null && !folders.isEmpty()) {
            data.folderList = new ArrayList<>(folders);
        }
        if (tplDatas!=null && !tplDatas.isEmpty()) {
            data.tplData = new ArrayList<>(tplDatas);
        }
        
        CourseFileDirViewInterface viewInterface = getCourseFileDirViewInterface(parentFolder);
        if (viewInterface == null){
            return;
        }
        viewInterface.setFolderDirData(data);
    }


    /**
     * 请求文件夹层级的返回展示数据
     */
    private void setFragmentData(FragmentManager fragmentManager, CourseFileDirViewInterface dirView, CourseTagBean bean, CourseFileNetData data, boolean isUpdate){
        if (isUpdate) {
            CourseDirDao.insertFileNetData(bean.parentFolder, data);
        }
        if (fragmentManager == null){
            return;
        }
        // 第二步 创建fragment
        if (dirView != null){
            dirView.setFolderAndType(bean);
            dirView.setFolderDirData(data);
        }else {
            CourseFileDirFragment fragment = null;
            String tag = makeFragmentName(bean.type,bean.parentFolder); // TAG 值
            Fragment fgByTag = fragmentManager.findFragmentByTag(tag);
            if (fgByTag != null){
                fragment = (CourseFileDirFragment) fgByTag;
            }
            if (fragment == null){
                fragment = new CourseFileDirFragment();
            }
            fragment.setFolderAndType(bean);
            fragment.setFolderDirData(data);
            if (bean.type == CourseFileDirEnum.SHOW){
//                FloatViewHelper.getInstance().getFloatViewGroup().setVisibility(View.VISIBLE);
                if (dirViewVector!=null ) {
                    dirViewVector.addElement(fragment);
                }
            }else if (bean.type == CourseFileDirEnum.MOVE){
//                FloatViewHelper.getInstance().getFloatViewGroup().setVisibility(View.GONE);
                if (moveViewVector!=null ) {
                    moveViewVector.addElement(fragment);
                }
            }else if (bean.type == CourseFileDirEnum.SELECT){
//                FloatViewHelper.getInstance().getFloatViewGroup().setVisibility(View.GONE);
                if (selectViewVector!=null ) {
                    selectViewVector.addElement(fragment);
                }
            }



            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // 入栈和回退的动作
//            transaction.setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
            transaction.setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_right,R.anim.slide_in_from_right,R.anim.slide_out_to_right);
            transaction.add(bean.viewId, fragment, tag);
            if (bean.addToBackStack) {
                transaction.addToBackStack(null);
            }
            transaction.commit();
        }


    }


    /**
     * 创建时 获取网络数据
     */
    private void getDataFromNet(final FragmentManager fragmentManager, final CourseFileDirViewInterface dirView, final CourseTagBean bean) {
        CourseFileNetData data = new CourseFileNetData();
        modifyParentFolder(data,bean.parentFolder);
        setFragmentData(fragmentManager,dirView,bean,data, true);

//        if (mDisposable!=null && !mDisposable.isDisposed()){
//            mDisposable.dispose();
//        }
//        HashMap<String, String> params = new HashMap<>();
//        params.put("parentId", ""+bean.parentFolder);
//        mDisposable = netApi.getCourseDir(NetTrainConfig.GET_CUSTOM_TPL_WITH_FOLDER, params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<CourseFileNetData>() {
//                    @Override
//                    public void onNext(CourseFileNetData data) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        throwable.printStackTrace();
//                        ToastUtil.show(throwable.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    /**
     * 修改后 获取网络数据
     */
    private void updateDataFromNet(final String parentFolder) {
        // 判断当前是否存在该 文件夹页面
        CourseFileDirViewInterface viewInterface = getCourseFileDirViewInterface(parentFolder);
        if (viewInterface == null){
            return;
        }
        // 模拟请求网络
        CourseFileNetData data = new CourseFileNetData();
        modifyParentFolder(data,parentFolder);
        updateFragmentData(parentFolder, data);

//        if (mDisposable!=null && !mDisposable.isDisposed()){
//            mDisposable.dispose();
//        }
//        HashMap<String, String> params = new HashMap<>();
//        params.put("parentId", ""+parentFolder);
//        mDisposable = netApi.getCourseDir(NetTrainConfig.GET_CUSTOM_TPL_WITH_FOLDER, params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<CourseFileNetData>() {
//                    @Override
//                    public void onNext(CourseFileNetData data) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        throwable.printStackTrace();
//                        ToastUtil.show(throwable.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }



    /**
     * 请求网络 创建文件夹
     */
    public void createFolderToNet(final FileDirBean bean){
        if (FastClickUtil.isFastClick()) {
            return;
        }
        CourseDirDao.insertFileDirBean(bean);
        notifyFragment(bean.getParentFolder());

//        if (mDisposable!=null && !mDisposable.isDisposed()){
//            mDisposable.dispose();
//        }
//        HashMap<String, String> params = new HashMap<>();
//        params.put("parentId", ""+bean.parentFolder); // 	父级文件夹ID
//        params.put("folderName", ""+bean.folderName);          // 文件夹名称
//        mDisposable = netApi.createFolderOrFile(NetTrainConfig.CREATE_FOLDER, params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<NetFolderCreate>() {
//                    @Override
//                    public void onNext(NetFolderCreate data) {
//                        createNewFolderToDao(bean,data);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        throwable.printStackTrace();
//                        ToastUtil.show(throwable.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    /**
     * 请求网络 删除文件夹或者文件
     */
    public void deleteFolderToNet(final FileDirBean bean){
        if (FastClickUtil.isFastClick()) {
            return;
        }
        NetFolderDelete data = new NetFolderDelete();
        deleteFolderBean(bean,data);
//        ToastUtil.show("删除成功");
//        if (mDisposable!=null && !mDisposable.isDisposed()){
//            mDisposable.dispose();
//        }
//        HashMap<String, String> params = new HashMap<>();
//        params.put("folderId", ""+bean.folderId);
//        params.put("templateId", ""+bean.templateId);
//        mDisposable = netApi.deleteFolderOrFile(NetTrainConfig.DELETE_FOLDER_OR_COURSE, params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<NetFolderDelete>() {
//                    @Override
//                    public void onNext(NetFolderDelete data) {
//                        deleteFolderBean(bean,data);
//                        ToastUtil.show("删除成功");
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        throwable.printStackTrace();
//                        ToastUtil.show(throwable.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    /**
     * 请求网络 重命名文件或文件夹
     */
    public void reNameFolderNet(String content, final FileDirBean bean){
        if (FastClickUtil.isFastClick()) {
            return;
        }
        NetFolderRename data = new NetFolderRename();
        reNameFolderToDao(bean,data);
//        if (mDisposable!=null && !mDisposable.isDisposed()){
//            mDisposable.dispose();
//        }
//        HashMap<String, String> params = new HashMap<>();
//        params.put("folderId", ""+bean.folderId); // 文件夹ID，修改课程名称时，此字段为空或为0
//        params.put("newName", ""+content);
//        params.put("templateId", ""+bean.templateId); // 课程ID，修改文件夹名称时，此字段为空或为0
//        mDisposable = netApi.reName(NetTrainConfig.RENAME_FOLDER, params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<NetFolderRename>() {
//                    @Override
//                    public void onNext(NetFolderRename data) {
//                        reNameFolderToDao(bean,data);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        throwable.printStackTrace();
//                        ToastUtil.show(throwable.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }


    /**
     * 请求网络 移动文件
     */
    private void moveFileToNet(final String toFolderId, final FileDirBean bean){
        if (FastClickUtil.isFastClick()) {
            return;
        }
        NetFolderMove data = new NetFolderMove();
        moveFileToDao(bean,data,toFolderId);

//        if (mDisposable!=null && !mDisposable.isDisposed()){
//            mDisposable.dispose();
//        }
//        HashMap<String, String> params = new HashMap<>();
//        params.put("fromFolderId", ""+ bean.parentFolder);
//        params.put("toFolderId", ""+ toFolderId);
//        params.put("templateId", ""+bean.templateId);
//        mDisposable = netApi.moveFile(NetTrainConfig.MOVE_FOLDER_OR_TPL, params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<NetFolderMove>() {
//                    @Override
//                    public void onNext(NetFolderMove data) {
//                        moveFileToDao(bean,data,toFolderId);
//                        ToastUtil.show("移动成功");
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        throwable.printStackTrace();
//                        ToastUtil.show(throwable.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }



    /**
     * 请求网络 创建副本
     */
    public void copyFileToNet(String tplName, final FileDirBean bean){
        if (FastClickUtil.isFastClick()) {
            return;
        }
        NetFileCopy data = new NetFileCopy();
        copyFileToDao(bean,data);

//        if (mDisposable!=null && !mDisposable.isDisposed()){
//            mDisposable.dispose();
//        }
//        HashMap<String, String> params = new HashMap<>();
//        params.put("templateId", ""+bean.templateId);
//        params.put("templateName", ""+tplName);
//        params.put("parentId", ""+bean.parentFolder);
//        mDisposable = netApi.copyeFile(NetTrainConfig.COPY_TEMPLETE, params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<NetFileCopy>() {
//                    @Override
//                    public void onNext(NetFileCopy data) {
//                        copyFileToDao(bean,data);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        throwable.printStackTrace();
//                        ToastUtil.show(throwable.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }




    private CourseFileDirViewInterface getCourseFileDirViewInterface(String parentFolder){
        if (dirViewVector!=null) {
            for (CourseFileDirViewInterface viewInterface : dirViewVector){
                String folder = viewInterface.getParentFolder();
                if (folder != null){
                    if (folder.equals(parentFolder)){
                        return viewInterface;
                    }
                }
            }
        }
        return null;
    }

    private void modifyParentFolder(CourseFileNetData data, String parentFolder){
        if (data!=null && data.tplData!=null) {
            for (FileDirBean tpl : data.tplData){
                tpl.setParentFolder(parentFolder);
            }
        }

    }


    /**
     * 此处 借鉴于系统 FragmentPagerAdapter 类中生成fragment类的方法
     * @param type
     * @param dirId parentFolder/fileID
     */
    private static String makeFragmentName(/*@IdRes int viewId,*/ CourseFileDirEnum type, String dirId) {
//        return "android:switcher:" + viewId + ":" + dirId;
        return "android:coach"+type.toString()+":dir"+ ":" + dirId;
    }



// ---------------------------- 派课 选择课程 相关 ----------------------------------------

    /**
     * 加载第一个训练教案
     */
    public void showSelectFragment(FragmentManager fragmentManager){
        // 展示 移动页面
        CourseTagBean tagBean = new CourseTagBean();
        tagBean.type = CourseFileDirEnum.SELECT;
//        tagBean.viewId = R.id.fg_select_course;
        tagBean.parentFolder = "0";
        tagBean.addToBackStack = true;
        tagBean.folderName = "桌面";
        tagBean.depth = "0";
        CourseFileDirFragmentControl.getInstance().addNewFragment(fragmentManager,null, tagBean);
    }

    /**
     * 选择课程的返回
     * @param activity
     * @param bean
     */
    public void setSelect(FragmentActivity activity, FileDirBean bean) {
        try {
//            ReservePersonCourseActivity ac = (ReservePersonCourseActivity) activity;
//            if (ac != null){
//                ac.onSelectCourse(bean);
//            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closedAllSelectView();
        }
    }
}
