import java.util.List;
import java.util.ArrayList;

   public class Category {
      private String name;
      private int id;

    public Category(String name) {
        this.name = name;
      }

    public String getName() {
        return name;
      }

      public static List<Category> all() {
        String sql = "SELECT id, name FROM categories";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).excuteAndFetch(Category.class);
        }
      }

      public int getId() {
        return id;
      }

     public List<Task> getTasks() {
     }

      public static Category find(int id) {
     }

     @Override
     public boolean equals(Object otherCategory) {
        if (!(otherCategory instanceof Category)) {
            return false;
        } else {
            Category newCategory = (Category) otherCategory;
            return this.getName().equals(newCategory.getName());
        }

        public void save() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "INSERT INTO categories(name) VALUES (:name)";
          this.id = (int) con.createQuery(sql, true)
            .addParameter("name", this.name)
            .executeUpdate()
            .getKey();
        }
      }
     }

    }