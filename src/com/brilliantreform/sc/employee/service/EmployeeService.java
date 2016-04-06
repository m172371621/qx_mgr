package com.brilliantreform.sc.employee.service;

import com.brilliantreform.sc.employee.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Lm on 2015/11/16 0016.
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;


    public List<Map> selEmployeeList(Map map) {
        return (List<Map>) employeeDao.selEmployeeList(map);
    }

    public Map selEmployee(int user_id) {
        return employeeDao.selEmployee(user_id);
    }


    public int selEmployeeCount(Map map) {
        return employeeDao.selEmployeeCount(map);
    }

    public List<Map> queryChildrenList(int cid) {
        return (List<Map>) employeeDao.queryChildrenList(cid);
    }

    public List<Integer> selMgrUserRole(int user_id) {
        return (List<Integer>) employeeDao.selMgrUserRole(user_id);
    }

    public List<Map> selMgrRole() {
        return (List<Map>) employeeDao.selMgrRole();
    }

    public int insertEmployee(Map map) {
        return employeeDao.insertEmployee(map);
    }

    public int insertMgrUserRole(Map map) {
        return employeeDao.insertMgrUserRole(map);
    }

    public int insertMgrUserCommunity(Map map) {
        return employeeDao.insertMgrUserCommunity(map);
    }

    public void updateEmployee(Map map) {
        employeeDao.updateEmployee(map);
    }

    public void delMgrUserRole(int user_id) {
        employeeDao.delMgrUserRole(user_id);
    }

    public void updateMgrUserCommunity(Map map) {
        employeeDao.updateMgrUserCommunity(map);
    }

    public void liZhiEmployee(int user_id) {
        employeeDao.liZhiEmployee(user_id);
    }
}
