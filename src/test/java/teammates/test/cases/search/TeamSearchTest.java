package teammates.test.cases.search;

import org.testng.annotations.Test;
import teammates.common.datatransfer.StudentSearchResultBundle;
import teammates.common.datatransfer.attributes.InstructorAttributes;
import teammates.common.datatransfer.attributes.StudentAttributes;
import teammates.storage.api.StudentsDb;
import teammates.test.driver.AssertHelper;

import java.util.Arrays;
import java.util.List;

/**
 * SUT: {@link StudentsDb},
 *      {@link teammates.storage.search.StudentSearchDocument},
 *      {@link teammates.storage.search.StudentSearchQuery}.
 */
public class TeamSearchTest extends BaseSearchTest  {

    @Test
    public void allTests() {

        String strHead = "Team:";
        StudentsDb studentsDb = new StudentsDb();

        StudentAttributes student1InCourse1 = dataBundle.students.get("student1InCourse1");
        StudentAttributes student2InCourse1 = dataBundle.students.get("student2InCourse1");
        StudentAttributes student3InCourse1 = dataBundle.students.get("student3InCourse1");
        StudentAttributes student4InCourse1 = dataBundle.students.get("student4InCourse1");
        StudentAttributes student5InCourse1 = dataBundle.students.get("student5InCourse1");
        StudentAttributes stu1InUnregCourse = dataBundle.students.get("student1InUnregisteredCourse");


        ______TS("success: search for students in whole system; query string does not match any student");

        StudentSearchResultBundle bundle =
                studentsDb.searchStudentsInWholeSystem(strHead+"non-existent");

        assertEquals(0, bundle.numberOfResults);
        assertTrue(bundle.studentList.isEmpty());

        ______TS("success: search for students in whole system; query Team 1 matches some students");

        bundle = studentsDb.searchStudentsInWholeSystem(strHead+"1");

        assertEquals(6, bundle.numberOfResults);
        AssertHelper.assertSameContentIgnoreOrder(
                Arrays.asList(student1InCourse1, student2InCourse1, student3InCourse1, student4InCourse1,student5InCourse1,stu1InUnregCourse),
                bundle.studentList);

    }
}
