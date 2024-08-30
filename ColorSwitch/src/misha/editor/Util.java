/*
 * Author: Misha Malinouski
 * Date:   12/18/2022
 * Rev:    01
 * Notes:  
 */

package misha.editor;

import java.lang.reflect.Array;
import misha.game.level.entity.platform.Platform;

public class Util {
	
	@SuppressWarnings("unchecked")
	public static <T> T[] addToArray(T[] arr, T obj) {
		Class<?> clazz = (obj.getClass().equals(Platform.class) || obj instanceof String) ? obj.getClass() : obj.getClass().getSuperclass();
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
	
}
