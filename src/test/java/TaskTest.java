import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.sql2o.*;

public class TaskTest{

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test?user=postgres&password=31546Bbd", null, null);
  }

 @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tasks *;";
      con.createQuery(sql).executeUpdate();
    }
  }

 @Test
  public void Task_instatentsCorrectly_true(){
    Task myTask = new Task("Mow the lawn", 1);

   assertTrue(myTask instanceof Task);
  }
  @Test
  public void Task_instantiatesWithDescription_String() {
    Task myTask = new Task("Mow the lawn", 1);
    assertEquals("Mow the lawn", myTask.getDescription());
  }
  @Test
  public void isComplited_taskIsFalseAfterIstatation_false(){
      Task myTask = new Task("Mow the lawn", 1);
      assertEquals(false, myTask.isCompleted());
  }
  @Test
  public void getCreatedAt_TimeWhenTaskIsCreated_today(){
    Task myTask = new Task("Mow the lawn", 1);
    assertEquals(LocalDateTime.now().getDayOfWeek(), myTask.getCreatedAt().getDayOfWeek());
  }
  @Test
 public void all_returnsAllInstancesOfTask_true() {
   Task firstTask = new Task("Mow the lawn", 1);
   firstTask.save();
   Task secondTask = new Task("Buy groceries" , 1);
   secondTask.save();
   assertEquals(true, Task.all().get(0).equals(firstTask));
   assertEquals(true, Task.all().get(1).equals(secondTask));
 }
  @Test
public void clear_emptiesAllTasksFromArrayList_0() {
  Task myTask = new Task("Mow the lawn", 1);

 assertEquals(Task.all().size(), 0);
}
@Test
     public void save_savesCategoryIdIntoDB_true() {
       Category myCategory = new Category("Household chores");
       myCategory.save();
       Task myTask = new Task("Mow the lawn", myCategory.getId());
       myTask.save();
       Task savedTask = Task.find(myTask.getId());
       assertEquals(savedTask.getCategoryId(), myCategory.getId());
     }
@Test
 public void find_returnsTaskWithSameId_secondTask() {
   Category myCategory = new Category("Household chores");
   myCategory.save();
   Task myTask = new Task("Mow the lawn", myCategory.getId());
   myTask.save();
   Task secondTask = new Task("Buy groceries", 2);
   secondTask.save();
   assertEquals(Task.find(secondTask.getId()), secondTask);
 }
@Test
public void save_assignsIdToObject() {
  Task myTask = new Task("Mow the lawn", 1);
  myTask.save();
  Task savedTask = Task.all().get(0);
  assertEquals(myTask.getId(), savedTask.getId());
}
@Test
 public void getId_tasksInstantiateWithAnID() {
   Task myTask = new Task("Mow the lawn", 1);
   myTask.save();
   assertTrue(myTask.getId() > 0);
 }

 @Test
 public void update_updatesTaskDescription_true() {
  Task myTask = new Task("Mow the lawn", 1);
  myTask.save();
  myTask.update("Take a nap");
  assertEquals("Take a nap", Task.find(myTask.getId()).getDescription());
 }

}