package com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload;

import com.google.gwt.dom.client.DataTransfer;

/**
 * Created by john on 16.05.2016.
 */
public class MultiUploadRequst {

    public static native String getFileNames(DataTransfer transfer, String url, Progress callback) /*-{
        var filelist = [];
        var totalSize = 0;
        var currentUpload = null;
        var totalProgress = 0;
        var xhr = new XMLHttpRequest();    // den AJAX Request anlegen

        for (var i = 0; i < transfer.files.length; i++){
            filelist.push(transfer.files[i]);
            totalSize += transfer.files[i].size;
            startNextUpload();
        }

        function startNextUpload() {
            if (filelist.length) {
                currentUpload = filelist.shift();
                uploadFile(currentUpload);
            }
        }

        function uploadFile(file) {
            xhr.open('POST', url);    // Angeben der URL und des Requesttyps
            xhr.upload.addEventListener("progress", handleProgress);
            xhr.addEventListener("load", handleComplete);
            xhr.addEventListener("error", handleError);

            var formdata = new FormData();    // Anlegen eines FormData Objekts zum Versenden unserer Datei
            formdata.append('uploadfile', file);  // Anhängen der Datei an das Objekt
            xhr.send(formdata);    // Absenden des Requests
        }

        function handleComplete(event)
        {
            totalProgress += currentUpload.size;  // Füge die Größe dem Gesamtfortschritt hinzu
            //startNextUpload(); // Starte den Upload der nächsten Datei
            callback.@com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload.Progress::onComplete(Ljava/lang/String;)(xhr.responseText);
        }

        function handleError(event)
        {
            //alert("Upload failed");    // Die Fehlerbehandlung kann natürlich auch anders aussehen
            totalProgress += currentUpload.size;  // Die Datei wird dem Progress trotzdem hinzugefügt, damit die Prozentzahl stimmt
            callback.@com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload.Progress::onError()();
            startNextUpload();  // Starte den Upload der nächsten Datei
        }

        function handleProgress(event)
        {
            var progress = totalProgress + event.loaded;  // Füge den Fortschritt des aktuellen Uploads temporär dem gesamten hinzu
            //console.log('Aktueller Fortschritt: ' + (progress / totalSize) + '%');
            //console.log("progress: " + progress + " total: " + totalProgress);
            callback.@com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload.Progress::onProgress(Ljava/lang/Long; Ljava/lang/Long;)(progress, totalProgress);
            //document.getElementById('progress').innerHTML = 'Aktueller Fortschritt: ' + (progress / totalSize) + '%';
        }

        return transfer.files;
    }-*/;

}
