package fi.solita;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import java.util.List;

record Post(long blogId, String author, String title, String content) {}
record Blog(long id, List<Post> posts) {}

public class BloggingAppTest {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void records_are_serialized_to_json() {
        var blog = createTestBlog();
        String json = gson.toJson(blog);
        System.out.println(json);

        // output:
        /*
        {
          "id": 1,
          "posts": [
            {
              "blogId": 1,
              "author": "Janne",
              "title": "This is title",
              "content": "This is content"
            }
          ]
        }
         */
    }

    @Test
    public void json_is_deserialized_to_a_record() {
        String json = gson.toJson(createTestBlog());
        var blog = gson.fromJson(json, Blog.class);
        System.out.println(blog);

        // output:
        // Blog[id=1, posts=[Post[blogId=1, author=Janne, title=This is title, content=This is content]]]
    }

    @Test
    public void json_with_missing_fields_is_deserialized_to_a_record() {
        var blog = gson.fromJson("{\"id\": 1}", Blog.class);
        System.out.println(blog);

        // output:
        // Blog[id=1, posts=[Post[blogId=1, author=Janne, title=This is title, content=This is content]]]
    }

    private static Blog createTestBlog() {
        var post = new Post(1, "Janne", "This is title", "This is content");
        var blog = new Blog(1, List.of(post));
        return blog;
    }
}