package org.youi.framework.core.orm;


/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午05:18:11</p>
 */
public class Pager implements java.io.Serializable{
	/**
	 * 
	 */
	private final static long serialVersionUID = 8676370649225208176L;

	public final static int DEFALUT_PAGESIZE = 20;//默认页面记录条数
	
	private final static int DEFALUT_PAGEINDEX = 1;
	//
	public final static int QUERY_TYPE_ALL = 0;//

	public final static int QUERY_TYPE_LIST = 1;//

	public final static int QUERY_TYPE_COUNT = 2;//
	
	public final static String EXPORT_TYPE_XLS = "xls";//导出excel
	
	public final static String EXPORT_TYPE_PDF = "pdf";//导出pdf
	
	public final static String EXPORT_TYPE_PRINT = "print";//打印
	
	private int pageSize;//
	
	private int pageIndex;//
	
	private int pageType;//
	
	private int startIndex;//
	
	private int counts;
	
	private String[] pagerProperties;//查询属性
	
	private String export;//导出标识
	
	private String[] exportHeaders;//导出表头
	
	private String[] exportProperties;//导出属性
	
	private String[] exportConverts;//导出属性

	public Pager(String pageSize,String pageIndex,String pageType){
		try {
			this.pageSize = Integer.parseInt(pageSize);
		} catch (NumberFormatException e) {
			this.pageSize = DEFALUT_PAGESIZE;
		}
		
		try {
			this.pageIndex = Integer.parseInt(pageIndex);
		} catch (NumberFormatException e) {
			this.pageIndex = DEFALUT_PAGEINDEX;
		}
		
		try {
			this.pageType = Integer.parseInt(pageType);
		} catch (NumberFormatException e) {
			this.pageType = QUERY_TYPE_ALL;
		}
		this.initPager();
	}

	public Pager(int pageSize,int pageIndex,int pageType){
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.pageType = pageType;
		this.initPager();
	}
	/**
	 * 
	 * @param pageSize
	 * @param pageIndex
	 */
	private void initPager(){
		pageIndex = (pageIndex<1)?1:pageIndex;
		this.startIndex = (this.pageIndex-1)*this.pageSize; //
	}
	
	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
		int maxPage = getMaxPage();
		if(maxPage>0&&pageIndex>maxPage){
			pageIndex = maxPage;
		}
		initPager();
	}

	public int getPageIndex() {
		return pageIndex;
	}
	
	public int getMaxPage(){
		//转换为Double，防止int类型相除时不返回小数的情况
		double maxPage = Math.ceil(new Double(counts)/pageSize);
		return new Double(maxPage).intValue();
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}


	/**
	 * @return the pageType
	 */
	public int getPageType() {
		return pageType;
	}


	/**
	 * @param pageType the pageType to set
	 */
	public void setPageType(int pageType) {
		this.pageType = pageType;
	}


	/**
	 * @return the export
	 */
	public String getExport() {
		return export;
	}

	/**
	 * @param export the export to set
	 */
	public void setExport(String export) {
		this.export = export;
	}


	/**
	 * @return the exportHeaders
	 */
	public String[] getExportHeaders() {
		return exportHeaders;
	}


	/**
	 * @param exportHeaders the exportHeaders to set
	 */
	public void setExportHeaders(String[] exportHeaders) {
		this.exportHeaders = exportHeaders;
	}


	/**
	 * @return the exportProperties
	 */
	public String[] getExportProperties() {
		return exportProperties;
	}


	/**
	 * @param exportProperties the exportProperties to set
	 */
	public void setExportProperties(String[] exportProperties) {
		this.exportProperties = exportProperties;
	}

	/**
	 * @return the pagerProperties
	 */
	public String[] getPagerProperties() {
		return pagerProperties;
	}

	/**
	 * @param pagerProperties the pagerProperties to set
	 */
	public void setPagerProperties(String[] pagerProperties) {
		this.pagerProperties = pagerProperties;
	}
	
	public String[] getExportConverts() {
		return exportConverts;
	}

	public void setExportConverts(String[] exportConverts) {
		this.exportConverts = exportConverts;
	}
	
}
