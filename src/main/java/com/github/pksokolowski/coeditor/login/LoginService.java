package com.github.pksokolowski.coeditor.login;

import com.github.pksokolowski.coeditor.model.User;
import com.github.pksokolowski.coeditor.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private UsersRepository usersRepository;

    public LoginService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    boolean validateUser(String name, String password){
        var user = usersRepository.findByName(name);
        if(user == null){
            // if no users exist, create admin user
            if(usersRepository.count() == 0){
                var admin = new User(name, password);
                usersRepository.save(admin);
                return true;
            }
            return false;
        }

        return password.equals(user.password);
    }
}
