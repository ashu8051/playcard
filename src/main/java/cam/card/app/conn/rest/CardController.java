package cam.card.app.conn.rest;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.card.app.model.Permission;
import com.card.app.model.Role;
import com.card.app.model.User;
import com.card.app.rest.model.Customer;

@RestController
//@Controller
//@RequestMapping("/springrest")
public class CardController {

	
	@GetMapping(value = "/home/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Long id) {

	    //User user = userService.findById(1);
		User user = new User();
		Role role=new Role();
		Permission  per=new  Permission();
		per.setId(1);
		per.setName("Super");
		role.setId(1);
		role.setRoleName("SU");
		HashSet<Permission> h=new HashSet<Permission>();
		h.add(per);
		role.setPermissions(h);
		
		user.setId(1);
		user.setPasscode("ashu");
		user.setUserName("ashu");
		user.setRole(role);

	    if (user == null) {
	        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	    }
	    else {
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    }
	}

	@Autowired
	private CustomerDAO customerDAO;

	
	@GetMapping("/customers")
	public List getCustomers() {
		return customerDAO.list();
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity getCustomer(@PathVariable("id") Long id) {

		Customer customer = customerDAO.get(id);
		if (customer == null) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@PostMapping(value = "/customers")
	public ResponseEntity createCustomer(@RequestBody Customer customer) {

		customerDAO.create(customer);

		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity deleteCustomer(@PathVariable Long id) {

		if (null == customerDAO.delete(id)) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(id, HttpStatus.OK);

	}

	@PutMapping("/customers/{id}")
	public ResponseEntity updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {

		customer = customerDAO.update(id, customer);

		if (null == customer) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}
}
