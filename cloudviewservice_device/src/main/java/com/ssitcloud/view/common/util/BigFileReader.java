package com.ssitcloud.view.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


/**
 * 大文本读取
 *
 * <p>2017年7月24日 上午9:17:37  
 * @author hjc 
 *
 */
public class BigFileReader {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		final int BUFFER_SIZE = 0x1200000;// 缓冲大小为12M

		System.out.println(BUFFER_SIZE);

		File f = new File("C:/Users/Administrator/Desktop/新建文件夹/bookitem.txt");

		int len = 0;
		Long start = System.currentTimeMillis();
		for (int z = 8; z > 0; z--) {
			MappedByteBuffer inputBuffer = new RandomAccessFile(f, "r")
					.getChannel().map(FileChannel.MapMode.READ_ONLY,
							f.length() * (z - 1) / 8, f.length() * 1 / 8);
			byte[] dst = new byte[BUFFER_SIZE];// 每次读出12M的内容
			for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {
				if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {
					for (int i = 0; i < BUFFER_SIZE; i++)
						dst[i] = inputBuffer.get(offset + i);
				} else {
					for (int i = 0; i < inputBuffer.capacity() - offset; i++)
						dst[i] = inputBuffer.get(offset + i);
				}
				int length = (inputBuffer.capacity() % BUFFER_SIZE == 0) ? BUFFER_SIZE
						: inputBuffer.capacity() % BUFFER_SIZE;

				len += new String(dst, 0, length).length();
				System.out.println(new String(dst,"UTF-8"));
				System.out.println(new String(dst, 0, length).length() + "-"
						+ (z - 1) + "-" + (8 - z + 1));
			}
		}
		System.out.println(len);
		long end = System.currentTimeMillis();
		System.out.println("读取文件文件花费：" + (end - start) + "毫秒");
	}

}
