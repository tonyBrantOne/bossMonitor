package com.quartz.monitor.util.connecPool.postgresql;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 17:41
 * @Description:
 */

import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 17:41
 * @Description:
 */
public class ConnectProxy {

    private static Logger LOG = LogManager.getLogger( ConnectProxy.class );


    private static final String DRIVER = "org.postgresql.Driver";
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private PostgresqlDataSources dataSources;

    public ConnectProxy(PostgresqlDataSources dataSources) {
        this.dataSources = dataSources;
    }

    /**
     * @return返回数据库连接对象
     */
    public Connection getCon(){
        try {
            //注册驱动
            Class.forName(DRIVER);
            con=DriverManager.getConnection(dataSources.getHost(), dataSources.getUser() , dataSources.getPassword());
        } catch (Exception e) {
            throw new ConnectionRejectException(e);
        }
        return con;
    }

    public void resertCon() throws Exception {
        if( con.isClosed() ){
            synchronized ( this ){
                if( con.isClosed() ){
                      con.close();
                      getCon();
                      LOG.warn("重新连上con:" + con);
                }
            }
        }
    }

    /**
     * 关闭所有数据库连接资源
     */
    public void closeAll() throws Exception {
        if(rs!=null) rs.close();
        if(ps!=null) ps.close();
    }


    /**
     * @param
     * @param
     * @return返回结果集
     */
    public ResultSet query(  String sql,String... pras ) throws Exception{
        try {
            LOG.warn("con.isClosed()----------------" + con.isClosed());
            resertCon();
            //创建预编译处理对象
            ps=con.prepareStatement(sql);

            //为参数赋值
            if(pras!=null){
                //遍历每个参数进行赋值
                for(int i=0;i<pras.length;i++){
                    ps.setString(i+1, pras[i]);
                }
            }
            //执行查询命令
            ResultSet rs=ps.executeQuery();
            //这里不要写关闭所有资源
            return rs;
        }catch ( Exception e ){
            throw new ConnectionRejectException(e);
        }finally {

        }
    }




}
