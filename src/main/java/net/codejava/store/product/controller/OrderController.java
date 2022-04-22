package net.codejava.store.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.codejava.store.auth.dao.UserRespository;
import net.codejava.store.constants.Constant;
import net.codejava.store.customer.dao.CustomerRespository;
import net.codejava.store.customer.models.data.Customer;
import net.codejava.store.product.dao.*;
import net.codejava.store.product.models.body.DetailBody;
import net.codejava.store.product.models.body.OrderBody;
import net.codejava.store.product.models.data.Order;
import net.codejava.store.product.models.data.OrderDetail;
import net.codejava.store.product.models.data.Product;
import net.codejava.store.product.models.view.*;
import net.codejava.store.response_model.*;
import net.codejava.store.utils.PageAndSortRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@Api(value = "candidate-api", description = "Nhóm API Order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private CustomerRespository customerRespository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    /**********************Order********************/
    @ApiOperation(value = "Danh sách hóa đơn", response = Iterable.class)
    @GetMapping("/orders")
    public Response getAllOrders(
            @RequestParam("type") int type,
            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Order.CREATE_AT)
            @RequestParam(value = "sortBy", defaultValue = Order.CREATE_AT) String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
    ) {
        Response response;

        try {
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<OrderPreview> orderView = null;
            switch (type) {
                case 0: {
                    orderView = orderRepository.getAllOrderPreview(pageable);
                    break;
                }
                case 1: {
                    orderView = orderRepository.getOrderChecked(pageable);
                    break;
                }
                case 2: {
                    orderView = orderRepository.getOrderUnchecked(pageable);
                    break;
                }
                case 3: {
                    orderView = orderRepository.getOrderCanceled(pageable);
                    break;
                }
            }

            response = new OkResponse(orderView);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @PostMapping("/delorder")
    @ApiOperation(value = "api xóa 1 hóa đơn", response = Iterable.class)
    public Response deleteOrder(@RequestBody int id) {
        Response response;
        try {
            orderRepository.delete(id);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }

        return response;
    }

    @PostMapping("/getStaticCustomerOrder")
    @ApiOperation(value = "api thống kê hóa đơn khách hàng", response = Iterable.class)
    public Response getStaticCustomerOrder(@RequestParam String customerId) {
        Response response;
        try {
            long orderChecked = orderRepository.getOrderCheckedByCustomerId(customerId).size();
            long orderUnchecked = orderRepository.getOrderUncheckedByCustomerId(customerId).size();
            long orderCanceled = orderRepository.getOrderCanceledByCustomerId(customerId).size();
            response = new OkResponse(new OrderStaticView(orderUnchecked, orderCanceled,  orderChecked));
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }

        return response;
    }

    @PostMapping("/ordersuccess")
    @ApiOperation(value = "api xử lý đơn hàng", response = Iterable.class)
    public Response updateOrder(int id) {
        Response response;
        try {
            Order order = orderRepository.getOne(id);
            order.setIsCheck(1);
            order.setUpdateAt(new Date());
            orderRepository.save(order);
            response = new OkResponse(order.getUpdateAt());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @GetMapping("/getdetail/{id}")
    @ApiOperation(value = "api chi tiết hóa đơn", response = Iterable.class)
    public Response getOrderDetail(@PathVariable("id") int id) {
        Response response;
        try {
            Order order = orderRepository.getOne(id);
            List<OrderDetailView> details = orderDetailRepository.getDetail(id);
            int totalPrice = orderDetailRepository.getTotalPrice(id);
            OrderPreview orderPreview = new OrderPreview(order);
            OrderDetailResultView orderDetailResultView = new OrderDetailResultView(
                    order.getCustomerName(),
                    order.getPhone(),
                    order.getAddress(),
                    orderPreview,
                    details,
                    totalPrice
            );
            response = new OkResponse(orderDetailResultView);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @PostMapping("/addorder")
    @ApiOperation(value = "api thêm 1 hóa đơn", response = Iterable.class)
    public Response addOrder(@RequestBody OrderBody body) {
        Response response;
        try {
            Customer c = customerRespository.getOne(body.getCustomerID());
            Order order = new Order();
            order.setCustomer(c);
            order.addOrder(body);
            double total = 0;
            for (DetailBody d : body.getDetails()) {
                Product p = productsRepository.getOne(d.getProductId());
                if (p.getQuantity() < d.getQuantity()){
                    return new ServerErrorResponse();
                }
                OrderDetail detail = new OrderDetail();
                detail.setProduct(p);
                detail.addDetail(d);
                detail.setOrder(order);
                total += detail.getTotal();
                p.setQuantity(p.getQuantity() - d.getQuantity());
                productsRepository.save(p);
                orderDetailRepository.save(detail);
            }
            order.setTotal(total);
            orderRepository.save(order);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @PostMapping("/cancelorder")
    @ApiOperation(value = "api hủy 1 hóa đơn", response = Iterable.class)
    public Response cancelOrder(int id) {
        Response response;
        try {
            Order order = orderRepository.getOne(id);
            order.setIsCheck(-1);
            order.setUpdateAt(new Date());
            orderRepository.save(order);

            List<Object[]> data = orderDetailRepository.getCancelDetail(id);
            if (data != null && !data.isEmpty()){
                for (Object[] object: data) {
                    Product p = productsRepository.getOne((String) object[0]);
                    p.setQuantity(p.getQuantity() + (int) object[1]);
                    productsRepository.flush();
                    productsRepository.save(p);
                }
            }

            response = new OkResponse(order.getUpdateAt());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @GetMapping("/getorder/{id}")
    @ApiOperation(value = "api lấy thông tin 1 hóa đơn", response = Iterable.class)
    public Response getOneOrder(@PathVariable("id") int id) {
        Response response;
        try {
            Order order = orderRepository.getOne(id);
            Customer c = customerRespository.getOne(order.getCustomer().getId());
            OrderView view = new OrderView();
            view.setId(order.getId());
            view.setCreatedDate(order.getCreateAt());
            view.setTotal(order.getTotal());
            view.setCustomerName(c.getFullName());
            view.setPhone(c.getPhone());
            view.setAddress(c.getAddress());
            response = new OkResponse(view);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

//    @ApiOperation(value = "Lấy toàn bộ hóa đơn đã check", response = Iterable.class)
//    @GetMapping("/orderschecked")
//    public Response getOrderChecked (
//            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
//            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
//            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
//            @RequestParam(value = "pageSize", required = false) Integer pageSize,
//            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Order.CREATE_AT)
//            @RequestParam(value = "sortBy", defaultValue = Order.CREATE_AT) String sortBy,
//            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
//            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
//    ) {
//        Response response;
//
//        try {
//            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
//            Page<OrderPreview> orderView = orderRepository.getOrderChecked(pageable);
//            response = new OkResponse(orderView);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }

    @ApiOperation(value = "api search order", response = Iterable.class)
    @GetMapping("/searchOrder")
    public Response searchProduct(
            @RequestParam("orderId") int id,
            @RequestParam("type") int type,
            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Order.CREATE_AT)
            @RequestParam(value = "sortBy", defaultValue = Order.CREATE_AT) String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
    ) {
        Response response;

        try {
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<OrderPreview> orderPreviews = null;
            switch (type) {
                case 0: {
                    orderPreviews = orderRepository.searchByOrderId(pageable, id);
                    break;
                }
                case 1: {
                    orderPreviews = orderRepository.searchByOrderIdAndStatus(pageable, id, 1);
                    break;
                }
                case 2: {
                    orderPreviews = orderRepository.searchByOrderIdAndStatus(pageable, id, 0);
                    break;
                }
                case 3: {
                    orderPreviews = orderRepository.searchByOrderIdAndStatus(pageable, id, -1);
                    break;
                }
            }
            response = new OkResponse(orderPreviews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

//    @ApiOperation(value = "Lấy toàn bộ hóa đơn chưa check", response = Iterable.class)
//    @GetMapping("/ordersunchecked")
//    public Response getOrderUnchecked(
//            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
//            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
//            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
//            @RequestParam(value = "pageSize", required = false) Integer pageSize,
//            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Order.CREATE_AT)
//            @RequestParam(value = "sortBy", defaultValue = Order.CREATE_AT) String sortBy,
//            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
//            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
//    ) {
//        Response response;
//
//        try {
//            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
//            Page<OrderPreview> orderView = orderRepository.getOrderUnchecked(pageable);
//            response = new OkResponse(orderView);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }
//    @ApiOperation(value = "Lấy toàn bộ hóa đơn đã hủy", response = Iterable.class)
//    @GetMapping("/orderscanceled")
//    public Response getOrderCanceled(
//            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
//            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
//            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
//            @RequestParam(value = "pageSize", required = false) Integer pageSize,
//            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Order.CREATE_AT)
//            @RequestParam(value = "sortBy", defaultValue = Order.CREATE_AT) String sortBy,
//            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
//            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
//    ) {
//        Response response;
//
//        try {
//            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
//            Page<OrderPreview> orderView = orderRepository.getOrderCanceled(pageable);
//            response = new OkResponse(orderView);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }

    @ApiOperation(value = "Lấy toàn bộ hóa đơn của khách hàng", response = Iterable.class)
    @GetMapping("/customerorder/{customerid}")
    public Response getCustomerOrder(
            @PathVariable("customerid") String customerid,
            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Order.CREATE_AT)
            @RequestParam(value = "sortBy", defaultValue = Order.CREATE_AT) String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
    ) {
        Response response;

        try {
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<OrderPreview> orderView = orderRepository.getCustomerOrder(customerid,pageable);
            response = new OkResponse(orderView);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

}
