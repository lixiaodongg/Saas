package com.shadow.service.cargo;

import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.Export;
import com.shadow.bean.cargo.ExportExample;

import java.lang.reflect.InvocationTargetException;

/**
 * 报运单的服务类
 */
public interface ExportService {

    PageInfo<Export> findByPage(ExportExample exportExample, int pageNum, int pageSize);


    Export findById(String id);

    void save(Export export) throws InvocationTargetException, IllegalAccessException;

    void update(Export export);

    void delete(String id);

}
