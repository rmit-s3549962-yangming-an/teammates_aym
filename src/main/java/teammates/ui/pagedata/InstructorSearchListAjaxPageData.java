package teammates.ui.pagedata;

import teammates.common.datatransfer.attributes.AccountAttributes;



import java.util.List;

public class InstructorSearchListAjaxPageData extends PageData {
    private String searchKey;
    private List<String> studentIndex;
    private List<String> teamIndex;
    private List<String> feedbackQustionIndex;

    public InstructorSearchListAjaxPageData(AccountAttributes account, String sessionToken, String searchKey,
                                            List<String> studentIndex, List<String> teamIndex, List<String> feedbackQustionIndex) {
        super(account, sessionToken);
        this.searchKey = searchKey;
        this.studentIndex = studentIndex;
        this.teamIndex = teamIndex;
        this.feedbackQustionIndex = feedbackQustionIndex;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public List<String> getStudentIndex() {
        return studentIndex;
    }

    public List<String> getTeamIndex() {
        return teamIndex;
    }

    public List<String> getFeedbackQustionIndex() {
        return feedbackQustionIndex;
    }
}
