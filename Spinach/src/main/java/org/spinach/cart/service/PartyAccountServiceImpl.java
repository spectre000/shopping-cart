package org.spinach.cart.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.spinach.cart.bean.AccountBean;
import org.spinach.cart.domain.Party;
import org.spinach.cart.domain.UserLogin;
import org.spinach.cart.exception.CartException;
import org.spinach.cart.mapper.PartyMapper;
import org.spinach.cart.util.UUIDGenerator;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Muhammed Safeer
 *
 */
@Component("partyAccountService")
public class PartyAccountServiceImpl implements PartyAccountService{

	private static final String DISABLED = "N";
	
	@Resource
	PartyMapper partyMapper;
	
	@Resource
	Party party;
	
	@Resource
	UserLogin userLogin;
	
	private static Logger logger = Logger.getLogger(PartyAccountServiceImpl.class);
	public PartyMapper getPartyMapper() {
		return partyMapper;
	}

	public void setPartyMapper(PartyMapper partyMapper) {
		this.partyMapper = partyMapper;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	@Override
	public void signUp(AccountBean account) throws CartException{
		try{
			UserLogin userLogin= partyMapper.getUserLogin(account);
			Party party = new Party();
			party.setPartyId(UUIDGenerator.getuuid());
			party.setCreatedOn(new Date());
			party.setUserLogin(userLogin);
			userLogin.setUserLoginId(UUIDGenerator.getuuid());
			userLogin.setParty(party);
			userLogin.setEnabled(DISABLED);
			this.party.addParty(party);
		}
		catch(RuntimeException ex){
			logger.error("Exception occured during the signup process.", ex);
			throw new CartException();
		}
	}

	@Override
	public boolean login(AccountBean account) throws CartException {
		boolean resultFromLogin = false;
		try{
			UserLogin userLogin= partyMapper.getUserLogin(account);
			userLogin = this.userLogin.isExist(userLogin.getUserId(), userLogin.getCurrentPassword());
			resultFromLogin = true;
			if(userLogin == null){
				resultFromLogin = false;
			}
		}
		catch(RuntimeException ex){
			logger.error("Exception occured during the Login process.", ex);
			throw new CartException();
		}
		return resultFromLogin;
	}
}
