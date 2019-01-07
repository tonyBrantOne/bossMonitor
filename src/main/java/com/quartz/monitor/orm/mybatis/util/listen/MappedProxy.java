package com.quartz.monitor.orm.mybatis.util.listen;


import com.quartz.monitor.orm.mybatis.config.Configuration;
import com.quartz.monitor.orm.mybatis.config.MappedStatement;
import com.quartz.monitor.orm.mybatis.sqlSession.DefaultSqlSession;
import com.quartz.monitor.orm.mybatis.sqlSession.SqlSession;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class MappedProxy implements InvocationHandler {
    public SqlSession sqlSession;

    public MappedProxy(SqlSession target) {
        this.sqlSession = target;
    }

    /**
     * 代理类调用任何方法都会到invoke这个方法里，从mehtod这个方法里就可以知道应该调用被代理类的哪个方法了。
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理执行：=="+method.getDeclaringClass().getName()+"类=====执行："+method.getName());
//        System.out.println("传入的参数："+args[0]);
        String sourceId = AopUtils.getTargetClass(proxy).getGenericInterfaces()[0].getTypeName() + "." + method.getName();
        DefaultSqlSession defaultSqlSession = (DefaultSqlSession) sqlSession;
        Configuration configuration = defaultSqlSession.getConfiguration();
        Map<String, MappedStatement> mappedStatements = configuration.getMappedStatements();
        MappedStatement mappedStatement = mappedStatements.get(sourceId);
        String handelType = mappedStatement.getHandelType();
        Class<?> aClass = method.getReturnType();
        if( "select".equals(handelType) ){
           if( Collection.class.isAssignableFrom(aClass)){
               return defaultSqlSession.selectList(sourceId,args[0]);
           }
           return defaultSqlSession.selectOne(sourceId,args[0]);
        }
        return null;

    //    Object result =  method.invoke(target,args);
   //     System.out.println("执行后返回值:" + result);
    }

    public void test( Method method ){
        Type[] types = method.getGenericParameterTypes();//按照声明顺序返回 Type 对象的数组
        for (Type type : types) {
            if( !(type instanceof ParameterizedType ))  continue;
            ParameterizedType pType = (ParameterizedType) type;//最外层都是ParameterizedType
            Type[] types2 = pType.getActualTypeArguments();//返回表示此类型【实际类型参数】的 Type 对象的数组
            for (int i = 0; i < types2.length; i++) {
                Type type2 = types2[i];
                System.out.println(i + "  类型【" + type2 + "】\t类型接口【" + type2.getClass().getInterfaces()[0].getSimpleName() + "】");
            }
        }

    }

}
