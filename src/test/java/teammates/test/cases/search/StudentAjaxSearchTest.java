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
public class StudentAjaxSearchTest extends BaseSearchTest {

    @Test
    public void allTests() {

        StudentsDb studentsDb = new StudentsDb();

        StudentAttributes stu1InCourse1 = dataBundle.students.get("student1InCourse1");
        StudentAttributes stu2InCourse1 = dataBundle.students.get("student2InCourse1");
        StudentAttributes stu1InCourse2 = dataBundle.students.get("student1InCourse2");
        StudentAttributes stu2InCourse2 = dataBundle.students.get("student2InCourse2");
        StudentAttributes stu1InUnregCourse = dataBundle.students.get("student1InUnregisteredCourse");
        StudentAttributes stu2InUnregCourse = dataBundle.students.get("student2InUnregisteredCourse");
        StudentAttributes stu1InArchCourse = dataBundle.students.get("student1InArchivedCourse");

        ______TS("success: search for students in whole system; query string does not match any student");

        StudentSearchResultBundle bundle =
                studentsDb.searchStudentsInWholeSystem("alice");

        assertEquals(1, bundle.numberOfResults);



    }

}
