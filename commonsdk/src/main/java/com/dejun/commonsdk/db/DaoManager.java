package com.dejun.commonsdk.db;

import android.content.Context;

import com.dejun.commonsdk.gen.DaoMaster;
import com.dejun.commonsdk.gen.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Author:DoctorWei
 * Time:2018/12/12 16:48
 * Description:
 * email:1348172474@qq.com
 */

public class DaoManager {
    public static  final String DB_NAME="android_course";
    public static DaoManager daoManager=new DaoManager();
    private static Context mContext;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    public static DaoManager getInstance(){
        return daoManager;
    }
    public static void init(Context context){
        mContext=context;
    }
    /**
     * 判断是否存在数据库，如果没有则创建
     */
    public DaoMaster getDaoMaster(){
        if (mDaoMaster==null){
            mDevOpenHelper=new DaoMaster.DevOpenHelper(mContext,DB_NAME,null);
            mDaoMaster=new DaoMaster(mDevOpenHelper.getWritableDatabase());
        }
        return mDaoMaster;
    }
    /**
     * 完成对数据库的添加，删除，修改，查询操作，仅仅是一个接口
     */
    public DaoSession getDaoSession(){
        if (mDaoSession==null){
            if (mDaoMaster==null){
                mDaoMaster=getDaoMaster();
            }
            mDaoSession=mDaoMaster.newSession();
        }
        return mDaoSession;
    }
    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug(){
        QueryBuilder.LOG_SQL=true;
        QueryBuilder.LOG_VALUES=true;
    }
    /**
     * 关闭所有操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }
    public void closeHelper(){
        if (mDevOpenHelper!=null){
            mDevOpenHelper.close();
            mDevOpenHelper=null;
        }
    }
    public void closeDaoSession(){
        if (mDaoSession!=null){
            mDaoSession.clear();
            mDaoSession=null;
        }
    }
}
