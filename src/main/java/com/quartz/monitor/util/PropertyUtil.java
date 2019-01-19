package com.quartz.monitor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/19 12:46
 * @Description:
 */
public class PropertyUtil {


    /**
     * 读取classPath路径下的配置文件,并存入缓存中
     */
    public static void readClassPathPropFileToMap(String PATH, Map< String,Properties > resultMap) throws Exception {
        URL url = ConfigUtil.class.getClassLoader().getResource(PATH);
        String packagesUrl = url.getFile();
        File scanFile = new File(packagesUrl);
        if( scanFile.isDirectory() ){
            File[] files = scanFile.listFiles();
            for( File file : files ){
                String key = file.getName().replaceAll(".properties","");
                Properties properties = prasePropByFile(file);
                resultMap.put(key,properties);
            }
        }
    }


    /**
     *
     * @param file 把文件转化为property对象
     * @return
     * @throws Exception
     */
    private static Properties prasePropByFile( File file ) throws Exception {
        Properties props = new Properties();
        InputStream inputStream = new FileInputStream(file);
        props.load(inputStream);
        return props;
    }

}
