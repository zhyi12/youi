/**
 * 
 */
package org.youi.framework.ui.field.model;

/**
 * @author zhyi_12
 *
 */
public class FieldMultiupload extends AbstractField {
	
	private String uploadUrl;
	private String fileTypes;  //文件类型
	private String fileTypesDescription;//文件类型描述
	private String fileSizeLimit;//文件大小限制，单位 byte
	private int fileUploadLimit;//文件上传数量限制
	private int fileQueueLimit;//每次上传最大选择文件数量

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.field.model.AbstractField#addonProperies()
	 */
	@Override
	public String[] addonProperies() {
		String[] addonProperies = { 
				stringProperty("uploadUrl",uploadUrl),
				stringProperty("fileTypes",fileTypes),
				stringProperty("fileTypesDescription",fileTypesDescription),
				
				stringProperty("fileSizeLimit",fileSizeLimit),
				intProperty("fileUploadLimit",fileUploadLimit),
				intProperty("fileQueueLimit",fileQueueLimit),
			};
			return addonProperies;
	}

	public String getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}

	public String getFileTypesDescription() {
		return fileTypesDescription;
	}

	public void setFileTypesDescription(String fileTypesDescription) {
		this.fileTypesDescription = fileTypesDescription;
	}

	public String getFileSizeLimit() {
		return fileSizeLimit;
	}

	public void setFileSizeLimit(String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}

	public int getFileUploadLimit() {
		return fileUploadLimit;
	}

	public void setFileUploadLimit(int fileUploadLimit) {
		this.fileUploadLimit = fileUploadLimit;
	}

	public int getFileQueueLimit() {
		return fileQueueLimit;
	}

	public void setFileQueueLimit(int fileQueueLimit) {
		this.fileQueueLimit = fileQueueLimit;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

}
