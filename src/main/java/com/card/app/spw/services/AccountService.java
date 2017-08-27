package com.card.app.spw.services;

import com.card.app.spw.webservices.Account;

/**
 * The Interface AccountService.
 */
public interface AccountService
{

	/**
	 * Gets the account details.
	 *
	 * @param accountNumber the account number
	 * @return the account details
	 */
	public Account getAccountDetails(String accountNumber);
}
