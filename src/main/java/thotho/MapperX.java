package thotho;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperX {
	
	private Map<String, ObjectX> holder = new HashMap<>();
	public void set(ObjectX objectX, String key){
		holder.put(key, objectX);
	}
	public static MapperX mapperX = new MapperX();
	public static MapperX instance(){
		return mapperX;
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
			for(Field field : instance.getClass().getDeclaredFields()){
				field.setAccessible(true);
				if(field.getType().equals(String.class)){
					root.set(field.getName(), (String)field.get(instance));
				} else if(field.getType().equals(List.class)){
					List list = (List) field.get(instance);
					List<ObjectX> listX = new ArrayList<>();
					for(Object o : list){
						listX.add(constObjectX(o));
					}
					root.set(field.getName(), listX);
				} else {
					root.set(field.getName(), constObjectX(field.get(instance)));
				}
			}
			return root;
		}catch(IllegalArgumentException | IllegalAccessException e){
			throw new RuntimeException(e);
		}
	}

}
