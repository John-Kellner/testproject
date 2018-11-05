package com.project.presentation.client.management.menu.view.table;

import com.google.gwt.user.client.ui.HTML;
import com.project.presentation.shared.dto.AvailableUserDTO;

import java.util.List;

/**
 * Created by john on 04.07.2015.
 */
public class TableAddedUser {
    public static HTML getTable(List<AvailableUserDTO> availableUsers){
        final StringBuilder builder = new StringBuilder();
        builder.append("<table class='striped'>");

        /** Head */
        builder.append("<thead>");
        builder.append("<tr>");
        builder.append("<th data-field='id'> Name </th>");
        builder.append("<th data-field='url'> URL </th>");
        builder.append("</tr>");
        builder.append("</thead>");

        /** Body */
        builder.append("<tbody>");

        for (AvailableUserDTO availableUser : availableUsers) {
            builder.append("<tr>");
            builder.append("<td>"+ availableUser.getName() + "</td>");
            builder.append("<td><a href="+ availableUser.getUserUrl() + " target='_blank'> " + availableUser.getUserUrl()+ " </a></td>");
            builder.append("</tr>");
        }

        builder.append("</tbody>");
        builder.append("</table>");
        final HTML html = new HTML(builder.toString());

        return html;
    }
}
