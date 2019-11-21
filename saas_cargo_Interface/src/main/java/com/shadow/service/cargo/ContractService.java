package com.shadow.service.cargo;

import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.Contract;
import com.shadow.bean.cargo.ContractExample;

import java.util.List;

public interface ContractService {

    /**
     * 分页查询
     * @param ContractExample 分页查询的参数
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @return
     */
    PageInfo<Contract> findByPage(
            ContractExample ContractExample, int pageNum, int pageSize);

    /**
     * 查询所有
     */
    List<Contract> findAll(ContractExample ContractExample);

    /**
     * 根据id查询
     */
    Contract findById(String id);

    /**
     * 新增
     */
    int save(Contract contract);

    /**
     * 修改
     */
    int update(Contract contract);

    /**
     * 删除
     */
    int  delete(String id);
}
