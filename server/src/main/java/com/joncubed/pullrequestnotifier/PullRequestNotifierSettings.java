/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.joncubed.pullrequestnotifier;

import jetbrains.buildServer.serverSide.BuildFeature;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * {@link BuildFeature} instance which is used to present the notifier configuration options on a TeamCity project.
 */
public class PullRequestNotifierSettings extends BuildFeature
{
  private final PluginDescriptor myPluginDescriptor;
  //private final PublisherManager myPublisherManager;

  public PullRequestNotifierSettings(@NotNull PluginDescriptor pluginDescriptor) {
    myPluginDescriptor = pluginDescriptor;
    //myPublisherManager = publisherManager;
  }

  @NotNull
  @Override
  public String getType() {
    return "pull-request-notifier";
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return "Pull Request Notifier";
  }

  @Nullable
  @Override
  public String getEditParametersUrl() {
    return myPluginDescriptor.getPluginResourcesPath("pullRequestNotifierSettings.jsp");
  }

  /**
   * Returns parameters description of the build feature, will be used in the TeamCity UI to
   * describe this feature settings. Can contain HTML, so please make sure it is safe in terms of XSS.
   *
   * @param params parameters to describe
   * @return short description of parameters.
   */
  @NotNull
  @Override
  public String describeParameters(@NotNull Map<String, String> params) {
    String approve = getParam(params, "pullRequestNotifierPlugin-approveOnSuccess", "false").equalsIgnoreCase("true") ? "approve" : "do not approve";
    String merge = getParam(params, "pullRequestNotifierPlugin_mergeOnSuccess", "false").equalsIgnoreCase("true") ? "merge" : "do not merge";
    String comment = getParam(params,"pullRequestNotifierPlugin-addComment", "false").equalsIgnoreCase("true") ? "add a comment" : "do not add a comment";

    return "Look for pull requests at " + params.get("pullRequestNotifierPlugin-hostType") + ". When a build completes " + comment + ", when it succeeds " + approve + " and " + merge + ".";
  }

  private String getParam(@NotNull Map<String, String> params, String key, String defaultValue) {
    return params.containsKey(key) ? params.get(key) : defaultValue;
  }
}
