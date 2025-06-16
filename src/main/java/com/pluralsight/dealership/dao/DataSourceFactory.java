package com.pluralsight.dealership.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.util.Properties;
import java.io.InputStream;

public class DataSourceFactory {
    private static BasicDataSource ds;
    private DataSourceFactory(){}
    public static DataSource get(){
        if(ds==null){
            synchronized(DataSourceFactory.class){
                if(ds==null){
                    try(InputStream in=DataSourceFactory.class.getClassLoader().getResourceAsStream("db.properties")){
                        Properties p=new Properties();
                        p.load(in);
                        ds=new BasicDataSource();
                        ds.setUrl(p.getProperty("jdbc.url"));
                        ds.setUsername(p.getProperty("jdbc.user"));
                        ds.setPassword(p.getProperty("jdbc.password"));
                        ds.setMinIdle(3);
                        ds.setMaxTotal(10);
                    }catch(Exception ex){throw new RuntimeException(ex);}
                }
            }
        }
        return ds;
    }
}
