package com.example.bullhornlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    BullhornRepository bullhornRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowerRepository followerRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("message",
                    "User Account Successfully Created");
        }
        return "index";
    }

    @RequestMapping("/update/{id}")
    public String userUpdate(@PathVariable("id") long id, Model model) {
        model.addAttribute("bullhorn", bullhornRepository.findById(id).get());
        return "add";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id) {
        bullhornRepository.deleteById(id);
        return "redirect:/bullhornlist";
    }


    @GetMapping("/add")
    public String addBullhorn(Model model) {
        model.addAttribute("bullhorn", new Bullhorn());
        return "add";
    }

    @PostMapping("/processing")
    public String processForm(@ModelAttribute Bullhorn bullhorn, BindingResult result, Model model) {
        String username = getUser().getUsername();
        bullhorn.setUsername(username);
        bullhornRepository.save(bullhorn);
        model.addAttribute("user", userRepository.findByUsername(username));
        model.addAttribute("bullhorns", bullhornRepository.findByUsername(username));
        return "index";
    }


    @PostMapping("/following")
    public String processForm(@ModelAttribute Follower follower, BindingResult result, Model model) {
        String username = getUser().getUsername();
        follower.setUsername(username);
        followerRepository.save(follower);
        model.addAttribute("user", userRepository.findByUsername(username));
        model.addAttribute("bullhorns", bullhornRepository.findByUsername(username));
        model.addAttribute("follower", followerRepository.findByUsername(username));
        return "index";
    }


    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User user = userRepository.findByUsername(currentusername);
        return user;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("bullhorns", bullhornRepository.findAll());
        model.addAttribute("user", userService.getCurrentUser());
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout2")
    public String logout2() {
        return "logoutout";
    }


    @RequestMapping("/bullhornlist")
    public String bullhornlist(HttpServletRequest request, Authentication authentication, Principal principal, Model model) {
//        Boolean isAdmin = request.isUserInRole("ADMIN");
//        Boolean isUser = request.isUserInRole("USER");
//        UserDetails userDetails = (UserDetails)
//                authentication.getPrincipal();

        String username = principal.getName();
        model.addAttribute("bullhorns", bullhornRepository.findByUsername(username));
        model.addAttribute("user", userRepository.findByUsername(username));
        return "bullhornlist";
    }

    @RequestMapping("/profile/{username}")
    public String showCourse(@PathVariable("username") String username, Principal principal, Model model) {
//        username = userService.getCurrentUser().getUsername();
        model.addAttribute("user", userRepository.findByUsername(username));
        model.addAttribute("bullhorns", bullhornRepository.findByUsername(username));
        model.addAttribute("follower", followerRepository.findByUsername(username));
        model.addAttribute("follower", new Follower());
        return "profile";
    }

    @RequestMapping("/follow/{id}")
    public String addfollow(@PathVariable("id") long id, Model model) {
        Follower f = new Follower();
        f.setUser(userRepository.findById(id).get());
        String username = userService.getCurrentUser().getUsername();
        f.setUsername(username);
        followerRepository.save(f);
        model.addAttribute("user", userRepository.findByUsername(username));
        model.addAttribute("bullhorns", bullhornRepository.findByUsername(username));
        model.addAttribute("follower", followerRepository.findByUsername(username));
        return "index";
    }

//    @RequestMapping("/following")
//    public String flower(Principal principal, Model model) {
//        String username = principal.getName();
//        model.addAttribute("bullhorns", bullhornRepository.findByFollowers(followers));
//        model.addAttribute("follower", userRepository.findByFollowers(followers));
//        return "following";
//    }

}