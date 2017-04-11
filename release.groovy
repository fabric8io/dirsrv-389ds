#!/usr/bin/groovy
/*
 *
 *   Copyright (c) 2016 Red Hat, Inc.
 *
 *   Red Hat licenses this file to you under the Apache License, version
 *   2.0 (the "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *   implied.  See the License for the specific language governing
 *   permissions and limitations under the License.
 */


def updateDependencies(source) {

    def properties = []

    updatePropertyVersion {
        updates = properties
        repository = source
        project = 'fabric8io/dir'
    }
}

def stage() {
    return stageProject {
        project = 'fabric8io/dirsrv-389ds'
        useGitTagForNextVersion = true
    }
}

def approveRelease(project) {
    def releaseVersion = project[1]
    approve {
        room = null
        version = releaseVersion
        console = null
        environment = 'fabric8'
    }
}

def release(project) {
    releaseProject {
        stagedProject = project
        useGitTagForNextVersion = true
        helmPush = false
        groupId = 'io.fabric8.devops.apps'
        githubOrganisation = 'fabric8io'
        artifactIdToWatchInCentral = 'dirsrv-389ds'
        artifactExtensionToWatchInCentral = 'jar'
    }
}

def mergePullRequest(prId) {
    mergeAndWaitForPullRequest {
        project = 'fabric8io/fabric8io/dirsrv-389ds'
        pullRequestId = prId
    }

}

return this
