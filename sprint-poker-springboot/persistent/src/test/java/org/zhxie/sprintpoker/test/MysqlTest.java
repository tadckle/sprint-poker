//package org.persistent.test;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import Role;
//import User;
//import RoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.util.Assert;
//
//import java.util.Date;
//import java.util.List;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {JpaConfiguration.class})
//public class MysqlTest {
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Before
//    public void initData(){
//        userRepository.deleteAll();
//        roleRepository.deleteAll();
//
//
//        Role role = new Role();
//        role.setName("admin");
//        roleRepository.save(role);
//        Assert.notNull(role.getId());
//
//        User user = new User();
//        user.setName("user");
//        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
//        user.setPassword(bpe.encode("user"));
//        user.setCreatedate(new Date());
//        userRepository.save(user);
//        Assert.notNull(user.getId());
//    }
//
//    @Test
//    public void insertUserRoles(){
//        User user = userRepository.findByName("user");
//        Assert.notNull(user);
//
//        List<Role> roles = roleRepository.findAll();
//        Assert.notNull(roles);
//        user.setRoles(roles);
//        userRepository.save(user);
//    }
//}
