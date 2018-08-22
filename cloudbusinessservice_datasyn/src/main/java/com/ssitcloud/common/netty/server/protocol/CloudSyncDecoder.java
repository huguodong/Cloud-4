package com.ssitcloud.common.netty.server.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * RPC Decoder
 * 
 * @author yeyalin
 */
public class CloudSyncDecoder extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg,List<Object> out) throws Exception {

		String split[] = msg.split("\\@\\$\\&\\%");

		String message = "";

		for (int i = 0; i < split.length; i++) {
			if (TextUtils.isEmpty(split[i]))
				continue;
			if (5 > split[i].length())
				break;
			message = split[i].substring(4);
		}

		out.add(message);

	}

}
