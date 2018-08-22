package com.ssitcloud.common.netty.server.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 消息组成：前四个字节是消息头，随后四个字节为消息长度（包含消息头、消息长度、消息体），最后八个字节是消息体
 * * BEFORE DECODE (16 bytes)                    AFTER DECODE (16 bytes)
 * +------+--------+------+----------------+      +------+----------------+
 * | Header | Length | Actual Content |     ----->| Actual Content |
 * | @$&%   |  0010  | "HELLO, WORLD" |           | "HELLO, WORLD" |
 * +------+--------+------+----------------+      +------+----------------+
 * @author yyl
 *
 */
public class SyncProtocolDecoder extends ByteToMessageDecoder {
	private final String HEADER = "@$&%";
	private final Pattern lengthPattern = Pattern.compile("[0-9|A-F]{4}");
	private final Charset charset = Charset.forName("GBK");
	private final int maxFrameLength;
	private final int lengthFieldOffset;
	private final int lengthFieldLength;
	private final int lengthFieldEndOffset;
	private final int lengthAdjustment;
	private final int initialBytesToStrip;
	private boolean discardingTooLongFrame;
	private long bytesToDiscard;

	public SyncProtocolDecoder(ByteOrder byteOrder, int maxFrameLength,
			int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
			int initialBytesToStrip) {
		if (byteOrder == null) {
			throw new NullPointerException("byteOrder");
		}

		if (maxFrameLength <= 0) {
			throw new IllegalArgumentException(
					"maxFrameLength must be a positive integer: "
							+ maxFrameLength);
		}

		if (lengthFieldOffset < 0) {
			throw new IllegalArgumentException(
					"lengthFieldOffset must be a non-negative integer: "
							+ lengthFieldOffset);
		}

		if (initialBytesToStrip < 0) {
			throw new IllegalArgumentException(
					"initialBytesToStrip must be a non-negative integer: "
							+ initialBytesToStrip);
		}

		if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
			throw new IllegalArgumentException("maxFrameLength ("
					+ maxFrameLength + ") "
					+ "must be equal to or greater than "
					+ "lengthFieldOffset (" + lengthFieldOffset + ") + "
					+ "lengthFieldLength (" + lengthFieldLength + ").");
		}

		this.maxFrameLength = maxFrameLength;
		this.lengthFieldOffset = lengthFieldOffset;
		this.lengthFieldLength = lengthFieldLength;
		this.lengthAdjustment = lengthAdjustment;
		lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
		this.initialBytesToStrip = initialBytesToStrip;
	}

	public SyncProtocolDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
		this(ByteOrder.BIG_ENDIAN, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
	}

	@Override
	protected final void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		Object decoded = decode(ctx, in);
		if (decoded != null) {
			out.add(decoded);
		}

	}

	/**
	 * 解码
	 * @param ctx
	 * @param in
	 * @return
	 * @throws Exception
	 */
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		
		System.out.println("解码前报文：" + in.toString(charset));
		
		if (discardingTooLongFrame) {
			long bytesToDiscard = this.bytesToDiscard;
			int localBytesToDiscard = (int) Math.min(bytesToDiscard,
					in.readableBytes());
			in.skipBytes(localBytesToDiscard);
			bytesToDiscard -= localBytesToDiscard;
			this.bytesToDiscard = bytesToDiscard;
		}
		
		if (in.readableBytes() < 8) {
			return null;
		}

		if (in.readableBytes() < lengthFieldEndOffset) {
			return null;
		}
		int actualLengthFieldOffset = in.readerIndex() + lengthFieldOffset;

		long frameLength = getUnadjustedFrameLength(ctx, in,
				actualLengthFieldOffset, lengthFieldLength);

		if (frameLength < 0) {
			in.skipBytes(in.readableBytes()+actualLengthFieldOffset);
			System.out.println("negative pre-adjustment length field: " + frameLength);
			return null;
		}

		frameLength += lengthAdjustment + lengthFieldEndOffset;

		if (frameLength < lengthFieldEndOffset) {
			in.skipBytes(in.readableBytes());
			
			System.out.println("Adjusted frame length ("
					+ frameLength + ") is less "
					+ "than lengthFieldEndOffset: " + lengthFieldEndOffset);
			return null;
		}

		if (frameLength > maxFrameLength) {
			long discard = frameLength - in.readableBytes();
			if (discard < 0) {
				// buffer contains more bytes then the frameLength so we can
				// discard all now
				in.skipBytes((int) frameLength);
			} else {
				// Enter the discard mode and discard everything received so
				// far.
				discardingTooLongFrame = true;
				bytesToDiscard = discard;
				in.skipBytes(in.readableBytes());
			}
			return null;
		}

		// never overflows because it's less than maxFrameLength
		int frameLengthInt = (int) frameLength;
		if (in.readableBytes() <frameLengthInt) {
			System.out.println("------可读字节长度: " +  in.readableBytes() + "------,报文长度" + frameLengthInt );
			in.readerIndex(in.readerIndex());
			return null;
		}

		if (initialBytesToStrip > frameLengthInt) {
			in.skipBytes(frameLengthInt);
			System.out.println("Adjusted frame length ("
					+ frameLength + ") is less " + "than initialBytesToStrip: "
					+ initialBytesToStrip);
			return null;
		}
		//in.skipBytes(initialBytesToStrip);

		// extract frame
		int readerIndex = in.readerIndex();
		int actualFrameLength = frameLengthInt - initialBytesToStrip;
		ByteBuf frame = extractFrame(ctx, in, readerIndex+initialBytesToStrip, actualFrameLength);
		in.readerIndex(in.writerIndex());
		System.out.println("解码后的报文：" + frame.toString(charset));
		return frame;
	}

	/**
	 * 调整消息体读取位置
	 * @param ctx
	 * @param in
	 * @param actualLengthFieldOffset
	 * @param lengthFieldLength2
	 * @return
	 */
	private long getUnadjustedFrameLength(ChannelHandlerContext ctx,
			ByteBuf in, int actualLengthFieldOffset, int lengthFieldLength2) {
		ByteBuf lengthBuf = extractFrame(ctx, in, actualLengthFieldOffset,
				lengthFieldLength);
		String bodyLength = lengthBuf.toString(charset);
		
		if(null == bodyLength || "".equals(bodyLength)){
			return -1L;
		}
		
		Matcher matcher = lengthPattern.matcher(bodyLength);
		//不是16进制
		if(! matcher.matches()){
			return -1L;
		}
		return HexUtil.hexString2long(bodyLength);
	}

	/**
	 * 封装消息体
	 * @param ctx
	 * @param buffer
	 * @param index
	 * @param length
	 * @return
	 */
	protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer,
			int index, int length) {
		ByteBuf frame = ctx.alloc().buffer(length);
		frame.writeBytes(buffer, index, length);
		return frame;
	}


}
