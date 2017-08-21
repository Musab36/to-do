import org.sql2o.*;
import java.time.LocalDateTime;
import org.junit.*;
import static org.junit.Assert.*;

public class TaskTest {

	@Before
	public void setUp() {
		DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", null, null);
	}
	
	@After
      public void tearDown() {
        try(Connection con = DB.sql2o.open()) {
          String deleteTasksQuery = "DELETE FROM tasks *;";
          String deleteCategoriesQuery = "DELETE FROM categories *;";
          con.createQuery(deleteTasksQuery).executeUpdate();
          con.createQuery(deleteCategoriesQuery).executeUpdate();
        }
      


	@Test
	public void getId_tasksInstantiatesWithAnId() {
		Task myTask = new Task("Mow the lawn");
		myTask.save();
		assertTrue(myTask.getId() > 0);
	}

	@Test
	public void find_returnsTaskWithSameId_secondTask() {
		Task firstTask = new Task("Mow the lawn");
		firstTask.save();
		Task secondTask = new Task("Buy groceries");
		secondTask.save();
		assertEquals(Task.find(secondTask.getId()), secondTask);
	}

	@Test
	public void equals_returnsTrueIfDescriptionsAreTheSame() {
		Task firstTask = new Task("Mow the lawn");
		Task secondTask = new Task("Mow the lawn");
		assertTrue(firstTask.equals(secondTask));
	}
    
    // Test responsible for inserting objects into the datebase
	@Test
	public void save_returnsTrueIfDescriptionsAreTheSame() {
		Task myTask = new Task("Mow the lawn");
		myTask.save();
		assertTrue(Task.all().get(0).equals(myTask));
	}

	@Test
	public void save_assignsIdToObject() {
		Task myTask = new Task("Mow the lawn");
		myTask.save();
		Task saveTask = Task.all().get(0);
		assertEquals(myTask.getId(), saveTask.getId());
	}
}