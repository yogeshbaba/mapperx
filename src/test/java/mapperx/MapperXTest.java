package mapperx;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import thotho.MapperX;
import thotho.ObjectX;
import thotho.ObjectXType;

public class MapperXTest {
	
	@org.junit.Test
	public void basicFetch(){
		MapperX mapper = MapperX.instance();
		ObjectX oX = mapper.constObjectX(constObject());
		mapper.set(oX, "cust");
		assertEquals("ravi",mapper.fetch("cust.name").get());
		assertEquals("bangalore",mapper.fetch("cust.address.0.city").get());
	}
	
	
	@org.junit.Test
	public void basicFetchWithoutAddress(){
		MapperX mapper = MapperX.instance();
		Person person = constObject();
		person.setAddress(null);
		ObjectX oX = mapper.constObjectX(person);
		mapper.set(oX, "cust");
		assertEquals("ravi",mapper.fetch("cust.name").get());
		assertEquals(ObjectXType.NOTFOUND,mapper.fetch("cust.address.0.city").type);
	}
	
	@org.junit.Test
	public void basicFetchWithInvalidField(){
		MapperX mapper = MapperX.instance();
		Person person = constObject();
		person.setAddress(null);
		ObjectX oX = mapper.constObjectX(person);
		mapper.set(oX, "cust");
		assertEquals("ravi",mapper.fetch("cust.name").get());
		assertEquals(ObjectXType.NOTFOUND,mapper.fetch("cust.name1").type);
	}
	
	@Test
	public void testRootList(){
		MapperX mapper = MapperX.instance();
		List<Person> persons = new ArrayList<>();
		persons.add(constObject());
		ObjectX objX = mapper.constObjectX(persons);
		System.out.println(objX);
		assertEquals("ravi",objX.get("ROOT.0.name").get());
	}
	
	@Test
	public void testConstBack(){
		MapperX mapper = MapperX.instance();
		ObjectX oX = mapper.constObjectX(constObject());
		Person object2 = (Person) mapper.constInstance(oX);			
		assertEquals(constObject().getAge(), object2.getAge()) ;
	}
	
	@Test
	public void testConstBackRootList(){
		MapperX mapper = MapperX.instance();
		List<Person> persons = new ArrayList<>();
		persons.add(constObject());
		ObjectX oX = mapper.constObjectX(persons);
		List<Person>  object2 = (List<Person>) mapper.constInstance(oX);			
		assertEquals(persons.get(0).getAge(), object2.get(0).getAge()) ;
		
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
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", address=" + address + ", department=" + department + "]";
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
	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + "]";
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

	@Override
	public String toString() {
		return "Department [name=" + name + "]";
	}
	
	
}