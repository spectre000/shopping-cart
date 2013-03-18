package org.spinach.cart.domain;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.persistence.*;

import org.spinach.cart.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;


/**
 * The persistent class for the City database table.
 * @author Muhammed Safeer
 */
@Entity
@Table(name="City")
@Component("city")
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="city_id", unique=true, nullable=false)
	private int cityId;

	@Column(name="city_name", length=45)
	private String cityName;

	//bi-directional many-to-one association to State
	@ManyToOne
	@JoinColumn(name="state_id", nullable=false)
	private State state;

	@Transient
	@Resource
	private CityRepository cityRepository;
	
	public City() {
	}

	public int getCityId() {
		return this.cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public CityRepository getCityRepository() {
		return cityRepository;
	}

	public void setCityRepository(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
	
	public void Add(City city){
		cityRepository.save(city);
	}
	
}