package teammates.test.cases.search;

import org.testng.annotations.Test;
import teammates.common.datatransfer.StudentSearchResultBundle;
import teammates.common.datatransfer.attributes.InstructorAttributes;
import teammates.common.datatransfer.attributes.StudentAttributes;
import teammates.storage.api.StudentsDb;
import teammates.test.driver.AssertHelper;

import java.util.ArrayList;
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


        InstructorAttributes ins1InCourse1 = dataBundle.instructors.get("instructor1OfCourse1");
        InstructorAttributes ins2InCourse1 = dataBundle.instructors.get("instructor2OfCourse1");
        InstructorAttributes helperInCourse1 = dataBundle.instructors.get("helperOfCourse1");
        InstructorAttributes ins1InCourse2 = dataBundle.instructors.get("instructor1OfCourse2");

        List<InstructorAttributes> instructors = new ArrayList<>();
        instructors.add(ins1InCourse1);
        instructors.add(ins2InCourse1);
        instructors.add(helperInCourse1);
        instructors.add(ins1InCourse2);

        ______TS("success: search for student1");

        StudentSearchResultBundle bundle =
                studentsDb.search("student1",instructors);

        assertEquals(2, bundle.numberOfResults);





    }

}
