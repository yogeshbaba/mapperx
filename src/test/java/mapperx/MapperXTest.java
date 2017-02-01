package mapperx;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import thotho.MapperX;
import thotho.ObjectX;

public class MapperXTest {
	
	@org.junit.Test
	public void basicFetch(){
		MapperX mapper = MapperX.instance();
		ObjectX oX = mapper.constObjectX(constObject());
		mapper.set(oX, "cust");
		assertEquals("ravi",mapper.fetch("cust.name").get());
		assertEquals("bangalore",mapper.fetch("cust.address.0.city").get());
	}
	
	public Person constObject(){
		Person p = new Person();
		p.setName("ravi");
		p.setAge("77");
		List<Address> addresses = new ArrayList<>();
		Address address1 = new Address();
		address1.setCity("bangalore");
		address1.setStreet("aecs");
		addresses.add(address1);
		Address address2 = new Address();
		address2.setCity("chennai");
		address2.setStreet("annanagar");
		addresses.add(address2);
		Department department = new Department();
		department.setName("CS");
		p.setDepartment(department);
		p.setAddress(addresses);
		return p;
		
	}

}


class Person {
	private String name;
	private String age;
	private List<Address> address;
	private Department department;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
}

class Address {
	private String street;
	private String city;
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}

class Department {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}