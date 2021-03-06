package com.quartz.monitor.publisher;

import com.quartz.monitor.conf.DataSourcesAll;
import com.quartz.monitor.conf.anno.Manualwired;
import com.quartz.monitor.conf.enums.MsgChildrenTypeEnum;
import com.quartz.monitor.conf.enums.StatusTypeEnum;
import com.quartz.monitor.conf.enums.WarnTypeEnum;
import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.dao.PostgresqlDao;
import com.quartz.monitor.handle.PostgresqlWatchHandle;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import com.quartz.monitor.util.ProxyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * title:
 * create by tony_jaa
 * date 2018/12/28
 */
@Component
public class QuartzPostgresqlMonitor extends AbstractQuartzMonitor<PostgresqlMonitorDTO> {
    private static Logger LOG = LogManager.getLogger( QuartzPostgresqlMonitor.class );
    private static final double CON_THRESHOLD = 0.8;
    private final String CHARGE_WARN = "连接池超负荷";

    @Manualwired
    private PostgresqlDao postgresqlDao;

    private PostgresqlWatchHandle<PostgresqlMonitorDTO> postgresqlWatchHandle;

    public PostgresqlWatchHandle<PostgresqlMonitorDTO> getPostgresqlWatchHandle() {
        return postgresqlWatchHandle;
    }

    public void setPostgresqlWatchHandle(PostgresqlWatchHandle<PostgresqlMonitorDTO> postgresqlWatchHandle) {
        this.postgresqlWatchHandle = ProxyUtil.getInstance(PostgresqlWatchHandle.class,postgresqlWatchHandle);
    }

    public void setPostgresqlDao(PostgresqlDao postgresqlDao) {
        this.postgresqlDao = postgresqlDao;
    }

    @Override
    protected void checkHeartJump() throws Exception {
        LOG.info("检查postgresql心跳");
        LOG.info("postgresql要检测的数据源为："+ DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST);
        List<PostgresqlMonitorDTO> list = new ArrayList<>(DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST.size());
        for (int i = 0; i < DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST.size(); i++) {
            PostgresqlMonitorDTO postgresqlMonitorDTO = new PostgresqlMonitorDTO();
            postgresqlMonitorDTO.setDataSources(DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST.get(i));
            list.add(postgresqlMonitorDTO);
        }
        judgeExcepType(list);
    }

    @Override
    public PostgresqlMonitorDTO judgeExcepType(PostgresqlMonitorDTO postgresqlMonitorDTO) throws Exception{
        LOG.info("开始判断postgresql异常类型");
        try {
            Map<String, Object> resultMap = postgresqlDao.selectCurrentConnections(postgresqlMonitorDTO);
       //     LOG.info("返回的resultMap值为："+resultMap);
            Map<String, Object> max_connection_map = postgresqlDao.selectMaxConnections(postgresqlMonitorDTO);
     //       LOG.warn("返回的resultMap值为："+resultMap + ",最大连接对象返回值："+max_connection_map);
            Integer current_connections = Integer.valueOf(resultMap.get("current_connections").toString());
            Integer max_connections = Integer.valueOf(max_connection_map.get("max_connections").toString());

            postgresqlMonitorDTO.getInfo().setCurrentConnectNum( current_connections );
            postgresqlMonitorDTO.getInfo().setMaxConnectNum( max_connections );
            BigDecimal conPercent = new BigDecimal(current_connections).divide( new BigDecimal(max_connections));
            LOG.warn("当前连接池的使用量为："+conPercent );
            postgresqlMonitorDTO.getInfo().setChargePercent(conPercent.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP).toString() + "%");
            if( conPercent.compareTo( new BigDecimal(CON_THRESHOLD)) > 0 ) {
                LOG.warn("当前连接池的使用超负荷" );
                postgresqlMonitorDTO.setStatus(StatusTypeEnum.WARN_TYPE.getMsgCode());
                super.assableStatus(postgresqlMonitorDTO,StatusTypeEnum.WARN_TYPE.getMsgCode(), WarnTypeEnum.CONNECT_EXCESS.getCode());
                postgresqlWatchHandle.connectExcessWarnning(postgresqlMonitorDTO);
            }else{
                super.assableStatus(postgresqlMonitorDTO,StatusTypeEnum.SUCCESS_TYPE.getMsgCode(), "");
                postgresqlWatchHandle.connectSuccess(postgresqlMonitorDTO);
            }
               return postgresqlMonitorDTO;
        }catch ( Exception e){
            LOG.error("postgresql监控抛出的异常为："+ e.getClass().getName());
            LOG.error(e);
            if( !( e instanceof ConnectionRejectException ) ) throw e;
            super.assableStatus(postgresqlMonitorDTO,StatusTypeEnum.ERROR_TYPE.getMsgCode(), "");
            postgresqlWatchHandle.connectReject(postgresqlMonitorDTO);
            return postgresqlMonitorDTO;
        }finally {
            LOG.info("judgeExcepType运行完成");
        }
    }

    @Override
    public void assableMonitor(PostgresqlMonitorDTO postgresqlMonitorDTO) {
        /**
         * 类型
         */
        postgresqlMonitorDTO.getDataSources().setUser(null);
        postgresqlMonitorDTO.getDataSources().setPassword(null);
        postgresqlMonitorDTO.getMonitorType().setCode( MsgChildrenTypeEnum.DBERR_TYPE.getMsgCode() );
        postgresqlMonitorDTO.getMonitorType().setName( MsgChildrenTypeEnum.DBERR_TYPE.getMsgName() );
        if( StringUtils.isBlank(postgresqlMonitorDTO.getWarnType()) ){
            String msg = StatusTypeEnum.getNameByCode(postgresqlMonitorDTO.getStatus());
            postgresqlMonitorDTO.setContent(postgresqlMonitorDTO.getMonitorType().getName()+ msg);
        }else{
            String msg = WarnTypeEnum.getNameByCode(postgresqlMonitorDTO.getWarnType());
            postgresqlMonitorDTO.setContent(postgresqlMonitorDTO.getMonitorType().getName()+ msg);
        }
        String hostHash = postgresqlMonitorDTO.getDataSources().getHost().split("&")[0].split("//")[1];
        postgresqlMonitorDTO.setServerName(hostHash);//服务主键

    }



}
