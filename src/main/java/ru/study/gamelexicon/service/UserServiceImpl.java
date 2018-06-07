package ru.study.gamelexicon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.gamelexicon.dao.RoleDao;
import ru.study.gamelexicon.dao.UserDao;
import ru.study.gamelexicon.model.Role;
import ru.study.gamelexicon.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SettingService settingService;

    @Override
    @Transactional
    public void save(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getByName("ROLE_USER"));
        user.setRoles(roles);
        user.setSetting(settingService.getDefaultSettings());
        userDao.save(user);
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    @Transactional
    public List<User> list() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void update(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getByName("ROLE_USER"));
        user.setRoles(roles);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return userDao.getOne(id);
    }

    @Override
    @Transactional
    public User findByToken(String token) {
        return userDao.findByToken(token);
    }

    @Transactional
    public Set<GrantedAuthority> getAuthorityByToken(String token){
        logger.debug(token);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return grantedAuthorities;
    }
}
