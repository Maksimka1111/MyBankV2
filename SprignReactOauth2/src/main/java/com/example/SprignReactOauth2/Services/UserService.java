package com.example.SprignReactOauth2.Services;

import com.example.SprignReactOauth2.Entities.MyUser;
import com.example.SprignReactOauth2.Entities.Roles;
import com.example.SprignReactOauth2.Repositories.RolesRepository;
import com.example.SprignReactOauth2.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    RolesRepository rolesRepository;
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(RolesRepository rolesRepository, UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserService() {
    }

    public MyUser save(MyUser user){
        Roles role = new Roles();
        user.setRolesList(new ArrayList<>());
        role.setUser(user);
        role.setId(user.getId());
        role.setRole("USER");
        user.getRolesList().add(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var usr = userRepository.save(user);
        rolesRepository.save(role);
        return usr;
    }
    public void delUser(String username){
        var user = userRepository.findMyUserByUsername(username);
        rolesRepository.deleteAll(user.getRolesList());
        userRepository.delete(user);
    }
    public MyUser findUser(MyUser user){
        return  userRepository.findMyUserByUsername(user.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findMyUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }
}

class MyUserDetails implements UserDetails{

    private MyUser user;

    public MyUserDetails(MyUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRolesList().stream()
                .map(Roles::getRole)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
