package com.esha.apiserver.util;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

    /**
     * 獲取FTPClient物件
     *
     * @param ftpHost     FTP主機伺服器
     * @param ftpPassword FTP 登入密碼
     * @param ftpUserName FTP登入使用者名稱
     * @param ftpPort     FTP埠 預設為21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 連線FTP伺服器
            ftpClient.login(ftpUserName, ftpPassword);// 登入FTP伺服器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                System.out.println("未連線到FTP，使用者名稱或密碼錯誤。");
                ftpClient.disconnect();
            } else {
                System.out.println("FTP連線成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("FTP的IP地址可能錯誤，請正確設定。");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FTP的埠錯誤,請正確設定。");
        }
        return ftpClient;
    }

}
