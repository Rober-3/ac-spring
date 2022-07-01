package my.project.acspring.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.project.acspring.models.dao.IBrandDao;
import my.project.acspring.models.entity.Brand;
import my.project.acspring.models.interfaces.IBrandService;


@Service
public class BrandServiceImpl implements IBrandService {

	@Autowired
	private IBrandDao brandDao;

	
	@Transactional(readOnly = true)
	@Override
	public List<Brand> getAll() {
		return (List<Brand>) brandDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Brand> getAllEmpty(boolean empty) {
		return brandDao.findEmpty(empty);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Brand> getAllWithClassicsApproved() {
		return brandDao.findWithClassicsApproved();
	}

	@Transactional(readOnly = true)
	@Override
	public Brand getById(Long id) {
		return brandDao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public Brand getByIdWithClassicsApproved(Long id) {
		return brandDao.findByIdWithClassicsApproved(id);
	}

	@Transactional
	@Override
	public Brand save(Brand brand) {
		return brandDao.save(brand);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		brandDao.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean exists(Long id) {
		return brandDao.existsById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Long count() {
		return brandDao.count();
	}

	@Transactional(readOnly = true)
	@Override
	public Long countEmpty(boolean empty) {
		return brandDao.countEmpty(empty);
	}

}
