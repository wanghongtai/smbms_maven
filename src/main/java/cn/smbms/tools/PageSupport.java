package cn.smbms.tools;

public class PageSupport {
    //当前页码-来自于用户输入
    private int currentPageNo = 1;

    //总数量（表）
    private int totalCount = 0;

    //页面容量
    private int pageSize = 5;

    //总页数-totalCount/pageSize（+1）
    private int totalPageCount = 1;

    public PageSupport(int currentPageNo, int pageSize, int totalCount) {


        this.currentPageNo = currentPageNo;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        if (pageSize < 0) {
            pageSize = 5;
        }
        //计算总页数,当前页码
        this.totalPageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        this.totalPageCount = this.totalPageCount == 0 ? 1 : this.totalPageCount;
        if (currentPageNo > totalPageCount) {
            this.currentPageNo = totalPageCount;
        }else if(currentPageNo<0){
            this.currentPageNo = 1;
        }
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }
}