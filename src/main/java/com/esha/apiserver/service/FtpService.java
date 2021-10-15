package com.esha.apiserver.service;

import com.esha.apiserver.util.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FtpService {

    /**
     * 本地上傳檔案到FTP伺服器
     *
     * @param ftpPath 遠端檔案路徑FTP
     * @throws IOException
     */
    public void upload(String ftpPath) {
        FTPClient ftpClient = null;
        System.out.println("開始上傳檔案到FTP.");
        try {
            ftpClient = FtpUtil.getFTPClient("10.10.10.99", "ftpadmin00", "CheFR63r", 21);
            // 設定PassiveMode傳輸
            ftpClient.enterLocalPassiveMode();
            // 設定以二進位制流的方式傳輸
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 對遠端目錄的處理
            String remoteFileName = ftpPath;

            if (ftpPath.contains("/")) {
                remoteFileName = ftpPath.substring(ftpPath.lastIndexOf("/") + 1);
            }

            File f = new File(ftpPath);
            InputStream in = new FileInputStream(f);
            ftpClient.storeFile(remoteFileName, in);
            in.close();
            System.out.println("上傳檔案" + remoteFileName + "到FTP成功!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
