package test;

import java.util.ArrayList;
import java.util.List;

public class RefTest {

	public static void main(String[] args) {

		List<Obj> objs = new ArrayList<RefTest.Obj>();

		Obj obj1 = new Obj();
		obj1.setX(5);

		Obj obj2 = new Obj();
		obj2.setX(5);

		objs.add(obj1);
		objs.add(obj2);
		
		System.out.println(objs);
		
		for(Obj obj : objs) {
			obj.setY(5);
		}
		
		System.out.println(objs);

	}

	public static class Obj {

		private int x;

		private int y;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		@Override
		public String toString() {
			return "Obj [x=" + x + ", y=" + y + "]";
		}

	}

}
