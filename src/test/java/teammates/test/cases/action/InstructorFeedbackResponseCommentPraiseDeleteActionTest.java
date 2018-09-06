package teammates.test.cases.action;

import org.testng.annotations.Test;
import teammates.common.datatransfer.attributes.*;
import teammates.common.util.Const;
import teammates.storage.api.FeedbackQuestionsDb;
import teammates.storage.api.FeedbackResponseCommentsDb;
import teammates.storage.api.FeedbackResponsesDb;
import teammates.ui.controller.AjaxResult;
import teammates.ui.controller.InstructorFeedbackResponseCommentPraiseAddAction;
import teammates.ui.controller.InstructorFeedbackResponseCommentPraiseDeleteAction;
import teammates.ui.pagedata.InstructorFeedbackResponseCommentAjaxPageData;

public class InstructorFeedbackResponseCommentPraiseDeleteActionTest extends BaseActionTest{

    @Override
    protected String getActionUri() {
        return Const.ActionURIs.INSTRUCTOR_FEEDBACK_RESPONSE_COMMENT_PRAISE_DELETE;
    }

    @Override
    @Test
    public void testExecuteAndPostProcess() throws Exception {
        FeedbackQuestionsDb feedbackQuestionsDb = new FeedbackQuestionsDb();
        FeedbackResponsesDb feedbackResponsesDb = new FeedbackResponsesDb();
        FeedbackResponseCommentsDb feedbackResponseCommentsDb = new FeedbackResponseCommentsDb();

        int questionNumber = 1;
        FeedbackQuestionAttributes feedbackQuestion = feedbackQuestionsDb.getFeedbackQuestion(
                "First feedback session", "idOfTypicalCourse1", questionNumber);

        String giverEmail = "student1InCourse1@gmail.tmt";
        String receiverEmail = "student1InCourse1@gmail.tmt";
        FeedbackResponseAttributes feedbackResponse =
                feedbackResponsesDb.getFeedbackResponse(feedbackQuestion.getId(), giverEmail, receiverEmail);

        FeedbackResponseCommentAttributes feedbackResponseComment =
                typicalBundle.feedbackResponseComments.get("comment1FromT1C1ToR1Q1S1C1");

        feedbackResponseComment = feedbackResponseCommentsDb.getFeedbackResponseComment(feedbackResponse.getId(),
                feedbackResponseComment.giverEmail, feedbackResponseComment.createdAt);
        assertNotNull("response comment not found", feedbackResponseComment);

        InstructorAttributes instructor = typicalBundle.instructors.get("instructor1OfCourse1");
        gaeSimulation.loginAsInstructor(instructor.googleId);

        ______TS("Unsuccessful csae: not enough parameters");

        verifyAssumptionFailure();

//        String[] submissionParams = new String[] {
//                Const.ParamsNames.COURSE_ID, feedbackResponseComment.courseId,
//                Const.ParamsNames.FEEDBACK_SESSION_NAME, feedbackResponseComment.feedbackSessionName,
//                Const.ParamsNames.FEEDBACK_RESPONSE_COMMENT_TEXT, "Comment to first response",
//                Const.ParamsNames.USER_ID, instructor.googleId
//        };

//        verifyAssumptionFailure(submissionParams);



        ______TS("Typical disliked successful case for unpublished session");

        String[] submissionParams = new String[] {
                Const.ParamsNames.COURSE_ID, feedbackResponseComment.courseId,
                Const.ParamsNames.FEEDBACK_SESSION_NAME, feedbackResponseComment.feedbackSessionName,
                Const.ParamsNames.FEEDBACK_RESPONSE_ID, feedbackResponseComment.feedbackResponseId,
                Const.ParamsNames.FEEDBACK_RESPONSE_COMMENT_ID, feedbackResponseComment.getId().toString(),
                Const.ParamsNames.FEEDBACK_RESPONSE_COMMENT_TEXT, feedbackResponseComment.commentText + " (Edited)",
                Const.ParamsNames.FEEDBACK_RESULTS_SORTTYPE, "recipient",
                Const.ParamsNames.RESPONSE_COMMENTS_SHOWCOMMENTSTO, "GIVER,INSTRUCTORS",
                Const.ParamsNames.RESPONSE_COMMENTS_SHOWGIVERTO, "GIVER,INSTRUCTORS"
        };

        InstructorFeedbackResponseCommentPraiseDeleteAction action = getAction(submissionParams);
        AjaxResult result = getAjaxResult(action);
        InstructorFeedbackResponseCommentAjaxPageData data =
                (InstructorFeedbackResponseCommentAjaxPageData) result.data;

        assertFalse(data.isLiked);

    }

    @Override
    protected InstructorFeedbackResponseCommentPraiseDeleteAction getAction(String... params) {
        return (InstructorFeedbackResponseCommentPraiseDeleteAction) gaeSimulation.getActionObject(getActionUri(), params);
    }

    @Override
    @Test
    protected void testAccessControl() throws Exception {
        final FeedbackQuestionsDb fqDb = new FeedbackQuestionsDb();
        final FeedbackResponsesDb frDb = new FeedbackResponsesDb();
        final FeedbackResponseCommentsDb frcDb = new FeedbackResponseCommentsDb();

        FeedbackSessionAttributes fs = typicalBundle.feedbackSessions.get("session1InCourse1");

        int questionNumber = 1;
        FeedbackQuestionAttributes feedbackQuestion = fqDb.getFeedbackQuestion(
                "First feedback session", "idOfTypicalCourse1", questionNumber);

        String giverEmail = "student1InCourse1@gmail.tmt";
        String receiverEmail = "student1InCourse1@gmail.tmt";
        FeedbackResponseAttributes feedbackResponse = frDb.getFeedbackResponse(feedbackQuestion.getId(),
                giverEmail, receiverEmail);

        FeedbackResponseCommentAttributes feedbackResponseComment = typicalBundle.feedbackResponseComments
                .get("comment1FromT1C1ToR1Q1S1C1");

        feedbackResponseComment = frcDb.getFeedbackResponseComment(feedbackResponse.getId(),
                feedbackResponseComment.giverEmail, feedbackResponseComment.createdAt);

        String[] submissionParams = new String[] {
                Const.ParamsNames.COURSE_ID, fs.getCourseId(),
                Const.ParamsNames.FEEDBACK_SESSION_NAME, fs.getFeedbackSessionName(),
                Const.ParamsNames.FEEDBACK_RESPONSE_ID, feedbackResponse.getId(),
                Const.ParamsNames.FEEDBACK_RESPONSE_COMMENT_ID, feedbackResponseComment.getId().toString(),
                Const.ParamsNames.FEEDBACK_RESPONSE_COMMENT_TEXT, "comment",
                Const.ParamsNames.FEEDBACK_RESULTS_SORTTYPE, "recipient"
        };
        // this person is not the giver. so not accessible
        verifyUnaccessibleWithoutModifySessionCommentInSectionsPrivilege(submissionParams);
        verifyOnlyInstructorsCanAccess(submissionParams);
    }



}
