package my.project.acspring.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.project.acspring.models.dao.IClassicDao;
import my.project.acspring.models.entity.Classic;
import my.project.acspring.models.interfaces.IClassicService;


@Service
public class ClassicServiceImpl implements IClassicService {
	
	@Autowired
	private IClassicDao classicDao;
	

	@Transactional(readOnly = true)
	@Override
	public List<Classic> getThree() {
		return classicDao.findThree();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Classic> getAll() {
		return (List<Classic>) classicDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Classic> getByBrand(Long id) {
		return classicDao.findByBrand(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Classic> getByOrigin(String name) {
		return classicDao.findByOrigin(name);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Classic> getByStatus(Boolean approved) {
		return classicDao.findByStatus(approved);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Classic> getDeleted() {
		return classicDao.findDeleted();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Classic> getByUser(Long id) {
		return classicDao.findByUser(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Classic> getByUserApproved(Long id, boolean approved) {
		return classicDao.findByUserApproved(id, approved);
	}

	@Transactional(readOnly = true)
	@Override
	public Classic getById(Long id) {
		return classicDao.findOne(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Classic getByIdApproved(Long id) {
		return classicDao.findOneApproved(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Classic getByIdByUser(Long idUser, Long idClassic) {
		return classicDao.findOneByUser(idUser, idClassic);
	}

	@Transactional
	@Override
	public Classic save(Classic classic) {
		return classicDao.save(classic);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		classicDao.deleteById(id);
	}

	@Transactional
	@Override
	public void deleteByUser(Long idUser, Long idClassic) {
		classicDao.deleteByUser(idUser, idClassic);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean exists(Long id) {
		return classicDao.existsById(id);
	}

	@Transactional
	@Override
	public void approve(Long id) {
		classicDao.validate(id);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Long count() {
		return classicDao.count();
	}

	@Transactional(readOnly = true)
	@Override
	public Long countApproved(boolean approved) {
		return classicDao.countApproved(approved);
	}

	@Transactional(readOnly = true)
	@Override
	public Long countApprovedByBrand(Long id) {
		return classicDao.countApprovedByBrand(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Long countDeleted() {
		return classicDao.countDeleted();
	}

	@Transactional(readOnly = true)
	@Override
	public Long countUserClassics(Long id) {
		return classicDao.countUserClassics(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Long countUserClassicsApproved(Long id, Boolean approved) {
		return classicDao.countUserClassicsApproved(id, approved);
	}

}
