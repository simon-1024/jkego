package cn.jkego.emall.service;


import cn.jkego.emall.dao.UserDao;
import cn.jkego.emall.domain.User;
import cn.jkego.emall.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;

@Service
public class UserService implements Serializable {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private BCryptPasswordEncoder encoder;




    /**
     * 7月17日晚，推翻重构，使用RabbitMQ 实现手机短信验证
     * 生命不息，折腾不止!~
     * @param user
     * @return
     */
    //用户注册
    public boolean register(User user){

        String newpassword = encoder.encode(user.getPassword());//加密后的密码
        user.setPassword(newpassword);

        //uid
        user.setUid(idWorker.nextId());
        //telephone
        user.setTelephone(user.getTelephone());
        //是否激活
        user.setState(0);
        //激活码
        String activeCode = idWorker.nextId();
        user.setCode(activeCode);

        User savedUser = userDao.save(user);
        if (savedUser!=null){

/*            //注册成功
            //发送激活邮件
            String emailMsg = "恭喜您注册成功~请点击以下链接激活账户"
                    + "<a href='http://localhost:8080/user/active?activeCode="+activeCode+"'>"
                    + "http://localhost:8080/user/active?activeCode="+activeCode+"</a>";

            //邮件设置
            //发送复杂类型的邮件
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            try {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage,true);
                messageHelper.setSubject("用户注册激活");
                messageHelper.setText(emailMsg,true);
                messageHelper.setTo(user.getEmail());
                messageHelper.setFrom("simon_1998@126.com");
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            mailSender.send(mimeMailMessage);*/
            return true;
        }else {
            return false;
        }
    }



    /**
     * 用户注册，检查用户名是否已经存在
     * @param username  ajax携带的数据：username
     * @return  返回布尔类型 true :存在  false:不存在
     */
    public boolean checkUsername(String username) {

        User user = userDao.findByName(username);

        return user==null?false:true;
    }




    /**
     * 用户注册后激活
     * @param code 激活码
     */
    public void activeUser(String code) {

         userDao.activeUser(code);
    }



    /**
     * 用户登录
     * @param username
     * @return
     */
    public User userLogin(String username,String password) {

        User user = userDao.findByUsername(username);

        if (user!=null&&encoder.matches(password,user.getPassword())){

            return user;
        }else {
            return  null;
        }
    }
}
