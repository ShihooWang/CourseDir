package shihoo.wang.coursedir;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import shihoo.wang.coursedir.dao.DaoMaster;
import shihoo.wang.coursedir.dao.DaoSession;

/**
 * Created by shihoo.wang on 2018/11/7.
 * Email shihu.wang@bodyplus.cc
 */

public class App extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        //配置数据库
        setupDatabase();
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "file.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
