package teammates.ui.controller;

import teammates.common.datatransfer.FeedbackResponseCommentSearchResultBundle;
import teammates.common.datatransfer.SectionDetailsBundle;
import teammates.common.datatransfer.StudentSearchResultBundle;
import teammates.common.datatransfer.TeamDetailsBundle;
import teammates.common.datatransfer.attributes.CourseAttributes;
import teammates.common.datatransfer.attributes.FeedbackSessionAttributes;
import teammates.common.datatransfer.attributes.InstructorAttributes;
import teammates.common.datatransfer.attributes.StudentAttributes;
import teammates.common.exception.EntityDoesNotExistException;
import teammates.common.util.*;
import teammates.ui.pagedata.InstructorSearchListAjaxPageData;
import teammates.ui.pagedata.InstructorStudentListAjaxPageData;

import java.util.*;

public class InstructorSearchListAjaxPageAction extends  Action {
    @Override
    protected ActionResult execute() throws EntityDoesNotExistException {
        gateKeeper.verifyInstructorPrivileges(account);
        String searchKey = getRequestParamValue(Const.ParamsNames.SEARCH_KEY);
        if (searchKey == null || "".endsWith(searchKey)) {
            searchKey = "";
            return null;
        }

        int numberOfSearchOptions = 0;

        boolean isSearchForStudents = getRequestParamAsBoolean(Const.ParamsNames.SEARCH_STUDENTS);
        if (isSearchForStudents) {
            numberOfSearchOptions++;
        }

        boolean isSearchForTeams = getRequestParamAsBoolean(Const.ParamsNames.SEARCH_TEAMS);
        if (isSearchForTeams) {
            numberOfSearchOptions++;
        }

        boolean isSearchFeedbackSessionData = getRequestParamAsBoolean(Const.ParamsNames.SEARCH_FEEDBACK_SESSION_DATA);
        if (isSearchFeedbackSessionData) {
            numberOfSearchOptions++;
        }

        List<String> studentSearchResults = new ArrayList<String>();

        List<String> teamsSearchResults = new ArrayList<String>();

        List<String> frCommentSearchResults = new ArrayList<String>();

        int totalResultsSize = 0;

        if (searchKey.isEmpty() || numberOfSearchOptions == 0) {
            //display search tips and tutorials
        } else {
            //Start searching
            List<InstructorAttributes> instructors = logic.getInstructorsForGoogleId(account.googleId);

            if (isSearchForStudents) {
                StudentSearchResultBundle studentSearchResultBundle = logic.searchStudents(searchKey, instructors);
                studentSearchResults = getStudentList(studentSearchResultBundle);
            }

            if (isSearchForTeams) {
                List<StudentAttributes> studentAttributesList = logic.searchTeamsAjax(searchKey);
                teamsSearchResults = getTeamList(studentAttributesList);
            }

            if (isSearchFeedbackSessionData) {
                FeedbackResponseCommentSearchResultBundle feedbackResponseCommentSearchResultBundle = logic.searchFeedbackResponseComments(searchKey, instructors);
                frCommentSearchResults = getFeedbackList(feedbackResponseCommentSearchResultBundle);
            }
        }

        InstructorSearchListAjaxPageData data = new InstructorSearchListAjaxPageData(account, sessionToken,
                searchKey, studentSearchResults, teamsSearchResults, frCommentSearchResults);

        return createAjaxResult(data);
    }


    public List<String> getStudentList(StudentSearchResultBundle studentSearchResultBundle) {
        List<String> studentsList = new ArrayList<>();

        for (int i = 0; i < studentSearchResultBundle.studentList.size(); i++) {
            StudentAttributes sa = studentSearchResultBundle.studentList.get(i);

            studentsList.add(sa.name);
        }
        return studentsList;
    }

    public List<String> getTeamList(List<StudentAttributes> studentAttributesList) {
        Set<String> teamsSet = new HashSet<>();

        for (int i = 0; i < studentAttributesList.size(); i++) {
            StudentAttributes sa = studentAttributesList.get(i);

            teamsSet.add(sa.team);
        }

        return new ArrayList<>(teamsSet);
    }

    public List<String> getFeedbackList(FeedbackResponseCommentSearchResultBundle feedbackResponseCommentSearchResultBundle) {
        List<String> sumList = new ArrayList<>();

        for (FeedbackSessionAttributes feedbackSessionAttributes : feedbackResponseCommentSearchResultBundle.sessions.values()) {

                sumList.add(feedbackSessionAttributes.getFeedbackSessionName());
        }

        return sumList;
    }
}
