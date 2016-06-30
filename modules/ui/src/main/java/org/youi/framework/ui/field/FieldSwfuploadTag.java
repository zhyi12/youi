/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.framework.ui.field;


import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.ui.field.model.FieldSwfupload;
import org.youi.framework.util.StringUtils;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jul 12, 2010
 */
public class FieldSwfuploadTag extends AbstractFieldTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -852355024888542092L;

	private String uploadUrl;// = "/upload/swfupload.html";//
	
	//flashUrl:'scripts/lib/swfupload.swf',
	//uploadUrl:'common/upload.jsp?repository=D:/testUpload',
	
	private String flashUrl = "/scripts/upload/swfupload.swf";//
	
	private String repository="/swfupload";//文件上传存储路径
	
	private String buttonCaption = "文件上传";
	private int buttonWidth = 73;
	private int buttonHeight = 30;
	
	//File Settings
	private String fileTypes;  //文件类型
	private String fileTypesDescription;//文件类型描述
	private String fileSizeLimit;//文件大小限制，单位 byte
	private int fileUploadLimit;//文件上传数量限制
	private int fileQueueLimit;//每次上传最大选择文件数量
	private String removeUrl;
	
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.AbstractFieldTag#createField()
	 */
	@Override
	protected AbstractField createField() {
		FieldSwfupload field = new FieldSwfupload();
		
		field.setFlashUrl(this.getUrlPath(flashUrl));
		
		if(StringUtils.isEmpty(uploadUrl)){
			uploadUrl = this.getMessage("youi.tag.swfUpload");
		}
		
		if(StringUtils.isEmpty(uploadUrl)){
			uploadUrl = "upload.html";
		}
		
		//组合文件上传路径
		String swfUploadUrl = this.getUrlPath(uploadUrl);
		//
		if(StringUtils.isNotEmpty(repository)){
			swfUploadUrl+=(swfUploadUrl.indexOf("?")==-1?"?":"&");
			swfUploadUrl+="repository=";
			swfUploadUrl+=repository;
		}
		
		field.setUploadUrl(swfUploadUrl);
		
		field.setButtonHeight(buttonHeight);
		field.setButtonWidth(buttonWidth);
		field.setButtonCaption(buttonCaption);
		field.setFileQueueLimit(fileQueueLimit);
		field.setFileSizeLimit(fileSizeLimit);
		field.setFileTypes(fileTypes);
		field.setFileTypesDescription(fileTypesDescription);
		field.setFileUploadLimit(fileUploadLimit);
		field.setRemoveUrl(swfUploadUrl);
		return field;
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		
		int filesContainerHeight;
		if(this.height>50){
			filesContainerHeight = this.height-this.buttonHeight-6;
		}else{
			filesContainerHeight = 50; 
		}
		
		htmls.append("<div class=\"field-swfupload-files\" style=\"height:"+filesContainerHeight+"px;\">")
			.append("<div class=\"field-swfupload-btn\"><div id=")
			.append(this._buildSwfuploadId())
			.append("></div></div></div>");
		
		return htmls.toString();
	}

	/**
	 * 创建组件ID
	 * @return
	 */
	private String _buildSwfuploadId() {
		return this.getId()+"_swfupload";
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
	 * @return the buttonHeight
	 */
	public int getButtonHeight() {
		return buttonHeight;
	}

	/**
	 * @param buttonHeight the buttonHeight to set
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

	/**
	 * @return the repository
	 */
	public String getRepository() {
		return repository;
	}

	/**
	 * @param repository the repository to set
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getRemoveUrl() {
		return removeUrl;
	}

	public void setRemoveUrl(String removeUrl) {
		this.removeUrl = removeUrl;
	}
	
	public void doFinally() {
		super.doFinally();
		this.fileQueueLimit = 0;
		this.removeUrl = null;
		this.uploadUrl = null;
//		this.flashUrl = "/scripts/upload/swfupload.swf"; 
//		this.buttonCaption = "文件上传";
		
		//File Settings
		this.fileTypes = null;  //文件类型
		this.fileTypesDescription = null;//文件类型描述
		this.fileSizeLimit = null;//文件大小限制，单位 byte
		this.fileUploadLimit=0;//文件上传数量限制
		this.fileQueueLimit=0;//每次上传最大选择文件数量
		
		
	}

}
