package my.project.acspring.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.project.acspring.models.dao.IOriginDao;
import my.project.acspring.models.entity.Origin;
import my.project.acspring.models.interfaces.IOriginService;


@Service
public class OriginServiceImpl implements IOriginService {

	@Autowired
	private IOriginDao originDao;
	
	
	@Transactional(readOnly = true)
	@Override
	public List<Origin> getAll() {
		return (List<Origin>) originDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Origin> getAllEmpty(boolean empty) {
		return originDao.findEmpty(empty);
	}

	@Transactional(readOnly = true)
	@Override
	public Origin getById(Long id) {
		return originDao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public Origin getByName(String name) {
		return originDao.findByName(name);
	}

	@Transactional
	@Override
	public Origin save(Origin origin) {
		return originDao.save(origin);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		originDao.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean exists(Long id) {
		return originDao.existsById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Long count() {
		return originDao.count();
	}

	@Transactional(readOnly = true)
	@Override
	public Long countEmpty(boolean empty) {
		return originDao.countEmpty(empty);
	}

}
