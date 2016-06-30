/**
 * 
 */
package org.youi.framework.ui.tree;


import java.util.ArrayList;
import java.util.List;

import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.util.JsUtils;
import org.youi.framework.util.StringUtils;


/**
 * <p>Title:SDC UI组件</p>
 *
 * <p>Description:</p>
 *
 * <p>Copyright:Copyright (c) 2011.11</p>
 *
 * <p>Company:iSoftstone</p>
 * 
 * @author zhouyi
 * @version 1.0.0
 */
public class TreeTag extends AbstractUiTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5051375663405027666L;
	/**
	 * 
	 */
	private String rootText = "根节点";
	
	private boolean check;
	
	private boolean dragable;//拖动标识
	
	private String dropStyle;//接收元素样式
	
	/******************************begin 使用数据集*******************************/
	//private List<Domain> items;//树数据集，一次加载数据
	
	private HtmlTreeNode tree;//树结构数据
	
	private String itemRootId;//指定数据根节点
	
	private boolean hideRoot;//隐藏根节点
	/******************************end   使用数据集*******************************/
	
	/******************************begin 使用迭代src*****************************/
	private String iteratorSrc;//ajax迭代数据，逐层展开读取数据
	
	private String idAttr;//数据中用于id的属性
	
	private String textAttr;//数据中用于文本的属性
	
	private String pidAttr;//父ID属性
	
	private String idPrefix;//id前缀
	
	private String iteratorParam;//迭代参数名称
	
	private String xmenu;
	/******************************end   使用数据集******************************/

	private String dataFormId;//节点绑定的form表单
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {
		if(dataFormId!=null){
			dataFormId = this.wrapWithPageId(dataFormId);
		}
	}

	@Override
	protected String[][] uiAttrs() {
		List<String[]> uiAttrs = new ArrayList<String[]>();
		if(StringUtils.isNotEmpty(xmenu)){
			uiAttrs.add(new String[]{"data-xmenu",this.wrapWithPageId(xmenu)});
		}
		return uiAttrs.toArray(new String[uiAttrs.size()][2]);
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		
		HtmlTreeNode uiTree = null;
		/*
		 * 树结构数据
		 */
		if(tree!=null){//优先于items
			uiTree = tree;
		}else if(StringUtils.isNotEmpty(iteratorSrc)&&StringUtils.isEmpty(iteratorParam)){
			uiTree = new HtmlTreeNode("root_"+this.id, rootText);
			uiTree.setSrc(getActionPath(iteratorSrc));
			uiTree.setGroup("root");
		}
		
		if(uiTree!=null){
			htmls.append(uiTree.toHtml(true,check));
		}
		
		return htmls.toString();
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		StringBuffer scripts = new StringBuffer();
		scripts.append("window.setTimeout(function(){$('#"+id+"').tree({");
		scripts.append(JsUtils.propertyValue("id", id, JsUtils.JSON_PROP_STR));
		if(dragable){
			scripts.append(JsUtils.propertyValue("dropStyle", dropStyle, JsUtils.JSON_PROP_STR));
			scripts.append(JsUtils.propertyValue("dragable", dragable,  JsUtils.JSON_PROP_INT));
			//
			String stopFunc =   this.wrapWithPageId("func_"+this.orgId)+"_stop"; 
			scripts.append(JsUtils.propertyValue("stop", "function(event,ui){if($.isFunction(window['"+stopFunc+"'])){window['"+stopFunc+"'].apply(this,[event,ui]);}}",  JsUtils.JSON_PROP_INT));
		}
		scripts.append(JsUtils.propertyValue("check", check,  JsUtils.JSON_PROP_INT));
		scripts.append(JsUtils.propertyValue("hideRoot", hideRoot,  JsUtils.JSON_PROP_INT));
		scripts.append(JsUtils.propertyValue("idPrefix", idPrefix, JsUtils.JSON_PROP_STR));
		
		scripts.append(JsUtils.propertyValue("idAttr", idAttr, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("textAttr", textAttr, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("pidAttr", pidAttr, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("root", rootText, JsUtils.JSON_PROP_STR));
//		
		if(StringUtils.isNotEmpty(iteratorSrc)){
			if(StringUtils.isNotEmpty(iteratorParam)){
				scripts.append(JsUtils.propertyValue("iteratorSrc",this.getActionPath(iteratorSrc), JsUtils.JSON_PROP_STR));
				scripts.append(JsUtils.propertyValue("iteratorParentAttr", iteratorParam, JsUtils.JSON_PROP_STR));
			}else{
				scripts.append(JsUtils.propertyValue("autoLoad",true ,  JsUtils.JSON_PROP_INT));
			}
		}
		
		if(StringUtils.isNotEmpty(xmenu)){
			scripts.append(JsUtils.propertyValue("xmenu", this.wrapWithPageId(xmenu), JsUtils.JSON_PROP_STR));
		}
		
		scripts.append(JsUtils.propertyValue("dataFormId", dataFormId, JsUtils.JSON_PROP_STR));
		
		scripts.append(	"	initHtml:false");
		scripts.append(	"});}),100");
		return wrapScripts(scripts.toString());
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiStyles()
	 */
	@Override
	protected String uiStyles() {
		return "youi-tree";
	}

	/**
	 * @return the rootText
	 */
	public String getRootText() {
		return rootText;
	}

	/**
	 * @param rootText the rootText to set
	 */
	public void setRootText(String rootText) {
		this.rootText = rootText;
	}

	/**
	 * @return the iteratorSrc
	 */
	public String getIteratorSrc() {
		return iteratorSrc;
	}

	/**
	 * @param iteratorSrc the iteratorSrc to set
	 */
	public void setIteratorSrc(String iteratorSrc) {
		this.iteratorSrc = iteratorSrc;
	}

	/**
	 * @return the itemRootId
	 */
	public String getItemRootId() {
		return itemRootId;
	}

	/**
	 * @param itemRootId the itemRootId to set
	 */
	public void setItemRootId(String itemRootId) {
		this.itemRootId = itemRootId;
	}

	/**
	 * @return the idAttr
	 */
	public String getIdAttr() {
		return idAttr;
	}

	/**
	 * @param idAttr the idAttr to set
	 */
	public void setIdAttr(String idAttr) {
		this.idAttr = idAttr;
	}

	/**
	 * @return the textAttr
	 */
	public String getTextAttr() {
		return textAttr;
	}

	/**
	 * @param textAttr the textAttr to set
	 */
	public void setTextAttr(String textAttr) {
		this.textAttr = textAttr;
	}

	/**
	 * @return the iteratorParam
	 */
	public String getIteratorParam() {
		return iteratorParam;
	}

	/**
	 * @param iteratorParam the iteratorParam to set
	 */
	public void setIteratorParam(String iteratorParam) {
		this.iteratorParam = iteratorParam;
	}

	/**
	 * @return the check
	 */
	public boolean isCheck() {
		return check;
	}

	/**
	 * @param check the check to set
	 */
	public void setCheck(boolean check) {
		this.check = check;
	}

	/**
	 * @return the tree
	 */
	public HtmlTreeNode getTree() {
		return tree;
	}

	/**
	 * @param tree the tree to set
	 */
	public void setTree(HtmlTreeNode tree) {
		this.tree = tree;
	}

	/**
	 * @return the idPrefix
	 */
	public String getIdPrefix() {
		return idPrefix;
	}

	/**
	 * @param idPrefix the idPrefix to set
	 */
	public void setIdPrefix(String idPrefix) {
		this.idPrefix = idPrefix;
	}

	public boolean isHideRoot() {
		return hideRoot;
	}

	public void setHideRoot(boolean hideRoot) {
		this.hideRoot = hideRoot;
	}

	public boolean isDragable() {
		return dragable;
	}

	public void setDragable(boolean dragable) {
		this.dragable = dragable;
	}

	public String getDropStyle() {
		return dropStyle;
	}

	public void setDropStyle(String dropStyle) {
		this.dropStyle = dropStyle;
	}

	/**
	 * @return the dataFormId
	 */
	public String getDataFormId() {
		return dataFormId;
	}

	/**
	 * @param dataFormId the dataFormId to set
	 */
	public void setDataFormId(String dataFormId) {
		this.dataFormId = dataFormId;
	}

	/**
	 * @return the pidAttr
	 */
	public String getPidAttr() {
		return pidAttr;
	}

	/**
	 * @param pidAttr the pidAttr to set
	 */
	public void setPidAttr(String pidAttr) {
		this.pidAttr = pidAttr;
	}

	@Override
	public String uiStartHtml() {
		StringBuffer htmls = new StringBuffer();
		htmls.append(super.uiStartHtml());
		htmls.append("<ul>");
		return htmls.toString() ;
	}

	@Override
	public String uiEndHtml() {
		StringBuffer htmls = new StringBuffer();
		htmls.append("</ul>");
		htmls.append(super.uiEndHtml());
		return htmls.toString() ;
	}

	public String getXmenu() {
		return xmenu;
	}

	public void setXmenu(String xmenu) {
		this.xmenu = xmenu;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.check = false;
		this.dragable = false;
		this.dropStyle = null;
		this.hideRoot = false;
		this.idAttr = null;
		this.textAttr = null;
		this.pidAttr = null;
		this.itemRootId = null;
		this.iteratorParam = null;
		this.tree = null;
		this.rootText = null;
		this.idPrefix=null;
		this.tree = null;
		this.dataFormId = null;
		this.xmenu = null;
	}

}
