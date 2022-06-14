package blog.controllers;

import java.util.List;

import blog.repositories.PostRepository;
import blog.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.RequestParam;

import blog.models.Post;
import blog.services.PostService;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private NotificationService notifyService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, "date",
                "desc", model);
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

    @GetMapping("/post/{id}/update")
    public String updatePost(@PathVariable(value = "id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "/update";
    }

    @GetMapping("/post/view/{id}")
    public String viewPost (@PathVariable(value = "id") Long id, Model model) {
        Post post = postService.getPostById(id);
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
        model.addAttribute("post", post);
        return "/view";
    }

    @GetMapping("/post/{id}/delete")
    public String confirmDelete(@PathVariable(value = "id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "/delete";
    }

     @GetMapping("/deletePost/{id}/confirm")
     public String deletePost(@PathVariable(value = "id") Long id) {
         this.postService.deletePostById(id);
         return "redirect:/?deleted";
     }

//    @GetMapping("/view_posts")
//    public String viewAllPages(Model model) {
//        model.addAttribute("post", postService.getAllPosts());
//        return "view_posts";
//    }

    @GetMapping({"/view_posts"})
    public ModelAndView getAllPosts() {
        ModelAndView mav = new ModelAndView("view_posts");
        mav.addObject("posts", postService.getAllPosts());
        return mav;
    }

//    @RequestMapping("/posts/view/{id}")
//    public String view(@PathVariable("id") Long id, Model model) {
//        Post post = postService.getPostById(id);
//        model.addAttribute("post", post);
//        return "posts/view";
//    }
    
//    @GetMapping("/page/{pageNo}")
//    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
//            Model model) {
//        int pageSize = 3;
//        Page<Post> page = postService.findPaginated(pageNo, pageSize);
//        List<Post> listPosts = page.getContent();
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("listPosts", listPosts);
//        return "index";
//    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value="pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 3;

        Page<Post> page = postService.findPaginated(pageNo, pageSize,
                sortField, sortDir);
        List<Post> listPosts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listPosts", listPosts);

        return "index";
    }
}