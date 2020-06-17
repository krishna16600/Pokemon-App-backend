package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Model.AuthUser;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000" ,allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userS;

    @GetMapping("/loginUser")
    public String logIn() {
        return "\"logged in\"";
    }

    //adding user to Database
    @PostMapping("/addUser")
    public String addUser(@RequestBody User user){
        return userS.addUserToDB(user);
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            request.getSession().invalidate();
        }
        return "/home";

    }

    @PostMapping("/authenticate")
    public User checkUser(@RequestBody AuthUser user){
        System.out.println(user.getEmail()+" "+user.getPassword());
        return userS.checkUser(user);
    }
    //adding fav pokemon to the user
    @GetMapping("/addPokemon/{pokemonId}")
    public String addFav(@PathVariable("pokemonId") Long pokemonId, Principal principal){
        Long userId = userS.getUser(principal.getName()).getUserId();
        System.out.println("user id is" + userId + "Pokemon id is "+pokemonId);
       return  userS.savePokemon(pokemonId,userId);
    }

    //removing fav pokemon of a user
    @Transactional
    @DeleteMapping("/deleteFav/{pokemonId}")
    public String delete(@PathVariable("pokemonId") Long pokemonId, Principal principal){
        return userS.deletePokemon(pokemonId,principal);
    }

    @GetMapping("/checkLiked/{pokemonId}")
    public Boolean checkIsPokemonLiked(@PathVariable("pokemonId") Long pokemonId, Principal principal){
        Long userId = userS.getUser(principal.getName()).getUserId();
        return userS.checkPokemonInDB(pokemonId,userId);
    }

    @GetMapping("/getAllPokemons")
    public List<Long> getAll(Principal principal){
        Long userId = userS.getUser(principal.getName()).getUserId();
        System.out.println(userS.getAllPokemons(userId));

        return userS.getAllPokemons(userId);
    }
}


