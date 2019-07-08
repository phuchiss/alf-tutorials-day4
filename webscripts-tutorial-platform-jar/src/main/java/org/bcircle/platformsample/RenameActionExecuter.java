package org.bcircle.platformsample;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;

import java.util.List;

public class RenameActionExecuter extends ActionExecuterAbstractBase {
    private ServiceRegistry serviceRegistry;

    private static String PARAM_NAME = "name";

    @Override
    protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {

        String name = (String) action.getParameterValue(PARAM_NAME);

        serviceRegistry.getNodeService().setProperty(actionedUponNodeRef, ContentModel.PROP_NAME,
                name + "-" + serviceRegistry.getNodeService().getProperty(actionedUponNodeRef,
                        ContentModel.PROP_NAME));
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
        paramList.add(new ParameterDefinitionImpl(PARAM_NAME, DataTypeDefinition.TEXT,
                true, "Start With"));
    }

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
}
