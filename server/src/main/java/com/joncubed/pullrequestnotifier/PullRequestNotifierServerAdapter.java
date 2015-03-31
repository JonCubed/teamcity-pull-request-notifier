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

import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.vcs.VcsRootInstanceEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;


/**
 * Server-side adapter which invokes post-build logic.
 */
public class PullRequestNotifierServerAdapter extends BuildServerAdapter {

  private final SBuildServer myBuildServer;
  private final BuildHistory myBuildHistory;

  public PullRequestNotifierServerAdapter(@NotNull SBuildServer sBuildServer,
                                          @NotNull BuildHistory buildHistory) {
    myBuildServer = sBuildServer;
    myBuildHistory = buildHistory;
  }

  public void register() {
    myBuildServer.addListener(this);
  }

  @Override
  public void buildFinished(@NotNull SRunningBuild build) {
    buildCompleted(build);
  }

  @Override
  public void buildInterrupted(@NotNull SRunningBuild build) {
    buildCompleted(build);
  }

  private void buildCompleted(@NotNull SRunningBuild build) {
    SBuildType buildType = build.getBuildType();
    if (buildType == null)
      return;

    final SFinishedBuild finishedBuild = myBuildHistory.findEntry(build.getBuildId());
    if (finishedBuild == null)
      return;

    // get the attached vcs root so we can extract owner and repo
    List<VcsRootInstanceEntry> vcsRootEntries = finishedBuild.getVcsRootEntries();
    VcsRootInstanceEntry vcsRootInstanceEntry = vcsRootEntries.get(0);

    Map<String, String> parameters = buildType.getParameters();
    Map<String, String> buildParameters = buildType.getBuildParameters();
    Map<String, String> buildOwnParameters = finishedBuild.getBuildOwnParameters();

    if (finishedBuild.getBuildStatus().isSuccessful()) {
      // mark as successful
    } else {
      // mark as failed
    }

  }
}
