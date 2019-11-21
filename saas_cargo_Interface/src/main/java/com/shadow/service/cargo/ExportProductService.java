package com.shadow.service.cargo;

import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.ExportProduct;
import com.shadow.bean.cargo.ExportProductExample;

import java.util.List;

public interface ExportProductService {

    ExportProduct findById(String id);

    void save(ExportProduct exportProduct);

    void update(ExportProduct exportProduct);

    void delete(String id);

    PageInfo<ExportProduct> findByPage(ExportProductExample exportProductExample,
                                       int pageNum, int pageSize);

    List<ExportProduct> findAll(ExportProductExample example);
}
