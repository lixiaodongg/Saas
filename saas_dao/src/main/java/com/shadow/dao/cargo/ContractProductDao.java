package com.shadow.dao.cargo;

import com.shadow.bean.cargo.ContractProduct;
import com.shadow.bean.cargo.ContractProductExample;
import com.shadow.bean.cargo.ContractProductVo;

import java.util.List;

public interface ContractProductDao {
    int deleteByPrimaryKey(String id);

    int insert(ContractProduct record);

    int insertSelective(ContractProduct record);

    List<ContractProduct> selectByExample(ContractProductExample example);

    ContractProduct selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractProduct record);

    int updateByPrimaryKey(ContractProduct record);

    List<ContractProductVo> findByShipTime(String companyId, String inputDate);
}