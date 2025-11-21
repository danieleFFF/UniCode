package it.unical.unicode.controller;

import it.unical.unicode.dao.AvatarDAO;
import it.unical.unicode.model.Avatar;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController //trasforma i dati in json
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") //permette ad angular di parlare con java
public class AvatarController {

    private final AvatarDAO avatarDao;

    public AvatarController(AvatarDAO avatarDao){
        this.avatarDao  = avatarDao;
    }

   @GetMapping("/avatars")
   public List<Avatar> getAllAvatars(){
        return avatarDao.getAllAvatars();
   }

}
