package com.project.presentation.client.management.start;

import com.google.gwt.user.client.ui.IsWidget;
import com.project.presentation.client.management.start.activity.impl.EDocumentPage;
import com.project.presentation.client.management.start.activity.impl.StartActivity;

/**
 * Created by john on 12.09.2015.
 */
public interface IStartView extends IsWidget {

    void setActivity(StartActivity startActivity);

    void setPage(EDocumentPage view);
}
