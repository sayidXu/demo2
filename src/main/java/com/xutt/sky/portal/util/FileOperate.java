package com.xutt.sky.portal.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.xutt.sky.portal.common.exception.BusinessException;

public class FileOperate {

	private String basePath = this.getClass().getResource("/").getPath();

	public String getBasePath() {
		return basePath;
	}

	public void procInputStreamToResponse(String filePath, String fileName, HttpServletResponse response)
			throws Exception {
		File file = new File(filePath);
		if (!file.exists() || file.isDirectory()) {
			throw new BusinessException("文件不存在！");
		}
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String((fileName).getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw new BusinessException("文件不存在！");
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	/**
	 * 
	 * @param files
	 *            批量上传文件到临时目录
	 * @param compName
	 *            uiDesign/(<b>compName</b>)/文件名
	 * @return 临时保存路径
	 * @throws Exception
	 */
	public List<String> uploadFiles(List<MultipartFile> files) throws Exception {
		List<String> list = new ArrayList<String>();
		for (MultipartFile file : files) {
			// 临时保存路径
			String tempPath = "uiDesign" + File.separator + "temp" + File.separator + file.getOriginalFilename();
			File tempFile = new File(basePath + tempPath);
			tempFile.getParentFile().mkdirs();
			// 转存文件
			file.transferTo(tempFile);
			list.add(tempPath);
		}
		return list;
	}

}
