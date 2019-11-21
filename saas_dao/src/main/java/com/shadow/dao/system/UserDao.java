package com.shadow.dao.system;


import com.shadow.bean.system.PeRole;
import com.shadow.bean.system.PeUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //根据企业id查询全部
    List<PeUser> findAll(String companyId);

    //根据id查询
    PeUser findById(String userId);

    //根据id删除
    int delete(String userId);

    //保存
    int save(PeUser user);

    //更新
    int update(PeUser user);

    List<PeRole> findRoleByUserId(String userId);

    int deleteUserRoleByUserId(String userId);

    int insertUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

    List<PeUser> findByEmail(String email);
}
