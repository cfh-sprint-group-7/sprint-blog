package blog.services;

import blog.models.Post;
import blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void savePost(Post post) {
        this.postRepository.save(post);
    }

    @Override
    public Post getPostById(long id) {
        Optional<Post> optional = postRepository.findById(id);
        Post post = null;
        if (optional.isPresent()) {
            post = optional.get();
        } else {
            throw new RuntimeException("Post not found with ID ::" + id);
        }
        return post;
    }

    @Override
    public void deletePostById(long id) {
        this.postRepository.deleteById(id);
    }
    
//    @Override
//    public Page<Post> findPaginated(int pageNum, int pageSize) {
//        Sort sort = Sort.by("id").descending();
//
//        Pageable pageable = PageRequest.of(pageNum - 1, pageSize).withSort(sort);
//        return this.postRepository.findAll(pageable);
//    }

    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize,
                                    String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        return this.postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findPages(int pageNo) {
        // TODO Auto-generated method stub
        return null;
    }
}