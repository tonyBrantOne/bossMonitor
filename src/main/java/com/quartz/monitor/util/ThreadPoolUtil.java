package com.quartz.monitor.util;


import com.quartz.monitor.model.MonitorDTO;

import java.util.concurrent.*;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/10 20:39
 * @Description:
 */
public class ThreadPoolUtil<T> {
    public static ExecutorService executorService = new ThreadPoolExecutor(12, 20, 1000,TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(50));


    public static <T>FutureTask<T> execute( Object object, String methodName,  Object param ,Class<T> clazz) throws Exception {
        FutureTask<T> futureTask = new FutureTask(new ThreadPoolUtil().new CommonCallable(object,methodName,param));
        executorService.submit(futureTask);
        return futureTask;
    }

    public class CommonCallable implements Callable<T> {

        Object target;
        String methodName;
        Object param;

        public CommonCallable(Object object, String methodName, Object param) {
            this.target = object;
            this.methodName = methodName;
            this.param = param;
        }

        @Override
        public T call() throws Exception {
            System.out.println("正在运行异步任务");
            System.out.println(target.getClass()+" " + methodName +"  "+ param.getClass());
            Object object = ReflectUtil.invoke(target, methodName, param);
            T t = (T) object;
            System.out.println("运行结束");
            return t;
        }

    }
}
