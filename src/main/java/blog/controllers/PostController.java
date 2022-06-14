package blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

import blog.models.Post;
import blog.services.PostService;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, model);
    }

    @GetMapping("/post/new")
    public String showNewPostForm(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "/create";
    }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("post") Post post, BindingResult bindingResult) {
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String updatePost(@PathVariable(value = "id") long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "/update";
    }

    @GetMapping("/deletePost/{id}")
    public String confirmDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "/delete";
    }

    // @GetMapping("/deletePost/{id}/confirm")
    // public String deletePost(@PathVariable(value = "id") long id) {
    //     this.postService.deletePostById(id);
    //     return "redirect:/?deleted";
    // }

    @GetMapping("/view")
    public String viewallPages(Model model) {
        return "view";
    }
    
    @RequestMapping("/posts/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/view";
    }
    
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
            Model model) {
        int pageSize = 3;
        Page<Post> page = postService.findPaginated(pageNo, pageSize);
        List<Post> listPosts = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listPosts", listPosts);
        return "index";
    }
}