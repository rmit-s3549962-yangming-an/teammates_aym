package teammates.test.cases.search;

import org.testng.annotations.Test;
import teammates.common.datatransfer.FeedbackResponseCommentSearchResultBundle;
import teammates.common.datatransfer.attributes.FeedbackResponseCommentAttributes;
import teammates.common.datatransfer.attributes.InstructorAttributes;
import teammates.storage.api.FeedbackResponseCommentsDb;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * SUT: {@link FeedbackResponseCommentsDb},
 *      {@link teammates.storage.search.FeedbackResponseCommentDocument},
 *      {@link teammates.storage.search.FeedbackResponseCommentSearchQuery}.
 */
public class FeedbackResponseCommentAjaxSearchTest extends BaseSearchTest {

    @Test
    public void allTests() {
        FeedbackResponseCommentsDb commentsDb = new FeedbackResponseCommentsDb();

        FeedbackResponseCommentAttributes frc1I1Q1S1C1 = dataBundle.feedbackResponseComments
                .get("comment1FromT1C1ToR1Q1S1C1");
        FeedbackResponseCommentAttributes frc1I1Q2S1C1 = dataBundle.feedbackResponseComments
                .get("comment1FromT1C1ToR1Q2S1C1");
        FeedbackResponseCommentAttributes frc1I3Q1S2C2 = dataBundle.feedbackResponseComments
                .get("comment1FromT1C1ToR1Q1S2C2");

        ArrayList<InstructorAttributes> instructors = new ArrayList<InstructorAttributes>();

        ______TS("success: search for comments; instructor1@course1");

        instructors.add(dataBundle.instructors.get("helperOfCourse1"));
        FeedbackResponseCommentSearchResultBundle bundle = commentsDb.search("instructor1@course1", instructors);
        assertEquals(1, bundle.sessions.size());




        instructors.add(dataBundle.instructors.get("helperOfCourse1"));
          bundle = commentsDb.search("course1", instructors);
        assertEquals(1, bundle.sessions.size());



    }
}
