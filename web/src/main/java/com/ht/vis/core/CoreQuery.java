package com.ht.vis.core;


import cn.hutool.core.util.StrUtil;

public abstract class CoreQuery {

    private String sortBy;

    private String descending;

    private Integer pn;

    private Integer ps;

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return this.sortBy;
    }

    public void setDescending(String descending) {
        this.descending = descending;
    }

    public String getDescending() {
        return this.descending;
    }

    public Integer getPn() {
        return pn==null?1:pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public Integer getPs() {
        return ps==null?15:ps;
    }

    public void setPs(Integer ps) {
        this.ps = ps;
    }

    public String getOrderBy(){
        String order=StrUtil.isBlank(descending)?null:descending;
        String sortField=StrUtil.isBlank(sortBy)?null:sortBy;
        if(StrUtil.isNotBlank(order)&&StrUtil.isNotBlank(sortField))
            return sortBy+" "+(("true".equals(order))?"desc":"asc");
        else
            return null;
    }
}