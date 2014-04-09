package com.newedgeengineering.flow.plugin.rundeck;

public interface PluginService extends Plugin {
    void addGrailsAppContext(Object context);
    void triggerStep(DelegateExecution execution);
    void checkStatus(DelegateExecution execution);
    void updateFlowParameters(DelegateExecution execution);
}
