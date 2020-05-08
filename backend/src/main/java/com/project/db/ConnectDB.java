package com.project.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.io.Reader;

import static org.apache.ibatis.io.Resources.*;

/**
 * Created by DongBaishun on 2017/7/10.
 */
@Component("connectDB")
public class ConnectDB
{
    public SqlSession getSqlSession() throws IOException {
        Reader reader= getResourceAsReader("mybatis-configure.xml");
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        return sqlSession;
    }
}
