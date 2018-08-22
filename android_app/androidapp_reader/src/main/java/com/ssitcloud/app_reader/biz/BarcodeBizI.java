package com.ssitcloud.app_reader.biz;

import android.graphics.Bitmap;

import com.ssitcloud.app_reader.common.exception.NonExistentPublicKey;
import com.ssitcloud.app_reader.common.exception.UnLoginException;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;

import java.util.List;

/**
 * Created by LXP on 2017/4/14.
 * 二维码业务
 */

public interface BarcodeBizI {
    /**
     * 生成读者认证二维码
     * @param dbe 需要在外部保证lib_id != null && cardPwd != null
     */
    Bitmap createAuthBarcode(ReaderCardDbEntity dbe) throws UnLoginException,NonExistentPublicKey;

    /**
     * 生成借书二维码
     * @param dbe 需要在外部保证lib_id != null && cardPwd != null
     */
    Bitmap createBorrowBarcode(ReaderCardDbEntity dbe,List<String> bookBarcodes) throws UnLoginException,NonExistentPublicKey;

}
