package com.raju.admin.appuser;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raju.admin.common.Response;
import com.raju.admin.commoniservices.IMasterCommonServices;
import com.raju.admin.constants.MessageConstants;
import com.raju.admin.constants.QualifierConstants;
import com.raju.admin.constants.UrlConstants;

@RestController
@RequestMapping(UrlConstants.APPUSER)
public class AppUserController {

	@Autowired
	private AppUserServiceImpl appUserServiceImpl;

	@Autowired
	@Qualifier(QualifierConstants.APPUSER_QUALIFIER)
	private IMasterCommonServices<AppUserDTO, ?> iMasterCommonServices;

	private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

	@PostMapping
	public ResponseEntity<Response<Object>> save(@RequestBody UserDTO userDTO) {
		UserDTO savedUser = appUserServiceImpl.save(userDTO);
		if ("failed".equalsIgnoreCase(savedUser.getStatus())) {
			return ResponseEntity.ok(
					Response.returnResponse(null, savedUser.getMessage(), HttpStatus.CONFLICT, MessageConstants.ERROR));
		}

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("userId", savedUser.getUserId());
		return ResponseEntity.ok(Response.returnResponse(responseData,
				"User Master (" + savedUser.getUserId() + ") Saved Successfully.", HttpStatus.CREATED, "success"));
	}

	@GetMapping(value = UrlConstants.FIND_BY_ID)
	public ResponseEntity<Response<Object>> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(Response.returnResponse(iMasterCommonServices.findById(id),
				String.format(MessageConstants.SUCCESS_ID, MessageConstants.USERMASTER), HttpStatus.OK,
				MessageConstants.SUCCESS));
	}

	@PutMapping({ "/{id}" })
	public ResponseEntity<Response<Object>> update(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {

		UserDTO updatedUser = this.appUserServiceImpl.update(id, userDTO);

		if ("failed".equalsIgnoreCase(updatedUser.getStatus())) {
			return ResponseEntity.ok(Response.returnResponse(null, updatedUser.getMessage(), HttpStatus.OK, "failed"));
		}

		return ResponseEntity.ok(Response.returnResponse(
				String.format("%s (%s) Updated Successfully.", MessageConstants.USERMASTER, updatedUser.getFullName()),
				HttpStatus.CREATED, "success"));
	}

	@DeleteMapping(value = UrlConstants.DELETE)
	public ResponseEntity<Response<Object>> delete(@PathVariable("id") Long id) {
		return ResponseEntity
				.ok(Response.returnResponse(
						String.format(MessageConstants.SUCCESS_DELETE, MessageConstants.USERMASTER,
								iMasterCommonServices.deleteById(id).getFullName()),
						HttpStatus.OK, MessageConstants.SUCCESS));
	}

	@GetMapping({ "/list" })
	public ResponseEntity<Response<Object>> findAll(@RequestParam(name = "search", required = false) String search,
			Pageable pageable) {
		return ResponseEntity.ok(Response.returnResponse(this.appUserServiceImpl.findAll(search, pageable),
				String.format("Fetched %s List successfully.", MessageConstants.USERMASTER), HttpStatus.OK, "success"));
	}

	@GetMapping(value = UrlConstants.TOGGLE_ACTIVE_STATUS_BY_ID)
	public ResponseEntity<Response<Object>> toggleRiderActiveStatus(@PathVariable("id") Long id) {
		return ResponseEntity.ok(Response.returnResponse(
				String.format(MessageConstants.SUCCESS_UPDATE, MessageConstants.USERMASTER,
						appUserServiceImpl.toggleUserActiveStatus(id).getUserId()),
				HttpStatus.OK, MessageConstants.SUCCESS));
	}

	@PostMapping({ "/login" })
	public AppUserVO login(@RequestBody LoginDTO loginDTO) {
		return this.appUserServiceImpl.login(loginDTO.getUserName(), loginDTO.getPassword());
	}
	
	@GetMapping(value = "/wallet/{id}/add")
	public ResponseEntity<Response<Object>> addAmountToUserWallet(@PathVariable("id") Long id,@RequestParam Double amount) {
		return ResponseEntity.ok(Response.returnResponse(
				String.format(MessageConstants.SUCCESS_UPDATE, MessageConstants.USERMASTER,
						appUserServiceImpl.addAmountToUserWallet(id,amount).getUserId()),
				HttpStatus.OK, MessageConstants.SUCCESS));
	}

}
