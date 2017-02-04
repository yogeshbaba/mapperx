package thotho;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperX {
	
	public static MapperX mapperX = new MapperX();
	public static MapperX instance(){
		return mapperX;
	}
	
	private Map<String, ObjectX> holder = new HashMap<>();
	
	public void set(ObjectX objectX, String key){
		holder.put(key, objectX);
	}
	
	public ObjectX fetch(String path){
		ObjectX ref = null;
		if(path.contains(".")){
			ref = mapperX.holder.get(path.substring(0, path.indexOf(".")));
			ref = ref.get(path.substring(path.indexOf(".")+1));
		} else {
			ref = mapperX.holder.get(path);
		}
		return ref;
	}
	
	public ObjectX constObjectX(Object instance){
		try{
			ObjectX root = ObjectX.createRoot();
			root.className = instance.getClass().getName();
			if(instance instanceof List){
				List<ObjectX> listX = new ArrayList<>();
				for(Object o : (List)instance){
					listX.add(constObjectX(o));
				}
				root.set("ROOT", listX);
			} else {				
				for(Field field : instance.getClass().getDeclaredFields()){
					field.setAccessible(true);
					if(field.getType().equals(String.class)){
						if(field.get(instance) != null){
							root.set(field.getName(), (String)field.get(instance));	
						}					
					}else if(field.getType().equals(List.class)){
						if(field.get(instance) != null){
							List list = (List) field.get(instance);
							List<ObjectX> listX = new ArrayList<>();
							for(Object o : list){
								listX.add(constObjectX(o));
							}
							root.set(field.getName(), listX);
						}					
					} else {
						if(field.get(instance) != null && field.get(instance)!= instance){
							root.set(field.getName(), constObjectX(field.get(instance)));	
						}					
					}
				}				
			}
			return root;
		}catch(IllegalArgumentException | IllegalAccessException e){
			throw new RuntimeException(e);
		}
	}
	
	public Object constInstance(ObjectX objectX) {
		try{
			
			Object instance = null;
			System.out.println("objectX.className = "+objectX.className);
			Class class1 = Class.forName(objectX.className);
			Constructor const1 = class1.getDeclaredConstructor();
			const1.setAccessible(true);
			instance = const1.newInstance();
			if(objectX.type == ObjectXType.OBJECT && objectX.get("ROOT").type == ObjectXType.ARRAY ){
				instance = new ArrayList<>();
				List list = (List) instance;
				for(int i=0 ; i <objectX.get("ROOT").length ; i++){
					list.add(constInstance(objectX.get("ROOT").get(Integer.toString(i))));
				}
			} else if(objectX.type == ObjectXType.OBJECT ){			
				for(Field field : class1.getDeclaredFields()){
					field.setAccessible(true);
					field.set(instance, constInstance(objectX.get(field.getName()))); //do null check
				}
			} else if(objectX.type == ObjectXType.ARRAY){
				instance = new ArrayList<>();
				List list = (List) instance;
				for(int i=0 ; i <objectX.length ; i++){
					list.add(constInstance(objectX.get(Integer.toString(i))));
				}
			} else if(objectX.type == ObjectXType.PRIMITIVE){
				instance = objectX.get();
			}
			return instance;
			
		} catch(ClassNotFoundException| InstantiationException| IllegalAccessException| NoSuchMethodException| SecurityException| IllegalArgumentException| InvocationTargetException e){
			throw new RuntimeException(e);
			
		}
		
	}

}