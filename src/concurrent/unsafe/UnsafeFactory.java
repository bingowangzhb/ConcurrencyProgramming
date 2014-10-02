package concurrent.unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * Unsafe instance factory
 * 
 * @author Charles Chen
 * @date 2014Äê10ÔÂ2ÈÕ
 */
public class UnsafeFactory {
	/**
	 * Get unsafe instance via reflection
	 * 
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Unsafe getInstance() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafeInstance.setAccessible(true);
		return (Unsafe) theUnsafeInstance.get(Unsafe.class);
	}

	/**
	 * -XX:MaxDirectMemorySize=40m
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Unsafe unsafe = null;
		try {
			while (true) {
				unsafe = UnsafeFactory.getInstance();
				long pointer = unsafe.allocateMemory(10 * 1024 * 1024);
				System.out.println(unsafe.getByte(pointer + 1));
				unsafe.freeMemory(pointer);
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
