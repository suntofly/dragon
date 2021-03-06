package com.tianshouzhi.dragon.ha.sqltype;

import com.tianshouzhi.dragon.ha.hint.ThreadLocalHintUtil;
import com.tianshouzhi.dragon.ha.jdbc.connection.DragonHAConnection;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by TIANSHOUZHI336 on 2016/12/4.
 */
public class HintTest extends BaseTest{
    @Test
    public void sqlHintTest() throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user ");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String tag_name = resultSet.getString("name");
            System.out.println("id:"+id+",name:"+tag_name);
        }
        preparedStatement = connection.prepareStatement("/*DRAGON_HA ( PHYSICAL_DS_INDEXES = dragon_ha_master )*/ SELECT * FROM user ");

        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String tag_name = resultSet.getString("name");
            System.out.println("id:"+id+",name:"+tag_name);
        }
        /*PreparedStatement insert = connection.prepareStatement("INSERT into user(user_id,username) VALUES (2,'wangxiaoxiao')");
        int i = insert.executeUpdate();
        insert.close();*/
    }

    @Test
    public void threadLocalHintTest() throws SQLException {
        ThreadLocalHintUtil.setDBIndexes("dragon_ha_master");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user ");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String tag_name = resultSet.getString("name");
            System.out.println("id:"+id+",name:"+tag_name);
        }
        ThreadLocalHintUtil.remove();
        resultSet.close();
        preparedStatement.close();
        connection.close();
        connection=null;
        DragonHAConnection newConn = (DragonHAConnection) dragonHADatasource.getConnection();
         preparedStatement = newConn.prepareStatement("SELECT * FROM user ");
         resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String tag_name = resultSet.getString("name");
            System.out.println("id:"+id+",name:"+tag_name);
        }
    }
}
