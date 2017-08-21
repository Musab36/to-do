import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Task {
  private String description;
  private boolean completed;
  private LocalDateTime createdAt;
  private int id;

  public Task(String description) {
    this.description = description;
    completed = false;
	  createdAt = LocalDateTime.now();
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return completed;
  }
  
  public LocalDateTime getCreatedAt() {
  	return createdAt;
  }

  public static List<Task> all() {
    String sql = "SELECT id, description FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).excuteAndFetch(Task.class);
    }
  }
  

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId();
    }
  }

   // Method responsible for inserting objects into the database
 public static Task find(int id) {
   try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM tasks where id=:id";
    Task task = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Task.class);
    return task;
  }
}
  }
