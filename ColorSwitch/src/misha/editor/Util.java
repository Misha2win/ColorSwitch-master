/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

import misha.game.level.entity.CSColor;
import misha.game.level.entity.obstacle.Element;
import misha.game.level.entity.obstacle.Obstacle;
import misha.game.level.entity.platform.Platform;

public class Util {
	
	public static int sharesAllButOne(String str1, String str2) {
		if (str1.length() != str2.length())
			return -1;
		
		int mismatchIndex = -1;
		
		for (int i = 0; i < str1.length(); i++) {
			if (str1.charAt(i) != str2.charAt(i)) {
				if (mismatchIndex != -1) {
					return -1;
				}
				
				mismatchIndex = i;
			}
		}
		
		return mismatchIndex;
	}
	
	public static boolean contains(Object[] arr, Object object) {
		for (Object obj : arr) {
			if (obj == object) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] addToArray(T[] arr, T obj) {
		Class<?> clazz = (obj.getClass().equals(Platform.class) || obj instanceof String) ? obj.getClass() : (obj instanceof Element ? Obstacle.class : obj.getClass().getSuperclass());
		T[] newArray = (T[]) Array.newInstance(clazz, arr.length + 1);
		for (int i = 0; i < newArray.length - 1; i++) {
			newArray[i] = arr[i];
		}
		newArray[newArray.length - 1] = obj;
		return newArray;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] removeFromArray(T[] arr, T obj) {
		Class<?> clazz = (obj.getClass().equals(Platform.class) || obj instanceof String) ? obj.getClass() : obj.getClass().getSuperclass();
		T[] newArray = (T[]) Array.newInstance(clazz, arr.length - 1);
		for (int i = 0, j = 0; i < arr.length; i++) {
			if (arr[i] == obj) {
				j++;
			} else {
				if (i == arr.length - 1 && j == 0)
					return arr; // obj is not in arr!
					
				newArray[i - j] = arr[i];
			}
		}
		return newArray;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T createEntityInstance(Class<T> clazz) {
		// Get all constructors of the entity class, then find whichever constructor is the smallest and instantiate with it
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		
		Constructor<T> smallestConstructor = null;
		for (Constructor<?> constructor : constructors) {
			if (smallestConstructor == null || constructor.getParameterCount() < smallestConstructor.getParameterCount()) {
				smallestConstructor = (Constructor<T>) constructor;
			}
		}
		
		if (smallestConstructor != null) {
			Object[] constructorParameters = new Object[smallestConstructor.getParameterCount()];
			
			Class<?>[] parameterTypes = smallestConstructor.getParameterTypes();
			for (int i = 0; i < parameterTypes.length; i++) {
				if (parameterTypes[i].equals(CSColor.class)) {
					constructorParameters[i] = CSColor.RED;
				} else if (parameterTypes[i].equals(int.class)) {
					constructorParameters[i] = 0;
				} else if (parameterTypes[i].equals(boolean.class)) {
					constructorParameters[i] = false;
				} else {
					throw new IllegalStateException("Unsupported type " + parameterTypes[i].getName());
				}
			}
			
			try {
                return smallestConstructor.newInstance(constructorParameters);
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate entity", e);
            }
		} else {
            throw new IllegalStateException("No constructor found for " + clazz.getName());
        }
	}
	
}
