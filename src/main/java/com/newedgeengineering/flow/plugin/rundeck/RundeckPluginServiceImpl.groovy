package com.newedgeengineering.flow.plugin.rundeck

import net.xeoh.plugins.base.annotations.Capabilities;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.activiti.engine.delegate.DelegateExecution
import au.com.centrumsystems.flow.activiti.FlowStatus
import org.json.JSONObject
import com.sun.jersey.core.util.Base64
import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.WebResource
import com.sun.jersey.api.client.ClientResponse
import groovy.json.JsonBuilder
import au.com.centrumsystems.flow.ConfigurationService
import au.com.centrumsystems.flow.integrations.IntegrationUtils

@PluginImplementation
public class RundeckPluginServiceImpl implements PluginService
{
    
    public static grailsApplication
    
    @Override
    public void addGrailsAppContext(context)
    {
        def ctx = context;
        grailsApplication = ctx.getBean("grailsApplication");
    }

    @Override
    public void triggerStep(DelegateExecution execution)
    {
        println "EXECUTION VARIABLES - " + execution.getVariables().toMapString();
        if(!execution.getVariableLocal(FlowStatus.FLOW_ACTIVITY_STATUS))
        {
            execution.createVariableLocal(FlowStatus.FLOW_ACTIVITY_STATUS, FlowStatus.IN_PROGRESS.name())
        }
        else
        {
            execution.setVariable(FlowStatus.FLOW_ACTIVITY_STATUS, FlowStatus.IN_PROGRESS.name())
        }
        
        println "RESPONSE: -----------  " + new JSONObject(response).toString()
    }

    private triggerPlanData(DelegateExecution execution)
    {
        println "Rundeck triggerPlanData"
        return ""
    }

    @Override
    public void checkStatus(DelegateExecution execution)
    {
        String state = getIssueStatusFromResponse(execution.getVariable("planKey"))
        execution.setVariable("flowVariableIssueStatus", state)

        execution.setVariable(PARAM_BUILD_STATUS,"COMPLETED");
        if(state == "Successful")
        {
            FlowStatus status = Enum.valueOf(FlowStatus.class, "SUCCESS")
            execution.setVariable(FlowStatus.FLOW_ACTIVITY_STATUS, status.name())
            execution.setVariable("TOOL_STATUS","Completed")
        } else {
            FlowStatus status = Enum.valueOf(FlowStatus.class, "FAILURE")
            execution.setVariable(FlowStatus.FLOW_ACTIVITY_STATUS, status.name())
            execution.setVariable("TOOL_STATUS","Completed")
        }
    }

    private String getIssueStatusFromResponse(JSONObject response)
    {
        return response.results.result.state
    }
    
    private JSONObject callRundeck(String planKey)
    {
        return ""
    }

    @Override
    public void updateFlowParameters(DelegateExecution execution)
    {
        //
    }

    /**
     * Annotated method which returns plugin's id (must be unique between plugins). 
     * this id have to match with toolId parameter in json file
     */
    @Capabilities
    public String[] capabilities()
    {
        return ["RUNDECK"] as String[];
    }
}