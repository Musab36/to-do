import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Task {
  private String description;
  private boolean complited;
  private LocalDateTime createdAt;
  private int categoryId;
  private int id;

 public Task(String description, int categoryId){
    this.description= description;
    complited = false;
    createdAt = LocalDateTime.now();
    this.categoryId= categoryId;
  }
  public String getDescription(){
    return description;
  }
  public boolean isCompleted(){
    return complited;
  }
  public LocalDateTime getCreatedAt(){
    return createdAt;
  }
  public int getId(){
    return id;
  }
  public int getCategoryId(){
    return categoryId;
  }

 public static Task find(int id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM tasks where id=:id";
    Task task = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Task.class);
    return task;
  }
}
  public static List<Task> all() {
     String sql = "SELECT id, description , categoryId FROM tasks";
     try(Connection con = DB.sql2o.open()) {
       return con.createQuery(sql).executeAndFetch(Task.class);
     }
   }

  @Override
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId() &&
             this.getCategoryId() == newTask.getCategoryId();
    }
  }
public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO tasks(description, categoryId) VALUES (:description, :categoryId)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("description", this.description)
      .addParameter("categoryId", this.categoryId)
      .executeUpdate()
      .getKey();
  }
}

public void update(String description) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE tasks SET description = :description WHERE id = :id";
    con.createQuery(sql)
      .addParameter("description", description)
      .addParameter("id", id)
      .executeUpdate();
  }
}
}
