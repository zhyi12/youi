/**
 * 
 */
package org.youi.framework.ui.field;

import org.springframework.util.StringUtils;
import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.ui.field.model.FieldImgupload;

/**
 * @author zhyi_12
 *
 */
public class FieldImguploadTag extends FieldSwfuploadTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2612240193242642906L;

	@Override
	public String uiContentHtml() {
		return "<div title=\"添加图片\" class=\"btn-add\">+</div>";
	}

	@Override
	protected AbstractField createField() {
		FieldImgupload field = new FieldImgupload();
		
		field.setFlashUrl(this.getUrlPath(getFlashUrl()));
		
		String uploadUrl = getUploadUrl();
		
		if(StringUtils.isEmpty(uploadUrl)){
			uploadUrl = this.getMessage("youi.tag.swfUpload");
		}
		
		if(StringUtils.isEmpty(uploadUrl)){
			uploadUrl = "upload.html";
		}
		
		//组合文件上传路径
		String swfUploadUrl = this.getUrlPath(uploadUrl);
		//
		if(!StringUtils.isEmpty(getRepository())){
			swfUploadUrl+=(swfUploadUrl.indexOf("?")==-1?"?":"&");
			swfUploadUrl+="repository=";
			swfUploadUrl+=getRepository();
		}
		
		field.setUploadUrl(swfUploadUrl);
		
		field.setButtonHeight(getButtonHeight());
		field.setButtonWidth(getButtonWidth());
		field.setButtonCaption(getButtonCaption());
		field.setFileQueueLimit(getFileQueueLimit());
		field.setFileSizeLimit(getFileSizeLimit());
		field.setFileTypes(getFileTypes());
		field.setFileTypesDescription(getFileTypesDescription());
		field.setFileUploadLimit(getFileUploadLimit());
		field.setRemoveUrl(swfUploadUrl);
		return field;
	}

	
}
