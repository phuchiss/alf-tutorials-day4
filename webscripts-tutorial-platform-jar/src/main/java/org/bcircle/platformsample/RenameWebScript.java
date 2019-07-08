package org.bcircle.platformsample;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.json.simple.JSONObject;
import org.springframework.extensions.webscripts.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RenameWebScript extends DeclarativeWebScript {

    private ServiceRegistry serviceRegistry;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected Map<String, Object> executeImpl(
            WebScriptRequest req, Status status, Cache cache) {

        String nodeid = req.getParameter("nodeid");
        String name = req.getParameter("name");

        NodeRef nodeRef = new NodeRef(nodeid);

        QName documentNo = QName.createQName("demo", "DocumentNo",
                serviceRegistry.getNamespaceService());

        serviceRegistry.getNodeService().setProperty(nodeRef, ContentModel.PROP_NAME,
                name + "-" + serviceRegistry.getNodeService().getProperty(nodeRef, ContentModel.PROP_NAME));

        serviceRegistry.getNodeService().setProperty(nodeRef, documentNo, "test");


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("status", "success");
        model.put("nodeid", nodeid);
        model.put("name", serviceRegistry.getNodeService().getProperty(nodeRef, ContentModel.PROP_NAME));

        return model;
    }

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
}
