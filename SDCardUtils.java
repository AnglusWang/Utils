package com.angluswang.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
/** 
 * SDCard工具类
 *
 */
public class SDCardUtils {
	/**
	 * 判断SD卡是否挂载
	 * 
	 * @return
	 */
	public static boolean isMounted() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	} 

	// 得到SD卡的根路径
	public static String getSDPath() {

		if (isMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}

	// 将文件保存到SD卡中
	public static boolean saveFileIntoSDCard(byte[] data, String path,
			String fileName) {

		if (isMounted()) {

			BufferedOutputStream bos = null;
			try {
				String filePath = getSDPath() + File.separator + path;
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}

				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, fileName)));
				bos.write(data, 0, data.length);
				bos.flush();

				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		}

		return false;
	}

	// 从SD卡中取出存储的文件
	public static byte[] getFileFromSDCard(String filePath) {

		if (isMounted()) {
			File file = new File(filePath);
			BufferedInputStream bis = null;
			ByteArrayOutputStream baos = null;
			if (file.exists()) {
				try {
					baos = new ByteArrayOutputStream();
					bis = new BufferedInputStream(new FileInputStream(file));
					int len = 0;
					byte[] buffer = new byte[1024 * 8];
					while ((len = bis.read(buffer)) != -1) {
						baos.write(buffer, 0, len);
						baos.flush();
					}

					return baos.toByteArray();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
							baos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
		}

		return null;

	}
}
