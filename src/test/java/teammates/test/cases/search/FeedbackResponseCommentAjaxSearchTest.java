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

        ______TS("success: search for comments; no results found as instructor doesn't have privileges");

        instructors.add(dataBundle.instructors.get("helperOfCourse1"));
        FeedbackResponseCommentSearchResultBundle bundle = commentsDb.search("\"self-feedback\"", instructors);
        assertEquals(0, bundle.numberOfResults);



    }

    /*
     * Verifies that search results match with expected output.
     * Compares the text for each comment as it is unique.
     *
     * @param actual the results from the search query.
     * @param expected the expected results for the search query.
     */
    private static void verifySearchResults(FeedbackResponseCommentSearchResultBundle actual,
                FeedbackResponseCommentAttributes... expected) {
        assertEquals(expected.length, actual.numberOfResults);
        assertEquals(expected.length, actual.comments.size());
        FeedbackResponseCommentAttributes.sortFeedbackResponseCommentsByCreationTime(Arrays.asList(expected));
        FeedbackResponseCommentAttributes[] sortedComments = Arrays.asList(expected)
                .toArray(new FeedbackResponseCommentAttributes[2]);
        int i = 0;
        for (String key : actual.comments.keySet()) {
            for (FeedbackResponseCommentAttributes comment : actual.comments.get(key)) {
                assertEquals(sortedComments[i].commentText, comment.commentText);
                i++;
            }
        }
    }
}
