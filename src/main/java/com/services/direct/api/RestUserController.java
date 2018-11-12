package com.services.direct.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.direct.bean.User;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
public class RestUserController {

	@Autowired
	  private UserService userService;

	  @Autowired
	  private ModelMapper modelMapper;

	  @PostMapping("/signin")
	  @ApiOperation(value = "${UserController.signin}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 422, message = "Invalid username/password supplied")})
	  public String login(//
	      @ApiParam("Username") @RequestParam String username, //
	      @ApiParam("Password") @RequestParam String password) {
	    return userService.signin(username, password);
	  }

	  @PostMapping("/signup")
	  @ApiOperation(value = "${UserController.signup}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 403, message = "Access denied"), //
	      @ApiResponse(code = 422, message = "Username is already in use"), //
	      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
	    return userService.signup(modelMapper.map(user, User.class));
	  }

	  @DeleteMapping(value = "/{username}")
	  @PreAuthorize("hasRole('ROLE_ADMIN')")
	  @ApiOperation(value = "${UserController.delete}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 403, message = "Access denied"), //
	      @ApiResponse(code = 404, message = "The user doesn't exist"), //
	      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  public String delete(@ApiParam("Username") @PathVariable String username) {
	    userService.delete(username);
	    return username;
	  }

	  @GetMapping(value = "/{username}")
	  @PreAuthorize("hasRole('ROLE_ADMIN')")
	  @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class)
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 403, message = "Access denied"), //
	      @ApiResponse(code = 404, message = "The user doesn't exist"), //
	      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
	    return modelMapper.map(userService.search(username), UserResponseDTO.class);
	  }

	  @GetMapping(value = "/me")
	  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	  @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class)
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 403, message = "Access denied"), //
	      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  public UserResponseDTO whoami(HttpServletRequest req) {
	    return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
	  }
	  
    @GetMapping
    public ResponseEntity<User> getUser(){

       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return  new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
