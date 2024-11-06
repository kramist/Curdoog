package com.haverson.common.core.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public class PageInfoUtil {

    public static <T> PageInfo<T> IPageToPageInfo(IPage<T> iPage) {
        PageInfo<T> pageInfo = new PageInfo();
        pageInfo.setPageNum((int) iPage.getCurrent());
        pageInfo.setPageSize((int) iPage.getSize());
        pageInfo.setTotalCount((int) iPage.getTotal());
        pageInfo.setPageCount((int) iPage.getPages());
        pageInfo.setListData(iPage.getRecords());
        return pageInfo;
    }

    public static <T, S> PageInfo<T> IPageToPageInfoAndClass(IPage<S> iPage, Class<T> targetCls) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum((int) iPage.getCurrent());
        pageInfo.setPageSize((int) iPage.getSize());
        pageInfo.setTotalCount((int) iPage.getTotal());
        pageInfo.setPageCount((int) iPage.getPages());
        List<T> tList = BeanCopyUtil.copyList(iPage.getRecords(), targetCls);
        pageInfo.setListData(tList);
        return pageInfo;
    }

}
