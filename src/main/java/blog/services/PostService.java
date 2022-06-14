package blog.services;

import blog.models.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    void savePost(Post post);

    Post getPostById(long id);

    void deletePostById(long id);

    Page<Post> findPaginated(int pageNum, int pageSize,
                             String sortField, String sortDirection);

    Page<Post> findPages(int pageNo);
}