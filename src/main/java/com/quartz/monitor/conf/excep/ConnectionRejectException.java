package com.quartz.monitor.conf.excep;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/4 17:46
 * @Description:
 */

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/4 17:46
 * @Description:
 */
public class ConnectionRejectException extends RuntimeException {

    public ConnectionRejectException() {
        super();
    }

    public ConnectionRejectException(String message) {
        super(message);
    }

    public ConnectionRejectException(Throwable cause) {
        super(cause);
    }
}
