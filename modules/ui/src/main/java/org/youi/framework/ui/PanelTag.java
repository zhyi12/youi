/**
 * 
 */
package org.youi.framework.ui;

import javax.servlet.jsp.tagext.Tag;

import org.youi.framework.ui.accordion.AccordionTag;
import org.youi.framework.ui.button.Button;
import org.youi.framework.ui.util.HtmlUtils;
import org.youi.framework.util.StringUtils;

/**
 * @author zhyi_12
 *
 */
public class PanelTag extends AbstractUiTag  implements IButtonContainerTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5029096254077670369L;

	private int column = 1;//列占位，用于panelgroup布局使用
	
	@Override
	public void initUi() {
		
	}
	
	public String uiStartHtml(){
		String title = caption==null?"":caption;
		StringBuffer htmls = new StringBuffer();
		int colColumn = column;
		if(colColumn<0||colColumn>12){
			colColumn = 12;
		}
		
		Tag accordionTag = findAncestorWithClass(this, AccordionTag.class);
		
		String accordionId = null;
		String panelContentId = this.id;
		if(accordionTag!=null){
			accordionId = ((AccordionTag)accordionTag).getId();
		}
		
		StringBuilder panelStyles = new StringBuilder("panel-content");
		
		if(StringUtils.isNotEmpty(caption)){
			htmls.append("<div class=\"youi-panel panel panel-default").append(" col-sm-"+colColumn)
				.append(StringUtils.isNotEmpty(styleClass)?" "+styleClass:"")
				.append("\" ")
				.append(attrHtml("style", attrStyle()))
				.append("><div class=\"panel-header panel-heading\" role=\"tab\">")
				.append("	<div class=\"panel-header-title\">")
				.append(StringUtils.isEmpty(accordionId)?title:("<a role=\"button\" data-toggle=\"collapse\" data-parent=\"#"+accordionId+"\" href=\"#"+panelContentId+"\" aria-expanded=\"true\" >"+title+"</a>"))
				.append("</div>")
				.append("</div>");
		}else{
			panelStyles.append(" col-sm-"+colColumn);
			panelStyles.append(this.styleClass==null?"":(" "+this.styleClass));
		}
		
		if(StringUtils.isNotEmpty(accordionId)){
			panelStyles.append(" panel-collapse collapse");
		}
		
		StringBuilder styles = new StringBuilder();
		if(height>0){
			styles.append("height:"+this.height+"");
		}
		
		htmls.append("<div style=\""+styles+"\" id=\""+panelContentId+"\" class=\""+panelStyles.toString()+"\">");
		return htmls.toString();
	}

	@Override
	public void addButton(Button button) {
		// TODO Auto-generated method stub
	}

	@Override
	public String uiContentHtml() {
		return "";
	}
	
	public String uiEndHtml(){
		StringBuffer htmls = new StringBuffer();
		if(StringUtils.isNotEmpty(caption)){
			htmls.append( HtmlUtils.panelPostfix());
		}else{
			htmls.append("</div>");
		}
		
		return htmls.toString();
	}

	@Override
	protected String uiStyles() {
		return "youi-panel";
	}

	@Override
	protected String uiScripts() {
		return "";
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
