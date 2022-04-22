package net.codejava.store.auth.controller;

import io.grpc.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.utility.RandomString;
import net.codejava.store.admin.dao.AdminRepository;
import net.codejava.store.admin.models.Admin;
import net.codejava.store.admin.models.body.AdminRegisterBody;
import net.codejava.store.auth.dao.UserRespository;
import net.codejava.store.auth.models.User;
import net.codejava.store.auth.models.body.NewPassword;
import net.codejava.store.customer.dao.CustomerRespository;
import net.codejava.store.customer.models.data.Customer;
import net.codejava.store.customer.models.view.HeaderProfile;
import net.codejava.store.email.EmailSenderService;
import net.codejava.store.response_model.*;
import net.codejava.store.utils.EmailValidate;
import net.codejava.store.utils.PasswordValidate;
import net.codejava.store.utils.SendEmailUtils;
import net.codejava.store.utils.UserDecodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Properties;
import java.util.Random;

import static net.codejava.store.utils.EmailValidate.VALID_EMAIL_ADDRESS_REGEX;
import static net.codejava.store.utils.EmailValidate.validate;

@RestController
@RequestMapping("/api/auths")
@Api(value = "auth-api", description = "Nhóm API đăng nhập và cấp access token, Không yêu cầu access token")
public class AuthController {
    //    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRespository userRespository;
    @Autowired
    CustomerRespository customerRespository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    EmailSenderService senderService;

    @ApiOperation(value = "api đăng nhập cho khách hàng", response = Iterable.class)
    @PostMapping("/customer/login")
    public Response CustomerLogin(@ApiParam(name = "Authorization", value = "username+\":\"+password, lấy kết quả encode theo Base64, sau đó thêm \"Basic \" + kết quả")
                                  @RequestHeader(HeaderConstant.AUTHORIZATION) String encodedString,
                                  @RequestBody String fcmToken) {
        Response response;
        try {
            User user = UserDecodeUtils.decodeFromAuthorizationHeader(encodedString);
            if (userRespository.findByUsername(user.getUsername()) == null) {
                return new NotFoundResponse("Account not exist");
            }
            if (!userRespository.isAccountActivated(user.getUsername(), RoleConstants.CUSTOMER)) {
                response = new ForbiddenResponse(ResponseConstant.ErrorMessage.ACCOUNT_NOT_VERIFIED);
            } else {
                User login = userRespository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
                if (login != null) {
                    Customer customer = customerRespository.findByUser_Id(login.getId());
                    HeaderProfile profile = new HeaderProfile(customer.getId(), customer.getFullName(),
                            customer.getAvatarUrl(), customer.getEmail());
                    login.setFcmToken(fcmToken);
                    userRespository.save(login);
                    response = new OkResponse(profile);
                } else {
                    response = new Response(HttpStatus.UNAUTHORIZED, ResponseConstant.Vi.WRONG_EMAIL_OR_PASSWORD);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "Api đăng nhập cho admin", response = Iterable.class)
    @PostMapping("/admin/login")
    public Response AdminLogin(@ApiParam(name = "encodedString", value = "username+\":\"+password, lấy kết quả encode theo Base64, sau đó thêm \"Basic \" + kết quả")
                               @RequestHeader(HeaderConstant.AUTHORIZATION) String encodedString) {
        Response response;
        try {
            User user = UserDecodeUtils.decodeFromAuthorizationHeader(encodedString);
            if (!userRespository.isAccountActivated(user.getUsername(), RoleConstants.ADMIN)) {
                response = new ForbiddenResponse(ResponseConstant.ErrorMessage.ACCOUNT_NOT_VERIFIED);
            } else {
                User login = userRespository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
                if (login != null) {
                    response = new OkResponse();
                } else {
                    response = new Response(HttpStatus.UNAUTHORIZED, ResponseConstant.Vi.WRONG_EMAIL_OR_PASSWORD);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }


    @PostMapping("facebook/login/{facebookUserID}")
    public Response FacebookLogin(@PathVariable("facebookUserID") String facebookUserID,
                                  @RequestBody String fcmToken) {
        Response response;
        try {
            User user = userRespository.findByUsername(facebookUserID);
            HeaderProfile profile;
            if (user == null) {

                return new OkResponse(new Boolean(false));
            } else {
                user.setFcmToken(fcmToken);
                userRespository.save(user);
                Customer customer = customerRespository.findByUser_Id(user.getId());
                profile = new HeaderProfile(customer.getId(), customer.getFullName(),
                        customer.getAvatarUrl(), customer.getEmail());
                response = new OkResponse(profile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }
//    @PutMapping("facebook/register/{fcmToken}")
//    public Response FacebookRegister(@RequestBody FacebookLoginBody facebookLoginBody,
//                                     @PathVariable("fcmToken") String fcmToken) {
//        Response response;
//        try {
//            User user = userRespository.findByUsername(facebookLoginBody.getFacebookUserID());
//            HeaderProfile profile;
//            if (user == null) {
//                user = new User();
//                user.setUsername(facebookLoginBody.getFacebookUserID());
//                user.setPassword(new RandomString().nextString());
//                user.setActived(true);
//                user.setFcmToken(fcmToken);
//                user = userRespository.save(user);
//
//                Customer customer = new Customer(facebookLoginBody);
//                customer.setUser(user);
//                customer = customerRespository.save(customer);
//                profile = new HeaderProfile(customer.getId(), customer.getFullName(),
//                        customer.getAvatarUrl(), customer.getEmail());
//                return new OkResponse(profile);
//            }else {
//                return new ResourceExistResponse("Account existed !");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }

    @PostMapping("facebook/login/state")
    public Response checkFacebookLoginState(@RequestBody String facebookUserID) {
        Response response;

        try {
            User user = userRespository.findByUsername(facebookUserID);
            if (user == null) {
                response = new OkResponse(false);
            } else {
                response = new OkResponse(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }

        return response;
    }

    @ApiOperation(value = "Đăng ký tài khoản khach hang", response = Iterable.class)
    @PostMapping("/customers/register")
    public Response register(@ApiParam(name = HeaderConstant.AUTHORIZATION,
            value = "username+\":\"+password, lấy kết quả encode theo Base64, sau đó thêm \"Basic \" + kết quả", required = true)
                             @RequestHeader(value = HeaderConstant.AUTHORIZATION) String encodedString, @ApiParam(name = "customerRegisterBody", value = "Tên đầy đủ KH", required = true)
                             @Valid @RequestBody Customer customerRegisterBody) {
        Response response;
        try {
            User u = UserDecodeUtils.decodeFromAuthorizationHeader(encodedString);
            if (!PasswordValidate.isPasswordValidate(u.getPassword())) {
                return new ForbiddenResponse(ResponseConstant.ErrorMessage.PASSWORD_TOO_SHORT);
            }

            User user = userRespository.findByUsername(u.getUsername());
            if (user != null) {
                return new ResourceExistResponse("Tài khoản đã tồn tại!");
            } else {
                user = new User();
                user.setPassword(u.getPassword());
                user.setUsername(u.getUsername());
                user.setRole(RoleConstants.CUSTOMER);
                user.setActived(true);

                userRespository.save(user);

                Customer customer = new Customer();
                customer.setUser(user);
                customer.setBirthday(customerRegisterBody.getBirthday());
                customer.setGender(customerRegisterBody.getGender());
                customer.setFullName(customerRegisterBody.getFullName());
                customer.setAddress(customerRegisterBody.getAddress());
                customer.setPhone(customerRegisterBody.getPhone());
                customer.setEmail(customerRegisterBody.getEmail());
                customerRespository.save(customer);

                SendEmailUtils.sendEmailActiveAccount(u.getUsername());

                response = new OkResponse(u.getUsername());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void sendEmail(String toEmail, String subject, String body) {
//        senderService.sendEmail(toEmail, subject, body);
//    }

    @ApiOperation(value = "Đăng ký tài khoản admin", response = Iterable.class)
    @PostMapping("/admins/register")
    public Response adminRegister(@ApiParam(name = HeaderConstant.AUTHORIZATION, value = "username+\":\"+password, lấy kết quả encode theo Base64, sau đó thêm \"Basic \" + kết quả", required = true)
                                  @RequestHeader(value = HeaderConstant.AUTHORIZATION) String encodedString, @ApiParam(name = "adminRegisterBody", value = "Tên đầy đủ KH", required = true)
                                  @Valid @RequestBody AdminRegisterBody adminRegisterBody) {
        Response response;
        try {
            User u = UserDecodeUtils.decodeFromAuthorizationHeader(encodedString);
            if (!PasswordValidate.isPasswordValidate(u.getPassword())) {
                return new ForbiddenResponse(ResponseConstant.ErrorMessage.PASSWORD_TOO_SHORT);
            }

            User user = userRespository.findByUsername(u.getUsername());
            if (user != null) {
                return new ResourceExistResponse("Tai khoan da ton tai!");
            } else {
                user = new User();
                user.setPassword(u.getPassword());
                user.setUsername(u.getUsername());
                user.setRole(RoleConstants.ADMIN);
                user.setActived(true);

                userRespository.save(user);

                Admin admin = new Admin();
                admin.setFullName(adminRegisterBody.getFullName());
                admin.setPosition(adminRegisterBody.getPosition());

                adminRepository.save(admin);

//                SendEmailUtils.sendEmailrequest(u.getUsername());
                response = new OkResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "Quên mật khẩu", response = Iterable.class)
    @PostMapping("/reset_password/{customer_id}")
    public Response resetPassword(@PathVariable("customer_id") String customerId) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerId);
            if (customer == null) {
                return new NotFoundResponse("Khách hàng không tồn tại");
            }

            if (!validate(customer.getEmail())) {
                return new ForbiddenResponse("Email của khách hàng không hợp lệ. Hãy cập nhật lại email trong thông tin người dùng");
            }
            String newPassword = new RandomString().nextString();
            customer.getUser().setPassword(newPassword);

            senderService.sendEmail(customer.getEmail(), "Mật khẩu mới", "Mật khẩu mới của quý khách là: " + newPassword);
            customerRespository.save(customer);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }


    @ApiOperation(value = "Xac nhan email", response = Iterable.class)
    @GetMapping("/registration/confirm/{username}")
    public Response confirm_email(@PathVariable("username") String username) {
        Response response;
        try {
            username += ".com";
            if (userRespository.findByUsername(username) == null) {
                return new NotFoundResponse("Email khong ton tai !");
            }
            userRespository.activeAccount(true, username);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "Xac nhan email", response = Iterable.class)
    @PostMapping("/resend/registration/confirm/{username}")
    public Response resend_confirm_email(@PathVariable("username") String username) {
        Response response;
        try {
            username += ".com";
            SendEmailUtils.sendEmailActiveAccount(username);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "Đổi mật khẩu", response = Iterable.class)
    @PostMapping("/customer/{customerID}/newPassword")
    public Response changePassword(@PathVariable("customerID") String customerID,
                                   @Valid @RequestBody NewPassword password) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not Exist");
            }
            User u = customer.getUser();
            if (u.getPassword().matches(password.getOldPassword())) {
                u.setPassword(password.getNewPassword());
                userRespository.save(u);
                response = new OkResponse();
            } else {
                response = new Response(HttpStatus.CONFLICT, ResponseConstant.Vi.OLD_PASSWORD_MISMATCH);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "Khóa người dùng", response = Iterable.class)
    @PostMapping("/customer/{customer_id}/lock_customer")
    public Response lockCustomer(@PathVariable("customer_id") String customerID) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not Exist");
            }
            User u = customer.getUser();
            u.setActived(false);
            userRespository.save(u);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "Mở khóa người dùng", response = Iterable.class)
    @PostMapping("/customer/{customer_id}/unlock_customer")
    public Response unlockCustomer(@PathVariable("customer_id") String customerId) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerId);
            if (customer == null) return new NotFoundResponse("Customer not Exist");
            User u = customer.getUser();
            u.setActived(true);
            userRespository.save(u);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

//    @ApiOperation(value = "Quên mật khẩu (Gửi lại email reset mật khẩu)", response = Iterable.class)
//    @PostMapping("/customer/{id}/reset_password")
//    public Response sendEmailToRessetPassword(
//            @PathVariable("id") String customerID,
//            @Valid @RequestBody String email) {
//        Response response;
//        try {
//            Customer customer = customerRespository.findOne(customerID);
//            if (customer == null) {
//                return new NotFoundResponse("Customer not Exist");
//            }
//            if (!validate(email)) {
//                return new Response(HttpStatus.GONE, ResponseConstant.ErrorMessage.INVALID_EMAIL);
//            }
//
//            User u = customer.getUser();
//            RandomString randomString = new RandomString();
//            String resetPassword = randomString.nextString();
//            u.setPassword(resetPassword);
//            userRespository.save(u);
//            SendEmailUtils.sendEmailResetPassword(email, resetPassword);
//            response = new OkResponse();
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }

}