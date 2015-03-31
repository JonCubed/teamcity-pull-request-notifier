<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ include file="/include.jsp" %>

<c:url var="actionUrl" value="/pullRequestNotifierSettings.html"/>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<c:set var="props" value="${propertiesBean.getProperties()}" />
<c:set var="mergeOnSuccess" value="${props.get(\"pullRequestNotifierPlugin_mergeOnSuccess\")}" />
<c:set var="approveOnSuccess" value="${props.get(\"pullRequestNotifierPlugin-approveOnSuccess\")}" />


<l:settingsGroup title="Host Settings">
  <tr id="pullRequestNotifierPlugin.hostType.container">
    <th><label for="pullRequestNotifierPlugin-hostType">Host Type:</label></th>
    <td>
      <props:selectProperty name="pullRequestNotifierPlugin-hostType">
        <props:option value="bitbucket">Bitbucket</props:option>
        <props:option value="github">GitHub</props:option>
      </props:selectProperty>
    </td>
  </tr>
  <tr id="pullRequestNotifierPlugin.userId.container">
    <th><label for="pullRequestNotifierPlugin-userId">Username:</label></th>
    <td>
      <props:textProperty name="pullRequestNotifierPlugin-userId"/>
    </td>
  </tr>
  <tr id="pullRequestNotifierPlugin.password.container">
    <th><label for="pullRequestNotifierPlugin-password">Password:</label></th>
    <td>
      <props:passwordProperty name="pullRequestNotifierPlugin-password"/>
    </td>
  </tr>
</l:settingsGroup>

<l:settingsGroup title="Behaviour">
  <tr id="pullRequestNotifierPlugin-addComment-container">
    <th><label for="pullRequestNotifierPlugin-addComment">Add comment:</label></th>
    <td>
      <props:checkboxProperty name="pullRequestNotifierPlugin-addComment" treatFalseValuesCorrectly="${true}" uncheckedValue="false" onclick="BS.pullRequestNotifierSettings.toggleCommentNote(this)"/>
      <span class="smallNote">
        <c:choose>
          <c:when test='${addComment.equalsIgnoreCase("true")}'>Add a comment to the pull request when the build completes.</c:when>
          <c:otherwise>Do not add a comment to the pull request when the build completes.</c:otherwise>
        </c:choose>
      </span>
    </td>
  </tr>
  <tr id="pullRequestNotifierPlugin-approveOnSuccess-container">
    <th><label for="pullRequestNotifierPlugin-approveOnSuccess">Approve on success:</label></th>
    <td>
      <props:checkboxProperty name="pullRequestNotifierPlugin-approveOnSuccess" treatFalseValuesCorrectly="${true}" uncheckedValue="false" onclick="BS.pullRequestNotifierSettings.toggleApproveNote(this)"/>
      <span class="smallNote">
        <c:choose>
          <c:when test='${approveOnSuccess.equalsIgnoreCase("true")}'>Pull request will be approved on build success.</c:when>
          <c:otherwise>Pull request will not be approved on build success.</c:otherwise>
        </c:choose>
      </span>
    </td>
  </tr>
  <tr id="pullRequestNotifierPlugin_mergeOnSuccess_container">
    <th><label for="pullRequestNotifierPlugin_mergeOnSuccess">Merge on success:</label></th>
    <td>
      <props:checkboxProperty name="pullRequestNotifierPlugin_mergeOnSuccess" treatFalseValuesCorrectly="${true}" uncheckedValue="false" onclick="BS.pullRequestNotifierSettings.toggleMergeNote(this)"/>
      <span class="smallNote">
        <c:choose>
          <c:when test='${mergeOnSuccess.equalsIgnoreCase("true")}'>Pull request will be merged on build success.</c:when>
          <c:otherwise>Pull request will not be merged on build success.</c:otherwise>
        </c:choose>
      </span>
    </td>
  </tr>
</l:settingsGroup>


<script type="text/javascript">
  BS.pullRequestNotifierSettings = {
    toggleCommentNote: function (chkbox) {
      this.toggleNote(chkbox,
              "#pullRequestNotifierPlugin-addComment-container .smallNote",
              "Add a comment to the pull request when the build completes.",
              "Do not add a comment to the pull request when the build completes."
      );
    },
    toggleApproveNote: function (chkbox) {
      this.toggleNote(chkbox,
              "#pullRequestNotifierPlugin-approveOnSuccess-container .smallNote",
              "Pull request will be approved on build success.",
              "Pull request will not be approved on build success."
      );
    },
    toggleMergeNote: function (chkbox) {
      this.toggleNote(chkbox,
              "#pullRequestNotifierPlugin_mergeOnSuccess_container .smallNote",
              "Pull request will be merged on build success.",
              "Pull request will not be merged on build success."
      );
    },
    toggleNote: function (chkbox, selector, checkedMsg, uncheckedMsg) {
      $j(selector).text(chkbox.checked ? checkedMsg : uncheckedMsg);
    }
  };
</script>