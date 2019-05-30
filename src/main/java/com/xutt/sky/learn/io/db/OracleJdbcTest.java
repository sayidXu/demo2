package com.xutt.sky.learn.io.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class OracleJdbcTest {
	public static void main(String[] args) {
//		add();
		query();
	}
	
	public static void add(){
		/*
		 * JDBC的六个固定步骤 1，注册数据库驱动[利用反射] 2，取得数据库连接对象Connection 3，创建SQL对象
		 * 4，执行SQL命令，并返回结果集 5，处理结果集 6，依次关闭结果集
		 */
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			/** 1，注册数据库驱动[利用反射] */
			// com.mysql.jdbc.Driver,oracle.jdbc.driver.OracleDriver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/** 2，取得数据库连接对象Connection */
			// String url = "jdbc:mysql:///zz2017";
			String url = "jdbc:oracle:thin:@10.1.192.94:1521/orcl";
			conn = DriverManager.getConnection(url, "API_SHOW", "API_SHOW");
			/** 3，创建SQL对象 */
			String sql = "insert into t_xutt(name,age) values(?,?)";
			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1,"中国"); 
			String name = "中国";
			pstmt.setBytes(1, name.getBytes("UTF-8"));
			pstmt.setString(2,"5");
			
			/** 4，执行SQL命令，并返回结果集 */
			int result  = pstmt.executeUpdate();
			System.out.println("成功插入"+result+"条数据!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/** 6，依次关闭结果集 */
			if (rs != null) {// 轻量级，创建和销毁rs所需要的时间和资源较小
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {// 轻量级，创建和销毁rs所需要的时间和资源较小
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {// 重量级，创建和销毁rs所需要的时间和资源较小
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void query(){
		/*
		 * JDBC的六个固定步骤 1，注册数据库驱动[利用反射] 2，取得数据库连接对象Connection 3，创建SQL对象
		 * 4，执行SQL命令，并返回结果集 5，处理结果集 6，依次关闭结果集
		 */
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			/** 1，注册数据库驱动[利用反射] */
			// com.mysql.jdbc.Driver,oracle.jdbc.driver.OracleDriver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/** 2，取得数据库连接对象Connection */
			// String url = "jdbc:mysql:///zz2017";
			String url = "jdbc:oracle:thin:@172.16.90.219:1521/orcl";
//			DriverManager.setLoginTimeout(10);
			conn = DriverManager.getConnection(url, "RTDXDB", "RTDXDB");
			/** 3，创建SQL对象 */
			String sql = "select * from RTDX_EX_SCHEME";
			String sql1 = "select name,age from t_xutt where name =? for update";
			String sql2 = "SELECT T1.SCHEME_ID,                                                           "+
					"       T1.SCHEME_NAME,                                                         "+
					"       T1.TASK_ID,                                                             "+
					"       T2.TASK_NAME,                                                           "+
					"       T1.SCHEME_STATE,                                                        "+
					"       T1.LINK_ID,                                                             "+
					"       T4.LINK_MAN_NAME,                                                       "+
					"       T5.SRC_TYPE,                                                            "+
					"       T5.SRC_CHILD_TYPE,                                                      "+
					"       T1.CREATE_USER_ID,                                                      "+
					"       T1.CREATE_USER_NAME,                                                    "+
					"       T1.UPDATE_USER_ID,                                                      "+
					"       T1.UPDATE_USER_NAME,                                                    "+
					"       TO_CHAR(T1.CREATE_DATE, 'yyyy-mm-dd hh24:mi:ss') AS CREATE_DATE,        "+
					"       TO_CHAR(T1.UPDATE_DATE, 'yyyy-mm-dd hh24:mi:ss') AS UPDATE_DATE,        "+
					"       T1.IS_BATCH,                                                            "+
					"       (SELECT T3.ATTR_NAME                                                    "+
					"          FROM SYS_COMMPARA T3                                                 "+
					"         WHERE T3.ATTR_ID = 'state'                                            "+
					"           AND T3.ID = T1.SCHEME_STATE) AS STATE_NAME,                         "+
					"       (SELECT T3.ATTR_NAME                                                    "+
					"          FROM SYS_COMMPARA T3                                                 "+
					"         WHERE T3.ATTR_ID = 'exType'                                           "+
					"           AND T3.ID = T5.SRC_TYPE) AS SRC_TYPE_NAME,                          "+
					"       (SELECT T3.ATTR_NAME                                                    "+
					"          FROM SYS_COMMPARA T3                                                 "+
					"         WHERE T3.ATTR_ID = (CASE T5.SRC_CHILD_TYPE                            "+
					"                 WHEN '1' THEN                                                 "+
					"                  'dbChildType'                                                "+
					"                 ELSE                                                          "+
					"                  'httpChildType'                                              "+
					"               END)                                                            "+
					"           AND T3.ID = T5.SRC_CHILD_TYPE) AS SRC_CHILD_TYPE_NAME,              "+
					"       (CASE                                                                   "+
					"         WHEN T2.TASK_STATE = '1' THEN                                         "+
					"          'NO'                                                                 "+
					"         ELSE                                                                  "+
					"          'YES'                                                                "+
					"       END) UPDATESTATE,                                                       "+
					"       (SELECT NVL((SUM(T6.RTDX_TOTAL_NUM) - SUM(T6.RTDX_SUCCESS_NUM)), 0)     "+
					"          FROM RTDX_COLLECT_LOG T6                                             "+
					"         WHERE T6.TASK_ID = T2.TASK_ID) AS COLLECTERRORNUM,                    "+
					"       (SELECT NVL((SUM(T7.HANDLE_TOTAL_NUM) - SUM(T7.HANDLE_SUCCESS_NUM)),    "+
					"                   0)                                                          "+
					"          FROM RTDX_HANDLE_LOG T7                                              "+
					"         WHERE T7.SCHEME_ID = T1.SCHEME_ID                                     "+
					"           AND INSTR(T1.BPM_ECHO, T7.BPM_NODE_NO, 1, 1) > 0) AS HANDLEERRORNUM,"+
					"       (SELECT NVL((SUM(T8.LOAD_TOTAL_NUM) - SUM(T8.LOAD_SUCCESS_NUM)), 0)     "+
					"          FROM RTDX_LOAD_LOG T8                                                "+
					"         WHERE T8.SCHEME_ID = T1.SCHEME_ID                                     "+
					"           AND INSTR(T1.BPM_ECHO, T8.BPM_NODE_NO, 1, 1) > 0) AS LOADERRORNUM   "+
					"  FROM RTDX_EX_SCHEME2 T1                                                       "+
					"  LEFT JOIN RTDX_COLLECTOR_TASK T2                                             "+
					"    ON T1.TASK_ID = T2.TASK_ID                                                 "+
					"  LEFT JOIN RTDX_LINKMAN T4                                                    "+
					"    ON T1.LINK_ID = T4.LINK_ID                                                 "+
					"  LEFT JOIN RTDX_DATA_SRC T5                                                   "+
					"    ON T2.SRC_ID = T5.SRC_ID                                                   "+
					" WHERE 2 = 2                                                                   ";
			String sql3 ="insert into RTDX_EX_SCHEME2" +
						 " select * from RTDX_EX_SCHEME2";
			pstmt = conn.prepareStatement(sql2);
//			pstmt.setString(1, "许涛涛");
			/*
			 * pstmt.setString(1,"xiaozheng"); pstmt.setString(2,"男");
			 */
			/** 4，执行SQL命令，并返回结果集 */
			pstmt.setQueryTimeout(1);
			System.out.println("执行开始========>" + (new Date()).getTime());
			rs = pstmt.executeQuery();
//			pstmt.executeUpdate();
			System.out.println("执行结束========>" + (new Date()).getTime());
			/** 5，处理结果集 */
			System.out.println("处理结果集执行开始========>" + (new Date()).getTime());
			
			/*while (rs.next()) {
//				Thread.sleep(2000l);
//				System.out.println(rs);
				String schemeId = rs.getString("SCHEME_ID");
				//System.out.println(schemeId);
				
				int age = rs.getInt("age");
				
				byte[] bytes = rs.getBytes("name");
				System.out.print("age:"+age+",name:");
				CodeUtil.printHexadecimal(bytes);
				System.out.println();
				String name = new String(bytes, "UTF-8");
				
				String name = rs.getString("name");
				System.out.println(name + ":" + age);
			}*/
			System.out.println("处理结果集执行结束========>" + (new Date()).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/** 6，依次关闭结果集 */
			if (rs != null) {// 轻量级，创建和销毁rs所需要的时间和资源较小
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {// 轻量级，创建和销毁rs所需要的时间和资源较小
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {// 重量级，创建和销毁rs所需要的时间和资源较小
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
