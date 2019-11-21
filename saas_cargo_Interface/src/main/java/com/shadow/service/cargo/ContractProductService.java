package com.shadow.service.cargo;

import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.ContractProduct;
import com.shadow.bean.cargo.ContractProductExample;
import com.shadow.bean.cargo.ContractProductVo;

import java.util.List;

public interface ContractProductService {

    /**
     * @param example  条件
     * @param pageNum  当前页
     * @param pageSize 每页条数
     * @return 分页对象
     */
    PageInfo<ContractProduct> findByPage(ContractProductExample example, int pageNum, int pageSize);


    List<ContractProduct> findAll(ContractProductExample example);


    ContractProduct findById(String id);

    int save(ContractProduct factory);

    int update(ContractProduct factory);

    int delete(String id);

    List<ContractProductVo> findByShipTime(String companyId, String inputDate);

}
