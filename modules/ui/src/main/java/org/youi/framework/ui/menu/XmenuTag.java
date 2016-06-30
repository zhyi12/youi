/**
 * 
 */
package org.youi.framework.ui.menu;

import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.util.JsUtils;

/**
 * @author zhyi_12
 *
 */
public class XmenuTag extends AbstractUiTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8115532630666640227L;
	
	private boolean popup = true;

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {
		
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		return "";
	}

	@Override
	protected String tagElementName() {
		return "ul";
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiStyles()
	 */
	@Override
	protected String uiStyles() {
		return "youi-xmenu"+(popup?" menu-popup":"");
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		StringBuffer scripts = new StringBuffer();
		scripts.append("$('#"+id+"').xmenu({");
		scripts.append(JsUtils.propertyValue("id", id, JsUtils.JSON_PROP_STR));
		
		//actions
//		if(actions!=null&&actions.size()>0){
//			scripts.append(	"menuActions:{");
//			int bIndex = 0;
//			for(String action:actions){
//				if(bIndex>0)scripts.append(",");
//				scripts.append(	action);
//				scripts.append( ":");//
//				scripts.append(	"'"+wrapWithPageId("func_xmenu_"+action)+"'");
//				bIndex++;
//			}
//			scripts.append(	"},");
//		}
		
		scripts.append(	"	initHtml:false");
		scripts.append(	"});");
		return scripts.toString();
	}


	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.popup = true;
	}

}
