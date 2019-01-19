package com.quartz.monitor.util.mail;

import com.quartz.monitor.conf.ConstantKey;
import com.quartz.monitor.conf.ConstantParam;
import com.quartz.monitor.conf.enums.StatusTypeEnum;
import com.quartz.monitor.model.AbstractMonitorDTO;
import com.quartz.monitor.model.DefaultMonitorType;
import com.quartz.monitor.orm.mybatis.util.ReflectMapperUtil;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * @Auther: tony_jaa
 * @Date: 2019/1/18 16:55
 * @Description:
 */

@Component
public class MailTemplate {
    private static Logger logger = LogManager.getLogger( MailTemplate.class );

    private Properties prop;
    private volatile Session session;
    private MimeMessage mm;
    private Transport ts;

    private boolean isDebug;
    private String fromAccount ;
    private String fromPassword;
    private String toAccount;
    private String protocl;

    public void sendMailMsg( AbstractMonitorDTO abstractMonitorDTO ) throws Exception {
        logger.warn("进入发送邮件的模板方法中");

        synchronized ( this ) {
            Message msg = createSimpleMail(abstractMonitorDTO);

            logger.warn("邮件标题及内容封装完毕，该邮件服务为"+abstractMonitorDTO.getServerName());
            try {
                ts.sendMessage(msg, msg.getAllRecipients());
            }catch ( Exception e ){
                logger.error("该服务的邮件发送失败,失败原因："+e);
            }
            logger.warn("邮件内容发送完毕");
        }

    }


    public   Session getSessionInstance() throws Exception {
        /**
         * 1.创建sesssion
         */
        if( session == null ){
            synchronized ( MailTemplate.class ){
                if( session == null ){
//                    prop = CACHE_PROP.get(ConstantKey.MAIL_CONF);
//                    prop.put("mail.smtp.auth",true);
                    resetCleartProp();
                    session=Session.getInstance(prop);
                    /**
                     * 2开启session的调试模式，可以查看当前邮件发送状态
                     */
                    session.setDebug( isDebug );

                    initMimeMessage(session);

                    initTransport(session);
                }
            }
        }

        return session;
    }

    private void resetCleartProp(){
        prop = new Properties();
        prop.put("mail.host",ConstantParam.CACHE_PROP.get(ConstantKey.MAIL_CONF).getProperty("mail.host") );
        prop.put("mail.transport.protocol", ConstantParam.CACHE_PROP.get(ConstantKey.MAIL_CONF).getProperty("mail.transport.protocol"));
        prop.put("mail.smtp.auth", true);
        isDebug = true;
        fromAccount = ConstantParam.CACHE_PROP.get(ConstantKey.MAIL_CONF).getProperty("fromAccount");
        fromPassword = ConstantParam.CACHE_PROP.get(ConstantKey.MAIL_CONF).getProperty("fromPassword");
        toAccount = ConstantParam.CACHE_PROP.get(ConstantKey.MAIL_CONF).getProperty("toAccount");
        protocl = ConstantParam.CACHE_PROP.get(ConstantKey.MAIL_CONF).getProperty("protocl");
    }

    private void initMimeMessage( Session session ) throws Exception {
        /**
         *  1:创建邮件对象
         */
        mm=new MimeMessage(session);
        /**
         * 2.设置发件人
         */
        mm.setFrom(new InternetAddress( fromAccount ));
        /*
         * 3.设置发件人
         */
        mm.setRecipient(Message.RecipientType.TO, new InternetAddress(toAccount));
    }



    private void initTransport( Session session ) throws Exception {
        /**
         *  2.通过session获取Transport对象（发送邮件的核心API）
         */
        ts=session.getTransport();
        /**
         *  3.通过邮件用户名密码链接
         */
        ts.connect(fromAccount, fromPassword);
    }





    private MimeMessage createSimpleMail( AbstractMonitorDTO abstractMonitorDTO ) throws Exception {

        String statusTypeName = StatusTypeEnum.getNameByCode(abstractMonitorDTO.getStatus());//预警状态
        Object  type  = ReflectMapperUtil.invokeGetField(abstractMonitorDTO,"monitorType");
        DefaultMonitorType defaultMonitorType = (DefaultMonitorType) type;

        mm.setSubject( new StringBuilder(defaultMonitorType.getName()).append(statusTypeName).toString());
        /**
         * 6.设置内容
         */
        mm.setContent(new StringBuilder("服务连接点为：").append(abstractMonitorDTO.getServerName()).append("，异常内容为：").append(abstractMonitorDTO.getContent()).toString(), protocl);

        return mm;
    }


}
