package com.raju.admin.roles;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.raju.admin.commoniservices.IMasterCommonServices;

@Service
public class RolesServiceImpl implements IMasterCommonServices<RolesDTO, Long> {

	@Override
	public RolesDTO save(RolesDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolesDTO findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolesDTO update(Long id, RolesDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolesDTO deleteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<RolesDTO> findAllByCompany(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
