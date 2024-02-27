package com.sophia.blog_java_backend.entity;
/**
 * About Pagination
 */

public class Page {
    // current page number (with default value)
    private int current = 1;
    // limit
    private int limit = 10;
    // total (for calculating total pages)
    private int rows;
    // query path(For reuse)
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // get the start
    public int getOffset() {
        // current * limit - limit = (current-1) * limit
        return (current-1) * limit;
    }

    // get total pages
    public int getTotal() {
        // rows / limit [+1]
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    // 获取起始页码
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    // 获取终止页码
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total:to;
    }
}
