package com.ssitcloud.common.netty.manage;

import io.netty.channel.Channel;

import com.ssitcloud.datasync.service.impl.DeviceRegister;

/**
 * Created by yeyalin on 2018-04-03.
 */
public class ConnectManage {
	
    private volatile static ConnectManage connectManage;

    private ConnectManage() {
    }

    public static ConnectManage getInstance() {
        if (connectManage == null) {
            synchronized (ConnectManage.class) {
                if (connectManage == null) {
                    connectManage = new ConnectManage();
                }
            }
        }
        return connectManage;
    }


    public Channel chooseChannel(String cliendId) {
    	Channel channel = (Channel)DeviceRegister.handlerMap.get(cliendId);
        return channel;
    }
}
