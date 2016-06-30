/**
 * 
 */
package org.youi.framework.ui.grid;

import java.util.ArrayList;
import java.util.List;

import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.GiuiConstants;
import org.youi.framework.ui.IButtonContainerTag;
import org.youi.framework.ui.button.Button;
import org.youi.framework.ui.util.GiuiHtmlUtils;
import org.youi.framework.ui.util.HtmlUtils;
import org.youi.framework.ui.util.JsUtils;
import org.youi.framework.util.StringUtils;

/**
 * @author zhyi_12
 *
 */
public class GridTag extends AbstractUiTag implements IColContainer,IButtonContainerTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = -42843255681281366L;
	
	private String src;
	
	private String removeSrc;
	
	private String idKeys;
	
	private boolean editable;
	
	private boolean showCheckbox;//是否显示checkbox列
	
	private boolean showRadio;//是否显示radio
	
	private boolean showNum;//是否显示序号列
	
	private int pageSize;//分页记录条数
	
	private boolean showFooter = true;
	
	private boolean load = true;
	
	private boolean panel = false;
	
	/**
	 * 非标签属性
	 */
	private List<GridCol> cols;//表格列模型集合
	
	private List<Button> buttons;//表格功能按钮集合

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {
		cols = new ArrayList<GridCol>();
		buttons = new ArrayList<Button>();
	}
	
	/**
	 * 使用panel包装grid组件 - 开始部分
	 */
	@Override
	protected String innerPrefixHtml() {
		if(!panel)return "";
		return HtmlUtils.panelPrefix(caption,styleClass);
	}
	
	/**
	 * 使用panel包装grid组件 - 结束部分
	 */
	@Override
	protected String innerPostfixHtml() {
		if(!panel)return "";
		return HtmlUtils.panelPostfix();
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		StringBuilder htmls = new StringBuilder();
		
		tableWidth();//计算宽度
		//table-bordered table-striped 
		
		if(buttons.size()>0){
			htmls.append(GiuiHtmlUtils.generateButtonsHtml(buttons,"right"));
		}
		
		if(this.editable){
			htmls.append("<div class=\"editor-tools\"><span data-command=\"gridCommand\" data-name=\"insertRow\" title=\"插入行\" class=\"youi-icon icon-plus\"></span>")
				.append("<span data-command=\"gridCommand\" data-name=\"removeRow\" class=\"youi-icon icon-remove\" title=\"删除行\"></span></div>");
		}
		
		htmls.append("<table class=\"table  table-hover table-condensed\">");
		//表头
		if(this.cols!=null){
			htmls.append("<thead>");
			for(GridCol col:cols){
				htmls.append("<th data-property=\""+col.getProperty()+"\" data-convert=\""+StringUtils.null2Empty(col.getConvert())+"\" class=\"grid-hcell dropdown\" width=\""+col.getWidth()+"\">")
				.append(StringUtils.isNotEmpty(col.getConvert())?"<div class=\"dropdown-property\" data-toggle=\"dropdown\" aria-expanded=\"true\">":"")
				.append(col.getCaption())
				.append(StringUtils.isNotEmpty(col.getConvert())?"(<span class=\"selected-item\">全部</span>)<span class=\"caret\" ></span></div><ul class=\"dropdown-menu\"></ul>":"")
				.append("</th>");
			}
			htmls.append("</thead>");
			htmls.append("");
		}
		htmls.append("<tbody></tbody>");
		
		htmls.append("</table>");
		
		if(this.showFooter){
			//表脚
			htmls.append("<div class=\"grid-footer\">")
				.append("<nav><ul class=\"pagination grid-navigator\">")
				.append("</ul><span class=\"pPageStat\"></span></nav>")
				.append("</div>");
		}
		
		return htmls.toString();
	}
	
	/**
	 * 计算列表table的宽度
	 * @return
	 */
	private int tableWidth() {
		if(width==null||width.endsWith("%"))return 0;
		int intWidth = Integer.parseInt(width);
		int colCount = cols.size();
		GridCol col;
		int tableWidth = 0;
//		if(showNum){
//			tableWidth+=NUM_CELL_WIDRH;
//		}
//		if(showCheckbox){
//			tableWidth+=CHECKBOX_CELL_WIDRH;
//		}
		for(int i=0;i<colCount;i++){
			col = (GridCol)cols.get(i);
			tableWidth+=Integer.parseInt(col.getWidth());
			//
			if(i==colCount-1&&tableWidth<intWidth){
				col.setWidth(""+(Integer.parseInt(col.getWidth())+intWidth-tableWidth));
				tableWidth = intWidth;
			}
			col =  null;
		}
		return tableWidth;
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#uiStyles()
	 */
	@Override
	protected String uiStyles() {
		return GiuiConstants.TAG_GRID;
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		int colCount = cols.size();
		GridCol col;
		
		StringBuilder scripts = new StringBuilder();
		scripts.append("$('#"+id+"')."+GiuiConstants.TAG_GRID+"({")
			.append(JsUtils.propertyValue("idKeys", idKeys, JsUtils.JSON_PROP_STR))
			.append(JsUtils.propertyValue("editable", editable, JsUtils.JSON_PROP_INT))
			.append(JsUtils.propertyValue("src", getActionPath(src), JsUtils.JSON_PROP_STR))
			.append(JsUtils.propertyValue("pageSize", pageSize>0?pageSize:15, JsUtils.JSON_PROP_INT))
			.append(JsUtils.propertyValue("showCheckbox", showCheckbox, JsUtils.JSON_PROP_INT))
			.append(JsUtils.propertyValue("showNum", showNum, JsUtils.JSON_PROP_INT))
			.append(JsUtils.propertyValue("load", load, JsUtils.JSON_PROP_INT))
			.append(JsUtils.propertyValue("showFooter", showFooter, JsUtils.JSON_PROP_INT))
			.append(JsUtils.propertyValue("removeSrc", getActionPath(removeSrc), JsUtils.JSON_PROP_STR))
			.append("cols:[");
		
		for(int i=0;i<colCount;i++){
			col = (GridCol)cols.get(i);
			if(i>0)scripts.append(",");
			scripts.append(col.toJson());
			col =  null;
		}
		
		scripts.append("],")
			.append(	"	initHtml:false")
			.append(	"});");
		
		return wrapScripts(scripts.toString());
	}

	@Override
	public void addCol(GridCol gridCol) {
		if(gridCol!=null){
			this.cols.add(gridCol);
		}
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getIdKeys() {
		return idKeys;
	}

	public void setIdKeys(String idKeys) {
		this.idKeys = idKeys;
	}

	public String getRemoveSrc() {
		return removeSrc;
	}

	public void setRemoveSrc(String removeSrc) {
		this.removeSrc = removeSrc;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isShowCheckbox() {
		return showCheckbox;
	}

	public void setShowCheckbox(boolean showCheckbox) {
		this.showCheckbox = showCheckbox;
	}

	public boolean isShowRadio() {
		return showRadio;
	}

	public void setShowRadio(boolean showRadio) {
		this.showRadio = showRadio;
	}

	public boolean isShowNum() {
		return showNum;
	}

	public void setShowNum(boolean showNum) {
		this.showNum = showNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isShowFooter() {
		return showFooter;
	}

	public void setShowFooter(boolean showFooter) {
		this.showFooter = showFooter;
	}

	public boolean isLoad() {
		return load;
	}

	public void setLoad(boolean load) {
		this.load = load;
	}

	public boolean isPanel() {
		return panel;
	}

	public void setPanel(boolean panel) {
		this.panel = panel;
	}

	@Override
	public void release() {
		super.release();
		this.cols = null;
		this.src = null;
		this.removeSrc = null;
		this.idKeys = null;
		
		this.panel = false;
		this.load = true;
		this.showFooter=true;
		this.showCheckbox = false;
		this.showNum = false;
	}

	@Override
	public void addButton(Button button) {
		if(button!=null){
			button.setCommand(GiuiConstants.TAG_GRID+"Command");
			buttons.add(button);
		}
	}

}
