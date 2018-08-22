package com.ssitcloud.common.netty.server.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * RPC Encoder
 * @author yeyalin
 */
public class CloudSyncEncoder extends MessageToMessageEncoder<Object> {

    /**
     * 报文格式处理
     * @param command
     * @return
     */
    public  String createFinalInstruction(String command)
    {
        int length;
        try
        {
            length = "@$&%".length() + command.getBytes("GBK").length;
        }
        catch(Exception ee)
        {
            return ee.getMessage();
        }
        String tmp_len = HexUtil.int2HexString(length + 4, 4).toUpperCase();
        StringBuilder sb = new StringBuilder();
        sb.append("@$&%");
        sb.append(tmp_len);
        sb.append(command);
        System.out.println("encode message: " + sb.toString());
        return sb.toString();
    }
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Object in,
			List<Object> out) throws Exception {
		String command = JsonUtil.objectToJson(in);
		String message=createFinalInstruction(command);
		out.add(message);
		
	}
}

