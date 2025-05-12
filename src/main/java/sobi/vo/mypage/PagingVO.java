package sobi.vo.mypage;



public class PagingVO {
    private int nowPage;     
    private int totalCount;  
    private int pageSize = 10;  // 페이지당
    private int blockSize = 5; 
    private int totalPage;
    private int startNo;
    private int endNo;
    private int startPage;
    private int endPage;

    public PagingVO(int nowPage, int totalCount) {
        this.nowPage = nowPage;
        this.totalCount = totalCount;

        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);

        this.startNo = (nowPage - 1) * pageSize;
        this.endNo = startNo + pageSize;

        this.startPage = ((nowPage - 1) / blockSize) * blockSize + 1;
        this.endPage = startPage + blockSize - 1;
        if (endPage > totalPage) endPage = totalPage;
    }

    public int getNowPage() { return nowPage; }
    public int getStartNo() { return startNo; }
    public int getPageSize() { return pageSize; }
    public int getStartPage() { return startPage; }
    public int getEndPage() { return endPage; }
    public int getTotalPage() { return totalPage; }
    public boolean hasPrev() { return startPage > 1; }
    public boolean hasNext() { return endPage < totalPage; }
}
