package org.youi.framework.ui.field.model;

public class FieldSwfupload extends AbstractField{
	private String buttonCaption;
	
	private String uploadUrl;
	
	private String flashUrl;
	
	private int buttonWidth;
	private int buttonHeight;
	
	//File Settings
	private String fileTypes;  //文件类型
	private String fileTypesDescription;//文件类型描述
	private String fileSizeLimit;//文件大小限制，单位 byte
	private int fileUploadLimit;//文件上传数量限制
	private int fileQueueLimit;//每次上传最大选择文件数量
	private String removeUrl;
	
	
	@Override
	public String[] addonProperies() {
		String[] addonProperies = {
				stringProperty("uploadUrl",uploadUrl),
				stringProperty("flashUrl",flashUrl),
				
				stringProperty("buttonCaption",buttonCaption),
				intProperty("buttonWidth",buttonWidth),
				intProperty("buttonHeight",buttonHeight),
				
				stringProperty("fileTypes",fileTypes),
				stringProperty("fileTypesDescription",fileTypesDescription),
				
				stringProperty("fileSizeLimit",fileSizeLimit),
				intProperty("fileUploadLimit",fileUploadLimit),
				intProperty("fileQueueLimit",fileQueueLimit),
				stringProperty("removeUrl",removeUrl),
			};
			return addonProperies;
	}
	/**
	 * @return the buttonCaption
	 */
	public String getButtonCaption() {
		return buttonCaption;
	}
	/**
	 * @param buttonCaption the buttonCaption to set
	 */
	public void setButtonCaption(String buttonCaption) {
		this.buttonCaption = buttonCaption;
	}
	/**
	 * @return the buttonWidth
	 */
	public int getButtonWidth() {
		return buttonWidth;
	}
	/**
	 * @param buttonWidth the buttonWidth to set
	 */
	public void setButtonWidth(int buttonWidth) {
		this.buttonWidth = buttonWidth;
	}
	/**
	 * @return the buttonHeigh
	 */
	public int getButtonHeight() {
		return buttonHeight;
	}
	/**
	 * @param buttonHeigh the buttonHeigh to set
	 */
	public void setButtonHeight(int buttonHeight) {
		this.buttonHeight = buttonHeight;
	}
	/**
	 * @return the fileTypes
	 */
	public String getFileTypes() {
		return fileTypes;
	}
	/**
	 * @param fileTypes the fileTypes to set
	 */
	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}
	/**
	 * @return the fileTypesDescription
	 */
	public String getFileTypesDescription() {
		return fileTypesDescription;
	}
	/**
	 * @param fileTypesDescription the fileTypesDescription to set
	 */
	public void setFileTypesDescription(String fileTypesDescription) {
		this.fileTypesDescription = fileTypesDescription;
	}
	/**
	 * @return the fileSizeLimit
	 */
	public String getFileSizeLimit() {
		return fileSizeLimit;
	}
	/**
	 * @param fileSizeLimit the fileSizeLimit to set
	 */
	public void setFileSizeLimit(String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}
	/**
	 * @return the fileUploadLimit
	 */
	public int getFileUploadLimit() {
		return fileUploadLimit;
	}
	/**
	 * @param fileUploadLimit the fileUploadLimit to set
	 */
	public void setFileUploadLimit(int fileUploadLimit) {
		this.fileUploadLimit = fileUploadLimit;
	}
	/**
	 * @return the fileQueueLimit
	 */
	public int getFileQueueLimit() {
		return fileQueueLimit;
	}
	/**
	 * @param fileQueueLimit the fileQueueLimit to set
	 */
	public void setFileQueueLimit(int fileQueueLimit) {
		this.fileQueueLimit = fileQueueLimit;
	}

	/**
	 * @return the uploadUrl
	 */
	public String getUploadUrl() {
		return uploadUrl;
	}
	/**
	 * @param uploadUrl the uploadUrl to set
	 */
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	/**
	 * @return the flashUrl
	 */
	public String getFlashUrl() {
		return flashUrl;
	}
	/**
	 * @param flashUrl the flashUrl to set
	 */
	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}
	public String getRemoveUrl() {
		return removeUrl;
	}
	public void setRemoveUrl(String removeUrl) {
		this.removeUrl = removeUrl;
	}
}
