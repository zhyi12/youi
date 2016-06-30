/**
 * 
 */
package org.youi.framework.ui.grid;

import java.util.ArrayList;
import java.util.List;

import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.IFieldContainerTag;
import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.util.StringUtils;

/**
 * @author zhyi_12
 *
 */
public class GridSingleQueryTag extends AbstractUiTag implements
		IFieldContainerTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1848545302464821331L;
	
	private String query;
	
	private List<AbstractField> fields = new ArrayList<AbstractField>();
	
	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.IFieldContainerTag#addField(com.gsoft.framework.ui.field.model.AbstractField)
	 */
	@Override
	public void addField(AbstractField field) {
		if(fields!=null&&field!=null){
			fields.add(field);
		}
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.IFieldContainerTag#getPrefix()
	 */
	@Override
	public String getPrefix() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {

	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		StringBuilder builder = new StringBuilder();
		
		if(fields!=null&&fields.size()>0){
			builder.append("<div class=\"single-property dropdown input-group-addon\">");
			builder.append("<span data-toggle=\"dropdown\" aria-expanded=\"true\"><span data-property=\""+fields.get(0).getProperty()+"\" class=\"query-property\">"+fields.get(0).getCaption()+"</span><span class=\"caret\"></span></span>");
			builder.append("<ul class=\"dropdown-menu\">");
			for(AbstractField field:fields){
				builder.append("<li data-value=\""+field.getProperty()+"\" class=\"option-item\">"+field.getCaption()+"</li>");
			}
			builder.append("</ul></div><input class=\"form-control query-value\" type=\"text\"/>")
			.append("<span data-name=\"singleQuerySubmit\" data-command=\"gridCommand\" class=\"query-button input-group-addon\">"+StringUtils.findNotEmpty(query,"搜索")+"</span>");
		}
		
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#uiStyles()
	 */
	@Override
	protected String uiStyles() {
		return "youi-grid-singleQuery";
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		return "";
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.fields = new ArrayList<AbstractField>();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
