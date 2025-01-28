/*
 * Author: Misha Malinouski
 * Date:   5/25/2022
 * Rev:    01
 * Notes:  
 */

package misha.game.level.entity;

public abstract class PhysicsCollider {
	
	public static boolean DRAW_COLLIDERS = false;
	
//	public abstract Vector3 isIntersecting(PhysicsCollider other);
//	
//	protected static Vector3[] getPossibleSeparatingAxes(Vector3[] normalsA, Vector3[] normalsB) {
//		LinkedList<Vector3> axes = new LinkedList<>();
//		
//		for (int i = 0; i < normalsA.length; i++) {
//			Vector3 axis = normalsA[i].normalize();
//			if (axis.length() > 0)
//				axes.add(axis);
//		}
//		
//		for (int i = 0; i < normalsB.length; i++) {
//			Vector3 axis = normalsB[i].normalize();
//			if (axis.length() > 0)
//				axes.add(axis);
//		}
//		
//		for (int i = 0; i < normalsA.length; i++) {
//			for (int j = 0; j < normalsB.length; j++) {
//				Vector3 axis = normalsA[i].cross(normalsB[j]).normalize();
//				if (axis.length() > 0)
//					axes.add(axis);
//			}
//		}
//		
//		return axes.toArray(new Vector3[axes.size()]);
//	}
//	
//	protected static Vector2 projectVertices(Vector3[] vertices, Vector3 axis) {
//		double min = Double.MAX_VALUE;
//		double max = Double.MIN_VALUE;
//		
//		for (int i = 0; i < vertices.length; i++) {
//			double projection = vertices[i].dot(axis);
//			
//			min = Math.min(min, projection);
//			max = Math.max(max, projection);
//		}
//		
//		return new Vector2(min, max);
//	}
	
}
