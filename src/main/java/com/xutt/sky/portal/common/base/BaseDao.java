package com.xutt.sky.portal.common.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseDao extends SqlSessionDaoSupport {
	private static Logger logger = LoggerFactory.getLogger(BaseDao.class);

	/**
	 * @Description 添加数据
	 * @author xutt
	 * @date 2018-2-6 15:36:24
	 * 
	 * @param statementName
	 *            sql语句id
	 * @param parameterObject
	 *            入参
	 * @return 添加记录数
	 */
	public int insert(String statementName, Object parameterObject) {
		int count = getSqlSession().insert(statementName, parameterObject);
		return count;
	}

	/**
	 * 
	 * @Description: 查询数据--单个对象
	 * @author xutt
	 * @date 2018-2-6 15:47:23
	 * @version V1.0
	 * 
	 */
	public Object queryForObject(String statementName, Object parameterObject) {
		Object oo = getSqlSession().selectOne(statementName, parameterObject);

		return oo;
	}

	/**
	 * 
	 * @Description: 查询数据--单个对象-不加参数
	 * @author xutt
	 * @date 2018-2-6 15:47:23
	 * @version V1.0
	 * 
	 */
	public Object queryForList(String statementName) {
		Object oo = null;
		try {
			oo = getSqlSession().selectList(statementName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return oo;
	}

	/**
	 * 
	 * @Description: 查询集合--有参数
	 * @author xutt
	 * @date 2018-2-6 15:47:23
	 * @version V1.0
	 * 
	 */
	public <T> List<T> queryForList(String statementName, Object parameterObject) {
		List<T> result = null;
		try {
			result = getSqlSession().selectList(statementName, parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @Description: 修改数据
	 * @author xutt
	 * @date 2018-2-6 15:47:23
	 * @version V1.0
	 * 
	 */
	public int updateObject(String statementName, Object parameterObject) {
		int count = getSqlSession().update(statementName, parameterObject);
		return count;
	}

	/**
	 * 
	 * @Description: 删除数据
	 * @author xutt
	 * @date 2018-2-6 15:47:23
	 * @version V1.0
	 * 
	 */
	public int deleteObject(String statementName, Object parameterObject) {
		int count = getSqlSession().delete(statementName, parameterObject);
		return count;
	}

	/**
	 * 
	 * @Description: 查询数据
	 * @author xutt
	 * @date 2018-2-6 15:47:23
	 * @version V1.0
	 * 
	 */
	public int selectOne(String statementName, Object parameterObject) {
		Integer count = 0;
		try {
			count = getSqlSession().selectOne(statementName, parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return count;
	}

	public double selectOneDouble(String statementName, Object parameterObject) {
		double val = 0;
		try {
			val = getSqlSession().selectOne(statementName, parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return val;
	}

	/**
	 * 
	 * @Description: 查询数据
	 * @author xutt
	 * @date 2018-2-6 15:47:23
	 * @version V1.0
	 * 
	 */
	public Map<String, Object> selectObjectByProcedure(String statementName, Map<String, Object> map) {
		try {
			getSqlSession().selectOne(statementName, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated method stub
		super.setSqlSessionFactory(sqlSessionFactory);
	}

}
