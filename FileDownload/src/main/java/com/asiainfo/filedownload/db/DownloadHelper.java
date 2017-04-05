package com.asiainfo.filedownload.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import de.greenrobot.dao.Property;
import de.greenrobot.dao.query.WhereCondition;

import java.util.List;

/**
 * Created by MicroKibaco on 4/5/17.
 */

public class DownloadHelper {

    private static DownloadHelper mDownloadHelper = new DownloadHelper();
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DownloadEntityDao mDownloadEntityDao;

    public DownloadHelper() {
    }

    public static DownloadHelper getInstance() {

        return mDownloadHelper;

    }

    public void init(Context context) {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "download.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        mDownloadEntityDao = mDaoSession.getDownloadEntityDao();


    }

    public void insert(DownloadEntity entity) {

        mDownloadEntityDao.insert(entity);

    }

    public List<DownloadEntity> getAll(String url) {

        Property mThreadId = DownloadEntityDao.Properties.Thread_id;
        WhereCondition mEq = DownloadEntityDao.Properties.Download_url.eq(url);
        return mDownloadEntityDao.queryBuilder().where(mEq).orderAsc(mThreadId).list();

    }
}
