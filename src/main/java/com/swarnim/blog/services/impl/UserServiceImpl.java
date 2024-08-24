package com.swarnim.blog.services.impl;
import com.swarnim.blog.config.AppConstants;
import com.swarnim.blog.entities.Role;
import com.swarnim.blog.exceptions.*;
import com.swarnim.blog.entities.User;
import com.swarnim.blog.payloads.ApiResponse;
import com.swarnim.blog.payloads.UserDto;
import com.swarnim.blog.repositories.UserRepo;
import com.swarnim.blog.repositories.RoleRepo;
import com.swarnim.blog.services.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User saveUser = this.userRepo.save(user);
        return this.userToDto(saveUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " Id ", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updateUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updateUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " Id ", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " Id ", userId));
        this.userRepo.delete(user);
    }
    private User dtoToUser(UserDto userDto)
    {
        User user =this.modelMapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }
    public UserDto userToDto(User user){
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        // Check For User Exist with Given Email or not ..!
        Optional<User> checkUser=userRepo.findByEmail(userDto.getEmail());
        if(checkUser.isPresent()){
            throw new ApiException("User Already Exist With Given Email :"+ userDto.getEmail());
        }

        User user=modelMapper.map(userDto,User.class);
        // password coded
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //roles
        SimpleJpaRepository<Object, Object> roleRepository;
        Role role= roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        User savedUser=userRepo.save(user);

        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public void updateRole(Integer userID, Integer roleID) {

        //TODO: only admin can be updated to normal role

        User user= userRepo.findById(userID).orElseThrow( ()-> new ResourceNotFoundException("User", "ID", userID));

        Role role=roleRepo.findById(roleID).get();
        user.setRoles(new HashSet<>());
        user.getRoles().add(role);

        userRepo.save(user);
    }

    //check that file is of excel type or not
    public boolean checkExcelFormate(MultipartFile file){
        String contantType = file.getContentType();
        if(contantType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return true;
        } else {
            return false;
        }
    }

    //User bulk upload from excel
    public  ApiResponse registerUsersInBulk(MultipartFile file) throws IOException {
        List<UserDto> list = new ArrayList<>();
        //through this we will get the whole workbook sheet
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        // from this we need a particular sheet which contains data : sheet name = data
        XSSFSheet sheet = workbook.getSheet("data");
        int rowNumber = 0;
        Iterator<Row> iterator = sheet.iterator();
        while(iterator.hasNext()){
            Row row = iterator.next();
            if(rowNumber ==0){
                rowNumber++;
                continue;
            }
            Iterator<Cell> cells = row.iterator();
            int cid =0;

            //user obj to store the userdata
            UserDto userDto = new UserDto();

            while(cells.hasNext()) {
                Cell cell = cells.next();
                switch(cid){
                    case 0:
                        userDto.setName(cell.getStringCellValue());
                        break;
                    case 1:
                        userDto.setEmail(cell.getStringCellValue());
                        break;
                    case 2:
                        userDto.setPassword(cell.getStringCellValue());
                        break;
                    case 3:
                        userDto.setAbout(cell.getStringCellValue());
                        break;
                    default:
                        break;
                }
                cid++;
            }
            UserDto user = this.registerNewUser(userDto);
        }
        return new ApiResponse("User registered!!!!!",true);
    }
}
