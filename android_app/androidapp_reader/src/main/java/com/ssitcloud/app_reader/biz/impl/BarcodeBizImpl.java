package com.ssitcloud.app_reader.biz.impl;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.zxing.WriterException;
import com.ssitcloud.app_reader.biz.BarcodeBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.SecurityBizI;
import com.ssitcloud.app_reader.common.exception.NonExistentPublicKey;
import com.ssitcloud.app_reader.common.exception.UnLoginException;
import com.ssitcloud.app_reader.common.utils.BarcodeUtil;
import com.ssitcloud.app_reader.common.utils.Base64Helper;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.PublicKeyEntity;

import java.util.List;

/**
 * Created by LXP on 2017/4/14.
 *
 */

public class BarcodeBizImpl implements BarcodeBizI {
    private Context mcontext;
    private SecurityBizI SecurityBiz;
    private LoginBizI loginBiz;

    public BarcodeBizImpl(Context context){
        mcontext = context.getApplicationContext();
        SecurityBiz = new SecurityBizImpl(mcontext);
        loginBiz = new LoginBizImpl(mcontext.getResources(),mcontext);
    }

    @Override
    public Bitmap createAuthBarcode(ReaderCardDbEntity dbe) throws UnLoginException,NonExistentPublicKey {

        LoginInfoDbEntity loginData = loginBiz.getLoginReturnData();
        if(loginData != null){
            PublicKeyEntity puk = SecurityBiz.getPuk();
            String barcodeStr = getAuthStr(puk.getKey_version(),dbe);
            try {
                return BarcodeUtil.createTwoDCode(barcodeStr,300,300);
            } catch (WriterException e) {
                return null;
            }
        }
        throw new UnLoginException();
    }

    @Override
    public Bitmap createBorrowBarcode(ReaderCardDbEntity dbe,List<String> bookBarcodes)  throws UnLoginException,NonExistentPublicKey{
        if(bookBarcodes == null){
            throw new IllegalArgumentException("bookBarcodes is null");
        }

        LoginInfoDbEntity loginData = loginBiz.getLoginReturnData();
        if(loginData != null){
            PublicKeyEntity puk = SecurityBiz.getPuk();
            String barcodeStr = getBorrowStr(puk.getKey_version(),dbe,bookBarcodes);
            try {
                return BarcodeUtil.createTwoDCode(barcodeStr,300,300);
            } catch (WriterException e) {
                return null;
            }
        }

        throw new UnLoginException();
    }

    private String getAuthStr(String pukVersion, ReaderCardDbEntity dbe){
        String os = "2";

        long unixTime = System.currentTimeMillis();
        String timeAndPwd = unixTime+"|"+dbe.getCardPwd();
        String ming = StringUtils.getMd5(timeAndPwd,true,false)+"|"+timeAndPwd;

        String mi = Base64Helper.encode(SecurityBiz.encryptData(ming.getBytes()));

        return os+"|"+pukVersion+"|"+dbe.getLib_id()+"|"+dbe.getCard_no()+"||"+mi;
    }


    private String getBorrowStr(String pukVersion, ReaderCardDbEntity dbe,List<String> bookBarcodes){
        String os = "2";

        long unixTime = System.currentTimeMillis();
        String timeAndPwd = unixTime+"|"+dbe.getCardPwd();
        String ming = StringUtils.getMd5(timeAndPwd,true,false)+"|"+timeAndPwd;

        String mi = Base64Helper.encode(SecurityBiz.encryptData(ming.getBytes()));

        StringBuilder borrowBarcode = new StringBuilder(128);
        for (int i = 0,z=bookBarcodes.size(); i < z; ++i) {
            String s = bookBarcodes.get(i);
            borrowBarcode.append(s);
            if(i<z-1){
                borrowBarcode.append(",");
            }
        }

        return os+"|"+pukVersion+"|"+dbe.getLib_id()+"|"+dbe.getCard_no()+"|"+borrowBarcode.toString()+"|"+mi;
    }
}
