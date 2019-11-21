package com.shadow.dao.cargo;

import com.shadow.bean.cargo.ExportContractProduct;
import com.shadow.bean.cargo.ExportContractProductExample;

import java.util.List;

public interface ExportContractProductDao {
    int deleteByPrimaryKey(String id);

    int insert(ExportContractProduct record);

    int insertSelective(ExportContractProduct record);

    List<ExportContractProduct> selectByExample(ExportContractProductExample example);

    ExportContractProduct selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExportContractProduct record);

    int updateByPrimaryKey(ExportContractProduct record);
}