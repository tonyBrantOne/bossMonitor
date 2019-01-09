package com.quartz.monitor.util;
import java.io.*;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/9 17:14
 * @Description:
 */
public class SerializeUtil {


    public static void serializeStream(OutputStream outputStream,Object object ) throws Exception {
                 ObjectOutputStream oo = new ObjectOutputStream(outputStream);
                 oo.writeObject(object);
                 oo.close();
    }



    public static void deserializeStream( InputStream inputStream  ) throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Object object = objectInputStream.readObject();
    }




}
