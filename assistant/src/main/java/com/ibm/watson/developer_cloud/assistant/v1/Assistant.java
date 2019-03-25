/*
 * Copyright 2018 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.ibm.watson.developer_cloud.assistant.v1;

import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.assistant.v1.model.*;
import com.ibm.watson.developer_cloud.http.RequestBuilder;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.service.model.GenericModel;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.util.GsonSingleton;
import com.ibm.watson.developer_cloud.util.ResponseConverterUtils;
import com.ibm.watson.developer_cloud.util.Validator;
import okhttp3.HttpUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The IBM Watson&trade; Assistant service combines machine learning, natural language understanding, and integrated
 * dialog tools to create conversation flows between your apps and your users.
 *
 * @version v1
 * @see <a href="http://www.ibm.com/watson/developercloud/assistant.html">Assistant</a>
 */
public class Assistant extends WatsonService {

  private static final String SERVICE_NAME = "assistant";
  private static final String URL = "https://gateway.watsonplatform.net/assistant/api";

  private String versionDate;

  /**
   * Instantiates a new `Assistant`.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   */
  public Assistant(String versionDate) {
    super(SERVICE_NAME);
    if ((getEndPoint() == null) || getEndPoint().isEmpty()) {
      setEndPoint(URL);
    }

    Validator.isTrue((versionDate != null) && !versionDate.isEmpty(), "version cannot be null.");

    this.versionDate = versionDate;
  }

  /**
   * Instantiates a new `Assistant` with username and password.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   * @param username the username
   * @param password the password
   */
  public Assistant(String versionDate, String username, String password) {
    this(versionDate);
    setUsernameAndPassword(username, password);
  }

  /**
   * Instantiates a new `Assistant` with IAM. Note that if the access token is specified in the
   * iamOptions, you accept responsibility for managing the access token yourself. You must set a new access token
   * before this
   * one expires or after receiving a 401 error from the service. Failing to do so will result in authentication errors
   * after this token expires.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   * @param iamOptions the options for authenticating through IAM
   */
  public Assistant(String versionDate, IamOptions iamOptions) {
    this(versionDate);
    setIamCredentials(iamOptions);
  }

  /**
   * Get response to user input.
   *
   * Send user input to a workspace and receive a response.
   *
   * There is no rate limit for this operation.
   *
   * @param messageOptions the {@link MessageOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link MessageResponse}
   */
  public ServiceCall<MessageResponse> message(MessageOptions messageOptions) {
    Validator.notNull(messageOptions, "messageOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "message" };
    String[] pathParameters = { messageOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=message");
    if (messageOptions.nodesVisitedDetails() != null) {
      builder.query("nodes_visited_details", String.valueOf(messageOptions.nodesVisitedDetails()));
    }
    final JsonObject contentJson = new JsonObject();
    if (messageOptions.input() != null) {
      contentJson.add("input", GsonSingleton.getGson().toJsonTree(messageOptions.input()));
    }
    if (messageOptions.alternateIntents() != null) {
      contentJson.addProperty("alternate_intents", messageOptions.alternateIntents());
    }
    if (messageOptions.context() != null) {
      contentJson.add("context", GsonSingleton.getGson().toJsonTree(messageOptions.context()));
    }
    if (messageOptions.entities() != null) {
      contentJson.add("entities", GsonSingleton.getGson().toJsonTree(messageOptions.entities()));
    }
    if (messageOptions.intents() != null) {
      contentJson.add("intents", GsonSingleton.getGson().toJsonTree(messageOptions.intents()));
    }
    if (messageOptions.output() != null) {
      contentJson.add("output", GsonSingleton.getGson().toJsonTree(messageOptions.output()));
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(MessageResponse.class));
  }

  /**
   * Create workspace.
   *
   * Create a workspace based on component objects. You must provide workspace components defining the content of the
   * new workspace.
   *
   * This operation is limited to 30 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param createWorkspaceOptions the {@link CreateWorkspaceOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Workspace}
   */
  public ServiceCall<Workspace> createWorkspace(CreateWorkspaceOptions createWorkspaceOptions) {
    String[] pathSegments = { "v1/workspaces" };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=createWorkspace");
    if (createWorkspaceOptions != null) {
      final JsonObject contentJson = new JsonObject();
      if (createWorkspaceOptions.name() != null) {
        contentJson.addProperty("name", createWorkspaceOptions.name());
      }
      if (createWorkspaceOptions.description() != null) {
        contentJson.addProperty("description", createWorkspaceOptions.description());
      }
      if (createWorkspaceOptions.language() != null) {
        contentJson.addProperty("language", createWorkspaceOptions.language());
      }
      if (createWorkspaceOptions.intents() != null) {
        contentJson.add("intents", GsonSingleton.getGson().toJsonTree(createWorkspaceOptions.intents()));
      }
      if (createWorkspaceOptions.entities() != null) {
        contentJson.add("entities", GsonSingleton.getGson().toJsonTree(createWorkspaceOptions.entities()));
      }
      if (createWorkspaceOptions.dialogNodes() != null) {
        contentJson.add("dialog_nodes", GsonSingleton.getGson().toJsonTree(createWorkspaceOptions.dialogNodes()));
      }
      if (createWorkspaceOptions.counterexamples() != null) {
        contentJson.add("counterexamples", GsonSingleton.getGson().toJsonTree(createWorkspaceOptions
            .counterexamples()));
      }
      if (createWorkspaceOptions.metadata() != null) {
        contentJson.add("metadata", GsonSingleton.getGson().toJsonTree(createWorkspaceOptions.metadata()));
      }
      if (createWorkspaceOptions.learningOptOut() != null) {
        contentJson.addProperty("learning_opt_out", createWorkspaceOptions.learningOptOut());
      }
      if (createWorkspaceOptions.systemSettings() != null) {
        contentJson.add("system_settings", GsonSingleton.getGson().toJsonTree(createWorkspaceOptions.systemSettings()));
      }
      builder.bodyJson(contentJson);
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Workspace.class));
  }

  /**
   * Create workspace.
   *
   * Create a workspace based on component objects. You must provide workspace components defining the content of the
   * new workspace.
   *
   * This operation is limited to 30 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @return a {@link ServiceCall} with a response type of {@link Workspace}
   */
  public ServiceCall<Workspace> createWorkspace() {
    return createWorkspace(null);
  }

  /**
   * Delete workspace.
   *
   * Delete a workspace from the service instance.
   *
   * This operation is limited to 30 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param deleteWorkspaceOptions the {@link DeleteWorkspaceOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteWorkspace(DeleteWorkspaceOptions deleteWorkspaceOptions) {
    Validator.notNull(deleteWorkspaceOptions, "deleteWorkspaceOptions cannot be null");
    String[] pathSegments = { "v1/workspaces" };
    String[] pathParameters = { deleteWorkspaceOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.delete(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=deleteWorkspace");
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Get information about a workspace.
   *
   * Get information about a workspace, optionally including all workspace content.
   *
   * With **export**=`false`, this operation is limited to 6000 requests per 5 minutes. With **export**=`true`, the
   * limit is 20 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param getWorkspaceOptions the {@link GetWorkspaceOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link WorkspaceExport}
   */
  public ServiceCall<WorkspaceExport> getWorkspace(GetWorkspaceOptions getWorkspaceOptions) {
    Validator.notNull(getWorkspaceOptions, "getWorkspaceOptions cannot be null");
    String[] pathSegments = { "v1/workspaces" };
    String[] pathParameters = { getWorkspaceOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=getWorkspace");
    if (getWorkspaceOptions.export() != null) {
      builder.query("export", String.valueOf(getWorkspaceOptions.export()));
    }
    if (getWorkspaceOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(getWorkspaceOptions.includeAudit()));
    }
    if (getWorkspaceOptions.sort() != null) {
      builder.query("sort", getWorkspaceOptions.sort());
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(WorkspaceExport.class));
  }

  /**
   * List workspaces.
   *
   * List the workspaces associated with a Watson Assistant service instance.
   *
   * This operation is limited to 500 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param listWorkspacesOptions the {@link ListWorkspacesOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link WorkspaceCollection}
   */
  public ServiceCall<WorkspaceCollection> listWorkspaces(ListWorkspacesOptions listWorkspacesOptions) {
    String[] pathSegments = { "v1/workspaces" };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=listWorkspaces");
    if (listWorkspacesOptions != null) {
      if (listWorkspacesOptions.pageLimit() != null) {
        builder.query("page_limit", String.valueOf(listWorkspacesOptions.pageLimit()));
      }
      if (listWorkspacesOptions.includeCount() != null) {
        builder.query("include_count", String.valueOf(listWorkspacesOptions.includeCount()));
      }
      if (listWorkspacesOptions.sort() != null) {
        builder.query("sort", listWorkspacesOptions.sort());
      }
      if (listWorkspacesOptions.cursor() != null) {
        builder.query("cursor", listWorkspacesOptions.cursor());
      }
      if (listWorkspacesOptions.includeAudit() != null) {
        builder.query("include_audit", String.valueOf(listWorkspacesOptions.includeAudit()));
      }
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(WorkspaceCollection.class));
  }

  /**
   * List workspaces.
   *
   * List the workspaces associated with a Watson Assistant service instance.
   *
   * This operation is limited to 500 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @return a {@link ServiceCall} with a response type of {@link WorkspaceCollection}
   */
  public ServiceCall<WorkspaceCollection> listWorkspaces() {
    return listWorkspaces(null);
  }

  /**
   * Update workspace.
   *
   * Update an existing workspace with new or modified data. You must provide component objects defining the content of
   * the updated workspace.
   *
   * This operation is limited to 30 request per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param updateWorkspaceOptions the {@link UpdateWorkspaceOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Workspace}
   */
  public ServiceCall<Workspace> updateWorkspace(UpdateWorkspaceOptions updateWorkspaceOptions) {
    Validator.notNull(updateWorkspaceOptions, "updateWorkspaceOptions cannot be null");
    String[] pathSegments = { "v1/workspaces" };
    String[] pathParameters = { updateWorkspaceOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=updateWorkspace");
    if (updateWorkspaceOptions.append() != null) {
      builder.query("append", String.valueOf(updateWorkspaceOptions.append()));
    }
    final JsonObject contentJson = new JsonObject();
    if (updateWorkspaceOptions.name() != null) {
      contentJson.addProperty("name", updateWorkspaceOptions.name());
    }
    if (updateWorkspaceOptions.description() != null) {
      contentJson.addProperty("description", updateWorkspaceOptions.description());
    }
    if (updateWorkspaceOptions.language() != null) {
      contentJson.addProperty("language", updateWorkspaceOptions.language());
    }
    if (updateWorkspaceOptions.intents() != null) {
      contentJson.add("intents", GsonSingleton.getGson().toJsonTree(updateWorkspaceOptions.intents()));
    }
    if (updateWorkspaceOptions.entities() != null) {
      contentJson.add("entities", GsonSingleton.getGson().toJsonTree(updateWorkspaceOptions.entities()));
    }
    if (updateWorkspaceOptions.dialogNodes() != null) {
      contentJson.add("dialog_nodes", GsonSingleton.getGson().toJsonTree(updateWorkspaceOptions.dialogNodes()));
    }
    if (updateWorkspaceOptions.counterexamples() != null) {
      contentJson.add("counterexamples", GsonSingleton.getGson().toJsonTree(updateWorkspaceOptions.counterexamples()));
    }
    if (updateWorkspaceOptions.metadata() != null) {
      contentJson.add("metadata", GsonSingleton.getGson().toJsonTree(updateWorkspaceOptions.metadata()));
    }
    if (updateWorkspaceOptions.learningOptOut() != null) {
      contentJson.addProperty("learning_opt_out", updateWorkspaceOptions.learningOptOut());
    }
    if (updateWorkspaceOptions.systemSettings() != null) {
      contentJson.add("system_settings", GsonSingleton.getGson().toJsonTree(updateWorkspaceOptions.systemSettings()));
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Workspace.class));
  }

  /**
   * Create intent.
   *
   * Create a new intent.
   *
   * This operation is limited to 2000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param createIntentOptions the {@link CreateIntentOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Intent}
   */
  public ServiceCall<Intent> createIntent(CreateIntentOptions createIntentOptions) {
    Validator.notNull(createIntentOptions, "createIntentOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "intents" };
    String[] pathParameters = { createIntentOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=createIntent");
    final JsonObject contentJson = new JsonObject();
    contentJson.addProperty("intent", createIntentOptions.intent());
    if (createIntentOptions.description() != null) {
      contentJson.addProperty("description", createIntentOptions.description());
    }
    if (createIntentOptions.examples() != null) {
      contentJson.add("examples", GsonSingleton.getGson().toJsonTree(createIntentOptions.examples()));
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Intent.class));
  }

  /**
   * Delete intent.
   *
   * Delete an intent from a workspace.
   *
   * This operation is limited to 2000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param deleteIntentOptions the {@link DeleteIntentOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteIntent(DeleteIntentOptions deleteIntentOptions) {
    Validator.notNull(deleteIntentOptions, "deleteIntentOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "intents" };
    String[] pathParameters = { deleteIntentOptions.workspaceId(), deleteIntentOptions.intent() };
    RequestBuilder builder = RequestBuilder.delete(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=deleteIntent");
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Get intent.
   *
   * Get information about an intent, optionally including all intent content.
   *
   * With **export**=`false`, this operation is limited to 6000 requests per 5 minutes. With **export**=`true`, the
   * limit is 400 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param getIntentOptions the {@link GetIntentOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link IntentExport}
   */
  public ServiceCall<IntentExport> getIntent(GetIntentOptions getIntentOptions) {
    Validator.notNull(getIntentOptions, "getIntentOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "intents" };
    String[] pathParameters = { getIntentOptions.workspaceId(), getIntentOptions.intent() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=getIntent");
    if (getIntentOptions.export() != null) {
      builder.query("export", String.valueOf(getIntentOptions.export()));
    }
    if (getIntentOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(getIntentOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(IntentExport.class));
  }

  /**
   * List intents.
   *
   * List the intents for a workspace.
   *
   * With **export**=`false`, this operation is limited to 2000 requests per 30 minutes. With **export**=`true`, the
   * limit is 400 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param listIntentsOptions the {@link ListIntentsOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link IntentCollection}
   */
  public ServiceCall<IntentCollection> listIntents(ListIntentsOptions listIntentsOptions) {
    Validator.notNull(listIntentsOptions, "listIntentsOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "intents" };
    String[] pathParameters = { listIntentsOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=listIntents");
    if (listIntentsOptions.export() != null) {
      builder.query("export", String.valueOf(listIntentsOptions.export()));
    }
    if (listIntentsOptions.pageLimit() != null) {
      builder.query("page_limit", String.valueOf(listIntentsOptions.pageLimit()));
    }
    if (listIntentsOptions.includeCount() != null) {
      builder.query("include_count", String.valueOf(listIntentsOptions.includeCount()));
    }
    if (listIntentsOptions.sort() != null) {
      builder.query("sort", listIntentsOptions.sort());
    }
    if (listIntentsOptions.cursor() != null) {
      builder.query("cursor", listIntentsOptions.cursor());
    }
    if (listIntentsOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(listIntentsOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(IntentCollection.class));
  }

  /**
   * Update intent.
   *
   * Update an existing intent with new or modified data. You must provide component objects defining the content of the
   * updated intent.
   *
   * This operation is limited to 2000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param updateIntentOptions the {@link UpdateIntentOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Intent}
   */
  public ServiceCall<Intent> updateIntent(UpdateIntentOptions updateIntentOptions) {
    Validator.notNull(updateIntentOptions, "updateIntentOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "intents" };
    String[] pathParameters = { updateIntentOptions.workspaceId(), updateIntentOptions.intent() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=updateIntent");
    final JsonObject contentJson = new JsonObject();
    if (updateIntentOptions.newIntent() != null) {
      contentJson.addProperty("intent", updateIntentOptions.newIntent());
    }
    if (updateIntentOptions.newExamples() != null) {
      contentJson.add("examples", GsonSingleton.getGson().toJsonTree(updateIntentOptions.newExamples()));
    }
    if (updateIntentOptions.newDescription() != null) {
      contentJson.addProperty("description", updateIntentOptions.newDescription());
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Intent.class));
  }

  /**
   * Create user input example.
   *
   * Add a new user input example to an intent.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param createExampleOptions the {@link CreateExampleOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Example}
   */
  public ServiceCall<Example> createExample(CreateExampleOptions createExampleOptions) {
    Validator.notNull(createExampleOptions, "createExampleOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "intents", "examples" };
    String[] pathParameters = { createExampleOptions.workspaceId(), createExampleOptions.intent() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=createExample");
    final JsonObject contentJson = new JsonObject();
    contentJson.addProperty("text", createExampleOptions.text());
    if (createExampleOptions.mentions() != null) {
      contentJson.add("mentions", GsonSingleton.getGson().toJsonTree(createExampleOptions.mentions()));
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Example.class));
  }

  /**
   * Delete user input example.
   *
   * Delete a user input example from an intent.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param deleteExampleOptions the {@link DeleteExampleOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteExample(DeleteExampleOptions deleteExampleOptions) {
    Validator.notNull(deleteExampleOptions, "deleteExampleOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "intents", "examples" };
    String[] pathParameters = { deleteExampleOptions.workspaceId(), deleteExampleOptions.intent(), deleteExampleOptions
        .text() };
    RequestBuilder builder = RequestBuilder.delete(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=deleteExample");
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Get user input example.
   *
   * Get information about a user input example.
   *
   * This operation is limited to 6000 requests per 5 minutes. For more information, see **Rate limiting**.
   *
   * @param getExampleOptions the {@link GetExampleOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Example}
   */
  public ServiceCall<Example> getExample(GetExampleOptions getExampleOptions) {
    Validator.notNull(getExampleOptions, "getExampleOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "intents", "examples" };
    String[] pathParameters = { getExampleOptions.workspaceId(), getExampleOptions.intent(), getExampleOptions.text() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=getExample");
    if (getExampleOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(getExampleOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Example.class));
  }

  /**
   * List user input examples.
   *
   * List the user input examples for an intent, optionally including contextual entity mentions.
   *
   * This operation is limited to 2500 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param listExamplesOptions the {@link ListExamplesOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link ExampleCollection}
   */
  public ServiceCall<ExampleCollection> listExamples(ListExamplesOptions listExamplesOptions) {
    Validator.notNull(listExamplesOptions, "listExamplesOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "intents", "examples" };
    String[] pathParameters = { listExamplesOptions.workspaceId(), listExamplesOptions.intent() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=listExamples");
    if (listExamplesOptions.pageLimit() != null) {
      builder.query("page_limit", String.valueOf(listExamplesOptions.pageLimit()));
    }
    if (listExamplesOptions.includeCount() != null) {
      builder.query("include_count", String.valueOf(listExamplesOptions.includeCount()));
    }
    if (listExamplesOptions.sort() != null) {
      builder.query("sort", listExamplesOptions.sort());
    }
    if (listExamplesOptions.cursor() != null) {
      builder.query("cursor", listExamplesOptions.cursor());
    }
    if (listExamplesOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(listExamplesOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(ExampleCollection.class));
  }

  /**
   * Update user input example.
   *
   * Update the text of a user input example.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param updateExampleOptions the {@link UpdateExampleOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Example}
   */
  public ServiceCall<Example> updateExample(UpdateExampleOptions updateExampleOptions) {
    Validator.notNull(updateExampleOptions, "updateExampleOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "intents", "examples" };
    String[] pathParameters = { updateExampleOptions.workspaceId(), updateExampleOptions.intent(), updateExampleOptions
        .text() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=updateExample");
    final JsonObject contentJson = new JsonObject();
    if (updateExampleOptions.newText() != null) {
      contentJson.addProperty("text", updateExampleOptions.newText());
    }
    if (updateExampleOptions.newMentions() != null) {
      contentJson.add("mentions", GsonSingleton.getGson().toJsonTree(updateExampleOptions.newMentions()));
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Example.class));
  }

  /**
   * Create counterexample.
   *
   * Add a new counterexample to a workspace. Counterexamples are examples that have been marked as irrelevant input.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param createCounterexampleOptions the {@link CreateCounterexampleOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Counterexample}
   */
  public ServiceCall<Counterexample> createCounterexample(CreateCounterexampleOptions createCounterexampleOptions) {
    Validator.notNull(createCounterexampleOptions, "createCounterexampleOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "counterexamples" };
    String[] pathParameters = { createCounterexampleOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=createCounterexample");
    final JsonObject contentJson = new JsonObject();
    contentJson.addProperty("text", createCounterexampleOptions.text());
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Counterexample.class));
  }

  /**
   * Delete counterexample.
   *
   * Delete a counterexample from a workspace. Counterexamples are examples that have been marked as irrelevant input.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param deleteCounterexampleOptions the {@link DeleteCounterexampleOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteCounterexample(DeleteCounterexampleOptions deleteCounterexampleOptions) {
    Validator.notNull(deleteCounterexampleOptions, "deleteCounterexampleOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "counterexamples" };
    String[] pathParameters = { deleteCounterexampleOptions.workspaceId(), deleteCounterexampleOptions.text() };
    RequestBuilder builder = RequestBuilder.delete(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=deleteCounterexample");
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Get counterexample.
   *
   * Get information about a counterexample. Counterexamples are examples that have been marked as irrelevant input.
   *
   * This operation is limited to 6000 requests per 5 minutes. For more information, see **Rate limiting**.
   *
   * @param getCounterexampleOptions the {@link GetCounterexampleOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Counterexample}
   */
  public ServiceCall<Counterexample> getCounterexample(GetCounterexampleOptions getCounterexampleOptions) {
    Validator.notNull(getCounterexampleOptions, "getCounterexampleOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "counterexamples" };
    String[] pathParameters = { getCounterexampleOptions.workspaceId(), getCounterexampleOptions.text() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=getCounterexample");
    if (getCounterexampleOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(getCounterexampleOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Counterexample.class));
  }

  /**
   * List counterexamples.
   *
   * List the counterexamples for a workspace. Counterexamples are examples that have been marked as irrelevant input.
   *
   * This operation is limited to 2500 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param listCounterexamplesOptions the {@link ListCounterexamplesOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link CounterexampleCollection}
   */
  public ServiceCall<CounterexampleCollection> listCounterexamples(
      ListCounterexamplesOptions listCounterexamplesOptions) {
    Validator.notNull(listCounterexamplesOptions, "listCounterexamplesOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "counterexamples" };
    String[] pathParameters = { listCounterexamplesOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=listCounterexamples");
    if (listCounterexamplesOptions.pageLimit() != null) {
      builder.query("page_limit", String.valueOf(listCounterexamplesOptions.pageLimit()));
    }
    if (listCounterexamplesOptions.includeCount() != null) {
      builder.query("include_count", String.valueOf(listCounterexamplesOptions.includeCount()));
    }
    if (listCounterexamplesOptions.sort() != null) {
      builder.query("sort", listCounterexamplesOptions.sort());
    }
    if (listCounterexamplesOptions.cursor() != null) {
      builder.query("cursor", listCounterexamplesOptions.cursor());
    }
    if (listCounterexamplesOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(listCounterexamplesOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(CounterexampleCollection.class));
  }

  /**
   * Update counterexample.
   *
   * Update the text of a counterexample. Counterexamples are examples that have been marked as irrelevant input.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param updateCounterexampleOptions the {@link UpdateCounterexampleOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Counterexample}
   */
  public ServiceCall<Counterexample> updateCounterexample(UpdateCounterexampleOptions updateCounterexampleOptions) {
    Validator.notNull(updateCounterexampleOptions, "updateCounterexampleOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "counterexamples" };
    String[] pathParameters = { updateCounterexampleOptions.workspaceId(), updateCounterexampleOptions.text() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=updateCounterexample");
    final JsonObject contentJson = new JsonObject();
    if (updateCounterexampleOptions.newText() != null) {
      contentJson.addProperty("text", updateCounterexampleOptions.newText());
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Counterexample.class));
  }

  /**
   * Create entity.
   *
   * Create a new entity, or enable a system entity.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param createEntityOptions the {@link CreateEntityOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Entity}
   */
  public ServiceCall<Entity> createEntity(CreateEntityOptions createEntityOptions) {
    Validator.notNull(createEntityOptions, "createEntityOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities" };
    String[] pathParameters = { createEntityOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=createEntity");
    final JsonObject contentJson = new JsonObject();
    contentJson.addProperty("entity", createEntityOptions.entity());
    if (createEntityOptions.description() != null) {
      contentJson.addProperty("description", createEntityOptions.description());
    }
    if (createEntityOptions.metadata() != null) {
      contentJson.add("metadata", GsonSingleton.getGson().toJsonTree(createEntityOptions.metadata()));
    }
    if (createEntityOptions.values() != null) {
      contentJson.add("values", GsonSingleton.getGson().toJsonTree(createEntityOptions.values()));
    }
    if (createEntityOptions.fuzzyMatch() != null) {
      contentJson.addProperty("fuzzy_match", createEntityOptions.fuzzyMatch());
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Entity.class));
  }

  /**
   * Delete entity.
   *
   * Delete an entity from a workspace, or disable a system entity.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param deleteEntityOptions the {@link DeleteEntityOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteEntity(DeleteEntityOptions deleteEntityOptions) {
    Validator.notNull(deleteEntityOptions, "deleteEntityOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities" };
    String[] pathParameters = { deleteEntityOptions.workspaceId(), deleteEntityOptions.entity() };
    RequestBuilder builder = RequestBuilder.delete(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=deleteEntity");
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Get entity.
   *
   * Get information about an entity, optionally including all entity content.
   *
   * With **export**=`false`, this operation is limited to 6000 requests per 5 minutes. With **export**=`true`, the
   * limit is 200 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param getEntityOptions the {@link GetEntityOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link EntityExport}
   */
  public ServiceCall<EntityExport> getEntity(GetEntityOptions getEntityOptions) {
    Validator.notNull(getEntityOptions, "getEntityOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities" };
    String[] pathParameters = { getEntityOptions.workspaceId(), getEntityOptions.entity() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=getEntity");
    if (getEntityOptions.export() != null) {
      builder.query("export", String.valueOf(getEntityOptions.export()));
    }
    if (getEntityOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(getEntityOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(EntityExport.class));
  }

  /**
   * List entities.
   *
   * List the entities for a workspace.
   *
   * With **export**=`false`, this operation is limited to 1000 requests per 30 minutes. With **export**=`true`, the
   * limit is 200 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param listEntitiesOptions the {@link ListEntitiesOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link EntityCollection}
   */
  public ServiceCall<EntityCollection> listEntities(ListEntitiesOptions listEntitiesOptions) {
    Validator.notNull(listEntitiesOptions, "listEntitiesOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities" };
    String[] pathParameters = { listEntitiesOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=listEntities");
    if (listEntitiesOptions.export() != null) {
      builder.query("export", String.valueOf(listEntitiesOptions.export()));
    }
    if (listEntitiesOptions.pageLimit() != null) {
      builder.query("page_limit", String.valueOf(listEntitiesOptions.pageLimit()));
    }
    if (listEntitiesOptions.includeCount() != null) {
      builder.query("include_count", String.valueOf(listEntitiesOptions.includeCount()));
    }
    if (listEntitiesOptions.sort() != null) {
      builder.query("sort", listEntitiesOptions.sort());
    }
    if (listEntitiesOptions.cursor() != null) {
      builder.query("cursor", listEntitiesOptions.cursor());
    }
    if (listEntitiesOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(listEntitiesOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(EntityCollection.class));
  }

  /**
   * Update entity.
   *
   * Update an existing entity with new or modified data. You must provide component objects defining the content of the
   * updated entity.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param updateEntityOptions the {@link UpdateEntityOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Entity}
   */
  public ServiceCall<Entity> updateEntity(UpdateEntityOptions updateEntityOptions) {
    Validator.notNull(updateEntityOptions, "updateEntityOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities" };
    String[] pathParameters = { updateEntityOptions.workspaceId(), updateEntityOptions.entity() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=updateEntity");
    final JsonObject contentJson = new JsonObject();
    if (updateEntityOptions.newFuzzyMatch() != null) {
      contentJson.addProperty("fuzzy_match", updateEntityOptions.newFuzzyMatch());
    }
    if (updateEntityOptions.newEntity() != null) {
      contentJson.addProperty("entity", updateEntityOptions.newEntity());
    }
    if (updateEntityOptions.newMetadata() != null) {
      contentJson.add("metadata", GsonSingleton.getGson().toJsonTree(updateEntityOptions.newMetadata()));
    }
    if (updateEntityOptions.newValues() != null) {
      contentJson.add("values", GsonSingleton.getGson().toJsonTree(updateEntityOptions.newValues()));
    }
    if (updateEntityOptions.newDescription() != null) {
      contentJson.addProperty("description", updateEntityOptions.newDescription());
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Entity.class));
  }

  /**
   * List entity mentions.
   *
   * List mentions for a contextual entity. An entity mention is an occurrence of a contextual entity in the context of
   * an intent user input example.
   *
   * This operation is limited to 200 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param listMentionsOptions the {@link ListMentionsOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link EntityMentionCollection}
   */
  public ServiceCall<EntityMentionCollection> listMentions(ListMentionsOptions listMentionsOptions) {
    Validator.notNull(listMentionsOptions, "listMentionsOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "mentions" };
    String[] pathParameters = { listMentionsOptions.workspaceId(), listMentionsOptions.entity() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=listMentions");
    if (listMentionsOptions.export() != null) {
      builder.query("export", String.valueOf(listMentionsOptions.export()));
    }
    if (listMentionsOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(listMentionsOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(EntityMentionCollection.class));
  }

  /**
   * Add entity value.
   *
   * Create a new value for an entity.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param createValueOptions the {@link CreateValueOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Value}
   */
  public ServiceCall<Value> createValue(CreateValueOptions createValueOptions) {
    Validator.notNull(createValueOptions, "createValueOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "values" };
    String[] pathParameters = { createValueOptions.workspaceId(), createValueOptions.entity() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=createValue");
    final JsonObject contentJson = new JsonObject();
    contentJson.addProperty("value", createValueOptions.value());
    if (createValueOptions.metadata() != null) {
      contentJson.add("metadata", GsonSingleton.getGson().toJsonTree(createValueOptions.metadata()));
    }
    if (createValueOptions.synonyms() != null) {
      contentJson.add("synonyms", GsonSingleton.getGson().toJsonTree(createValueOptions.synonyms()));
    }
    if (createValueOptions.patterns() != null) {
      contentJson.add("patterns", GsonSingleton.getGson().toJsonTree(createValueOptions.patterns()));
    }
    if (createValueOptions.valueType() != null) {
      contentJson.addProperty("type", createValueOptions.valueType());
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Value.class));
  }

  /**
   * Delete entity value.
   *
   * Delete a value from an entity.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param deleteValueOptions the {@link DeleteValueOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteValue(DeleteValueOptions deleteValueOptions) {
    Validator.notNull(deleteValueOptions, "deleteValueOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "values" };
    String[] pathParameters = { deleteValueOptions.workspaceId(), deleteValueOptions.entity(), deleteValueOptions
        .value() };
    RequestBuilder builder = RequestBuilder.delete(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=deleteValue");
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Get entity value.
   *
   * Get information about an entity value.
   *
   * This operation is limited to 6000 requests per 5 minutes. For more information, see **Rate limiting**.
   *
   * @param getValueOptions the {@link GetValueOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link ValueExport}
   */
  public ServiceCall<ValueExport> getValue(GetValueOptions getValueOptions) {
    Validator.notNull(getValueOptions, "getValueOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "values" };
    String[] pathParameters = { getValueOptions.workspaceId(), getValueOptions.entity(), getValueOptions.value() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=getValue");
    if (getValueOptions.export() != null) {
      builder.query("export", String.valueOf(getValueOptions.export()));
    }
    if (getValueOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(getValueOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(ValueExport.class));
  }

  /**
   * List entity values.
   *
   * List the values for an entity.
   *
   * This operation is limited to 2500 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param listValuesOptions the {@link ListValuesOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link ValueCollection}
   */
  public ServiceCall<ValueCollection> listValues(ListValuesOptions listValuesOptions) {
    Validator.notNull(listValuesOptions, "listValuesOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "values" };
    String[] pathParameters = { listValuesOptions.workspaceId(), listValuesOptions.entity() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=listValues");
    if (listValuesOptions.export() != null) {
      builder.query("export", String.valueOf(listValuesOptions.export()));
    }
    if (listValuesOptions.pageLimit() != null) {
      builder.query("page_limit", String.valueOf(listValuesOptions.pageLimit()));
    }
    if (listValuesOptions.includeCount() != null) {
      builder.query("include_count", String.valueOf(listValuesOptions.includeCount()));
    }
    if (listValuesOptions.sort() != null) {
      builder.query("sort", listValuesOptions.sort());
    }
    if (listValuesOptions.cursor() != null) {
      builder.query("cursor", listValuesOptions.cursor());
    }
    if (listValuesOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(listValuesOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(ValueCollection.class));
  }

  /**
   * Update entity value.
   *
   * Update an existing entity value with new or modified data. You must provide component objects defining the content
   * of the updated entity value.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param updateValueOptions the {@link UpdateValueOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Value}
   */
  public ServiceCall<Value> updateValue(UpdateValueOptions updateValueOptions) {
    Validator.notNull(updateValueOptions, "updateValueOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "values" };
    String[] pathParameters = { updateValueOptions.workspaceId(), updateValueOptions.entity(), updateValueOptions
        .value() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=updateValue");
    final JsonObject contentJson = new JsonObject();
    if (updateValueOptions.newSynonyms() != null) {
      contentJson.add("synonyms", GsonSingleton.getGson().toJsonTree(updateValueOptions.newSynonyms()));
    }
    if (updateValueOptions.valueType() != null) {
      contentJson.addProperty("type", updateValueOptions.valueType());
    }
    if (updateValueOptions.newMetadata() != null) {
      contentJson.add("metadata", GsonSingleton.getGson().toJsonTree(updateValueOptions.newMetadata()));
    }
    if (updateValueOptions.newPatterns() != null) {
      contentJson.add("patterns", GsonSingleton.getGson().toJsonTree(updateValueOptions.newPatterns()));
    }
    if (updateValueOptions.newValue() != null) {
      contentJson.addProperty("value", updateValueOptions.newValue());
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Value.class));
  }

  /**
   * Add entity value synonym.
   *
   * Add a new synonym to an entity value.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param createSynonymOptions the {@link CreateSynonymOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Synonym}
   */
  public ServiceCall<Synonym> createSynonym(CreateSynonymOptions createSynonymOptions) {
    Validator.notNull(createSynonymOptions, "createSynonymOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "values", "synonyms" };
    String[] pathParameters = { createSynonymOptions.workspaceId(), createSynonymOptions.entity(), createSynonymOptions
        .value() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=createSynonym");
    final JsonObject contentJson = new JsonObject();
    contentJson.addProperty("synonym", createSynonymOptions.synonym());
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Synonym.class));
  }

  /**
   * Delete entity value synonym.
   *
   * Delete a synonym from an entity value.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param deleteSynonymOptions the {@link DeleteSynonymOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteSynonym(DeleteSynonymOptions deleteSynonymOptions) {
    Validator.notNull(deleteSynonymOptions, "deleteSynonymOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "values", "synonyms" };
    String[] pathParameters = { deleteSynonymOptions.workspaceId(), deleteSynonymOptions.entity(), deleteSynonymOptions
        .value(), deleteSynonymOptions.synonym() };
    RequestBuilder builder = RequestBuilder.delete(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=deleteSynonym");
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Get entity value synonym.
   *
   * Get information about a synonym of an entity value.
   *
   * This operation is limited to 6000 requests per 5 minutes. For more information, see **Rate limiting**.
   *
   * @param getSynonymOptions the {@link GetSynonymOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Synonym}
   */
  public ServiceCall<Synonym> getSynonym(GetSynonymOptions getSynonymOptions) {
    Validator.notNull(getSynonymOptions, "getSynonymOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "values", "synonyms" };
    String[] pathParameters = { getSynonymOptions.workspaceId(), getSynonymOptions.entity(), getSynonymOptions.value(),
        getSynonymOptions.synonym() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=getSynonym");
    if (getSynonymOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(getSynonymOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Synonym.class));
  }

  /**
   * List entity value synonyms.
   *
   * List the synonyms for an entity value.
   *
   * This operation is limited to 2500 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param listSynonymsOptions the {@link ListSynonymsOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link SynonymCollection}
   */
  public ServiceCall<SynonymCollection> listSynonyms(ListSynonymsOptions listSynonymsOptions) {
    Validator.notNull(listSynonymsOptions, "listSynonymsOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "values", "synonyms" };
    String[] pathParameters = { listSynonymsOptions.workspaceId(), listSynonymsOptions.entity(), listSynonymsOptions
        .value() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=listSynonyms");
    if (listSynonymsOptions.pageLimit() != null) {
      builder.query("page_limit", String.valueOf(listSynonymsOptions.pageLimit()));
    }
    if (listSynonymsOptions.includeCount() != null) {
      builder.query("include_count", String.valueOf(listSynonymsOptions.includeCount()));
    }
    if (listSynonymsOptions.sort() != null) {
      builder.query("sort", listSynonymsOptions.sort());
    }
    if (listSynonymsOptions.cursor() != null) {
      builder.query("cursor", listSynonymsOptions.cursor());
    }
    if (listSynonymsOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(listSynonymsOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(SynonymCollection.class));
  }

  /**
   * Update entity value synonym.
   *
   * Update an existing entity value synonym with new text.
   *
   * This operation is limited to 1000 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param updateSynonymOptions the {@link UpdateSynonymOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link Synonym}
   */
  public ServiceCall<Synonym> updateSynonym(UpdateSynonymOptions updateSynonymOptions) {
    Validator.notNull(updateSynonymOptions, "updateSynonymOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "entities", "values", "synonyms" };
    String[] pathParameters = { updateSynonymOptions.workspaceId(), updateSynonymOptions.entity(), updateSynonymOptions
        .value(), updateSynonymOptions.synonym() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=updateSynonym");
    final JsonObject contentJson = new JsonObject();
    if (updateSynonymOptions.newSynonym() != null) {
      contentJson.addProperty("synonym", updateSynonymOptions.newSynonym());
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Synonym.class));
  }

  /**
   * Create dialog node.
   *
   * Create a new dialog node.
   *
   * This operation is limited to 500 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param createDialogNodeOptions the {@link CreateDialogNodeOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link DialogNode}
   */
  public ServiceCall<DialogNode> createDialogNode(CreateDialogNodeOptions createDialogNodeOptions) {
    Validator.notNull(createDialogNodeOptions, "createDialogNodeOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "dialog_nodes" };
    String[] pathParameters = { createDialogNodeOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=createDialogNode");
    final JsonObject contentJson = new JsonObject();
    contentJson.addProperty("dialog_node", createDialogNodeOptions.dialogNode());
    if (createDialogNodeOptions.description() != null) {
      contentJson.addProperty("description", createDialogNodeOptions.description());
    }
    if (createDialogNodeOptions.conditions() != null) {
      contentJson.addProperty("conditions", createDialogNodeOptions.conditions());
    }
    if (createDialogNodeOptions.parent() != null) {
      contentJson.addProperty("parent", createDialogNodeOptions.parent());
    }
    if (createDialogNodeOptions.previousSibling() != null) {
      contentJson.addProperty("previous_sibling", createDialogNodeOptions.previousSibling());
    }
    if (createDialogNodeOptions.output() != null) {
      contentJson.add("output", GsonSingleton.getGson().toJsonTree(createDialogNodeOptions.output()));
    }
    if (createDialogNodeOptions.context() != null) {
      contentJson.add("context", GsonSingleton.getGson().toJsonTree(createDialogNodeOptions.context()));
    }
    if (createDialogNodeOptions.metadata() != null) {
      contentJson.add("metadata", GsonSingleton.getGson().toJsonTree(createDialogNodeOptions.metadata()));
    }
    if (createDialogNodeOptions.nextStep() != null) {
      contentJson.add("next_step", GsonSingleton.getGson().toJsonTree(createDialogNodeOptions.nextStep()));
    }
    if (createDialogNodeOptions.actions() != null) {
      contentJson.add("actions", GsonSingleton.getGson().toJsonTree(createDialogNodeOptions.actions()));
    }
    if (createDialogNodeOptions.title() != null) {
      contentJson.addProperty("title", createDialogNodeOptions.title());
    }
    if (createDialogNodeOptions.nodeType() != null) {
      contentJson.addProperty("type", createDialogNodeOptions.nodeType());
    }
    if (createDialogNodeOptions.eventName() != null) {
      contentJson.addProperty("event_name", createDialogNodeOptions.eventName());
    }
    if (createDialogNodeOptions.variable() != null) {
      contentJson.addProperty("variable", createDialogNodeOptions.variable());
    }
    if (createDialogNodeOptions.digressIn() != null) {
      contentJson.addProperty("digress_in", createDialogNodeOptions.digressIn());
    }
    if (createDialogNodeOptions.digressOut() != null) {
      contentJson.addProperty("digress_out", createDialogNodeOptions.digressOut());
    }
    if (createDialogNodeOptions.digressOutSlots() != null) {
      contentJson.addProperty("digress_out_slots", createDialogNodeOptions.digressOutSlots());
    }
    if (createDialogNodeOptions.userLabel() != null) {
      contentJson.addProperty("user_label", createDialogNodeOptions.userLabel());
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(DialogNode.class));
  }

  /**
   * Delete dialog node.
   *
   * Delete a dialog node from a workspace.
   *
   * This operation is limited to 500 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param deleteDialogNodeOptions the {@link DeleteDialogNodeOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteDialogNode(DeleteDialogNodeOptions deleteDialogNodeOptions) {
    Validator.notNull(deleteDialogNodeOptions, "deleteDialogNodeOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "dialog_nodes" };
    String[] pathParameters = { deleteDialogNodeOptions.workspaceId(), deleteDialogNodeOptions.dialogNode() };
    RequestBuilder builder = RequestBuilder.delete(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=deleteDialogNode");
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Get dialog node.
   *
   * Get information about a dialog node.
   *
   * This operation is limited to 6000 requests per 5 minutes. For more information, see **Rate limiting**.
   *
   * @param getDialogNodeOptions the {@link GetDialogNodeOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link DialogNode}
   */
  public ServiceCall<DialogNode> getDialogNode(GetDialogNodeOptions getDialogNodeOptions) {
    Validator.notNull(getDialogNodeOptions, "getDialogNodeOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "dialog_nodes" };
    String[] pathParameters = { getDialogNodeOptions.workspaceId(), getDialogNodeOptions.dialogNode() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=getDialogNode");
    if (getDialogNodeOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(getDialogNodeOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(DialogNode.class));
  }

  /**
   * List dialog nodes.
   *
   * List the dialog nodes for a workspace.
   *
   * This operation is limited to 2500 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param listDialogNodesOptions the {@link ListDialogNodesOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link DialogNodeCollection}
   */
  public ServiceCall<DialogNodeCollection> listDialogNodes(ListDialogNodesOptions listDialogNodesOptions) {
    Validator.notNull(listDialogNodesOptions, "listDialogNodesOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "dialog_nodes" };
    String[] pathParameters = { listDialogNodesOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=listDialogNodes");
    if (listDialogNodesOptions.pageLimit() != null) {
      builder.query("page_limit", String.valueOf(listDialogNodesOptions.pageLimit()));
    }
    if (listDialogNodesOptions.includeCount() != null) {
      builder.query("include_count", String.valueOf(listDialogNodesOptions.includeCount()));
    }
    if (listDialogNodesOptions.sort() != null) {
      builder.query("sort", listDialogNodesOptions.sort());
    }
    if (listDialogNodesOptions.cursor() != null) {
      builder.query("cursor", listDialogNodesOptions.cursor());
    }
    if (listDialogNodesOptions.includeAudit() != null) {
      builder.query("include_audit", String.valueOf(listDialogNodesOptions.includeAudit()));
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(DialogNodeCollection.class));
  }

  /**
   * Update dialog node.
   *
   * Update an existing dialog node with new or modified data.
   *
   * This operation is limited to 500 requests per 30 minutes. For more information, see **Rate limiting**.
   *
   * @param updateDialogNodeOptions the {@link UpdateDialogNodeOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link DialogNode}
   */
  public ServiceCall<DialogNode> updateDialogNode(UpdateDialogNodeOptions updateDialogNodeOptions) {
    Validator.notNull(updateDialogNodeOptions, "updateDialogNodeOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "dialog_nodes" };
    String[] pathParameters = { updateDialogNodeOptions.workspaceId(), updateDialogNodeOptions.dialogNode() };
    RequestBuilder builder = RequestBuilder.post(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=updateDialogNode");
    final JsonObject contentJson = new JsonObject();
    if (updateDialogNodeOptions.nodeType() != null) {
      contentJson.addProperty("type", updateDialogNodeOptions.nodeType());
    }
    if (updateDialogNodeOptions.newActions() != null) {
      contentJson.add("actions", GsonSingleton.getGson().toJsonTree(updateDialogNodeOptions.newActions()));
    }
    if (updateDialogNodeOptions.newConditions() != null) {
      contentJson.addProperty("conditions", updateDialogNodeOptions.newConditions());
    }
    if (updateDialogNodeOptions.newContext() != null) {
      contentJson.add("context", GsonSingleton.getGson().toJsonTree(updateDialogNodeOptions.newContext()));
    }
    if (updateDialogNodeOptions.newPreviousSibling() != null) {
      contentJson.addProperty("previous_sibling", updateDialogNodeOptions.newPreviousSibling());
    }
    if (updateDialogNodeOptions.newVariable() != null) {
      contentJson.addProperty("variable", updateDialogNodeOptions.newVariable());
    }
    if (updateDialogNodeOptions.newUserLabel() != null) {
      contentJson.addProperty("user_label", updateDialogNodeOptions.newUserLabel());
    }
    if (updateDialogNodeOptions.newMetadata() != null) {
      contentJson.add("metadata", GsonSingleton.getGson().toJsonTree(updateDialogNodeOptions.newMetadata()));
    }
    if (updateDialogNodeOptions.newTitle() != null) {
      contentJson.addProperty("title", updateDialogNodeOptions.newTitle());
    }
    if (updateDialogNodeOptions.newDescription() != null) {
      contentJson.addProperty("description", updateDialogNodeOptions.newDescription());
    }
    if (updateDialogNodeOptions.newDigressOut() != null) {
      contentJson.addProperty("digress_out", updateDialogNodeOptions.newDigressOut());
    }
    if (updateDialogNodeOptions.newEventName() != null) {
      contentJson.addProperty("event_name", updateDialogNodeOptions.newEventName());
    }
    if (updateDialogNodeOptions.newDigressOutSlots() != null) {
      contentJson.addProperty("digress_out_slots", updateDialogNodeOptions.newDigressOutSlots());
    }
    if (updateDialogNodeOptions.newNextStep() != null) {
      contentJson.add("next_step", GsonSingleton.getGson().toJsonTree(updateDialogNodeOptions.newNextStep()));
    }
    if (updateDialogNodeOptions.newDigressIn() != null) {
      contentJson.addProperty("digress_in", updateDialogNodeOptions.newDigressIn());
    }
    if (updateDialogNodeOptions.newOutput() != null) {
      contentJson.add("output", GsonSingleton.getGson().toJsonTree(updateDialogNodeOptions.newOutput()));
    }
    if (updateDialogNodeOptions.newParent() != null) {
      contentJson.addProperty("parent", updateDialogNodeOptions.newParent());
    }
    if (updateDialogNodeOptions.newDialogNode() != null) {
      contentJson.addProperty("dialog_node", updateDialogNodeOptions.newDialogNode());
    }
    builder.bodyJson(contentJson);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(DialogNode.class));
  }

  /**
   * List log events in all workspaces.
   *
   * List the events from the logs of all workspaces in the service instance.
   *
   * If **cursor** is not specified, this operation is limited to 40 requests per 30 minutes. If **cursor** is
   * specified, the limit is 120 requests per minute. For more information, see **Rate limiting**.
   *
   * @param listAllLogsOptions the {@link ListAllLogsOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link LogCollection}
   */
  public ServiceCall<LogCollection> listAllLogs(ListAllLogsOptions listAllLogsOptions) {
    Validator.notNull(listAllLogsOptions, "listAllLogsOptions cannot be null");
    String[] pathSegments = { "v1/logs" };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=listAllLogs");
    builder.query("filter", listAllLogsOptions.filter());
    if (listAllLogsOptions.sort() != null) {
      builder.query("sort", listAllLogsOptions.sort());
    }
    if (listAllLogsOptions.pageLimit() != null) {
      builder.query("page_limit", String.valueOf(listAllLogsOptions.pageLimit()));
    }
    if (listAllLogsOptions.cursor() != null) {
      builder.query("cursor", listAllLogsOptions.cursor());
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(LogCollection.class));
  }

  /**
   * List log events in a workspace.
   *
   * List the events from the log of a specific workspace.
   *
   * If **cursor** is not specified, this operation is limited to 40 requests per 30 minutes. If **cursor** is
   * specified, the limit is 120 requests per minute. For more information, see **Rate limiting**.
   *
   * @param listLogsOptions the {@link ListLogsOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link LogCollection}
   */
  public ServiceCall<LogCollection> listLogs(ListLogsOptions listLogsOptions) {
    Validator.notNull(listLogsOptions, "listLogsOptions cannot be null");
    String[] pathSegments = { "v1/workspaces", "logs" };
    String[] pathParameters = { listLogsOptions.workspaceId() };
    RequestBuilder builder = RequestBuilder.get(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
        pathParameters));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics", "service_name=conversation;service_version=v1;operation_id=listLogs");
    if (listLogsOptions.sort() != null) {
      builder.query("sort", listLogsOptions.sort());
    }
    if (listLogsOptions.filter() != null) {
      builder.query("filter", listLogsOptions.filter());
    }
    if (listLogsOptions.pageLimit() != null) {
      builder.query("page_limit", String.valueOf(listLogsOptions.pageLimit()));
    }
    if (listLogsOptions.cursor() != null) {
      builder.query("cursor", listLogsOptions.cursor());
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(LogCollection.class));
  }

  /**
   * Delete labeled data.
   *
   * Deletes all data associated with a specified customer ID. The method has no effect if no data is associated with
   * the customer ID.
   *
   * You associate a customer ID with data by passing the `X-Watson-Metadata` header with a request that passes data.
   * For more information about personal data and customer IDs, see [Information
   * security](https://cloud.ibm.com/docs/services/assistant/information-security.html).
   *
   * @param deleteUserDataOptions the {@link DeleteUserDataOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteUserData(DeleteUserDataOptions deleteUserDataOptions) {
    Validator.notNull(deleteUserDataOptions, "deleteUserDataOptions cannot be null");
    String[] pathSegments = { "v1/user_data" };
    RequestBuilder builder = RequestBuilder.delete(RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments));
    builder.query(VERSION, versionDate);
    builder.header("X-IBMCloud-SDK-Analytics",
        "service_name=conversation;service_version=v1;operation_id=deleteUserData");
    builder.query("customer_id", deleteUserDataOptions.customerId());
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Creates a version for a particular skill using its definition id.
   * @param definitionId definition Id of the workspace
   * @param description description for the snapshot(or version) to be created
   * @return a {@link ServiceCall} with a response type of Snapshot
   */
  public ServiceCall<Snapshot> createSnapshot(String definitionId , String description) {
    String[] pathSegments = { "v1/workers/definitions","snapshots" };
    String[] pathParameters = { definitionId };
    HttpUrl httpUrl = RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
            pathParameters);
    RequestBuilder builder = RequestBuilder.post(httpUrl);
    builder.query(VERSION,versionDate);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("description",description);
    builder.bodyJson(jsonObject);
    return createServiceCall(builder.build(),ResponseConverterUtils.getObject(Snapshot.class));
  }

  private ServiceCall<Void> deleteUtil(String[] pathSegments,String[] pathParameters) {
    HttpUrl httpUrl = RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
            pathParameters);
    RequestBuilder builder = RequestBuilder.delete(httpUrl);
    return createServiceCall(builder.build(),ResponseConverterUtils.getVoid());
  }

  /**
   * Removes the version from the workspace.
   * @param snapshot Snapshit model.
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteSnapshot(Snapshot snapshot) {
    String definitionId = snapshot.getWorkerDefinitionId();
    String snapShotId = snapshot.getId();
    String[] pathSegments = { "v1/workers/definitions","snapshots"};
    String[] pathParameters = { definitionId, snapShotId };
    return deleteUtil(pathSegments,pathParameters);
  }

  private  <T extends GenericModel> ServiceCall<T> getData(String[] pathSegments, String[] pathParameters , Class tClass) {
    return getData(pathSegments,pathParameters,null,tClass);
  }

  private  <T extends GenericModel> ServiceCall<T> getData(String[] pathSegments, String[] pathParameters, Map<String,String> params, Class tClass) {
    HttpUrl httpUrl = RequestBuilder.constructHttpUrl(getEndPoint(), pathSegments,
            pathParameters);
    RequestBuilder builder = RequestBuilder.get(httpUrl);
    builder.query(VERSION, versionDate);
    if (params != null) {
      for(String key:params.keySet()) {
        builder.query(key,params.get(key));
      }
    }
    ServiceCall<T> response =
            createServiceCall(builder.build(), ResponseConverterUtils.getObject(tClass));
    return response;
  }

  /**
   * Lists all definitions in a collections.
   * @return a {@link ServiceCall} with a response type of DefinitionCollection
   */
  public ServiceCall<DefinitionCollection> getDefinitions() {
    String[] pathSegments = { "v1/workers/definitions"  };
    String[] pathParameters = {  };
    return getData(pathSegments,pathParameters,DefinitionCollection.class);
  }

  /**
   * Fetch Definition from definition Id.
   * @param definitionId definition Id
   * @return a {@link ServiceCall} with a response type of Definition
   */
  public ServiceCall<Definition> getDefinition(String definitionId) {
    String[] pathSegments = { "v1/workers/definitions"  };
    String[] pathParameters = { definitionId };
    return getData(pathSegments,pathParameters,Definition.class);
  }

  /**
   * Used to fetch snapShot Object without export data.
   * @param definitionId definition Id
   * @param snapShotId snapShot Id
   * @return  a {@link ServiceCall} with a response type of Snapshot
   */
  public ServiceCall<Snapshot> getSnapShot(String definitionId,String snapShotId) {
    return getSnapShot(definitionId,snapShotId,false);
  }

  /**
   * Used to fetch snapshot object along with workspace data.
   * @param definitionId Definition Id.
   * @param snapShotId SnapShot Id
   * @param exportData Whether to export snapshot workspace data.
   * @return  a {@link ServiceCall} with a response type of Snapshot
   */
  public ServiceCall<Snapshot> getSnapShot(String definitionId,String snapShotId,boolean exportData) {
    String[] pathSegments = { "v1/workers/definitions","snapshots"};
    String[] pathParameters = { definitionId, snapShotId };
    Map<String,String> params = new HashMap<>();
    if (exportData) {
      params.put("export","true");
    }
    return getData(pathSegments,pathParameters,params,Snapshot.class);
  }

  /**
   * Fetch all snapshots in a definition.
   * @param definitionId definition Id
   * @return  a {@link ServiceCall} with a response type of SnapshotCollection
   */
  public ServiceCall<SnapshotCollection> getSnapShots(String definitionId) {
    String[] pathSegments = { "v1/workers/definitions","snapshots"};
    String[] pathParameters = { definitionId };
    return getData(pathSegments,pathParameters,SnapshotCollection.class);
  }

}
