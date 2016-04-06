package com.brilliantreform.sc.employee.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Lm on 2015/11/16 0016.
 */
@Repository
public class EmployeeDao {
    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    private static final String NAMESPACE = "employee.";

    public List<Map> selEmployeeList(Map map) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE.concat("selEmployeeList"), map);
    }

    public Map selEmployee(int user_id) {
        return (Map) sqlMapClient.queryForObject(NAMESPACE.concat("selEmployee"), user_id);
    }

    public int selEmployeeCount(Map map) {
        return (Integer) sqlMapClient.queryForObject(NAMESPACE.concat("selEmployeeCount"), map);
    }

    public List<Map> queryChildrenList(int cid) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE.concat("queryChildrenList"), cid);
    }

    public List<Integer> selMgrUserRole(int user_id) {
        return (List<Integer>) sqlMapClient.queryForList(NAMESPACE.concat("selMgrUserRole"), user_id);
    }

    public List<Map> selMgrRole() {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE.concat("selMgrRole"));
    }

    public int insertEmployee(Map map) {
        return (Integer) sqlMapClient.insert(NAMESPACE.concat("insertEmployee"), map);
    }

    public int insertMgrUserRole(Map map) {
        return (Integer) sqlMapClient.insert(NAMESPACE.concat("insertMgrUserRole"), map);
    }

    public int insertMgrUserCommunity(Map map) {
        return (Integer) sqlMapClient.insert(NAMESPACE.concat("insertMgrUserCommunity"), map);
    }

    public void updateEmployee(Map map) {
        sqlMapClient.update(NAMESPACE.concat("updateEmployee"),map);
    }

    public void delMgrUserRole(int user_id) {
         sqlMapClient.delete(NAMESPACE.concat("delMgrUserRole"), user_id);
    }

    public void updateMgrUserCommunity(Map map) {
        sqlMapClient.update(NAMESPACE.concat("updateMgrUserCommunity"), map);
    }

    public void liZhiEmployee(int user_id) {
        sqlMapClient.update(NAMESPACE.concat("liZhiEmployee"),user_id);
    }
}
