package net.codejava.store.admin.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.codejava.store.admin.dao.ApplicationVersionRepository;
import net.codejava.store.admin.dao.StoreBranchRepository;
import net.codejava.store.admin.models.StoreBranch;
import net.codejava.store.admin.models.body.LatLngBody;
import net.codejava.store.admin.models.body.StoreBranchBody;
import net.codejava.store.admin.models.view.StoreBranchViewModel;
import net.codejava.store.auth.dao.UserRespository;
import net.codejava.store.auth.models.User;
import net.codejava.store.notification.FCMService;
import net.codejava.store.product.dao.CategoryRepository;
import net.codejava.store.product.dao.ProductsRepository;
import net.codejava.store.product.models.body.ClothesBody;
import net.codejava.store.product.models.data.Category;
import net.codejava.store.product.models.data.Product;
import net.codejava.store.response_model.NotFoundResponse;
import net.codejava.store.response_model.OkResponse;
import net.codejava.store.response_model.Response;
import net.codejava.store.response_model.ServerErrorResponse;
import net.codejava.store.utils.PageAndSortRequestBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Null;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/api/admins")
@Api(value = "Admin-api", description = "Nhóm API Admin, Yêu cầu access token của Admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    StoreBranchRepository storeBranchRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserRespository userRespository;
    @Autowired
    ApplicationVersionRepository applicationVersionRepository;

    @GetMapping("/version")
    Response getVersionApp(){
        Response response;
        try {
            int version = applicationVersionRepository.findAll().get(0).getVersion();
            response = new OkResponse(version);
        }catch (Exception e){
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }
    @ApiOperation(value = "thêm một chi nhánh cho công ty", response = Iterable.class)
    @PostMapping("/store_branch")
    Response InsertBranch(@RequestBody StoreBranchBody storeBranchBody) {
        Response response;
        try {
            StoreBranch storeBranch = new StoreBranch(storeBranchBody);
            storeBranchRepository.save(storeBranch);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }
    @ApiOperation(value = "Lấy tất cả chi nhánh công ty", response = Iterable.class)
    @GetMapping("/store_branch")
    Response getAllBranch() {
        Response response;
        try {
            Sort sort = PageAndSortRequestBuilder.createSortRequest(StoreBranch.CREATED_DATE, "desc");
            List<StoreBranchViewModel> lsBranch = storeBranchRepository.getStoreBranchViewModel(sort);
            response = new OkResponse(lsBranch);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }

        return response;
    }

    @PostMapping("clothes/{categoryID}")
    Response insertClothes(@PathVariable("categoryID")String categoryID,
                           @RequestBody ClothesBody clothesBody){
        Response response;
        try {
            Category category = categoryRepository.findOne(categoryID);
            if(category == null){
                return new NotFoundResponse("Category not exist!");
            }
            Product product = new Product(clothesBody);
            product.setCategory(category);
            productsRepository.save(product);
            List<User> users = userRespository.findAll();
            for (User u:users){
                FCMService.sendNotification(restTemplate,u.getFcmToken(), product);
            }
            response = new OkResponse(product);
        }catch (Exception e){
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }


//    @ApiOperation(value = "xuất thông tin sản phẩm quần áo", response = Iterable.class)
//    @PutMapping("import/clothes")
//    @Transactional(rollbackFor = Exception.class)
//    Response importClothes() {
//        Response response;
//
//        try {
//            InputStream ExcelFileToRead = new FileInputStream("D:\\Downloads\\store\\ptit.store\\src\\main\\resources\\demo.xlsx");
//            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
//            XSSFSheet sheet = wb.getSheetAt(0);
//            XSSFRow row;
//            XSSFCell cell;
//            List<Product> clothes = new ArrayList<>();
//            Iterator rows = sheet.rowIterator();
//
//            rows.next();
//            while (rows.hasNext()) {
//                Product product1 = new Product();
//                row = (XSSFRow) rows.next();
//                Iterator cellIterator = row.cellIterator();
//                cellIterator.next();
//                while (cellIterator.hasNext()) {
//                    cell = (XSSFCell) cellIterator.next();
////                    System.out.println(cell.getColumnIndex());
//                    switch (cell.getColumnIndex()) {
//                        case 0: {
//                            System.out.print("0");
////                            clothes1.setPrice((int) getCellValue(cell));
////                            System.out.println(getCellValue(cell).toString());
//                            break;
//                        }
//                        case 1: {
//                            System.out.print("1");
//
////                            clothes1.setDescription((String) getCellValue(cell));
//                            break;
//                        }
//                        case 2: {
//                            System.out.print("2");
//
//                            product1.setLogoUrl((String) getCellValue(cell));
//                            break;
//                        }
//
//                        case 3: {
//                            System.out.print("3");
//
//                            product1.setName((String) getCellValue(cell));
//                            break;
//                        }
//                        case 4: {
//                            System.out.print("4");
//
//                            product1.setCategory(categoryRepository.findOne((String) getCellValue(cell)));
//                            break;
//                        }
//                        case 5: {
//                            System.out.println("5");
//                            product1.setPrice((int)((double) getCellValue(cell)));
//                            break;
//                        }
//                    }
//                }
//                product1.setCreatedDate(new Date());
//                productsRepository.save(product1);
//                clothes.add(product1);
//            }
//
//            response = new OkResponse(clothes);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_BLANK: {
                return null;
            }
        }

        return null;
    }

    @ApiOperation(value = "khoảng cách các chi nhánh của công ty", response = Iterable.class)
    @PostMapping("/store_branch_distance")
    Response getAllBranchViewModel(@Null @RequestBody LatLngBody latLngBody,
                                   @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là : " + StoreBranch.CREATED_DATE)
                                   @RequestParam(value = "pageIndex", defaultValue = StoreBranch.CREATED_DATE) String sortBy,
                                   @ApiParam(name = "sortType", value = "Nhận asc|desc, mặc đính là desc")
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "desc") String sortType) {
        Response response;
        try {
            Sort sort = PageAndSortRequestBuilder.createSortRequest(sortBy, sortType);
            List<StoreBranchViewModel> lsBranch = storeBranchRepository.getStoreBranchViewModel(sort);
            for (StoreBranchViewModel storeBranchViewModel : lsBranch) {
                if (latLngBody.getLat() != -1&&latLngBody.getLng()!=-1) {
                    storeBranchViewModel.setDistance(distance(storeBranchViewModel.getLat(), storeBranchViewModel.getLng(),
                            latLngBody.getLat(), latLngBody.getLng()));
                } else {
                    storeBranchViewModel.setDistance(-1);
                }

            }
            Collections.sort(lsBranch, new Comparator<StoreBranchViewModel>() {
                @Override
                public int compare(StoreBranchViewModel storeBranchViewModel, StoreBranchViewModel t1) {
                    if (storeBranchViewModel.getDistance() > t1.getDistance())
                        return -1;
                    return 1;
                }
            });
            response = new OkResponse(lsBranch);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }

        return response;
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344;

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
