package com.ssitcloud.app_operator.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.ChangePwdBizI;
import com.ssitcloud.app_operator.biz.MessageRemindBizI;
import com.ssitcloud.app_operator.biz.impl.ChangePwdBizImpl;
import com.ssitcloud.app_operator.biz.impl.MessageRemindBizImpl;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.db.entity.MessageRemindDbEntity;
import com.ssitcloud.app_operator.view.viewInterface.ChangePwdViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/3/22 18:54
 * @author shuangjunjie
 */

public class MessageRemindPresenter {

    private Context context;
    private MessageRemindBizI messageRemindBiz;

    public MessageRemindPresenter(Context context) {
        this.context = context.getApplicationContext();
        this.messageRemindBiz = new MessageRemindBizImpl(this.context);
    }

    public List<MessageRemindDbEntity> getMessageRemind(int page, int pageSize){
        return messageRemindBiz.getMessageRemind(page,pageSize);
    }
}
