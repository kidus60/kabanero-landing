/******************************************************************************
 *
 * Copyright 2019 IBM Corporation and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

$(document).ready(function() {
    loadAllInfo();
    setListeners();    
});

function setListeners(){
    // event delegation for dynamic collection hub input copy
    $(document).on("click", ".collection-hub-input", function(){
        $(this).siblings(".input-group-append").children(".tooltip-copy").tooltip("show");
        copy($(this));
    });

    $(document).on("click", ".copy-to-clipboard", function(){
        let id = $(this).data("inputIDToCopy");
        copy($(`#${id}`));
    });

    function copy(input){
        $(input).select();
        document.execCommand("copy");

        setTimeout(function(){
            $(".tooltip-copy").tooltip("hide");
        }, 1000);
    }
}

// Request to get all instances names
function loadAllInfo(){
    fetchAllInstances()
        .then(setInstanceData);

    fetchAllTools()
        .then(setToolData);
}

// Set details on UI for any given instance
function setInstanceData(instances){
    for(let instance of instances){
        let instanceName = instance.instanceName;
        let details = instance.details || {};

        let pane = new InstancePane(instanceName, details.dateCreated, details.repos, details.clusterName, 
            details.collections, details.cliURL);

        $("#instance-data-container").append(pane.instanceHTML, "<hr/>");
    }
}

// Set details on UI for any given instance
function setToolData(tools){
    let noTools = true;
    for(let tool of tools){
        if(typeof tool.label === "undefined" || tool.label.length === 0 || 
        typeof tool.location === "undefined" || tool.location.length === 0){
            continue;
        }

        let toolPane = new ToolPane(tool.label, tool.location);
        $("#tool-data-container").append(toolPane.toolHTML);
        noTools = false;
    }

    if(noTools){
        $("#no-tools").show();
    }
}