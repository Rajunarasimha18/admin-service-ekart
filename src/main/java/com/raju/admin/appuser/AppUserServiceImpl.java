package com.raju.admin.appuser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raju.admin.common.Mail;
import com.raju.admin.commoniservices.IMasterCommonServices;
import com.raju.admin.constants.MessageConstants;
import com.raju.admin.enums.Role;
import com.raju.admin.exception.CommonException;
import com.raju.admin.exception.DuplicateRecordException;
import com.raju.admin.security.JwtTokenProvider;

@Service
public class AppUserServiceImpl implements IMasterCommonServices<AppUserDTO, Long> {

	@Autowired
	private IAppUserDao iAppUserDetailsDao;

	@Autowired
	@Lazy
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Mail mail;

	@Override
	public AppUserDTO save(AppUserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUserDTO findById(Long id) {
		AppUser appUser = (AppUser) this.iAppUserDetailsDao.findById(id).orElseThrow(() -> {
			return new CommonException(String.format("Requested %s with the id : %s Not Found.", "User Master", id),
					HttpStatus.NOT_FOUND);
		});
		return this.modelMapper.map(this.iAppUserDetailsDao.save(appUser), AppUserDTO.class);
	}

	@Override
	public AppUserDTO update(Long id, AppUserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUserDTO deleteById(Long id) {
		AppUser appUser = (AppUser) this.iAppUserDetailsDao.findById(id).orElseThrow(() -> {
			return new CommonException(String.format("Requested %s with the id : %s Not Found.", "User Master", id),
					HttpStatus.NOT_FOUND);
		});
		appUser.setIsDeletedValue(true);
		return this.modelMapper.map(this.iAppUserDetailsDao.save(appUser), AppUserDTO.class);
	}

	@Override
	public Page<AppUserDTO> findAllByCompany(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserDTO update(Long id, UserDTO dto) {
		AppUser appUser = (AppUser) this.iAppUserDetailsDao.findById(id).orElseThrow(() -> {
			return new CommonException(String.format("Requested %s with the id : %s Not Found.", "User Master", id),
					HttpStatus.NOT_FOUND);
		});

		if (Objects.nonNull(dto.getFullName())) {
			appUser.setFullName(dto.getFullName());
		}
		if (Objects.nonNull(dto.getMobile())) {
			appUser.setMobile(dto.getMobile());
		}
		if (Objects.nonNull(dto.getEmail())) {
			appUser.setEmail(dto.getEmail());
		}
		return (UserDTO) this.modelMapper.map(this.iAppUserDetailsDao.save(appUser), UserDTO.class);
	}

	public AppUserVO login(String userName, String password) {
		try {
			AppUser appUser = null;

			// ---------- FETCH USER ----------
			if (userName.equals("admin@ekart.com")) {

				List<AppUser> users = iAppUserDetailsDao.findByUserNameByList(userName);

				if (users.isEmpty()) {
					// ---------- SAFE ADMIN AUTO CREATION ----------
					UserDTO dto = new UserDTO();
					dto.setUserName("admin@ekart.com");
					dto.setFullName("admin@ekart.com");
					dto.setEmail("admin@ekart.com");
					List<Role> rolesList = new ArrayList<>();
					rolesList.add(Role.ROLE_SUPER_ADMIN);
					dto.setRoles(rolesList);

					this.save(dto);

					throw new CommonException("Admin user created. Please login again",
							HttpStatus.UNPROCESSABLE_ENTITY);
				}

				appUser = users.get(0);

			} else {
				appUser = iAppUserDetailsDao.findByUserName(userName);

				if (appUser == null) {
					throw new CommonException("Invalid username/password", HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}

			// ---------- USER STATUS VALIDATION ----------
			if (!appUser.getIsActive()) {
				throw new CommonException("User is locked contact administrator", HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (appUser.getIsDeletedValue()) {
				throw new CommonException("Invalid username/password", HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// ---------- PASSWORD CHECK ----------
			boolean passwordMatched = mail.md5Generator(password).equals(appUser.getPassword())
					|| password.equals(appUser.getPassword());

			if (!passwordMatched) {
				throw new CommonException("Invalid username/password", HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// ---------- SUCCESS ----------
			AppUserVO appUserVO = modelMapper.map(appUser, AppUserVO.class);

			appUserVO.setToken(jwtTokenProvider.generateToken(appUser.getUserName()));

			appUser.setCurrentToken(appUserVO.getToken());
			iAppUserDetailsDao.save(appUser);

			return appUserVO;

		} catch (CommonException ex) {
			throw ex;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CommonException("Something went wrong. Please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public UserDTO save(UserDTO appUserDTO) {

		if (!appUserDTO.getEmail().equals("admin@ekart.com")) {
			AppUser au = iAppUserDetailsDao.findUserByEmail(appUserDTO.getEmail());

			if (au != null) {
				throw new DuplicateRecordException("This email already registered in our system with different user");
			}
		}

		try {
			// Generate hash password
			String pwd = appUserDTO.getPassword() != null ? appUserDTO.getPassword() : appUserDTO.getEmail();
			String hashPwd = mail.md5Generator(pwd);
			appUserDTO.setPassword(hashPwd);
			appUserDTO.setUserName(appUserDTO.getMobile());

			AppUser appUserEntity = modelMapper.map(appUserDTO, AppUser.class);

			// -------- KEEPING YOUR CONDITION --------
			if (!appUserDTO.getEmail().equalsIgnoreCase("admin@ekart.com")) {

				// If you want to add extra logic for normal users, put here
				// Example: send email, set username, etc.
				// Currently you commented everything, so nothing special happens.

			}

			boolean emailExists = iAppUserDetailsDao.existsUserByEmail(appUserEntity.getEmail());

			boolean mobileExists = iAppUserDetailsDao.existsUserByMobile(appUserEntity.getMobile());

			if (emailExists || mobileExists) {
				UserDTO errorDto = new UserDTO();

				if (emailExists) {
					errorDto.setMessage("This email already registered in our system with different user");
				}
				if (mobileExists) {
					errorDto.setMessage("This phone number already registered in our system with different user");
				}

				errorDto.setStatus("failed");
				return errorDto;
			}

			AppUser savedUser = iAppUserDetailsDao.save(appUserEntity);

			return modelMapper.map(savedUser, UserDTO.class);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CommonException("Failed to create user: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Page<AppUserDTO> findAll(String search, Pageable pageable) {
		if (search != null && !search.isEmpty()) {
			search = Pattern.quote(search);
		}
		Page<AppUser> page;

		if (search == null || search.trim().isEmpty()) {
			page = this.iAppUserDetailsDao.getUserList1(pageable);
		} else {
			page = this.iAppUserDetailsDao.getUserList(search, pageable);
		}
		return page.map(driver -> {
			AppUserDTO dto = new AppUserDTO();
			BeanUtils.copyProperties(driver, dto);
			return dto;
		});
	}

	public AppUserDTO toggleUserActiveStatus(Long id) {
		AppUser r = iAppUserDetailsDao.findById(id)
				.orElseThrow(() -> new CommonException(
						String.format(MessageConstants.NOT_FOUND, MessageConstants.USERMASTER, id),
						HttpStatus.NOT_FOUND));

		if (r.getIsActive()) {
			AppUser appUser = (AppUser) this.iAppUserDetailsDao.findById(id).orElseThrow(() -> {
				return new CommonException(String.format("Requested %s with the id : %s Not Found.", "User Master", id),
						HttpStatus.NOT_FOUND);
			});
			appUser.setIsActive(false);
			this.iAppUserDetailsDao.save(appUser);
		} else {
			AppUser appUser = (AppUser) this.iAppUserDetailsDao.findById(id).orElseThrow(() -> {
				return new CommonException(String.format("Requested %s with the id : %s Not Found.", "User Master", id),
						HttpStatus.NOT_FOUND);
			});
			appUser.setIsActive(true);
			this.iAppUserDetailsDao.save(appUser);
		}

		r.setIsActive(!r.getIsActive());
		return modelMapper.map(iAppUserDetailsDao.save(r), AppUserDTO.class);
	}

	public AppUserDTO addAmountToUserWallet(Long id, Double amount) {
		AppUser r = iAppUserDetailsDao.findById(id)
				.orElseThrow(() -> new CommonException(
						String.format(MessageConstants.NOT_FOUND, MessageConstants.USERMASTER, id),
						HttpStatus.NOT_FOUND));		
		if(r.getWalletBalance()!=null) {
			r.setWalletBalance(r.getWalletBalance()+amount);
		}
		else {
			r.setWalletBalance(amount);
		}
		
		return modelMapper.map(iAppUserDetailsDao.save(r), AppUserDTO.class);
	}

}
