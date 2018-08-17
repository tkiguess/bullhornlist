package com.example.bullhornlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @RequestMapping(value="/register", method= RequestMethod.GET)
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value ="/register", method=RequestMethod.POST)
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model){
        model.addAttribute("user", user); //here
        if (result.hasErrors()){
            return "registration";
        }else{
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
    public String delCourse(@PathVariable("id") long id){
        bullhornRepository.deleteById(id);
        return "redirect:/bullhornlist";
    }


    @GetMapping("/add")
    public String addBullhorn(Model model){
        model.addAttribute("bullhorn", new Bullhorn());
        return "add";
    }
    @PostMapping("/process2")
    public String processForm(@ModelAttribute Bullhorn bullhorn, BindingResult result, Model model)
    {
        String username = getUser().getUsername();
        bullhorn.setUsername(username);
        bullhornRepository.save(bullhorn);
        model.addAttribute("bullhorns", bullhornRepository.findByUsername(username));
        return "bullhornlist";
    }


    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User user = userRepository.findByUsername(currentusername);
        return user;
    }
    @RequestMapping("/")
    public  String index(Model model){

        model.addAttribute("bullhorns", bullhornRepository.findAll());
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/bullhornlist")
    public String bullhornlist(HttpServletRequest request, Authentication authentication, Principal principal, Model model){
        Boolean isAdmin = request.isUserInRole("ADMIN");
        Boolean isUser = request.isUserInRole("USER");
        UserDetails userDetails = (UserDetails)
                authentication.getPrincipal();

        String username = principal.getName();
        model.addAttribute("bullhorns", bullhornRepository.findByUsername(username));
        return "bullhornlist";
    }

}