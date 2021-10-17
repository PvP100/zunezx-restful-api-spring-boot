package net.codejava.store.customer.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.codejava.store.constants.Constant;
import net.codejava.store.customer.dao.CustomerRespository;
import net.codejava.store.customer.dao.FeedbackRepository;
import net.codejava.store.customer.dao.RateRepository;
import net.codejava.store.customer.dao.SaveClothesRepository;
import net.codejava.store.customer.models.body.OrderBody;
import net.codejava.store.customer.models.body.ProfileBody;
import net.codejava.store.customer.models.body.RateBody;
import net.codejava.store.customer.models.body.SetOrderBody;
import net.codejava.store.customer.models.data.Customer;
import net.codejava.store.customer.models.data.Feedback;
import net.codejava.store.customer.models.data.CustomerOrder;
import net.codejava.store.customer.models.data.Rate;
import net.codejava.store.customer.models.view.HeaderProfile;
import net.codejava.store.customer.models.view.CustomerOrderPreview;
import net.codejava.store.customer.models.view.Profile;
import net.codejava.store.customer.models.view.SaveClothesPreview;
import net.codejava.store.product.dao.CustomerOrderRepository;
import net.codejava.store.product.dao.ProductsRepository;
import net.codejava.store.product.dao.OrderRepository;
import net.codejava.store.product.models.data.Product;
import net.codejava.store.product.models.data.SaveClothes;
import net.codejava.store.response_model.NotFoundResponse;
import net.codejava.store.response_model.OkResponse;
import net.codejava.store.response_model.Response;
import net.codejava.store.response_model.ServerErrorResponse;
import net.codejava.store.utils.PageAndSortRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/customers")
@Api(value = "candidate-api", description = "Nhóm API Customer, Yêu cầu access token của Khách hàng")
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    CustomerRespository customerRespository;
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    RateRepository rateRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    SaveClothesRepository saveClothesRepository;
    @Autowired
    private CustomerOrderRepository orderRepository;

    @ApiOperation(value = "Lấy Lấy avatar + email + tên Khách hàng", response = Iterable.class)
    @GetMapping("/headerProfiles/{id}")
    Response getHeaderProfile(@PathVariable("id") String customerID) {
        Response response;
        try {
            HeaderProfile headerProfile = customerRespository.getHeaderProfile(customerID);
            response = new OkResponse(headerProfile);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @GetMapping("/profiles/{id}")
    Response getProfile(@PathVariable("id") String customerID) {
        Response response;
        try {
            Profile profile = customerRespository.getProfile(customerID);
            response = new OkResponse(profile);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "Update Profile", response = Iterable.class)
    @PutMapping("updateProfile/{id}")
    Response updateProfile(@PathVariable("id") String customerID, @RequestBody ProfileBody profileBody) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not exist!");
            }
            customer.update(profileBody);
            customerRespository.save(customer);
            Profile profile = new Profile(customer);
            response = new OkResponse(profile);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }


    @GetMapping("/{customerID}/clothes/{id}/state")
    public Response getDetailClothesState(@PathVariable("customerID")String customerID,
                                          @PathVariable("id") String clothesID) {
        Response response;
        try {
            Product product = productsRepository.findById(clothesID);
            if (product == null) {
                return new NotFoundResponse("Clothes not Exist");
            }

            Customer customer = customerRespository.findOne(customerID);
            if(customer==null){
                return new NotFoundResponse("Customer not Exist");
            }

            if(saveClothesRepository.existsByCustomer_IdAndProduct_Id(customerID,clothesID)){
                response = new OkResponse(true);
            }else {
                response = new OkResponse(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }
    //Gui phan hoi
    @ApiOperation(value = "Gưi phản hồi từ khách hàng", response = Iterable.class)
    @PostMapping("/{customerID}/feedback")
    Response feedback(@PathVariable("customerID") String customerID,
                      @RequestBody String content) {
        Response response;

        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not existed!");
            }
            Feedback feedback = new Feedback(customer, content);
            feedbackRepository.save(feedback);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    //Danh gia shop
    @ApiOperation(value = "Đánh giá cửa hàng", response = Iterable.class)
    @PutMapping("/{customerID}/rate")
    Response rateShop(@PathVariable("customerID") String customerID,
                      @RequestBody RateBody rateBody) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not found");
            }
            Rate rate = new Rate(customer, rateBody);
            rateRepository.save(rate);
            response = new OkResponse("Success");
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }


    /**********************SaveClothes********************/
    @ApiOperation(value = "Api lưu sản phẩm", response = Iterable.class)
    @GetMapping("/{customerID}/save_clothes")
    public Response saveClothes(@PathVariable("customerID") String customerID,
                                @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + SaveClothes.SAVED_DATE)
                                @RequestParam(value = "sortBy", defaultValue = SaveClothes.SAVED_DATE) String sortBy,
                                @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
                                @RequestParam(value = "sortType", defaultValue = "desc") String sortType,
                                @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
                                @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not Exist");
            }

            Pageable pageable = PageAndSortRequestBuilder
                    .createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<SaveClothesPreview> saveClothesPreviews = saveClothesRepository.getAllSavedClothes(customerID, pageable);

            response = new OkResponse(saveClothesPreviews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    //Lay chi tiet clothes
    @ApiOperation(value = "Lấy chi tiết quần áo", response = Iterable.class)
    @GetMapping("/clothes/{id}")
    public Response getDetailClothes(@PathVariable("id") String clothesID) {
        Response response;
        try {
            response = new OkResponse(productsRepository.findById(clothesID));
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }
    @ApiOperation(value = "Api Lưu quần áo", response = Iterable.class)
    @PutMapping("/{customerID}/save_clothes/{id}")
    public Response saveClothes(@PathVariable("customerID") String customerID,
                                @PathVariable("id") String clothesID) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not Exist");
            }
            Product product = productsRepository.findById(clothesID);
            if (product == null) {
                return new NotFoundResponse("Clothes not Exist");
            }

            product.addSave();
            productsRepository.save(product);

            saveClothesRepository.save(new SaveClothes(product, customer));
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @Transactional
    void updateSaveClothes(String customerID, String clothesID) {
//        saveClothesRepository.updateState(customerID,clothesID);
    }
    @ApiOperation(value = "Api hủy lưu sản phẩm", response = Iterable.class)
    @DeleteMapping("/{customerID}/save_clothes/{id}")
    public Response deleteSaveClothes(@PathVariable("customerID") String customerID,
                                      @PathVariable("id") String clothesID,
                                      @ApiParam(name = "pageIndex", value = "index trang, mặc định là 0")
                                      @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                      @ApiParam(name = "pageSize", value = "Kích thước trang, mặc định và tối đa là " + Constant.MAX_PAGE_SIZE)
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là : " + SaveClothes.SAVED_DATE)
                                      @RequestParam(value = "pageIndex", defaultValue = SaveClothes.SAVED_DATE) String sortBy,
                                      @ApiParam(name = "sortType", value = "Nhận asc|desc, mặc đính là desc")
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "desc") String sortType) {
        Response response;
        try {

            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not Exist");
            }
            Product product = productsRepository.findById(clothesID);
            if (product == null) {
                return new NotFoundResponse("Clothes not Exist");
            }

            product.subSave();
            productsRepository.save(product);
            saveClothesRepository.deleteByCustomer_idAndProduct_Id(customer.getId(), clothesID);

            Pageable pageable = PageAndSortRequestBuilder
                    .createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<SaveClothesPreview> saveClothesPreviews = saveClothesRepository.getAllSavedClothes(customerID, pageable);

            response = new OkResponse(saveClothesPreviews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    //Order
    @ApiOperation(value = "Api tạo order cho khách hàng", response = Iterable.class)
    @PostMapping("/{customerID}/orders/{clothesID}")
    public Response insertOrder(@PathVariable("customerID") String customerID,
                                @PathVariable("clothesID") String clothesID,
                                @RequestBody OrderBody orderBody) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not Exist");
            }

            Product product = productsRepository.findOne(clothesID);
            if (product == null) {
                return new NotFoundResponse("Clothes not Exist");
            }

            CustomerOrder order = new CustomerOrder(product, customer, orderBody);
            orderRepository.save(order);
            response = new OkResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }//Order
    @ApiOperation(value = "Api tạo order cho khách hàng", response = Iterable.class)
    @PutMapping("/{customerID}/orders")
    public Response insertOrder(@PathVariable("customerID") String customerID,
                                @RequestBody Set<SetOrderBody> orderBodies) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not Exist");
            }

            for(SetOrderBody setOrderBody:orderBodies){
                Product product = productsRepository.findOne(setOrderBody.getClothesID());
                if(product !=null){
                    CustomerOrder order = new CustomerOrder(product,customer,setOrderBody);
                    orderRepository.save(order);
                }
            }
            response = new OkResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "Lay tat ca cac don dat hang cua khach hang")
    @GetMapping("/{customerID}/orders")
    public Response getAllOrder(@PathVariable("customerID") String customerID,
                                @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là : " + CustomerOrder.CREATED_DATE)
                                @RequestParam(value = "pageIndex", defaultValue = CustomerOrder.CREATED_DATE) String sortBy,
                                @ApiParam(name = "sortType", value = "Nhận asc|desc, mặc đính là desc")
                                @RequestParam(value = "pageSize", required = false, defaultValue = "desc") String sortType,
                                @ApiParam(name = "pageIndex", value = "index trang, mặc định là 0")
                                @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                @ApiParam(name = "pageSize", value = "Kích thước trang, mặc định và tối đa là " + Constant.MAX_PAGE_SIZE)
                                @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not Exist");
            }
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, CustomerOrder.CREATED_DATE, "desc", Constant.MAX_PAGE_SIZE);
            Page<CustomerOrderPreview> orderPreviews = orderRepository.getAllOrderPreview(customerID, pageable);

            response = new OkResponse(orderPreviews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }
}