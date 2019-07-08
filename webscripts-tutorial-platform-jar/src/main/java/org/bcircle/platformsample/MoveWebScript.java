package org.bcircle.platformsample;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.springframework.extensions.webscripts.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MoveWebScript extends DeclarativeWebScript {

    private ServiceRegistry serviceRegistry;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected Map<String, Object> executeImpl(
            WebScriptRequest req, Status status, Cache cache) {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -10);
        String dateAfter = dateFormat.format(c.getTime());

        ResultSet resultSet = serviceRegistry.getSearchService().query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE,
                SearchService.LANGUAGE_FTS_ALFRESCO,
                "PATH:'/app:company_home/cm:Main//.' AND demo:DocumentDate:[MIN TO "
                        + dateAfter + "] AND cm:name:*citizen*");

        NodeRef companyHome = serviceRegistry.getNodeLocatorService().getNode("companyhome",
                null, null);

        String[] path = { "Archive" };

        try {
            FileInfo archiveFolder = serviceRegistry.getFileFolderService()
                    .resolveNamePath(companyHome, Arrays.asList(path));

            for(NodeRef nodeRef : resultSet.getNodeRefs()) {
                serviceRegistry.getNodeService().moveNode(nodeRef, archiveFolder.getNodeRef(), null
                , null);
            }

        } catch (FileNotFoundException e) {
            status.setCode(500, e.getMessage());
        }

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", "success");

        return model;
    }

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
}
