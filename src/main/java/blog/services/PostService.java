package blog.services;

import blog.models.Post;
import org.springframework.data.domain.Page;

public interface PostService {

    void savePost(Post post);

    Post getPostById(long id);

    void deletePostById(long id);

    Page<Post> findPaginated(int pageNum, int pageSize);

    Page<Post> findpages(int pageNo);
}