package com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload;

/**
 * Created by john on 22.05.2016.
 *
 * Upload Information parser
 */
public class UploadHelper {

    public static UploadBean checkResponse(String response) {
        final String[] split = response.split(";");
        final UploadBean bean = new UploadBean();

        if (split[0] != null){
            final boolean isSuccess = new Boolean(split[0].replace("upload=", ""));
            bean.setUploadSuccess(isSuccess);
        }else {
            bean.setUploadSuccess(false);
        }

        if (split[1] != null){
            final long settingID = Long.parseLong(split[1].replace("id=", ""));
            bean.setAttchmentID(settingID);
        }else {
            bean.setAttchmentID(-1L);
        }

        if (split[2] != null){
            final String filename = split[2].replace("filename=", "");
            bean.setFilename(filename);
        }else {
            bean.setFilename("");
        }

        if (split[3] != null){
            final String reason = split[3].replace("reason=", "");
            bean.setReasonWhenFailed(reason);
        }else {
            bean.setReasonWhenFailed("");
        }

        return bean;
    }
}
