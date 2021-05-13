package com.anlohse.minesweeper.commons.util;

public class IOUtils {

	public static long bytesToLong(byte[] bs) {
		long r = bs[7];
		for (int i = 6; i >= 0; i--)
			r = (r << 8) | (bs[i] & 0xff);
		return r;
	}
	
	public static byte[] longToBytes(long l) {
		byte[] bs = new byte[8];
		for (int i = 0; i < 8; i++) {
			bs[i] = (byte) (l & 0xff);
			l = l >> 8;
		}
		return bs;
	}
	
}
