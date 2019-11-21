package com.shadow.dao.cargo;

import com.shadow.bean.cargo.Contract;
import com.shadow.bean.cargo.ContractExample;

import java.util.List;


public interface ContractDao {
    int deleteByPrimaryKey(String id);

    int insert(Contract record);

    int insertSelective(Contract record);

    List<Contract> selectByExample(ContractExample example);

    Contract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);
}