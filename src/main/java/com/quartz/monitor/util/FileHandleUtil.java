package com.quartz.monitor.util;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;

/**
 * @Auther: tony_jaa
 * @Date: 2018/8/17 19:28
 * @Description:
 */
public class FileHandleUtil {
    private static org.apache.logging.log4j.Logger LOG = LogManager.getLogger(FileHandleUtil.class);



    /**
     * 远程替换本地文件
     * @param
     * @param path2
     * @throws Exception
     */
    public static void copyRemoteToLocalFile( String path2,   InputStream inputStream ) throws Exception {
    //    InputStream inputStream =null;
        OutputStream outputStream = null;
        try {
        //    inputStream = connection.getInputStream();
            outputStream = new FileOutputStream( path2 );
            replaceLocalFile(inputStream,outputStream);
        }
        finally {
            outputStream.close();
        }
    }


    /**
     * 本地替换本地文件
     * @param path1
     * @param path2
     * @throws Exception
     */
    public static void copyLocalToLocalFile(String path1, String path2) throws Exception {
        InputStream inputStream =null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(path1);
            outputStream = new FileOutputStream(path2);
            replaceLocalFile(inputStream,outputStream);
        }
        finally {
            inputStream.close();
            outputStream.close();
        }
    }

    /**
     * 从文件中读取字符串。
     * @param path
     * @return
     * @throws Exception
     */
    public static String bufferedStrRead( String path ) throws Exception {
        BufferedReader bReader = null;
        try {
            File file = new File(path);//定义一个file对象，用来初始化FileReader
            FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
            bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
            StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
            String s = "";
            while ( ( s = bReader.readLine()) != null ) {//逐行读取文件内容，不读取换行符和末尾的空格
                sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            }
            return sb.toString();
        }finally {
            if( bReader != null ) bReader.close();
        }
    }


    /**
     * 将字符串写入文件中
     * @param str
     * @param filePath
     * @throws Exception
     */
    public static void bufferedStrWriter(String str,String filePath) throws Exception{
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            File file = new File(filePath);
            if( !file.exists() ) file.createNewFile();
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(str);
        }catch ( Exception e ){
            e.printStackTrace();
            throw e;
        }finally{
            if( bw != null ) bw.close();
            if( fw != null ) fw.close();
        }
    }



    /**
     * 输入流数据传输到输出流
     * @param inputStream
     * @param outputStream
     * @throws Exception
     */
    private static void replaceLocalFile(InputStream inputStream, OutputStream outputStream) throws Exception{
        byte[] buf = new byte[1024];
        int bytesRead;
        while ( ( bytesRead = inputStream.read(buf)) != -1){
            outputStream.write(buf,0,bytesRead);
        }
        LOG.info("流数据转换完成");
    }


    /**
     * 获取文件的MD5的值
     * @param
     * @return
     * @throws Exception
     */
    public static String getFileMD5ByInputStrem( InputStream inputStream ) throws Exception {
        MessageDigest digest = null;
     //   InputStream inputStream =null;
        try {
            digest = MessageDigest.getInstance("MD5");
      //      inputStream = new FileInputStream(path1);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ( ( bytesRead = inputStream.read(buf)) != -1){
                digest.update(buf, 0, bytesRead);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            String bigStr = bigInt.toString();
       //     System.out.println("文件MD5的值为" + bigStr);
            return bigStr;
        }
        finally {
        //    inputStream.close();
        }
    }


    /**
     * 判断文件夹是否存在并创建文件
     * @param path
     */
    public static void judgeFoldExist( String path ) {
        File file = new File(path);
        if( !file.isDirectory() ){
            file.mkdir();
        }
    }


    /**
     * 判断文件夹是否存在并创建文件
     * @param path
     */
    public static Boolean judgeFileisExist( String path ) {
        File file = new File(path);
        if( !file.exists() ){
            return false;
        }
        return true;
    }

    public static void deleteFile( String path ) {
        File file = new File(path);
        if( file.exists() ){
            file.delete();
        }
    }

    public static void deleteFoldAllFile( String path ){
        File file = new File(path);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        for(File f : fs){
            f.delete();
        }
    }


    public static String getClassPathUrl( String fileUrl ){
        String filePath = FileHandleUtil.class.getClassLoader().getResource("/").toString() + fileUrl;
        filePath = filePath.replaceAll("file:/","");
        return filePath;
    }



}
