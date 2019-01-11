package com.dejun.commonsdk.db;

import android.content.Context;
import com.dejun.commonsdk.db.model.PersonRes;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/12/12 18:12
 * Description:
 * email:1348172474@qq.com
 */

public class GreenDaoClient {
    private DaoManager daoManager;

    public GreenDaoClient(Context context) {
        daoManager = DaoManager.getInstance();
        daoManager.init(context);
    }
    /**
     * 插入数据，如果表未创建，先创建表
     */
    public boolean insert(PersonRes personRes){
        boolean flag=false;
        flag=daoManager.getDaoSession().getPersonResDao().insert(personRes)==-1?false:true;
        Logger.d("insert:"+personRes);
        return flag;
    }
    /**
     * 插入多条数据
     */
    public void insertList(final List<PersonRes> personResList){
        daoManager.getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (PersonRes personRes:personResList ){
                    daoManager.getDaoSession().insertOrReplace(personRes);
                }
            }
        });
    }
    /**
     * 修改一条数据
     */
    public boolean update(PersonRes personRes){
        boolean flag=false;
        try {
            daoManager.getDaoSession().update(personRes);
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 删除单条数据
     */
    public boolean delete(PersonRes personRes){
        boolean flag=false;
        try {
            daoManager.getDaoSession().delete(personRes);
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 删除所有数据
     */
    public boolean deleteAll(){
        boolean flag=false;
        try {
            daoManager.getDaoSession().deleteAll(PersonRes.class);
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 查询所有记录
     */
    public List<PersonRes> queryAll(){
        return daoManager.getDaoSession().loadAll(PersonRes.class);
    }
}
