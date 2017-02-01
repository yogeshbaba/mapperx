package mapperx;

import org.junit.Test;

import org.junit.Assert;

import org.junit.Test;

import thotho.ObjectX;

public class ObjectXTest {
	
	@Test
	public void getField(){
		ObjectX o = ObjectX.createRoot();
		o.set("name", "hari");
		o.set("age", "90");
		ObjectX address = ObjectX.createRoot();
		address.set("street", "s1");
		address.set("city", "bangalore");
		o.set("address", address);
		Assert.assertEquals("hari", o.get("name").get());
		Assert.assertEquals("s1", o.get("address.street").get());
		
	}

}
