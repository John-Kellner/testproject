package com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload;

/**
 * Created by john on 22.05.2016.
 */
public class UploadBean {
    private Long attachmentID = -1L;
    private boolean isUploadSuccess = false;
    private String filename = "";
    private String reasonWhenFailed = "";

    public Long getAttachmentID() {
        return attachmentID;
    }

    public void setAttchmentID(Long einstellungID) {
        this.attachmentID = einstellungID;
    }

    public boolean isUploadSuccess() {
        return isUploadSuccess;
    }

    public void setUploadSuccess(boolean uploadSuccess) {
        isUploadSuccess = uploadSuccess;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getReasonWhenFailed() {
        return reasonWhenFailed;
    }

    public void setReasonWhenFailed(String reasonWhenFailed) {
        this.reasonWhenFailed = reasonWhenFailed;
    }
}
