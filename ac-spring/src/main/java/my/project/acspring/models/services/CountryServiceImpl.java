package my.project.acspring.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.project.acspring.models.dao.ICountryDao;
import my.project.acspring.models.entity.Country;
import my.project.acspring.models.interfaces.ICountryService;


@Service
public class CountryServiceImpl implements ICountryService {

	@Autowired
	private ICountryDao countryDao;
	
	
	@Transactional(readOnly = true)
	@Override
	public List<Country> getAll() {
		return (List<Country>) countryDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Country> getAllEmpty(boolean vacio) {
		return countryDao.findEmpty(vacio);
	}

	@Transactional(readOnly = true)
	@Override
	public Country getById(Long id) {
		return countryDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Country save(Country pais) {
		return countryDao.save(pais);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		countryDao.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean exists(Long id) {
		return countryDao.existsById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Long countCountries() {
		return countryDao.count();
	}

	@Transactional(readOnly = true)
	@Override
	public Long countEmpty(boolean vacio) {
		return countryDao.countEmpty(vacio);
	}

}
