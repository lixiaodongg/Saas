package com.shadow.service.cargo;


import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.ExportContractProduct;
import com.shadow.bean.cargo.ExportContractProductExample;

import java.util.List;

public interface ExportContractProductService {

    /**
     * @param example  条件
     * @param pageNum  当前页
     * @param pageSize 每页条数
     * @return 分页对象
     */
    PageInfo<ExportContractProduct> findByPage(ExportContractProductExample example, int pageNum, int pageSize);


    List<ExportContractProduct> findAll(ExportContractProductExample example);


    ExportContractProduct findById(String id);

    int save(ExportContractProduct exportContractProduct);

    int update(ExportContractProduct exportContractProduct);

    int delete(String id);




}
