package thotho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ObjectX {
	
	Type type;
	Map<String, ObjectX> fields = new HashMap<>();
	Map<String, Function<ObjectX, ObjectX>> customField = new HashMap<>();
	Map<String, String> cust2Actual = new HashMap<>();
	String value;
	String className; //className
	String id; //applicable array time for a give field
	
	public ObjectX(){
		
	}
	
	public ObjectX(String value){
		this.type = Type.PRIMITIVE;
		this.value = value;
		this.className = value.getClass().getName();
	}

	public String get(){
		return value;
	}
	
	public ObjectX get(String field){
		ObjectX tmp = this;
		for(String subField : field.split("\\.")){
			if(tmp.fields.containsKey(subField)){
				System.out.println(subField);
				tmp = tmp.fields.get(subField);
			} else {
				if(tmp.customField.containsKey(field) && tmp.cust2Actual.containsKey(field)){
					return tmp.customField.get(field).apply(tmp.fields.get(tmp.cust2Actual.get(field)));
				}
			}
		}
		return tmp;
	}
	public List<ObjectX> getList(){
		return new ArrayList<>(fields.values());
	}
	
	public void set(String field, String value){
		fields.put(field, new ObjectX(value));
	}
	
	public void setCustomField(String field, String srcField, Function<ObjectX, ObjectX> map){
		cust2Actual.put(field, srcField);
		customField.put(field, map);
	}
	
	public void set(String field, List<ObjectX> values){
		ObjectX holder = createRoot();
		holder.type = type.ARRAY;
		for(int i=0 ; i<values.size(); i++){
			holder.fields.put(String.valueOf(i), values.get(i));
		}
		fields.put(field, holder);
	}
	
	public void set(String field, ObjectX value){
		fields.put(field, value);
	}
	
	public static ObjectX createRoot(){
		ObjectX o = new ObjectX();
		o.type = Type.OBJECT;
		return o;
	}
	
	
	@Override
	public String toString() {
		return "ObjectX [\ntype=" + type + ", fields=\n" + fields + ", customField=" + customField + ", cust2Actual="
				+ cust2Actual + ", value=" + value + ", className=" + className + ", id=" + id + "]";
	}
	
	
}

enum Type {OBJECT, ARRAY,PRIMITIVE}
