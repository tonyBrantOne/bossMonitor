package com.quartz.monitor.publisher;


import com.quartz.monitor.handle.EsWatchHandle;
import com.quartz.monitor.model.esModel.EsMonitorDTO;
import com.quartz.monitor.util.ProxyUtil;
import org.springframework.stereotype.Component;

/**
 * title:
 * create by tony_jaa
 * date 2018/12/28
 */
@Component
public class QuartzEsMonitor extends AbstractQuartzMonitor<EsMonitorDTO> {

    private EsWatchHandle esWatchHandle;

    public EsWatchHandle getEsWatchHandle() {
        return esWatchHandle;
    }

    public void setEsWatchHandle(EsWatchHandle esWatchHandle) {
        this.esWatchHandle = ProxyUtil.getInstance(EsWatchHandle.class,esWatchHandle);
    }

    @Override
    protected void checkHeartJump() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("检查es心跳:=========================================================");
//        esWatchHandle.connectExcessWarnning(new EsMonitorDTO());
//        esWatchHandle.connectReject(new EsMonitorDTO());
    }

    @Override
    public EsMonitorDTO judgeExcepType(EsMonitorDTO esMonitorDTO) throws Exception{
        return null;
    }

    @Override
    public void assableMonitor(EsMonitorDTO esMonitorDTO) {

    }


}
