package com.project.presentation.client.management.start.controller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;

import java.util.logging.Logger;

/**
 * Created by john on 12.09.2015.
 */
public class StartViewController extends Composite {
    private RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);
    protected Logger logger = Logger.getLogger("StartViewController");


}
